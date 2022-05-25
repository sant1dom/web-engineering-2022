package org.webeng.collector_site.data.proxy;

import org.webeng.collector_site.data.dao.*;
import org.webeng.collector_site.data.impl.DiscoImpl;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItemProxy;
import org.webeng.framework.data.DataLayer;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoProxy extends DiscoImpl implements DataItemProxy {
    protected boolean modified;
    protected int utente_key;
    protected int padre_key;

    protected DataLayer dataLayer;

    public DiscoProxy(DataLayer d) {
        super();
        //dependency injection
        this.dataLayer = d;
        this.utente_key = 0;
        this.padre_key = 0;
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
    public void setDataInserimento(LocalDate dataInserimento) {
        super.setDataInserimento(dataInserimento);
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
    public Utente getUtente() {
        if (super.getUtente() == null && utente_key > 0) {
            try {
                super.setUtente(((UtenteDAO) dataLayer.getDAO(Utente.class)).getUtente(utente_key));
            } catch (DataException ex) {
                Logger.getLogger(UtenteProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getUtente();
    }

    @Override
    public void setUtente(Utente utente) {
        super.setUtente(utente);
        this.utente_key = utente.getKey();
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

    public void setUtenteKey(int utente_key) {
        this.utente_key = utente_key;
        super.setUtente(null);
    }

    public void setPadreKey(int padre_key) {
        this.padre_key = padre_key;
        super.setPadre(null);
    }
}
