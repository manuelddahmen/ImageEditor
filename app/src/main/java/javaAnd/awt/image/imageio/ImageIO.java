package javaAnd.awt.image.imageio;

//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javaAnd.awt.image.BufferedImage;

public class ImageIO {
    public static BufferedImage read(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedImage bitmap2 = ImageIO.read(fileInputStream);
            fileInputStream.close();
            return bitmap2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage read(FileInputStream fileInputStream) {
        return new BufferedImage(BitmapFactory.decodeStream(fileInputStream));
    }

    public static boolean write(BufferedImage imageOut, String jpg, File out) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        imageOut.getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
        fileOutputStream.close();
        return false;
    }


    public static boolean write(Bitmap image, String jpg, File out) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(out);
            image.compress(Bitmap.CompressFormat.JPEG, 10, fileOutputStream);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("ImageIO write");
            e.printStackTrace();
        }
        return false;
    }
    /*
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

    public static boolean write(Bitmap imageOut, String jpg, File out) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        imageOut.compress(Bitmap.CompressFormat.JPEG, 10, fileOutputStream);
        fileOutputStream.close();
        return false;
    }*/
}
