package org.webeng.data.dao;

import org.webeng.data.model.Disco;
import org.webeng.data.model.Image;
import org.webeng.data.proxy.DiscoProxy;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface ImageDAO {
    Image getImage(String filename) throws DataException;

    Image getImage(int disco_key) throws DataException;

    void storeImage(Image image) throws DataException;

    List<Image> getImages() throws DataException;

    List<Image> getImages(Disco disco) throws DataException;

}

