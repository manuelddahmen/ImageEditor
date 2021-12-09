package one.empty3.feature;

import one.empty3.feature.LocalExtrema;
import one.empty3.feature.M3;
import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;

import javax.imageio.ImageIO;
import java.io.File;

public class ExtremaProcess extends ProcessFile {
    private boolean setMin = true;
    private final int pointsCount;
    private final int neighbourSize;
    protected double sub[];
    private double threshold = 0.5;


    public ExtremaProcess() {
        this.neighbourSize = 3;//neighbourSize;
        this.pointsCount = 1; //pointsCount;
        //sub = new double[4*lines*columns];
    }

    public boolean process(File in, File out) {
        one.empty3.feature.PixM pix = null;
        if (!in.getName().endsWith(".jpg"))
            return false;

        try {
            pix = new one.empty3.feature.PixM(ImageIO.read(in));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }


        one.empty3.feature.LocalExtrema le = new LocalExtrema(
                pix.getColumns(), pix.getLines(),
                neighbourSize, pointsCount);


        PixM m = le.filter(new M3(pix, 1, 1)).getImagesMatrix()[0][0];

        try {
            ImageIO.write(m.normalize(0, 1).getImage(), "jpg", out);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


    }


}
