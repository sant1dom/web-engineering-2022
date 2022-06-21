package org.webeng.collector_site.controller.search;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ResultDispatcher extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        result.activate("search/search.ftl", request, response);
    }

    private void action_autori(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            Autore autore =  dataLayer.getAutoreDAO().getAutore(request.getParameter("id"));
            List<Disco> dischi = dataLayer.getDiscoDAO().getDischi(autore);
            List<Traccia> tracce = dataLayer.getTracciaDAO().getTracce(autore);
            request.setAttribute("autore", autore);
            request.setAttribute("dischi", dischi);
            request.setAttribute("tracce", tracce);

            result.activate("autore/show.ftl", request, response);

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

            if (request.getParameter("item_type") != null) {
                String item_type = request.getParameter("item_type");
                switch (item_type) {
                    case "AUTORI" -> action_autori(request, response);
                    case "COLLEZIONI" -> action_default(request, response);
                    case "DISCHI" -> action_default(request, response);
                    case "UTENTI" -> action_default(request, response);
                    default -> action_default(request, response);
                }
                action_default(request, response);
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
