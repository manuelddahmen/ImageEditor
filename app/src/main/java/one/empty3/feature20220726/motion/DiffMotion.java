package one.empty3.feature20220726.motion;

import one.empty3.feature20220726.Linear;
import one.empty3.feature20220726.PixM;

import javaAnd.awt.image.BufferedImage;

public class DiffMotion extends Motion {
    @Override
    public javaAnd.awt.image.BufferedImage process(PixM frame1, PixM frame2) {

        Linear linear = new Linear(frame1, frame2, frame1.copy());
        linear.op2d2d(new char[]{'-'}, new int[][]{{1, 0, 2}}, new int[]{2});

        return linear.getImages()[2].normalize(-1, 1, 0, 1).normalize(0, 1).getImage();
    }
}
