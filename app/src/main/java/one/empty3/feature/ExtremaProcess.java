
package one.empty3.feature;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import java.io.File;

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
            pix = PixM.getPixM(ImageIO.read(in), -10.0);
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
