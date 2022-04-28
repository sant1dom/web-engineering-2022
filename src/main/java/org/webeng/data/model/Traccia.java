package org.webeng.data.model;

import org.webeng.framework.data.DataItem;

import java.util.List;

public interface Traccia extends DataItem<Integer> {
    String getTitolo();

    void setTitolo(String titolo);

    int getDurata();

    void setDurata(int durata);

    String getISWC();

    void setISWC(String iswc);

    List<Autore> getAutori();

    void setAutori(List<Autore> autori);

    void addAutore(Autore autore);

}
