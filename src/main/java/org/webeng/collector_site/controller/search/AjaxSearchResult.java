package org.webeng.collector_site.controller.search;

import com.google.gson.Gson;
import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
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
            List<String> nomiUtenti = Utility.getUtenti(request, response);
//            List<String> nomiDischi = Utility.getDischi(request, response);
            List<String> nomiCollezioni = Utility.getCollezioni(request, response);

            String data = "[";

            if (!nomiUtenti.isEmpty()) {
                data += "{ \"UTENTI\":  " + gson.toJson(nomiUtenti) + "},";
            }
//            if (!nomiDischi.isEmpty()) {
//               data += "{ \"DISCHI\":  " +  gson.toJson(nomiDischi) + "},";
//            }
            //TODO devo trovare un modo per non far arrabbiare il parser del json
            if (!nomiCollezioni.isEmpty()) {
                data += "{ \"COLLEZIONI\": " +  gson.toJson(nomiCollezioni ) + "}";
            }

            data += "]";

            out.println(data);
            out.flush();
            out.close();
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Imposta e stampa il json per la risposta ad una richiesta di ricerca da parte di ajax
     */
    private void setGson(List<String> suggestions, PrintWriter out) {

    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
