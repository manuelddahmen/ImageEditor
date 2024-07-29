package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature0.GoogleFaceDetection
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.util.UUID


class FaceActivity : ActivitySuperClass() {
    private var thisActivity: Activity = this
    private val OPEN_MODEL: Int = 531131
    private val CREATE_FILE: Int = 455135
    private var originalImage: File? = null
    private lateinit var selectedPoint: android.graphics.Point
    private lateinit var faceOverlayView: FaceOverlayView
    override fun onCreate(savedInstanceState: Bundle?) {
        thisActivity = this
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_face)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        drawIfBitmap();

        selectedPoint = Point()
        try {
            if (intent.extras?.getDouble("selectedPoint.x") != null && intent.extras?.getDouble("selectedPoint.y") != null) {
                selectedPoint.x = (intent.extras?.getDouble("selectedPoint.x")!!.toInt())
                selectedPoint.y = (intent.extras?.getDouble("selectedPoint.y")!!.toInt())
            }
        } catch (ex : RuntimeException) {
            ex.printStackTrace()
        }
        if (currentFile.currentFile != null) {
            try {
                if (currentBitmap == null) {
                val currentBitmapRef: BufferedImage = ImageIO.read(currentFile.currentFile)
                if (currentBitmapRef != null && currentBitmapRef.bitmap != null) {
                    currentBitmap = currentBitmapRef.bitmap
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
            } else {
                Toast.makeText(
                    applicationContext, "Error while execute face detection",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (ex:RuntimeException) {
        }
        } else {
            Toast.makeText(
                applicationContext, "Error while execute face detection",
                Toast.LENGTH_LONG
            ).show()
        }
        val faceDetection = findViewById<Button>(R.id.face_detection)

        faceDetection.setOnClickListener {
            if (faceOverlayView.isFinish()) {
                faceOverlayView.isDrawing = false
                faceOverlayView.isFinish = false
                try {
                    faceOverlayView.setBitmap(ImageIO.read(currentFile.currentFile).getBitmap());
                    val writePhoto =
                        Utils().writePhoto(this, faceOverlayView.mCopy, "face-contours")
                    originalImage = currentFile.currentFile
                    currentFile.add(DataApp(writePhoto))
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
            try {
                if (faceOverlayView.googleFaceDetection != null) {
                    GoogleFaceDetection.setInstance(faceOverlayView.googleFaceDetection)
                    GoogleFaceDetection.setInstance2(faceOverlayView.googleFaceDetection)
                    faceOverlayView.isFinish = true
                    val intentSettings =
                        Intent(applicationContext, FaceActivitySettings::class.java)
                    if (selectedPoint != null) {
                        intentSettings.putExtra("selectedPoint.x", selectedPoint!!.x)
                        intentSettings.putExtra("selectedPoint.y", selectedPoint!!.y)
                    }
                    if (faceOverlayView.googleFaceDetection == null && !GoogleFaceDetection.isInstance()) {
                        if (GoogleFaceDetection.getInstance2() != null) {
                            faceOverlayView.googleFaceDetection =
                                GoogleFaceDetection.getInstance2()
                            GoogleFaceDetection.setInstance(
                                GoogleFaceDetection.getInstance(
                                    false, currentBitmap
                                )
                            )
                        }
                    } else if (faceOverlayView.googleFaceDetection != null && GoogleFaceDetection.isInstance()) {
                        //intentSettings.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
                        GoogleFaceDetection.setInstance(faceOverlayView.googleFaceDetection)
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.face_le_fichier_ouvert_ou_le_visage_d_tect_existe),
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (GoogleFaceDetection.isInstance()) {
                        faceOverlayView.googleFaceDetection =
                            GoogleFaceDetection.getInstance(false, currentBitmap)
                    }

                    if (originalImage != null) {
                        intentSettings.putExtra("originalImage", originalImage)
                    }
                    passParameters(intentSettings)
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.face_attendez_que_la_d_tection_de_visage_soit_termin_e),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    getString(R.string.error_icon_content_description) + ex.localizedMessage + "\n" + ex.stackTraceToString(),
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
                if (v != null && event != null) {
                    val location = IntArray(2)
                    v!!.getLocationOnScreen(location)
                    val viewX = location[0]
                    val viewY = location[1]
                    val x: Float = event!!.getRawX() - viewX
                    val y: Float = event!!.getRawY() - viewY

                    val p = Point(x.toInt(), y.toInt())
                }
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
                var filesFile = getExternalFilesDir("model.fac")
                var i: Int = 0
                //while (filesFile==null || filesFile!!.exists()) {
                //    filesFile = getExternalFilesDir("face-drawings-" + UUID.randomUUID() + ".fac")
                //    i++
                //}
                val photoURI: Uri = filesFile!!.toUri()
                val permissionsStorage = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.MANAGE_DOCUMENTS
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
                val permission4 = ActivityCompat.checkSelfPermission(
                    applicationContext, Manifest.permission.MANAGE_DOCUMENTS
                )
                if (permission1 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        thisActivity,
                        permissionsStorage,
                        requestExternalStorage
                    )
                }
                if (permission2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        thisActivity,
                        permissionsStorage,
                        requestExternalStorage
                    )
                }
                if (permission3 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        thisActivity,
                        permissionsStorage,
                        requestExternalStorage
                    )
                }

                if (permission4 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        thisActivity,
                        permissionsStorage,
                        requestExternalStorage
                    )
                }

                val intentSave = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "application/*.fac"
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/*.fac"))
                    putExtra(Intent.EXTRA_TITLE, filesFile.name)
                    putExtra("maxRes", maxRes)
                    if (currentFile.currentFile != null) {
                        putExtra("currentFile", currentFile.currentFile)
                    }
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
            var autoname: File? =
                getExternalFilesDir("face-drawings-" + UUID.randomUUID() + ".fac")
            while (autoname == null || autoname.exists())
                autoname = File("face-drawings-" + UUID.randomUUID() + ".fac")
            val autoname1: File = autoname

            val permissionsStorage = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.MANAGE_DOCUMENTS
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
            val permission4 = ActivityCompat.checkSelfPermission(
                applicationContext, Manifest.permission.MANAGE_DOCUMENTS
            )
            if (permission1 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    thisActivity,
                    permissionsStorage,
                    requestExternalStorage
                )
            }
            if (permission2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    thisActivity,
                    permissionsStorage,
                    requestExternalStorage
                )
            }
            if (permission3 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    thisActivity,
                    permissionsStorage,
                    requestExternalStorage
                )
            }
            if (permission4 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    thisActivity,
                    permissionsStorage,
                    requestExternalStorage
                )
            }

            /*
                            val photoURI = FileProvider.getUriForFile(
                                applicationContext,
                                applicationContext.packageName + ".provider",
                                autoname1
                            )
            */
            val intentLoad = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/*.fac"
                putExtra("currentFile", currentFile.currentFile)
                putExtra("maxRes", maxRes)
                putExtra(Intent.EXTRA_TITLE, "model.fac")
                //                   setDataAndType(photoURI, "application/*.fac")
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/*.fac"))
            }
            val intent2 = Intent.createChooser(intentLoad, "Choose a file")
            try {
                intent2.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intent2, OPEN_MODEL)
            } catch (ex: RuntimeException) {
                ex.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Error while loading model (open file dialog): " + ex.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun getRealPathFromURI2(contentURI: Uri): String? {
        val result: String?
        val cursor = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
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

    @Deprecated("Deprecated")
    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        super.onActivityResult(requestCode, resultCode, result)
        if ((resultCode == RESULT_OK) && result != null && ((result.extras != null &&
                    result.extras!!.get(Intent.EXTRA_STREAM) != null) ||
                    result.data != null)
        ) {
            var get: Uri? = null
            if (result.data != null) {
                get = result.data
            } else {
                try {
                    get = result.extras!!.get("data") as Uri
                } catch (ex: RuntimeException) {
                    try {
                        get = result.extras!!.get(Intent.EXTRA_STREAM) as Uri
                    } catch (ex1: RuntimeException) {
                        try {
                            get = result.data
                        } catch (ex2: RuntimeException) {
                        }
                    }
                }
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
            var file: File

            if (get != null) {
                try {
                    file = get.path?.let { File(it) }!!
                } catch (ex: RuntimeException) {
                    ex.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "FIle==null after filechooser " + ex.message,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    return
                } catch (ex1: NullPointerException) {
                    ex1.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "FIle==null after filechooser " + ex1.message,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    return
                }
                if (requestCode == CREATE_FILE) {
                    val openOutputStream = contentResolver.openOutputStream(get)
                    if (openOutputStream != null) {
                        try {
                            val instance: GoogleFaceDetection = faceOverlayView.googleFaceDetection
                            instance.encode(DataOutputStream(openOutputStream))
                        } catch (ex: RuntimeException) {
                            ex.printStackTrace()
                            ex.printStackTrace()
                            Toast.makeText(
                                applicationContext,
                                "Error while writing file .fac (instance encoding - runtime) " + ex.message,
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        } catch (ex1: java.io.NotSerializableException) {
                            ex1.printStackTrace()
                            Toast.makeText(
                                applicationContext,
                                "Error while writing file .fac (instance encoding - not serialisable)" + ex1.message,
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }
                    }
                } else if (requestCode == OPEN_MODEL) {
                    try {
                        val inputStream = getRealPathFromIntentData2(result)
                        //this.getFileContent(requestCode, resultCode, result) ?: return
                        val dataInputStream: DataInputStream = DataInputStream(inputStream)
                        val googleFaceDetection =
                            GoogleFaceDetection(currentBitmap).decode(dataInputStream) as GoogleFaceDetection?
                        if(googleFaceDetection!=null) {
                            GoogleFaceDetection.setInstance(googleFaceDetection)
                            faceOverlayView.googleFaceDetection = googleFaceDetection
                            if (googleFaceDetection!!.dataFaces.size > 0 && googleFaceDetection!!.dataFaces[0].faceSurfaces.size > 0)
                                faceOverlayView.googleFaceDetection.selectedSurface =
                                    googleFaceDetection!!.dataFaces[0].faceSurfaces[0]
                            GoogleFaceDetection.setInstance2(googleFaceDetection)
                        } else if (faceOverlayView.googleFaceDetection == null) {
                            Toast.makeText(
                                applicationContext,
                                "GoogleFaceDetection == null",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (ex: RuntimeException) {
                        ex.printStackTrace()
                        Toast.makeText(
                            applicationContext,
                            "Error while reading file .fac (1) (instance decoding - runtime) " + ex.message,
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (ex1: NullPointerException) {
                        ex1.printStackTrace()
                        Toast.makeText(
                            applicationContext,
                            "Error while reading file .fac: (2) (instance decoding - runtime) " + ex1.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
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


