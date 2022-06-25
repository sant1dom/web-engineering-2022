package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.collector_site.data.proxy.UtenteProxy;
import org.webeng.framework.data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO_MySQL extends DAO implements UtenteDAO {
    private PreparedStatement sLogin;
    private PreparedStatement sUtenti;
    private PreparedStatement sUtenteByID;
    private PreparedStatement sUtenteByUsername;
    private PreparedStatement sUtentiByCollezione;
    private PreparedStatement sUtentiByDisco;
    private PreparedStatement sUtentiCondivisi;
    private PreparedStatement uUtente;
    private PreparedStatement iUtente;
    private PreparedStatement dUtente;
    private PreparedStatement addUtenteCollezione;
    private PreparedStatement dUtenteCollezione;
    private PreparedStatement fUtentiByUsername;

    public UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate nella classe
            sLogin = connection.prepareStatement("SELECT * FROM utente WHERE username = ? AND password = ?");
            sUtenteByID = connection.prepareStatement("SELECT * FROM utente WHERE id=?");
            sUtenti = connection.prepareStatement("SELECT id FROM utente");
            sUtenteByUsername = connection.prepareStatement("SELECT * from utente WHERE username=?");
            sUtentiByCollezione = connection.prepareStatement("SELECT utente.id FROM utente JOIN collezione_condivisa_con ccc on utente.id = ccc.utente_id JOIN collezione c on ccc.collezione_id = c.id WHERE c.id=?");
            sUtentiByDisco = connection.prepareStatement("SELECT utente.id FROM utente JOIN disco d on utente.id = d.utente_id WHERE d.id=?");
            sUtentiCondivisi = connection.prepareStatement("SELECT utente.id FROM utente JOIN collezione_condivisa_con ccc ON utente.id = ccc.utente_id WHERE ccc.collezione_id = ?");
            uUtente = connection.prepareStatement("UPDATE utente SET nome=?, cognome=?, username=?, email=?, password=?, version=? WHERE id=?");
            iUtente = connection.prepareStatement("INSERT INTO utente (nome, cognome, username, email, password) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            dUtente = connection.prepareStatement("DELETE FROM utente WHERE id=?");
            addUtenteCollezione = connection.prepareStatement("INSERT INTO collezione_condivisa_con (utente_id, collezione_id) VALUES (?,?)");
            dUtenteCollezione = connection.prepareStatement("DELETE FROM collezione_condivisa_con WHERE collezione_id=? AND utente_id=?");

            //query per auto completamento ricerca
            fUtentiByUsername = connection.prepareStatement("SELECT * from utente WHERE username LIKE CONCAT('%', ? ,'%') OR (nome IS NOT NULL AND nome LIKE CONCAT('%' ,? ,'%')) OR (cognome IS NOT NULL AND cognome LIKE CONCAT('%' ,? ,'%'))");
        } catch (SQLException ex) {
            throw new DataException("Error initializing users data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sLogin.close();
            sUtenti.close();
            sUtenteByID.close();
            sUtenteByUsername.close();
            sUtentiByCollezione.close();
            sUtentiByDisco.close();
            sUtentiCondivisi.close();
            uUtente.close();
            iUtente.close();
            dUtente.close();
            addUtenteCollezione.close();
            dUtenteCollezione.close();
            fUtentiByUsername.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing prepared statements", ex);
        }
        super.destroy();
    }

    @Override
    public Utente doLogin(String username, String password) throws DataException {
        try {
            sLogin.setString(1, username);
            sLogin.setString(2, password);
            ResultSet rs = sLogin.executeQuery();
            if (rs.next()) {
                return createUtente(rs);
            } else {
                throw new SQLException();
            }

        } catch (SQLException ex) {
            throw new DataException("User not found", ex);
        }
    }

    @Override
    public Utente createUtente() {
        return new UtenteProxy(getDataLayer());
    }

    public UtenteProxy createUtente(ResultSet rs) throws DataException {
        if (rs == null) {
            return null;
        }
        try {
            UtenteProxy u = (UtenteProxy) createUtente();

            u.setKey(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setCognome(rs.getString("cognome"));
            u.setEmail(rs.getString("email"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));

            return u;
        } catch (SQLException ex) {
            throw new DataException("Unable to create user object form ResultSet", ex);
        }
    }

    @Override
    public Utente getUtente(String username) throws DataException {
        Utente u;
        try {
            sUtenteByUsername.setString(1, username);
            try (ResultSet rs = sUtenteByUsername.executeQuery()) {
                if (rs.next()) {
                    u = createUtente(rs);
                    dataLayer.getCache().add(Utente.class, u);
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load user by username", ex);
        }
        return u;
    }

    @Override
    public Utente getUtente(int utente_key) throws DataException {
        Utente u;
        //prima vediamo se l'oggetto è già stato caricato
        //first look for this object in the cache
        if (dataLayer.getCache().has(Utente.class, utente_key)) {
            u = dataLayer.getCache().get(Utente.class, utente_key);
        } else {
            //altrimenti lo carichiamo dal database
            //otherwise load it from database
            try {
                sUtenteByID.setInt(1, utente_key);
                try (ResultSet rs = sUtenteByID.executeQuery()) {
                    if (rs.next()) {
                        u = createUtente(rs);
                        dataLayer.getCache().add(Utente.class, u);
                    } else {
                        throw new SQLException();
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load user by ID", ex);
            }
        }
        return u;
    }

    @Override
    public List<Utente> getUtentiCondivisi(Collezione collezione) throws DataException {
        try {
            List<Utente> utenti = new ArrayList<>();
            sUtentiCondivisi.setInt(1, collezione.getKey());
            try (ResultSet rs = sUtentiCondivisi.executeQuery()) {
                while (rs.next()) {
                    utenti.add(getUtente(rs.getInt("id")));
                }
            }
            return utenti;
        } catch (SQLException ex) {
            throw new DataException("Unable to delete collezione", ex);
        }
    }

    @Override
    public void storeUtente(Utente utente) throws DataException {
        try {
            if (utente.getKey() != null && utente.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (utente instanceof DataItemProxy && !((DataItemProxy) utente).isModified()) {
                    return;
                }
                if (utente.getNome() != null) {
                    uUtente.setString(1, utente.getNome());
                } else {
                    uUtente.setString(1, null);
                }
                if (utente.getCognome() != null) {
                    uUtente.setString(2, utente.getCognome());
                } else {
                    uUtente.setString(2, null);
                }
                uUtente.setString(3, utente.getUsername());
                uUtente.setString(4, utente.getEmail());
                uUtente.setString(5, utente.getPassword());

                long current_version = utente.getVersion();
                long next_version = current_version + 1;

                uUtente.setLong(6, next_version);
                uUtente.setLong(7, utente.getKey());

                if (uUtente.executeUpdate() == 0) {
                    throw new OptimisticLockException(utente);
                } else {
                    utente.setVersion(next_version);
                }
            } else { //insert
                if (utente.getNome() != null) {
                    iUtente.setString(1, utente.getNome());
                } else {
                    iUtente.setString(1, null);
                }
                if (utente.getCognome() != null) {
                    iUtente.setString(2, utente.getCognome());
                } else {
                    iUtente.setString(2, null);
                }
                iUtente.setString(3, utente.getUsername());
                iUtente.setString(4, utente.getEmail());
                iUtente.setString(5, utente.getPassword());

                if (iUtente.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
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
                            utente.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Utente.class, utente);
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
            if (utente instanceof DataItemProxy) {
                ((DataItemProxy) utente).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            if (ex.getMessage().contains("Duplicated entry")) {
                throw new DataException("Duplicated entry", ex);
            } else {
                throw new DataException("Unable to store user", ex);
            }
        }

    }

    @Override
    public List<Utente> getUtenti() throws DataException {
        List<Utente> result = new ArrayList<>();
        try (ResultSet rs = sUtenti.executeQuery()) {
            while (rs.next()) {
                result.add(getUtente(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load users", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getUtentiByKeyword(String keyword) throws DataException {
        List<Utente> result = new ArrayList<>();
        try {
            if (keyword.isBlank()) {
                result = getUtenti();
            } else {
                fUtentiByUsername.setString(1, keyword);
                fUtentiByUsername.setString(2, keyword);
                fUtentiByUsername.setString(3, keyword);
                ResultSet rs = fUtentiByUsername.executeQuery();
                while (rs.next()) {
                    Utente utente = createUtente(rs);
                    utente.setPassword("");
                    result.add(utente);
                }
            }
        } catch (SQLException | DataException ex) {
            throw new DataException("Unable to set keyword", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getUtenti(Collezione collezione) throws DataException {
        List<Utente> result = new ArrayList<>();
        try {
            sUtentiByCollezione.setInt(1, collezione.getKey());
            try (ResultSet rs = sUtentiByCollezione.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load users in shared collection", ex);
        }
        return result;
    }

    @Override
    public Utente getUtente(Disco disco) throws DataException {
        Utente u = null;
        try {
            sUtentiByDisco.setInt(1, disco.getKey());
            try (ResultSet rs = sUtentiByDisco.executeQuery()) {
                if (rs.next()) {
                    u = createUtente(rs);
                    dataLayer.getCache().add(Utente.class, u);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load utente related to disk", ex);
        }
        return u;
    }

    @Override
    public void deleteUtente(Utente utente) throws DataException {
        try {
            dUtente.setInt(1, utente.getKey());
            if (dUtente.executeUpdate() == 1) {
                //rimuoviamo l'oggetto dalla cache
                //remove the object from the cache
                dataLayer.getCache().delete(Utente.class, utente.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to delete utente", ex);
        }
    }

    @Override
    public void deleteUtenteCondiviso(Collezione collezione, Utente utente) throws DataException {
        try {
            dUtenteCollezione.setInt(1, collezione.getKey());
            dUtenteCollezione.setInt(2, utente.getKey());
            if (dUtenteCollezione.executeUpdate() == 1) {
                dataLayer.getCache().delete(Utente.class, utente.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Error removing user in collection", ex);
        }
    }

    @Override
    public void addUtentiCondivisi(List<Utente> utente, Collezione collezione) throws DataException {
        for (Utente u : utente) {
            addUtenteCondiviso(u, collezione);
        }
    }

    @Override
    public void addUtenteCondiviso(Utente utente, Collezione collezione) throws DataException {
        try {
            addUtenteCollezione.setInt(1, utente.getKey());
            addUtenteCollezione.setInt(2, collezione.getKey());
            addUtenteCollezione.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to add user to shared collection", ex);
        }
    }
}