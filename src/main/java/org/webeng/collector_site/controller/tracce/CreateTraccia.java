package org.webeng.collector_site.controller.tracce;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.TracciaImpl;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateTraccia extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals("POST")) {
            saveTraccia(request, response);
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
        List<Autore> autori = ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutori();
        List<Traccia> tracce = ((CollectorsDataLayer) request.getAttribute("datalayer")).getTracciaDAO().getTraccePadri();
        request.setAttribute("authors", Objects.requireNonNullElse(autori, ""));
        request.setAttribute("tracce", Objects.requireNonNullElse(tracce, ""));
        result.activate("tracce/create.ftl", request, response);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void saveTraccia(HttpServletRequest request, HttpServletResponse response) {
        try {
            CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");
            String titolo = request.getParameter("titolo");
            int durata = Integer.parseInt(request.getParameter("durata"));
            String iswc = request.getParameter("iswc");
            List<Autore> autori = new ArrayList<>();
            Traccia padre = null;


            if (!(request.getParameter("padre").equals("")) && !request.getParameter("padre").isBlank()) {
                padre = dataLayer.getTracciaDAO().getTraccia(Integer.parseInt(request.getParameter("padre")));
            }

            if (request.getParameterValues("autore") != null && request.getParameterValues("autore").length > 0) {
                for (String autore : request.getParameterValues("autore")) {
                    autori.add(dataLayer.getAutoreDAO().getAutore(Integer.parseInt(autore)));
                }
            } else {
                autori= dataLayer.getAutoreDAO().getAutori(padre);

            }


            dataLayer.getTracciaDAO().storeTraccia(new TracciaImpl(titolo, durata, iswc, autori, padre));
            response.sendRedirect("/index-traccia");
        } catch (Exception e) {
            handleError(e, request, response);
        }
    }
}
