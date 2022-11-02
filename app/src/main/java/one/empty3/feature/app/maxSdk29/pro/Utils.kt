package one.empty3.feature.app.maxSdk29.pro

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import java.io.File
import java.io.FileOutputStream
import java.util.*

public class Utils {
    public val TEMPORARY: Int = 1

    fun writeBitmap(imageView: ImageView): File? {

        val drawable = imageView.drawable

        val bitmap: Bitmap
        if (drawable is BitmapDrawable) bitmap =
            drawable.bitmap else if (drawable!!.current is BitmapDrawable) {
            bitmap = (drawable!!.current as BitmapDrawable).bitmap
        } else {
            bitmap =
                if (drawable!!.intrinsicWidth <= 0 || drawable!!.intrinsicHeight <= 0) {
                    Bitmap.createBitmap(
                        1,
                        1,
                        Bitmap.Config.ARGB_8888
                    ) // Single color bitmap will be created of 1x1 pixel
                } else {
                    Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }


        val file: File = File("" + UUID.randomUUID() + ".jpg")

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))

        return file
    }

    /***
     * Write copy of original file in data folder
     * @param bitmap
     * @param name
     * @return
     */
    fun writePhoto(activity: Activity, bitmap: Bitmap, name: String): File {
        val camera = Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        )
        var n = 1
        //Folder is already created
        var name2 = name + "-photo-" + UUID.randomUUID().toString()
        var dirName1 = ""
        var dirName2 = ""
        dirName1 = Environment.getDataDirectory().path
        dirName2 = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.getAbsolutePath()
            .toString()

        n++


        //startActivityForResult(camera, 1);
        val dir1 = File(dirName1)
        val file1 = File(dirName1 + File.separator + name2 + ".jpg")
        val dir2 = File(dirName2)
        val file2 = File(dirName2 + File.separator + name2 + ".jpg")
        val uriSavedImage = Uri.fromFile(file2)
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage)
        if (!dir1.exists()) if (!dir1.mkdirs()) {
            System.err.print("Dir not created \$dir1")
        }
        if (!dir2.exists()) if (!dir2.mkdirs()) {
            System.err.println("Dir not created \$dir2")
        }
        try {
            if (!file1.exists()) {
                ImageIO.write(BufferedImage(bitmap), "jpg", file1)
                System.err.print("Image written 1/2 $file1 return")
                //System.err.println("File (photo) " + file1.getAbsolutePath());
                return file1
            }
        } catch (ex: Exception) {
        //    ex.printStackTrace()
            Log.e("SAVE FILE", "writePhoto: erreur file 1/2")
        }
        try {
            if (!file2.exists()) {
                ImageIO.write(BufferedImage(bitmap), "jpg", file2)
                //        System.err.print("Image written 2/2 $file2 return")
                //System.err.println("File (photo) " + file2.getAbsolutePath());
                return file2
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e("SAVE FILE", "writePhoto: erreur file 2/2")
        }
        return file1
    }

}