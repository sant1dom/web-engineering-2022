/*
 * Login.java
 *
 * Questo esempio mostra come utilizzare le sessioni per autenticare un utente
 *
 * This example shows how to use sessions to authenticate the user
 *
 */
package org.webeng.collector_site.controller.auth;

import org.webeng.collector_site.controller.CollectorsBaseController;
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
        result.activate("auth/login.ftl", request, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.isBlank() || password.isBlank()) {
            //se mancano dei campi
            //torniamo a pagina di login
            request.setAttribute("error", "Inserire username e password");
            action_default(request, response);
        } else {
            password = SecurityHelpers.encryptPassword(password);
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

            Utente utente = null;
            try {
                utente = dataLayer.getUtenteDAO().doLogin(username, password);
            } catch (DataException ex) {
                handleError(ex, request, response);
            }

            if (utente != null) {
                if (utente.getKey() != null) {
                    //se la validazione ha successo
                    //carichiamo lo userid dal database utenti
                    int userid = utente.getKey();
                    SecurityHelpers.createSession(request, username, userid);
                    //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                    //if an origin URL has been transmitted, return to it
                    if (request.getParameter(REFERRER) != null) {
                        response.sendRedirect(request.getParameter(REFERRER));
                    } else {
                        response.sendRedirect("/");
                    }
                } else {
                    //se la validazione non ha successo
                    //torniamo a pagina di login
                    request.setAttribute("error", "Username o password errati");
                    action_default(request, response);
                }
            } else {
                handleError("Login fallito", request, response);
            }
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (SecurityHelpers.checkSession(request) != null) {
                response.sendRedirect("/");
            }
            if (request.getParameter("username") != null && request.getParameter("password") != null) {
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

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
