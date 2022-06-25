package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IndexDisco extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Ottengo l'utente loggato
            Utente utente = Utility.getUtente(request, response);
            if (utente != null) {
                request.setAttribute("utente", utente);
            }

            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            action_default(request, response);
        } catch (TemplateManagerException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            List<Disco> dischi;

            if (request.getParameter("keyword") != null && !request.getParameter("keyword").isBlank()) {
                dischi = dataLayer.getDiscoDAO().getDischiByKeyword(request.getParameter("keyword"));
                request.setAttribute("keyword", request.getParameter("keyword"));
            } else {
                dischi = dataLayer.getDiscoDAO().getDischi();
            }

            //lista dei dischi
            request.setAttribute("dischi", dischi);

            result.activate("/dischi/index.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
