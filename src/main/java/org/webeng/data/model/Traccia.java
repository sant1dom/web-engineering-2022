package org.webeng.data.model;

import org.webeng.framework.data.DataItem;

import java.time.LocalDate;
import java.util.List;

public interface Traccia extends DataItem<Integer> {
    String getTitolo();

    void setTitolo(String titolo);

    int getDurata();

    void setDurata(int durata);

    String getISWC();

    void setISWC(String iswc);

    LocalDate getDataInserimento();

    void setDataInserimento(LocalDate dataInserimento);

    List<Autore> getAutori();

    void setAutori(List<Autore> autori);

    void addAutore(Autore autore);

    void removeAutore(Autore autore);

    List<Traccia> getFigli();

    void setFigli(List<Traccia> figli);

    void addFiglio(Traccia figlio);

    void removeFiglio(Traccia figlio);

    List<Disco> getDischi();

    void setDischi(List<Disco> dischi);

    Traccia getPadre();

    void setPadre(Traccia padre);
}
