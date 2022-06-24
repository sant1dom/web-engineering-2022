package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EliminaAutoreDisco extends CollectorsBaseController {

    public static final String REFERRER = "referrer";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession s = SecurityHelpers.checkSession(request);
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            if (s == null) {
                action_anonymous(request, response);
            } else {
                //Ottengo l'utente loggato
                Utente utente = Utility.getUtente(request, response);
                if (utente != null) {
                    request.setAttribute("utente", utente);
                }
                action_delete(request, response);
            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {
        Disco disco= ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.valueOf(request.getParameter("id_disco")));
        Autore autore= ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutore(Integer.valueOf(request.getParameter("id_autore")));
        ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().deleteDiscoAutore(disco,autore);
        response.sendRedirect("/show-disco?id_disco="+disco.getKey());

    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }
}
