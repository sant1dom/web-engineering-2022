package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;

import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class DeleteCollezione extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            HttpSession s = SecurityHelpers.checkSession(request);
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            if (s == null) {
                action_anonymous(request, response);
            } else {
                action_logged(request, response);
            }
        } catch (TemplateManagerException | DataException | IOException ex) {
            handleError(ex, request, response);
        }

    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException, IOException {
        Collezione collezione = ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id_collezione")));
        List<Disco> dischi = ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi(collezione);
        for(Disco disco:dischi){
            ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().deleteDisco(collezione,disco);
        }
        ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().deleteCollezione(collezione);
        response.sendRedirect("/lista-collezioni");
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }
}