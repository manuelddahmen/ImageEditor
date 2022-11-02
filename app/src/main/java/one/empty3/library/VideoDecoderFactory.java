package one.empty3.library;

import java.io.File;

public class VideoDecoderFactory {
    /***
     * Creates and start video decoder (frame by frame, no sound)
     * @param f Movie
     * @param m Texture extends TextureMov
     * @return VideoDecoder thread instance
     */
    public static VideoDecoder createInstance(File f, TextureMov m) {
        VideoDecoder decode =
                new VideoDecoder(f, m) {
                    @Override
                    public int size() {
                        return super.size();
                    }
                };
        decode.start();
        return decode;
    }


}

