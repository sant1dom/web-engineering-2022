package org.webeng.collector_site.data.dao;
import org.webeng.collector_site.data.model.*;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataLayer;

import javax.sql.DataSource;
import java.sql.SQLException;

public class CollectorsDataLayer extends DataLayer {
    public CollectorsDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        //registriamo i nostri dao
        //register our daos
        registerDAO(Autore.class, new AutoreDAO_MySQL(this));
        registerDAO(Utente.class, new UtenteDAO_MySQL(this));
        registerDAO(Collezione.class, new CollezioneDAO_MySQL(this));
        registerDAO(Traccia.class, new TracciaDAO_MySQL(this));
        registerDAO(Disco.class, new DiscoDAO_MySQL(this));
        registerDAO(Image.class, new ImageDAO_MySQL(this));
        registerDAO(Stats.class, new StatsDAO_MySQL(this));
    }

    //helpers
    public AutoreDAO getAutoreDAO() {
        return (AutoreDAO) getDAO(Autore.class);
    }

    public UtenteDAO getUtenteDAO() {
        return (UtenteDAO) getDAO(Utente.class);
    }

    public CollezioneDAO getCollezioneDAO() {
        return (CollezioneDAO) getDAO(Collezione.class);
    }

    public TracciaDAO getTracciaDAO() {
        return (TracciaDAO) getDAO(Traccia.class);
    }

    public DiscoDAO getDiscoDAO() {
        return (DiscoDAO) getDAO(Disco.class);
    }

    public ImageDAO getImageDAO() {
        return (ImageDAO) getDAO(Image.class);
    }

    public StatsDAO getStatsDAO() { return (StatsDAO) getDAO(Stats.class); }
}
