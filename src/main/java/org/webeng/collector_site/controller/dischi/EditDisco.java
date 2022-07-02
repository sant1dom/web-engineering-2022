package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditDisco extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (request.getMethod().equals("POST")) {
            updateDisco(request, response);
        } else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_anonymous(request, response);
                } else {
                    action_logged(request, response);
                }
            } catch (TemplateManagerException | DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());

        Disco disco = ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.parseInt(request.getParameter("id")));
        Genere[] genere = Genere.values();
        List<String> generi = new ArrayList<>();
        for (Genere g : genere) {
            generi.add(g.name());
        }
        Formato[] formato = Formato.values();
        List<String> formati = new ArrayList<>();
        for (Formato f : formato) {
            formati.add(f.name());
        }
        StatoConservazione[] stato_conservazione = StatoConservazione.values();
        List<String> statoConservazione = new ArrayList<>();
        for (StatoConservazione s : stato_conservazione) {
            statoConservazione.add(s.name());
        }
        request.setAttribute("disco", disco);
        request.setAttribute("generi", Objects.requireNonNull(generi));
        request.setAttribute("formati", Objects.requireNonNull(formati));
        request.setAttribute("statoConservazione", Objects.requireNonNull(statoConservazione));
        result.activate("dischi/edit.ftl", request, response);

    }

 private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    private void updateDisco(HttpServletRequest request, HttpServletResponse response) {
        try {
            CollectorsDataLayer datalayer = (CollectorsDataLayer) request.getAttribute("datalayer");

            String titolo = request.getParameter("titolo");
            String anno = request.getParameter("anno");
            String barcode = request.getParameter("barcode");
            String etichetta = request.getParameter("etichetta");
            Genere genere = Genere.valueOf(request.getParameter("genere"));
            Formato formato = Formato.valueOf(request.getParameter("formato"));
            StatoConservazione statoConservazione;
            if ("DIGITALE".equals(formato.name())) {
                statoConservazione = null;
            } else {
                statoConservazione = StatoConservazione.valueOf(request.getParameter("statoConservazione"));
            }

            int id = Integer.parseInt(request.getParameter("id"));

            Disco disco = datalayer.getDiscoDAO().getDisco(id);

            disco.setTitolo(titolo);
            disco.setAnno(anno);
            disco.setBarCode(barcode);
            disco.setEtichetta(etichetta);
            disco.setGenere(genere);
            disco.setStatoConservazione(statoConservazione);
            disco.setFormato(formato);
            datalayer.getDiscoDAO().storeDisco(disco);

            response.sendRedirect("/show-disco?id=" + id);

        } catch (Exception e) {
            handleError(e, request, response);
        }
    }
}
