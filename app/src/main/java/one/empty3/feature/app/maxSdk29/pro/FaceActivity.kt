package one.empty3.feature.app.maxSdk29.pro

import android.os.Bundle
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.library.core.export.ObjExport.Face

class FaceActivity : ActivitySuperClass() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.acttivity_face)

        val faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)

        if(currentFile!=null)
            faceOverlayView.setBitmap(ImageIO.read(currentFile).bitmap);
    }
}