package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddDischi extends CollectorsBaseController {

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
                if (request.getMethod().equals("POST")) {
                    action_logged(request, response);
                }
            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws DataException {
        try {
            CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");
            Collezione collezione = dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id")));
            List<Disco> dischi = new ArrayList<>();

            if (request.getParameterValues("dischi[]") != null) {
                for (String disco : request.getParameterValues("dischi[]")) {
                    dischi.add(dataLayer.getDiscoDAO().getDisco(Integer.parseInt(disco)));
                }

                //aggiunta di ogni disco selezionato nella collezione in questione
                for (Disco disco : dischi) {
                    dataLayer.getCollezioneDAO().addDiscoCollezione(collezione, disco);
                }

                response.sendRedirect("/show-collezione?id=" + request.getParameter("id"));
            } else {
                throw new DataException("Nessun disco selezionato");
            }
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
