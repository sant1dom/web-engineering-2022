package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.collector_site.data.proxy.CollezioneProxy;
import org.webeng.framework.data.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollezioneDAO_MySQL extends DAO implements CollezioneDAO {
    private PreparedStatement sCollezioni;
    private PreparedStatement sCollezioneByTitolo;
    private PreparedStatement sCollezioneByID;
    private PreparedStatement sCollezioniByDisco;
    private PreparedStatement sCollezioniByUtente;
    private PreparedStatement sCollezioniCondiviseByUtente;
    private PreparedStatement uCollezione;
    private PreparedStatement iCollezione;
    private PreparedStatement dCollezione;
    private PreparedStatement addDiscoCollezione;
    private PreparedStatement addUtenteCondiviso;
    private PreparedStatement fCollezioniByTitolo;
    private PreparedStatement fCollezioniByTitoloPrivate;
    private PreparedStatement fCollezioniByTitoloCondivise;
    private PreparedStatement sCollezioniByUtentePubbliche;
    private PreparedStatement idUtentiAttivi;

    public CollezioneDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            //precompiliamo tutte le query utilizzate nella classe
            sCollezioni = connection.prepareStatement("SELECT * FROM collezione WHERE privacy = 'PUBBLICO'");
            sCollezioneByID = connection.prepareStatement("SELECT * FROM collezione WHERE id = ?");
            sCollezioneByTitolo = connection.prepareStatement("SELECT * FROM collezione WHERE titolo = ?");
            sCollezioniByDisco = connection.prepareStatement("SELECT collezione.id FROM collezione JOIN collezione_disco dhc ON collezione.id = dhc.collezione_id JOIN disco d ON d.id = dhc.disco_id WHERE d.id = ?");
            sCollezioniByUtente = connection.prepareStatement("SELECT collezione.id FROM collezione WHERE utente_id = ?");
            sCollezioniByUtentePubbliche = connection.prepareStatement("SELECT collezione.id FROM collezione WHERE utente_id = ? AND privacy = 'PUBBLICO'");
            sCollezioniCondiviseByUtente = connection.prepareStatement("SELECT c.id FROM collezione c  JOIN collezione_condivisa_con ccc on c.id = ccc.collezione_id JOIN utente u on u.id = ccc.utente_id WHERE u.id = ?");
            iCollezione = connection.prepareStatement("INSERT INTO collezione (titolo, privacy, data_creazione,version, utente_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uCollezione = connection.prepareStatement("UPDATE collezione SET titolo = ?, privacy = ?, version = ? WHERE id = ? AND version = ?");
            dCollezione = connection.prepareStatement("DELETE FROM collezione WHERE id = ?");
            addDiscoCollezione = connection.prepareStatement("INSERT INTO collezione_disco (collezione_id, disco_id) VALUES (?, ?)");
            addUtenteCondiviso = connection.prepareStatement("INSERT INTO collezione_condivisa_con (collezione_id, utente_id) VALUES (?, ?)");
            idUtentiAttivi = connection.prepareStatement("SELECT utente_id, COUNT(*) AS occorrenza FROM collezione WHERE privacy = 'PUBBLICO' GROUP BY utente_id ORDER BY occorrenza DESC LIMIT 3");
            fCollezioniByTitolo = connection.prepareStatement("SELECT * FROM collezione WHERE privacy = 'PUBBLICO' AND titolo LIKE CONCAT('%', ? , '%')");
            fCollezioniByTitoloPrivate = connection.prepareStatement("SELECT * FROM collezione WHERE privacy = 'PRIVATO' AND utente_id = ? AND titolo LIKE CONCAT('%', ? , '%')");
            fCollezioniByTitoloCondivise = connection.prepareStatement("SELECT * FROM collezione JOIN collezione_condivisa_con ccc on collezione.id = ccc.collezione_id WHERE privacy = 'CONDIVISO' AND ccc.utente_id = ? AND titolo LIKE CONCAT('%', ? , '%')");
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
            sCollezioniCondiviseByUtente.close();
            uCollezione.close();
            iCollezione.close();
            dCollezione.close();
            addUtenteCondiviso.close();
            addDiscoCollezione.close();
            fCollezioniByTitolo.close();
            fCollezioniByTitoloPrivate.close();
            fCollezioniByTitoloCondivise.close();
            idUtentiAttivi.close();
            sCollezioniByUtentePubbliche.close();
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
        CollezioneProxy c = new CollezioneProxy(getDataLayer());
        try {
            c.setKey(rs.getInt("id"));
            c.setTitolo(rs.getString("titolo"));
            c.setPrivacy(rs.getString("privacy"));
            c.setDataCreazione(LocalDate.parse(rs.getString("data_creazione")));
            c.setUtenteKey(rs.getInt("utente_id"));
            c.setVersion(rs.getLong("version"));
            return c;
        } catch (SQLException ex) {
            throw new DataException("Unable to create collection object form ResultSet", ex);
        }
    }

    @Override
    public Collezione getCollezione(int collezione_key) throws DataException {
        Collezione c = null;
        if (dataLayer.getCache().has(Collezione.class, collezione_key)) {
            c = dataLayer.getCache().get(Collezione.class, collezione_key);
        } else {
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
                    c = createCollezione(rs);
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

                long current_version = collezione.getVersion();
                long next_version = current_version + 1;

                uCollezione.setLong(3, next_version);
                uCollezione.setInt(4, collezione.getKey());
                uCollezione.setLong(5, current_version);

                if (uCollezione.executeUpdate() == 0) {
                    throw new OptimisticLockException(collezione);
                } else {
                    collezione.setVersion(next_version);
                }

            } else { //insert
                iCollezione.setString(1, collezione.getTitolo());
                iCollezione.setString(2, collezione.getPrivacy());
                iCollezione.setDate(3, Date.valueOf(collezione.getDataCreazione()));
                iCollezione.setLong(4, collezione.getVersion());
                iCollezione.setInt(5, collezione.getUtente().getKey());
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

                //Condivisione della collezione
                addUtentiCondivisi(collezione);
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
    public void addDiscoCollezione(Collezione collezione, Disco disco) throws DataException {
        try {
            if (collezione.getKey() != null && collezione.getKey() > 0) {
                if (collezione instanceof DataItemProxy && !((DataItemProxy) collezione).isModified()) {
                    return;
                }

                uCollezione.setString(1, collezione.getTitolo());
                uCollezione.setString(2, collezione.getPrivacy());

                long current_version = collezione.getVersion();
                long next_version = current_version + 1;

                uCollezione.setLong(3, next_version);
                uCollezione.setInt(4, collezione.getKey());
                uCollezione.setLong(5, current_version);

                if (uCollezione.executeUpdate() == 0) {
                    throw new OptimisticLockException(collezione);
                } else {
                    collezione.setVersion(next_version);
                }
                addDisco(collezione, disco);
            }
            if (collezione instanceof DataItemProxy) {
                ((DataItemProxy) collezione).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to add disco to collezione", ex);

        }
    }

    //Condivisione della collezione
    @Override
    public void addUtentiCondivisi(Collezione collezione) throws DataException {
        int id = collezione.getKey();
        for (Utente u : collezione.getUtentiCondivisi()) {
            if (u.getKey() != null && u.getKey() > 0) {
                try {
                    addUtenteCondiviso.setInt(1, id);
                    addUtenteCondiviso.setInt(2, u.getKey());
                    addUtenteCondiviso.executeUpdate();
                } catch (SQLException ex) {
                    throw new DataException("Error adding shared user to collection", ex);
                }

            }
        }
    }

    @Override
    public List<Collezione> getCollezioni() throws DataException {
        List<Collezione> collezione = new ArrayList<>();
        try (ResultSet rs = sCollezioni.executeQuery()) {
            while (rs.next()) {
                collezione.add(getCollezione(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections", ex);
        }
        return collezione;
    }

    @Override
    public List<Collezione> getCollezioniByKeyword(String keyword) throws DataException {
        List<Collezione> result = new ArrayList<>();
        try {
            fCollezioniByTitolo.setString(1, keyword);
            try (ResultSet rs = fCollezioniByTitolo.executeQuery()) {
                while (rs.next()) {
                    result.add(createCollezione(rs));
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load collections", ex);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to set keyword", ex);
        }
        return result;
    }

    @Override
    public List<Collezione> getCollezioniByKeywordLogged(String keyword, Utente utente) throws DataException {
        Set<Collezione> result = new HashSet<>();
        try {
            //pesco le collezioni per l'utente autenticato che fanno match la keyword
            fCollezioniByTitolo.setString(1, keyword);
            fCollezioniByTitoloPrivate.setInt(1, utente.getKey());
            fCollezioniByTitoloPrivate.setString(2, keyword);
            fCollezioniByTitoloCondivise.setInt(1, utente.getKey());
            fCollezioniByTitoloCondivise.setString(2, keyword);
            //faccio merge di tutte le collezioni in un'unica lista
            try (ResultSet rs = fCollezioniByTitolo.executeQuery()) {
                while (rs.next()) {
                    result.add(createCollezione(rs));
                }
            }
            try (ResultSet rs = fCollezioniByTitoloPrivate.executeQuery()) {
                while (rs.next()) {
                    result.add(createCollezione(rs));
                }
            }
            try (ResultSet rs = fCollezioniByTitoloCondivise.executeQuery()) {
                while (rs.next()) {
                    result.add(createCollezione(rs));
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load collections", ex);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to set keyword", ex);
        }
        return new ArrayList<>(result);
    }

    @Override
    public List<Collezione> getCollezioni(Disco disco) throws DataException {
        List<Collezione> collezioni = new ArrayList<>();
        try {
            sCollezioniByDisco.setInt(1, disco.getKey());
            try (ResultSet rs = sCollezioniByDisco.executeQuery()) {
                while (rs.next()) {
                    collezioni.add(getCollezione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections by disco", ex);
        }
        return collezioni;
    }

    @Override
    public List<Collezione> getCollezioni(Utente utente) throws DataException {
        List<Collezione> collezioni = new ArrayList<>();
        try {
            sCollezioniByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = sCollezioniByUtente.executeQuery()) {
                while (rs.next()) {
                    collezioni.add(getCollezione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections by utente", ex);
        }
        return collezioni;
    }


    @Override
    public List<Collezione> getCollezioniCondivise(Utente utente) throws DataException {
        List<Collezione> collezioni = new ArrayList<>();
        try {
            sCollezioniCondiviseByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = sCollezioniCondiviseByUtente.executeQuery()) {
                while (rs.next()) {
                    collezioni.add(getCollezione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections shared with utente", ex);
        }
        return collezioni;
    }

    @Override
    public void addDisco(Collezione collezione, Disco disco) throws DataException {
        try {
            addDiscoCollezione.setInt(1, collezione.getKey());
            addDiscoCollezione.setInt(2, disco.getKey());
            addDiscoCollezione.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Error setting disk in collection", ex);
        }
    }

    @Override
    public void deleteCollezione(Collezione collezione) throws DataException {
        try {
            dCollezione.setInt(1, collezione.getKey());
            if (dCollezione.executeUpdate() == 1) {
                //rimuoviamo l'oggetto dalla cache
                //remove the object from the cache
                dataLayer.getCache().delete(Collezione.class, collezione.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to delete collezione", ex);
        }
    }

    public List<Integer> getUtentiAttivi() throws DataException {
        List<Integer> id_utenti = new ArrayList<>();
        try {
            try (ResultSet rs = idUtentiAttivi.executeQuery()) {
                while (rs.next()) {
                    id_utenti.add(rs.getInt("utente_id"));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting id utenti", ex);
        }
        return id_utenti;
    }

    @Override
    public List<Collezione> getCollezioniPubbliche(Utente utente) throws DataException {
        List<Collezione> collezioni = new ArrayList<>();
        try {
            sCollezioniByUtentePubbliche.setInt(1, utente.getKey());
            try (ResultSet rs = sCollezioniByUtentePubbliche.executeQuery()) {
                while (rs.next()) {
                    collezioni.add(getCollezione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load collections by utente", ex);
        }
        return collezioni;
    }

}

