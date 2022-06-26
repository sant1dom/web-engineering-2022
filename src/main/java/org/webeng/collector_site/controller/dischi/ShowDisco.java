package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ShowDisco extends CollectorsBaseController {
    public static final String REFERRER = "referrer";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            HttpSession s = SecurityHelpers.checkSession(request);
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            Utente utente= null;
            if (s != null) {
                utente = Utility.getUtente(request, response);
            }
                if (utente != null){
                    request.setAttribute("utente", utente);
                }
                action_default(request, response);
        } catch (TemplateManagerException | DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException, IOException {
        TemplateResult result = new TemplateResult(getServletContext());
        CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");

        Disco disco = dataLayer.getDiscoDAO().getDisco(Integer.parseInt(request.getParameter("id")));
        List<Traccia> tracce = dataLayer.getTracciaDAO().getTracce(disco);
        List<Traccia> tracceAdd = dataLayer.getTracciaDAO().tracciaNonInDisco(disco);
        List<Autore> autori = dataLayer.getAutoreDAO().getAutori(disco);
        List<Autore> autoriAdd = dataLayer.getAutoreDAO().getAutoriNonDisco(disco);
        List<Image> immagini= dataLayer.getImageDAO().getImages(disco);

        request.setAttribute("disco", disco);
        request.setAttribute("autoriAdd", Objects.requireNonNull(autoriAdd));
        request.setAttribute("tracce", Objects.requireNonNull(tracce));
        request.setAttribute("tracceAdd", Objects.requireNonNull(tracceAdd));
        request.setAttribute("autori", Objects.requireNonNull(autori));
        request.setAttribute("immagini", Objects.requireNonNull(immagini));

        result.activate("dischi/show.ftl", request, response);
    }
}
