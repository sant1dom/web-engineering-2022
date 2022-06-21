package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface DiscoDAO {
    Disco getDisco(String barcode) throws DataException;

    Disco createDisco();

    Disco getDisco(int disco_key) throws DataException;

    int storeDisco(Disco disco) throws DataException;

    void deleteDisco(Disco disco) throws DataException;
    void deleteDisco(Collezione collezione,Disco disco) throws DataException;

    List<Disco> getDischi() throws DataException;

    List<Disco> getDischi(Collezione collezione) throws DataException;

    List<Disco> getDischi(Autore autore) throws DataException;

    List<Disco> getDischi(Traccia traccia) throws DataException;

    List<Disco> getDischi(Utente utente) throws DataException;

    List<Disco> getFigli(Disco disco) throws DataException;

    Disco getPadre(Disco disco) throws DataException;

    List<Disco> getDischiPadri() throws DataException;

    void addDischi(Collezione collezione, List<Disco> dischi) throws DataException;

    void addDisco(Collezione collezione, Disco disco) throws DataException;

    void addDiscoToCollezioni(List<Collezione> collezioni, Disco disco) throws DataException;


}
