package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.image.imageio.ImageIO
import java.io.File


class FaceActivity : ActivitySuperClass() {
    private var originalImage: File? = null
    private var selectedPoint : android.graphics.Point? = null
    private lateinit var faceOverlayView:FaceOverlayView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_face)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        drawIfBitmap();

        if(intent.extras?.getDouble("selectedPoint.x") !=null) {
            selectedPoint?.x = (intent.extras?.getDouble("selectedPoint.x") as Int)
        }
        if(intent.extras?.getDouble("selectedPoint.y") !=null) {
            selectedPoint?.y = (intent.extras?.getDouble("selectedPoint.y") as Int)
        }

            if (currentFile != null) {
            if (currentBitmap == null)
                currentBitmap = ImageIO.read(currentFile).getBitmap()

            Utils().loadImageInImageView(currentBitmap, faceOverlayView)

            faceOverlayView.setBitmap(currentBitmap);

            faceOverlayView.setActivity(this)
        }

        val faceDetection = findViewById<Button>(R.id.face_detection)

        faceDetection.setOnClickListener {
            if(faceOverlayView.isFinish()) {
                faceOverlayView.isDrawing = false
                faceOverlayView.isFinish = false
                faceOverlayView.setBitmap(ImageIO.read(currentFile).getBitmap());
                val writePhoto = Utils().writePhoto(this, faceOverlayView.mCopy, "face-contours")
                originalImage = currentFile
                currentFile = writePhoto
                //Utils().loadImageInImageView(faceOverlayView.mCopy, faceOverlayView)
            }
        }
        val faceDrawSettings = findViewById<Button>(R.id.face_draw_settings)
        faceDrawSettings.setOnClickListener {
            if(faceOverlayView.googleFaceDetection!=null) {
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
                if(originalImage!=null) {
                    intentSettings.putExtra("originalImage", originalImage)
                }
                passParameters(intentSettings)
            } else {
                Toast.makeText(applicationContext, "Attendez que la détection de visage soit terminée."
                , Toast.LENGTH_LONG).show()
            }
        }

        faceOverlayView.setOnClickListener{
            if (faceOverlayView.googleFaceDetection!=null) {
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

            if(selectedPoint!=null) {
                intentBack.putExtra("selectedPoint.x", selectedPoint!!.x)
                intentBack.putExtra("selectedPoint.y", selectedPoint!!.y)
            }
            if(faceOverlayView.googleFaceDetection!=null) {
                //intentBack.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
             }

            if(originalImage!=null) {
                intentBack.putExtra("originalImage", originalImage)
            }
            passParameters(intentBack)

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


