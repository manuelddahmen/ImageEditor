package one.empty3.libs.partialandroid;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Images {
    private Bitmap image;

    public Images(Bitmap image) {
        this.image = image;
    }

    public Images(int x, int y) {
        this.image = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
    }

    public static void save(Images images, File path) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path.getAbsolutePath());
            images.getImage().compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Images load(File path) {
        return new Images(BitmapFactory.decodeFile(path.getAbsolutePath()));
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getRgb(int x, int y) {
        return image.getPixel(x, y);
    }

    public void setRgb(int x, int y, int rgb) {
        image.setPixel(x, y, rgb);
    }
}