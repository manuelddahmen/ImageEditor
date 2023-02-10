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
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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
    fun writePhoto(activity: AppCompatActivity, bitmap: Bitmap, name: String): File? {

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
        activity: AppCompatActivity,
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


    public fun getMaxRes(activity: Activity, savedInstanceState: Bundle): Int {
        val maxRes: Int;
        if (savedInstanceState == null ||
            savedInstanceState.containsKey("maxRes") ||
            savedInstanceState.getInt("maxRes") <= 0
        ) {
            maxRes = MyCameraActivity.MAX_RES_DEFAULT
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
}