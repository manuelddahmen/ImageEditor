package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.GoogleFaceDetection
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.util.UUID


class FaceActivity : ActivitySuperClass() {
    private val OPEN_MODEL: Int = 531131
    private val CREATE_FILE: Int = 455135
    private var originalImage: File? = null
    private var selectedPoint: android.graphics.Point? = null
    private lateinit var faceOverlayView: FaceOverlayView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_face)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        drawIfBitmap();

        if (intent.extras?.getDouble("selectedPoint.x") != null) {
            selectedPoint?.x = (intent.extras?.getDouble("selectedPoint.x") as Int)
        }
        if (intent.extras?.getDouble("selectedPoint.y") != null) {
            selectedPoint?.y = (intent.extras?.getDouble("selectedPoint.y") as Int)
        }

        if (currentFile != null) {
            if (currentBitmap == null)
                currentBitmap = ImageIO.read(currentFile).getBitmap()

            Utils().loadImageInImageView(currentBitmap, faceOverlayView)

            try {
                faceOverlayView.setImageBitmap3(currentBitmap);

                faceOverlayView.setActivity(this)

            } catch (ex: RuntimeException) {
                Toast.makeText(
                    applicationContext, "Error while execute face detection",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val faceDetection = findViewById<Button>(R.id.face_detection)

        faceDetection.setOnClickListener {
            if (faceOverlayView.isFinish()) {
                faceOverlayView.isDrawing = false
                faceOverlayView.isFinish = false
                try {
                    faceOverlayView.setBitmap(ImageIO.read(currentFile).getBitmap());
                    val writePhoto =
                        Utils().writePhoto(this, faceOverlayView.mCopy, "face-contours")
                    originalImage = currentFile
                    currentFile = writePhoto
                    //Utils().loadImageInImageView(faceOverlayView.mCopy, faceOverlayView)
                } catch (ex: RuntimeException) {
                    Toast.makeText(
                        applicationContext, "Error while execute face detection",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        val faceDrawSettings = findViewById<Button>(R.id.face_draw_settings)
        faceDrawSettings.setOnClickListener {
            if (faceOverlayView.googleFaceDetection != null) {
                faceOverlayView.isFinish = true
                val intentSettings = Intent(applicationContext, FaceActivitySettings::class.java)
                if (selectedPoint != null) {
                    intentSettings.putExtra("selectedPoint.x", selectedPoint!!.x)
                    intentSettings.putExtra("selectedPoint.y", selectedPoint!!.y)
                }
                if (faceOverlayView.googleFaceDetection == null && !GoogleFaceDetection.isInstance()) {
                    faceOverlayView.performClick()
                } else if (GoogleFaceDetection.isInstance()) {
                    faceOverlayView.googleFaceDetection = GoogleFaceDetection.getInstance(false)
                }
                if (faceOverlayView.googleFaceDetection != null) {
                    //intentSettings.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
                }
                if (originalImage != null) {
                    intentSettings.putExtra("originalImage", originalImage)
                }
                passParameters(intentSettings)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Attendez que la détection de visage soit terminée.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        faceOverlayView.setOnClickListener {
            if (faceOverlayView.googleFaceDetection != null) {
                val surface = faceOverlayView.googleFaceDetection.getSurface(selectedPoint);
                if (surface != null) {
                    faceOverlayView.googleFaceDetection.selectedSurface = surface
                }
            }
        }
        faceOverlayView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val location = IntArray(2)
                v!!.getLocationOnScreen(location)
                val viewX = location[0]
                val viewY = location[1]
                val x: Float = event!!.getRawX() - viewX
                val y: Float = event!!.getRawY() - viewY

                val p = Point(x.toInt(), y.toInt())

                return true
            }
        })

        val backToMain = findViewById<Button>(R.id.back_to_main2)

        backToMain.setOnClickListener {
            faceOverlayView.isDrawing = false
            faceOverlayView.isFinish = true
            val intentBack = Intent(applicationContext, MyCameraActivity::class.java)

            if (selectedPoint != null) {
                intentBack.putExtra("selectedPoint.x", selectedPoint!!.x)
                intentBack.putExtra("selectedPoint.y", selectedPoint!!.y)
            }
            if (faceOverlayView.googleFaceDetection != null) {
                //intentBack.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
            }

            if (originalImage != null) {
                intentBack.putExtra("originalImage", originalImage)
            }

            passParameters(intentBack)

        }

        val saveModel = findViewById<Button>(R.id.save_model)
        val loadModel = findViewById<Button>(R.id.load_model)

        /*
        val startForResultSaveModel = registerForActivityResult(ActivityResultContracts.CreateDocument("*.model")) {
            if(it!=null) {
                val file = it.toFile()
                val fileOutputStream = FileOutputStream(file)
                val oos = ObjectOutputStream(fileOutputStream)
                try {
                    oos.writeUnshared(faceOverlayView.googleFaceDetection)
                } catch (ex: RuntimeException) {
                    ex.printStackTrace()
                }
            }
        }
        val startForResultLoadModel = registerForActivityResult(ActivityResultContracts.GetContent()) {
                if (it!=null) {
                    val fileInputStream = FileInputStream(it.toFile())
                    val oos = ObjectInputStream(fileInputStream)
                    try {
                        faceOverlayView.googleFaceDetection = oos.readUnshared() as GoogleFaceDetection?
                    } catch (ex: RuntimeException) {
                        ex.printStackTrace()
                    }
                }
        }

         */
        saveModel.setOnClickListener {
            if (faceOverlayView.googleFaceDetection != null) {
                /*var filesFile = getFileInPictureDir("model.model")
                var i: Int = 0
                while (filesFile.toFile().exists()) {
                    filesFile = getFileInPictureDir("model$i.model")
                    i++
                }
                val pickerInitialUri: Uri? = filesFile*/
                val intentSave = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    putExtra(Intent.EXTRA_TITLE, "face-drawings-" + UUID.randomUUID() + ".model")
                    type = "model/*.model"
                    putExtra("currentFile", currentFile)
                    putExtra("maxRes", maxRes)
                }
                if (intent.resolveActivity(packageManager) != null) {
                    try {
                        startActivityForResult(intentSave, CREATE_FILE)
                    } catch (ex: RuntimeException) {
                        ex.printStackTrace()
                        Toast.makeText(
                            applicationContext, "Error while saving model : " + ex.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        loadModel.setOnClickListener {
            var autoname: File? = getExternalFilesDir("face-drawings-" + UUID.randomUUID() + ".model")
            if(autoname==null)
                autoname = File("face-drawings-" + UUID.randomUUID() + ".model")
            val photoURI = FileProvider.getUriForFile(
                applicationContext,
                applicationContext.packageName + ".provider",
                autoname)
            val intentLoad = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_TITLE, "face-drawings-" + UUID.randomUUID() + ".model")
                type = "model/*.model"
                putExtra("currentFile", currentFile)
                putExtra("maxRes", maxRes)
            }
            val intent2 = Intent.createChooser(intentLoad, "Choose a file")
            try {
                startActivityForResult(intent2, OPEN_MODEL)
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Error while loading model (open file dialog): " + ex.message,
                    Toast.LENGTH_LONG
                ).show()
            }
/*
            val photoURI = FileProvider.getUriForFile(
                applicationContext,
                applicationContext.packageName + ".provider",
                File(currentFile.absolutePath + ".model")
            )
            val intentLoad = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_TITLE, "face-drawings-" + UUID.randomUUID() + ".model")
                setDataAndType(photoURI, "model*.model")
                putExtra("currentFile", currentFile)
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("model*.model"))
            }

            val intent2 = Intent.createChooser(intentLoad, "Choose a file")
            try {
                startActivityForResult(intent2, OPEN_MODEL)
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Error while loading model : " + ex.message,
                    Toast.LENGTH_LONG
                ).show()
            }
*/
        }
    }

    fun getFileInPictureDir(s: String): Uri {

        val permissionsStorage = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            //, Manifest.permission.READ_MEDIA_IMAGES
        )
        val requestExternalStorage = 1
        val permission1 = ActivityCompat.checkSelfPermission(
            applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val permission2 = ActivityCompat.checkSelfPermission(
            applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission3 = ActivityCompat.checkSelfPermission(
            applicationContext, Manifest.permission.READ_MEDIA_IMAGES
        )

        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                permissionsStorage,
                requestExternalStorage
            )
        }
        if (permission2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                permissionsStorage,
                requestExternalStorage
            )
        }
        if (permission3 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                permissionsStorage,
                requestExternalStorage
            )
        }


        val picturesDirectory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
        val uriFile = File(picturesDirectory.absolutePath + '/' + s)
        return FileProvider.getUriForFile(
            applicationContext,
            applicationContext.packageName + ".provider",
            uriFile
        )
    }

    fun getFileContent(requestCode: Int, resultCode: Int, result: Intent?): InputStream? {

        val uri: Uri? = result?.data
        var fileContent: ByteArray? = null
        var inputStream: InputStream? = null

        try {
            if (uri != null) {

                val cursor = contentResolver.query(uri, null, null, null, null)

                val nameIndex = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()

                val name = cursor.getString(nameIndex)
                val size = cursor.getLong(sizeIndex).toString()
                inputStream = contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    fileContent = ByteArray(size as Int)
                    inputStream.read(fileContent)
                    fileContent = ByteArray(1024)
                    val baos = ByteArrayOutputStream()
                    var read: Int
                    while (inputStream.read(fileContent)
                            .also { read = it } > -1
                    ) baos.write(fileContent, 0, read)
                    fileContent = baos.toByteArray()


                    baos.close()
                }
            }
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                }
            }
        }

        return inputStream
    }

    fun createFileDocument(requestCode: Int, resultCode: Int, result: Intent?): ByteArray? {

        val uri: Uri? = result?.data
        var fileContent: ByteArray? = null
        var outputStream: OutputStream? = null

        try {
            if (uri != null) {

                val cursor = contentResolver.query(uri, null, null, null, null)

                val nameIndex = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()

                val name = cursor.getString(nameIndex)
                val size = cursor.getLong(sizeIndex).toString()
                outputStream = contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    fileContent = ByteArray(size as Int)
                    outputStream.write(fileContent)
                    outputStream.close()
                }
            }
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                }
            }
        }

        return fileContent
    }

    @Deprecated("Deprecated")
    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        super.onActivityResult(requestCode, resultCode, result)
        if ((resultCode == RESULT_OK) && result != null && ((result.extras != null &&
                    result.extras!!.get(Intent.EXTRA_STREAM) != null) ||
                    result.data != null)
        ) {
            var get: Uri? = null
            try {
                get = result.extras!!.get("data") as Uri
            } catch (ex: RuntimeException) {
                try {
                    get = result.extras!!.get(Intent.EXTRA_STREAM) as Uri
                } catch (ex1: RuntimeException) {
                    try {
                        get = result.data
                    } catch (ex2: RuntimeException) {
                        try {
                            val cursor =
                                contentResolver.query(result.data!!, null, null, null, null)

                            val nameIndex = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            val sizeIndex = cursor!!.getColumnIndex(OpenableColumns.SIZE)
                            cursor!!.moveToFirst()

                            val name = cursor!!.getString(nameIndex)
                            val size = cursor!!.getLong(sizeIndex).toString()
                        } catch (_: RuntimeException) {

                        } catch (_: java.lang.NullPointerException) {

                        }
                    }
                }
            }


            var file: File? = null

            try {
                //file = get.toFile()
            } catch (ex: RuntimeException) {
                Toast.makeText(
                    applicationContext,
                    "FIle==null after filechooser ",
                    Toast.LENGTH_LONG
                )
                    .show()
                return
            } catch (ex1: NullPointerException) {
                Toast.makeText(
                    applicationContext,
                    "FIle==null after filechooser ",
                    Toast.LENGTH_LONG
                )
                    .show()
                return
            }
            if (requestCode == CREATE_FILE) {
                val openOutputStream = contentResolver.openOutputStream(result.data as Uri)
                if (openOutputStream != null) {
                    val oos = ObjectOutputStream(openOutputStream)
                    try {
//                        oos.writeUnshared(faceOverlayView.googleFaceDetection)
//                        oos.close()

                        val instance = faceOverlayView.googleFaceDetection

                        instance.encode(DataOutputStream(openOutputStream))

                        oos.flush()
                        oos.close()
                    } catch (ex: RuntimeException) {
                        ex.printStackTrace()
                        return
                    } catch (ex1: java.io.NotSerializableException) {
                        return
                    }
                }
            } else if (requestCode == OPEN_MODEL) {
                val inputStream = this.getFileContent(requestCode, resultCode, result) ?: return
                var dataInputStream: DataInputStream = DataInputStream(inputStream)
                faceOverlayView.googleFaceDetection =
                    GoogleFaceDetection().decode(dataInputStream) as GoogleFaceDetection?
            }
        }
    }

    fun onceDrawFaceoverlay(faceOverlayView: FaceOverlayView) {

    }

    fun checkPointCordinates(a: Point): Boolean {
        val x = a.x
        val y = a.y
        return if (x >= 0 && x < faceOverlayView.width && y >= 0 && y < faceOverlayView.height) {
            true
        } else {
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var g = 0
        for (granted in grantResults) {
            g = g + if (granted == PackageManager.PERMISSION_GRANTED) 1 else 0
        }

        if (g > 0 && requestCode == 4232403) {
            run {
                ;
            }
        }
    }


}


