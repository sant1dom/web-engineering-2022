package org.webeng.collector_site.data.dao;


import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Image;
import org.webeng.collector_site.data.proxy.ImageProxy;
import org.webeng.framework.data.DAO;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO_MySQL extends DAO implements ImageDAO {
    private PreparedStatement sImageByID, sImageByDisco, sImageByFileName, sImages, iImages, dImagesByDisco;

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
            sImages = connection.prepareStatement("SELECT id FROM image");
            dImagesByDisco = connection.prepareStatement("DELETE FROM image WHERE id=? AND disco_id=?");
            iImages = connection.prepareStatement("INSERT INTO image (size, type, filename, disco_id, version) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            sImageByID.close();
            sImageByDisco.close();
            dImagesByDisco.close();
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
        ImageProxy i = (ImageProxy) createImage();
        try {
            i.setKey(rs.getInt("id"));
            i.setImageSize(rs.getLong("size"));
            i.setImageType(rs.getString("type"));
            i.setFileName(rs.getString("filename"));
            i.setVersion(rs.getLong("version"));
            i.setDiscoKey(rs.getInt("disco_id"));
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
        List<Image> images = new ArrayList<>();
        try (ResultSet rs = sImages.executeQuery()) {
            while (rs.next()) {
                images.add(getImage(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load images", ex);
        }
        return images;
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

    @Override
    public void storeImages(List<Image> images) throws DataException {
        try {
            for (Image image : images) {
                iImages.setInt(1, (int) image.getImageSize());
                iImages.setString(2, image.getImageType());
                iImages.setString(3, image.getFileName());
                iImages.setInt(4, image.getDisco().getKey());
                iImages.setInt(5, 1);
                iImages.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store images", ex);
        }
    }

    @Override
    public void deleteImage(Disco disco, Image image) throws DataException {
        try {
            dImagesByDisco.setInt(1, image.getKey());
            dImagesByDisco.setInt(2, disco.getKey());
            dImagesByDisco.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete image", ex);
        }

    }

}
