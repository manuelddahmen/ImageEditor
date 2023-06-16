/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.io;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import one.empty3.feature20220726.PixM;
import one.empty3.feature20220726.ProcessBean;

public abstract class ProcessFile extends ProcessNFiles {
    protected boolean shouldOverwrite = false;
    public ProcessBean bean;
    private File outputDirectory = null;
    private List<File> imagesStack = new ArrayList<>();


    protected static boolean isImage(File in) {
        if( in != null && (in.getAbsolutePath().toLowerCase().endsWith(".jpg")
                || in.getAbsolutePath().toLowerCase().endsWith(".png")))
            return true;
        BufferedImage read = ImageIO.read(in);
        if(read!=null) return true;
        return false;
    }
    public ProcessFile() {
        super();
    }
    public void initProperties(ProcessNFiles processFile) {
        super.initProperties(processFile);
        if(properties==null) {
            properties = new ObjectWithProperties(processFile);
        }
        getProperties().addProperty("maxRes", ObjectWithProperties.ClassTypes.AtomicInt, this.maxRes);
        this.processNFiles.add(this);

    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }



    public abstract boolean process(File in, File out);

    public boolean processMem(PixM in, PixM out) {
        return in != null && out != null;
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

    @Override
    public boolean processFiles(File out, File... ins) {
        return process(ins[0], out);
    }
}
