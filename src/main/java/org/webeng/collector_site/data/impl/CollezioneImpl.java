package org.webeng.collector_site.data.impl;

import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.framework.data.DataItemImpl;

import java.time.LocalDate;
import java.util.List;

    public class CollezioneImpl extends DataItemImpl<Integer> implements Collezione {
    private String titolo;
    private String privacy;
    private LocalDate dataCreazione;
    private Utente utente;
    private List<Disco> dischi;
    private List<Utente> utentiCondivisione;

    public CollezioneImpl() {
        super();
        this.titolo = "";
        this.privacy = "";
        this.dataCreazione = LocalDate.now();
        this.utente = null;
        this.dischi = null;
        this.utentiCondivisione = null;
    }

    public CollezioneImpl(String titolo, String privacy, Utente utente, LocalDate dataCreazione, List<Disco> dischi, List<Utente> utentiCondivisione) {
        super();
        this.titolo = titolo;
        this.privacy = privacy;
        this.dataCreazione = dataCreazione;
        this.utente = utente;
        this.dischi = dischi;
        this.utentiCondivisione = utentiCondivisione;
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
    public String getPrivacy() {
        return privacy;
    }

    @Override
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    @Override
    public Utente getUtente() {
        return utente;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public LocalDate getDataCreazione() {
        return this.dataCreazione;
    }

    @Override
    public void setDataCreazione(LocalDate dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    @Override
    public List<Disco> getDischi() {
        return dischi;
    }

    @Override
    public void setDischi(List<Disco> dischi) {
        this.dischi = dischi;
    }

    @Override
    public void addDischi(List<Disco> dischi) {
        this.dischi.addAll(dischi);
    }

    @Override
    public List<Utente> getUtentiCondivisi() {
        return this.utentiCondivisione;
    }

    @Override
    public void setUtentiCondivisi(List<Utente> utentiCondivisione) {
        this.utentiCondivisione = utentiCondivisione;
    }

    @Override
    public void addUtenteCondiviso(Utente utente) {
        if (this.utentiCondivisione == null) {
            this.utentiCondivisione = new java.util.ArrayList<>();
        }
        this.utentiCondivisione.add(utente);
    }

    @Override
    public void removeUtenteCondiviso(Utente utente) {
        if (this.utentiCondivisione != null) {
            this.utentiCondivisione.remove(utente);
        }
    }

}