package one.empty3.feature_arr_style;


import one.empty3.io.ProcessFile;

import java.io.File;
import java.util.Objects;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.library.Point3D;

public class GradProcess extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        PixM pix;
        try {
            if (maxRes == 0) {
                pix = new PixM((Objects.requireNonNull(Objects.requireNonNull(ImageIO.read(in)).bitmap)), true);
            } else {
                pix = PixM.getPixM(Objects.requireNonNull(Objects.requireNonNull(ImageIO.read(in)).bitmap), maxRes);
            }
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();
            Linear linear = new Linear(imagesMatrix[0][0], imagesMatrix[0][1], new PixM(pix.getColumns(), pix.getLines()));
            linear.op2d2d(new char[]{'+'}, new int[][]{{1, 0}}, new int[]{2});
            ImageIO.write(linear.getImages()[2].normalize(0.0, 1.0).getImage(), "jpg", out);

            imagesStack.add(out);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
