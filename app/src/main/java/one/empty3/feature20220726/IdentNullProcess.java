package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class IdentNullProcess extends ProcessFile {

    @Override
    public boolean process(File in, File out) {
        try {
            PixM pixM = null;
            pixM = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)), maxRes);
            ImageIO.write(pixM.getImage(), "jpg", out);
            addSource(out);
            return true;
        } catch (
                IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
