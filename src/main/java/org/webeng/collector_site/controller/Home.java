package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Image;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet per la visualizzazione della home page.
 * @author Davide De Acetis
 */
public class Home extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");

            List<Traccia> tracce = dataLayer.getTracciaDAO().getTraccePopolari();
            List<Disco> dischi = dataLayer.getDiscoDAO().getDischiPopolari();

            for(Disco disco: dischi){
                List<Image> immagini= dataLayer.getImageDAO().getImages(disco);
                disco.setImmagini(immagini);
            }
            List<Integer> utenti_id= dataLayer.getCollezioneDAO().getUtentiAttivi();
            List<Utente> utenti= new ArrayList<>();

            for(Integer id: utenti_id){
                Utente utente = dataLayer.getUtenteDAO().getUtente(id);
                utenti.add(utente);
            }
            for(Utente utente: utenti){
                utente.setCollezioni(dataLayer.getCollezioneDAO().getCollezioniPubbliche(utente));
            }
            request.setAttribute("utenti", utenti);
            request.setAttribute("tracce", tracce);
            request.setAttribute("dischi", dischi);

            result.activate("index.ftl", request, response);
        } catch (TemplateManagerException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);

            //Ottengo l'utente loggato
            Utente utente = Utility.getUtente(request, response);
            if (utente != null) {
                request.setAttribute("utente", utente);
            }
            action_default(request, response);
        } catch (DataException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet per la visualizzazione della home page.";
    }
}
