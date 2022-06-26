package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.StreamResult;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
                action_showDisco(request, response);
        } catch (TemplateManagerException | DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_showDisco(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException, IOException {
        TemplateResult result = new TemplateResult(getServletContext());
        Disco disco = ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.valueOf(request.getParameter("id_disco")));
        List<Traccia> tracce= ((CollectorsDataLayer) request.getAttribute("datalayer")).getTracciaDAO().getTracce(disco);
        List<Traccia> tracceDaAggiungere= ((CollectorsDataLayer) request.getAttribute("datalayer")).getTracciaDAO().tracciaNonInDisco(disco);
        List<Autore> autoriDisco= ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutori(disco);
        List<Autore> autoriDaAggiungere= ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutoriNonDisco(disco);
        request.setAttribute("disco", disco);
        request.setAttribute("autoriDaAggiungere", Objects.requireNonNull(autoriDaAggiungere));
        request.setAttribute("tracce", Objects.requireNonNull(tracce));
        request.setAttribute("tracceDaAggiungere", Objects.requireNonNull(tracceDaAggiungere));
        request.setAttribute("autoriDisco", Objects.requireNonNull(autoriDisco));
        request.setAttribute("utente", Objects.requireNonNull(Utility.getUtente(request,response)));
        List<Image> immagini= ((CollectorsDataLayer) request.getAttribute("datalayer")).getImageDAO().getImages(disco);
        request.setAttribute("immagini", Objects.requireNonNull(immagini));
        result.activate("disco/show-disco.ftl", request, response);
    }
}
