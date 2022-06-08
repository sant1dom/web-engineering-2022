package org.webeng.collector_site.data.proxy;

import org.webeng.collector_site.data.dao.CollezioneDAO;
import org.webeng.collector_site.data.dao.DiscoDAO;
import org.webeng.collector_site.data.impl.UtenteImpl;
import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItemProxy;
import org.webeng.framework.data.DataLayer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteProxy extends UtenteImpl implements DataItemProxy {
    protected boolean modified;

    protected DataLayer dataLayer;

    public UtenteProxy(DataLayer d) {
        super();
        //dependency injection
        this.dataLayer = d;
        this.modified = false;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }


    @Override
    public void setCognome(String cognome) {
        super.setCognome(cognome);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.modified = true;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.modified = true;
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        this.modified = true;
    }

    @Override
    public List<Collezione> getCollezioni() {
        if (super.getCollezioni() == null) {
            try {
                super.setCollezioni(((CollezioneDAO) dataLayer.getDAO(Collezione.class)).getCollezioni(this));
            } catch (DataException ex) {
                Logger.getLogger(UtenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getCollezioni();
    }


    @Override
    public void setCollezioni(List<Collezione> collezioni) {
        super.setCollezioni(collezioni);
        this.modified = true;
    }

    @Override
    public void removeCollezione(Collezione collezione) {
        super.removeCollezione(collezione);
        this.modified = true;
    }

    @Override
    public List<Disco> getDischi() {
        if (super.getDischi() == null) {
            try {
                super.setDischi(((DiscoDAO) dataLayer.getDAO(Disco.class)).getDischi(this));
            } catch (DataException ex) {
                Logger.getLogger(UtenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getDischi();
    }

    @Override
    public void setDischi(List<Disco> dischi) {
        super.setDischi(dischi);
        this.modified = true;
    }

    @Override
    public void addDisco(Disco disco) {
        super.addDisco(disco);
        this.modified = true;
    }

    @Override
    public void removeDisco(Disco disco) {
        super.removeDisco(disco);
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

    @Override
    public String toString(){
        return super.toString();
    }
}
