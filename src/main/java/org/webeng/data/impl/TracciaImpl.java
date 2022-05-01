package org.webeng.data.impl;

import org.webeng.data.model.Autore;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Traccia;
import org.webeng.framework.data.DataItemImpl;

import java.time.LocalDate;
import java.util.List;

public class TracciaImpl extends DataItemImpl<Integer> implements Traccia {
    private String titolo;
    private int durata;
    private String iswc;
    private LocalDate dataInserimento;
    private List<Autore> autori;
    private List<Traccia> figli;
    private List<Disco> dischi;

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
    public LocalDate getDataInserimento() {
        return this.dataInserimento;
    }

    @Override
    public void setDataInserimento(LocalDate dataInserimento) {
        this.dataInserimento = dataInserimento;
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
        if (this.autori == null) {
            this.autori = new java.util.ArrayList<>();
        }
        this.autori.add(autore);
    }

    @Override
    public void removeAutore(Autore autore) {
        if (this.autori != null) {
            this.autori.remove(autore);
        }
    }

    @Override
    public List<Traccia> getFigli() {
        return this.figli;
    }

    @Override
    public void setFigli(List<Traccia> figli) {
        this.figli = figli;
    }

    @Override
    public void addFiglio(Traccia figlio) {
        if (this.figli == null) {
            this.figli = new java.util.ArrayList<>();
        }
        this.figli.add(figlio);
    }

    @Override
    public void removeFiglio(Traccia figlio) {
        if (this.figli != null) {
            this.figli.remove(figlio);
        }
    }

    @Override
    public List<Disco> getDischi() {
        return this.dischi;
    }

    @Override
    public void setDischi(List<Disco> dischi) {
        this.dischi = dischi;
    }
}
