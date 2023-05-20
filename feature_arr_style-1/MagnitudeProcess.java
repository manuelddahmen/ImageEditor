package one.empty3.feature_arr_style;


import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.util.Objects;

/***
 * Magnitude² filter
 */
public class MagnitudeProcess extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        File file = in;
        PixM pix;
        try {
            pix = PixM.getPixM(Objects.requireNonNull(Objects.requireNonNull(ImageIO.read(file)).bitmap), maxRes);
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();
            Linear linearProd1 = new Linear(imagesMatrix[0][0], imagesMatrix[0][0],
                    new PixM(pix.getColumns(), pix.getLines()));
            linearProd1.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
            Linear linearProd2 = new Linear(imagesMatrix[0][1], imagesMatrix[0][1],
                    new PixM(pix.getColumns(), pix.getLines()));
            linearProd2.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
            Linear res = new Linear(linearProd1.getImages()[2], linearProd2.getImages()[2],
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
