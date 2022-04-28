package org.webeng.data.impl;

import org.webeng.data.model.Autore;
import org.webeng.data.model.Disco;
import org.webeng.data.model.TipologiaAutore;
import org.webeng.data.model.Traccia;
import org.webeng.framework.data.DataItemImpl;

import java.util.List;

public class AutoreImpl extends DataItemImpl<Integer> implements Autore {
    private String nome;
    private String cognome;
    private String nomeArtistico;
    private TipologiaAutore tipologiaAutore;

    public AutoreImpl(){
        super();
        this.nome = "";
        this.cognome = "";
        this.nomeArtistico = "";
        this.tipologiaAutore = null;
    }

    public AutoreImpl(String nome, String cognome, String nomeArtistico, TipologiaAutore tipologiaAutore){
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

}
