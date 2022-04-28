package org.webeng.data.dao;

import org.webeng.data.model.Autore;
import org.webeng.data.model.Collezione;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Utente;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface DiscoDAO {
    Disco getDisco(String barcode) throws DataException;

    Disco getDisco(int disco_key) throws DataException;

    void storeDisco(Disco disco) throws DataException;

    List<Disco> getDischi() throws DataException;

    List<Disco> getDischi(Collezione collezione) throws DataException;

    List<Disco> getDischi(Autore autore) throws DataException;

    List<Disco> getDischi(Utente utente) throws DataException;

    List<Disco> getFigli() throws DataException;
}
