package one.empty3.feature20220726.kmeans;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature20220726.PixM;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/*
 line : l, c, r, g, b
 */
public class MakeDataset {
    public MakeDataset(File image,
                       File outputCsv, int res) {
        try {
            BufferedImage img = ImageIO.read(image);
            PixM pix;
            if (res > 0)

                pix = PixM.getPixM(img, res);
            else
                pix = new PixM(img);

            PrintWriter pw = new PrintWriter(outputCsv);
            for (int l = 0; l < pix.getLines(); l++)
                for (int c = 0; c < pix.getColumns(); c++) {
                    //if (pix.luminance(c, l) > 0.1) { // ADDED
                    pix.setCompNo(0);
                    double r = pix.get(c, l);

                    pix.setCompNo(1);
                    double g = pix.get(c, l);

                    pix.setCompNo(2);
                    double b = pix.get(c, l);

                    pw.println("" + c + " " + l + " " +
                            r + " " + g + " " + b);
                    //}
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeFeature
            (HashMap<Integer, double[]> data,
             File csvout) {
        try {

            PrintWriter pw = new PrintWriter(csvout);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < data.get(i).length; j++) {
                    pw.print(data.get(i)[j] + " ");
                }
                pw.println();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

} 
