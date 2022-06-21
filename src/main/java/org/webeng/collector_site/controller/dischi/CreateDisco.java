package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.DiscoImpl;
import org.webeng.collector_site.data.impl.ImageImpl;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class CreateDisco extends CollectorsBaseController {
    public static final String REFERRER = "referrer";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
       if(request.getMethod().equals("POST")){
           saveDisco(request,response);
       }else {
        try {
            HttpSession s = SecurityHelpers.checkSession(request);
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            if (s == null) {
                action_anonymous(request, response);
            }else{
                    action_logged(request, response);
                }
        } catch (TemplateManagerException | DataException | IOException ex) {
            handleError(ex, request, response);
        }
       }
    }


    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        Utente utente= Utility.getUtente(request, response);
        List<Collezione> collezioni=((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioni(utente);
        List<Autore> autori = ((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutori();
        List<Disco> dischiPadri=((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischiPadri();
        List<Traccia> tracce= ((CollectorsDataLayer) request.getAttribute("datalayer")).getTracciaDAO().getTracce();


        Genere[] genere= Genere.values();
        List<String> generi= new ArrayList<String >();
        for(Genere g: genere){
            generi.add(g.name());
        }
        Formato[] formato= Formato.values();
        List<String> formati= new ArrayList<String >();
        for(Formato f: formato){
            formati.add(f.name());
        }
        StatoConservazione[] stato_conservazione= StatoConservazione.values();
        List<String> statoConservazione= new ArrayList<String >();
        for(StatoConservazione s: stato_conservazione){
            statoConservazione.add(s.name());
        }
        request.setAttribute("collezioni", Objects.requireNonNull(collezioni));
        request.setAttribute("dischiPadri", Objects.requireNonNull(dischiPadri));
        request.setAttribute("authors", Objects.requireNonNull(autori));
        request.setAttribute("generi", Objects.requireNonNull(generi));
        request.setAttribute("formati", Objects.requireNonNull(formati));
        request.setAttribute("statoConservazione", Objects.requireNonNull(statoConservazione));
        request.setAttribute("tracce", Objects.requireNonNull(tracce));
        result.activate("disco/creaDisco.ftl", request,response );
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void saveDisco(HttpServletRequest request, HttpServletResponse response)  {
           try {
               List<Image> immagini = new ArrayList<>();
               String titolo = request.getParameter("titolo");
               String anno = request.getParameter("anno");
               String barcode = request.getParameter("barcode");
               String etichetta = request.getParameter("etichetta");
               int doppioni= Integer.parseInt(request.getParameter("doppioni"));

               Genere genere = Genere.valueOf(request.getParameter("genere"));
               Utente utente = Utility.getUtente(request, response);

               Formato formato = Formato.valueOf(request.getParameter("formato"));
               StatoConservazione statoConservazione = StatoConservazione.valueOf(request.getParameter("statoConservazione"));

               LocalDate dataInserimento = LocalDate.now();
               List<Autore> autori = new ArrayList<>();
               for (String autore : request.getParameterValues("autore")) {
                   autori.add(((CollectorsDataLayer) request.getAttribute("datalayer")).getAutoreDAO().getAutore(Integer.parseInt(autore)));
               }
               List<Traccia> tracce = new ArrayList<>();
               for (String traccia : request.getParameterValues("tracce")) {
                   tracce.add(((CollectorsDataLayer) request.getAttribute("datalayer")).getTracciaDAO().getTraccia(Integer.parseInt(traccia)));
               }
               String padre_id = "0";
               if (!(request.getParameter("padre").equals(""))) {
                   padre_id = request.getParameter("padre");
               }
               Disco padre = ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.parseInt(padre_id));
               Disco disco = new DiscoImpl(titolo, anno, etichetta, barcode, genere, statoConservazione, formato, dataInserimento, utente, autori, immagini, tracce, padre,doppioni);

              String idDisco= String.valueOf(((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().storeDisco(disco));
              List<Collezione> collezioni= new ArrayList<>();
              if(request.getParameterValues("collezioni")!=null){
                  for(String collezione_id: request.getParameterValues("collezioni")){
                      collezioni.add(((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(Integer.parseInt(collezione_id)));
                  }
                  ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().addDiscoToCollezioni(collezioni, disco);
              }

               HttpSession s= SecurityHelpers.checkSession(request);
               s.setAttribute("idDisco",idDisco);
               response.sendRedirect("/add-immagini");
           }catch (Exception e){
               handleError(e, request, response);
           }

    }

}
