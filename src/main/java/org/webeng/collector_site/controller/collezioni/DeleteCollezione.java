package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;

import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class DeleteCollezione extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
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

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException, IOException {
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        Collezione collezione = dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id")));
        List<Disco> dischi = dataLayer.getDiscoDAO().getDischi(collezione);
        List<Utente> utenti = dataLayer.getUtenteDAO().getUtentiCondivisi(collezione);
        Utente user = Utility.getUtente(request, response);

        /* cancellazione di tutte tutte le associazioni
        tra un disco nella collezione e la collezione in questione
        nella tabella collezione_disco richiamando il metodo deleteDisco */
        if (user.equals(collezione.getUtente())) {
            for (Disco disco : dischi) {
                dataLayer.getDiscoDAO().deleteDisco(collezione, disco);
            }

            /* se la collezione è condivisa cancello prima tutte le associazioni
            tra un utente con cui la collezione era condivisa e la collezione in questione
            nella tabella collezione_condivisa_con richiamando il metodo deleteUtenteCondiviso */

            if (collezione.getPrivacy().equals("CONDIVISO")) {
                for (Utente utente : utenti)
                    dataLayer.getUtenteDAO().deleteUtenteCondiviso(collezione, utente);
            }
            //cancellazione della collezione
            dataLayer.getCollezioneDAO().deleteCollezione(collezione);

            response.sendRedirect("/profilo");
        } else {
            response.sendRedirect("/home");
        }
    }

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute(REFERRER, completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
