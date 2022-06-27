package org.webeng.collector_site.controller.collezioni;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Collezione;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditCollezione extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (request.getMethod().equals("POST")) {
            updateCollezione(request, response);
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
        CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");
        var collezioneDAO = dataLayer.getCollezioneDAO();
        var utenteDAO = dataLayer.getUtenteDAO();

        Collezione collezione = collezioneDAO.getCollezione(Integer.parseInt(request.getParameter("id")));

        request.setAttribute("collezione", collezione);

        request.setAttribute("utenti_condivisi", utenteDAO.getUtentiCondivisi(collezione));
        result.activate("collezioni/edit.ftl", request, response);

    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void updateCollezione(HttpServletRequest request, HttpServletResponse response) {
        try {
            CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");
            Collezione collezione = dataLayer.getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id")));
            boolean error = false;

            if (request.getParameterValues("user_share") != null) {

                String username = request.getParameter("user_share");
                List<Utente> utenti = new ArrayList<>();
                utenti.add(dataLayer.getUtenteDAO().getUtente(username));
                collezione.setUtentiCondivisi(utenti);

            } else {
                String titolo = request.getParameter("titolo");
                String privacy = String.valueOf(request.getParameter("privacy"));
                List<Collezione> collezioni = dataLayer.getCollezioneDAO().getCollezioni(Utility.getUtente(request, response));
                /*chiamata dei metodi setTitolo,setPrivacy
                e setUtentiCondivisi sui valori di titolo
                e privacy inseriti dall'utente */
                collezione.setTitolo(titolo);
                collezione.setPrivacy(privacy);


                if (!titolo.equalsIgnoreCase(collezione.getTitolo())) {
                    for (Collezione c : collezioni) {
                        if (c.getTitolo().equalsIgnoreCase(titolo)) {
                            request.setAttribute("error", "Hai già una collezione con questo titolo!");
                            action_logged(request, response);
                            error = true;
                            break;
                        }
                    }
                }
            }
            if (!error) {
                //chiamata metodo storeCollezione per aggiornare la collezione
                dataLayer.getCollezioneDAO().storeCollezione(collezione);
                response.sendRedirect("/show-collezione?id=" + collezione.getKey());
            }


        } catch (DataException | IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
}
