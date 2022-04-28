package org.webeng.model;

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

    public String getFilename();

    public void setFilename(String imageFilename);
}
