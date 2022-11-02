package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

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
        PixM pix = null;
        if (!in.getName().endsWith(".jpg"))
            return false;

        try {
            pix = new PixM(ImageIO.read(in));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }


        LocalExtrema le = new LocalExtrema(
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
