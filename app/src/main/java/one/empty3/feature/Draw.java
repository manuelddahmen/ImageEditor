package one.empty3.feature;

import java.io.File;
import java.util.Objects;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;

import one.empty3.io.ProcessFile;

public class Draw extends ProcessFile {
    public Draw() {

    }

    public boolean process(File in, File out) {
        try {
            ImageIO.write(Objects.requireNonNull(ImageIO.read(in)), "jpg", out);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


        return true;
    }
}
