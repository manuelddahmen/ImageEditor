package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.image.BufferedImage
import javaAnd.awt.image.imageio.ImageIO
import java.io.File
import java.io.IOException

@ExperimentalCamera2Interop class FaceActivity : ActivitySuperClass() {
    private fun cameraCaptureRecord(b: Button) {
        //Camera().process(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_face)

        val faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        drawIfBitmap();


        if(currentFile!=null) {
            if(currentBitmap==null)
                currentBitmap = ImageIO.read(currentFile).getBitmap()

            Utils().loadImageInImageView(currentBitmap, faceOverlayView)

            faceOverlayView.setBitmap(currentBitmap);
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