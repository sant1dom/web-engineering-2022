package org.webeng.collector_site.controller;

import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registrazione extends CollectorsBaseController{
    public static final String REFERRER = "referrer";



    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        result.activate("registrazione.ftl", request, response);
    }
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
            try {

                    String https_redirect_url = SecurityHelpers.checkHttps(request);
                    request.setAttribute("https-redirect", https_redirect_url);
                    action_default(request, response);

            } catch (TemplateManagerException ex) {
                handleError(ex, request, response);
            }

        }
}
