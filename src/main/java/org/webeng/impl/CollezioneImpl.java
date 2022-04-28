package org.webeng.impl;

import org.webeng.framework.data.DataItemImpl;
import org.webeng.model.Collezione;
import org.webeng.model.Disco;
import org.webeng.model.Utente;

import java.util.List;

public class CollezioneImpl extends DataItemImpl<Integer> implements Collezione {
    private String titolo;
    private String privacy;
    private Utente utente;
    private List<Disco> dischi;
    private List<Utente> utentiCondivisione;

    public CollezioneImpl() {
        super();
        this.titolo = "";
        this.privacy = "";
        this.utente = null;
        this.dischi = null;
        this.utentiCondivisione = null;
    }

    public CollezioneImpl(String titolo, String privacy, Utente utente, List<Disco> dischi, List<Utente> utentiCondivisione) {
        super();
        this.titolo = titolo;
        this.privacy = privacy;
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
    public List<Disco> getDischi() {
        return dischi;
    }

    @Override
    public void setDischi(List<Disco> dischi) {
        this.dischi = dischi;
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
        this.utentiCondivisione.add(utente);
    }

    @Override
    public void removeUtenteCondiviso(Utente utente) {
        this.utentiCondivisione.remove(utente);
    }

}