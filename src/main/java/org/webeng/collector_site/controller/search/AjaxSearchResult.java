package org.webeng.collector_site.controller.search;

import com.google.gson.Gson;
import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
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
//            List<String> nomiDischi = getDischi(request, response);
            List<String> nomiCollezioni = Utility.getCollezioni(request, response);

            if (!nomiUtenti.isEmpty()) {
                out.print(gson.toJson(nomiUtenti));
            }
//            if (!nomiDischi.isEmpty()) {
//                out.print(gson.toJson(nomiDischi));
//            }
            //TODO devo trovare un modo per non far arrabbiare il parser del json
//            if (!nomiCollezioni.isEmpty()) {
//                out.print(gson.toJson(nomiCollezioni));
//            }

            out.flush();
            out.close();
        } catch (IOException | DataException ex) {
            handleError(ex, request, response);
        }
    }


    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
