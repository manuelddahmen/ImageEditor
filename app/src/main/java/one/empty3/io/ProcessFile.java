package one.empty3.io;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature20220726.PixM;

import java.io.File;
import java.util.*;

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
    protected int maxRes = 0;
    private Properties property;
    private File outputDirectory = null;
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


    protected static boolean isImage(File in) {
        return in != null && (in.getAbsolutePath().toLowerCase().endsWith(".jpg")
                || in.getAbsolutePath().toLowerCase().endsWith(".png"));
    }

}
