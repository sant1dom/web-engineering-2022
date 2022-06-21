package org.webeng.collector_site.controller.search;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResultDispatcher extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        result.activate("search/search.ftl", request, response);
    }

    private void action_utente(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            Utente utente = dataLayer.getUtenteDAO().getUtente(request.getParameter("id"));

            request.setAttribute("utente", utente);

            result.activate("utenti/show.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_autori(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/show-autore");
            dispatcher.forward(request,response);

        } catch (IOException | ServletException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_disco(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            Disco disco = dataLayer.getDiscoDAO().getDisco(request.getParameter("id"));

            request.setAttribute("disco", disco);

            result.activate("dischi/show.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_traccia(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            Traccia traccia = dataLayer.getTracciaDAO().getTraccia(request.getParameter("id"));

            request.setAttribute("traccia", traccia);

            result.activate("tracce/show.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
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
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            String type = request.getParameter("item_type");

            if (type != null) {
                switch (type) {
                    case ("UTENTI") -> action_utente(request, response);
                    case ("AUTORI") -> action_autori(request, response);
                    case ("DISCHI") -> action_disco(request, response);
                    case ("TRACCE") -> action_traccia(request, response);
                    default -> action_default(request, response);
                }
            }
        } catch (TemplateManagerException | DataException ex) {
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
