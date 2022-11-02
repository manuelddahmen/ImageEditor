package one.empty3.feature20220726.opsNto1;

import one.empty3.feature20220726.PixM;
import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class ComposeNto1 extends ProcessFile {
    public boolean addEntry(Composer composer, ProcessFile... processFiles) {
        return false;
    }

    @Override
    public boolean process(File in, File out) {
        try {
            boolean success = false;
            PixM inpix = PixM.getPixM(ImageIO.read(in), maxRes);
            PixM outpix = PixM.getPixM(ImageIO.read(in), maxRes);
            //success = processMem(inpix, outpix);
            ImageIO.write(outpix.getImage(), "jpg", out);
            return success;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
