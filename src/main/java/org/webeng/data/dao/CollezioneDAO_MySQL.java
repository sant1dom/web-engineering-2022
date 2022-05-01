package org.webeng.data.dao;
import org.webeng.data.model.Collezione;
import org.webeng.data.model.Disco;
import org.webeng.data.model.Utente;
import org.webeng.data.proxy.CollezioneProxy;
import org.webeng.framework.data.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
public class CollezioneDAO_MySQL extends DAO {
    private PreparedStatement sCollezioni,sCollezioneByTitolo,sCollezioneByID,sCollezioniByDisco,sCollezioniByUtente,sUtentiCondivisi,uCollezione,iCollezione,dCollezione;
    private ResultSet rsCollezioni,rsCollezioneByTitolo,rsCollezioneByID,rsCollezioniByDisco,rsCollezioniByUtente,rsUtentiCondivisi
    public CollezioneDAO_MySQL(CollectorsDataLayer collectorsDataLayer) {
        super();
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            //precompiliamo tutte le query utilizzate nella classe
            sCollezioni = connection.prepareStatement("SELECT * FROM collezione");
            sCollezioneByID = connection.prepareStatement("SELECT * FROM collezione WHERE id = ?");
            sCollezioneByTitolo = connection.prepareStatement("SELECT * FROM collezione WHERE titolo = ?");
            sCollezioniByDisco = connection.prepareStatement("SELECT collezione.id FROM collezione JOIN disco_collezione dhc on collezione.id = dhc.collezione_id JOIN disco d on d.id = dhc.disco_id WHERE d.id = ?");
            sCollezioniByUtente = connection.prepareStatement("SELECT collezione.id FROM collezione JOIN utente_collezione uhc on collezione.id = uhc.collezione_id JOIN utente u on u.id = uhc.utente_id WHERE u.id = ?");
            sUtentiCondivisi=connection.prepareStatement("SELECT utente.id FROM utente JOIN utente_collezione chu on utente.id=chu.utente_id JOIN collezione c on c.id=chu.collezione_id WHERE c.id= ?");
            uCollezione = connection.prepareStatement("UPDATE collezione SET titolo = ?, privacy = ?, data_creazione = ?, version = ? WHERE id = ? AND version = ?");
            iCollezione = connection.prepareStatement("INSERT INTO collezione (titolo, privacy, data_creazione) VALUES (?, ?, ?)");
            dCollezione = connection.prepareStatement("DELETE FROM collezione WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing collections data layer", ex);
        }
    }
    @Override
    public void destroy() throws DataException {
        try {
            sCollezioneByID.close();
            sCollezioni.close();
            sCollezioneByTitolo.close();
            sCollezioniByDisco.close();
            sCollezioniByUtente.close();
            uCollezione.close();
            iCollezione.close();
            dCollezione.close();
        } catch (SQLException ex) {
            throw new DataException("Error destroying collections data layer", ex);
        }
        super.destroy();
    }
    @Override
    public Collezione createCollezione() {
        return new CollezioneProxy(getDataLayer());
    }

    private CollezioneProxy createCollezione(ResultSet rs) throws DataException {
        try {
            CollezioneProxy c = (CollezioneProxy)createCollezione();
            c.setKey(rs.getInt("id"));
            c.setTitolo(rs.getString("titolo"));
            c.setPrivacy(rs.getString("privacy"));
            c.setDataCreazione(rs.getString("data_creazione"));
            c.setVersion(rs.getLong("version"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create collection object form ResultSet", ex);
        }
    }

    @Override
    public Collezione getCollezione(int collezione_key) throws DataException {
        Collezione c = null;
        //prima vediamo se l'oggetto è già stato caricato
        //first look for this object in the cache
        if (dataLayer.getCache().has(Collezione.class, collezione_key)) {
            c = dataLayer.getCache().get(Collezione.class, collezione_key);
        } else {
            //altrimenti lo carichiamo dal database
            //otherwise load it from database
            try {
                sCollezioneByID.setInt(1, collezione_key);
                try (ResultSet rs = sCollezioneByID.executeQuery()) {
                    if (rs.next()) {
                        c = createCollezione(rs);
                        dataLayer.getCache().add(Collezione.class, c);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load collection by ID", ex);
            }
        }
        return c;
    }

    @Override
    public Collezione getCollezione(String titolo) throws DataException {
       Collezione c = null;
        try {
            sCollezioneByTitolo.setString(1, titolo);
            try (ResultSet rs = sCollezioneByTitolo.executeQuery()) {
                if (rs.next()) {
                    a = createCollezione(rs);
                    dataLayer.getCache().add(Collezione.class, c);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collection by title", ex);
        }
        return c;
    }


    @Override
    public void storeCollezione(Collezione collezione) throws DataException {
        try {
            if (collezione.getKey() != null && collezione.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (collezione instanceof DataItemProxy && !((DataItemProxy) collezione).isModified()) {
                    return;
                }
                uCollezione.setString(1, collezione.getTitolo());
                uCollezione.setString(2, collezione.getPrivacy());

                uCollezione.setDate(3,java.sql.Date.valueOf(collezione.getDataCreazione()));

                long current_version = collezione.getVersion();
                long next_version = current_version + 1;

                uCollezione.setLong(5, next_version);
                uCollezione.setInt(6, autore.getKey());
                uCollezione.setLong(7, current_version);

                if (uCollezione.executeUpdate() == 0) {
                    throw new OptimisticLockException(collezione);
                } else {
                    collezione.setVersion(next_version);
                }
            } else { //insert
                iCollezione.setString(1, collezione.getTitolo());
                iCollezione.setString(2,collezione.getPrivacy());
                iCollezione.setDate(3,java.sql.Date.valueOf(collezione.getDataCreazione()));

                if (iCollezione.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iCollezione.getGeneratedKeys()) {
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
                            collezione.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Collezione.class, collezione);
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
            if (collezione instanceof DataItemProxy) {
                ((DataItemProxy) collezione).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store collezione", ex);
        }
    }

    @Override
    public List<Autore> getCollezioni() throws DataException {
        List<Collezione> result = new ArrayList<>();
        try (ResultSet rs = sCollezioni.executeQuery()) {
            while (rs.next()) {
                result.add(getCollezione(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections", ex);
        }
        return result;
    }

    @Override
    public List<Autore> getCollezioni(Disco disco) throws DataException {
        List<Collezione> result = new ArrayList<>();
        try {
            sCollezioniByDisco.setInt(1, disco.getKey());
            try (ResultSet rs = sCollezioniByDisco.executeQuery()) {
                while (rs.next()) {
                    result.add(getCollezione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections by disco", ex);
        }
        return result;
    }

    @Override
    public List<Autore> getCollezioni(Utente utente) throws DataException {
        List<Collezione> result = new ArrayList<>();
        try {
            sCollezioniByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = sCollezioneByTitolo.executeQuery()) {
                while (rs.next()) {
                    result.add(getCollezione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections by utente", ex);
        }
        return result;
    }


    @Override
    public List<Utente> getUtentiCondivisi(Collezione collezione) throws DataException {
        List<Utente> result = new ArrayList<>();
        try {
            sUtentiCondivisi.setInt(1, collezione.getKey());
            try (ResultSet rs = sUtentiCondivisi.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load utenti condivisi by collection", ex);
        }
        return result;
    }

    @Override
    public void deleteAutore(Collezione collezione) throws DataException {
        try {
            dCollezione.setInt(1, collezione.getKey());
            dCollezione.execute();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete collezione", ex);
        }
    }



}
}
