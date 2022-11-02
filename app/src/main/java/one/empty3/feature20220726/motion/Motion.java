package one.empty3.feature20220726.motion;

import one.empty3.feature20220726.PixM;

import javaAnd.awt.image.BufferedImage;

import java.util.ArrayList;

/*
 * Motion
 * resize part (+/-/show/hide), move part, rotate part
 */
public abstract class Motion /*extends ProcessFile */ {
    public static final int BUFFER_MAX_FRAMES = 26;
    public ArrayList<BufferedImage> frames = new ArrayList<>();


    public boolean addFrame(BufferedImage bufferedImage) {
        if (bufferedImage != null) {
            this.frames.add(bufferedImage);
        }
        return frames.size() > BUFFER_MAX_FRAMES;
    }

    public javaAnd.awt.image.BufferedImage processFrame() {
        PixM frame1 = null;
        PixM frame2 = null;
        if (frames.size() == 0 || frames.get(0) == null)
            return null;
        if (frames.size() >= 2 && frames.size() < BUFFER_MAX_FRAMES) {

            frame1 = new PixM(frames.get(0));
            frame2 = new PixM(frames.get(1));
            frames.remove(0);
        } else if (frames.size() >= BUFFER_MAX_FRAMES) {
            frame1 = new PixM(frames.get(0));
            frame2 = new PixM(frames.get(1));
            frames.remove(0);
        } else {
            return null;
        }

        return process(frame1, frame2);
    }

    public abstract javaAnd.awt.image.BufferedImage process(PixM frame1, PixM frame2);

}
