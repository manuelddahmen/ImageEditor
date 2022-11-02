package one.empty3.feature20220726;

import one.empty3.feature20220726.motion.Motion;

import javaAnd.awt.image.BufferedImage;

public
class EffectMotion extends Motion {
    @Override
    public javaAnd.awt.image.BufferedImage process(PixM frame1, PixM frame2) {
        return frame2.getImage();
    }
}
