package org.webeng.model;

import org.webeng.framework.data.DataItem;

import java.util.List;

public interface Utente extends DataItem<Integer> {
    String getNome();

    void setNome(String nome);

    String getCognome();

    void setCognome(String cognome);

    String getEmail();

    void setEmail(String email);

    String getUsername();

    void setUsername(String username);

    void setPassword(String password);

    List<Collezione> getCollezioni();

    void setCollezioni(List<Collezione> collezioni);

    void addCollezione(Collezione collezione);

    void removeCollezione(Collezione collezione);

    List<Disco> getDischi();

    void setDischi(List<Disco> dischi);

    void addDisco(Disco disco);

    void removeDisco(Disco disco);
}
