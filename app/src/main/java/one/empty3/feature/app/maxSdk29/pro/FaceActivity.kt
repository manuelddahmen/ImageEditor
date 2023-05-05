package one.empty3.feature.app.maxSdk29.pro

import android.graphics.Bitmap
import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.key
import androidx.core.util.forEach
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector
import com.google.mlkit.vision.common.InputImage
import javaAnd.awt.image.imageio.ImageIO

class FaceActivity : ActivitySuperClass() {
    private lateinit var detector: FaceDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.acttivity_face)

        val faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)

        if(currentFile!=null)
            faceOverlayView.setBitmap(ImageIO.read(currentFile).bitmap);

        drawIfBitmap();


        detector = FaceDetector.Builder(applicationContext)
            .setTrackingEnabled(false)
            .setLandmarkType(FaceDetector.ALL_LANDMARKS)
            .setMode(FaceDetector.FAST_MODE)
            .build()
        if (!detector.isOperational) {
            //Handle contingency
        }
    }

    fun processNextFrame(image : Bitmap) {
        val build = Frame.Builder().setBitmap(image).build()
        val result = detector.detect(build).forEach { key, value -> {

        } }


    }
}