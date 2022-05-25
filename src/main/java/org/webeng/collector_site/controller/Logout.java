/*
 * Logout.java
 *
 * Questo esempio mostra come utilizzare le sessioni per autenticare un utente
 * 
 * This example shows how to use sessions to authenticate the user
 *
 */
package org.webeng.collector_site.controller;

import org.webeng.framework.security.SecurityHelpers;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class Logout extends CollectorsBaseController {

    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityHelpers.disposeSession(request);
        //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
        //if an origin URL has been transmitted, return to it
        if (request.getParameter("referrer") != null) {
            response.sendRedirect(request.getParameter("referrer"));
        } else {
            response.sendRedirect("issues");
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            action_logout(request, response);
        } catch (IOException ex) {
            handleError(ex, request, response);
        }
    }    
}
