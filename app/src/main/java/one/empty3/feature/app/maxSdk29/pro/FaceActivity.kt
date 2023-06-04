package one.empty3.feature.app.maxSdk29.pro

import android.os.Bundle
import android.widget.Button
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.image.imageio.ImageIO

@ExperimentalCamera2Interop class FaceActivity : ActivitySuperClass() {
    private fun cameraCaptureRecord(b: Button) {
        Camera().process(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_face)

        val faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        drawIfBitmap();

        if(currentFile!=null) {
            if(currentBitmap==null)
                currentBitmap = ImageIO.read(currentFile).bitmap
            faceOverlayView.setBitmap(currentBitmap);
        }

        val b : Button =  findViewById<Button>(R.id.camera_preview_video)
        b.setOnClickListener({
            cameraCaptureRecord(b)
        })


    }

}