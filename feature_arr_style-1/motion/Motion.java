package one.empty3.feature_arr_style.motion;

import android.graphics.Bitmap;

import one.empty3.feature_arr_style.Linear;
import one.empty3.feature_arr_style.PixM;

import java.util.ArrayList;
import java.util.List;

/***
 * Motion
 * resize part (+/-/show/hide), move part, rotate part
 */
public abstract class Motion /*extends ProcessFile */ {
    private static final int BUFFER_MAX_FRAMES = 25;
    protected List<Bitmap> frames = new ArrayList<>();

    public boolean addFrame(Bitmap Bitmap) {
        this.frames.add(Bitmap);
        return frames.size() > BUFFER_MAX_FRAMES;
    }

    public PixM processFrame() {
        PixM frame1 = null;
        PixM frame2 = null;
        if (frames.size() == 0 || frames.get(0) == null)
            return null;
        if (frames.size() > 1 && frames.size() < BUFFER_MAX_FRAMES) {

            frame1 = new PixM(frames.get(0));
            frames.remove(0);
        } else if (frames.size() >= BUFFER_MAX_FRAMES) {
            frame1 = new PixM(frames.get(0));
            frames.remove(0);
        } else if (frames.size() == 1) {
            Bitmap Bitmap = frames.get(0);
            if (Bitmap != null)
                return null;
        } else {
            return null;
        }
        if (frames.size() >= 1 && frames.get(0) != null) {
            frame2 = new PixM(frames.get(0));

            Linear linear = new Linear(frame1, frame2, frame1.copy());
            linear.op2d2d(new char[]{'-'}, new int[][]{{1, 0, 2}}, new int[]{2});

            return linear.getImages()[2];
        }

        if (frames.size() > 0 && frames.get(0) != null)
            return new PixM(frames.get(0));
        return null;
    }


    public abstract Bitmap process();
/*
    @Override
    public boolean process(File in, File out) {
        PixM pixM = this.processFrame();
        if (pixM != null) {
            ImageIO.write(pixM.getImage(), "jpg",
        }
    }
*/
}
