package one.empty3.feature.app.maxSdk29.pro

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.key
import androidx.core.util.forEach
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector
import com.google.mlkit.vision.common.InputImage
import javaAnd.awt.image.imageio.ImageIO

@ExperimentalCamera2Interop class FaceActivity : ActivitySuperClass() {
    private lateinit var detector: FaceDetector

    private fun cameraCaptureRecord(b: Button) {
        Camera().process(this)
    }

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

        val b : Button =  findViewById<Button>(R.id.camera_preview_video)
        b.setOnClickListener({
            cameraCaptureRecord(b)
        })
    }

    fun processNextFrame(image : Bitmap) {
        val build = Frame.Builder().setBitmap(image).build()
        val result = detector.detect(build).forEach { key, value -> {

        } }


    }
}