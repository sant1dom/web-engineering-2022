package org.webeng.collector_site.data.model;

import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItem;

import java.io.InputStream;

public interface Image extends DataItem<Integer> {

    InputStream getImageData() throws DataException;

    void setImageData(InputStream is) throws DataException;

    String getImageType();

    void setImageType(String type);

    long getImageSize();

    void setImageSize(long size);

    String getFileName();

    void setFileName(String imageFilename);

    Disco getDisco();

    void setDisco(Disco disco);

}
