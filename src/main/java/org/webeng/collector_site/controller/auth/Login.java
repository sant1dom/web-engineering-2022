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

/**
 * Servlet per l'autenticazione dell'utente.
 *
 * @author Davide De Acetis
 */
public class Login extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    /**
     * Caricamento della pagina di login
     */
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        result.activate("auth/login.ftl", request, response);
    }

    /**
     * Gestione della richiesta di login
     */
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.isBlank() || password.isBlank()) {
            //se mancano dei campi torniamo alla pagina di login
            request.setAttribute("error", "Inserire username e password");
            action_default(request, response);
        } else {
            try {
                //se i campi sono presenti controlliamo se l'utente esiste
                password = SecurityHelpers.encryptPassword(password);
                CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
                Utente utente = null;
                utente = dataLayer.getUtenteDAO().doLogin(username, password);
                if (utente != null) {
                    //se l'utente esiste lo autentichiamo
                    if (utente.getKey() != null) {
                        //carichiamo lo userid dal database utenti
                        int userid = utente.getKey();
                        SecurityHelpers.createSession(request, username, userid);
                        //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                        if (request.getParameter(REFERRER) != null) {
                            response.sendRedirect(request.getParameter(REFERRER));
                        } else {
                            response.sendRedirect("/");
                        }
                    } else {
                        //se la validazione non ha successo si torna alla pagina di login
                        request.setAttribute("error", "Username o password errati");
                        action_default(request, response);
                    }
                } else {
                    throw new DataException("Login fallito");
                }
            } catch (DataException ex) {
                if (ex.getMessage().contains("User not found")) {
                    request.setAttribute("error", "Username o password errati");
                    action_default(request, response);
                } else {
                    handleError(ex, request, response);
                }
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            //se l'utente è già autenticato torniamo alla home
            if (SecurityHelpers.checkSession(request) != null) {
                response.sendRedirect("/");
            }
            //se i campi username e password sono presenti effettuiamo il login
            if (request.getParameter("username") != null && request.getParameter("password") != null) {
                action_login(request, response);
            } else {
                //altrimenti carichiamo la pagina di login
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
        return "Servlet per la gestione della login di un utente";
    }
}
