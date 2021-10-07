package one.empty3.feature.motion;

import android.graphics.Bitmap;

public class WebcamMotion extends Motion {

    @Override
    public Bitmap process() {
        return processFrame().getImage();
    }
}
