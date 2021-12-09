package one.empty3.feature;

import one.empty3.feature.GaussFilterPixM;
import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;

import java.io.File;


public class GaussFilterProcess extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        if (!in.getName().endsWith(".jpg"))

            return false;

        one.empty3.feature.PixM pix = null;
        BufferedImage img = null;

        try {
            img = ImageIO.read(in);
            pix = one.empty3.feature.PixM.getPixM(img, maxRes);

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;

            // assertTrue(false);


        }

        one.empty3.feature.GaussFilterPixM th = new GaussFilterPixM(pix, 1);

        one.empty3.feature.PixM pixRes = new one.empty3.feature.PixM(pix.getColumns(), pix.getLines());
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

