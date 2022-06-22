package org.webeng.collector_site.controller.search;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Servlet per la gestione dei risultati di ricerca.
 *
 * @author Davide De Acetis
 */
public class Search extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    /**
     * Gestisce il caso base in cui viene inserita una keyword da ricercare senza selezionare tra i suggerimenti
     *
     * @param request  servlet request
     * @param response servlet response
     */
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

            List<Utente> utenti = Utility.getUtenti(request, response);
            List<Collezione> collezioni = Utility.getCollezioni(request, response);
            List<Disco> dischi = Utility.getDischi(request, response);
            List<Traccia> tracce = Utility.getTracce(request, response);
            List<Autore> autori = Utility.getAutori(request, response);

            request.setAttribute("keyword", request.getParameter("keyword"));
            request.setAttribute("utenti", utenti);
            request.setAttribute("collezioni", collezioni);
            request.setAttribute("dischi", dischi);
            request.setAttribute("tracce", tracce);
            request.setAttribute("autori", autori);

            result.activate("search/search.ftl", request, response);
        }catch (DataException | TemplateManagerException ex) {
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
            //Ottengo l'utente loggato
            Utente utente = Utility.getUtente(request, response);
            if (utente != null) {
                request.setAttribute("utente", utente);
            }

            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            action_default(request, response);
        } catch (DataException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Servlet per la gestione dei risultati di ricerca";
    }
}
