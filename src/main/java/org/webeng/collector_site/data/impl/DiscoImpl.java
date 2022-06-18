package org.webeng.collector_site.data.impl;

import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataItemImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiscoImpl extends DataItemImpl<Integer> implements Disco {
    private String titolo;
    private String anno;
    private String etichetta;
    private String barcode;
    private Genere genere;
    private StatoConservazione statoConservazione;
    private Formato formato;
    private LocalDate dataInserimento;
    private Utente utente;
    private List<Autore> autori;
    private List<Image> immagini;
    private List<Traccia> tracce;
    private Disco padre;
    private List<Disco> figli;

    public DiscoImpl() {
        super();
        this.titolo = "";
        this.anno = "";
        this.etichetta = "";
        this.barcode = "";
        this.genere = null;
        this.statoConservazione = null;
        this.formato = null;
        this.dataInserimento = null;
        this.utente = null;
        this.autori = null;
        this.immagini = null;
        this.tracce = null;
    }

    public DiscoImpl(String titolo, String anno, String etichetta, String barcode, Genere genere, StatoConservazione statoConservazione, Formato formato, LocalDate dataInserimento,Utente utente, List<Autore> autori, List<Image> immagini, List<Traccia> tracce, Disco padre) {
        super();
        this.titolo = titolo;
        this.anno = anno;
        this.etichetta = etichetta;
        this.barcode = barcode;
        this.genere = genere;
        this.statoConservazione = statoConservazione;
        this.formato = formato;
        this.dataInserimento = dataInserimento;
        this.utente = utente;
        this.autori = autori;
        this.immagini = immagini;
        this.tracce = tracce;
        this.padre= padre;
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
    public LocalDate getDataInserimento() {
        return this.dataInserimento;
    }

    @Override
    public void setDataInserimento(LocalDate dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    @Override
    public Disco getPadre() {
        return this.padre;
    }

    @Override
    public void setPadre(Disco disco) {
        this.padre = disco;
    }

    @Override
    public List<Disco> getFigli() {
        return this.figli;
    }

    @Override
    public void setFigli(List<Disco> figli) {
        this.figli = figli;
    }

    @Override
    public void addFiglio(Disco disco) {
        if (this.figli == null) {
            this.figli = new ArrayList<>();
        }
        this.figli.add(disco);
    }

    @Override
    public void removeFiglio(Disco disco) {
        if (this.figli != null) {
            this.figli.remove(disco);
        }
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
        if (this.autori == null) {
            this.autori = new ArrayList<>();
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
    public List<Image> getImmagini() {
        return immagini;
    }

    @Override
    public void setImmagini(List<Image> immagini) {
        this.immagini = immagini;
    }

    @Override
    public void addImmagine(Image immagine) {
        if (this.immagini == null) {
            this.immagini = new ArrayList<>();
        }
        this.immagini.add(immagine);
    }

    @Override
    public void removeImmagine(Image immagine) {
        if (this.immagini != null) {
            this.immagini.remove(immagine);
        }
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
        if(this.tracce == null) {
            this.tracce = new ArrayList<>();
        }
        this.tracce.add(traccia);
    }

    @Override
    public void removeTraccia(Traccia traccia) {
        if (this.tracce != null) {
            this.tracce.remove(traccia);
        }
    }
}
