/*
 * Copyright (c) 2023.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro;

import java.io.File;

class DataApp {
    private int maxRes;
    private File image;
    private File originalSizeImage;
    private boolean isOriginalSize;

    public DataApp(int maxRes, File image, File originalSizeImage, boolean isOriginalSize) {
        this.maxRes = maxRes;
        this.image = image;
        this.originalSizeImage = originalSizeImage;
        this.isOriginalSize = isOriginalSize;
    }

    public int getMaxRes() {
        return maxRes;
    }

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public File getOriginalSizeImage() {
        return originalSizeImage;
    }

    public void setOriginalSizeImage(File originalSizeImage) {
        this.originalSizeImage = originalSizeImage;
    }

    public boolean isOriginalSize() {
        return isOriginalSize;
    }

    public void setOriginalSize(boolean originalSize) {
        isOriginalSize = originalSize;
    }
}
