package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.CollezioneImpl;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Genere;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateCollezione extends CollectorsBaseController {
    public static final String REFERRER = "referrer";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if(request.getMethod().equals("POST")){
            saveCollezione(request,response);
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

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        List<Disco> dischi = ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi();
        request.setAttribute("utente", Utility.getUtente(request, response));
        request.setAttribute("dischi", Objects.requireNonNullElse(dischi, ""));

        result.activate("collezioni/create_collezione.ftl", request, response);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void saveCollezione(HttpServletRequest request, HttpServletResponse response) {
        try {
            String titolo = request.getParameter("titolo");
            String privacy = String.valueOf(request.getParameter("privacy"));
            Utente utente= Utility.getUtente(request, response);
            List<Disco> dischi = new ArrayList<>();

            for (String disco : request.getParameterValues("disco")) {
                dischi.add(((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.parseInt(disco)));
            }

            LocalDate dataCreazione= LocalDate.now();
            Collezione collezione=new CollezioneImpl(titolo,privacy,utente,dataCreazione,dischi,null);
            ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(collezione);

            for (Disco disco : dischi) {
                ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().addDisco(collezione, disco);
            }

            response.sendRedirect("/home");
        } catch (Exception e) {
            handleError(e, request, response);
        }
    }



}
