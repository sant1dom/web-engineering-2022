package org.webeng.data.model;

import org.webeng.framework.data.DataItem;

import java.time.LocalDate;
import java.util.List;

public interface Disco extends DataItem<Integer> {
    String getTitolo();

    void setTitolo(String titolo);

    List<Autore> getAutori();

    void setAutori(List<Autore> autori);

    void addAutore(Autore autore);

    void removeAutore(Autore autore);

    String getAnno();

    void setAnno(String anno);

    Genere getGenere();

    void setGenere(Genere genere);

    String getEtichetta();

    void setEtichetta(String etichetta);

    String getBarCode();

    void setBarCode(String barCode);

    Utente getUtente();

    void setUtente(Utente utente);

    List<Image> getImmagini();

    void setImmagini(List<Image> immagini);

    void addImmagine(Image immagine);

    void removeImmagine(Image immagine);

    List<Traccia> getTracce();

    void setTracce(List<Traccia> tracce);

    void addTraccia(Traccia traccia);

    void removeTraccia(Traccia traccia);

    StatoConservazione getStatoConservazione();

    void setStatoConservazione(StatoConservazione statoConservazione);

    Formato getFormato();

    void setFormato(Formato formato);

    LocalDate getDataInserimento();

    void setDataInserimento(LocalDate dataInserimento);

    Disco getPadre();

    void setPadre(Disco disco);

    List<Disco> getFigli();

    void setFigli(List<Disco> figli);

    void addFiglio(Disco disco);

    void removeFiglio(Disco disco);
}
