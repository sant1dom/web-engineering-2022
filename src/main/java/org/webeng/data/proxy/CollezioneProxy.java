package org.webeng.data.proxy;

import org.webeng.data.dao.DiscoDAO;
import org.webeng.data.dao.UtenteDAO;
import org.webeng.data.impl.CollezioneImpl;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Utente;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItemProxy;
import org.webeng.framework.data.DataLayer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollezioneProxy extends CollezioneImpl implements DataItemProxy {
    protected boolean modified;
    protected int utente_key;

    protected DataLayer dataLayer;

    public CollezioneProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
        this.utente_key = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public void setTitolo(String titolo){
        super.setTitolo(titolo);
        this.modified = true;
    }

    @Override
    public void setPrivacy(String privacy){
        super.setPrivacy(privacy);
        this.modified = true;
    }

    @Override
    public Utente getUtente(){
        if(super.getUtente() == null && utente_key > 0) {
            try {
                super.setUtente(((UtenteDAO) dataLayer.getDAO(Utente.class)).getUtente(utente_key));
            } catch (DataException ex) {
                Logger.getLogger(CollezioneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getUtente();
    }

    @Override
    public void setUtente(Utente utente) {
        super.setUtente(utente);
        this.utente_key = utente.getKey();
    }

    @Override
    public void setDataCreazione(LocalDate dataCreazione){
        super.setDataCreazione(dataCreazione);
        this.modified = true;
    }

    @Override
    public List<Disco> getDischi(){
        if(super.getDischi() == null){
            try {
                super.setDischi(((DiscoDAO) dataLayer.getDAO(Disco.class)).getDischi(this));
            } catch (DataException ex) {
                Logger.getLogger(CollezioneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getDischi();
    }

    @Override
    public void setDischi(List<Disco> dischi){
        super.setDischi(dischi);
        this.modified = true;
    }

    @Override
    public List<Utente> getUtentiCondivisi(){
        if(super.getUtentiCondivisi() == null && utente_key > 0) {
            try {
                super.setUtentiCondivisi(((UtenteDAO) dataLayer.getDAO(Utente.class)).getUtentiCondivisi(this));
            } catch (DataException ex) {
                Logger.getLogger(CollezioneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getUtentiCondivisi();
    }

    public void setUtentiCondivisi(List<Utente> utentiCondivisi){
        super.setUtentiCondivisi(utentiCondivisi);
        this.modified = true;
    }

    public void addUtenteCondiviso(Utente utente){
        super.addUtenteCondiviso(utente);
        this.modified = true;
    }

    public  void  removeUtenteCondiviso(Utente utente){
        super.removeUtenteCondiviso(utente);
        this.modified = true;
    }

    //METODI DEL PROXY
    //PROXY-ONLY METHODS
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    public void setDiscoKey(int utente_key) {
        this.utente_key = utente_key;
        //resettiamo la cache dell'autore
        //reset author cache
        super.setUtente(null);
    }
}
