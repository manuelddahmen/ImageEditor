package one.empty3.feature20220726;


import javaAnd.awt.image.imageio.ImageIO;

import one.empty3.io.ProcessFile;

import java.io.File;

import javaAnd.awt.image.BufferedImage;

public class TrueHarrisProcess extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))

            return false;

        File file = in;

        PixM pix = null;
        BufferedImage img = null;

        try {
            img = ImageIO.read(file);
            pix = PixM.getPixM(img, maxRes);

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;

            // assertTrue(false);


        }

        TrueHarris th = new TrueHarris(pix);
        PixM pixM = new PixM(pix.getImage());
        for (int c = 0; c < 3; c++) {
            th.setCompNo(c);
            pixM.setCompNo(c);
            pix.setCompNo(c);
            for (int i = 0; i < pix.getColumns(); i++)
                for (int j = 0; j < pix.getLines(); j++)
                    pixM.set(i, j, th.filter(i, j));
        }


        PixM normalize = pixM.normalize(0.0, 1.0);


        //


        try {

            ImageIO.write(normalize.getImage(), "JPEG", out);
            return true;
        } catch (Exception ex) {

        }
        return true;
    }

} 
