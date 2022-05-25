package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Autore;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.TipologiaAutore;
import org.webeng.collector_site.data.model.Traccia;
import org.webeng.collector_site.data.proxy.AutoreProxy;
import org.webeng.framework.data.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AutoreDAO_MySQL extends DAO implements AutoreDAO {
    private PreparedStatement sAutori, sAutoreByID, sAutoreByNomeArtistico, sAutoriByDisco, sAutoriByTraccia, uAutore, iAutore, dAutore;

    public AutoreDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            //precompiliamo tutte le query utilizzate nella classe
            sAutori = connection.prepareStatement("SELECT * FROM autore");
            sAutoreByID = connection.prepareStatement("SELECT * FROM autore WHERE id = ?");
            sAutoreByNomeArtistico = connection.prepareStatement("SELECT * FROM autore WHERE nome_artistico = ?");
            sAutoriByDisco = connection.prepareStatement("SELECT autore.id FROM autore JOIN disco_autore dha on autore.id = dha.autore_id JOIN disco d on d.id = dha.disco_id WHERE d.id = ?");
            sAutoriByTraccia = connection.prepareStatement("SELECT autore.id FROM autore JOIN traccia_autore tha on autore.id = tha.autore_id JOIN traccia t on t.id = tha.traccia_id WHERE t.id = ?");
            uAutore = connection.prepareStatement("UPDATE autore SET nome = ?, cognome = ?, nome_artistico = ?, tipologia_autore = ?, version = ? WHERE id = ? AND version = ?");
            iAutore = connection.prepareStatement("INSERT INTO autore (nome, cognome, nome_artistico, tipologia_autore) VALUES (?, ?, ?, ?)");
            dAutore = connection.prepareStatement("DELETE FROM autore WHERE id = ?");
        } catch (SQLException ex) {
                throw new DataException("Error initializing authors data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sAutoreByID.close();
            sAutori.close();
            sAutoreByNomeArtistico.close();
            sAutoriByDisco.close();
            sAutoriByTraccia.close();
            uAutore.close();
            iAutore.close();
            dAutore.close();
        } catch (SQLException ex) {
            throw new DataException("Error destroying authors data layer", ex);
        }
        super.destroy();
    }


    @Override
    public Autore createAutore() {
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
    public Autore getAutore(String nomeArtistico) throws DataException {
        Autore a = null;
        try {
            sAutoreByNomeArtistico.setString(1, nomeArtistico);
            try (ResultSet rs = sAutoreByNomeArtistico.executeQuery()) {
                if (rs.next()) {
                    a = createAutore(rs);
                    dataLayer.getCache().add(Autore.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load author by artistic name", ex);
        }
        return a;
    }


    @Override
    public void storeAutore(Autore autore) throws DataException {
        try {
            if (autore.getKey() != null && autore.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (autore instanceof DataItemProxy && !((DataItemProxy) autore).isModified()) {
                    return;
                }
                uAutore.setString(1, autore.getNome());
                uAutore.setString(2, autore.getCognome());
                if (autore.getNomeArtistico() != null) {
                    uAutore.setString(3, autore.getNomeArtistico());
                } else {
                    uAutore.setNull(3, Types.VARCHAR);
                }

                uAutore.setString(4, String.valueOf(autore.getTipologia()));

                long current_version = autore.getVersion();
                long next_version = current_version + 1;

                uAutore.setLong(5, next_version);
                uAutore.setInt(6, autore.getKey());
                uAutore.setLong(7, current_version);

                if (uAutore.executeUpdate() == 0) {
                    throw new OptimisticLockException(autore);
                } else {
                    autore.setVersion(next_version);
                }
            } else { //insert
                iAutore.setString(1, autore.getNome());
                iAutore.setString(2, autore.getCognome());
                if (autore.getNomeArtistico() != null) {
                    iAutore.setString(3, autore.getNomeArtistico());
                } else {
                    iAutore.setNull(3, Types.VARCHAR);
                }
                iAutore.setString(4, String.valueOf(autore.getTipologia()));
                if (iAutore.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iAutore.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            int key = keys.getInt(1);
                            //aggiornaimo la chiave in caso di inserimento
                            //after an insert, uopdate the object key
                            autore.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Autore.class, autore);
                        }
                    }
                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//            //if possible, we return the just-inserted object RELOADED from the
//            //database through our API. In this way, the resulting
//            //object will ambed any data correction performed by
//            //the DBMS
//            if (key > 0) {
//                autore.copyFrom(getAutore(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
            //if we have a proxy, reset its dirty attribute
            if (autore instanceof DataItemProxy) {
                ((DataItemProxy) autore).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store autore", ex);
        }
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

    @Override
    public void deleteAutore(Autore autore) throws DataException {
        try {
            dAutore.setInt(1, autore.getKey());
            if (dAutore.executeUpdate() == 1) {
                //rimuoviamo l'oggetto dalla cache
                //remove the object from the cache
                dataLayer.getCache().delete(Autore.class, autore.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to delete autore", ex);
        }
    }
}
