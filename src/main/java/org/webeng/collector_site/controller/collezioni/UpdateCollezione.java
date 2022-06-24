package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.dao.UtenteDAO;
import org.webeng.collector_site.data.impl.CollezioneImpl;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateCollezione extends CollectorsBaseController {

    public static final String REFERRER = "referrer";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if(request.getMethod().equals("POST")){
            updateCollezione(request,response);
        }else {
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
        var collezioneDAO = ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO();
        var utenteDAO = ((CollectorsDataLayer) request.getAttribute("datalayer")).getUtenteDAO();;
        Collezione collezione = collezioneDAO.getCollezione(Integer.parseInt(request.getParameter("id_collezione")));

        request.setAttribute("collezione", collezione);

        request.setAttribute("utenti_condivisi", utenteDAO.getUtentiCondivisi(collezione));
        result.activate("collezioni/update_collezione.ftl", request, response);

    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void updateCollezione(HttpServletRequest request, HttpServletResponse response) {
        try {
            String titolo = request.getParameter("titolo");
            List<String> utenti_usernames = List.of(request.getParameterValues("utenti[]"));
            List<Utente> utenti = new ArrayList<>();

            for(String username: utenti_usernames){
                try {
                    Utente utente = ((CollectorsDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(username);
                    utenti.add(utente);
                } catch (DataException ignored) {}
            }



            String privacy = String.valueOf(request.getParameter("privacy"));

            Collezione collezione = ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id_collezione")));

            //chiamata dei metodi setTitolo,setPrivacy e setUtentiCondivisi sui valori di titolo e privacy inseriti dall'utente
            collezione.setTitolo(titolo);
            collezione.setPrivacy(privacy);
            collezione.setUtentiCondivisi(utenti);

            //chiamata metodo storeCollezione per aggiornare la collezione
            ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(collezione);

            response.sendRedirect("/show-collezioni");

        } catch (Exception e) {
            handleError(e, request, response);
            e.printStackTrace();
        }
    }


}
