package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.image.BufferedImage;

import java.io.File;

public class HarrisProcess extends ProcessFile {
    public boolean process(File in, File out) {
        try {
            BufferedImage img = ImageIO.read(in);
            PixM m2 = PixM.getPixM(img, maxRes);
            HarrisToPointInterest h = new HarrisToPointInterest(2, 2);

            m2.applyFilter(h);

            ImageIO.write(m2.normalize(0.0, 1.0).getImage(), "JPEG", out);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
