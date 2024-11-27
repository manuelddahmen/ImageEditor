package one.empty3;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import one.empty3.libs.Image;

public class ImageIO {

    public static @NotNull Image read(@NotNull File in) {
        return Image.loadFile(in);
    }

    public static boolean write(@NotNull Image image, String jpg, File out, boolean shouldOverwrite) {
        return image.saveFile(out);
    }

    public static void write(@NotNull Bitmap bitmap, String jpg, File out) {
        if (!out.getParentFile().exists())
            out.getParentFile().mkdirs();

        new Image(bitmap).saveFile(out);
    }

    public static void write(@NotNull Bitmap bitmap, String jpg, File out, boolean shouldOverwrite) {
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
            new Image(bitmap).saveFile(out);
        }
    }
}
