package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.dao.DiscoDAO;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CreateDisco extends CollectorsBaseController{
    public static final String REFERRER = "referrer";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
       if(request.getMethod().equals("POST")){
           saveDisco(request,response);
       }else {
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
    }


    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());

        List<Collezione> collezioni=((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioni();
        List<Autore> autori = ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutori();
        List<Disco> dischiPadri=((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischiPadri();

        List<Genere> generi = Arrays.asList(Genere.values());
        List<Formato> formati =Arrays.asList(Formato.values());
        List<StatoConservazione> statiConservazione = Arrays.asList(StatoConservazione.values());

        request.setAttribute("collezioni", Objects.requireNonNullElse(collezioni, ""));
        request.setAttribute("dischiPadri", Objects.requireNonNullElse(dischiPadri, ""));
        request.setAttribute("authors", Objects.requireNonNullElse(autori, ""));
        request.setAttribute("generi", Objects.requireNonNullElse(generi, ""));
        request.setAttribute("formati", Objects.requireNonNullElse(formati, ""));
        request.setAttribute("statiConservazione", Objects.requireNonNullElse(statiConservazione, ""));
        result.activate("creaDisco.ftl", request,response );
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void saveDisco(HttpServletRequest request, HttpServletResponse response) {
    }

}
