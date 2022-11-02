package one.empty3.feature_arr_style.motion;

import one.empty3.feature_arr_style.PixM;

import android.graphics.Bitmap;

public class DiffMotion extends Motion {
    @Override
    public Bitmap process() {
        PixM pixM = processFrame();
        if (pixM == null)
            return null;
        for (int c = 0; c < pixM.getCompCount(); c++) {
            pixM.setCompNo(c);
            for (int i = 0; i < pixM.getCompCount(); i++)
                for (int j = 0; j < pixM.getCompCount(); j++) {
                    pixM.set(i, j, Math.abs(pixM.get(i, j)));
                }
        }
        return pixM.normalize(-1, 1).getImage();
    }
}
