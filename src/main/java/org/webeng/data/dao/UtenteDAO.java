package org.webeng.data.dao;

import org.webeng.data.model.Collezione;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Utente;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface UtenteDAO {
    Utente getUtente(String username) throws DataException;

    Utente getUtente(int utente_key) throws DataException;

    void storeUtente(Utente utente) throws DataException;

    List<Utente> getUtenti() throws DataException;

    List<Utente> getUtenti(Collezione collezione) throws DataException;

    List<Utente> getUtenti(Disco disco) throws DataException;
}
