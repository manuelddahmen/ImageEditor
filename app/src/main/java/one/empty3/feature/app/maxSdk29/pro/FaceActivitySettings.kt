package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.widget.Button

import androidx.annotation.RequiresApi
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.GoogleFaceDetection
import java.io.IOException

@ExperimentalCamera2Interop class FaceActivitySettings : ActivitySuperClass() {

    private lateinit var selectedPoint: Point
    private lateinit var faceOverlayView:FaceOverlayView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.face_draw_settings)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        if(intent.extras?.get("selectedPoint.x")!=null) {
            selectedPoint = Point(intent.extras?.get("selectedPoint.x") as Int, intent.extras?.get("selectedPoint.y") as Int)
        }
        val get = intent.extras?.get("googleFaceDetect")
        if(get!=null)
            faceOverlayView.googleFaceDetection = get as GoogleFaceDetection

        drawIfBitmap();


        if(currentFile!=null) {
            if(currentBitmap==null)
                currentBitmap = ImageIO.read(currentFile).getBitmap()

            Utils().loadImageInImageView(currentBitmap, faceOverlayView)

            faceOverlayView.setBitmap(currentBitmap);

            faceOverlayView.setActivity(this)
        }

        val back = findViewById<Button>(R.id.face_draw_settings_back)

        back.performClick()

        back.setOnClickListener {
            faceOverlayView.setFinish(true)

            faceOverlayView.setDrawing(false)

            val intentBack = Intent(applicationContext, FaceActivity::class.java)

            if(selectedPoint!=null) {
                intentBack.putExtra("point.x", selectedPoint.x)
                intentBack.putExtra("point.y", selectedPoint.y)
            }
            if(faceOverlayView.googleFaceDetection.selectedSurface!=null) {
                intentBack.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
            }


            passParameters(intentBack)
        }

        selectedPoint = Point()
        if(intent.extras?.get("selectedPoint.x") !=null) {
            selectedPoint.x = intent.extras?.get("selectedPoint.x") as Int
        }
        if(intent.extras?.get("selectedPoint.y") !=null) {
            selectedPoint.y = intent.extras?.get("selectedPoint.y") as Int
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

        if (g > 0 && requestCode==4232403) {
            run {
                val testSphere: Bitmap = TestZBufferAndroid().testSphere()


                val file = "testSphere.jpg"
                try {
                    val filesFile = getFilesFile(file)
                    println(filesFile)
                    ImageIO.write(testSphere, "jpg", filesFile)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }
    }



}