package org.webeng.data.dao;

import org.webeng.data.model.Autore;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Traccia;
import org.webeng.data.proxy.TracciaProxy;
import org.webeng.framework.data.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TracciaDAO_MySQL extends DAO implements TracciaDAO {
    private PreparedStatement sTracciaByID;
    private PreparedStatement sTracciaByISWC;
    private PreparedStatement sTracce;
    private PreparedStatement sTracceByDisco;
    private PreparedStatement sTracceByAutore;
    private PreparedStatement sFigliTraccia;
    private PreparedStatement sPadreTraccia;
    private PreparedStatement iTraccia;
    private PreparedStatement uTraccia;
    private PreparedStatement dTraccia;
    private PreparedStatement addTracciaAutore;
    private PreparedStatement addTracciaDisco;
    public TracciaDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sTracciaByID = connection.prepareStatement("SELECT * FROM traccia WHERE id = ?");
            sTracciaByISWC = connection.prepareStatement("SELECT * FROM traccia WHERE iswc = ?");
            sTracce = connection.prepareStatement("SELECT id FROM traccia");
            sTracceByDisco = connection.prepareStatement("SELECT t.id FROM traccia t JOIN disco_traccia dt ON t.id = dt.traccia_id JOIN disco d ON d.id = dt.disco_id WHERE d.id = ?");
            sTracceByAutore = connection.prepareStatement("SELECT t.id FROM traccia t JOIN traccia_autore ta ON t.id = ta.traccia_id JOIN autore a ON a.id = ta.autore_id WHERE a.id = ?");
            sFigliTraccia = connection.prepareStatement("SELECT t.id FROM traccia t WHERE t.padre = ?");
            sPadreTraccia = connection.prepareStatement("SELECT padre FROM traccia WHERE id = ?");
            iTraccia = connection.prepareStatement("INSERT INTO traccia (titolo, iswc, data_inserimento, durata, padre, version) VALUES (?,?,?,?,?,?)");
            uTraccia = connection.prepareStatement("UPDATE traccia SET titolo = ?, iswc = ?, durata = ?, padre = ?, version = ? WHERE id = ? AND version = ?");
            dTraccia = connection.prepareStatement("DELETE FROM traccia WHERE id = ? AND version = ?");
            addTracciaAutore = connection.prepareStatement("INSERT INTO traccia_autore (traccia_id, autore_id) VALUES (?,?)");
            addTracciaDisco = connection.prepareStatement("INSERT INTO disco_traccia (disco_id, traccia_id) VALUES (?, ?)");
        } catch (SQLException ex) {
            throw new DataException("Error initializing tracks data layer",ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sTracciaByID.close();
            sTracciaByISWC.close();
            sTracce.close();
            sTracceByDisco.close();
            sTracceByAutore.close();
            sFigliTraccia.close();
            sPadreTraccia.close();
            iTraccia.close();
            uTraccia.close();
            dTraccia.close();
            addTracciaAutore.close();
            addTracciaDisco.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing tracks data layer",ex);
        }
    }

    @Override
    public Traccia createTraccia(){
        return new TracciaProxy(getDataLayer());
    }

    private TracciaProxy createTraccia(ResultSet rs) throws DataException {
        TracciaProxy t = new TracciaProxy(getDataLayer());
        try {
            t.setKey(rs.getInt("id"));
            t.setTitolo(rs.getString("titolo"));
            t.setISWC(rs.getString("iswc"));
            t.setDataInserimento(LocalDate.parse(rs.getString("data_inserimento")));
            t.setDurata(rs.getInt("durata"));
            t.setVersion(rs.getInt("version"));
            t.setPadre(getTraccia(rs.getInt("padre")));
            return t;
        } catch (SQLException ex) {
            throw new DataException("Error creating track",ex);
        }
    }
    @Override
    public Traccia getTraccia(int traccia_key) throws DataException {
        Traccia t = null;
        if (dataLayer.getCache().has(Traccia.class, traccia_key)) {
            t = dataLayer.getCache().get(Traccia.class, traccia_key);
        } else {
            try {
                sTracciaByID.setInt(1, traccia_key);
                ResultSet rs = sTracciaByID.executeQuery();
                if(rs.next()){
                    t = createTraccia(rs);
                    dataLayer.getCache().add(Traccia.class, t);
                }
            } catch (SQLException ex) {
                throw new DataException("Error getting track by id",ex);
            }
        }
        return t;
    }

    @Override
    public Traccia getTraccia(String iswc) throws DataException {
        Traccia t = null;
        try {
            sTracciaByISWC.setString(1, iswc);
            ResultSet rs = sTracciaByISWC.executeQuery();
            if(rs.next()){
                t = createTraccia(rs);
                dataLayer.getCache().add(Traccia.class, t);
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting track by id",ex);
        }
        return t;
    }


    @Override
    public void storeTraccia(Traccia traccia) throws DataException {
        try {
            if (traccia.getKey() != null && traccia.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (traccia instanceof DataItemProxy && !((DataItemProxy) traccia).isModified()) {
                    return;
                }
                uTraccia.setString(1, traccia.getTitolo());
                uTraccia.setString(2, traccia.getISWC());
                uTraccia.setInt(3, traccia.getDurata());
                uTraccia.setInt(4, traccia.getPadre().getKey());

                long current_version = traccia.getVersion();
                long next_version = current_version + 1;

                uTraccia.setLong(5, next_version);
                uTraccia.setInt(6, traccia.getKey());
                uTraccia.setLong(7, current_version);

                if (uTraccia.executeUpdate() == 0) {
                    throw new OptimisticLockException(traccia);
                } else {
                    traccia.setVersion(next_version);
                }
            } else { //insert
                uTraccia.setString(1, traccia.getTitolo());
                uTraccia.setString(2, traccia.getISWC());
                uTraccia.setInt(3, traccia.getDurata());
                uTraccia.setInt(4, traccia.getPadre().getKey());

                if (iTraccia.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iTraccia.getGeneratedKeys()) {
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
                            traccia.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Traccia.class, traccia);
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
            if (traccia instanceof DataItemProxy) {
                ((DataItemProxy) traccia).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store traccia", ex);
        }
    }

    @Override
    public void deleteTraccia(Traccia traccia) throws DataException {
        try {
            dTraccia.setInt(1, traccia.getKey());
            if (dTraccia.executeUpdate() == 1) {
                //rimuoviamo l'oggetto dalla cache
                //remove the object from the cache
                dataLayer.getCache().delete(Traccia.class, traccia.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to delete traccia", ex);
        }
    }

    @Override
    public List<Traccia> getTracce() throws DataException {
        List<Traccia> tracce = new ArrayList<>();
        try {
            ResultSet rs = sTracce.executeQuery();
            while(rs.next()){
                tracce.add(getTraccia(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting tracks",ex);
        }
        return tracce;
    }

    @Override
    public List<Traccia> getTracce(Disco disco) throws DataException {
        List<Traccia> tracce = new ArrayList<>();
        try {
            sTracceByDisco.setInt(1, disco.getKey());
            ResultSet rs = sTracce.executeQuery();
            while(rs.next()){
                tracce.add(getTraccia(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting tracks by disk",ex);
        }
        return tracce;
    }

    @Override
    public List<Traccia> getTracce(Autore autore) throws DataException {
        List<Traccia> tracce = new ArrayList<>();
        try {
            sTracceByAutore.setInt(1, autore.getKey());
            ResultSet rs = sTracce.executeQuery();
            while(rs.next()){
                tracce.add(getTraccia(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting tracks by author",ex);
        }
        return tracce;
    }

    @Override
    public List<Traccia> getFigli(Traccia traccia) throws DataException {
        List<Traccia> figli = new ArrayList<>();
        try {
            sFigliTraccia.setInt(1, traccia.getKey());
            ResultSet rs = sFigliTraccia.executeQuery();
            while(rs.next()){
                figli.add(getTraccia(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting track's children",ex);
        }
        return figli;
    }

    @Override
    public Traccia getPadre(Traccia traccia) throws DataException {
        Traccia padre = null;
        try {
            sPadreTraccia.setInt(1, traccia.getKey());
            ResultSet rs = sPadreTraccia.executeQuery();
            if(rs.next()){
                padre = getTraccia(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting track's parent",ex);
        }
        return padre;
    }

    @Override
    public void addTracce(Disco disco, List<Traccia> tracce) throws DataException {
        for (Traccia traccia : tracce) {
            addTraccia(disco, traccia);
        }
    }

    @Override
    public void addTraccia(Disco disco, Traccia traccia) throws DataException {
        try {
            addTracciaDisco.setInt(1, disco.getKey());
            addTracciaDisco.setInt(2, traccia.getKey());
            addTracciaDisco.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Error setting track", ex);
        }
    }

    @Override
    public void addTracce(Autore autore, List<Traccia> tracce) throws DataException {
        for (Traccia traccia : tracce) {
            addTraccia(autore, traccia);
        }
    }

    @Override
    public void addTraccia(Autore autore, Traccia traccia) throws DataException {
        try {
            addTracciaAutore.setInt(1, autore.getKey());
            addTracciaAutore.setInt(2, traccia.getKey());
            addTracciaAutore.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Error setting track", ex);
        }
    }
}
