package org.webeng.collector_site.data.proxy;

import org.webeng.collector_site.data.dao.DiscoDAO;
import org.webeng.collector_site.data.impl.ImageImpl;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.framework.data.DataException;
import org.webeng.framework.data.DataItemProxy;
import org.webeng.framework.data.DataLayer;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageProxy extends ImageImpl implements DataItemProxy {
    protected boolean modified;
    protected int disco_key;

    protected DataLayer dataLayer;

    public ImageProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
        this.disco_key = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public Disco getDisco() {
        if(super.getDisco() == null && disco_key > 0) {
            try {
                super.setDisco(((DiscoDAO) dataLayer.getDAO(Disco.class)).getDisco(disco_key));
            } catch (DataException ex) {
                Logger.getLogger(ImageProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getDisco();
    }

    @Override
    public void setImageData(InputStream imageData) {
        super.setImageData(imageData);
        this.modified = true;
    }

    @Override
    public void setImageType(String imageType) {
        super.setImageType(imageType);
        this.modified = true;
    }

    @Override
    public void setImageSize(long imageSize) {
        super.setImageSize(imageSize);
        this.modified = true;
    }

    @Override
    public void setFileName(String fileName) {
        super.setFileName(fileName);
        this.modified = true;
    }

    @Override
    public void setDisco(Disco disco) {
        super.setDisco(disco);
        if(disco != null) {
            this.disco_key = disco.getKey();
        } else {
            this.disco_key = 0;
        }
        this.modified = true;
    }

    //METODI DEL PROXY
    //PROXY-ONLY METHODS
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    public void setDiscoKey(int disco_key) {
        this.disco_key = disco_key;
        super.setDisco(null);
    }
}
