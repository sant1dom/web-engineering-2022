package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Collezione;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Utente;
import org.webeng.collector_site.data.proxy.UtenteProxy;
import org.webeng.framework.data.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO_MySQL extends DAO implements UtenteDAO {
    private PreparedStatement sLogin;
    private PreparedStatement sUtenti;
    private PreparedStatement sUtenteByID;
    private PreparedStatement sUtenteByUsername;
    private PreparedStatement sUtentiByCollezione;
    private PreparedStatement sUtentiByDisco;
    private PreparedStatement uUtente;
    private PreparedStatement iUtente;
    private PreparedStatement dUtente;
    private PreparedStatement addUtenteCollezione;

    public UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate nella classe
            //precompile all the queries uses in this class
            sLogin = connection.prepareStatement("SELECT * FROM utente WHERE username = ? AND password = ?");
            sUtenteByID = connection.prepareStatement("SELECT * FROM utente WHERE id=?");
            sUtenti = connection.prepareStatement("SELECT id FROM utente");
            sUtenteByUsername = connection.prepareStatement("SELECT * from utente WHERE username=?");
            sUtentiByCollezione = connection.prepareStatement("SELECT utente.id FROM utente JOIN collezione_condivisa_con ccc on utente.id = ccc.utente_id JOIN collezione c on ccc.collezione_id = c.id WHERE c.id=?");
            sUtentiByDisco = connection.prepareStatement("SELECT utente.id FROM utente JOIN disco d on utente.id = d.utente_id WHERE d.id=?");
            uUtente = connection.prepareStatement("UPDATE utente SET nome=?, cognome=?, email=?, username=?, password=? WHERE id=?");
            iUtente = connection.prepareStatement("INSERT INTO utente (nome, cognome, email, username, password) VALUES (?,?,?,?,?)");
            dUtente = connection.prepareStatement("DELETE FROM utente WHERE id=?");
            addUtenteCollezione = connection.prepareStatement("INSERT INTO collezione_condivisa_con (utente_id, collezione_id) VALUES (?,?)");
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
            uUtente.close();
            iUtente.close();
            dUtente.close();
            addUtenteCollezione.close();
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
            return createUtente(rs);
        } catch (SQLException ex) {
            throw new DataException("Error logging in user", ex);
        }
    }

    @Override
    public Utente createUtente() {
        return new UtenteProxy(getDataLayer());
    }

    private UtenteProxy createUtente(ResultSet rs) throws DataException {
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
        Utente u = null;
        try {
            sUtenteByUsername.setString(1, username);
            try (ResultSet rs = sUtenteByUsername.executeQuery()) {
                if (rs.next()) {
                    u = createUtente(rs);
                    dataLayer.getCache().add(Utente.class, u);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load user by username", ex);
        }
        return u;
    }

    @Override
    public Utente getUtente(int utente_key) throws DataException {
        Utente u = null;
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
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load user by ID", ex);
            }
        }
        return u;
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
                uUtente.setString(1, utente.getNome());
                uUtente.setString(2, utente.getCognome());
                if (utente.getUsername() != null) {
                    uUtente.setString(3, utente.getUsername());
                } else {
                    uUtente.setNull(3, Types.VARCHAR);
                }

                uUtente.setString(4, utente.getEmail());
                uUtente.setString(5, utente.getPassword());

                long current_version = utente.getVersion();
                long next_version = current_version + 1;

                uUtente.setLong(5, next_version);
                uUtente.setInt(6, utente.getKey());
                uUtente.setLong(7, current_version);

                if (uUtente.executeUpdate() == 0) {
                    throw new OptimisticLockException(utente);
                } else {
                    utente.setVersion(next_version);
                }
            } else { //insert
                iUtente.setString(1, utente.getNome());
                iUtente.setString(2, utente.getCognome());
                if (utente.getUsername() != null) {
                    iUtente.setString(3, utente.getUsername());
                } else {
                    iUtente.setNull(3, Types.VARCHAR);
                }
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
            throw new DataException("Unable to store utente", ex);
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