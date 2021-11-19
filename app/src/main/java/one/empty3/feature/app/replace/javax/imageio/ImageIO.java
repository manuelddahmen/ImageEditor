package one.empty3.feature.app.replace.javax.imageio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIO {
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

    public static boolean write(Bitmap imageOut, String jpg, File out) throws FileNotFoundException {
        imageOut.compress(Bitmap.CompressFormat.JPEG, 10, new FileOutputStream(out));
        return false;
    }
}
