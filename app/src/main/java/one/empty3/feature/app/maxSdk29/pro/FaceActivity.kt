package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.Point
import javaAnd.awt.image.imageio.ImageIO

@ExperimentalCamera2Interop
class FaceActivity : ActivitySuperClass() {
    private lateinit var faceOverlayView:FaceOverlayView
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_face)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        drawIfBitmap();


        if (currentFile != null) {
            if (currentBitmap == null)
                currentBitmap = ImageIO.read(currentFile).getBitmap()

            Utils().loadImageInImageView(currentBitmap, faceOverlayView)

            faceOverlayView.setBitmap(currentBitmap);

            faceOverlayView.setActivity(this)
        }

        val camera_preview_video_face = findViewById<Button>(R.id.camera_preview_video_face)

        camera_preview_video_face.performClick()

        camera_preview_video_face.setOnClickListener {
            faceOverlayView.setFinish(true)

            faceOverlayView.setDrawing(false)


        }
        val face_draw_settings = findViewById<Button>(R.id.face_draw_settings)
        face_draw_settings.setOnClickListener {
            val intentSettings = Intent(applicationContext, FaceActivitySettings::class.java)
            passParameters(intentSettings)
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
            val intentBack = Intent(applicationContext, MyCameraActivity::class.java)
            passParameters(intentBack)

        }
    }

    fun checkPointCordinates(a: Point): Boolean {
        val x = a.getX().toInt()
        val y = a.getY().toInt()
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

