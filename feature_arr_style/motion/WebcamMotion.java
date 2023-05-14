package one.empty3.feature_arr_style.motion;

import android.graphics.Bitmap;

public class WebcamMotion extends Motion {

    @Override
    public Bitmap process() {
        return processFrame().getImage();
    }
}
