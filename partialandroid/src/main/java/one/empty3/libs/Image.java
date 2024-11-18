package one.empty3.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import one.empty3.libs.commons.IImageMp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Image extends BitmapDrawable implements IImageMp {
    public Image(Bitmap image) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(image);
        }
    }

    public Image(int width, int height) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
        }
    }

    public Image(int columns, int lines, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(Bitmap.createBitmap(columns, lines, Bitmap.Config.ARGB_8888));
        }
    }

    public int getRgb(int x, int y) {
        return getBitmap().getPixel(x, y);
    }

    @Override
    public IImageMp getFromFile(File file) {
        return loadFile(file);
    }

    @Override
    public boolean saveToFile(String path) {
        return saveToFile(path);
    }

    @Override
    public void setImageToMatrix(int[][] ints) {

    }

    @Override
    public int[][] getMatrix() {
        return new int[0][];
    }

    @Override
    public int getWidth() {
        return getBitmap().getWidth();
    }

    @Override
    public int getHeight() {
        return getBitmap().getHeight();
    }

    public void setRgb(int x, int y, int rgb) {
        getBitmap().setPixel(x, y, rgb);
    }

    public static Image loadFile(File path) {
        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(path));
        return new Image(bitmap);
    }

    public boolean saveFile(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(path));
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}