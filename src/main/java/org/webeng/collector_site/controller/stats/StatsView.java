package org.webeng.collector_site.controller.stats;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.dao.StatsDAO_MySQL;
import org.webeng.collector_site.data.model.Genere;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StatsView extends CollectorsBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession s = SecurityHelpers.checkSession(request);
        String https_redirect_url = SecurityHelpers.checkHttps(request);
        request.setAttribute("https-redirect", https_redirect_url);
        switch (request.getParameter("action") != null ? request.getParameter("action") : "") {
            case "autore":
                try {
                    response.getWriter().println(((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getDischiPerAutore(request.getParameter("autore")));
                } catch (DataException | IOException e) {
                    e.printStackTrace();
                    handleError(e, request, response);
                }
                break;
            case "genere":
                try {
                    response.getWriter().println(((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getDischiPerGenere(request.getParameter("genere")));
                } catch (DataException | IOException e) {
                    handleError(e, request, response);
                }
                break;
            case "etichetta":
                try {
                    response.getWriter().println(((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getDischiPerEtichetta(request.getParameter("etichetta")));
                } catch (DataException | IOException e) {
                    handleError(e, request, response);
                }
                break;
            case "anno":
                try {
                    response.getWriter().println(((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getDischiPerAnno(Integer.parseInt(request.getParameter("anno"))));
                } catch (DataException | IOException e) {
                    e.printStackTrace();
                    handleError(e, request, response);
                }
                break;
            default:
                if (s == null) {
                    action_anonymous(request, response);
                } else {
                    action_logged(request, response);
                }
                break;
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            Utente utente = Utility.getUtente(request, response);
            request.setAttribute("utente", utente);
            request.setAttribute("autori", ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutori());
            request.setAttribute("generi", Genere.values());
            request.setAttribute("etichette", ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getEtichette());
            request.setAttribute("stats", ((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getTotalStats());
            request.setAttribute("numero_collezioni_private", ((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getNumeroCollezioniPrivateUtente(utente.getKey()));
            request.setAttribute("numero_collezioni_totali", ((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getNumeroCollezioniTotaliUtente(utente.getKey()));
            result.activate("stats/stats.ftl", request, response);
        } catch (TemplateManagerException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute("autori", ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutori());
            request.setAttribute("generi", Genere.values());
            request.setAttribute("etichette", ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getEtichette());
            request.setAttribute("stats", ((CollectorsDataLayer) request.getAttribute("datalayer")).getStatsDAO().getTotalStats());
            result.activate("stats/stats.ftl", request, response);
        } catch (TemplateManagerException | DataException ex) {
            handleError(ex, request, response);
            ex.printStackTrace();
        }
    }
}
