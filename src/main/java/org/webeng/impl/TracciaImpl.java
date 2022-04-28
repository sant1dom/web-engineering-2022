package org.webeng.impl;

import org.webeng.framework.data.DataItemImpl;
import org.webeng.model.Autore;
import org.webeng.model.Disco;
import org.webeng.model.Traccia;

import java.util.List;

public class TracciaImpl extends DataItemImpl<Integer> implements Traccia {
    private String titolo;
    private int durata;
    private String iswc;
    private List<Autore> autori;

    public TracciaImpl(String titolo, int durata, String iswc, List<Autore> autori) {
        super();
        this.titolo = titolo;
        this.durata = durata;
        this.iswc = iswc;
        this.autori = autori;
    }

    public TracciaImpl() {
        super();
        this.titolo = "";
        this.durata = 0;
        this.iswc = "";
        this.autori = null;
    }

    @Override
    public String getTitolo() {
        return titolo;
    }

    @Override
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public int getDurata() {
        return durata;
    }

    @Override
    public void setDurata(int durata) {
        this.durata = durata;
    }

    @Override
    public String getISWC() {
        return this.iswc;
    }

    @Override
    public void setISWC(String iswc) {
        this.iswc = iswc;
    }


    @Override
    public List<Autore> getAutori() {
        return autori;
    }

    @Override
    public void setAutori(List<Autore> autori) {
        this.autori = autori;
    }

    @Override
    public void addAutore(Autore autore) {
        this.autori.add(autore);
    }

}
