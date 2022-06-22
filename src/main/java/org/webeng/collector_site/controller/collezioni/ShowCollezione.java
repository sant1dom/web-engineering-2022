package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ShowCollezione extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

            Collezione collezione = dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id_collezione")));
            List <Disco> dischi=((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi(collezione);
            request.setAttribute("collezione", collezione);
            request.setAttribute("dischi", Objects.requireNonNullElse(dischi, ""));
            result.activate("collezioni/show_collezione.ftl", request, response);

        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }



    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
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

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        Collezione  collezione = ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id_collezione")));
        List <Disco> dischi=((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi(collezione);
        request.setAttribute("collezione", collezione);
        request.setAttribute("dischi", Objects.requireNonNullElse(dischi, ""));
        result.activate("collezioni/show_collezione.ftl", request, response);
    }
    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }


}


