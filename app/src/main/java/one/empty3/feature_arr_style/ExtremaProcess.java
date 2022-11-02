
package one.empty3.feature_arr_style;

import android.graphics.Bitmap;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.util.Objects;

import one.empty3.io.ProcessFile;

public class ExtremaProcess extends ProcessFile {
    private boolean setMin = true;
    private final int pointsCount;
    private final int neighbourSize;
    protected double sub[];
    private double threshold = 0.5;


    public ExtremaProcess() {
        this.neighbourSize = 8;//neighbourSize;
        this.pointsCount = 1; //pointsCount;
        //sub = new double[4*lines*columns];
    }

    public boolean process(File in, File out) {
        PixM pix = null;
        if (!in.getName().endsWith(".jpg"))
            return false;

        try {
            Bitmap read = Objects.requireNonNull(ImageIO.read(in)).bitmap;
            if (read != null)
                pix = PixM.getPixM(read, -10.0);
            else
                System.err.println("Error read bitmap ImageIO.read==null");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }


        LocalExtrema le = new LocalExtrema(
                pix.getColumns(), pix.getLines(),
                3, 0);


        PixM m = le.filter(new M3(pix, 1, 1)).getImagesMatrix()[0][0];

        try {
            ImageIO.write(m.getImage(), "jpg", out);
            return true;
        } catch (Exception ex) {
            return false;
        }


    }


}
