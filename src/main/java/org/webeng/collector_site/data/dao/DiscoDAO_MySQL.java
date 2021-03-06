package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.*;
import org.webeng.collector_site.data.proxy.DiscoProxy;
import org.webeng.framework.data.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscoDAO_MySQL extends DAO implements DiscoDAO {
    private PreparedStatement sDiscoByID;
    private PreparedStatement sDiscoByBarcode;
    private PreparedStatement sDischiByAnno;
    private PreparedStatement sDischiByGenere;
    private PreparedStatement sDischiByEtichetta;
    private PreparedStatement sDischi;
    private PreparedStatement sDischiByCollezione;
    private PreparedStatement sDischiByAutore;
    private PreparedStatement sDischiByTraccia;
    private PreparedStatement sDischiByUtente;
    private PreparedStatement sFigliDisco;
    private PreparedStatement sPadreDisco;
    private PreparedStatement sDischiPadri;
    private PreparedStatement uDisco;
    private PreparedStatement iDisco;
    private PreparedStatement dDisco;
    private PreparedStatement dDiscoCollezione;
    private PreparedStatement addDiscoCollezione;
    private PreparedStatement addDiscoAutore;
    private PreparedStatement addDiscoTraccia;
    private PreparedStatement sEtichette;
    private PreparedStatement fDischiByKeyword;
    private PreparedStatement dTracciaDisco;
    private PreparedStatement dAutoreDisco;
    private PreparedStatement dischiPopolari;
    private PreparedStatement uDiscoPadre;

    public DiscoDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sDiscoByID = connection.prepareStatement("SELECT * FROM disco WHERE id = ?");
            sDiscoByBarcode = connection.prepareStatement("SELECT * FROM disco WHERE barcode = ?");
            sDischiByAnno = connection.prepareStatement("SELECT id FROM disco WHERE anno = ?");
            sDischiByGenere = connection.prepareStatement("SELECT id FROM disco WHERE genere = ?");
            sDischiByEtichetta = connection.prepareStatement("SELECT id FROM disco WHERE etichetta = ?");
            sDischi = connection.prepareStatement("SELECT id FROM disco");
            sDischiByCollezione = connection.prepareStatement("SELECT d.id FROM disco d JOIN collezione_disco cd on d.id = cd.disco_id JOIN collezione c on c.id = cd.collezione_id WHERE c.id = ?");
            sDischiByAutore = connection.prepareStatement("SELECT d.id FROM disco d JOIN disco_autore ad on d.id = ad.disco_id JOIN autore a on a.id = ad.autore_id WHERE a.id = ?");
            sDischiByTraccia = connection.prepareStatement("SELECT d.id FROM disco d JOIN disco_traccia td on d.id = td.disco_id JOIN traccia t on t.id = td.traccia_id WHERE t.id = ?");
            sDischiByUtente = connection.prepareStatement("SELECT d.id FROM disco d WHERE utente_id = ?");
            sFigliDisco = connection.prepareStatement("SELECT d.id FROM disco d WHERE padre = ?");
            sPadreDisco = connection.prepareStatement("SELECT d.padre FROM disco d WHERE id = ?");
            sDischiPadri = connection.prepareStatement("SELECT id FROM disco WHERE disco.padre IS NULL");
            sEtichette = connection.prepareStatement("SELECT DISTINCT etichetta FROM disco");
            uDisco = connection.prepareStatement("UPDATE disco SET titolo = ?, barcode = ?, anno = ?, genere = ?, etichetta = ?, formato = ?, stato_conservazione = ?, version = ? WHERE id = ? AND version = ?");
            iDisco = connection.prepareStatement("INSERT INTO disco (titolo, barcode, anno, genere, etichetta, formato, data_inserimento, utente_id, stato_conservazione, padre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            dDisco = connection.prepareStatement("DELETE FROM disco WHERE id = ?");
            dDiscoCollezione = connection.prepareStatement("DELETE FROM collezione_disco WHERE collezione_id = ? AND disco_id = ?");
            addDiscoCollezione = connection.prepareStatement("INSERT INTO collezione_disco (collezione_id, disco_id) VALUES (?, ?)");
            addDiscoAutore = connection.prepareStatement("INSERT INTO disco_autore (disco_id, autore_id) VALUES (?,?)");
            addDiscoTraccia = connection.prepareStatement("INSERT INTO disco_traccia (disco_id, traccia_id) VALUES (?,?)");
            dTracciaDisco= connection.prepareStatement("DELETE FROM disco_traccia WHERE disco_id = ? AND traccia_id = ?");
            dAutoreDisco= connection.prepareStatement("DELETE FROM disco_autore WHERE disco_id = ? AND autore_id = ?");
            fDischiByKeyword = connection.prepareStatement("SELECT id FROM disco WHERE padre IS NULL AND (titolo LIKE CONCAT('%', ?, '%') OR barcode LIKE CONCAT('%', ?, '%') OR anno LIKE CONCAT('%', ?, '%') OR genere LIKE CONCAT('%', ?, '%') OR etichetta LIKE CONCAT('%', ?, '%')) LIMIT 4");
            dischiPopolari = connection.prepareStatement("SELECT padre, COUNT(*) AS occorrenza FROM disco WHERE padre IS NOT NULL GROUP BY padre ORDER BY occorrenza DESC LIMIT 4");
            uDiscoPadre= connection.prepareStatement("UPDATE disco SET padre = ? WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing tracks data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sDiscoByID.close();
            sDiscoByBarcode.close();
            sDischiByAnno.close();
            sDischiByGenere.close();
            sDischiByEtichetta.close();
            sDischi.close();
            sDischiByCollezione.close();
            sDischiByAutore.close();
            sDischiByTraccia.close();
            sDischiByUtente.close();
            sFigliDisco.close();
            sPadreDisco.close();
            sEtichette.close();
            uDisco.close();
            iDisco.close();
            dDisco.close();
            dDiscoCollezione.close();
            addDiscoCollezione.close();
            dTracciaDisco.close();
            dAutoreDisco.close();
            fDischiByKeyword.close();
            dischiPopolari.close();
            uDiscoPadre.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing disks data layer", ex);
        }
    }

    @Override
    public Disco createDisco() {
        return new DiscoProxy(getDataLayer());
    }

    private DiscoProxy createDisco(ResultSet rs) throws DataException {
        DiscoProxy d = new DiscoProxy(getDataLayer());
        try {
            d.setKey(rs.getInt("id"));
            d.setBarCode(rs.getString("barcode"));
            d.setTitolo(rs.getString("titolo"));
            d.setAnno(String.valueOf(rs.getInt("anno")));
            d.setGenere(Genere.valueOf(rs.getString("genere")));
            d.setEtichetta(rs.getString("etichetta"));
            d.setFormato(Formato.valueOf(rs.getString("formato")));
            if (rs.getString("stato_conservazione") != null && !rs.getString("stato_conservazione").isBlank()) {
                d.setStatoConservazione(StatoConservazione.valueOf(rs.getString("stato_conservazione")));
            } else {
                d.setStatoConservazione(null);
            }
            d.setUtenteKey(rs.getInt("utente_id"));
            d.setPadreKey(rs.getInt("padre"));
            d.setVersion(rs.getInt("version"));
        } catch (SQLException ex) {
            throw new DataException("Error creating disk", ex);
        }
        return d;
    }

    @Override
    public Disco getDisco(int disco_key) throws DataException {
        Disco d = null;
        if (dataLayer.getCache().has(Disco.class, disco_key)) {
            d = dataLayer.getCache().get(Disco.class, disco_key);
        } else {
            try {
                sDiscoByID.setInt(1, disco_key);
                try (ResultSet rs = sDiscoByID.executeQuery()) {
                    if (rs.next()) {
                        d = createDisco(rs);
                        dataLayer.getCache().add(Disco.class, d);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Error getting disk by id", ex);
            }
        }
        return d;
    }

    @Override
    public Disco getDisco(String barcode) throws DataException {
        Disco d = null;
        try {
            sDiscoByBarcode.setString(1, barcode);
            try (ResultSet rs = sDiscoByBarcode.executeQuery()) {
                if (rs.next()) {
                    d = createDisco(rs);
                    dataLayer.getCache().add(Disco.class, d);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disk by barcode", ex);
        }

        return d;
    }

    @Override
    public int storeDisco(Disco disco) throws DataException {
        try {
            if (disco.getKey() != null && disco.getKey() > 0) {
                if (disco instanceof DiscoProxy && !((DiscoProxy) disco).isModified()) {
                    return 0;
                }
                uDisco.setString(1, disco.getTitolo());
                uDisco.setString(2, disco.getBarCode());
                uDisco.setString(3, disco.getAnno());
                uDisco.setString(4, disco.getGenere().toString());
                uDisco.setString(5, disco.getEtichetta());
                uDisco.setString(6, disco.getFormato().toString());

                if (disco.getStatoConservazione() != null) {
                    uDisco.setString(7, disco.getStatoConservazione().toString());
                } else {
                    uDisco.setNull(7, Types.VARCHAR);
                }
                long current_version = disco.getVersion();
                long next_version = current_version + 1;

                uDisco.setLong(8, next_version);
                uDisco.setInt(9, disco.getKey());
                uDisco.setLong(10, current_version);



                if (uDisco.executeUpdate() == 0) {
                    throw new OptimisticLockException(disco);
                } else {
                    disco.setVersion(next_version);
                }
            } else {
                iDisco.setString(1, disco.getTitolo());
                iDisco.setString(2, disco.getBarCode());
                iDisco.setString(3, disco.getAnno());
                iDisco.setString(4, disco.getGenere().toString());
                iDisco.setString(5, disco.getEtichetta());
                iDisco.setString(6, disco.getFormato().toString());
                iDisco.setDate(7, Date.valueOf(disco.getDataInserimento()));
                iDisco.setInt(8, disco.getUtente().getKey());

                if (disco.getStatoConservazione() != null) {
                    iDisco.setString(9, disco.getStatoConservazione().toString());
                } else {
                    iDisco.setString(9, null);
                }
                if (disco.getPadre() != null) {
                    iDisco.setInt(10, disco.getPadre().getKey());
                } else
                    iDisco.setNull(10, Types.INTEGER);
                if (iDisco.executeUpdate() == 1) {
                    try (ResultSet rs = iDisco.getGeneratedKeys()) {
                        if (rs.next()) {
                            int key = rs.getInt(1);
                            disco.setKey(key);
                            dataLayer.getCache().add(Disco.class, disco);
                        }
                    }
                }
                for (Autore a : disco.getAutori()) {
                    addDiscoAutore.setInt(1, disco.getKey());
                    addDiscoAutore.setInt(2, a.getKey());
                    addDiscoAutore.executeUpdate();
                }
                for (Traccia t : disco.getTracce()) {
                    addDiscoTraccia.setInt(1, disco.getKey());
                    addDiscoTraccia.setInt(2, t.getKey());
                    addDiscoTraccia.executeUpdate();
                }
            }
            if (disco instanceof DataItemProxy) {
                ((DataItemProxy) disco).setModified(false);
            }
            return disco.getKey();
        } catch (SQLException ex) {
            throw new DataException("Error storing disk", ex);
        }
    }
    @Override
    public void  updateDiscoPadre(Disco disco) throws DataException {

        try {
            if (disco.getPadre() != null) {
                uDiscoPadre.setInt(1, disco.getPadre().getKey());
            } else {
                uDiscoPadre.setNull(1, Types.INTEGER);
            }
            uDiscoPadre.setInt(2, disco.getKey());
            uDiscoPadre.executeUpdate();

        }catch(SQLException ex){
            throw new DataException("Error updating father to the disk", ex);
        }

    }


    @Override
    public void deleteDisco(Disco disco) throws DataException {
        try {
            dDisco.setInt(1, disco.getKey());
            if (dDisco.executeUpdate() == 1) {
                dataLayer.getCache().delete(Disco.class, disco.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Error deleting disk", ex);
        }
    }

    @Override
    public void deleteDisco(Collezione collezione,Disco disco) throws DataException {
        try {
            dDiscoCollezione.setInt(1, collezione.getKey());
            dDiscoCollezione.setInt(2, disco.getKey());
            if(dDiscoCollezione.executeUpdate() == 1){
                dataLayer.getCache().delete(Disco.class, disco.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Error removing disk in collection", ex);
        }
    }

    @Override
    public List<Disco> getDischi() throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            try (ResultSet rs = sDischi.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks", ex);
        }
        return dischi;
    }

    @Override
    public List<Disco> getDischiByKeyword(String keyword) throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            fDischiByKeyword.setString(1, keyword);
            fDischiByKeyword.setString(2, keyword);
            fDischiByKeyword.setString(3, keyword);
            fDischiByKeyword.setString(4, keyword);
            fDischiByKeyword.setString(5, keyword);
            try (ResultSet rs = fDischiByKeyword.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("id")));
                }
            }
        } catch (SQLException | DataException ex) {
            throw new DataException("Error getting disks", ex);
        }
        return dischi;
    }

    @Override
    public List<Disco> getDischi(Collezione collezione) throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            sDischiByCollezione.setInt(1, collezione.getKey());
            try (ResultSet rs = sDischiByCollezione.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks by collection", ex);
        }
        return dischi;
    }

    @Override
    public List<Disco> getDischi(Autore autore) throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            sDischiByAutore.setInt(1, autore.getKey());
            try (ResultSet rs = sDischiByAutore.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks by author", ex);
        }
        return dischi;
    }

    @Override
    public List<Disco> getDischi(Traccia traccia) throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            sDischiByTraccia.setInt(1, traccia.getKey());
            try (ResultSet rs = sDischiByTraccia.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks by track", ex);
        }
        return dischi;
    }

    @Override
    public List<Disco> getDischi(Utente utente) throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            sDischiByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = sDischiByUtente.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks by user", ex);
        }
        return dischi;
    }

    @Override
    public List<Disco> getFigli(Disco disco) throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            sFigliDisco.setInt(1, disco.getKey());
            try (ResultSet rs = sFigliDisco.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks children", ex);
        }
        return dischi;
    }

    @Override
    public Disco getPadre(Disco disco) throws DataException {
        Disco padre = null;
        try {
            sPadreDisco.setInt(1, disco.getKey());
            ResultSet rs = sPadreDisco.executeQuery();
            if (rs.next()) {
                padre = getDisco(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks' parent", ex);
        }
        return padre;
    }

    @Override
    public List<Disco> getDischiPadri() throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            ResultSet rs = sDischiPadri.executeQuery();
            while (rs.next()) {
                dischi.add(getDisco(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting parents disks", ex);
        }
        return dischi;
    }

    @Override
    public void addDischi(Collezione collezione, List<Disco> dischi) throws DataException {
        for (Disco disco : dischi) {
            addDisco(collezione, disco);
        }
    }
    @Override
    public void addDiscoToCollezioni(List<Collezione> collezioni, Disco disco ) throws DataException {
        for (Collezione collezione : collezioni) {
            addDisco(collezione, disco);
        }
    }

    @Override
    public void deleteDiscoTraccia(Disco disco, Traccia traccia) throws DataException {
        try {
            dTracciaDisco.setInt(1, disco.getKey());
            dTracciaDisco.setInt(2, traccia.getKey());
            dTracciaDisco.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Error deleting disk track", ex);
        }
    }

    @Override
    public void addDiscoTraccia(Disco disco, List<Traccia> tracce) throws DataException {
        try {
            for (Traccia traccia : tracce) {
                addDiscoTraccia.setInt(1, disco.getKey());
                addDiscoTraccia.setInt(2, traccia.getKey());
                addDiscoTraccia.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataException("Error adding traccia to disco", ex);
        }
    }

        @Override
    public List<String> getEtichette() throws DataException {
        List<String> etichette = new ArrayList<>();
        try {
            ResultSet rs = sEtichette.executeQuery();
            while (rs.next()) {
                etichette.add(rs.getString("etichetta"));
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting labels", ex);
        }
        return etichette;
    }

    @Override
    public void deleteDiscoAutore(Disco disco, Autore autore) throws DataException {
        try {
            dAutoreDisco.setInt(1, disco.getKey());
            dAutoreDisco.setInt(2, autore.getKey());
            dAutoreDisco.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Errore nell'eliminare un autore di un disco", ex);
        }
        }

    @Override
    public void addDiscoAutore(Disco disco, List<Autore> autore) throws DataException {
        try {
            for (Autore aut : autore) {
                addDiscoAutore.setInt(1, disco.getKey());
                addDiscoAutore.setInt(2, aut.getKey());
                addDiscoAutore.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataException("Errore nell'aggiungere un autore a un disco", ex);
        }
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
    public List<Disco> getDischiPopolari() throws DataException {
        List<Disco> dischi = new ArrayList<>();
        try {
            try (ResultSet rs = dischiPopolari.executeQuery()) {
                while (rs.next()) {
                    dischi.add(getDisco(rs.getInt("padre")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Error getting disks", ex);
        }
        return dischi;
    }
}