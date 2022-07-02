package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.DiscoImpl;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateDisco extends CollectorsBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (request.getMethod().equals("POST")) {
            saveDisco(request, response);
        } else {
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
                    action_logged(request, response);
                }
            } catch (TemplateManagerException | DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        Utente utente = Utility.getUtente(request, response);
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));

        List<Collezione> collezioni = dataLayer.getCollezioneDAO().getCollezioni(utente);
        List<Autore> autori = dataLayer.getAutoreDAO().getAutori();
        List<Disco> dischiPadri = dataLayer.getDiscoDAO().getDischiPadri();
        List<Traccia> tracce = dataLayer.getTracciaDAO().getTracce();


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
        request.setAttribute("collezioni", Objects.requireNonNull(collezioni));
        request.setAttribute("dischiPadri", Objects.requireNonNull(dischiPadri));
        request.setAttribute("authors", Objects.requireNonNull(autori));
        request.setAttribute("generi", Objects.requireNonNull(generi));
        request.setAttribute("formati", Objects.requireNonNull(formati));
        request.setAttribute("statoConservazione", Objects.requireNonNull(statoConservazione));
        request.setAttribute("tracce", Objects.requireNonNull(tracce));

        result.activate("dischi/create.ftl", request, response);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void saveDisco(HttpServletRequest request, HttpServletResponse response) {
        try {
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            Utente utente = Utility.getUtente(request, response);
            List<Autore> autori = new ArrayList<>();
            List<Traccia> tracce = new ArrayList<>();
            List<Image> immagini = new ArrayList<>();
            Disco padre = new DiscoImpl();
            String padre_id;
            Genere genere;
            Formato formato = Formato.valueOf(request.getParameter("formato"));
            StatoConservazione statoConservazione;
            LocalDate dataInserimento = LocalDate.now();

            if (request.getParameter("formato").equals("DIGITALE")) {
                statoConservazione = null;
            } else {
                statoConservazione = StatoConservazione.valueOf(request.getParameter("statoConservazione"));
            }

            String titolo = request.getParameter("titolo");
            String anno = request.getParameter("anno");
            String barcode = request.getParameter("barcode");
            String etichetta = request.getParameter("etichetta");


            if (request.getParameter("padre")!=null && !request.getParameter("padre").equals("")) {
                padre_id = request.getParameter("padre");
                padre = dataLayer.getDiscoDAO().getDisco(Integer.parseInt(padre_id));
                genere = padre.getGenere();
                autori = dataLayer.getAutoreDAO().getAutori(padre);
                tracce = dataLayer.getTracciaDAO().getTracce(padre);
            } else {
                for (String autore : request.getParameterValues("autore")) {
                    autori.add(dataLayer.getAutoreDAO().getAutore(Integer.parseInt(autore)));
                }

                for (String traccia : request.getParameterValues("tracce")) {
                    tracce.add(dataLayer.getTracciaDAO().getTraccia(Integer.parseInt(traccia)));
                }
                genere = Genere.valueOf(request.getParameter("genere"));
            }
            Disco disco = new DiscoImpl(titolo, anno, etichetta, barcode, genere, statoConservazione, formato, dataInserimento, utente, autori, immagini, tracce, padre);

            String idDisco = String.valueOf(dataLayer.getDiscoDAO().storeDisco(disco));
            List<Collezione> collezioni = new ArrayList<>();
            if (request.getParameterValues("collezioni") != null) {
                for (String collezione_id : request.getParameterValues("collezioni")) {
                    collezioni.add(dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(collezione_id)));
                }
                dataLayer.getDiscoDAO().addDiscoToCollezioni(collezioni, disco);
            }

            response.sendRedirect("/show-disco?id=" + idDisco);
        } catch (Exception e) {
            e.printStackTrace();
            handleError(e, request, response);
        }
    }
}
