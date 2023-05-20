//package one.empty3.feature_arr_style;
//
//import one.empty3.io.ProcessFile;
//
//import java.io.File;
//import android.graphics.Color;
//import android.graphics.Bitmap;
//import javaAnd.awt.image.imageio.ImageIO;//
//public class IsleProcess extends ProcessFile {
//    public boolean process(File in, File out) {
//
//
//        if (!in.getName().endsWith(".jpg"))
//
//            return false;
//
//        File file = in;
//
//        PixM pix = null;
//        Bitmap img = null;
//        try {
//            img = ImageIO.read(file);
//            pix = PixM.getPixM(img, -10.0);
//
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//
//            return false;
//
//            // assertTrue(false);
//
//
//        }
//
//        IsleFilterPixM il = new IsleFilterPixM
//                (pix);
//        il.setCValues(Color.BLUE, Color.WHITE, 0.4);
//        il.filter();
//        try {
//
//            ImageIO.write(pix.getImage(), "JPEG", out);
//
//        } catch (Exception ex) {
//
//        }
//
//        return false;
//    }
//}
