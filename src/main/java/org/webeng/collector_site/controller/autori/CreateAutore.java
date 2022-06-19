package org.webeng.collector_site.controller.autori;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.AutoreImpl;
import org.webeng.collector_site.data.model.TipologiaAutore;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

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
                    action_logged(request, response);
                }
            } catch (TemplateManagerException | DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }


    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("utente", Utility.getUtente(request, response));
        request.setAttribute("tipologie", TipologiaAutore.values());
        result.activate("autori/create_autore.ftl", request, response);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
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
            response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "/"));
        } catch (Exception e) {
            e.printStackTrace();
            handleError(e, request, response);
        }
    }
}
