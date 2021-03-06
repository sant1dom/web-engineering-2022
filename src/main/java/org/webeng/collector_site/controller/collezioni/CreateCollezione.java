package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.CollezioneImpl;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Genere;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateCollezione extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            if (request.getMethod().equals("POST")) {
                saveCollezione(request, response);
            } else {
                try {
                    HttpSession s = SecurityHelpers.checkSession(request);
                    String https_redirect_url = SecurityHelpers.checkHttps(request);
                    request.setAttribute("https-redirect", https_redirect_url);
                    if (s == null) {
                        action_anonymous(request, response);
                    } else {
                        //Ottengo l'utente loggato
                        Utente utente = Utility.getUtente(request, response);
                        if (utente != null) {
                            request.setAttribute("utente", utente);
                        }
                        action_logged(request, response);
                    }
                } catch (TemplateManagerException | DataException | IOException ex) {
                    handleError(ex, request, response);
                }
            }
        } catch (TemplateManagerException | DataException | ServletException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        List<Disco> dischi = ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi(Utility.getUtente(request, response));
        request.setAttribute("dischi", Objects.requireNonNullElse(dischi, ""));

        result.activate("collezioni/create.ftl", request, response);
    }

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    private void saveCollezione(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        String titolo = request.getParameter("titolo");
        String privacy = String.valueOf(request.getParameter("privacy"));
        Utente utente = Utility.getUtente(request, response);
        List<String> utenti_usernames = List.of(request.getParameterValues("utenti[]"));
        List<Utente> utenti = new ArrayList<>();
        List<Disco> dischi = new ArrayList<>();
        CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");

        List<Collezione> collezioni = dataLayer.getCollezioneDAO().getCollezioni(Utility.getUtente(request, response));
        boolean exit = false;
        for (Collezione c : collezioni) {
            if (c.getTitolo().equalsIgnoreCase(titolo)) {
                request.setAttribute("error", "Hai gi?? una collezione con questo titolo!");
                action_logged(request, response);
                exit = true;
                break;
            }

        }
        if (!exit) {
            try {
                for (String disco : request.getParameterValues("disco")) {
                    dischi.add(dataLayer.getDiscoDAO().getDisco(Integer.parseInt(disco)));
                }

                if (privacy.equals("CONDIVISO")) {
                    for (String username : utenti_usernames) {
                        Utente user = dataLayer.getUtenteDAO().getUtente(username);
                        utenti.add(user);
                    }
                }

                LocalDate dataCreazione = LocalDate.now();

                //creo una collezione passandogli tutti i parametri e faccio la store della collezione
                Collezione collezione = new CollezioneImpl(titolo, privacy, utente, dataCreazione, dischi, utenti);
                dataLayer.getCollezioneDAO().storeCollezione(collezione);

                //per ogni disco selezionato per la collezione aggiungo il disco alla collezione in questione
                for (Disco disco : dischi) {
                    dataLayer.getDiscoDAO().addDisco(collezione, disco);
                }

                response.sendRedirect("/show-collezione?id=" + collezione.getKey());
            } catch (DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

}
