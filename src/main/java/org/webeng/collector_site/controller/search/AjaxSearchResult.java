package org.webeng.collector_site.controller.search;

import com.google.gson.Gson;
import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.dao.UtenteDAO;
import org.webeng.framework.data.DataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AjaxSearchResult extends CollectorsBaseController {

    public AjaxSearchResult() {
        super();
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
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();
            List<String> nomiUtenti = getUtenti(request, response);
//            List<String> nomiDischi = getDischi(request, response);
//            List<String> nomiCollezioni = getCollezioni(request, response);

            if (!nomiUtenti.isEmpty()) {
                out.print(gson.toJson(nomiUtenti));
            }
//            if (!nomiDischi.isEmpty()) {
//                out.print(gson.toJson(nomiDischi));
//            }
//            if (!nomiCollezioni.isEmpty()) {
//                out.print(gson.toJson(nomiCollezioni));
//            }

            out.flush();
            out.close();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getUtenti(HttpServletRequest request, HttpServletResponse response) {
        List<String> nomiUtenti = new ArrayList<>();
        try {
            String keyword = request.getParameter("keyword");
            //datalayer
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            //ottengo il DAO dell'utente
            UtenteDAO utenteDAO = dataLayer.getUtenteDAO();
            //ottengo l'utente tramite la sua id nella sessione
            nomiUtenti = utenteDAO.getUtenti(keyword);
        } catch (DataException ex) {
            handleError(ex, request, response);
        }
        return nomiUtenti;
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
