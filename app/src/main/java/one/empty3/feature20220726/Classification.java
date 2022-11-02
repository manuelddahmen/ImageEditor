package one.empty3.feature20220726;

import javaAnd.awt.Color;
import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Classification extends ProcessFile {
    Random random = new Random();
    private javaAnd.awt.image.BufferedImage imageOut;
    private int SIZE = 5;
    private double ratio = 0.3;
    private double threshold = 0.3;


    @Override
    public boolean process(final File in, final File out) {
        if (!in.getName().endsWith(".jpg"))
            return false;
        PixM selectPointColorMassAglo = null;
        BufferedImage read = null;
        read = new BufferedImage(Objects.requireNonNull(ImageIO.read(in)).bitmap);
        selectPointColorMassAglo = PixM.getPixM(read, maxRes);
        imageOut = ImageIO.read(in);
        assert selectPointColorMassAglo != null;
        SelectPointColorMassAglo selectPointColorMassAglo1 = new SelectPointColorMassAglo(read);
        int color = Color.WHITE;
        for (int i = 0; i < selectPointColorMassAglo1.getColumns(); i += 1)
            for (int j = 0; j < selectPointColorMassAglo1.getLines(); j += 1) {
                selectPointColorMassAglo1.setTmpColor(Color.random());
                double v = selectPointColorMassAglo1.averageCountMeanOf(i, j, SIZE, SIZE, threshold);
                if (v > ratio) {
                    imageOut.setRGB(i, j, color);/*selectPointColorMassAglo.getChosenColor().getRGB()*/
                } else {
                    double[] doubles = Lumiere.getDoubles(read.getRGB(i, j));
                    /*for(int c=0; c<3; c++)
                        doubles[c] = doubles[c]/3;
*/
                    imageOut.setRGB(i, j, Lumiere.getInt(doubles));
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
