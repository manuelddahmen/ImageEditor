package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Button

import androidx.annotation.RequiresApi
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.image.imageio.ImageIO
import java.io.IOException

@ExperimentalCamera2Interop class FaceActivitySettings : ActivitySuperClass() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.face_draw_settings)

        val faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


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
            passParameters(intentBack)
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