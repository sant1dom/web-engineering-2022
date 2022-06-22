package org.webeng.collector_site.data.dao;

import org.webeng.framework.data.DataException;

import java.util.List;

public interface StatsDAO {

    int getDischiPerGenere(String genere) throws DataException;

    int getDischiPerAnno(int anno) throws DataException;

    int getDischiPerAutore(String nomeArtistico) throws DataException;

    int getDischiPerEtichetta(String etichetta) throws DataException;

    List<Integer> getTotalStats() throws DataException;

    int getNumeroCollezioniPrivateUtente(int utenteId) throws DataException;

    int getNumeroCollezioniTotaliUtente(int utenteId) throws DataException;
}
