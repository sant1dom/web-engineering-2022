package org.webeng.collector_site.data.dao;

import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Image;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface ImageDAO {

    Image createImage();

    Image getImage(String filename) throws DataException;

    Image getImage(int disco_key) throws DataException;

    List<Image> getImages() throws DataException;

    List<Image> getImages(Disco disco) throws DataException;

    void storeImages(List<Image> images) throws DataException;

}

