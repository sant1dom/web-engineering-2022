package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.*;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Classe per metodi di utilità
 */
public class Utility {
    private Utility() {
    }

    /**
     * Controlla se la sessione è attiva, se si ottiene l'utente dalla sessione e lo restituisce,
     * altrimenti restituisce null.
     * Questo metodo è utilizzato per ottenere l'utente che ha effettuato il login e poterlo inserire nella request
     * prima del caricamento della pagina.
     *
     * @param request  servlet request
     * @param response servlet response
     * @return l'utente che ha effettuato il login
     * @throws DataException segnalazione di errore
     */
    public static Utente getUtente(HttpServletRequest request, HttpServletResponse response) throws DataException {
        Utente utente;
        //sessione dell'utente
        HttpSession s = request.getSession(false);
        //id dell'utente
        if (s != null) {
            int userid = (int) s.getAttribute("userid");
            //datalayer
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            //ottengo il DAO dell'utente
            UtenteDAO utenteDAO = dataLayer.getUtenteDAO();
            //ottengo l'utente tramite la sua id nella sessione
            utente = utenteDAO.getUtente(userid);
            return utente;
        } else {
            return null;
        }
    }

    /**
     * @param request  servlet request
     * @param response servlet response
     * @return la lista degli utenti i cui dati contengono la keyword passata come parametro nella request
     * @throws DataException segnalazione di errore
     */
    public static List<String> getUtenti(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<String> utenti;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO dell'utente
        UtenteDAO utenteDAO = dataLayer.getUtenteDAO();
        //ottengo gli utenti tramite la query e la keyword
        utenti = utenteDAO.getUtentiByKeyword(keyword);

        return utenti;
    }

    /**
     * @param request  servlet request
     * @param response servlet response
     * @return la lista delle collezioni i cui dati contengono la keyword passata come parametro nella request
     * @throws DataException segnalazione di errore
     */
    public static List<String> getCollezioni(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<String> collezioni;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO delle
        CollezioneDAO collezioneDAO = dataLayer.getCollezioneDAO();
        //ottengo le collezioni tramite la query e la keyword
        collezioni = collezioneDAO.getCollezioniByKeyword(keyword);

        return collezioni;
    }

    /**
     * @param request  servlet request
     * @param response servlet response
     * @return la lista dei dischi i cui dati contengono la keyword passata come parametro nella request
     * @throws DataException segnalazione di errore
     */
    public static List<Disco> getDischi(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<Disco> dischi;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO dei dischi
        DiscoDAO discoDAO = dataLayer.getDiscoDAO();
        //ottengo i dischi tramite la query e la keyword
        dischi = discoDAO.getDischiByKeyword(keyword);

        return dischi;
    }

    /**
     * @param request  servlet request
     * @param response servlet response
     * @return la lista delle tracce i cui dati contengono la keyword passata come parametro nella request
     * @throws DataException segnalazione di errore
     */
    public static List<String> getTracce(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<String> tracce;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO delle tracce
        TracciaDAO tracciaDAO = dataLayer.getTracciaDAO();
        //ottengo le tracce tramite la query e la keyword
        tracce = tracciaDAO.getTracceByKeyword(keyword);

        return tracce;
    }

    /**
     * @param request  servlet request
     * @param response servlet response
     * @return la lista degli autori i cui dati contengono la keyword passata come parametro nella request
     * @throws DataException segnalazione di errore
     */
    public static List<Autore> getAutori(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<Autore> autori;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO dei dischi
        AutoreDAO autoreDAO = dataLayer.getAutoreDAO();
        //ottengo l'autore tramite la query e la keyword
        autori = autoreDAO.getAutoriByKeyword(keyword);

        return autori;
    }
}
