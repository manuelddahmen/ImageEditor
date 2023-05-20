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

package one.empty3.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature20220726.PixM;

public abstract class ProcessFile {
    //    public InterfaceMatrix matrix(Bitmap bitmap) {
//        return MFactory.getInstance(bitmap);
//    }
//    public InterfaceMatrix matrix(File in, boolean isBitmap) {
//        return MFactory.getInstance(in, isBitmap);
//    }
//    public InterfaceMatrix matrix(Bitmap bitmap, int maxRes) {
//        return MFactory.getInstance(bitmap, maxRes);
//    }
//    public InterfaceMatrix matrix(int columns, int lines, boolean isBitmap) {
//        return MFactory.getInstance(lines, columns, isBitmap);
//    }
    protected int maxRes = 1280;
    private Properties property;
    private File outputDirectory = null;
    public boolean shouldOverwrite = true;
    protected List<File> imagesStack = new ArrayList<>();

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public PixM getSource(String s) {
        Properties p = getProperty();
        String property = p.getProperty(s);
        File file = new File(property);
        BufferedImage read = null;
        read = ImageIO.read(file);
        return (new PixM(read.bitmap));
    }

    private Properties getProperty() {
        return property;
    }

    public void setProperty(Properties property) {
        this.property = property;
    }

    public boolean process(File in, File out) {
        return false;
    }


    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public File getStackItem(int index) {
        System.out.printf("STACK %d : %s", index, imagesStack.get(index));
        return imagesStack.get(index);
    }

    public void setStack(List<File> files1) {
        this.imagesStack = files1;
    }

    public void addSource(File fo) {
        imagesStack.add(fo);
    }


    public boolean isImage(File in) {
        if(in.exists() && in.isFile()) {
            BufferedImage read = ImageIO.read(in);
            return read != null && read.bitmap != null;
        } else {
            return false;
        }
    }

}
