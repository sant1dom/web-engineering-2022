package org.webeng.collector_site.data.dao;

import org.webeng.framework.data.DAO;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StatsDAO_MySQL extends DAO implements StatsDAO {
    PreparedStatement dischiPerGenere;
    PreparedStatement dischiPerAnno;
    PreparedStatement dischiPerAutore;
    PreparedStatement dischiPerEtichetta;
    PreparedStatement dischiTotali;
    PreparedStatement tracceTotali;
    PreparedStatement autoriTotali;
    PreparedStatement etichetteTotali;
    PreparedStatement generiTotali;
    PreparedStatement numeroCollezioniPubbliche;
    PreparedStatement numeroCollezioniPrivateUtente;
    PreparedStatement numeroCollezioniTotaliUtente;

    public StatsDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try{
            super.init();
            dischiPerGenere = connection.prepareStatement("SELECT COUNT(*) FROM disco WHERE genere = ? AND padre IS NULL");
            dischiPerAnno = connection.prepareStatement("SELECT COUNT(*) FROM disco WHERE anno = ? AND padre IS NULL");
            dischiPerAutore = connection.prepareStatement("SELECT COUNT(*) FROM disco join disco_autore da on disco.id = da.disco_id join autore a on a.id = da.autore_id WHERE a.nome_artistico = ? AND padre IS NULL");
            dischiPerEtichetta = connection.prepareStatement("SELECT COUNT(*) FROM disco WHERE etichetta = ? AND padre IS NULL");
            dischiTotali = connection.prepareStatement("SELECT COUNT(*) FROM disco WHERE padre IS NULL");
            tracceTotali = connection.prepareStatement("SELECT COUNT(*) FROM traccia WHERE padre IS NULL");
            autoriTotali = connection.prepareStatement("SELECT COUNT(*) FROM autore");
            etichetteTotali = connection.prepareStatement("SELECT COUNT(DISTINCT etichetta) FROM disco WHERE padre IS NULL");
            generiTotali = connection.prepareStatement("SELECT COUNT(DISTINCT genere) FROM disco WHERE padre IS NULL");
            numeroCollezioniPubbliche = connection.prepareStatement("SELECT COUNT(*) FROM collezione WHERE privacy = 'PUBBLICA'");
            numeroCollezioniPrivateUtente = connection.prepareStatement("SELECT COUNT(*) FROM collezione WHERE privacy = 'PRIVATA' AND utente_id = ?");
            numeroCollezioniTotaliUtente = connection.prepareStatement("SELECT COUNT(*) FROM collezione WHERE utente_id = ?");
        } catch (SQLException e) {
            throw new DataException("Error initializing stats data layer",e);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            super.destroy();
            dischiPerGenere.close();
            dischiPerAnno.close();
            dischiPerAutore.close();
            dischiPerEtichetta.close();
            dischiTotali.close();
            tracceTotali.close();
            autoriTotali.close();
            etichetteTotali.close();
            generiTotali.close();
            numeroCollezioniPubbliche.close();
            numeroCollezioniPrivateUtente.close();
            numeroCollezioniTotaliUtente.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing disks data layer", ex);
        }
    }

    @Override
    public int getDischiPerGenere(String genere) throws DataException {
        try {
            dischiPerGenere.setString(1, genere);
            var rs = dischiPerGenere.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataException("Error getting discs per genre", e);
        }
    }

    @Override
    public int getDischiPerAnno(int anno) throws DataException {
        try {
            dischiPerAnno.setInt(1, anno);
            var rs = dischiPerAnno.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataException("Error getting discs per year", e);
        }
    }

    @Override
    public int getDischiPerAutore(String nomeArtistico) throws DataException {
        try {
            dischiPerAutore.setString(1, nomeArtistico);
            var rs = dischiPerAutore.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataException("Error getting discs per author", e);
        }
    }

    @Override
    public int getDischiPerEtichetta(String etichetta) throws DataException {
        try {
            dischiPerEtichetta.setString(1, etichetta);
            var rs = dischiPerEtichetta.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataException("Error getting discs per label", e);
        }
    }

    @Override
    public List<Integer> getTotalStats() throws DataException {
        try {
            ResultSet rs;
            rs = dischiTotali.executeQuery();
            rs.next();
            int dischiTotali = rs.getInt(1);
            rs = tracceTotali.executeQuery();
            rs.next();
            int tracceTotali = rs.getInt(1);
            rs = autoriTotali.executeQuery();
            rs.next();
            int autoriTotali = rs.getInt(1);
            rs = etichetteTotali.executeQuery();
            rs.next();
            int etichetteTotali = rs.getInt(1);
            rs = generiTotali.executeQuery();
            rs.next();
            int generiTotali = rs.getInt(1);
            return List.of(dischiTotali, tracceTotali, autoriTotali, etichetteTotali, generiTotali);
        } catch (SQLException e) {
            throw new DataException("Error getting total stats", e);
        }
    }

    @Override
    public int getNumeroCollezioniPrivateUtente(int utenteId) throws DataException {
        try {
            numeroCollezioniPrivateUtente.setInt(1, utenteId);
            var rs = numeroCollezioniPrivateUtente.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataException("Error getting private collections number", e);
        }
    }

    @Override
    public int getNumeroCollezioniTotaliUtente(int utenteId) throws DataException {
        try {
            numeroCollezioniTotaliUtente.setInt(1, utenteId);
            var rs = numeroCollezioniTotaliUtente.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataException("Error getting total collections number", e);
        }
    }
}
