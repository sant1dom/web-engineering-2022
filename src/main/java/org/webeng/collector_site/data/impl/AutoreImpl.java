package org.webeng.collector_site.data.impl;

import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.TipologiaAutore;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.framework.data.DataItemImpl;

import java.util.List;

public class AutoreImpl extends DataItemImpl<Integer> implements Autore {
    private String nome;
    private String cognome;
    private String nomeArtistico;
    private TipologiaAutore tipologiaAutore;
    private List<Disco> dischi;
    private List<Traccia> tracce;

    public AutoreImpl(){
        super();
        this.nome = "";
        this.cognome = "";
        this.nomeArtistico = "";
        this.tipologiaAutore = null;
    }

    public AutoreImpl(String nome, String cognome, String nomeArtistico, TipologiaAutore tipologiaAutore) {
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.nomeArtistico = nomeArtistico;
        this.tipologiaAutore = tipologiaAutore;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String getNomeArtistico() {
        return nomeArtistico;
    }

    @Override
    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    @Override
    public TipologiaAutore getTipologia() {
        return tipologiaAutore;
    }

    @Override
    public void setTipologia(TipologiaAutore tipologiaAutore) {
        this.tipologiaAutore = tipologiaAutore;
    }

    @Override
    public List<Disco> getDischi() {
        return this.dischi;
    }

    @Override
    public void setDischi(List<Disco> dischi) {
        this.dischi = dischi;
    }

    @Override
    public List<Traccia> getTracce() {
        return this.tracce;
    }

    @Override
    public void setTracce(List<Traccia> tracce) {
        this.tracce = tracce;
    }

}
