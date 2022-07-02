package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.framework.data.DataException;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddAutori extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            aggiungiAutori(request, response);
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

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    private void aggiungiAutori(HttpServletRequest request, HttpServletResponse response) {
        List<Autore> autore = new ArrayList<>();
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        try {
            if (request.getParameterValues("autori[]") != null) {
                for (String a : request.getParameterValues("autori[]")) {
                    autore.add(dataLayer.getAutoreDAO().getAutore(Integer.parseInt(a)));
                }
            }
            Disco disco = dataLayer.getDiscoDAO().getDisco(Integer.parseInt(request.getParameter("id")));
            dataLayer.getDiscoDAO().addDiscoAutore(disco, autore);

            response.sendRedirect("/show-disco?id=" + request.getParameter("id"));
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }
}
