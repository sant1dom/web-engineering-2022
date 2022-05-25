package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.*;
import org.webeng.collector_site.data.proxy.DiscoProxy;
import org.webeng.framework.data.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscoDAO_MySQL extends DAO implements DiscoDAO {
    PreparedStatement sDiscoByID;
    PreparedStatement sDiscoByBarcode;
    PreparedStatement sDischiByAnno;
    PreparedStatement sDischiByGenere;
    PreparedStatement sDischiByEtichetta;
    PreparedStatement sDischi;
    PreparedStatement sDischiByCollezione;
    PreparedStatement sDischiByAutore;
    PreparedStatement sDischiByTraccia;
    PreparedStatement sDischiByUtente;
    PreparedStatement sFigliDisco;
    PreparedStatement sPadreDisco;
    PreparedStatement uDisco;
    PreparedStatement iDisco;
    PreparedStatement dDisco;
    PreparedStatement addDiscoCollezione;

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
            uDisco = connection.prepareStatement("UPDATE disco SET titolo = ?, barcode = ?, anno = ?, genere = ?, etichetta = ?, formato = ?, padre = ?, version = ? WHERE id = ? AND version = ?");
            iDisco = connection.prepareStatement("INSERT INTO disco (titolo, barcode, anno, genere, etichetta, formato, data_inserimento, utente_id, padre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            dDisco = connection.prepareStatement("DELETE FROM disco WHERE id = ?");
            addDiscoCollezione = connection.prepareStatement("INSERT INTO collezione_disco (collezione_id, disco_id) VALUES (?, ?)");

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
            uDisco.close();
            iDisco.close();
            dDisco.close();
            addDiscoCollezione.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing disks data layer", ex);
        }
    }

    @Override
    public Disco createDisco(){return new DiscoProxy(getDataLayer());}

    private DiscoProxy createDisco(ResultSet rs) throws DataException{
        DiscoProxy d = new DiscoProxy(getDataLayer());
        try {
            d.setKey(rs.getInt("id"));
            d.setBarCode(rs.getString("barcode"));
            d.setAnno(String.valueOf(rs.getInt("anno")));
            d.setGenere(Genere.valueOf(rs.getString("genere")));
            d.setEtichetta(rs.getString("etichetta"));
            d.setUtenteKey(rs.getInt("utente_id"));
            d.setPadreKey(rs.getInt("padre"));
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
    public void storeDisco(Disco disco) throws DataException {
        try {
            if (disco.getKey() != null && disco.getKey() > 0) {
                if(disco instanceof DiscoProxy && ((DiscoProxy)disco).isModified()){
                    return;
                }
                uDisco.setString(1, disco.getTitolo());
                uDisco.setString(2, disco.getBarCode());
                uDisco.setString(3, disco.getAnno());
                uDisco.setString(4, disco.getGenere().toString());
                uDisco.setString(5, disco.getEtichetta());
                uDisco.setString(6, disco.getFormato().toString());
                uDisco.setInt(7, disco.getPadre().getKey());

                long current_version = disco.getVersion();
                long next_version = current_version + 1;

                uDisco.setLong(8, next_version);
                uDisco.setInt(9, disco.getKey());
                uDisco.setLong(10, current_version);

                if(uDisco.executeUpdate() == 0){
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
                iDisco.setInt(9, disco.getPadre().getKey());
                if(iDisco.executeUpdate() == 1){
                    try (ResultSet rs = iDisco.getGeneratedKeys()) {
                        if (rs.next()) {
                            disco.setKey(rs.getInt(1));
                            dataLayer.getCache().add(Disco.class, disco);
                        }
                    }
                }
            }
            if (disco instanceof DataItemProxy) {
                ((DataItemProxy) disco).setModified(false);
            }
        } catch (SQLException ex) {
            throw new DataException("Error storing disk", ex);
        }
    }

    @Override
    public void deleteDisco(Disco disco) throws DataException {
        try {
            dDisco.setInt(1, disco.getKey());
            if(dDisco.executeUpdate() == 1){
                dataLayer.getCache().delete(Disco.class, disco.getKey());
            }
        } catch (SQLException ex) {
            throw new DataException("Error deleting disk", ex);
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
            throw new DataException("Error getting disks' children", ex);
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
    public void addDischi(Collezione collezione, List<Disco> dischi) throws DataException {
        for (Disco disco : dischi) {
            addDisco(collezione, disco);
        }
    }

    @Override
    public void addDisco(Collezione collezione, Disco disco) throws DataException {
        try {
            addDiscoCollezione.setInt(1, collezione.getKey());
            addDiscoCollezione.setInt(2, disco.getKey());
            addDiscoCollezione.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Error setting track", ex);
        }
    }
}
