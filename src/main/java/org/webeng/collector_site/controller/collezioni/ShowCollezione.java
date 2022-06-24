package org.webeng.collector_site.controller.collezioni;

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
import java.util.List;

/**
 * Servlet per la visualizzazione del singolo autore.
 * @author Davide De Acetis
 */
public class ShowCollezione extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

            Collezione collezione = dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id")));
            Utente proprietario = collezione.getUtente();
            List<Disco> dischi = collezione.getDischi();
            List<Utente>utenti_condivisi=collezione.getUtentiCondivisi();

            //Ogni collezione ha una lista di dischi e il suo proprietario.
            request.setAttribute("collezione", collezione);
            request.setAttribute("dischi", dischi);
            request.setAttribute("proprietario", proprietario);
            request.setAttribute("utenti_condivisi", utenti_condivisi);

            result.activate("/collezioni/show.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
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
        } catch (TemplateManagerException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet per la visualizzazione del singolo autore.";
    }
}
