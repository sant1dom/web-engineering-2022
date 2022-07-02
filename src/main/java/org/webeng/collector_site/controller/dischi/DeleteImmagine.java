package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Image;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteImmagine extends CollectorsBaseController {
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
                action_deleteImmagine(request, response);
            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_deleteImmagine(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

        Disco disco = dataLayer.getDiscoDAO().getDisco(Integer.parseInt(request.getParameter("d")));
        Image image = dataLayer.getImageDAO().getImage(Integer.parseInt(request.getParameter("i")));
        Path imagePath = Paths.get(getServletContext().getInitParameter("uploads.directory") + "\\" + image.getFileName());
        try {
            Files.delete(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataLayer.getImageDAO().deleteImage(disco, image);
        response.sendRedirect("/show-disco?id=" + disco.getKey());
    }


 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}