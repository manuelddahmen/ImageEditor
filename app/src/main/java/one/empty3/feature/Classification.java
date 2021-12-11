package one.empty3.feature;

import one.empty3.feature.PixM;
import one.empty3.feature.SelectPointColorMassAglo;
import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.feature.app.replace.java.awt.image.BufferedImage;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.library.core.lighting.Colors;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;


import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Classification extends ProcessFile {
    Random random = new Random();
    private BufferedImage imageOut;
    private int SIZE = 5;
    private double ratio = 0.3;
    private double threshold = 0.3;


    @Override
    public boolean process(final File in, final File out) {
        if (!in.getName().endsWith(".jpg"))
            return false;
        one.empty3.feature.PixM selectPointColorMassAglo = null;
        BufferedImage read = null;
        read = ImageIO.read(in);
        selectPointColorMassAglo = PixM.getPixM(read, maxRes);
        imageOut = ImageIO.read(in);
        assert selectPointColorMassAglo != null;
        SelectPointColorMassAglo selectPointColorMassAglo1 = new SelectPointColorMassAglo(read.bitmap);
        int color = Color.WHITE;
        for (int i = 0; i < selectPointColorMassAglo1.getColumns(); i += 1)
            for (int j = 0; j < selectPointColorMassAglo1.getLines(); j += 1) {
                selectPointColorMassAglo1.setTmpColor(Colors.random());
                double v = selectPointColorMassAglo1.averageCountMeanOf(i, j, SIZE, SIZE, threshold);
                if (v > ratio) {
                    imageOut.bitmap.setPixel(i, j, color);/*selectPointColorMassAglo.getChosenColor().getRGB()*/
                } else {
                    double[] doubles = Lumiere.getDoubles(read.bitmap.getPixel(i, j));
                    /*for(int c=0; c<3; c++)
                        doubles[c] = doubles[c]/3;
*/
                    imageOut.bitmap.setPixel(i, j, Lumiere.getInt(doubles));
                }
            }

        try {
            ImageIO.write(imageOut, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
