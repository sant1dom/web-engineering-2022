package org.webeng.data.proxy;

import org.webeng.data.dao.AutoreDAO;
import org.webeng.data.dao.DiscoDAO;
import org.webeng.data.dao.ImageDAO;
import org.webeng.data.dao.TracciaDAO;
import org.webeng.data.impl.DiscoImpl;
import org.webeng.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItemProxy;
import org.webeng.framework.data.DataLayer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoProxy extends DiscoImpl implements DataItemProxy {
    protected boolean modified;

    protected DataLayer dataLayer;

    public DiscoProxy(DataLayer d) {
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
    public void setTitolo(String titolo) {
        super.setTitolo(titolo);
        this.modified = true;
    }

    @Override
    public void setAnno(String anno) {
        super.setAnno(anno);
        this.modified = true;
    }

    @Override
    public void setEtichetta(String etichetta) {
        super.setEtichetta(etichetta);
        this.modified = true;
    }

    @Override
    public void setBarCode(String barCode) {
        super.setBarCode(barCode);
        this.modified = true;
    }

    @Override
    public void setGenere(Genere genere) {
        super.setGenere(genere);
        this.modified = true;
    }

    @Override
    public void setStatoConservazione(StatoConservazione statoConservazione) {
        super.setStatoConservazione(statoConservazione);
        this.modified = true;
    }

    @Override
    public void setFormato(Formato formato) {
        super.setFormato(formato);
        this.modified = true;
    }

    @Override
    public void setPadre(Disco disco) {
        super.setPadre(disco);
        this.modified = true;
    }

    @Override
    public List<Disco> getFigli() {
        if (super.getFigli() == null) {
            try {
                super.setFigli(((DiscoDAO) dataLayer.getDAO(Disco.class)).getFigli(this));
            } catch (DataException ex) {
                Logger.getLogger(UtenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getFigli();
    }



    @Override
    public void setFigli(List<Disco> figli) {
        super.setFigli(figli);
        this.modified = true;
    }

    @Override
    public void addFiglio(Disco disco) {
        super.addFiglio(disco);
        this.modified = true;
    }

    @Override
    public void removeFiglio(Disco disco) {
        super.removeFiglio(disco);
        this.modified = true;
    }

    @Override
    public void setUtente(Utente utente) {
        super.setUtente(utente);
        this.modified = true;
    }

    @Override
    public List<Autore> getAutori() {
        if (super.getAutori() == null) {
            try {
                super.setAutori(((AutoreDAO) dataLayer.getDAO(Autore.class)).getAutori(this));
            } catch (DataException ex) {
                Logger.getLogger(UtenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAutori();
    }

    @Override
    public void setAutori(List<Autore> autori) {
        super.setAutori(autori);
        this.modified = true;
    }

    @Override
    public void addAutore(Autore autore) {
        super.addAutore(autore);
        this.modified = true;
    }

    @Override
    public void removeAutore(Autore autore) {
        super.removeAutore(autore);
        this.modified = true;
    }

    @Override
    public List<Image> getImmagini(){
        if (super.getImmagini() == null) {
            try {
                super.setImmagini(((ImageDAO) dataLayer.getDAO(Image.class)).getImages(this));
            } catch (DataException ex) {
                Logger.getLogger(UtenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getImmagini();
    }
    @Override
    public void setImmagini(List<Image> immagini) {
        super.setImmagini(immagini);
        this.modified = true;
    }

    @Override
    public void addImmagine(Image immagine) {
        super.addImmagine(immagine);
        this.modified = true;
    }

    @Override
    public void removeImmagine(Image immagine) {
        super.removeImmagine(immagine);
        this.modified = true;
    }

    @Override
    public List<Traccia> getTracce() {
        if (super.getTracce() == null) {
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

    @Override
    public void addTraccia(Traccia traccia) {
        super.addTraccia(traccia);
        this.modified = true;
    }

    @Override
    public void removeTraccia(Traccia traccia) {
        super.removeTraccia(traccia);
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
