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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateDischiCollezione extends CollectorsBaseController {

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

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());

        Collezione collezione=((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id_collezione")));
        List<Disco> dischiCollezione=((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi(collezione);
        List <Disco> allDischi=((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi();

        /* la lista dischi conterrà tutti i dischi che non sono già nella collezione */
        List<Disco> dischi=new ArrayList<>();
        dischi.addAll(allDischi);
        dischi.removeAll(dischiCollezione);

        request.setAttribute(REFERRER, request.getParameter("id_collezione"));
        request.setAttribute("collezione", collezione);
        request.setAttribute("dischi", Objects.requireNonNullElse(dischi, ""));
        result.activate("collezioni/update_dischiCollezione.ftl", request, response);

    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void updateCollezione(HttpServletRequest request, HttpServletResponse response) {
        try {

            List<Disco> dischi = new ArrayList<>();

            for (String disco : request.getParameterValues("disco")) {
                dischi.add(((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.parseInt(disco)));
            }
            Collezione collezione = ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(Integer.parseInt(request.getParameter("id_collezione")));

            if(Utility.getUtente(request,response).equals(collezione.getUtente())) {
                collezione.setTitolo(collezione.getTitolo());
                collezione.setPrivacy(collezione.getPrivacy());

                ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(collezione);

                for (Disco disco : dischi) {
                    ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().addDisco(collezione, disco);
                }
                response.sendRedirect("/show-collezioni");
            }
            else
                response.sendRedirect("/home");
        } catch (Exception e) {
            handleError(e, request, response);
        }
    }


}
