package one.empty3.feature_arr_style;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;

public class DericheFilterProcess extends ProcessFile {


    @Override
    public boolean process(File in, File out) {
        PixM pixM = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)).bitmap, maxRes);


        ImageIO.write(pixM.getImage(), "jpg", out);


        return false;
    }
}
