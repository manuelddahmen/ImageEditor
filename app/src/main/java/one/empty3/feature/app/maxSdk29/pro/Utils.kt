package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import java.io.File
import java.io.FileOutputStream
import java.util.*

public class Utils {
    val appDir = "/storage/emulated/0/Android/data/" + BuildConfig.APPLICATION_ID + "/"

    public val INT_WRITE_STORAGE: Int = 8728932

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
        var name2 = "photo-" + UUID.randomUUID().toString()
        var dirName1 = Environment.getDataDirectory().path
        var dirName2 = this.appDir

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
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), INT_WRITE_STORAGE
            )
        }

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                if (!file1.exists()) {
                    if (ImageIO.write(BufferedImage(bitmap), "jpg", file1)) {
                        written = true;
                        fileWritten = file1;
                        return file1
                    }
                }
            } catch (ex: NullPointerException) {

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            try {
                if (!file2.exists()) {
                    if (ImageIO.write(BufferedImage(bitmap), "jpg", file2)) {
                        written = true;
                        fileWritten = file2;
                        return file2
                    }
                }
            } catch (ex: NullPointerException) {

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        if (written) {
            return fileWritten;
        } else {
            Log.e("SAVE FILE ERRORS", "writePhoto: error file 2/2")
            throw NullPointerException("No file written, Utils.writePhoto");
        }

    }

}