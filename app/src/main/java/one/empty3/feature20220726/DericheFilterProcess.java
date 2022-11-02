package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DericheFilterProcess extends ProcessFile {


    @Override
    public boolean process(File in, File out) {
        try {
            PixM pixM = PixM.getPixM(ImageIO.read(in), maxRes);


            ImageIO.write(pixM.getImage(), "jpg", out);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
