package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface AutoreDAO {
    Autore createAutore() throws DataException;

    Autore getAutore(String nomeArtistico) throws DataException;

    Autore getAutore(int autore_key) throws DataException;

    void storeAutore(Autore autore) throws DataException;

    List<Autore> getAutori() throws DataException;

    List<Autore> getAutoriByKeyword(String keyword) throws DataException;
    List<Autore> getAutori(Disco disco) throws DataException;

    List<Autore> getAutori(Traccia traccia) throws DataException;

    void deleteAutore(Autore autore) throws DataException;
}
