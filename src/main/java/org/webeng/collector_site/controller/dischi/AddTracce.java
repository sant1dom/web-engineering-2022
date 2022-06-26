package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.framework.data.DataException;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddTracce extends CollectorsBaseController {
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
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        try {
            if (request.getParameterValues("tracce[]") != null) {
                for (String t : request.getParameterValues("tracce[]")) {
                    tracce.add(dataLayer.getTracciaDAO().getTraccia(Integer.parseInt(t)));
                }
            }
            Disco disco = dataLayer.getDiscoDAO().getDisco(Integer.parseInt(request.getParameter("id")));
            dataLayer.getDiscoDAO().addDiscoTraccia(disco, tracce);

            response.sendRedirect("/show-disco?id=" + disco.getKey());
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }
}
