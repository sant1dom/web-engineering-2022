package org.webeng.impl;

import org.webeng.framework.data.DataItemImpl;
import org.webeng.model.*;

import java.util.List;

public class DiscoImpl extends DataItemImpl<Integer> implements Disco {
    private String titolo;
    private String anno;
    private String etichetta;
    private String barcode;
    private Genere genere;
    private StatoConservazione statoConservazione;
    private Formato formato;
    private Utente utente;
    private List<Autore> autori;
    private List<Image> immagini;
    private List<Traccia> tracce;

    public DiscoImpl() {
        super();
        this.titolo = "";
        this.anno = "";
        this.etichetta = "";
        this.barcode = "";
        this.genere = null;
        this.statoConservazione = null;
        this.formato = null;
        this.utente = null;
        this.autori = null;
        this.immagini = null;
        this.tracce = null;
    }

    public DiscoImpl(String titolo, String anno, String etichetta, String barcode, Genere genere, StatoConservazione statoConservazione, Formato formato, Utente utente, List<Autore> autori, List<Image> immagini, List<Traccia> tracce) {
        super();
        this.titolo = titolo;
        this.anno = anno;
        this.etichetta = etichetta;
        this.barcode = barcode;
        this.genere = genere;
        this.statoConservazione = statoConservazione;
        this.formato = formato;
        this.utente = utente;
        this.autori = autori;
        this.immagini = immagini;
        this.tracce = tracce;
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
    public String getAnno() {
        return anno;
    }

    @Override
    public void setAnno(String anno) {
        this.anno = anno;
    }

    @Override
    public String getEtichetta() {
        return etichetta;
    }

    @Override
    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }

    @Override
    public String getBarCode() {
        return barcode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.barcode = barCode;
    }


    @Override
    public Genere getGenere() {
        return genere;
    }

    @Override
    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    @Override
    public StatoConservazione getStatoConservazione() {
        return statoConservazione;
    }

    @Override
    public void setStatoConservazione(StatoConservazione statoConservazione) {
        this.statoConservazione = statoConservazione;
    }

    @Override
    public Formato getFormato() {
        return formato;
    }

    @Override
    public void setFormato(Formato formato) {
        this.formato = formato;
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

    @Override
    public void removeAutore(Autore autore) {
        this.autori.remove(autore);
    }

    @Override
    public List<Image> getImmagini() {
        return immagini;
    }

    @Override
    public void setImmagini(List<Image> immagini) {
        this.immagini = immagini;
    }

    @Override
    public void addImmagine(Image immagine) {
        this.immagini.add(immagine);
    }

    @Override
    public void removeImmagine(Image immagine) {
        this.immagini.remove(immagine);
    }

    @Override
    public List<Traccia> getTracce() {
        return tracce;
    }

    @Override
    public void setTracce(List<Traccia> tracce) {
        this.tracce = tracce;
    }

    @Override
    public void addTraccia(Traccia traccia) {
        this.tracce.add(traccia);
    }

    @Override
    public void removeTraccia(Traccia traccia) {
        this.tracce.remove(traccia);
    }
}
