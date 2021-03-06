package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface CollezioneDAO {
    Collezione createCollezione();

    Collezione getCollezione(int id) throws DataException;

    Collezione getCollezione(String titolo) throws DataException;

    void storeCollezione(Collezione collezione) throws DataException;
    void addDiscoCollezione(Collezione collezione,Disco disco) throws DataException;
    void addDisco(Collezione collezione, Disco disco)throws DataException;
    List<Collezione> getCollezioni() throws DataException;

    List<Collezione> getCollezioniByKeyword(String keyword) throws DataException;

    List<Collezione> getCollezioniByKeywordLogged(String keyword, Utente utente) throws DataException;

    List<Collezione> getCollezioni(Disco disco) throws DataException;

    List<Collezione> getCollezioni(Utente utente) throws DataException;

     List<Collezione> getCollezioniCondivise(Utente utente) throws DataException;
     List<Integer> getUtentiAttivi() throws DataException;

     List<Collezione> getCollezioniPubbliche(Utente utente) throws DataException;

    void deleteCollezione(Collezione collezione) throws DataException;
    void addUtentiCondivisi(Collezione collezione) throws DataException;
}
