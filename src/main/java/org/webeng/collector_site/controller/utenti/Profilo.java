package org.webeng.collector_site.controller.utenti;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet per il profilo di un utente
 * @author Davide De Acetis
 */
public class Profilo extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));

            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            Utente utente_generico;
            List<Collezione> collezioni = null;
            List<Disco> dischi = null;
            List<Collezione> collezioni_condivise;

            Utente utente = Utility.getUtente(request, response);

            if (request.getParameter("id") != null && !request.getParameter("id").isBlank()) {
                int id = Integer.parseInt(request.getParameter("id"));
                utente_generico = dataLayer.getUtenteDAO().getUtente(id);
                if (utente_generico != null) {
                    request.setAttribute("utente_generico", utente_generico);
                    collezioni = dataLayer.getCollezioneDAO().getCollezioniPubbliche(utente_generico);
                    dischi = dataLayer.getDiscoDAO().getDischi(utente_generico);
                    if (utente != null) {
                        request.setAttribute("utente", utente);
                    }
                } else {
                    response.sendRedirect("/");
                }
            } else if(utente != null) {
                request.setAttribute("utente", utente);
                request.setAttribute("utente_generico", utente);
                collezioni = dataLayer.getCollezioneDAO().getCollezioni(utente);
                dischi = dataLayer.getDiscoDAO().getDischi(utente);
                collezioni_condivise = dataLayer.getCollezioneDAO().getCollezioniCondivise(utente);
                request.setAttribute("collezioni_condivise", collezioni_condivise);
            }else{
                response.sendRedirect("/");
            }

            request.setAttribute("collezioni", collezioni);
            request.setAttribute("dischi", dischi);
            
            result.activate("utenti/profilo.ftl", request, response);
        } catch (TemplateManagerException | DataException | IOException ex) {
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

            action_default(request, response);
        } catch (TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }


    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
