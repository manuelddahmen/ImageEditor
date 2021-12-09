package one.empty3.library;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.AWTFrameGrab;
import org.jcodec.codecs.h264.H264Utils;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.common.tools.MainUtils;
import org.jcodec.containers.mp4.Brand;
import org.jcodec.containers.mp4.MP4Packet;
import org.jcodec.containers.mp4.MP4TrackType;
//import org.jcodec.containers.mp4.muxer.FramesMP4MuxerTrack;
import org.jcodec.containers.mp4.muxer.MP4Muxer;
import org.jcodec.scale.AWTUtil;
import org.jcodec.scale.ColorUtil;
import org.jcodec.scale.Transform;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
public class DecodeJcodec extends VideoDecoder {

public DecodeJcodec(File f, TextureMov tex) {
     super(f,tex);
}
    double startSec = 51.632;
    long frameCount = MAXSIZE;
public void run() {

    int maxFrames = (int)MAXSIZE;
    FileChannelWrapper in = null;
    try {
        AWTFrameGrab fg = null;
            in = NIOUtils.readableChannel(file);
        try {
            fg = AWTFrameGrab.createAWTFrameGrab(in);
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
        }
        int i=0;
        int j=-1;
        BufferedImage frame = null;
        while ( frame==null || i!=j) {
            j = i;
            if(imgBuf.size()>MAXSIZE) {
                try {
                    assert fg != null;
                    frame = fg.getFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert frame != null;
                imgBuf.add(new ECBufferedImage(frame));
                i++;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }

        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } finally {
        NIOUtils.closeQuietly(in);
    }
}
 
}