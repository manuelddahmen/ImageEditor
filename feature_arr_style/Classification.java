package one.empty3.feature_arr_style;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.library.core.lighting.Colors;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import java.io.File;
import java.util.Random;

public class Classification extends ProcessFile {
    Random random = new Random();
    private Bitmap imageOut;
    private int SIZE = 5;
    private double ratio = 0.3;
    private double threshold = 0.3;


    @Override
    public boolean process(final File in, final File out) {
        if (!in.getName().endsWith(".jpg"))
            return false;
        PixM selectPointColorMassAglo = null;
        Bitmap read = null;
        read = ImageIO.read(in).bitmap;
        selectPointColorMassAglo = PixM.getPixM(read, maxRes);
        imageOut = ImageIO.read(in).bitmap;
        assert selectPointColorMassAglo != null;
        SelectPointColorMassAglo selectPointColorMassAglo1 = new SelectPointColorMassAglo(read);
        int color = Color.WHITE;
        for (int i = 0; i < selectPointColorMassAglo1.getColumns(); i += 1)
            for (int j = 0; j < selectPointColorMassAglo1.getLines(); j += 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    selectPointColorMassAglo1.setTmpColor(Colors.random().toArgb());
                }
                double v = selectPointColorMassAglo1.averageCountMeanOf(i, j, SIZE, SIZE, threshold);
                if (v > ratio) {
                    imageOut.setPixel(i, j, color);/*selectPointColorMassAglo.getChosenColor().toARGB()*/
                } else {
                    double[] doubles = Lumiere.getDoubles(read.getPixel(i, j));
                    /*for(int c=0; c<3; c++)
                        doubles[c] = doubles[c]/3;
*/
                    imageOut.setPixel(i, j, Lumiere.getInt(doubles));
                }
            }

        ImageIO.write(imageOut, "jpg", out);
        return true;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
