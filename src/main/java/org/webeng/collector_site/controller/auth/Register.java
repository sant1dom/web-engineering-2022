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
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
            //se mancano dei campi
            //torniamo a pagina di registrazione
            request.setAttribute("error", "Inserire tutti i campi obbligatori");
            action_default(request, response);
        } else {
            Utente utente = null;
            UtenteDAO utenteDAO = null;
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            try {
                utenteDAO = dataLayer.getUtenteDAO();
                utente = utenteDAO.createUtente();
            } catch (DataException ex) {
                handleError(ex, request, response);
            }

            if (utente != null) {
                //se la validazione ha successo
                //carichiamo l'utente sul db
                try {
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
                    //if an origin URL has been transmitted, return to it
                    if (request.getParameter(REFERRER) != null) {
                        response.sendRedirect(request.getParameter(REFERRER));
                    } else {
                        response.sendRedirect("/");
                    }
                } catch (DataException ex) {
                    handleError(ex, request, response);
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
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (SecurityHelpers.checkSession(request) != null) {
                response.sendRedirect("/");
            }
            if (request.getParameter("username") != null && request.getParameter("email") != null && request.getParameter("password") != null) {
                action_register(request, response);
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
