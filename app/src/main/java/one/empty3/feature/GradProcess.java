package one.empty3.feature;


import one.empty3.feature.GradientFilter;
import one.empty3.feature.Linear;
import one.empty3.feature.M3;
import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import java.io.File;

public class GradProcess extends ProcessFile {

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        File file = in;
        one.empty3.feature.PixM pix;
        try {
            pix = one.empty3.feature.PixM.getPixM(ImageIO.read(file), maxRes);
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            one.empty3.feature.PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();
            one.empty3.feature.Linear linear = new Linear(imagesMatrix[0][0], imagesMatrix[0][1], new PixM(pix.getColumns(), pix.getLines()));
            linear.op2d2d(new char[]{'+'}, new int[][]{{1, 0}}, new int[]{2});
            ImageIO.write(linear.getImages()[2].normalize(0.0, 1.0).getImage(), "jpg", out);

            addSource(out);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
