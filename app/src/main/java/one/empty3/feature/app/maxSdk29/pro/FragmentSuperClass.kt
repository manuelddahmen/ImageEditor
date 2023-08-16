package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.fragment.app.Fragment
import one.empty3.feature20220726.PixM
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.Date
import java.util.Objects
import java.util.Properties

@ExperimentalCamera2Interop
open class FragmentSuperClass : Fragment() {
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
    val TAG: String? = "one.empty3.feature.app.maxSdk29.pro"
    val MAXRES_DEFAULT = 1200
    protected val cordsConsts = arrayOf("x", "y", "z", "r", "g", "b", "a", "t", "u", "v")
    private val ONSAVE_INSTANCE_STATE = 21516
    private val ONRESTORE_INSTANCE_STATE = 51521
    val filenameSaveState = "state.properties"
    val imageViewFilename = "imageView.jpg"
    val imageViewFilenameProperties = "imageView.properties"
    var appDataPath = "/one.empty3.feature.app.maxSdk29.pro/"
    var variableName: String? = null
    var variable: String? = null
    protected var imageView: ImageViewSelection? = null
    protected var currentFile: File? = null
    protected var cords = arrayOf("x", "y", "z", "r", "g", "b", "a", "t", "u", "v")
    protected var currentBitmap: Bitmap? = null
    var maxRes :Int
         get() {
            return Utils().getMaxRes(this)
        }
        set(value) {maxRes = value}
    lateinit var activity: ActivitySuperClass
        @Throws(FileNotFoundException::class)
        fun getPathInput(uri: Uri?): InputStream? {
            return activity.applicationContext.contentResolver.openInputStream(
                uri!!
            )
        }

        protected open fun getRealPathFromIntentData(file: Intent): InputStream? {
            try {
                return getPathInput(file.data)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        protected fun getRealPathFromURI(uri: Uri?): InputStream? {
            try {
                return getPathInput(uri)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (activity.intent != null) {
                getParameters(activity.intent)
                if (currentFile == null && savedInstanceState != null) {
                    try {
                        if (savedInstanceState.getString("currentFile") != null) {
                            currentFile = File(savedInstanceState.getString("currentFile"))
                        }
                    } catch (ex: RuntimeException) {
                        ex.printStackTrace()
                    }
                }
            }
            if (imageView == null) imageView = activity.findViewById(R.id.currentImageView)
            if (currentFile != null) {
                testIfValidBitmap()
            } else loadInstanceState()

            maxRes = Utils().getMaxRes(this.activity)
        }

        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            /*requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, ONSAVE_INSTANCE_STATE);
*/saveInstanceState()
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == ONSAVE_INSTANCE_STATE && grantResults != null) {
                var g = 0
                for (granted in grantResults) {
                    g = g + if (granted == PackageManager.PERMISSION_GRANTED) 1 else 0
                }
                if (g > 0) saveInstanceState()
            }
            if (requestCode == ONRESTORE_INSTANCE_STATE && grantResults != null) {
                var g = 0
                for (granted in grantResults) {
                    g = g + if (granted == PackageManager.PERMISSION_GRANTED) 1 else 0
                }
                if (g > 0) restoreInstanceState()
            }
        }

        fun restoreInstanceState() {
            Utils().loadImageState(activity, false)
            val properties = Properties()
            try {
                properties.load(FileInputStream(imageViewPersistantPropertiesFile))
                for (i in cords.indices) {
                    cords[i] = properties.getProperty(
                        cordsConsts.get(
                            i
                        ),
                        cords[i]
                    )
                }
                val maxRes1 = properties.getProperty("maxRes", "" + maxRes)
                if (maxRes1 != null) {
                    try {
                        maxRes = maxRes1.toInt()
                    } catch (ignored: NumberFormatException) {
                    }
                }
                try {
                    val currentFile1 =
                        properties.getProperty("currentFile", currentFile!!.absolutePath)
                    currentFile = File(currentFile1)
                    if (currentFile1 == null || currentFile1.length == 0) {
                        val imageViewPersistantFile = imageViewPersistantFile
                        if (imageViewPersistantFile!!.exists()) {
                            currentFile = imageViewPersistantFile
                        }
                    }
                } catch (ex: RuntimeException) {
                    Toast.makeText(
                        activity.applicationContext,
                        "Error restoring currentFile",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } catch (ignored: IOException) {
            }
        }

        fun testIfValidBitmap() {
            if (currentFile == null) loadInstanceState()
            if (currentFile != null) {
                if (!currentFile!!.exists()) {
                    currentFile = null
                    return
                }
                try {
                    val fileInputStream = FileInputStream(currentFile)
                    if (BitmapFactory.decodeStream(fileInputStream)
                        == null
                    ) currentFile = null
                } catch (e: FileNotFoundException) {
                    System.err.println("Error file:$currentFile")
                    currentFile = null
                } catch (exception: RuntimeException) {
                    currentFile = null
                }
            }
        }

        protected fun loadInstanceState() {
            var currentFile1: String? = null
            Utils().loadImageState(activity, false)
            val properties = Properties()
            try {
                properties.load(FileInputStream(imageViewPersistantPropertiesFile))
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            try {
                for (i in cords.indices) {
                    cords[i] = properties.getProperty(
                        cordsConsts.get(
                            i
                        ),
                        cords[i]
                    )
                }
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
            }
            try {
                val maxRes1 = properties.getProperty(
                    "maxRes",
                    "" + MAXRES_DEFAULT
                )
                if (maxRes1 != null && maxRes1.length > 0) {
                    try {
                        maxRes = maxRes1.toDouble().toInt()
                    } catch (ex1: RuntimeException) {
                        ex1.printStackTrace()
                    }
                }
                currentFile1 = properties.getProperty("currentFile", currentFile!!.absolutePath)
                if (currentFile1 != null) currentFile = File(currentFile1)
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
            }
            try {
                var currentFile2: File? = null
                currentFile2 =
                    if (currentFile1 == null) imageViewPersistantFile else File(currentFile1)
                if (currentFile2 != null && currentFile2.exists()) {
                    currentFile = currentFile2
                }
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
            }
            if (currentFile == null) Toast.makeText(
                activity.applicationContext,
                "Cannot find current file (working copy)",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        protected fun saveInstanceState() {
            val properties = Properties()
            try {
                properties.load(FileInputStream(imageViewPersistantPropertiesFile))
                for (i in cords.indices) {
                    properties.setProperty(
                        cordsConsts.get(
                            i
                        ),
                        cords[i]
                    )
                }
                properties.setProperty("maxRes", "" + maxRes)
                try {
                    properties.setProperty("currentFile", currentFile!!.absolutePath)
                    val imageViewPersistantFile = currentFile
                    if (currentFile != null) Objects.requireNonNull(this.imageViewPersistantFile)?.let {
                        Utils().writeFile(
                            this,
                            BitmapFactory.decodeStream(
                                FileInputStream(currentFile)
                            ),
                            it,
                            this.imageViewPersistantFile!!,
                            maxRes, true
                        )
                    }
                } catch (e: FileNotFoundException) {
                    throw RuntimeException(e)
                }
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            try {
                properties.store(
                    FileOutputStream(imageViewPersistantPropertiesFile),
                    "#" + Date().toString()
                )
            } catch (ignored: IOException) {
            }
        }

        fun passParameters(to: Intent) {
            if (currentFile != null) to.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
            to.putExtra("maxRes", maxRes)
            Utils().putExtra(
                to,
                cords,
                cordsConsts,
                variableName,
                variable
            )
            println("c className = " + this.javaClass)
            println("m variableName = $variableName")
            println("m variable =     $variable")
            println("i variableName = " + activity.intent.getStringExtra("variableName"))
            println("i variable =     " + activity.intent.getStringExtra("variable"))
            println("c to.className = " + to.type)
            startActivity(to)
        }

        fun getParameters(from: Intent?) {
            val utils = Utils()
            currentFile = utils.getCurrentFile(from!!)
            maxRes = utils.getMaxRes(this.activity)
            utils.loadImageInImageView(activity)
            utils.loadVarsMathImage(activity, activity.intent)
        }

        protected fun getFilesFile(s: String): File {
            return File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files/$s")
        }

        val imageViewPersistantFile: File?
            get() = getFilesFile(imageViewFilename)
        val imageViewPersistantPropertiesFile: File?
            get() = getFilesFile(imageViewFilenameProperties)

        fun saveActivityProperties(properties: Properties): Boolean {
            val filesFile = getFilesFile(this.javaClass.canonicalName + ".txt")
            return try {
                val fileOutputStream = FileOutputStream(filesFile)
                properties.store(fileOutputStream, "Properties for activity: $javaClass")
                true
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        fun loadActivityProperties(properties: Properties): Properties {
            val filesFile = getFilesFile(this.javaClass.canonicalName + ".txt")
            return try {
                val fileInputStream = FileInputStream(filesFile)
                properties.load(fileInputStream)
                properties
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        fun drawIfBitmap() {
            saveInstanceState()
            try {
                currentBitmap = null
                if (imageView == null) imageView = activity.findViewById(R.id.imageViewSelection)
                if (imageView != null && currentFile != null) Utils().setImageView(activity, activity.imageView)
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
            }
        }

        fun loadImage(choose_directoryData: InputStream, isCurrentFile: Boolean): Bitmap {
            var photo: Bitmap? = null
            if (maxRes > 0) {
                System.err.println("FileInputStream$choose_directoryData")
                photo = BitmapFactory.decodeStream(choose_directoryData)
                photo = PixM.getPixM(photo, maxRes).image.getBitmap()
                System.err.println("Get file (bitmap) : $photo")
            } else {
                System.err.println("FileInputStream$choose_directoryData")
                photo = BitmapFactory.decodeStream(choose_directoryData)
                System.err.println("Get file (bitmap) : $photo")
            }
            return if (photo != null && isCurrentFile) {
                currentFile = Utils().writePhoto(activity, photo, "loaded_image-")
                Utils().setImageView(activity, activity.imageView)
                photo
            } else if (photo != null) {
                photo
            } else {
                System.err.println("file == null. Error.")
                throw NullPointerException("File==null ActivitySuperClass, loadImage")
            }
        }

        fun loadImage(photo: Bitmap?, choose_directoryData: File?, isCurrentFile: Boolean): Bitmap {
            return try {
                loadImage(FileInputStream(choose_directoryData), isCurrentFile)
            } catch (e: FileNotFoundException) {
                throw RuntimeException(e)
            }
        }

        override fun onDestroy() {
            saveInstanceState()
            super.onDestroy()
        }

}
