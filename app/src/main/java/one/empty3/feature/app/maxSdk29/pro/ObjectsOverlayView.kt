package one.empty3.feature.app.maxSdk29.pro

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.util.AttributeSet
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

class ObjectsOverlayView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ImageViewSelection(context, attrs, defStyleAttr) {

    private lateinit var detectedsBounds: java.util.ArrayList<RectF>
    private lateinit var detecteds: ArrayList<String>

    fun setBitmap(bitmap : Bitmap) : Boolean {
        // Multiple object detection in static images
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()  // Optional
            .build()

        val objectDetector = ObjectDetection.getClient(options)

        objectDetector.process(InputImage.fromBitmap(bitmap, 0))
            .addOnSuccessListener { detectedObjects ->
                var detecteds = arrayListOf<String>()
                var detectedsBounds = arrayListOf<RectF>()
                if (detectedObjects != null) {
                    for ((index, detectedObject) in detectedObjects.withIndex()) {
                        val rectf = RectF(detectedObject.boundingBox)
                        var isA: String = ""
                        detectedObject.getLabels().forEachIndexed { index1, label ->
                                isA += label.text + " "

                        }
                        detecteds[index] = isA
                        detectedsBounds[index] = rectf

                    }
                }

                this.detecteds = detecteds
                this.detectedsBounds = detectedsBounds
                return@addOnSuccessListener
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                return@addOnFailureListener
            }

        return true
    }

}