package javax.imageio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageIO {
    public static Bitmap read(File file) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(file)) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(Bitmap imageOut, String jpg, File out) throws FileNotFoundException {
        imageOut.compress(Bitmap.CompressFormat.JPEG, 10, new FileOutputStream(out));
        return false;
    }
}
