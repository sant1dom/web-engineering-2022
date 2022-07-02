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
import java.util.ArrayList;
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
        //prima di cancellare il disco lo rimuovo da tutte le collezioni in cui si trova
        List<Collezione>collezioni= dataLayer.getCollezioneDAO().getCollezioni(disco);
        for(Collezione collezione:collezioni){
            dataLayer.getDiscoDAO().deleteDisco(collezione,disco);
        }

        List<Disco>dischiFigli=dataLayer.getDiscoDAO().getFigli(disco);

        //verifico se il disco da eliminare ha figli
        if(dischiFigli!=null && dischiFigli.size()>1){
            //prendo il primo figlio
            Disco d =dataLayer.getDiscoDAO().getFigli(disco).get(0);
            //setto il padre del primo figlio a null
            d.setPadre(null);
            //aggiorno il disco con il nuovo padre
            dataLayer.getDiscoDAO().updateDiscoPadre(d);
            //cancello il primo figlio dalla lista dei figli del disco padre
            dataLayer.getDiscoDAO().getFigli(disco).remove(0);
                //setto il nuovo padre ai figli del disco da eliminare
                for (Disco discoFiglio : dataLayer.getDiscoDAO().getFigli(disco)) {
                    discoFiglio.setPadre(d);
                    dataLayer.getDiscoDAO().updateDiscoPadre(discoFiglio);
                }
        }
        if(dischiFigli!=null && dischiFigli.size()==1){
            //prendo il primo figlio
            Disco d =dataLayer.getDiscoDAO().getFigli(disco).get(0);
            //setto il padre del primo figlio a null
            d.setPadre(null);
            //aggiorno il disco con il nuovo padre
            dataLayer.getDiscoDAO().updateDiscoPadre(d);
        }

        dataLayer.getDiscoDAO().deleteDisco(disco);
        Utente utente = Utility.getUtente(request, response);
        if (utente != null) {
            response.sendRedirect("/profilo?id=" + utente.getKey());
        } else {
            response.sendRedirect("/index-disco");
        }
    }

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
