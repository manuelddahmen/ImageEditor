package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class IdentNullProcess extends ProcessFile {

    @Override
    public boolean process(File in, File out) {
        PixM pixM = null;
        pixM = PixM.getPixM(Objects.requireNonNull(Objects.requireNonNull(ImageIO.read(in)).bitmap), maxRes);
        ImageIO.write(pixM.getImage(), "jpg", out);
        imagesStack.add(out);
        return true;

    }

}
