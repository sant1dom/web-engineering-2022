package org.webeng.data.dao;

import org.webeng.data.model.Disco;
import org.webeng.data.model.Image;
import org.webeng.framework.data.DataException;

import java.util.List;

public interface ImageDAO {

    Image createImage();

    Image getImage(String filename) throws DataException;

    Image getImage(int disco_key) throws DataException;

    List<Image> getImages() throws DataException;

    List<Image> getImages(Disco disco) throws DataException;

}

