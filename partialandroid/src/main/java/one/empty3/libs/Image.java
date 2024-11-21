package one.empty3.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import org.jetbrains.annotations.NotNull;

import one.empty3.libs.commons.IImageMp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Image extends BitmapDrawable implements IImageMp {
    private Bitmap image;

    public Image(Bitmap image) {
        setImage(image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(image);
        }
    }

    private void setImage(Bitmap image) {
        this.image = image;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(image);
        }
    }

    public Image(int width, int height) {
        setImage(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(image);
        }
    }

    public Image(int columns, int lines, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setImage(Bitmap.createBitmap(columns, lines, Bitmap.Config.ARGB_8888));
            setBitmap(image);
        }
    }


    public int getRgb(int x, int y) {
        return image.getPixel(x, y);
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
        return new int[1][1];
    }

    @Override
    public int getWidth() {
        if(image==null)
            return getBitmap().getWidth();
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        if(image==null)
            return getBitmap().getHeight();
        return image.getHeight();
    }

    public void setRgb(int x, int y, int rgb) {
        image.setPixel(x, y, rgb);
    }

    public static Image loadFile(File path) {
        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(path));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return new Image(bitmap);
        }
        return new Image(bitmap);
    }

    public boolean saveFile(File path) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                try {
                    if (image != null) {
                        image.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(path));
                        return true;
                    } else {
                        getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(path));
                        return true;
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        return false;
    }

    public @NotNull Bitmap getImage() {
        return image==null?getBitmap():image;
    }
}