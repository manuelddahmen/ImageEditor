package one.empty3.feature.motion;

import one.empty3.feature.ClassSchemaBuilder;
import one.empty3.feature.Linear;
import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.java.awt.image.BufferedImage;


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

    public PixM processFrame() {
        PixM frame1 = null;
        PixM frame2 = null;
        if (frames.size() == 0 || frames.get(0) == null)
            return null;
        if (frames.size() >= 2 && frames.size() < BUFFER_MAX_FRAMES) {

            frame1 = new PixM(frames.get(0));
            frame2 = new PixM(frames.get(1));
            frames.remove(0);
            if (frames.size() > 2)
                frames.remove(0);
        } else if (frames.size() >= BUFFER_MAX_FRAMES) {
            frame1 = new PixM(frames.get(0));
            frame2 = new PixM(frames.get(1));
            frames.remove(0);
        } else {
            return null;
        }
        Linear linear = new Linear(frame1, frame2, frame1.copy());
        linear.op2d2d(new char[]{'-'}, new int[][]{{1, 0, 2}}, new int[]{2});

        return linear.getImages()[2];

    }


    public abstract BufferedImage process();
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
