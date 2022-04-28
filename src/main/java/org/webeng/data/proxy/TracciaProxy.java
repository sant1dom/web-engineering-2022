package org.webeng.data.proxy;

import org.webeng.data.dao.AutoreDAO;
import org.webeng.data.impl.TracciaImpl;
import org.webeng.data.model.Autore;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItemProxy;
import org.webeng.framework.data.DataLayer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TracciaProxy extends TracciaImpl implements DataItemProxy {
    protected boolean modified;

    protected DataLayer dataLayer;

    public TracciaProxy(DataLayer d) {
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
    public void setTitolo(String titolo){
        super.setTitolo(titolo);
        this.modified = true;
    }

    @Override
    public void setDurata(int durata){
        super.setDurata(durata);
        this.modified = true;
    }

    @Override
    public void setISWC(String iswc){
        super.setISWC(iswc);
        this.modified = true;
    }

    @Override
    public List<Autore> getAutori(){
        if (super.getAutori() == null) {
            try {
                super.setAutori(((AutoreDAO) dataLayer.getDAO(Autore.class)).getAutori(this));
            } catch (DataException ex) {
                Logger.getLogger(TracciaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAutori();
    }

    @Override
    public void setAutori(List<Autore> autori){
        super.setAutori(autori);
        this.modified = true;
    }

    @Override
    public void addAutore(Autore autore){
        super.addAutore(autore);
        this.modified = true;
    }

    @Override
    public void removeAutore(Autore autore){
        super.removeAutore(autore);
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
