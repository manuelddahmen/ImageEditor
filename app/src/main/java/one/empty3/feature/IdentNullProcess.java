package one.empty3.feature;

import one.empty3.io.ProcessFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class IdentNullProcess extends ProcessFile {

    @Override
    public boolean process(File in, File out) {
        try {
            PixM pixM = null;
            pixM =  PixM.getPixM(ImageIO.read(in), maxRes);
            ImageIO.write(pixM.getImage(), "jpg", out);
            images.add(out);
            return true;
        } catch (
                IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
