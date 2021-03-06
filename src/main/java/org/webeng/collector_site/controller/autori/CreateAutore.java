package org.webeng.collector_site.controller.autori;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.AutoreImpl;
import org.webeng.collector_site.data.model.TipologiaAutore;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

public class CreateAutore extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals("POST")) {
            saveAutore(request, response);
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
            } catch (TemplateManagerException | DataException | IOException | ServletException ex) {
                handleError(ex, request, response);
            }
        }
    }


    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("tipologie", TipologiaAutore.values());
        result.activate("autori/create.ftl", request, response);
    }

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    private void saveAutore(HttpServletRequest request, HttpServletResponse response) {
        try {
            ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().storeAutore(
                    new AutoreImpl(
                            request.getParameter("nome"),
                            request.getParameter("cognome"),
                            request.getParameter("nome_artistico"),
                            TipologiaAutore.valueOf(request.getParameter("tipologia_autore"))
                    )
            );
            response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "index-autore"));
        } catch (Exception e) {
            e.printStackTrace();
            handleError(e, request, response);
        }
    }
}
