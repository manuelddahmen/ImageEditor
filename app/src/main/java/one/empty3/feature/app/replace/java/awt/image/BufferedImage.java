package one.empty3.feature.app.replace.java.awt.image;

//import android.graphics.Bitmap;

import android.graphics.Bitmap;

import androidx.core.graphics.BitmapCompat;

public class BufferedImage {
    //public Bitmap bitmap;
    public BufferedImage bufferedImage;

    public static Bitmap BufferedImage(int width, int height, Bitmap.Config config) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        return bitmap;
    }
}
