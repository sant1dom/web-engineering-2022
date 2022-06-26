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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndexCollezione extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

            List<Collezione> collezioni_utente = dataLayer.getCollezioneDAO().getCollezioni(Utility.getUtente(request, response));
            List<Collezione> collezioni_condivise = dataLayer.getCollezioneDAO().getCollezioniCondivise(Utility.getUtente(request, response));
            List<Collezione> collezioni_pubbliche = dataLayer.getCollezioneDAO().getCollezioni();
            Set<Collezione> collezioni = new HashSet<>();

            if (request.getParameter("keyword") != null && !request.getParameter("keyword").isBlank()) {

                if (Utility.getUtente(request, response) != null) {
                    System.out.println("Cerco collezioni utente loggato keyword");
                    collezioni.addAll(dataLayer.getCollezioneDAO().getCollezioniByKeywordLogged(request.getParameter("keyword"), Utility.getUtente(request, response)));
                } else {
                    System.out.println("Cerco collezioni utente NON loggato keyword");
                    collezioni.addAll(dataLayer.getCollezioneDAO().getCollezioniByKeyword(request.getParameter("keyword")));
                }
            } else {
                collezioni.addAll(collezioni_utente);
                collezioni.addAll(collezioni_condivise);
                collezioni.addAll(collezioni_pubbliche);
            }

            //lista dei collezioni
            request.setAttribute("collezioni", collezioni);

            result.activate("/collezioni/index.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
