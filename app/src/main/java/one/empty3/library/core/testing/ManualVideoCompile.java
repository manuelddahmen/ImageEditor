//package one.empty3.library.core.testing;
//
//import android.graphics.Color;
//import one.empty3.feature.app.replace.java.awt.Graphics;
//import android.graphics.Bitmap;
//import java.io.File;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.xuggle.xuggler.ICodec;
//import com.xuggle.xuggler.IContainer;
//import com.xuggle.xuggler.IPacket;
//import com.xuggle.xuggler.IPixelFormat;
//import com.xuggle.xuggler.IRational;
//import com.xuggle.xuggler.IStream;
//import com.xuggle.xuggler.IStreamCoder;
//import com.xuggle.xuggler.IVideoPicture;
//import com.xuggle.xuggler.demos.DecodeAndPlayVideo;
//import com.xuggle.xuggler.video.ConverterFactory;
//import com.xuggle.xuggler.video.IConverter;
//
//public class ManualVideoCompile {
//	public ManualVideoCompile(){}
//	private static final Logger logger = LoggerFactory.getLogger(ManualVideoCompile.class);
//
//	private static final String OUTPUT_FILE = "/home/rfkrocktk/Desktop/out.flv";
//
//	IStreamCoder videoStreamCoder;
//        IContainer container;
//        double positionInMicroseconds;
//        int frameRate;
//
//
//	public void init(String OUTPUT_FILE, int width, int height, int frameRate, int bitrate) {
//if(bitrate<=0)
//     bitrate = 350000;
//		logger.debug("Compiling an empty stream to {}", OUTPUT_FILE);
//
//		File outputFile = new File(OUTPUT_FILE+".flv");
//
//		//if (outputFile.exists())
//		//	outputFile.delete();
//
////		open a container
//		container = IContainer.make();
//		container.open(OUTPUT_FILE, IContainer.Type.WRITE, null);
//
////		create the video stream and get its coder
//		ICodec videoCodec = ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_H264);
//		IStream videoStream = container.addNewStream(videoCodec);
//	        videoStreamCoder = videoStream.getStreamCoder();
//		IRational i = IRational.make(1, frameRate);
////		setup the stream coder
//		//frameRate = (int)IRational.make(1, frameRate);
//
//		videoStreamCoder.setWidth(width);
//		videoStreamCoder.setHeight(height);
//		videoStreamCoder.setFrameRate(i);
//		videoStreamCoder.setTimeBase(IRational.make(i.getDenominator(),
//				i.getNumerator()));
//		videoStreamCoder.setBitRate(bitrate);
//		videoStreamCoder.setNumPicturesInGroupOfPictures(frameRate);
//		videoStreamCoder.setPixelType(IPixelFormat.Type.YUV420P);
//		videoStreamCoder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, true);
//		videoStreamCoder.setGlobalQuality(0);
//
////		open the coder first
//		videoStreamCoder.open(null, null);
//
////		write the header
//		container.writeHeader();
//
////		let us begin
//		positionInMicroseconds = 0;
//		}
//public void frame(BufferedImageAndroid in) {
////              encode 30 frames, right
////			create a green box with a 50 pixel border for the frame image
//			BufferedImageAndroid outputImage = new BufferedImageAndroid(videoStreamCoder.getWidth(),
//					videoStreamCoder.getHeight(), BufferedImageAndroid.TYPE_INT_ARGB);
//
//			Graphics graphics = outputImage.getGraphics();
//			graphics.setColor(Color.GREEN);
//			graphics.drawImage(in, 0, 0,
//					videoStreamCoder.getWidth() - 100,
//					videoStreamCoder.getHeight() - 100, null);
//
//			outputImage = convert(outputImage, BufferedImageAndroid.TYPE_3BYTE_BGR);
//
////			now, create a packet
//			IPacket packet = IPacket.make();
//
//			IConverter converter = ConverterFactory.createConverter(outputImage,
//					videoStreamCoder.getPixelType());
//
//			IVideoPicture frame = converter.toPicture(outputImage, (long)positionInMicroseconds);
//			frame.setQuality(0);
//
//			if (videoStreamCoder.encodeVideo(packet, frame, 0) < 0) {
//				throw new RuntimeException("Unable to encode video.");
//			}
//
//			if (packet.isComplete()) {
//				if (container.writePacket(packet) < 0) {
//					throw new RuntimeException("Could not write packet to container.");
//				}
//			}
//
////			after all this, increase the timestamp by one frame (in microseconds)
//			positionInMicroseconds += (frameRate * Math.pow(1000, 2));
//		}
//    public void end() {
////		done, so now let's wrap this up.
//		container.writeTrailer();
//
//		videoStreamCoder.close();
////		container.flushPackets();
//		container.close();
//	}
//
//	private static BufferedImageAndroid convert(BufferedImageAndroid value, int type) {
//		if (value.getType() == type)
//			return value;
//
//		BufferedImageAndroid result = new BufferedImageAndroid(value.getWidth(), value.getHeight(),
//				type);
//
//		result.getGraphics().drawImage(value, 0, 0, null);
//
//		return result;
//	}
//}
