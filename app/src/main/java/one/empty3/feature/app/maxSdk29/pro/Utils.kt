package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import java.io.File
import java.util.*

public class Utils() {
    val appDir = "/data/data/one.empty3.feature.app.minSdk29.pro/files"

    private val INT_WRITE_STORAGE: Int = 8728932

    /***
     * Write copy of original file in data folder
     * @param bitmap
     * @param name
     * @return file
     */
    @RequiresApi(Build.VERSION_CODES.S)
    fun writePhoto(activity: Activity, bitmap: Bitmap, name: String): File? {

        var written = false;
        var fileWritten: File? = null;

        //Folder is already created
        var name2 = "photo-" + UUID.randomUUID().toString()
        var dirName1 = this.appDir
        var dirName2 = this.appDir

        activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString()
        val dir1 = File(dirName1)
        var file1 = File(dirName1 + File.separator + name2 + ".jpg")
        val dir2 = File(dirName2)
        var file2 = File(dirName2 + File.separator + name2 + ".jpg")
        val uriSavedImage = Uri.fromFile(file2)
        if (!dir1.exists()) if (!dir1.mkdirs()) {
            System.err.print("Dir not created \$dir1")
        }
        if (!dir2.exists()) if (!dir2.mkdirs()) {
            System.err.println("Dir not created \$dir2")
        }
        return writeFile(activity, bitmap, file1, file2)
    }

    public fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: Array<Int>
    ) {
        System.err.println("Ok: files permission Utils")
        return
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public fun writeFile(activity: Activity, bitmap: Bitmap, file1: File, file2: File): File? {
        if (ContextCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.MANAGE_MEDIA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_MEDIA
                    ), INT_WRITE_STORAGE
                )
            }
        }

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.MANAGE_MEDIA
            )
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            var written = false;
            var fileWritten: File? = null;
            try {
                if (!file1.exists()) {
                    if (ImageIO.write(BufferedImage(bitmap), "jpg", file1)) {
                        fileWritten = file1;
                        written = true
                        return file1
                    }
                }
            } catch (ex: NullPointerException) {
                ex.printStackTrace()
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
                ex.printStackTrace()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            if (written) {
                return fileWritten
            } else {
                Log.e("SAVE FILE ERRORS", "writePhoto: error file 2/2")
                throw NullPointerException("No file written, Utils.writePhoto");
            }
        }
        return file1
    }
}