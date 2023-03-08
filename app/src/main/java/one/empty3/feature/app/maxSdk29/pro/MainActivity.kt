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

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.installations.interop.BuildConfig
import javaAnd.awt.Point
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature.app.maxSdk29.pro.databinding.ActivityMain2Binding
import one.empty3.feature20220726.PixM
import java.io.*
import java.nio.file.Files
import java.nio.file.Files.copy
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var FILESYSTEM_WRITE_PICTURE: Int = 139183
    private var workingResolutionOriginal: Boolean = false
    private val ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER: Int = 38901831
    private val ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER: Int = 37197319
    private lateinit var applicationState: AppData
    private var currentBitmap: File? = null
    private var currentFile: File? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private var maxRes = 0
    private var imageView: ImageView? = null
    private var rectfs: java.util.ArrayList<RectF>? = null
    private var loaded = false
    private var thisActivity: Activity? = null
    private var currentDir: File? = null
    private val MY_CAMERA_PERMISSION_CODE = 0
    private var clipboard: Clipboard? = null
    private val REQUEST_CREATE_DOCUMENT_SAVE_IMAGE = 0
    private var drawPointA: Point? = null
    private var drawPointB: Point? = null
    private val currentFileZoomed = false
    private var currentPixM: PixM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationState = AppData()

        setContentView(R.layout.activity_main2)


        setSupportActionBar(findViewById(R.id.toolbar))

/*        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
*/
        val onClickListener: View = findViewById<View>(R.id.fab)

        val subs: ArrayList<View> = ArrayList<View>();
        subs.add(TextView(onClickListener.context))//
        subs.add(TextView(onClickListener.context))//

        (subs[0] as TextView).text = getString(R.string.add_picture_file_from_directory)
        (subs[1] as TextView).text = getString(R.string.add_picture_from_camera)
        onClickListener.setOnClickListener { view ->
            Snackbar.make(view, "Add a new picture from Camera ? ", Snackbar.LENGTH_LONG)
                .setAction("Add from camera?", View.OnClickListener {
                    onClickListener.run {

                    }

                })
                .show()
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.button_bar_open_save_share, ImagePreviewFragment()).commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.action_bar_container, ActionBarFragment()).commit()


        val currentActionFragment: Fragment = StartActivityFragment()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.currentFragmentViews, currentActionFragment
            ).commit()

        addButtonsListeners(this, applicationState, savedInstanceState)
    }

    private fun addButtonsListeners() {


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    fun addButtonsListeners(activity: Activity, appData: AppData?, savedInstanceState: Bundle?) {
        maxRes = Utils().getMaxRes(activity, savedInstanceState)
        imageView = activity.findViewById<View>(R.id.currentImageView) as ImageView
        rectfs = java.util.ArrayList()
        loaded = true
        currentBitmap = Utils().getCurrentFile(activity.intent)
        currentFile = currentBitmap
        if (currentFile != null) if (Utils().loadImageInImageView(
                currentFile,
                imageView!!
            )
        ) loaded = true
        thisActivity = activity
        currentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val takePhoto = activity.findViewById<Button>(R.id.takePhotoButton)
        takePhoto?.setOnClickListener {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            }
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activity.startActivityForResult(cameraIntent, MyCameraActivity.CAMERA_REQUEST)
            val bundle = Bundle()
            bundle.putString("currentFile", currentFile.toString())
            findNavController(activity, R.id.currentFragmentViews)
                .navigate(R.id.fragment_choose_effects, bundle)
        }
        val effectsButton2 = activity.findViewById<Button>(R.id.effectsButtonNew)
        effectsButton2?.setOnClickListener { v ->
            if (currentFile != null) {
                imageView = activity.findViewById(R.id.currentImageView)
                val intent = Intent(Intent.ACTION_EDIT)
                System.err.println("Click on Effect button")
                if (currentFile != null || currentBitmap != null) {
                    if (currentFile == null) currentFile = currentBitmap?.getAbsoluteFile()
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            currentBitmap = Utils().writePhoto(
                                thisActivity!!,
                                BitmapFactory.decodeStream(FileInputStream(currentFile)),
                                "EffectOn"
                            )
                            currentFile = currentBitmap
                        }
                        intent.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
                        intent.setClass(imageView!!.context, ChooseEffectsActivity2::class.java)
                        intent.putExtra("data", currentFile)
                        val viewById =
                            activity.findViewById<View>(R.id.editMaximiumResolution)
                        intent.putExtra(
                            "maxRes",
                            (viewById as TextView).text.toString().toDouble().toInt()
                        )
                        System.err.println("Start activity : EffectChoose")
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    activity.startActivity(intent)
                } else {
                    System.err.println("No file assigned")
                    System.err.println("Can't Start activity : EffectChoose")
                }
            } else toastButtonDisabled(v)
        }
        val fromFiles = activity.findViewById<View>(R.id.choosePhotoButton)
        fromFiles?.setOnClickListener { v: View? -> startCreation() }
        val copy = activity.findViewById<View>(R.id.copy)
        copy?.setOnClickListener { v: View? ->
            if (clipboard != null) {
                clipboard!!.copied = true
                copy.setBackgroundColor(Color.rgb(40, 255, 40))
                Toast.makeText(
                    activity.applicationContext,
                    "Subimage copied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val paste = activity.findViewById<View>(R.id.paste)
        paste?.setOnClickListener { v: View ->
            clipboard = Clipboard.defaultClipboard
            if (currentFile != null) {
                if (clipboard == null && Clipboard.defaultClipboard != null) clipboard =
                    Clipboard.defaultClipboard
                if (clipboard != null && clipboard!!.copied && clipboard!!.destination != null && clipboard!!.source != null) {
                    val dest = PixM.getPixM<Any>(
                        Objects.requireNonNull(
                            ImageIO.read(
                                currentFile
                            )
                        ).bitmap
                    )
                    if (rectfs!!.size > 0) clipboard!!.destination =
                        rectfs!![rectfs!!.size - 1] else {
                        return@setOnClickListener
                    }
                    val x = Math.min(
                        clipboard!!.destination.left.toInt(),
                        clipboard!!.destination.right.toInt()
                    )
                    val y = Math.min(
                        clipboard!!.destination.bottom.toInt(),
                        clipboard!!.destination.top.toInt()
                    )
                    val w =
                        Math.abs(clipboard!!.destination.right.toInt() - clipboard!!.destination.left.toInt())
                    val h =
                        Math.abs(clipboard!!.destination.bottom.toInt() - clipboard!!.destination.top.toInt())
                    dest.pasteSubImage(clipboard!!.source, x, y, w, h)
                    System.err.println("Destionation coord = " + clipboard!!.destination)
                    System.err.println(
                        "Theory Copied pixels = " + clipboard!!.source
                            .columns * clipboard!!.source.lines
                    )
                    val bitmap = dest.bitmap
                    currentFile = Utils()
                        .writePhoto(activity, bitmap, "copy_paste")
                    currentBitmap = currentFile
                    imageView!!.setImageBitmap(bitmap)
                    paste.setBackgroundColor(Color.rgb(40, 255, 40))
                    copy!!.setBackgroundColor(Color.rgb(40, 255, 40))
                    Toast.makeText(
                        activity.applicationContext,
                        "Subimage pasted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else toastButtonDisabled(v)
        }
        val about = activity.findViewById<View>(R.id.About)
        about?.setOnClickListener { v -> openUserData(v) }
        val shareView = activity.findViewById<View>(R.id.share)
        shareView?.setOnClickListener { v ->
            if (currentFile != null) {
                val uri = Uri.fromFile(currentFile)
                val photoURI = FileProvider.getUriForFile(
                    activity.applicationContext,
                    activity.applicationContext.packageName + ".provider",
                    currentFile!!
                )
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI)
                shareIntent.setDataAndType(photoURI, "image/jpeg")
                shareIntent.putExtra("data", photoURI)
                startActivity(shareIntent)
            } else toastButtonDisabled(v)
        }
        if (shareView != null) shareView.isEnabled = true
        val save = activity.findViewById<View>(R.id.save)
        save?.setOnClickListener { v ->
            if (currentFile != null) {
                saveImageState(isWorkingResolutionOriginal())
                val permissionsStorage = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                val requestExternalStorage = 1
                val permission1 = ActivityCompat.checkSelfPermission(
                    activity.applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                val permission2 = ActivityCompat.checkSelfPermission(
                    activity.applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                if (permission1 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        thisActivity!!,
                        permissionsStorage,
                        requestExternalStorage
                    )
                }
                if (permission2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        thisActivity!!,
                        permissionsStorage,
                        requestExternalStorage
                    )
                }
                val picturesDirectory = File(
                    activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .toString()
                )
                var target: Path? = null
                target = copy(
                    currentFile!!.toPath(),
                    File(picturesDirectory.absolutePath + UUID.randomUUID() + ".jpg")
                        .toPath()
                )
                if (target == null)
                    target = currentFile?.toPath()
                val photoURI = FileProvider.getUriForFile(
                    activity.applicationContext,
                    activity.applicationContext.packageName + ".provider", target!!.toFile()
                )
                val intentSave = Intent(Intent.ACTION_CREATE_DOCUMENT)

                //send an ACTION_CREATE_DOCUMENT intent to the system. It will open a dialog where the user can choose a location and a filename
                intentSave.addCategory(Intent.CATEGORY_OPENABLE)
                intentSave.putExtra(
                    Intent.EXTRA_TITLE,
                    "photo-" + UUID.randomUUID() + ".jpg"
                )
                intentSave.setDataAndType(photoURI, "image/jpeg")
                activity.startActivityForResult(intentSave, REQUEST_CREATE_DOCUMENT_SAVE_IMAGE)
            } else toastButtonDisabled(v)
        }


        //Draw activity (pass: rectangle, image, image view size.
        val draw_activity_button = activity.findViewById<View>(R.id.draw_activity)
        //draw_activity_button.setEnabled(false);
        draw_activity_button?.setOnClickListener {
            if (drawPointA != null && drawPointB != null) {
                val intentDraw = Intent(Intent.ACTION_CHOOSER)
                intentDraw.setClass(activity.applicationContext, TextAndImages::class.java)
                intentDraw.putExtra(
                    "drawRectangle",
                    Rect(
                        drawPointA!!.x.toInt(),
                        drawPointA!!.y.toInt(),
                        drawPointB!!.x.toInt(),
                        drawPointB!!.y.toInt()
                    )
                )
                intentDraw.putExtra("currentFile", currentFile)
                intentDraw.putExtra("currentFileZoomed", currentFileZoomed)
                startActivity(intentDraw)
            }
        }
        val computePixels = activity.findViewById<Button>(R.id.activity_compute_pixels)
        computePixels?.setOnClickListener { v: View ->
            if (currentFile != null) {
                val uri = Uri.fromFile(currentFile)
                //Uri photoURI = FileProvider.getUriForFile(activity.getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", currentFile);
                val intentDraw = Intent(Intent.ACTION_EDIT)
                intentDraw.setDataAndType(uri, "image/jpeg")
                intentDraw.putExtra("currentFile", currentFile)
                intentDraw.putExtra(
                    "maxRes",
                    Utils()
                        .getMaxRes(activity, savedInstanceState)
                )
                intentDraw.putExtra("data", uri)
                intentDraw.setClass(activity.applicationContext, GraphicsActivity::class.java)
                startActivity(intentDraw)
            } else toastButtonDisabled(v)
        }
        if (imageView != null) {
            imageView!!.setOnClickListener { }
            imageView!!.setOnTouchListener { v: View, event: MotionEvent ->
                imageView = activity.findViewById(R.id.currentImageView)
                if (currentFile != null) {
                    var location = IntArray(2)
                    v.getLocationOnScreen(location)
                    val viewX = location[0]
                    val viewY = location[1]
                    location = intArrayOf(0, 0)
                    val x = event.rawX - viewX
                    val y = event.rawY - viewY
                    val p = Point(x.toInt(), y.toInt())
                    if (drawPointA == null && drawPointB == null) {
                        System.err.println("Sélection du point A: ")
                        if (checkPointCordinates(p)) {
                            drawPointA = p
                            System.err.println("Draw point A = (" + drawPointA!!.getX() + ", " + drawPointA!!.getY() + ") ")
                        } else System.err.println("Error cordinates A")
                        // Sélectionner A
                    } else {
                        System.err.println("Sélection du point B: ")
                        // Sélectionner B
                        if (checkPointCordinates(p)) {
                            drawPointB = p
                            System.err.println("Draw point B = (" + drawPointB!!.getX() + ", " + drawPointB!!.getY() + ") ")
                        } else System.err.println("Error cordinates B")
                        //drawPointA = new Point(, Touch.getY());
                    }
                    if (drawPointA != null && drawPointB != null && drawPointA!!.getX() != drawPointB!!.getX() && drawPointA!!.getY() != drawPointB!!.getY()) {
                        System.err.println("2 points sélectionnés A et B")
                        val viewById =
                            activity.findViewById<ImageViewSelection>(R.id.currentImageView)
                        viewById.drawingRect = RectF(
                            drawPointA!!.getX().toFloat(),
                            drawPointA!!.getY().toFloat(),
                            drawPointB!!.getX().toFloat(),
                            drawPointB!!.getY().toFloat()
                        )
                        viewById.setDrawingRectState(true)
                        System.err.println(viewById.drawingRect.toString())
                        //viewById.draw(new Canvas());
                        // Désélectionner A&B
                        //drawPointA = null;


                        // PixM zone
                        currentPixM = getSelectedZone()
                        if (currentPixM != null) {
                            System.err.println("Draw Selection")
                            imageView!!.setImageBitmap(currentPixM!!.image.bitmap)
                            if (clipboard == null && Clipboard.defaultClipboard == null) {
                                Clipboard.defaultClipboard =
                                    Clipboard(currentPixM)
                                clipboard =
                                    Clipboard.defaultClipboard
                            } else if (Clipboard.defaultClipboard != null && clipboard != null) {
                                if (clipboard == null) clipboard =
                                    Clipboard.defaultClipboard
                                val read =
                                    ImageIO.read(currentFile)
                                clipboard!!.destination = viewById.drawingRect
                                //rectfs.get(rectfs.size() - 1));
                            }
                            System.err.println("Selection drawn")
                        } else {
                            System.err.println("current PixM == null")
                        }
                    }
                } else toastButtonDisabled(v)
                true
            }
        }

        //Select rectangle toggle
        val unselect = activity.findViewById<View>(R.id.unselect_rect)
        unselect?.setOnClickListener { v ->
            if (currentFile != null) {
                val read = ImageIO.read(currentFile)
                imageView!!.setImageBitmap(read.getBitmap())
                drawPointA = null
                drawPointB = null
            } else toastButtonDisabled(v)
        }
        val addText = activity.findViewById<View>(R.id.buttonAddText)
        addText?.setOnClickListener { view -> addText(view) }
        val openNewUI = activity.findViewById<View>(R.id.new_layout_app)
        openNewUI?.setOnClickListener { view: View? ->
            val intent2 = Intent()
            intent2.setClass(activity.applicationContext, MainActivity::class.java)
            startActivity(intent2)
        }
        if (!isLoaded()) {
            loadImageState(isWorkingResolutionOriginal())
        }
    }

/*
    class LoadImage(private val file: File, private val resolution: Int) :
        AsyncTask<Any?, Any?, Any?>() {
        override fun doInBackground(objects: Array<Any?>): Any? {
            try {
                val photo = BitmapFactory.decodeStream(FileInputStream(file))
                System.err.println(
                    """
                    Photo bitmap : ${file.toURI()}
                    File exists?${file.exists()}
                    """.trimIndent()
                )
                imageView??.setImageBitmap(photo)
                //imageView.setBackground(Drawable.createFromStream(new FileInputStream(currentBitmap), "chosenImage"));
                System.err.println("Image main intent loaded")
                //saveImageState();
                maxRes = resolution
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    }
*/

    @Throws(FileNotFoundException::class)
    fun getPathInput(uri: Uri?): InputStream? {
        return contentResolver.openInputStream(uri!!)
    }

    @Throws(FileNotFoundException::class)
    fun getPathOutput(uri: Uri?): OutputStream? {
        return contentResolver.openOutputStream(uri!!)
    }

    private fun getRealPathFromURI(file: Intent?): InputStream? {
        try {
            return getPathInput(file!!.data)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun toastButtonDisabled(button: View?) {
        if (currentFile == null) {
            val text = getString(R.string.button_current_file_is_null)
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }

    private fun isLoaded(): Boolean {
        return loaded
    }

    fun saveImageState(imageViewOriginal: Boolean) {
        val file = true
        imageView = findViewById(R.id.currentImageView)
        if (imageView == null) return
        val drawable = imageView!!.drawable
        var bitmapOriginal: Bitmap? = null
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) bitmapOriginal =
            drawable.bitmap else if (drawable.current is BitmapDrawable) {
            if (isWorkingResolutionOriginal()) {
                bitmapOriginal = (drawable.current as BitmapDrawable).bitmap
            }
            bitmap = PixM.getPixM(bitmap, getMaxRes()).bitmap
        } else {
            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(
                    1,
                    1,
                    Bitmap.Config.ARGB_8888
                ) // Single color bitmap will be created of 1x1 pixel
            } else {
                // ???
                if (isWorkingResolutionOriginal()) {
                    bitmapOriginal = PixM.getPixM(bitmap, 0).bitmap
                }
                bitmapOriginal = PixM.getPixM(bitmap, getMaxRes()).bitmap
                bitmap = bitmapOriginal
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(
                0, 0, if (getMaxRes() == 0) canvas.width else getMaxRes(),
                if (getMaxRes() == 0) canvas.height else getMaxRes()
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
                val filesFile = getFilesFile(MyCameraActivity.IMAGE_VIEW_JPG)
                fos = FileOutputStream(filesFile)
                bm.compress(Bitmap.CompressFormat.JPEG, 90, fos)
                System.err.println("Image updated")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        if (imageViewOriginal && bitmapOriginal != null) {
            bm = bitmapOriginal
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos) //bm is the bitmap object
            val b = baos.toByteArray()
            val encoded = Base64.encodeToString(b, Base64.DEFAULT)
            var fos: OutputStream? = null
            val filesFile = getFilesFile(MyCameraActivity.IMAGE_VIEW_ORIGINAL_JPG)
            try {
                fos = FileOutputStream(filesFile)
                bm.compress(Bitmap.CompressFormat.JPEG, 90, fos)
                System.err.println("Image updated")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    fun getImageRatio(bitmap: Bitmap): Int {
        return bitmap.width / bitmap.height
    }

    fun setMaxResImage(bitmap: Bitmap): Point? {
        val imageRatio = getImageRatio(bitmap)
        return Point(
            getMaxRes() / imageRatio,
            getMaxRes() * imageRatio
        )
    }

    private fun getMaxRes(): Int {
        val maxResText = findViewById<EditText>(R.id.editMaximiumResolution)
        maxRes = maxResText.text.toString().toDouble().toInt()
        return maxRes
    }

    fun loadImageState(originalImage: Boolean) {
        val file = true
        val ot = ""
        val imageFile = getFilesFile("imageViewOriginal.jpg")
        val imageFileLow = getFilesFile(MyCameraActivity.IMAGE_VIEW_JPG)
        if (file && imageFile.exists()) {
            try {
                var imageViewBitmap: Bitmap? = null
                imageViewBitmap = if (isWorkingResolutionOriginal()) {
                    BitmapFactory.decodeStream(FileInputStream(imageFile))
                } else {
                    BitmapFactory.decodeStream(FileInputStream(imageFileLow))
                }
                if (imageViewBitmap != null) {
                    imageView = findViewById<View>(R.id.currentImageView) as ImageView
                    if (imageView != null) {
                        imageView!!.setImageBitmap(imageViewBitmap)
                        currentFile = imageFile
                        currentBitmap = imageFile
                        System.err.println("Image reloaded")

                        //createCurrentUniqueFile();
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun createCurrentUniqueFile() {
        try {
            if (currentFile != null) {
                val photo = BitmapFactory.decodeStream(FileInputStream(currentFile))
                System.err.println("Get file (bitmap) : $photo")
                val myPhotoV2022 = writePhoto(photo, "MyPhotoV2022" + UUID.randomUUID())
                System.err.println("Written copy : " + myPhotoV2022.absolutePath)
                //photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(currentFile));
                fillGallery(photo, FileInputStream(myPhotoV2022))
                System.err.println("Set in ImageView : " + myPhotoV2022.absolutePath)
                currentFile = myPhotoV2022
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun getFilesFile(s: String): File {
        return File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files/" + File.separator + s)
    }


    private fun getSelectedZone(): PixM? {
        if (currentFile != null) {
            val pixM =
                PixM.getPixM(Objects.requireNonNull(ImageIO.read(currentFile)), maxRes.toDouble())
            if (drawPointA == null || drawPointB == null) {
                return null
            }
            val xr = 1.0 / imageView!!.width * pixM.columns
            val yr = 1.0 / imageView!!.height * pixM.lines
            val x1 = Math.min(drawPointA!!.getX() * xr, drawPointB!!.getX() * xr).toInt()
            val x2 = Math.max(drawPointA!!.getX() * xr, drawPointB!!.getX() * xr).toInt()
            val y1 = Math.min(drawPointA!!.getY() * yr, drawPointB!!.getY() * yr).toInt()
            val y2 = Math.max(drawPointA!!.getY() * yr, drawPointB!!.getY() * yr).toInt()
            val copy = pixM.copySubImage(x1, y1, x2 - x1, y2 - y1)
            System.err.printf("Copied rect = (%d, %d, %d, %d)\n", x1, y1, x2 - x1, y2 - y1)
            if (copy != null) {
                // currentFileZoomedBitmap = copy.getImage().getBitmap();
            }
            val rectF = RectF(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
            rectfs!!.add(rectF)
            val localRectIn = getLocalRectIn(null)
            if (localRectIn != null) {
                System.err.printf(
                    "Local rect = (%f, %f, %f, %f)\n",
                    localRectIn.left,
                    localRectIn.top,
                    localRectIn.right,
                    localRectIn.bottom
                )
                drawPointB = null
                drawPointA = null
                return copy
            } else {
                System.err.println("Error getLocalRectIn : returns null")
            }
        }
        return null
    }

    fun getLocalRectIn(current: RectF?): RectF? {
        var current = current
        val originalComponentView =
            RectF(0f, 0f, imageView!!.width.toFloat(), imageView!!.height.toFloat())
        //RectF destinationComponentView = originalComponentView;
        val read = ImageIO.read(currentFile)
        if (rectfs!!.size == 0) return current
        var i = 0
        current = originalComponentView
        while (i < rectfs!!.size) {
            //RectF originalImageRect = new RectF(0, 0, read.getWidth(), read.getHeight());
            //RectF newImageRect = rectfs.get(rectfs.size() - 1);
            val currentSubImage = rectfs!![i]
            current = RectF(
                current!!.left + currentSubImage.left / currentSubImage.width() * current.width(),
                current.top + currentSubImage.top / currentSubImage.height() * current.height(),
                current.right + currentSubImage.right / currentSubImage.width() * current.width(),
                current.bottom + currentSubImage.bottom / currentSubImage.height() * current.height()
            )
            i++
        }
        return current
    }

    private fun checkPointCordinates(a: Point): Boolean {
        val x = a.getX().toInt()
        val y = a.getY().toInt()
        return if (x >= 0 && x < imageView!!.width && y >= 0 && y < imageView!!.height) {
            true
        } else {
            false
        }
    }

    private fun save(toSave: Bitmap) {}

    private fun openUserData(view: View) {
        saveImageState(isWorkingResolutionOriginal())
        val intent = Intent(view.context, LicenceUserData::class.java)
        startActivity(intent)
    }

    private fun startCreation() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "file/*.*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        val intent2 = Intent.createChooser(intent, "Choose a file")
        System.err.println(intent2)
        startActivityForResult(intent2, ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER)
    }

    private fun startCreationMovie() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "file/*.*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("video/*"))
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        val intent2 = Intent.createChooser(intent, "Choose a file")
        System.err.println(intent2)
        startActivityForResult(intent2, ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER)
    }

    @Throws(FileNotFoundException::class)
    fun fillGallery(photo: Bitmap?, fileInputStream: InputStream?) {
        var photo = photo
        if (photo == null) {
            photo = BitmapFactory.decodeStream(fileInputStream)
        }
        imageView = findViewById(R.id.currentImageView)
        imageView?.setImageBitmap(photo)
        println("Image set in ImageView 4/4")
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, MyCameraActivity.CAMERA_REQUEST)
            } else {
            }
        }
    }

    /***
     * Write copy of original file in data folder
     * @param bitmap
     * @param name
     * @return
     */
    fun writePhoto(bitmap: Bitmap, name: String): File {
        var name = name
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var n = 1
        //Folder is already created
        name = name + "-photo-" + UUID.randomUUID().toString()
        var dirName1 = ""
        var dirName2 = ""
        dirName1 = Environment.getDataDirectory().path
        dirName2 = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
        n++


        //startActivityForResult(camera, 1);
        val dir1 = File(dirName1)
        val file1 = File(dirName1 + File.separator + name + ".jpg")
        val dir2 = File(dirName2)
        val file2 = File(dirName2 + File.separator + name + ".jpg")
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
                saveImageState(isWorkingResolutionOriginal())
                //System.err.println("File (photo) " + file1.getAbsolutePath());
                return file1
            }
        } catch (ex: Exception) {
            Log.e("SAVE FILE", "writePhoto: erreur file 1/2")
        }
        try {
            if (!file2.exists()) {
                ImageIO.write(BufferedImage(bitmap), "jpg", file2)
                System.err.print("Image written 2/2 $file2 return")
                //System.err.println("File (photo) " + file2.getAbsolutePath());
                saveImageState(isWorkingResolutionOriginal())
                return file2
            }
        } catch (ex: Exception) {
            Log.e("SAVE FILE", "writePhoto: erreur file 2/2")
        }
        return file1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MyCameraActivity.CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.extras != null && data.extras!!["data"] != null) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    MyCameraActivity.CAMERA_REQUEST
                )
                val bitmap = data.extras!!["data"] as Bitmap?
                imageView = findViewById(R.id.currentImageView)
                imageView?.setImageBitmap(bitmap)
                System.err.printf("Image set 4/4")
                val f = writePhoto(bitmap!!, "MyImage")

//                try {
//                    imageView.setBackground(Drawable.createFromStream(new FileInputStream(f), "chosenFile"));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                if (f == null) {
                    System.err.println("Can't write copy image file from camera ")
                } else {
                    currentFile = f
                    try {
                        fillGallery(bitmap, FileInputStream(f))
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
                saveImageState(isWorkingResolutionOriginal())
                val root = Environment.getExternalStorageDirectory().toString()
                val myDir = File("$root/saved_images")
                myDir.mkdirs()
                val timeStamp =
                    SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + UUID.randomUUID()
                val fname = "Shutta_$timeStamp.jpg"
                val finalBitmap: Bitmap
                try {
                    finalBitmap = BitmapFactory.decodeStream(FileInputStream(currentFile))
                    val file = File(myDir, fname)
                    ImageIO.write(BufferedImage(finalBitmap), "", file)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        if (requestCode == ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER && resultCode == RESULT_OK) {
            var choose_directoryData: InputStream? = null
            choose_directoryData = getRealPathFromURI(data)
            val fromStream = AnimatedImageDrawable.createFromStream(
                choose_directoryData,
                "file" + UUID.randomUUID()
            )
            val current = fromStream!!.current
            val videoFile = File(
                Environment.getExternalStorageDirectory().absolutePath + "/screenshots/",
                "myvideo.mp4"
            )
            val videoFileUri = Uri.parse(videoFile.toString())
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(videoFile.absolutePath)
            val rev = ArrayList<Bitmap>()

            //Create a new Media Player
            val mp = MediaPlayer.create(baseContext, videoFileUri)
            val millis = mp.duration
            var bitmap: Bitmap?
            var i = 0
            while (i < 10000000) {
                bitmap = retriever.getFrameAtIndex(i)
                if (bitmap == null) break
                rev.add(bitmap)
                try {
                    fillGallery(bitmap, choose_directoryData)
                    rev.removeAt(0)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                i += 1
            }
        }
        if (requestCode == ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER && resultCode == RESULT_OK) {
            var choose_directoryData: InputStream? = null
            choose_directoryData = getRealPathFromURI(data)
            var photo: Bitmap? = null
            System.err.println(choose_directoryData)
            photo = BitmapFactory.decodeStream(choose_directoryData)
            try {
                System.err.println("Get file (bitmap) : $photo")
                val myPhotoV2022 = writePhoto(photo, "MyPhotoV2022")
                System.err.println("Written copy : " + myPhotoV2022.absolutePath)
                //photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(currentFile));
                fillGallery(photo, FileInputStream(myPhotoV2022))
                System.err.println("Set in ImageView : " + myPhotoV2022.absolutePath)
                currentFile = myPhotoV2022
                System.err.println("Set as class member")
                saveImageState(isWorkingResolutionOriginal())
                System.err.println("SaveImageState")

                //createCurrentUniqueFile();
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            saveImageState(isWorkingResolutionOriginal())
        } else if (requestCode == 10000 && resultCode == RESULT_OK) {
        }
        if (requestCode == REQUEST_CREATE_DOCUMENT_SAVE_IMAGE && resultCode == RESULT_OK) {
            try {
                val uri = data!!.data

//                if (!currentFile.exists()) {
                val inputStream = FileInputStream(currentFile)
                var byteRead = -1
                val output = applicationContext.contentResolver.openOutputStream(
                    uri!!
                )
                while (inputStream.read().also { byteRead = it } != -1) {
                    output!!.write(byteRead)
                }
                output!!.flush()
                output.close()
                inputStream.close()

//                }
//            else {
//                    Toast.makeText(getApplicationContext(), "Le fichier existe déjà", Toast.LENGTH_SHORT).show();
//                    System.out.println("Le fichier existe déjà");
//                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == FILESYSTEM_WRITE_PICTURE && resultCode == RESULT_OK) {
            if (currentFile != null) {
                val path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
                FileProvider.getUriForFile(
                    Objects.requireNonNull(
                        applicationContext
                    ), BuildConfig.APPLICATION_ID + ".provider",
                    currentFile!!
                )
                val myPath = Paths.get(path, "" + UUID.randomUUID() + currentFile!!.name)
                val fileStr = currentFile!!.name
                if (myPath.toFile().exists()) {
                } else {
                    val dir =
                        File(currentDir.toString() + File.separator + "FeatureApp" + File.separator)
                    var file: File? =
                        File(currentDir.toString() + File.separator + "FeatureApp" + File.separator + fileStr)
                    if (myPath.toFile().exists() && myPath.toFile().isDirectory) {
                    }
                    if (File(myPath.toFile().parent).isDirectory && !File(myPath.toFile().parent).exists()) {
                        val file1 = File(myPath.toFile().parent)
                        file1.mkdirs()
                    }
                    val mime = MimeTypeMap.getSingleton()
                    val ext = currentFile!!.name.substring(currentFile!!.name.lastIndexOf(".") + 1)
                    val type = mime.getMimeTypeFromExtension(ext)
                    file = myPath.toFile()
                    try {
                        Files.copy(currentFile!!.toPath(), myPath)
                        var uri = Uri.fromFile(file)
                        uri = FileProvider.getUriForFile(
                            applicationContext,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file
                        )
                        val intent1 = Intent(Intent.ACTION_SEND, uri)
                        startActivity(intent1)
                        //MediaStore.EXTRA_OUTPUT
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                saveImageState(isWorkingResolutionOriginal())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadImageState(isWorkingResolutionOriginal())
    }

    override fun onRestart() {
        super.onRestart()
        loadImageState(isWorkingResolutionOriginal())
    }

    fun fillFromStorageState(data: File?) {
        var choose_directoryData: InputStream? = null
        try {
            choose_directoryData = FileInputStream(data)
            var photo: Bitmap? = null
            photo = BitmapFactory.decodeStream(choose_directoryData)
            try {
                System.err.println("Get file (bitmap) : $photo")
                val myPhotoV2022 = writePhoto(photo, "MyPhotoV2022")
                System.err.println("Written copy : " + myPhotoV2022.absolutePath)
                //photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(currentFile));
                fillGallery(photo, FileInputStream(myPhotoV2022))
                System.err.println("Set in ImageView : " + myPhotoV2022.absolutePath)
                currentFile = myPhotoV2022
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            saveImageState(isWorkingResolutionOriginal())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        loadImageState(isWorkingResolutionOriginal())
        try {
            if (savedInstanceState.containsKey("maxRes")) {
                maxRes =
                    if (savedInstanceState.getInt("maxRes") > -1) savedInstanceState.getInt("maxRes") else MyCameraActivity.MAX_RES_DEFAULT
                //currentFile = new File((String) savedInstanceState.getString("currentFile"));
                //currentBitmap = new File((String) savedInstanceState.getString("currentBitmap"));
                //currentDir = new File((String) savedInstanceState.getString("currentDir"));
                if (currentBitmap != null) {
                    val bitmap = File(currentFile!!.absolutePath)
                    try {
                        val bitmap1 = BitmapFactory.decodeStream(FileInputStream(bitmap))
                        //                try {
//                    imageView.setBackground(Drawable.createFromStream(new FileInputStream(bitmap), "chosenFile"));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                        imageView!!.setImageBitmap(bitmap1)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
                imageView = findViewById(R.id.currentImageView)
            }
        } catch (e: Exception) {
            Log.i("MyCameraActivity", "Error in OnRestoreState")
        }
        imageView = findViewById<View>(R.id.currentImageView) as ImageView
        thisActivity = this
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        saveImageState(isWorkingResolutionOriginal())
        if (outState != null) {
            //outState.putString("currentFile", currentFile.getAbsolutePath());
            //outState.putString("currentBitmap", currentBitmap.getAbsolutePath());
            //outState.putString("currentDir", currentDir.getAbsolutePath());
            //outState.putString("currentImageViewFile", currentFile.getAbsolutePath());
            outState.putInt("maxRes", maxRes)
            imageView = findViewById<View>(R.id.currentImageView) as ImageView
        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    fun unselectA(view: View?) {
        drawPointA = null
        drawPointB = null
        val imageView = findViewById<ImageViewSelection>(R.id.currentImageView)
        if (rectfs!!.size >= 1) rectfs!!.removeAt(rectfs!!.size - 1)
        imageView.setDrawingRectState(false)
    }

    fun drawActivity(view: View?) {
        val intent = Intent()
        intent.setClass(applicationContext, TextAndImages::class.java)
        val selection = intent.putExtra(
            "selection",
            Rect(
                drawPointA!!.getX().toInt(),
                drawPointA!!.getY().toInt(),
                drawPointB!!.getX().toInt(),
                drawPointB!!.getY().toInt()
            )
        )
    }

    override fun onDestroy() {
        saveImageState(isWorkingResolutionOriginal())
        super.onDestroy()
    }

    fun addText(view: View?) {
        if (currentFile != null) {
            val textIntent = Intent(Intent.ACTION_VIEW)
            textIntent.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
            textIntent.setClass(applicationContext, TextActivity::class.java)
            textIntent.putExtra("currentFile", currentFile)
            if (rectfs!!.size > 0) textIntent.putExtra(
                "rect",
                if (rectfs!!.size > 0) rectfs!![rectfs!!.size - 1] else null
            ) else textIntent.putExtra("rect", Rect())
            startActivity(textIntent)
        }
    }

    fun isWorkingResolutionOriginal(): Boolean {
        return workingResolutionOriginal
    }

    fun setWorkingResolutionOriginal(workingResolutionOriginal: Boolean) {
        this.workingResolutionOriginal = workingResolutionOriginal
    }

}