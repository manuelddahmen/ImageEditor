package one.empty3;

import android.graphics.Bitmap;

import java.io.File;

public class Image {
    /***
     * Method reccourci au lien {@link one.empty3.libs.Image}
     * @param image
     * @param jpg
     * @param file
     * @param shouldOverwrite
     */
    public static void saveFile(Bitmap image, String jpg, File file, boolean shouldOverwrite) {
        new one.empty3.libs.Image(image).saveFile(file);
    }

    public static void saveFile(one.empty3.libs.Image image, String jpg, File file, boolean shouldOverwrite) {
        image.saveFile(file);
    }
}
