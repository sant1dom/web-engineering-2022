package org.webeng.data.impl;

import org.webeng.data.model.Image;
import org.webeng.framework.data.DataItemImpl;

import java.io.InputStream;

public class ImageImpl extends DataItemImpl<Integer> implements Image {
    private InputStream imageData;
    private String imageType;
    private long imageSize;
    private String fileName;

    public ImageImpl(InputStream imageData, String imageType, long imageSize, String fileName) {
        super();
        this.imageData = imageData;
        this.imageType = imageType;
        this.imageSize = imageSize;
        this.fileName = fileName;
    }

    public ImageImpl() {
        super();
        this.imageData = null;
        this.imageType = "";
        this.imageSize = 0;
        this.fileName = "";
    }

    @Override
    public InputStream getImageData() {
        return imageData;
    }

    @Override
    public void setImageData(InputStream imageData) {
        this.imageData = imageData;
    }

    @Override
    public String getImageType() {
        return imageType;
    }

    @Override
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @Override
    public long getImageSize() {
        return imageSize;
    }

    @Override
    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
