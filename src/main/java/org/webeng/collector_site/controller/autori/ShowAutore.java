package org.webeng.collector_site.controller.autori;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAutore extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

            Autore autore = dataLayer.getAutoreDAO().getAutore(Integer.parseInt(request.getParameter("item_id")));
            List<Disco> dischi = dataLayer.getDiscoDAO().getDischi(autore);
            List<Traccia> tracce = dataLayer.getTracciaDAO().getTracce(autore);

            request.setAttribute("autore", autore);
            request.setAttribute("dischi", dischi);
            request.setAttribute("tracce", tracce);

            result.activate("/autori/show.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            Utente utente = Utility.getUtente(request, response);

            if (utente != null) {
                TemplateResult result = new TemplateResult(getServletContext());
                request.setAttribute(REFERRER, request.getParameter(REFERRER));
                request.setAttribute("utente", utente);
                result.activate("/autori/show.ftl", request, response);
            } else {
                TemplateResult result = new TemplateResult(getServletContext());
                request.setAttribute(REFERRER, request.getParameter(REFERRER));
                result.activate("/autori/show.ftl", request, response);
            }
        } catch (TemplateManagerException | DataException ex) {
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
            HttpSession s = SecurityHelpers.checkSession(request);
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            if (s == null) {
                action_anonymous(request, response);
            } else {
                action_logged(request, response);
            }
        } catch (TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
