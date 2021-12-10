package one.empty3.feature.app.replace.java.awt.image;

//import android.graphics.Bitmap;

import android.graphics.Bitmap;

public class BufferedImage {
    public Bitmap bitmap;
    public static Bitmap BufferedImage(int width, int height, Bitmap.Config config) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        return bitmap;
    }
    public BufferedImage(int width, int height, Bitmap.Config config) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }
    public int getHeight() {
        return bitmap.getHeight();
    }
}
