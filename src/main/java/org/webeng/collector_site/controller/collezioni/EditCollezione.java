package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditCollezione extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        if (request.getMethod().equals("POST")) {
            try {
                //Ottengo l'utente loggato
                Utente utente = Utility.getUtente(request, response);
                if (utente != null) {
                    request.setAttribute("utente", utente);
                }
                updateCollezione(request, response);
            } catch (DataException e) {
                throw new RuntimeException(e);
            }
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
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

        Collezione collezione = dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id")));
        Utente proprietario = collezione.getUtente();
        List<Disco> dischi = collezione.getDischi();
        List<Utente> utenti_condivisi = collezione.getUtentiCondivisi();
        List<Disco> dischiAdd = new ArrayList<>(dataLayer.getDiscoDAO().getDischi(Utility.getUtente(request, response)));

        dischiAdd.removeAll(dischi);

        //Ogni collezione ha una lista di dischi e il suo proprietario.
        request.setAttribute("collezione", collezione);
        request.setAttribute("dischi", dischi);
        request.setAttribute("proprietario", proprietario);
        request.setAttribute("utenti_condivisi", utenti_condivisi);
        request.setAttribute("dischiAdd", dischiAdd);

        result.activate("/collezioni/show.ftl", request, response);

    }

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    private void updateCollezione(HttpServletRequest request, HttpServletResponse response) {
        try {
            CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");
            Collezione collezione = dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id")));
            if (collezione.getPrivacy().equals("CONDIVISO") && !String.valueOf(request.getParameter("privacy")).equals("CONDIVISO")) {
                List<Utente> utenti_condivisi = dataLayer.getUtenteDAO().getUtentiCondivisi(collezione);
                if (utenti_condivisi.size() > 0) {
                    for (Utente utente_condiviso : utenti_condivisi) {
                        dataLayer.getUtenteDAO().deleteUtenteCondiviso(collezione, utente_condiviso);
                    }
                }
            }

            if (request.getParameterValues("user_share") != null) {

                String username = request.getParameter("user_share");
                List<Utente> utenti = new ArrayList<>();
                utenti.add(dataLayer.getUtenteDAO().getUtente(username));
                if(collezione.getUtentiCondivisi().containsAll(utenti))
                    request.setAttribute("errorUser", "Utente già inserito!");
                else {
                     collezione.setUtentiCondivisi(utenti);
                     dataLayer.getCollezioneDAO().addUtentiCondivisi(collezione);
                }
            } else {
                boolean error = false;
                String titolo = request.getParameter("titolo");
                String privacy = String.valueOf(request.getParameter("privacy"));
                List<Collezione> collezioni = dataLayer.getCollezioneDAO().getCollezioni(Utility.getUtente(request, response));


                if (!titolo.equalsIgnoreCase(collezione.getTitolo())) {
                    for (Collezione c : collezioni) {
                        if (c.getTitolo().equalsIgnoreCase(titolo) && !c.getKey().equals(collezione.getKey())) {
                            request.setAttribute("errorTitolo", "Hai già una collezione con questo titolo!");
                            error = true;
                            break;
                        }
                    }
                }
                if (!error) {
                     /*chiamata dei metodi setTitolo,setPrivacy
                     e setUtentiCondivisi sui valori di titolo
                     e privacy inseriti dall'utente */
                    collezione.setTitolo(titolo);
                    collezione.setPrivacy(privacy);
                    //chiamata metodo storeCollezione per aggiornare la collezione
                    dataLayer.getCollezioneDAO().storeCollezione(collezione);
                }
            }
            action_logged(request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}