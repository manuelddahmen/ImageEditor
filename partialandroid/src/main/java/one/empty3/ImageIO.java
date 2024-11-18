package one.empty3;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import one.empty3.libs.Image;

public class ImageIO {

    public static @NotNull Image read(File in) {
        return Image.loadFile(in);
    }

    public static boolean write(Image image, String jpg, File out, boolean shouldOverwrite) {
        return image.saveFile(out);
    }

    public static void write(Bitmap bitmap, String jpg, File out) {
        new Image(bitmap).saveFile(out);
    }

    public static void write(Bitmap bitmap, String jpg, File out, boolean shouldOverwrite) {
        if (!out.exists()) {
            new Image(bitmap).saveFile(out);
        }
    }
}
