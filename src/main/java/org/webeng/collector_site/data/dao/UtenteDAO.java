package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface UtenteDAO {

    Utente doLogin(String email, String password) throws DataException;

    Utente createUtente() throws DataException;

    Utente getUtente(String username) throws DataException;

    Utente getUtente(int utente_key) throws DataException;

    void storeUtente(Utente utente) throws DataException;

    List<Utente> getUtenti() throws DataException;

    public List<String> getUtenti(String keyword) throws DataException;

    List<Utente> getUtenti(Collezione collezione) throws DataException;

    Utente getUtente(Disco disco) throws DataException;

    void deleteUtente(Utente utente) throws DataException;

    void addUtentiCondivisi(List<Utente> utente, Collezione collezione) throws DataException;

    void addUtenteCondiviso(Utente utente, Collezione collezione) throws DataException;
}
