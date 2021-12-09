package one.empty3.feature;

import one.empty3.io.*;

import java.io.File;
import javax.imageio.ImageIO;

public class Draw extends ProcessFile {
    public Draw() {

    }

    public boolean process(File in, File out) {
        try {
            ImageIO.write(ImageIO.read(in), "jpg", out);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


        return true;
    }
}
