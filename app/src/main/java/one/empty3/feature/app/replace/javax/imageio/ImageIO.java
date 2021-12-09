package one.empty3.feature.app.replace.javax.imageio;

//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIO {
    public static one.empty3.feature.app.replace.one.empty3.feature.app.replace.java.awt.image.BufferedImage read(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            one.empty3.feature.app.replace.one.empty3.feature.app.replace.java.awt.image.BufferedImage bitmap2 =  one.empty3.feature.app.replace.javax.imageio.ImageIO.read(fileInputStream);
            fileInputStream.close();
            return bitmap2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(BufferedImage imageOut, String jpg, File out) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(out);
         one.empty3.feature.app.replace.javax.imageio.ImageIO.write(imageOut, out.getName().substring(-3), out);
        fileOutputStream.close();
        return false;
    }
    /*
    public static Bitmap read(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(Bitmap imageOut, String jpg, File out) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        imageOut.compress(Bitmap.CompressFormat.JPEG, 10, fileOutputStream);
        fileOutputStream.close();
        return false;
    }*/
}
