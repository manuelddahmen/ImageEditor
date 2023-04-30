/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.net.toFile
import androidx.lifecycle.findViewTreeViewModelStoreOwner
//import com.nostra13.universalimageloader.core.ImageLoader
import javaAnd.awt.Point
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.PixM
import java.io.*
import java.util.*


public class Utils() {
    val appDir = "/data/data/one.empty3.feature.app.minSdk29.pro/files"
    val cords: Array<String> = arrayOf("x", "y", "z", "r", "g", "b", "a", "t", "u", "v")
    val cordsValues: Array<String> = arrayOf("x", "y", "z", "r", "g", "b", "a", "t", "u", "v")
    private val INT_WRITE_STORAGE: Int = 8728932
    //var imageLoader: ImageLoader = ImageLoader.getInstance() // Get singleton instance

/*
    fun getSavedApplicationData(activity: EmptyActivity) {
        //val file = File(appDir + "/config.txt")
        val bundle = Parcel.obtain()
        var i = 0
        for (cord in cords) {
            val any = bundle.getObject(cord)
            if (any is String) {
                val str = any as String
                cordsValues[i] = str
            }
            i = i + 1
        }
    }

    fun saveApplicationData(activity: Activity, cords: StringArray) {
        //val file = File(appDir + "/config.txt")
        val bundle = ResourceBundle.getBundle("config")
        var i = 0
        for (cord in cords) {
            //if (cord != null)
            //    bundle.keySet().add(cord, cords[i])
            //i = i + 1
        }
    }
*/
    /***
     * Write copy of original file in data folder
     * @param bitmap
     * @param name
     * @return file
     */
    fun writePhoto(activity: ActivitySuperClass, bitmap: Bitmap, name: String): File? {

        var written = false;
        var fileWritten: File? = null;

        //Folder is already created
        var name2 = "photo-" + UUID.randomUUID().toString()
        var dirName1 = activity.applicationContext.getFilesDir().absolutePath
        var dirName2 = this.appDir

        activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString()
        val dir1 = File(dirName1)
        var file1 = File(dirName1 + File.separator + name2 + ".jpg")
        val dir2 = File(dirName2)
        var file2 = File(dirName2 + File.separator + name2 + ".jpg")
        val uriSavedImage = Uri.fromFile(file2)
        if (!dir1.exists()) if (!dir1.mkdirs()) {
            System.err.print("Dir not created \$dir1" + file1.absolutePath)
        }
        if (!dir2.exists()) if (!dir2.mkdirs()) {
            System.err.println("Dir not created \$dir2" + file2.absolutePath)
        }
        return writeFile(activity, bitmap, file1, file2)
    }

    public fun writeFile(
        activity: ActivitySuperClass,
        bitmap: Bitmap,
        file1: File,
        file2: File
    ): File? {
        var written = false;
        var fileWritten: File? = null;
        try {
            if (!file1.exists()) {
                if (ImageIO.write(BufferedImage(bitmap), "jpg", file1)) {
                    fileWritten = file1;
                    written = true
                    System.out.println("File written1: $file1")
                    return file1
                }
            } else {
                System.err.println("File exists: $file1")
            }
        } catch (ex: android.system.ErrnoException) {
            ex.printStackTrace()
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
                    System.out.println("File written2: $file2")
                    return file2
                }
            }
        } catch (ex: android.system.ErrnoException) {
            ex.printStackTrace()
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


    public fun getMaxRes(activity: Activity, savedInstanceState: Bundle?): Int {
        var maxRes: Int = 0;
        maxRes = activity?.intent?.getIntExtra("maxRes", MyCameraActivity.MAX_RES_DEFAULT)!!;
        if (savedInstanceState == null ||
            !savedInstanceState.containsKey("maxRes") ||
            savedInstanceState.getInt("maxRes") <= 0
        ) {
            maxRes = 0
        } else {
            maxRes = savedInstanceState.getInt("maxRes")

        }
        return maxRes
    }

    public fun setImageView(activity: ActivitySuperClass, imageView: ImageViewSelection?): File? {
        var currentFile: File? = null
        val intent: Intent = activity.intent
        val data = intent?.data
        currentFile = data?.toFile()
        if (data == null) {
            currentFile = getCurrentFile(intent = intent)
        }
        if (currentFile == null) {
            currentFile = activity.getCurrentFile()
        }

        System.err.println("set ImageView from  = $currentFile")

        if (currentFile != null) {
            val bi = ImageIO.read(currentFile)
            if (bi != null) {
                val bitmap = bi.getBitmap()
                if (bitmap != null && imageView != null) {
                    imageView.setImageBitmap2(bitmap)
                }
            }
        }

        return currentFile
    }

    fun addCurrentFileToIntent(
        intent: Intent,
        activity: ActivitySuperClass?,
        currentFile: File
    ): File {
        //intent.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
        intent.putExtra("currentFile", currentFile)
        intent.putExtra("data", currentFile)
        intent.data = Uri.fromFile(currentFile)
        System.out.println("Add currentFile to parameter")

        if (activity != null) {
            var j = 0
            while (j < ActivitySuperClass.cordsConsts.size) {
                intent.putExtra(ActivitySuperClass.cordsConsts[j], activity.cords[j])
                j++
            }
        }
        return currentFile
    }


    public fun putExtra(calculatorIntent: Intent, formulas: Array<String>, cord: String) {
        var j = 0
        for (s in cords) {
            calculatorIntent.putExtra(s, formulas[j])
            if (s == cord) {
                calculatorIntent.putExtra("variable", formulas[j])
            }
            j++
        }
    }

    fun getCurrentFile(intent: Intent): File? {

        if (intent.hasExtra("data") && intent.extras!!.get("data") is File)
            return intent.extras?.get("data") as File
        if (intent.data != null && intent.data is Uri)
            return intent.data!!.getPath()?.let { File(it) }
        if (intent.data != null && intent.data is File)
            return intent.data as File
        if (intent.hasExtra("currentFile") && (intent.extras?.get("currentFile") is File))
            return (intent.extras?.get("currentFile") as File)
        if (intent.hasExtra("currentFile") && (intent.extras?.get("currentFile") is String))
            return File(intent.extras?.get("currentFile") as String)
        if (intent.data != null && intent.data is File)
            return intent.data as File
        return null
    }

    fun loadImageInImageView(activity: ActivitySuperClass): Boolean {
        if(activity.currentFile==null) {
            activity.currentFile = activity.getImageViewPersistantFile()
        }

        var imageView : ImageViewSelection? = activity.imageView
        if(imageView==null) {
            activity.imageView = activity.findViewById<ImageViewSelection>(R.id.currentImageView)
            imageView = activity.imageView
        }
        if (activity.currentFile!=null && activity.currentFile.exists()) {
            try {
                val mBitmap :Bitmap = BitmapFactory.decodeStream(FileInputStream(activity.currentFile))
                val maxRes = getMaxRes(activity)
                val cb = Bitmap.createBitmap(mBitmap, 0, 0,
                    getImageRatio(mBitmap)*maxRes, maxRes)
                val dim : Int= getMaxRes(activity)
                if(imageView!=null)
                    Utils().setImageView(imageView, cb)
                return true
            } catch (e: FileNotFoundException) {
            }
        }
        return false
    }


    fun saveImageState(activity: ActivitySuperClass) {
        val file = true
        val imageView = activity.findViewById<ImageViewSelection>(R.id.currentImageView)
        if (imageView == null) return
        val drawable: Drawable = imageView.getDrawable()
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            bitmap = drawable.bitmap
        } else {
            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(
                    1,
                    1,
                    Bitmap.Config.ARGB_8888
                ) // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = PixM.getPixM(bitmap, getMaxRes(activity)).bitmap
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(
                0, 0, if (getMaxRes(activity) == 0) canvas.width else getMaxRes(activity),
                if (getMaxRes(activity) == 0) canvas.height else getMaxRes(activity)
            )
            drawable.draw(canvas)
        }
        var bm: Bitmap? = null
        if (bitmap != null) {
            bm = bitmap
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos) //bm is the bitmap object
            val b = baos.toByteArray()
            val encoded = Base64.encodeToString(b, Base64.DEFAULT)
            var fos: OutputStream? = null
            try {
                fos = FileOutputStream(activity.getImageViewPersistantFile())
                bm.compress(Bitmap.CompressFormat.JPEG, 90, fos)
                System.err.println("Image updated")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun isWorkingResolutionOriginal(): Boolean {
        return false
    }

    fun getImageRatio(bitmap: Bitmap): Int {
        return bitmap.width / bitmap.height
    }

    fun setMaxResImage(activity: Activity, bitmap: Bitmap): Point? {
        val imageRatio = getImageRatio(bitmap)
        return Point(
            getMaxRes(activity) / imageRatio,
            getMaxRes(activity) * imageRatio
        )
    }

    private fun getMaxRes(activity: Activity): Int {
        val maxResText: EditText = activity.findViewById<EditText>(R.id.editMaximiumResolution)
        val maxRes = maxResText.text.toString().toDouble().toInt()
        return maxRes
    }

    fun loadImageState(activity: ActivitySuperClass, originalImage: Boolean) {
        val file = true
        val ot = ""
        val imageFile = activity.getImageViewPersistantFile()
        if (file && imageFile?.exists() == true) {
            try {
                var bitmap = BitmapFactory.decodeStream(FileInputStream(imageFile))
                if (bitmap != null) {
                    val imageView: ImageViewSelection =
                        activity.findViewById<View>(R.id.currentImageView) as ImageViewSelection
                    Utils().setImageView(imageView, bitmap);
                    activity.currentFile = imageFile
                    //activity.currentBitmap = imageFile
                    System.err.println("Image reloaded")

                    createCurrentUniqueFile(activity);
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    public fun createCurrentUniqueFile(activity: ActivitySuperClass): File? {
        try {
            if (activity.currentFile != null) {
                val photo = BitmapFactory.decodeStream(FileInputStream(activity.currentFile))
                System.err.println("Get file (bitmap) : $photo")
                val myPhotoV2022: File? =
                    this.writePhoto(activity, photo, "MyPhotoV2022" + UUID.randomUUID())
                System.err.println("Written copy : " + myPhotoV2022!!.absolutePath)
                System.err.println("Set in ImageView : " + myPhotoV2022.absolutePath)

                return myPhotoV2022
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getFilesFile(s: String): File {
        return File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files" + File.separator + s)
    }

    public fun setImageView(imageView: ImageViewSelection, bitmap: Bitmap) {
        //imageLoader.displayImage(bitmap, imageView);
        imageView.setImageBitmap2(bitmap)
        imageView.setPixels(PixM(bitmap))

    }
}

