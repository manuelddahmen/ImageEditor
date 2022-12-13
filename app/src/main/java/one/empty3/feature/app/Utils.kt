package one.empty3.feature.app

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import java.io.File
import java.util.*

public class Utils {

    /***
     * Write copy of original file in data folder
     * @param bitmap
     * @param name
     * @return
     */
    fun writePhoto(activity: Activity, bitmap: Bitmap, name: String): File? {
        var written = false;
        var fileWritten: File? = null;

        //Folder is already created
        var name2 = name + "photo-" + UUID.randomUUID().toString()
        var dirName1 = ""
        var dirName2 = ""
        dirName1 = Environment.getDataDirectory().path
        dirName2 =
            activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString()
        val dir1 = File(dirName1)
        val file1 = File(dirName1 + File.separator + name2 + ".jpg")
        val dir2 = File(dirName2)
        val file2 = File(dirName2 + File.separator + name2 + ".jpg")
        val uriSavedImage = Uri.fromFile(file2)
        if (!dir1.exists()) if (!dir1.mkdirs()) {
            System.err.print("Dir not created \$dir1")
        }
        if (!dir2.exists()) if (!dir2.mkdirs()) {
            System.err.println("Dir not created \$dir2")
        }
        try {
            if (!file1.exists()) {
                if (ImageIO.write(BufferedImage(bitmap), "jpg", file1)) {
                    written = true;
                    fileWritten = file1;
                }
                return file1
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        try {
            if (!file2.exists()) {
                if (ImageIO.write(BufferedImage(bitmap), "jpg", file2)) {
                    written = true;
                    fileWritten = file2;
                }
                return file2
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        if (written) {
            return fileWritten;
        } else {
            Log.e("SAVE FILE ERRORS", "writePhoto: error file 2/2")
            throw NullPointerException("No file written, Utils.writePhoto");
        }
    }

}