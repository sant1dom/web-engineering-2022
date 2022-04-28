package org.webeng.data.dao;

import org.webeng.data.model.Autore;
import org.webeng.data.model.Collezione;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Traccia;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface AutoreDAO {
    Autore createAutore() throws DataException;

    Autore getAutore(String nomeArtistico) throws DataException;

    Autore getAutore(int autore_key) throws DataException;

    void storeAutore(Autore autore) throws DataException;

    List<Autore> getAutori() throws DataException;

    List<Autore> getAutori(Disco disco) throws DataException;

    List<Autore> getAutori(Traccia traccia) throws DataException;

}
