package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.net.toFile
import androidx.core.net.toUri
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.GoogleFaceDetection
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


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

            } catch (ex : RuntimeException) {
                Toast.makeText(applicationContext, "Error while execute face detection",
                    Toast.LENGTH_LONG).show()
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
                if (faceOverlayView.googleFaceDetection == null) {
                    faceOverlayView.performClick()
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
        saveModel.setOnClickListener {
            if (faceOverlayView != null && faceOverlayView.googleFaceDetection != null) {
                var filesFile = getFilesFile("model.model")
                var i : Int = 0
                while(filesFile.exists()) {
                    filesFile = getFilesFile("model$i.model")
                    i++
                }
                var pickerInitialUri: Uri? = null
                if(currentFile!=null)
                    pickerInitialUri = currentFile.parentFile.toUri()
                else
                    pickerInitialUri = Uri.fromFile(File("./"))

                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*.model"
                        putExtra(Intent.EXTRA_TITLE, "model.model")

                        // Optionally, specify a URI for the directory that should be opened in
                        // the system file picker before your app creates the document.
                        putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
                }
                startActivityForResult(intent, CREATE_FILE)


                val fileOutputStream = FileOutputStream(filesFile)
                val oos = ObjectOutputStream(fileOutputStream)
                oos.writeUnshared(faceOverlayView.googleFaceDetection)
            }
        }
        loadModel.setOnClickListener {
            if (faceOverlayView != null && faceOverlayView.googleFaceDetection != null) {
                var filesFile = getFilesFile("model")
                var i : Int = 0
                while(filesFile.exists()) {
                    filesFile = getFilesFile("model"+i)
                    i++
                }
                var pickerInitialUri: Uri? = null
                if(currentFile!=null)
                    pickerInitialUri = currentFile.parentFile.toUri()
                else
                    pickerInitialUri = Uri.fromFile(File("./"))

                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "*.model"

                        // Optionally, specify a URI for the file that should appear in the
                        // system file picker when it loads.
                        putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
                    }

                    startActivityForResult(intent, OPEN_MODEL)

                val fileInputStream = FileInputStream(filesFile)
                val oos = ObjectInputStream(fileInputStream)
                faceOverlayView.googleFaceDetection = oos.readUnshared() as GoogleFaceDetection?
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null && data.data!=null && resultCode== RESULT_OK) {
            if (requestCode == CREATE_FILE) {
                val fileOutputStream = FileOutputStream(data.data!!.toFile())
                val oos = ObjectOutputStream(fileOutputStream)
                oos.writeUnshared(faceOverlayView.googleFaceDetection)
            } else if (requestCode == OPEN_MODEL) {
                val fileInputStream = FileInputStream(data.data!!.toFile())
                val oos = ObjectInputStream(fileInputStream)
                faceOverlayView.googleFaceDetection = oos.readUnshared() as GoogleFaceDetection?
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


