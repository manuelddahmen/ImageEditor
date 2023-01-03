/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
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
