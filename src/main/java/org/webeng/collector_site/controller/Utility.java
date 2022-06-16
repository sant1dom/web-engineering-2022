package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.dao.UtenteDAO;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Utility {
    private Utility() {
    }
    public static Utente getUtente(HttpServletRequest request, HttpServletResponse response) throws DataException {
        Utente utente = null;
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
        return utente;
    }
}
