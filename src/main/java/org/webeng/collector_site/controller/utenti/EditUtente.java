package org.webeng.collector_site.controller.utenti;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUtente extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getMethod().equals("POST")) {
                updateUtente(request, response);
            } else {

                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_anonymous(request, response);
                } else {
                    action_logged(request, response);
                }
            }
        } catch (TemplateManagerException | DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException, IOException {
        TemplateResult result = new TemplateResult(getServletContext());

        Utente utente = Utility.getUtente(request, response);
        if (utente != null &&
                request.getParameter("id") != null &&
                !request.getParameter("id").isBlank() &&
                Integer.parseInt(request.getParameter("id")) == utente.getKey()) {
            request.setAttribute("utente", utente);
            result.activate("utenti/edit.ftl", request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void updateUtente(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException, IOException {
        try {
            String username = request.getParameter("username");
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String vecchia_password = request.getParameter("vecchia_password");

            if (username.isBlank() || email.isBlank() || password.isBlank() || vecchia_password.isBlank()) {
                request.setAttribute("error", "Compilare tutti i campi obbligatori");
                action_logged(request, response);
            } else {
                Utente utente = Utility.getUtente(request, response);
                if (utente != null) {
                    vecchia_password = SecurityHelpers.encryptPassword(vecchia_password);
                    if (vecchia_password != null && vecchia_password.equals(utente.getPassword())) {
                        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

                        utente.setUsername(username);
                        utente.setNome(nome);
                        utente.setCognome(cognome);
                        utente.setEmail(email);
                        utente.setPassword(SecurityHelpers.encryptPassword(password));
                        dataLayer.getUtenteDAO().storeUtente(utente);

                        response.sendRedirect("/profilo?id=" + utente.getKey());
                    } else {
                        request.setAttribute("error", "Password errata");
                        action_logged(request, response);
                    }
                } else {
                    throw new DataException("Utente non trovato");
                }
            }
        } catch (DataException | IOException | TemplateManagerException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                request.setAttribute("error", "Username già in uso");
                action_logged(request, response);
            } else {
                handleError(e, request, response);
            }
        }
    }
}
