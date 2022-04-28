package org.webeng.data.dao;

import org.webeng.data.model.Autore;
import org.webeng.data.model.Disco;
import org.webeng.data.model.TipologiaAutore;
import org.webeng.data.model.Traccia;
import org.webeng.data.proxy.AutoreProxy;
import org.webeng.framework.data.DAO;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutoreDAO_MySQL extends DAO implements AutoreDAO {
    private PreparedStatement sAutori, sAutoreByID, sAutoreByNomeArtistico, sAutoriByDisco, sAutoriByTraccia;

    public AutoreDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate nella classe
            //precompile all the queries uses in this class
            sAutoreByID = connection.prepareStatement("SELECT * FROM autore WHERE id=?");
            sAutori = connection.prepareStatement("SELECT id FROM autore");
            sAutoreByNomeArtistico = connection.prepareStatement("SELECT * from autore WHERE nome_artistico=?");
            sAutoriByDisco = connection.prepareStatement(""); //TODO: necessario inserimento query
            sAutoriByTraccia = connection.prepareStatement(""); //TODO: necessario inserimento query
        } catch (SQLException ex) {
            throw new DataException("Error initializing authors data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent è una buona pratica...
        try {
            sAutoreByID.close();
            sAutori.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }


    @Override
    public Autore createAutore() throws DataException {
        return new AutoreProxy(getDataLayer());
    }

    private AutoreProxy createAutore(ResultSet rs) throws DataException {
        try {
            AutoreProxy a = (AutoreProxy)createAutore();
            a.setKey(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setCognome(rs.getString("cognome"));
            a.setNomeArtistico(rs.getString("nome_artistico"));
            a.setTipologia(TipologiaAutore.valueOf(rs.getString("tipologia")));
            a.setVersion(rs.getLong("version"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create author object form ResultSet", ex);
        }
    }
    @Override
    public Autore getAutore(String nomeArtistico) throws DataException {
        Autore a = null;
        try {
            sAutoreByNomeArtistico.setString(1, nomeArtistico);
            try (ResultSet rs = sAutoreByID.executeQuery()) {
                if (rs.next()) {
                    a = createAutore(rs);
                    dataLayer.getCache().add(Autore.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load author by ID", ex);
        }
        return a;
    }

    @Override
    public Autore getAutore(int autore_key) throws DataException {
        Autore a = null;
        //prima vediamo se l'oggetto è già stato caricato
        //first look for this object in the cache
        if (dataLayer.getCache().has(Autore.class, autore_key)) {
            a = dataLayer.getCache().get(Autore.class, autore_key);
        } else {
            //altrimenti lo carichiamo dal database
            //otherwise load it from database
            try {
                sAutoreByID.setInt(1, autore_key);
                try (ResultSet rs = sAutoreByID.executeQuery()) {
                    if (rs.next()) {
                        a = createAutore(rs);
                        dataLayer.getCache().add(Autore.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load author by ID", ex);
            }
        }
        return a;
    }

    @Override
    public void storeAutore(Autore autore) throws DataException {

    }

    @Override
    public List<Autore> getAutori() throws DataException {
        List<Autore> result = new ArrayList<>();
        try (ResultSet rs = sAutori.executeQuery()) {
            while (rs.next()) {
                result.add(getAutore(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load authors", ex);
        }
        return result;
    }

    @Override
    public List<Autore> getAutori(Disco disco) throws DataException {
        List<Autore> result = new ArrayList<>();
        try {
            sAutoriByDisco.setInt(1, disco.getKey());
            try (ResultSet rs = sAutoriByDisco.executeQuery()) {
                while (rs.next()) {
                    result.add(getAutore(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load authors by disco", ex);
        }
        return result;
    }

    @Override
    public List<Autore> getAutori(Traccia traccia) throws DataException {
        List<Autore> result = new ArrayList<>();
        try {
            sAutoriByTraccia.setInt(1, traccia.getKey());
            try (ResultSet rs = sAutoriByTraccia.executeQuery()) {
                while (rs.next()) {
                    result.add(getAutore(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load authors by traccia", ex);
        }
        return result;
    }
}
