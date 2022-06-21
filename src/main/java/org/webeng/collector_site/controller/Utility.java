package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.*;
import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.controller.AbstractBaseController;
import org.webeng.framework.data.DataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private Utility() {
    }
    public static Utente getUtente(HttpServletRequest request, HttpServletResponse response) throws DataException {
        Utente utente = null;
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

    public static List<String> getUtenti(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<String> nomiUtenti;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO dell'utente
        UtenteDAO utenteDAO = dataLayer.getUtenteDAO();
        //ottengo l'utente tramite la sua id nella sessione
        nomiUtenti = utenteDAO.getUtentiByKeyword(keyword);

        return nomiUtenti;
    }
//todo: quando i dischi saranno implementati

//    public static List<String> getDischi(HttpServletRequest request, HttpServletResponse response) throws DataException {
//        List<String> nomiDischi;
//        //keyword inserita dall'utente
//        String keyword = request.getParameter("keyword");
//        //datalayer
//        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
//        //ottengo il DAO dei dischi
//        DiscoDAO discoDAO = dataLayer.getDiscoDAO();
//        //ottengo l'utente tramite la sua id nella sessione
//        nomiDischi = discoDAO.getDischi(keyword);
//
//        return nomiDischi;
//    }

    public static List<Autore> getAutori(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<Autore> autori;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO dei dischi
        AutoreDAO autoreDAO = dataLayer.getAutoreDAO();
        //ottengo l'utente tramite la sua id nella sessione
        autori = autoreDAO.getAutoriByKeyword(keyword);

        return autori;
    }

    public static List<String> getTracce(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<String> nomiTracce;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO delle tracce
        TracciaDAO tracciaDAO = dataLayer.getTracciaDAO();
        //ottengo l'utente tramite la sua id nella sessione
        nomiTracce = tracciaDAO.getTracceByKeyword(keyword);

        return nomiTracce;
    }

    public static List<String> getCollezioni(HttpServletRequest request, HttpServletResponse response) throws DataException {
        List<String> nomiCollezioni;
        //keyword inserita dall'utente
        String keyword = request.getParameter("keyword");
        //datalayer
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        //ottengo il DAO dei dischi
        CollezioneDAO collezioneDAO = dataLayer.getCollezioneDAO();
        //ottengo l'utente tramite la sua id nella sessione
        nomiCollezioni = collezioneDAO.getCollezioniByKeyword(keyword);

        return nomiCollezioni;
    }
}
