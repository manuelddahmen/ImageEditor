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
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
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
    fun writePhoto(activity: Activity, bitmap: Bitmap, name: String): File? {

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
        activity: Activity,
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
                    System.out.println("File written: $file1")
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
                    System.out.println("File written: $file2")
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
        var maxRes: Int = -1;
        maxRes = activity.intent.getIntExtra("maxRes", MyCameraActivity.MAX_RES_DEFAULT);
        if (savedInstanceState == null ||
            !savedInstanceState.containsKey("maxRes") ||
            savedInstanceState.getInt("maxRes") <= 0
        ) {

        } else {
            maxRes = savedInstanceState.getInt("maxRes")

        }
        return maxRes
    }

    public fun setImageView(activity: Activity, imageView: ImageView?): File? {
        var currentFile: File? = null
        val intent: Intent = activity.intent
        if (intent?.getData() != null) {
            if (intent.data != null) {
                val data = intent.data
                System.err.println("File returned from effects' list = $data")
                currentFile = File(data!!.path)
                if (currentFile != null) {
                    val bi = ImageIO.read(currentFile)
                    if (bi != null) {
                        val bitmap = bi.getBitmap()
                        if (bitmap != null && imageView != null) {
                            imageView.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        } else {
            System.err.println("intent data Main==null")
        }
        return currentFile
    }

    fun addCurrentFileToIntent(
        activity: Activity,
        imageView: ImageView?,
        intent: Intent,
        currentFile: File
    ): File {
        val intent = Intent(Intent.ACTION_EDIT)
        try {
            val decodeStream = BitmapFactory.decodeStream(FileInputStream(currentFile))
            var currentBitmap = Utils().writePhoto(activity, decodeStream, "EffectOn") // ???

            intent.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
            intent.setClass(activity.applicationContext, ChooseEffectsActivity2::class.java)
            intent.putExtra("currentFile", currentFile)
            intent.putExtra("data", currentFile)
            if (imageView != null)
                imageView.setImageBitmap(decodeStream);
            System.err.println("Add currentFile to parameter")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return currentFile
    }

    fun getCurrentFile(intent: Intent): File? {
        if (intent.data != null && intent.data is Uri)
            return intent.data!!.getPath()?.let { File(it) }
        if (intent.hasExtra("currentFile") && (intent.extras?.get("currentFile") is File))
            return (intent.extras?.get("currentFile") as File)
        if (intent.hasExtra("currentFile") && (intent.extras?.get("currentFile") is String))
            return File(intent.extras?.get("currentFile") as String)
        if (intent.data != null && intent.data is File)
            return intent.data as File
        if (intent.hasExtra("data") && intent.extras!!.get("data")!!.javaClass.equals(
                File.listRoots().get(0).javaClass
            )
        )
            return intent.data as File
        if (intent.hasExtra("data") && intent.extras!!.get("data")!!.javaClass.equals(String.javaClass))
            return File(intent.data as String)

        return null
    }
}

