package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.dao.UtenteDAO;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Home extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        result.activate("index.ftl", request, response);
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            Utente utente = getUtente(request, response);

            if (utente != null) {
                TemplateResult result = new TemplateResult(getServletContext());
                request.setAttribute(REFERRER, request.getParameter(REFERRER));
                request.setAttribute("utente", utente);
                result.activate("index.ftl", request, response);
            } else {
                TemplateResult result = new TemplateResult(getServletContext());
                request.setAttribute(REFERRER, request.getParameter(REFERRER));
                result.activate("index.ftl", request, response);
            }
        } catch (TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private Utente getUtente(HttpServletRequest request, HttpServletResponse response) {
        Utente utente = null;

        try {
            //sessione dell'utente
            HttpSession s = request.getSession(false);
            //id dell'utente
            int userid = (int) s.getAttribute("userid");
            //datalayer
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            //ottengo il DAO dell'utente
            UtenteDAO utenteDAO = dataLayer.getUtenteDAO();
            //ottengo l'utente tramite la sua id nella sessione
            utente = utenteDAO.getUtente(userid);
        } catch (DataException ex) {
            handleError(ex, request, response);
        }

        return utente;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession s = SecurityHelpers.checkSession(request);
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            if (s == null) {
                action_anonymous(request, response);
            } else {
                action_logged(request, response);
            }
        } catch (TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
