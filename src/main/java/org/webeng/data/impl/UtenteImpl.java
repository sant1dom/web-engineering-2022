package org.webeng.data.impl;

import org.webeng.data.model.Disco;
import org.webeng.framework.data.DataItemImpl;
import org.webeng.data.model.Collezione;
import org.webeng.data.model.Utente;

import java.util.List;

public class UtenteImpl extends DataItemImpl<Integer> implements Utente {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String username;
    private List<Collezione> collezioni;
    private List<Disco> dischi;

    public UtenteImpl(String nome, String cognome, String email, String password, String username, List<Collezione> collezioni, List<Disco> dischi) {
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.username = username;
        this.collezioni = collezioni;
        this.dischi = dischi;
    }

    public UtenteImpl(){
        super();
        this.nome = "";
        this.cognome = "";
        this.email = "";
        this.password = "";
        this.username = "";
        this.collezioni = null;
        this.dischi = null;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public List<Collezione> getCollezioni() {
        return collezioni;
    }

    @Override
    public void setCollezioni(List<Collezione> collezioni) {
        this.collezioni = collezioni;
    }

    @Override
    public void addCollezione(Collezione collezione) {
        this.collezioni.add(collezione);
    }

    @Override
    public void removeCollezione(Collezione collezione) {
        this.collezioni.remove(collezione);
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
    public void addDisco(Disco disco) {
        this.dischi.add(disco);
    }

    @Override
    public void removeDisco(Disco disco) {
        this.dischi.remove(disco);
    }
}
