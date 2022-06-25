package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
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
                action_delateDisco(request, response);
            }
        } catch ( DataException | IOException ex) {
            handleError(ex, request, response);
        }

    }

    private void action_delateDisco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {
        Disco disco=((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.valueOf(request.getParameter("id_disco")));
        List<Traccia> tracce= ((CollectorsDataLayer) request.getAttribute("datalayer")).getTracciaDAO().getTracce(disco);
        List<Autore> autori= ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutori(disco);
        List<Image> images= ((CollectorsDataLayer) request.getAttribute("datalayer")).getImageDAO().getImages(disco);
        for(Traccia traccia:tracce){
            ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().delateDiscoTraccia(disco,traccia);
        }
        for(Autore autore:autori){
            ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().deleteDiscoAutore(disco,autore);
        }
        for(Image image:images) {
            Path imagePath= Paths.get(getServletContext().getInitParameter("uploads.directory") +"\\"+ image.getFileName());
            try{
                Files.delete(imagePath);
            }catch(Exception e) {
                e.printStackTrace();
            }
            ((CollectorsDataLayer) request.getAttribute("datalayer")).getImageDAO().deleteImage(disco,image);
        }
        ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().deleteDisco(disco);
        response.sendRedirect("/lista-dischi");
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }
}
