package org.webeng.data.dao;


import org.webeng.data.model.Disco;
import org.webeng.data.model.Image;
import org.webeng.data.proxy.ImageProxy;
import org.webeng.framework.data.DAO;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO_MySQL extends DAO implements ImageDAO {
    private PreparedStatement sImageByID, sImageByDisco, sImageByFileName;

    public ImageDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            //precompiliamo tutte le query utilizzate nella classe
            sImageByID = connection.prepareStatement("SELECT * FROM image WHERE ID=?");
            sImageByDisco = connection.prepareStatement("SELECT id FROM image WHERE disco_id=?");
            sImageByFileName = connection.prepareStatement("SELECT * FROM image WHERE filename=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            sImageByID.close();
            sImageByDisco.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing prepared statements", ex);
        }
        super.destroy();
    }

    @Override
    public Image createImage() {
        return new ImageProxy(getDataLayer());
    }

    //helper
    private ImageProxy createImage(ResultSet rs) throws DataException {
        ImageProxy i = (ImageProxy)createImage();
        try {
            i.setKey(rs.getInt("id"));
            i.setImageSize(rs.getLong("size"));
            i.setImageType(rs.getString("type"));
            i.setFileName(rs.getString("filename"));
            i.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create image object form ResultSet", ex);
        }
        return i;
    }

    @Override
    public Image getImage(String filename) throws DataException {
        Image i = null;
        try {
            sImageByFileName.setString(1, filename);
            try (ResultSet rs = sImageByFileName.executeQuery()) {
                if (rs.next()) {
                    i = createImage(rs);
                    dataLayer.getCache().add(Image.class, i);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load image by filename", ex);
        }
        return i;
    }

    @Override
    public Image getImage(int image_key) throws DataException {
        Image i = null;
        //prima vediamo se l'oggetto è già stato caricato
        //first look for this object in the cache
        if (dataLayer.getCache().has(Image.class, image_key)) {
            i = dataLayer.getCache().get(Image.class, image_key);
        } else {
            //altrimenti lo carichiamo dal database
            //otherwise load it from database
            try {
                sImageByID.clearParameters();
                sImageByID.setInt(1, image_key);
                try (ResultSet rs = sImageByID.executeQuery()) {
                    if (rs.next()) {
                        i = createImage(rs);
                        //e lo mettiamo anche nella cache
                        //and put it also in the cache
                        dataLayer.getCache().add(Image.class, i);

                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load image by ID", ex);
            }
        }
        return i;
    }

    @Override
    public List<Image> getImages() throws DataException {
        return null;
    }

    @Override
    public List<Image> getImages(Disco disco) throws DataException {
        List<Image> result = new ArrayList<>();
        try {
            sImageByDisco.setInt(1, disco.getKey());
            try (ResultSet rs = sImageByDisco.executeQuery()) {
                while (rs.next()) {
                    result.add(getImage(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load images by disco", ex);
        }
        return result;
    }

}
