package one.empty3.io;

import one.empty3.feature.PixM;

import one.empty3.feature.app.replace.java.awt.image.BufferedImage;
import  one.empty3.feature.app.replace.javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProcessFile {
    protected int maxRes = 400;
    private Properties property;
    private File outputDirectory = null;
    private List<File> imagesStack = new ArrayList<>();

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public PixM getSource(String s) {
        try {
            Properties p = getProperty();
            String property = p.getProperty(s);
            File file = new File(property);
            BufferedImage read = null;
            read = ImageIO.read(file);
            return (new PixM(read));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}
