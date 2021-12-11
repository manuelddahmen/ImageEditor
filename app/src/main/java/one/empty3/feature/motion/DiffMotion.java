package one.empty3.feature.motion;

import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.java.awt.image.BufferedImage;
import one.empty3.feature.motion.Motion;



public class DiffMotion extends Motion {
    @Override
    public BufferedImage process() {
        PixM pixM = processFrame();
        if (pixM == null)
            return null;
        /*for (int c = 0; c < pixM.getCompCount(); c++) {
            pixM.setCompNo(c);
            for (int i = 0; i < pixM.getCompCount(); i++)
                for (int j = 0; j < pixM.getCompCount(); j++) {
                    pixM.set(i, j, Math.abs(pixM.get(i, j)));
                }
        }*/
        return pixM.normalize(-1, 1, 0, 1).normalize(0, 1).getImage();
    }
}
