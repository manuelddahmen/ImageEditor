package one.empty3.feature;


import one.empty3.feature.GradientFilter;
import one.empty3.feature.Linear;
import one.empty3.feature.M3;
import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import java.io.File;

/*
 * Magnitude² filter
 */
public class MagnitudeProcess extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        File file = in;
        one.empty3.feature.PixM pix;
        try {
            pix = one.empty3.feature.PixM.getPixM(ImageIO.read(file), maxRes);
            one.empty3.feature.GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            one.empty3.feature.PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();
            one.empty3.feature.Linear linearProd1 = new one.empty3.feature.Linear(imagesMatrix[0][0], imagesMatrix[0][0],
                    new one.empty3.feature.PixM(pix.getColumns(), pix.getLines()));
            linearProd1.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
            one.empty3.feature.Linear linearProd2 = new one.empty3.feature.Linear(imagesMatrix[0][1], imagesMatrix[0][1],
                    new one.empty3.feature.PixM(pix.getColumns(), pix.getLines()));
            linearProd2.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
            one.empty3.feature.Linear res = new Linear(linearProd1.getImages()[2], linearProd2.getImages()[2],
                    new PixM(pix.getColumns(), pix.getLines()));
            res.op2d2d(new char[]{'+'}, new int[][]{{1, 0}}, new int[]{2});
            ImageIO.write(res.getImages()[2].normalize(0.0, 1.0).getImage(), "jpg", out);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
