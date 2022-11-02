package one.empty3.feature20220726;


import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;

public class GradProcess extends ProcessFile {

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public boolean process(File in, File out) {

        //if (!in.getName().endsWith(".jpg"))
        //    return false;
        File file = in;
        PixM pix;
        try {
            pix = PixM.getPixM(ImageIO.read(file), maxRes);
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();
            Linear linear = new Linear(imagesMatrix[0][0], imagesMatrix[0][1], new PixM(pix.getColumns(), pix.getLines()));
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
