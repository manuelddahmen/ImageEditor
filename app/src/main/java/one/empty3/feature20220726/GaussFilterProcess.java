package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.image.BufferedImage;

import java.io.File;


public class GaussFilterProcess extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        if (!in.getName().endsWith(".jpg"))

            return false;

        PixM pix = null;
        BufferedImage img = null;

        try {
            img = ImageIO.read(in);
            pix = PixM.getPixM(img, maxRes);

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;

            // assertTrue(false);


        }

        GaussFilterPixM th = new GaussFilterPixM(pix, 1);

        PixM pixRes = new PixM(pix.getColumns(), pix.getLines());
        for (int c = 0; c < 3; c++) {
            th.setCompNo(c);
            pix.setCompNo(c);
            pixRes.setCompNo(c);
            for (int i = 0; i < pix.getColumns(); i++)
                for (int j = 0; j < pix.getLines(); j++)
                    pixRes.set(i, j, th.filter(i, j));
        }


        PixM normalize = pix.normalize(0.0, 1.0);


        //


        try {

            ImageIO.write(normalize.getImage(), "JPEG", out);
            return true;
        } catch (Exception ex) {

        }
        return true;
    }

}

