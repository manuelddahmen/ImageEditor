package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.GoogleFaceDetection
import one.empty3.library.ColorTexture
import one.empty3.library.Point3D
import one.empty3.library.Polygon
import java.io.File
import java.util.function.Consumer

@ExperimentalCamera2Interop
class FaceActivity : ActivitySuperClass() {
    private lateinit var originalImage: File
    private var selectedPoint : android.graphics.Point? = null
    private lateinit var faceOverlayView:FaceOverlayView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_face)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        drawIfBitmap();

        if(intent.extras?.get("selectedPoint.x") !=null) {
            selectedPoint?.x = (intent.extras?.get("selectedPoint.x") as Int)
        }
        if(intent.extras?.get("selectedPoint.y") !=null) {
            selectedPoint?.y = (intent.extras?.get("selectedPoint.y") as Int)
        }

        if(intent.extras?.get("googleFaceDetect")!=null) {
            faceOverlayView.googleFaceDetection = intent.extras!!.get("googleFaceDetect") as GoogleFaceDetection?
        }


            if (currentFile != null) {
            if (currentBitmap == null)
                currentBitmap = ImageIO.read(currentFile).getBitmap()

            Utils().loadImageInImageView(currentBitmap, faceOverlayView)

            faceOverlayView.setBitmap(currentBitmap);

            faceOverlayView.setActivity(this)
        }

        val camera_preview_video_face = findViewById<Button>(R.id.camera_preview_video_face)

        camera_preview_video_face.setOnClickListener {
            if(faceOverlayView.isFinish()) {
                faceOverlayView.isDrawing = false
                faceOverlayView.isFinish = false
                faceOverlayView.setBitmap(ImageIO.read(currentFile).getBitmap());
                val writePhoto = Utils().writePhoto(this, faceOverlayView.mCopy, "face-contours")
                originalImage = currentFile
                currentFile = writePhoto
                Utils().loadImageInImageView(faceOverlayView.mCopy, faceOverlayView)
            }
        }
        val face_draw_settings = findViewById<Button>(R.id.face_draw_settings)
        face_draw_settings.setOnClickListener {
            faceOverlayView.isFinish = true
            val intentSettings = Intent(applicationContext, FaceActivitySettings::class.java)
            if(selectedPoint!=null) {
                intentSettings.putExtra("selectedPoint.x", selectedPoint!!.x)
                intentSettings.putExtra("selectedPoint.y", selectedPoint!!.y)
            }
            if(faceOverlayView.googleFaceDetection.selectedSurface!=null) {
                intentSettings.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
            }
            passParameters(intentSettings)
        }

        faceOverlayView.setOnClickListener{
            val surface = faceOverlayView.googleFaceDetection.getSurface(selectedPoint);
            if(surface!=null) {
                faceOverlayView.googleFaceDetection.selectedSurface = surface
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



                if (checkPointCordinates(p)) {

                }
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
            if(faceOverlayView.googleFaceDetection.selectedSurface!=null) {
                intentBack.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
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

