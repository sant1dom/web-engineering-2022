package org.webeng.collector_site.controller.search;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet per la gestione dei risultati di ricerca.
 * @author Davide De Acetis
 */
public class ResultDispatcher extends CollectorsBaseController {

    public static final String REFERRER = "referrer";

    /**
     * Gestisce il caso base in cui viene inserita una keyword da ricercare senza selezionare tra i suggerimenti
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("/search?keyword=" + request.getParameter("keyword"));
        } catch (IOException ex) {
            handleError(ex, request, response);
        }

    }

    /**
     * Gestisce i risultati per gli utenti.
     * Se viene passato un id di un utente, viene visualizzata la pagina di dettaglio dell'utente.
     * Altrimenti viene visualizzata la lista degli utenti corrispondenti alla keyword.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void action_utente(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!request.getParameter("item_id").isBlank()) {
                response.sendRedirect("/profilo?id=" + request.getParameter("item_id"));
            } else {
                response.sendRedirect("/index-utente?keyword=" + request.getParameter("keyword"));
            }
        } catch (IOException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Gestisce i risultati per gli collezioni.
     * Se viene passato un id di un collezione, viene visualizzata la pagina di dettaglio dell'collezione.
     * Altrimenti viene visualizzata la lista degli collezioni corrispondenti alla keyword.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void action_collezioni(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!request.getParameter("item_id").isBlank()) {
                response.sendRedirect("/show-collezione?id=" + request.getParameter("item_id"));
            } else {
                response.sendRedirect("/index-collezione?keyword=" + request.getParameter("keyword"));
            }
        } catch (IOException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Gestisce i risultati per i dischi.
     * Se viene passato un id di un disco, viene visualizzata la pagina di dettaglio del disco.
     * Altrimenti viene visualizzata la lista dei dischi corrispondenti alla keyword.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void action_disco(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!request.getParameter("item_id").isBlank()) {
                response.sendRedirect("/show-disco?id=" + request.getParameter("item_id"));
            } else {
                response.sendRedirect("/index-disco?keyword=" + request.getParameter("keyword"));
            }
        } catch (IOException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Gestisce i risultati per le tracce.
     * Se viene passato un id di una traccia, viene visualizzata la pagina di dettaglio dela traccia.
     * Altrimenti viene visualizzata la lista delle tracce corrispondenti alla keyword.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void action_traccia(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!request.getParameter("item_id").isBlank()) {
                response.sendRedirect("/show-traccia?id=" + request.getParameter("item_id"));
            } else {
                response.sendRedirect("/index-traccia?keyword=" + request.getParameter("keyword"));
            }
        } catch (IOException ex) {
            handleError(ex, request, response);
        }
    }

    /**
     * Gestisce i risultati per gli autori.
     * Se viene passato un id di un autore, viene visualizzata la pagina di dettaglio dell'autore.
     * Altrimenti viene visualizzata la lista degli autori corrispondenti alla keyword.
     *
     * @param request servlet request
     * @param response servlet response
     */
    private void action_autori(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!request.getParameter("item_id").isBlank()) {
                response.sendRedirect("/show-autore?id=" + request.getParameter("item_id"));
            } else {
                response.sendRedirect("/index-autore?keyword=" + request.getParameter("keyword"));
            }
        } catch (IOException ex) {
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
        String https_redirect_url = SecurityHelpers.checkHttps(request);
        request.setAttribute("https-redirect", https_redirect_url);
        String type = request.getParameter("item_type");

        // controllo se il tipo di item è stato impostato, se si lo gestisco in base al tipo,
        // altrimenti mostro la pagina di ricerca generica
        if (!type.isBlank()) {
            switch (type) {
                case ("UTENTI") -> action_utente(request, response);
                case ("COLLEZIONI") -> action_collezioni(request, response);
                case ("DISCHI") -> action_disco(request, response);
                case ("TRACCE") -> action_traccia(request, response);
                case ("AUTORI") -> action_autori(request, response);
                default -> action_default(request, response);
            }
        } else {
            action_default(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Servlet per la gestione dei risultati di ricerca";
    }
}
