package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DeleteDisco extends CollectorsBaseController {

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
                //Ottengo l'utente loggato
                Utente utente = Utility.getUtente(request, response);
                if (utente != null) {
                    request.setAttribute("utente", utente);
                }
                action_deleteDisco(request, response);
            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_deleteDisco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

        Disco disco = dataLayer.getDiscoDAO().getDisco(Integer.parseInt(request.getParameter("id")));
        List<Traccia> tracce = dataLayer.getTracciaDAO().getTracce(disco);
        List<Autore> autori = dataLayer.getAutoreDAO().getAutori(disco);
        List<Image> images = dataLayer.getImageDAO().getImages(disco);

        for (Traccia traccia : tracce) {
            dataLayer.getDiscoDAO().deleteDiscoTraccia(disco, traccia);
        }
        for (Autore autore : autori) {
            dataLayer.getDiscoDAO().deleteDiscoAutore(disco, autore);
        }
        for (Image image : images) {
            Path imagePath = Paths.get(getServletContext().getInitParameter("uploads.directory") + "\\" + image.getFileName());
            try {
                Files.delete(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dataLayer.getImageDAO().deleteImage(disco, image);
        }
        
        dataLayer.getDiscoDAO().deleteDisco(disco);
        Utente utente = Utility.getUtente(request, response);
        if (utente != null) {
            response.sendRedirect("/profilo?id=" + utente.getKey());
        } else {
            response.sendRedirect("/index-disco");
        }
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }
}
