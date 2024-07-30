/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

//import com.nostra13.universalimageloader.core.ImageLoader

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import androidx.preference.PreferenceManager
//import com.linkedin.android.litr.MediaTransformer
//import com.linkedin.android.litr.TransformationListener
//import com.linkedin.android.litr.TransformationOptions
//import com.linkedin.android.litr.analytics.TrackTransformationInfo
//import com.linkedin.android.litr.filter.GlFilter
import javaAnd.awt.Point
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature0.MBitmap.maxRes
import one.empty3.feature0.PixM
import one.empty3.io.ProcessFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import kotlin.math.max


class Utils {
    val appDir = "/data/data/one.empty3.feature.app.minSdk29.pro/files"
    val cords: Array<String> = arrayOf("x", "y", "z", "r", "g", "b", "a", "t", "u", "v")
    val cordsValues: Array<String> = arrayOf("x", "y", "z", "r", "g", "b", "a", "t", "u", "v")
    private val INT_WRITE_STORAGE: Int = 8728932

    /***
     * Write copy of original file in data folder
     * @param bitmap
     * @param name
     * @return file
     */
    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun writePhoto(activity: ActivitySuperClass, bitmap: Bitmap, name: String): File? {
        val maxRes = getMaxRes(activity)
        var written = false;
        var fileWritten: File? = null;

        //Folder is already created
        var name2 = name + UUID.randomUUID().toString()
        var dirName1 = activity.applicationContext.filesDir.absolutePath
        var dirName2 = this.appDir

        activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString()
        val dir1 = File(dirName1)
        var file1 = File(
            dirName1 + File.separator + name2 + "-"
                    + UUID.randomUUID() + ".jpg"
        )
        val dir2 = File(dirName2)
        var file2 = File(
            dirName2 + File.separator + name2 + "-"
                    + UUID.randomUUID() + ".jpg"
        )
        val uriSavedImage = Uri.fromFile(file2)
        if (!dir1.exists()) if (!dir1.mkdirs()) {
            System.err.print("Dir not created \$dir1" + file1.absolutePath)
        }
        if (!dir2.exists()) if (!dir2.mkdirs()) {
            System.err.println("Dir not created \$dir2" + file2.absolutePath)
        }
        return writeFile(activity, bitmap, file1, file2, maxRes, true)
    }

    fun getMaxRes(context: Context): Int {
        val defaultSharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        val floatStr: String? = defaultSharedPreferences.getString("maxRes", "0.0")

        if (floatStr != null) {
            try {
                maxRes = floatStr.toFloat().toInt()

                System.out.println("maxRes = " + maxRes)

                return maxRes
            } catch (e: java.lang.RuntimeException) {
                println("Error casting maxRes" + floatStr.javaClass)
            }
        }
        return maxRes
    }

    fun getMaxRes(fragment: FragmentSuperClass): Int {
        return getMaxRes(fragment.requireContext())
    }

    fun getMaxRes(activitySuperClass: ActivitySuperClass): Int {
        if (activitySuperClass == null || activitySuperClass.applicationContext == null)
            return ActivitySuperClass.MAXRES_DEFAULT
        return getMaxRes(activitySuperClass.applicationContext)
    }
    /*
    fun applyOnMedia(applicationContext: Context, inFile:File,
                     outFile : File, numFrames: Int =-1, apf : AndroidProcessFile) {
        var isImage = false
        val mediaTransformer = MediaTransformer(applicationContext)
        val filters :List<GlFilter> = ArrayList()
        var numFrames1 = numFrames
        if(inFile.name.toLowerCase(Locale.current).endsWith("jpg")) {
            isImage = true
            if(numFrames==-1) {
                numFrames1 = 150
            } else {

            }
        }
        else
            isImage = false
        class TransformationListenerExt() : TransformationListener {
            override fun onStarted(id: String) {
                if(isImage) {

                } else {

                }
            }

            override fun onProgress(id: String, progress: Float) {
                TODO("Not yet implemented")
            }

            override fun onCompleted(
                id: String,
                trackTransformationInfos: MutableList<TrackTransformationInfo>?
            ) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(
                id: String,
                trackTransformationInfos: MutableList<TrackTransformationInfo>?
            ) {
                TODO("Not yet implemented")
            }

            override fun onError(
                id: String,
                cause: Throwable?,
                trackTransformationInfos: MutableList<TrackTransformationInfo>?
            ) {
                TODO("Not yet implemented")
            }

        }
        val transformer : TransformationListener = TransformationListenerExt()


        mediaTransformer.transform(""+UUID.randomUUID(),
            inFile.toUri(), outFile.toUri(),
            MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_MPEG4,
                maxRes, maxRes), MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC,
            3000, 1),
            transformer,
            TransformationOptions.Builder().setVideoFilters(filters).build()
        )
    }
*/
    public fun writeFile(
        fragment: FragmentSuperClass,
        bitmap: Bitmap,
        file1: File,
        file2: File, maxImageSize: Int, shouldOverwrite: Boolean
    ): File? {
        if (maxImageSize <= 0) {
        }
        var written = false;
        var fileWritten: File? = null;
        var bitmap2: Bitmap
        if (maxImageSize > 0) {
            var scaledBy: Int = max(bitmap.width, bitmap.height)
            try {
                bitmap2 = Bitmap.createScaledBitmap(
                    bitmap,
                    (1.0 * maxImageSize / scaledBy * bitmap.width).toInt(),
                    (1.0 * maxImageSize / scaledBy * bitmap.height).toInt(),
                    true
                )
            } catch (ex: RuntimeException) {
                try {
                    ex.printStackTrace()
                    bitmap2 = Bitmap.createScaledBitmap(
                        bitmap,
                        (1.0 * maxImageSize * getMaxRes(fragment)).toInt(),
                        (1.0 * maxImageSize * getMaxRes(fragment)).toInt(),
                        true
                    )
                } catch (ex: RuntimeException) {
                    ex.printStackTrace()
                    bitmap2 = Bitmap.createScaledBitmap(
                        bitmap, 400, 400, true
                    )

                }
            }
        } else bitmap2 = bitmap
        try {
            if (!file1.exists() || shouldOverwrite) {
                if (ImageIO.write(bitmap2, "jpg", file1)) {
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
                if (ImageIO.write(BufferedImage(bitmap2), "jpg", file2, shouldOverwrite)) {
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
            val f = fragment.activity2.getFilesFile("from_error-" + UUID.randomUUID() + ".jpg")
            if (ImageIO.write(bitmap, "jpg", f)) {
                written = true;
                fileWritten = file2;
                return f
            }

            Log.e("SAVE FILE ERRORS", "writePhoto: error file 2/2")
            throw NullPointerException("No file written, Utils.writePhoto");
        }
    }


    public fun writeFile(
        activity: ActivitySuperClass,
        bitmap: Bitmap,
        file1: File,
        file2: File, maxImageSize: Int, shouldOverwrite: Boolean
    ): File? {
        if (maxImageSize <= 0) {
        }
        var written = false;
        var fileWritten: File? = null;
        var bitmap2: Bitmap
        if (maxImageSize > 0) {
            var scaledBy: Int = max(bitmap.width, bitmap.height)
            try {
                bitmap2 = Bitmap.createScaledBitmap(
                    bitmap,
                    (1.0 * maxImageSize / scaledBy * bitmap.width).toInt(),
                    (1.0 * maxImageSize / scaledBy * bitmap.height).toInt(),
                    true
                )
            } catch (ex: RuntimeException) {
                try {
                    ex.printStackTrace()
                    bitmap2 = Bitmap.createScaledBitmap(
                        bitmap,
                        (1.0 * maxImageSize * getMaxRes(activity)).toInt(),
                        (1.0 * maxImageSize * getMaxRes(activity)).toInt(),
                        true
                    )
                } catch (ex: RuntimeException) {
                    ex.printStackTrace()
                    bitmap2 = Bitmap.createScaledBitmap(
                        bitmap, 400, 400, true
                    )

                }
            }
        } else bitmap2 = bitmap
        try {
            if (!file1.exists() || shouldOverwrite) {
                if (ImageIO.write(bitmap2, "jpg", file1)) {
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
                if (ImageIO.write(BufferedImage(bitmap2), "jpg", file2, shouldOverwrite)) {
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
            val f = activity.getFilesFile("from_error-" + UUID.randomUUID() + ".jpg")
            if (ImageIO.write(bitmap, "jpg", f)) {
                written = true;
                fileWritten = file2;
                return f
            }

            Log.e("SAVE FILE ERRORS", "writePhoto: error file 2/2")
            throw NullPointerException("No file written, Utils.writePhoto");
        }
    }

    public fun getMaxRes(fragment: FragmentSuperClass, savedInstanceState: Bundle?): Int {
        val defaultSharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(fragment.requireContext())

        val floatStr: String? = defaultSharedPreferences.getString("maxRes", "0.0")

        if (floatStr != null) {
            try {
                maxRes = floatStr.toFloat().toInt()

                System.out.println("maxRes = " + maxRes)

                return maxRes
            } catch (e: java.lang.RuntimeException) {
                println("Error casting maxRes" + floatStr.javaClass)
            }
        }

        if (fragment != null && fragment.requireActivity() != null) {
            maxRes =
                fragment!!.requireActivity().intent.getIntExtra(
                    "maxRes",
                    ActivitySuperClass.MAXRES_DEFAULT
                )
        }
        if (maxRes == -1) {
            if (savedInstanceState == null ||
                !savedInstanceState.containsKey("maxRes") ||
                savedInstanceState.getInt("maxRes") != -1
            ) {
                return getMaxRes(fragment);
            } else {
                maxRes = savedInstanceState.getInt("maxRes")

            }
        }
        if (maxRes == -1) {
            maxRes = 0
        }
        println("maxRes = $maxRes")
        return maxRes;
    }

    public fun setImageView(activity: ActivitySuperClass, imageView: ImageViewSelection): File? {
        var currentFile: File? = null
        val intent: Intent = activity.intent
        val data = intent?.data
        currentFile = data?.toFile()
        if (data == null) {
            currentFile = getCurrentFile(intent = intent, activity)
        }
        if (currentFile == null) {
            currentFile = activity.getCurrentFile()
        }

        System.err.println("set ImageView from  = $currentFile")

        if (currentFile != null && currentFile.exists()) {
            val bi = ImageIO.read(currentFile)
            if (bi != null) {
                val bitmap = bi.getBitmap()
                if (bitmap != null) {
                    imageView.setImageBitmap2(bitmap)
                }
            }
        }

        return currentFile
    }

    public fun setImageView2(activity: ActivitySuperClass, imageView: ImageViewSelection): File? {
        var currentFile: File? = getCurrentFile(intent = activity.intent, activity)
        val intent: Intent = activity.intent
        val extras = intent.extras
        if(extras!=null) {
            val data0 = extras.get(Intent.EXTRA_STREAM)
            var data: Uri? = null
            if(data0!=null)
                    data = data0 as Uri
            if(data!=null) {
                var inputStream: InputStream? = null
                inputStream = activity.getRealPathFromURI(data)
                val bitmapDecodeStream: Bitmap = BitmapFactory.decodeStream(inputStream)
                currentFile = getFilesFile("imported-")
                bitmapDecodeStream.compress(
                    Bitmap.CompressFormat.JPEG, 100, FileOutputStream(currentFile)
                )
            }
            if (data == null) {
                    //currentFile = getCurrentFile(intent = intent, activity)
            }
            if (currentFile == null) {
                currentFile = activity.getCurrentFile()
            }
            System.err.println("set ImageView from  = $currentFile")

            if (currentFile != null) {
                val bi = ImageIO.read(currentFile)
                if (bi != null) {
                    val bitmap = bi.getBitmap()
                    if (bitmap != null) {
                        imageView.setImageBitmap2(bitmap)
                    }
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
        intent.putExtra("maxRes", ActivitySuperClass.MAXRES_DEFAULT)
        return currentFile
    }


    public fun putExtra(
        calculatorIntent: Intent,
        cords: Array<String>,
        consts: Array<String>,
        variableName: String?,
        variable: String?
    ) {
        for (j in cords.indices) {
            calculatorIntent.putExtra(consts[j], cords[j])
            if (consts[j].equals(variableName) && variable != null && variableName != null) {
                calculatorIntent.putExtra("variable", variable)
                calculatorIntent.putExtra("variableName", variableName)
            }
        }

    }

    @Deprecated(message = "Double")
    private fun putExtraCords(activity: ActivitySuperClass, calculatorIntent: Intent) {
        var j = 0
        for (s in ActivitySuperClass.cordsConsts) {
            calculatorIntent.putExtra(s, activity.cords[j])
            j++
        }
    }

    fun getCurrentFile(intent: Intent, activity: ActivitySuperClass): File? {

        if (intent.hasExtra("data") && intent.extras!!.get("data") is File)
            return intent.extras?.get("data") as File
        if (intent.data != null && intent.data is Uri)
            return intent.data!!.getPath()?.let { File(it) }
        if (intent.data != null) {
            return intent.data as File
        }
        if (intent.hasExtra("currentFile") && (intent.extras?.get("currentFile") is File))
            return (intent.extras?.get("currentFile") as File)
        if (intent.hasExtra("currentFile") && (intent.extras?.get("currentFile") is String))
            return File(intent.extras?.get("currentFile") as String)
        if (intent.data != null)
            return intent.data?.toFile()
        if (intent.extras?.get(Intent.EXTRA_STREAM) != null) {
            try {
                val uri = intent.extras!!.get(Intent.EXTRA_STREAM) as Uri
                val file = activity.getRealPathFromURIString(uri)
                if(file!=null) {
                    val realPathFromIntentData: InputStream = FileInputStream(file)
                    val writeFile = writeFile(
                        activity,
                        BitmapFactory.decodeStream(realPathFromIntentData),
                        getFilesFile("imported-"),
                        getFilesFile("imported-"),
                        0,
                        false
                    )
                return writeFile
                }

            } catch (ex: RuntimeException) {
                ex.printStackTrace()
                return null
            }
        }
        return null
    }

    fun loadImageInImageView(activity: ActivitySuperClass): Boolean {
        try {
            if (activity.currentFile.getCurrentFile() == null) {
                activity.currentFile!!.add(DataApp(activity.imageViewPersistantFile))
            }
            var imageView: ImageViewSelection? = activity.imageView
            if (imageView == null) {
                activity.imageView =
                    activity.findViewById<ImageViewSelection>(R.id.currentImageView)
                imageView = activity.imageView
            }
            if (activity.currentFile.currentFile != null && activity.currentFile.currentFile.exists()) {
                try {
                    val fileInputStream =
                        FileInputStream(activity.currentFile.currentFile) ?: return false
                    var mBitmap: Bitmap
                    try {
                        mBitmap =
                            BitmapFactory.decodeStream(fileInputStream) ?: return false
                    } catch (ex: OutOfMemoryError) {
                        Toast.makeText(
                            activity.applicationContext,
                            "No memory, will try smaller image",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        ex.printStackTrace()
                        val options = Options()
                        options.outHeight = maxRes
                        options.outWidth = maxRes

                        mBitmap = BitmapFactory.decodeFile(
                            activity.currentFile.currentFile.absolutePath,
                            options
                        )
                    }

                    val maxRes = getMaxRes(activity)

                    val cb: Bitmap
                    if (maxRes > 0)
                        cb = Bitmap.createBitmap(
                            mBitmap, 0, 0,
                            mBitmap.width, mBitmap.height
                        )
                    else
                        cb = mBitmap.copy(Bitmap.Config.ARGB_8888, true)

                    val dim: Int = getMaxRes(activity)
                    if (imageView != null)
                        Utils().setImageView(imageView, cb)
                    return true
                } catch (e: FileNotFoundException) {
                }
            }
        } catch (ex:NullPointerException) {
            return false
        }
        return false
    }

    fun loadImageInImageView(bitmap: Bitmap, imageViewEffectPreview: ImageViewSelection) {
        setImageView(imageViewEffectPreview, bitmap)
    }


    fun saveImageState(activity: ActivitySuperClass) {
        val file = true
        val imageView = activity.findViewById<ImageViewSelection>(R.id.currentImageView)
        if (imageView == null) return
        val drawable: Drawable = imageView.getDrawable()
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            bitmap = drawable.bitmap.copy(Bitmap.Config.ARGB_8888, true)
        } else {
            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(
                    activity.getMaxRes(),
                    activity.getMaxRes(),
                    Bitmap.Config.ARGB_8888
                ).copy(
                    Bitmap.Config.ARGB_8888,
                    true
                ) // Single color bitmap will be created of 1x1 pixel
            }
        }
        if (bitmap == null)
            return;
        val canvas = Canvas(bitmap)
        drawable.setBounds(
            0, 0, if (getMaxRes(activity) == 0) canvas.width else getMaxRes(activity),
            if (getMaxRes(activity) == 0) canvas.height else getMaxRes(activity)
        )
        drawable.draw(canvas)
        var bm: Bitmap? = null
        if (bitmap != null) {
            bm = bitmap.copy(Bitmap.Config.ARGB_8888, true)
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

    fun getImageRatio(bitmap: Bitmap): Double {
        return (1.0) * bitmap.width / bitmap.height
    }

    fun setMaxResImage(activity: ActivitySuperClass, bitmap: Bitmap): Point? {
        val imageRatio = getImageRatio(bitmap)
        return Point(
            (getMaxRes(activity) / imageRatio).toInt(),
            (getMaxRes(activity) * imageRatio).toInt()
        )
    }

    fun loadImageState(activity: ActivitySuperClass, originalImage: Boolean) {
        val file = true
        val imageFile = activity.imageViewPersistantFile
        if (file && (imageFile != null) && imageFile.exists()) {
            try {
                var bitmap = BitmapFactory.decodeStream(FileInputStream(imageFile))
                if (bitmap != null) {
                    try {
                        activity.imageView =
                            activity.findViewById<View>(R.id.currentImageView) as ImageViewSelection
                    } catch (ex: NullPointerException) {
                        return
                    }
                    if (activity.imageView != null) {
                        Utils().setImageView(activity.imageView, bitmap);
                    }
                    activity.currentFile.add(DataApp(imageFile))
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
            if (activity.currentFile.currentFile != null) {
                val photo = BitmapFactory.decodeStream(FileInputStream(activity.currentFile.currentFile))
                System.err.println("Get file (bitmap) : $photo")
                activity.currentFile.add(
                    DataApp(
                    this.writePhoto(activity, photo, "create-unique" + UUID.randomUUID())))
                return activity.currentFile.currentFile
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

    fun loadVarsMathImage(activity: ActivitySuperClass, intent: Intent) {

        if (intent.getExtras() != null)
            for (i in ActivitySuperClass.cordsConsts.indices) {
                if (intent.getStringExtra(ActivitySuperClass.cordsConsts[i]) != null)
                    activity.cords[i] = intent.getStringExtra(ActivitySuperClass.cordsConsts[i])!!
            }

        activity.variableName = intent.getStringExtra("variableName")
        activity.variable = intent.getStringExtra("variable")
        val indexOf = ActivitySuperClass.cordsConsts.indexOf(activity.variableName)
        if (activity.variableName != null && activity.variable != null && indexOf >= 0) {
            activity.cords[indexOf] = activity.variable
        }

    }

    public fun runEffectsOnThumbnail(fileIn: File, p: PixM, effect: ProcessFile): File? {
        if (effect != null && fileIn.exists()) {
            var randomUUID = UUID.randomUUID()
            val fileInThumb = getFilesFile("thumbIn-" + effect + randomUUID + "-jpg")
            ImageIO.write(
                PixM.getPixM(
                    ImageIO.read(fileIn).getBitmap(),
                    Math.max(p.getColumns(), p.getLines())
                ).bitmap, "jpg", fileInThumb
            )
            val fileOut = getFilesFile("thumbOut-" + effect + randomUUID + "-jpg")
            effect.setMaxRes(30)
            if (effect.process(fileInThumb, fileOut)) {
                return fileOut
            }
        }
        return null
    }

    fun getIntentFile(intent: Intent): File? {

        if (intent.hasExtra(Intent.EXTRA_STREAM))
            return (intent.extras!!.get(Intent.EXTRA_STREAM) as Uri).toFile()
        if (intent.hasExtra("data") && intent.extras!!.get("data") is File)
            return intent.extras?.get("data") as File
        if (intent.data != null && intent.data is Uri)
            return intent.data!!.getPath()?.let { File(it) }!!
        if (intent.data != null) {
            //return intent.data as File
        }
        if (intent.data != null)
            return intent.data?.toFile()!!
        return null
    }
    /*
        private lateinit var referrerClient: InstallReferrerClient

        fun installReferrer(activity: ActivitySuperClass) {
            try {
                this.referrerClient =
                    InstallReferrerClient.newBuilder(activity.applicationContext).build()
                this.referrerClient.startConnection(object : InstallReferrerStateListener {

                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        when (responseCode) {
                            InstallReferrerResponse.OK -> {
                                // Connection established.
                                System.out.println("Connection established with InstallReferrer.")
                            }

                            InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                                // API not available on the current Play Store app.
                                System.err.println("Connection not established with InstallReferrer : API not available")
                            }

                            InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                                // Connection couldn't be established.
                                System.err.println("Connection not established with InstallReferrer : Service Unavailable")
                            }
                        }
                    }

                    override fun onInstallReferrerServiceDisconnected() {
                        // Try to restart the connection on the next request to
                        // Google Play by calling the startConnection() method.
                    }
                })

            } catch (re : RuntimeException) {
                re.printStackTrace()
            }
        }
    */

    fun createCurrentUniqueFile(applicationContext: Context, filename:String): File? {
        return applicationContext.getExternalFilesDir(filename)
    }
}

