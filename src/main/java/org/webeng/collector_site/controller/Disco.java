package org.webeng.collector_site.controller;

import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Disco extends CollectorsBaseController{
    public static final String REFERRER = "referrer";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (SecurityHelpers.checkSession(request) == null) {
                response.sendRedirect("/");
            }
            if ( request.getParameter("titolo") != null && request.getParameter("anno") != null  && request.getParameter("formato") != null
                    && request.getParameter("data_inserimento") != null && request.getParameter("stato_conservazione") != null
                    && request.getParameter("genere") != null && request.getParameter("formato") != null  )  {
                action_creaDisco(request, response);
            } else {
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        result.activate("creaDisco.ftl", request, response);
    }

    private void action_creaDisco(HttpServletRequest request, HttpServletResponse response) {
    }
}
