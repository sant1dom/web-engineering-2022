package org.webeng.collector_site.controller.auth;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.dao.UtenteDAO;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet che gestisce la registrazione di un nuovo utente.
 *
 * @author Davide De Acetis
 */
public class Register extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        result.activate("auth/register.ftl", request, response);
    }

    private void action_register(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            //se mancano dei campi obbligatori, ritorna alla pagina di registrazione
            request.setAttribute("error", "Inserire tutti i campi obbligatori");
            action_default(request, response);
        } else {
            try {
                CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
                UtenteDAO utenteDAO = dataLayer.getUtenteDAO();
                Utente utente = utenteDAO.createUtente();

                if (utente != null) {
                    //se la validazione ha successo carichiamo l'utente sul db

                    if (nome != null && !(nome.equals(""))) {
                        utente.setNome(nome);
                    } else {
                        utente.setNome(null);
                    }
                    if (cognome != null && !(cognome.equals(""))) {
                        utente.setCognome(cognome);
                    } else {
                        utente.setCognome(null);
                    }
                    utente.setUsername(username);
                    utente.setEmail(email);
                    utente.setPassword(SecurityHelpers.encryptPassword(password));

                    utenteDAO.storeUtente(utente);

                    int userid = utente.getKey();
                    SecurityHelpers.createSession(request, username, userid);
                    //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                    if (request.getParameter(REFERRER) != null) {
                        response.sendRedirect(request.getParameter(REFERRER));
                    } else {
                        response.sendRedirect("/");
                    }
                } else {
                    handleError("Login fallito", request, response);
                }
            } catch (DataException ex) {
                handleError(ex, request, response);
            }
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            //se l'utente è già autenticato torniamo alla home
            if (SecurityHelpers.checkSession(request) != null) {
                response.sendRedirect("/");
            }
            //se i campi username, email e password sono presenti effettuiamo la registrazione
            if (!request.getParameter("username").isBlank() && !request.getParameter("email").isBlank() && !request.getParameter("password").isBlank()) {
                action_register(request, response);
            } else {
                //altrimenti carichiamo la pagina di registrazione
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
