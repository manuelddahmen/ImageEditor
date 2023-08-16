package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.android.datatransport.BuildConfig
import javaAnd.awt.Point
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.PixM
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Objects
import java.util.Properties
import java.util.UUID

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
@ExperimentalCamera2Interop
class MyCameraActivityVerion5 : FragmentSuperClass() {
    var properties = Properties()
    private var thisActivity: FragmentSuperClass? = null
    private var currentDir: File? = null
    private val currentFileOriginalResolution: File? = null
    private val currentFileZoomed: File? = null
    private val beta = false
    private var drawPointA: Point? = null
    private var drawPointB: Point? = null
    private var rectfs: List<RectF> = ArrayList()
    private val currentFileZoomedBitmap: Bitmap? = null
    private var currentPixM: PixM? = null
    private var isLoaded = false
    var isWorkingResolutionOriginal = false
    private var clipboard: Clipboard? = null
    private val copied = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main, container, false)
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        maxRes = Utils().getMaxRes(requireActivity3())
        imageView = activity.findViewById(R.id.currentImageView)
        rectfs = ArrayList()
        isLoaded = true
        currentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val takePhoto = requireActivity3().findViewById<Button>(R.id.takePhotoButton)
        takePhoto.setOnClickListener {
            if (requireActivity3().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA /*, Manifest.permission.READ_MEDIA_IMAGES*/),
                    MY_CAMERA_PERMISSION_CODE
                )
            }
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(
                cameraIntent,
                CAMERA_REQUEST
            )


            //findNavController(thisActivity, R.id.).navigate(R.id.flow_step_one_dest, null)
        }
        val effectsButton2 = requireActivity3().findViewById<Button>(R.id.effectsButtonNew)
        effectsButton2.setOnClickListener {
            if (currentFile != null) {
                imageView = requireActivity3().findViewById(R.id.currentImageView)
                val intent1 = Intent(requireActivity3().applicationContext, ChooseEffectsActivity2::class.java)
                passParameters(intent1)
            }
        }
        val fromFiles = requireActivity3().findViewById<View>(R.id.choosePhotoButton)
        fromFiles.setOnClickListener { v: View? -> startCreation() }
        val copy = requireActivity3().findViewById<View>(R.id.copy)
        copy.setOnClickListener { v: View? ->
            if (clipboard != null) {
                clipboard!!.copied = true
                copy.setBackgroundColor(Color.rgb(40, 255, 40))
                Toast.makeText(requireActivity3().applicationContext, "Subimage copied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val paste = requireActivity3().findViewById<View>(R.id.paste)
        paste.setOnClickListener { v: View? ->
            clipboard = Clipboard.defaultClipboard
            if (currentFile != null) {
                if (clipboard != null && clipboard!!.copied && clipboard!!.destination != null && clipboard!!.source != null
                ) {
                    val dest = PixM(
                        Objects.requireNonNull(
                            ImageIO.read(
                                currentFile
                            )
                        ).bitmap
                    )
                    val x = Math.min(
                        clipboard!!.destination.right,
                        clipboard!!.destination.left
                    ).toInt()
                    val y = Math.min(
                        clipboard!!.destination.bottom,
                        clipboard!!.destination.top
                    ).toInt()
                    val w =
                        Math.abs(clipboard!!.destination.right - clipboard!!.destination.left)
                            .toInt()
                    val h =
                        Math.abs(clipboard!!.destination.bottom - clipboard!!.destination.top)
                            .toInt()
                    dest.pasteSubImage(clipboard!!.source, x, y, w, h)
                    val bitmap = dest.bitmap
                    currentFile = Utils()
                        .writePhoto(requireActivity3(), bitmap, "copy_paste")
                    Utils().setImageView(requireActivity3().imageView, bitmap)
                    paste.setBackgroundColor(Color.rgb(40, 255, 40))
                    copy.setBackgroundColor(Color.rgb(40, 255, 40))
                    Toast.makeText(
                        requireActivity3().applicationContext,
                        R.string.subimage_pasted,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    System.err.println(
                        "Image copiée: " + clipboard!!.source
                            .columns + " " + clipboard!!.source.lines
                    )
                    System.err.println("Zone de collage: x:$x, y:$y w:$w h:$h")
                }
            } else toastButtonDisabled(v)
        }
        val about = requireActivity3().findViewById<View>(R.id.About)
        about.setOnClickListener { v -> openUserData(v) }
        val shareView = requireActivity3().findViewById<View>(R.id.share)
        shareView.setOnClickListener { v ->
            if (currentFile != null) {
                val uri = Uri.fromFile(currentFile)
                val photoURI = FileProvider.getUriForFile(
                    requireActivity3().applicationContext,
                    requireActivity3().applicationContext.packageName + ".provider",
                    requireActivity3().currentFile
                )
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI)
                shareIntent.setDataAndType(photoURI, "image/jpeg")
                shareIntent.putExtra("data", photoURI)
                startActivity(shareIntent)
            } else toastButtonDisabled(v)
        }
        shareView.isEnabled = true
        val save = requireActivity3().findViewById<View>(R.id.save)
        save.setOnClickListener(View.OnClickListener { v ->
            if (currentFile != null) {
                saveImageState(true)
                val permissionsStorage = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                val requestExternalStorage = 1
                val permission1 = ActivityCompat.checkSelfPermission(
                    requireActivity3().applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                val permission2 = ActivityCompat.checkSelfPermission(
                    requireActivity3().applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                if (permission1 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        requireActivity3(),
                        permissionsStorage,
                        requestExternalStorage
                    )
                }
                if (permission2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        requireActivity3(),
                        permissionsStorage,
                        requestExternalStorage
                    )
                }
                val picturesDirectory =
                    File(requireActivity3().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
                var target: Path? = null
                target = try {
                    Files.copy(
                        requireActivity3().currentFile.toPath(),
                        File(picturesDirectory.absolutePath + UUID.randomUUID() + ".jpg").toPath()
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@OnClickListener
                }
                val photoURI = FileProvider.getUriForFile(
                    requireActivity3().applicationContext,
                    requireActivity3().applicationContext.packageName + ".provider",
                    if (target == null) requireActivity3().currentFile else target.toFile()
                )


                //ActivityResultContracts.CreateDocument createDocument = new ActivityResultContracts.CreateDocument("image/jpeg");
                val intentSave = Intent(Intent.ACTION_CREATE_DOCUMENT)
                //        createDocument.createIntent(getApplicationContext(), "Save as new image");


                //send an ACTION_CREATE_DOCUMENT intent to the system. It will open a dialog where the user can choose a location and a filename
                intentSave.addCategory(Intent.CATEGORY_OPENABLE)
                intentSave.putExtra(Intent.EXTRA_TITLE, "photo-" + UUID.randomUUID() + ".jpg")
                intentSave.setDataAndType(photoURI, "image/jpeg")
                //createDocument.parseResult(new ²);
                startActivityForResult(intentSave, REQUEST_CREATE_DOCUMENT_SAVE_IMAGE)
            } else toastButtonDisabled(v)
        })


        //Draw activity (pass: rectangle, image, image view size.
        val face = requireActivity3().findViewById<View>(R.id.buttonFace)
        face.setOnClickListener { view: View? ->
            if (currentFile != null) {
                val faceIntent = Intent(Intent.ACTION_VIEW)
                faceIntent.setClass(requireActivity3().applicationContext, FaceActivity::class.java)
                if (currentPixM != null) {
                    faceIntent.putExtra("zoom", currentPixM!!.bitmap)
                }
                passParameters(faceIntent)
            }
        }
        val computePixels = requireActivity3().findViewById<Button>(R.id.activity_compute_pixels)
        computePixels.setOnClickListener { v: View? ->
            if (currentFile != null) {
                val uri = Uri.fromFile(currentFile)
                //Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", currentFile);
                val intentDraw =
                    Intent(requireActivity3().applicationContext, GraphicsActivity::class.java)
                intentDraw.setDataAndType(uri, "image/jpeg")
                intentDraw.putExtra("data", uri)
                passParameters(intentDraw)
            } else toastButtonDisabled(v)
        }
        requireActivity3().imageView.setOnClickListener { }
        requireActivity3().imageView.setOnTouchListener { v: View, event: MotionEvent ->
            imageView = requireActivity3().findViewById(R.id.currentImageView)
            if (currentFile != null) {
                val location = IntArray(2)
                v.getLocationOnScreen(location)
                val viewX = location[0]
                val viewY = location[1]
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
                        requireActivity3().findViewById<ImageViewSelection>(R.id.currentImageView)
                    val bitmap =
                        ImageIO.read(currentFile).bitmap
                    val rectF = getSelectedCordsImgToView(bitmap, viewById)
                    viewById.drawingRect = rectF
                    viewById.setDrawingRectState(true)
                    System.err.println(viewById.drawingRect.toString())
                    if (rectF != null) {
                        currentPixM = getSelectedZone(getSelectedCordsImgToView(bitmap, viewById))
                        if (currentPixM != null) {
                            System.err.println("Draw Selection")
                            Utils()
                                .setImageView(requireActivity3().imageView, currentPixM!!.image.bitmap)
                            if (clipboard == null && Clipboard.defaultClipboard == null) {
                                Clipboard.defaultClipboard =
                                    Clipboard(currentPixM)
                                clipboard =
                                    Clipboard.defaultClipboard
                            }
                            if (Clipboard.defaultClipboard != null && clipboard != null) {
                                if (clipboard!!.copied) {
                                    clipboard!!.destination = rectF
                                    drawPointA = null
                                    drawPointB = null
                                } else {
                                    clipboard!!.source = currentPixM
                                }
                            }
                            System.err.println("Selection drawn")
                        } else {
                            System.err.println("current PixM == null")
                        }
                    }
                }
            } else toastButtonDisabled(v)
            true
        }

        //Select rectangle toggle
        val unselect = requireActivity3().findViewById<View>(R.id.unselect_rect)
        unselect.setOnClickListener { v ->
            if (currentFile != null) {
                val read = ImageIO.read(currentFile)
                if (read.getBitmap() != null) {
                    Utils()
                        .setImageView(requireActivity3().imageView, read.getBitmap())
                }
                drawPointA = null
                drawPointB = null
            } else toastButtonDisabled(v)
        }
        val addText = requireActivity3().findViewById<View>(R.id.buttonAddText)
        addText.setOnClickListener { view: View? ->
            addText(
                view
            )
        }
        val openNewUI = requireActivity3().findViewById<View>(R.id.new_layout_app)
        openNewUI.setOnClickListener { view: View? ->
            val intent2 = Intent()
            intent2.setClass(
                requireActivity3().applicationContext,
                MyCameraActivity::class.java
            )
            passParameters(intent2)
        }

//        Button crashButton = new Button(this);
//        crashButton.setText("Test Crash");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
        if (requireActivity3().currentFile != null) {
            drawIfBitmap()
        } else {
            restoreInstanceState()
        }
        val settings = requireActivity3().findViewById<View>(R.id.settings)
        settings.setOnClickListener {
            val settingsIntent = Intent(
                requireActivity3().applicationContext,
                SettingsScrollingActivity::class.java
            )
            passParameters(settingsIntent)
        }
    }

    private fun getSelectedCordsImgToView(bitmap: Bitmap, imageView: ImageView): RectF? {
        if (requireActivity3().currentFile != null) {
            val pixM = PixM(Objects.requireNonNull(ImageIO.read(requireActivity3().currentFile)).bitmap)
            if (requireActivity3().drawPointA == null || requireActivity3().drawPointB == null) {
                return null
            }
            val xr = 1.0 / requireActivity3().imageView.width * pixM.columns
            val yr = 1.0 / requireActivity3().imageView.height * pixM.lines
            val x1 = Math.min(requireActivity3().drawPointA!!.getX() * xr, requireActivity3().drawPointB!!.getX() * xr).toInt()
            val x2 = Math.max(requireActivity3().drawPointA!!.getX() * xr, requireActivity3().drawPointB!!.getX() * xr).toInt()
            val y1 = Math.min(requireActivity3().drawPointA!!.getY() * yr, requireActivity3().drawPointB!!.getY() * yr).toInt()
            val y2 = Math.max(requireActivity3().drawPointA!!.getY() * yr, requireActivity3().drawPointB!!.getY() * yr).toInt()
            return RectF(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
        }
        return null
    }

    private fun getSelectedCordsViewToImg(imageView: ImageView, rectF: RectF): RectF? {
        if (currentFile != null) {
            val x1 = Math.min(rectF.left, rectF.right).toInt()
            val x2 = Math.max(rectF.left, rectF.right).toInt()
            val y1 = Math.min(rectF.bottom, rectF.top).toInt()
            val y2 = Math.max(rectF.bottom, rectF.top).toInt()
            val xr = 1.0 / (1.0 * imageView.width / (x2 - x1))
            val yr = 1.0 / (1.0 * imageView.height / y2 - y1)
            return RectF(x1.toFloat(), y1.toFloat(), (x2 - x1).toFloat(), (y2 - y1).toFloat())
        }
        return null
    }

    internal inner class LoadImage(private val file: File, private val resolution: Int) :
        AsyncTask<Any?, Any?, Any?>() {
        override fun doInBackground(objects: Array<Any?>): Any? {
            try {
                val photo = BitmapFactory.decodeStream(
                    FileInputStream(
                        file
                    )
                )
                System.err.println(
                    """
                        Photo bitmap : ${file.toURI()}
                        File exists?${file.exists()}
                        """.trimIndent()
                )
                Utils().setImageView(requireActivity3().imageView, photo)
                //imageView.setBackground(Drawable.createFromStream(new FileInputStream(currentBitmap), "chosenImage"));
                System.err.println("Image main intent loaded")
                //saveImageState();
                val maxRes = resolution
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    }

    @Throws(FileNotFoundException::class)
    fun getPathOutput(uri: Uri?): OutputStream? {
        return requireActivity3().applicationContext.contentResolver.openOutputStream(
            uri!!
        )
    }

    public override fun getRealPathFromIntentData(file: Intent): InputStream? {
        try {
            return requireActivity3().getPathInput(file.data)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun toastButtonDisabled(button: View?) {
        if (currentFile == null) {
            val text = getString(R.string.button_current_file_is_null)
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(requireActivity3().applicationContext, text, duration)
            toast.show()
        }
    }

    fun saveImageState(imageViewOriginal: Boolean) {
        val file = true
        if (imageView == null) return
        val drawable = requireActivity3().imageView.drawable
        var bitmapOriginal: Bitmap? = null
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) bitmapOriginal =
            drawable.bitmap else if (drawable.current is BitmapDrawable) {
            if (isWorkingResolutionOriginal) {
                bitmapOriginal = (drawable.current as BitmapDrawable).bitmap
            }
            bitmap = PixM.getPixM(bitmap!!, maxRes).bitmap
        } else {
            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            // Single color bitmap will be created of 1x1 pixel
            } else {
                // ???
                if (isWorkingResolutionOriginal) {
                    bitmapOriginal = PixM.getPixM(bitmap!!, 0).bitmap
                }
                bitmapOriginal = PixM.getPixM(bitmap!!, maxRes).bitmap
                bitmap = bitmapOriginal
            }
            val canvas = Canvas(bitmap!!)
            drawable.setBounds(
                0, 0, if (maxRes == 0) canvas.width else maxRes,
                if (maxRes == 0) canvas.height else maxRes
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
                val filesFile = getFilesFile(IMAGE_VIEW_JPG)
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
            val filesFile = getFilesFile(IMAGE_VIEW_ORIGINAL_JPG)
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

    fun setMaxResImage(bitmap: Bitmap): Point {
        val imageRatio = getImageRatio(bitmap)
        return Point(
            maxRes / imageRatio,
            maxRes * imageRatio
        )
    }

    fun loadImageState(originalImage: Boolean) {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
            ), 0
        )
        val file = true
        val ot = ""
        val imageFile = getFilesFile("imageViewOriginal.jpg")
        val imageFileLow = getFilesFile(IMAGE_VIEW_JPG)
        if (file && imageFile.exists()) {
            try {
                var imageViewBitmap: Bitmap? = null
                imageViewBitmap = if (isWorkingResolutionOriginal) {
                    BitmapFactory.decodeStream(FileInputStream(imageFile))
                } else {
                    BitmapFactory.decodeStream(FileInputStream(imageFileLow))
                }
                if (imageViewBitmap != null) {
                    if (imageView != null) {
                        Utils().setImageView(requireActivity3().imageView, imageViewBitmap)
                        currentFile = imageFile
                        System.err.println("Image reloaded")
                        currentFile = Utils().createCurrentUniqueFile(requireActivity3())
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }

    private fun getSelectedZone(selectedCords: RectF?): PixM? {
        if (currentFile != null) {
            val pixM = PixM(Objects.requireNonNull(ImageIO.read(currentFile)))
            return pixM.copySubImage(
                selectedCords!!.left.toInt(),
                selectedCords.top.toInt(),
                (selectedCords.right - selectedCords.left).toInt(),
                (selectedCords.bottom - selectedCords.top).toInt()
            )
        }
        return null
    }

    fun getLocalRectIn(current: RectF): RectF {
        var current = current
        val originalComponentView =
            RectF(0f, 0f, imageView!!.width.toFloat(), requireActivity3().imageView.height.toFloat())
        //RectF destinationComponentView = originalComponentView;
        val read = ImageIO.read(currentFile)
        if (rectfs.size == 0) return current
        var i = 0
        current = originalComponentView
        while (i < rectfs.size) {
            //RectF originalImageRect = new RectF(0, 0, read.getWidth(), read.getHeight());
            //RectF newImageRect = rectfs.get(rectfs.size() - 1);
            val currentSubImage = rectfs[i]
            current = RectF(
                current.left + currentSubImage.left / currentSubImage.width() * current.width(),
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
        return if (x >= 0 && x < requireActivity3().imageView.width && y >= 0 && y < requireActivity3().imageView.height) {
            true
        } else {
            false
        }
    }

    private fun save(toSave: Bitmap) {}
    private fun openUserData(view: View) {
        //saveImageState(isWorkingResolutionOriginal());
        val intent = Intent(view.context, LicenceUserData::class.java)
        passParameters(intent)
    }

    /***Dépendances des modules
     * Ce module DocumentsUI dépend de l'autorisation MANAGE_DOCUMENTS protégée par l'autorisation de signature ; une classe d'autorisation supplémentaire garantit qu'une seule application sur l'appareil dispose de l'autorisation MANAGE_DOCUMENTS .
     *
     */
    private fun startCreation() {
        requireWriteTempFilePermission()
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*"))
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        val intent2 = Intent.createChooser(intent, "Choose a file")
        System.err.println(intent2)
        startActivityForResult(intent2, ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER)
    }

    private fun startCreationMovie() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("file/*.*")
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
        val p2 = photo
        Utils().setImageView(requireActivity3().imageView, p2!!)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
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
        val shouldOverwrite = true
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var n = 1
        //Folder is already created
        name = name + "-photo-" + UUID.randomUUID().toString()
        var dirName1 = ""
        var dirName2 = ""
        dirName1 = Environment.getDataDirectory().path
        dirName2 = requireActivity3().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
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
                ImageIO.write(BufferedImage(bitmap), "jpg", file1, shouldOverwrite)
                System.err.print("Image written 1/2 $file1 return")
                saveImageState(isWorkingResolutionOriginal)
                //System.err.println("File (photo) " + file1.getAbsolutePath());
                return file1
            }
        } catch (ex: Exception) {
            Log.e("SAVE FILE", "writePhoto: erreur file 1/2")
        }
        try {
            if (!file2.exists()) {
                ImageIO.write(BufferedImage(bitmap), "jpg", file2, shouldOverwrite)
                System.err.print("Image written 2/2 $file2 return")
                //System.err.println("File (photo) " + file2.getAbsolutePath());
                saveImageState(isWorkingResolutionOriginal)
                return file2
            }
        } catch (ex: Exception) {
            Log.e("SAVE FILE", "writePhoto: erreur file 2/2")
        }
        return file1
    }

    /*
        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
        public boolean requestPermissionAppStorage() {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
            )
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_MEDIA_IMAGES
                    )
                            != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_MEDIA_IMAGES}, INT_READ_MEDIA_IMAGES);

                return (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_MEDIA_IMAGES
                )
                        != PackageManager.PERMISSION_GRANTED);
            } else return true;
        }
    */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.extras != null && data.extras!!["data"] != null) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
                val bitmap = data.extras!!["data"] as Bitmap?
                imageView = requireActivity3().findViewById(R.id.currentImageView)
                if (bitmap != null && imageView != null) {
                    Utils().setImageView(requireActivity3().imageView, bitmap)
                    System.err.printf("Image set 4/4")
                    currentFile = Utils().writePhoto(
                        requireActivity3(),
                        bitmap,
                        "camera-"
                    )
                }
            }
        }
        if (requestCode == ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER && resultCode == RESULT_OK) {
            var choose_directoryData: InputStream? = null
            choose_directoryData = getRealPathFromIntentData(data!!)
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
            val mp = MediaPlayer.create(requireActivity3().baseContext, videoFileUri)
            val millis = mp.duration
            var bitmap: Bitmap?
            var i = 0
            while (i < 100) {
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
            imageView = requireActivity3().findViewById(R.id.currentImageView)


            //DownloadImageTask downloadImageTask = new DownloadImageTask((ImageViewSelection) findViewById(R.id.currentImageView));

            //AsyncTask<String, Void, Bitmap> execute = downloadImageTask.execute(getRealPathFromURI(data).toString());
            var choose_directoryData: InputStream? = null
            choose_directoryData = getRealPathFromIntentData(data!!)
            if (choose_directoryData == null) {
                choose_directoryData = try {
                    FileInputStream(data.dataString)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    return
                }
            }
            if (choose_directoryData != null) {
                loadImage(choose_directoryData, true)
            }
        } else if (requestCode == 10000 && resultCode == RESULT_OK) {
        }
        if (requestCode == REQUEST_CREATE_DOCUMENT_SAVE_IMAGE && resultCode == RESULT_OK) {
            try {
                val uri = data!!.data

//                if (!currentFile.exists()) {
                val inputStream = FileInputStream(currentFile)
                var byteRead = -1
                val output = requireActivity3().applicationContext.contentResolver.openOutputStream(
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
                        requireActivity3().applicationContext
                    ), BuildConfig.APPLICATION_ID + ".provider", requireActivity3().currentFile
                )
                val myPath = Paths.get(path, "" + UUID.randomUUID() + requireActivity3().currentFile.name)
                val fileStr = requireActivity3().currentFile.name
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
                    val ext = requireActivity3().currentFile.name.substring(requireActivity3().currentFile.name.lastIndexOf(".") + 1)
                    val type = mime.getMimeTypeFromExtension(ext)
                    file = myPath.toFile()
                    try {
                        Files.copy(requireActivity3().currentFile.toPath(), myPath)
                        var uri = Uri.fromFile(file)
                        uri = FileProvider.getUriForFile(
                            requireActivity3().applicationContext,
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
                saveImageState(isWorkingResolutionOriginal)
            }
        }
    }

    private fun requireWriteTempFilePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 2621621
            )
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2621621)
        }
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
            saveImageState(isWorkingResolutionOriginal)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    fun unselectA(view: View?) {
        drawPointA = null
        drawPointB = null
        val imageView = requireActivity3().findViewById<ImageViewSelection>(R.id.currentImageView)
        if (rectfs.size >= 1) requireActivity3().rectfs.removeAt(rectfs.size - 1)
        imageView.setDrawingRectState(false)
    }

    fun addText(view: View?) {
        if (currentFile != null && imageView != null) {
            val textIntent = Intent(Intent.ACTION_VIEW)
            textIntent.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
            textIntent.setClass(requireActivity3().applicationContext, TextActivity::class.java)
            textIntent.putExtra("currentFile", currentFile)
            if (rectfs.size > 0) textIntent.putExtra(
                "rect",
                if (rectfs.size > 0) rectfs[rectfs.size - 1] else null
            ) else textIntent.putExtra("rect", Rect())
            startActivity(textIntent)
        }
    }
    override fun onPause() {
        super.onPause()
        if (currentFile != null) saveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        if (currentFile == null) loadInstanceState()
    }

    companion object {
        private const val INT_READ_MEDIA_IMAGES = 445165
        private const val TAG = "one.empty3.feature.app.maxSdk29.pro.MyCameraActivity"
        const val MAX_RES_DEFAULT = 200
        const val IMAGE_VIEW_ORIGINAL_JPG = "imageViewOriginal.jpg"
        const val IMAGE_VIEW_JPG = "imageView.jpg"
        private const val REQUEST_CREATE_DOCUMENT_SAVE_IMAGE = 4072040
        const val CAMERA_REQUEST = 1888
        private const val MY_CAMERA_PERMISSION_CODE = 100
        private const val ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER = 9998
        private const val ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER = 9999
        private const val FILESYSTEM_WRITE_PICTURE = 1111
        private const val MY_EXTERNAL_STORAGE_PERMISSION_CODE = 7777
    }
    private fun requireActivity3() : MyCameraActivity {
        return (activity as MyCameraActivity)
    }

}
