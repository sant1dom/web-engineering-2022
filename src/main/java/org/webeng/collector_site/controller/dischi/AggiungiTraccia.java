package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AggiungiTraccia extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            aggiungiTracce(request, response);
        } else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_anonymous(request, response);
                }
            } catch (IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void aggiungiTracce(HttpServletRequest request, HttpServletResponse response) {
        List<Traccia> tracce = new ArrayList<>();
        try{
        if(request.getParameterValues("tracce")!=null){
            for(String t: request.getParameterValues("tracce")){
                tracce.add(((CollectorsDataLayer) request.getAttribute("datalayer")).getTracciaDAO().getTraccia(Integer.valueOf(t)));
            }
        }
        Disco disco= ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.valueOf(request.getParameter("id_disco")));
        ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().addDiscoTraccia(disco,tracce);
        }catch(DataException e){}
        try {
            response.sendRedirect("/show-disco?id_disco="+request.getParameter("id_disco"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
