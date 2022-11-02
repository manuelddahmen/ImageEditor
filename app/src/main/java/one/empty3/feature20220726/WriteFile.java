package one.empty3.feature20220726;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.util.logging.*;

public class WriteFile {
    static int no = 1;
    static String directory = "./output/";

    public static void init() {

    }

    public static boolean writeNext(BufferedImage imageJpeg, String name) {
        writeNext(name, imageJpeg);
        return true;
    }

    public static void writeNext(String name, BufferedImage imageJpeg) {

        File n = new File(directory);

        n = new File(directory + File.separator + no + "-" + name + ".jpg");
        new File(n.getAbsolutePath().substring(0, n.getAbsolutePath().lastIndexOf('/'))).mkdirs();
        try {

            no++;
            ImageIO.write(imageJpeg, "jpg", n);
            Logger.getLogger(WriteFile.class.toString()).log(Level.INFO, "file written: " + n.getAbsolutePath());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }
}
