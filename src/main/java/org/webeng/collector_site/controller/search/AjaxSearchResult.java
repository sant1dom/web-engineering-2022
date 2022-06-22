package org.webeng.collector_site.controller.search;

import com.google.gson.Gson;
import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.controller.Utility;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.framework.data.DataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet per la creazione del json per la richiesta ajax della ricerca.
 * @author Davide De Acetis
 */
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
            //Vengono eseguite le query per ottenere i risultati attraverso la keyword
            List<String> utenti = Utility.getUtenti(request, response);
            List<String> collezioni = Utility.getCollezioni(request, response);
            List<Disco> dischi = Utility.getDischi(request, response);
            List<String> tracce = Utility.getTracce(request, response);
            List<Autore> autori = Utility.getAutori(request, response);

            //Viene costruita la stringa che verrà poi convertita in json
            //La stringa è costruita in modo da risultare un array di oggetti, ognuno corrispondente a una tipologia
            //di risultato. Ogni tipologia conterrà una lista di oggetti, ognuno corrispondente a un risultato.
            String data = "[";

            if (!utenti.isEmpty()) {
                data += "{ \"UTENTI\":  " + gson.toJson(utenti) + "}";
            }

            if (!collezioni.isEmpty()) {
                data += "{ \"COLLEZIONI\": " + gson.toJson(collezioni) + "}";
            }

            if (!dischi.isEmpty()) {
                data += "{ \"DISCHI\":  {";
                for (Disco disco : dischi) {
                    data += gson.toJson(disco.getKey().toString()) + ": [" +
                            gson.toJson(disco.getTitolo()) + "," +
                            gson.toJson(disco.getEtichetta()) + "," +
                            gson.toJson(disco.getAnno()) + "," +
                            gson.toJson(disco.getGenere()) + "," +
                            "] ,";
                }
                data += "}}";
            }

            if (!tracce.isEmpty()) {
                data += "{ \"TRACCE\":  " + gson.toJson(tracce) + "}";
            }

            if (!autori.isEmpty()) {
                data += "{ \"AUTORI\":  {";
                for (Autore autore : autori) {
                    data += gson.toJson(autore.getKey().toString()) + ": [" +
                            gson.toJson(autore.getNomeArtistico()) + "," +
                            gson.toJson(autore.getNome()) +
                            "] ,";
                }
                data += "}}";
            }

            data += "]";

            //Vengono individuati e rimosse eventuali virgole finali e anomalie
            data = data.replace("}{", "},{");
            data = data.replace("][", "],[");
            data = data.replace(",}", "}");
            data = data.replace(",]", "]");

            //Viene stampato il json e inviato al client
            out.println(data);
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
        return "Servlet che costruisce il json per la ricerca ajax";
    }
}
