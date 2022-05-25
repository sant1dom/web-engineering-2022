package org.webeng.collector_site.data.proxy;

import org.webeng.collector_site.data.dao.DiscoDAO;
import org.webeng.collector_site.data.dao.TracciaDAO;
import org.webeng.collector_site.data.impl.AutoreImpl;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.TipologiaAutore;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItemProxy;
import org.webeng.framework.data.DataLayer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoreProxy extends AutoreImpl implements DataItemProxy {
    protected boolean modified;

    protected DataLayer dataLayer;

    public AutoreProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
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
    public void setNomeArtistico(String nomeArtistico) {
        super.setNomeArtistico(nomeArtistico);
        this.modified = true;
    }

    @Override
    public void setTipologia(TipologiaAutore tipologia) {
        super.setTipologia(tipologia);
        this.modified = true;
    }

    @Override
    public List<Disco> getDischi() {
        if(super.getDischi() == null) {
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
    public List<Traccia> getTracce() {
        if(super.getTracce() == null) {
            try {
                super.setTracce(((TracciaDAO) dataLayer.getDAO(Traccia.class)).getTracce(this));
            } catch (DataException ex) {
                Logger.getLogger(UtenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getTracce();
    }

    @Override
    public void setTracce(List<Traccia> tracce) {
        super.setTracce(tracce);
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
}
