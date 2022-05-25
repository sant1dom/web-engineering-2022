package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface TracciaDAO {
    Traccia getTraccia(String iswc) throws DataException;

    Traccia createTraccia();

    Traccia getTraccia(int traccia_key) throws DataException;

    void storeTraccia(Traccia traccia) throws DataException;

    void deleteTraccia(Traccia traccia) throws DataException;

    List<Traccia> getTracce() throws DataException;

    List<Traccia> getTracce(Disco disco) throws DataException;

    List<Traccia> getTracce(Autore autore) throws DataException;

    List<Traccia> getFigli(Traccia traccia) throws DataException;

    Traccia getPadre(Traccia traccia) throws DataException;

    void addTracce(Disco disco, List<Traccia> tracce) throws DataException;

    void addTraccia(Disco disco, Traccia traccia) throws DataException;

    void addTracce(Autore autore, List<Traccia> tracce) throws DataException;

    void addTraccia(Autore autore, Traccia traccia) throws DataException;
}
