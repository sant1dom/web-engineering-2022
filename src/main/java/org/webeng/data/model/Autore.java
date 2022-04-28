package org.webeng.data.model;

import org.webeng.framework.data.DataItem;

import java.util.List;

public interface Autore extends DataItem<Integer> {
    String getNome();

    void setNome(String nome);

    String getCognome();

    void setCognome(String cognome);

    String getNomeArtistico();

    void setNomeArtistico(String nomeArtistico);

    TipologiaAutore getTipologia();

    void setTipologia(TipologiaAutore tipologia);

    List<Disco> getDischi();

    void setDischi(List<Disco> dischi);

    List<Traccia> getTracce();

    void setTracce(List<Traccia> tracce);

}
