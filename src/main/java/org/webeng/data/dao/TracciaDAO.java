package org.webeng.data.dao;

import org.webeng.data.model.Autore;
import org.webeng.data.model.Traccia;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Traccia;
import org.webeng.data.proxy.TracciaProxy;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface TracciaDAO {
    Traccia getTraccia(String iswc) throws DataException;

    Traccia getTraccia(int traccia_key) throws DataException;

    void storeTraccia(Traccia traccia) throws DataException;

    List<Traccia> getTracce() throws DataException;

    List<Traccia> getTracce(Disco disco) throws DataException;

    List<Traccia> getTracce(Autore autore) throws DataException;

    List<Traccia> getFigli(Traccia traccia) throws DataException;
}
