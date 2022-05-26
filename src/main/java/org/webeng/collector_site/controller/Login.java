/*
 * Login.java
 *
 * Questo esempio mostra come utilizzare le sessioni per autenticare un utente
 * 
 * This example shows how to use sessions to authenticate the user
 *
 */
package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        result.activate("login.ftl", request, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("u");
        String password = SecurityHelpers.encryptPassword(request.getParameter("p"));
        //... VALIDAZIONE IDENTITA'...
        //... IDENTITY CHECKS ...
        Utente utente = null;
        try {
            utente = ((CollectorsDataLayer) request.getAttribute("datalayer")).getUtenteDAO().doLogin(username, password);
        } catch (DataException ex) {
            handleError(ex, request, response);
        }

        if (utente != null) {
            //se la validazione ha successo
            //if the identity validation succeeds
            //carichiamo lo userid dal database utenti
            //load userid from user database

            int userid = utente.getKey();
            SecurityHelpers.createSession(request, username, userid);
            //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
            //if an origin URL has been transmitted, return to it
            if (request.getParameter(REFERRER) != null) {
                response.sendRedirect(request.getParameter(REFERRER));
            } else {
                response.sendRedirect("home");
            }
        } else {
            handleError("Login failed", request, response);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else {
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }   
}
