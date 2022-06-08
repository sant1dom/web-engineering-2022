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
            HttpSession s = request.getSession(false);
            //ottengo il DAO dell'utente
            UtenteDAO utenteDAO = ((CollectorsDataLayer) request.getAttribute("datalayer")).getUtenteDAO();
            //ottengo l'utente tramite la sua id nella sessione
            Utente utente = utenteDAO.getUtente(Integer.parseInt(s.getAttribute("userid").toString()));
            //ci deve essere un errore in createUtente o getUtente(id) perchè non posso ripescare i dati dell'utente creato
            System.out.println(s.getAttribute("userid").toString() + " " + s.getAttribute("username"));
            System.out.println(utente.getNome()); //non stampa nulla
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
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
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
