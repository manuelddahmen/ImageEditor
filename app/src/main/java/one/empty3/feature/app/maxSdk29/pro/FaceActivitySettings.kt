package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.compose.material3.surfaceColorAtElevation
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.GoogleFaceDetection
import one.empty3.feature20220726.GoogleFaceDetection.FaceData.Surface
import one.empty3.library.Lumiere
import one.empty3.library.Polygon
import java.util.function.Consumer

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

@ExperimentalCamera2Interop
class FaceActivitySettings : ActivitySuperClass() {

    private var selectedSurface: Int = 0
    private lateinit var selectedPoint: Point
    private lateinit var faceOverlayView: FaceOverlayView
    private var googleFaceDetection: GoogleFaceDetection? = GoogleFaceDetection.getInstance()
    private lateinit var selectedSurfaces:ArrayList<Surface>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.face_draw_settings)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)


        if (intent.extras?.getInt("selectedPoint.x") != null) {
            selectedPoint = Point(
                intent.extras?.getInt("selectedPoint.x") as Int,
                intent.extras?.getInt("selectedPoint.y") as Int
            )
        }
        //val get = intent.parcelable<GoogleFaceDetection>("googleFaceDetect")
        //if (get != null)
        //    googleFaceDetection = get as GoogleFaceDetection

        googleFaceDetection = GoogleFaceDetection.getInstance()


        drawIfBitmap();


        if (currentFile != null) {
            if (currentBitmap == null)
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

            if (selectedPoint != null) {
                intentBack.putExtra("point.x", selectedPoint.x)
                intentBack.putExtra("point.y", selectedPoint.y)
            }
            if (faceOverlayView.googleFaceDetection!=null) {
                //intentBack.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
            }


            passParameters(intentBack)
        }

        selectedPoint = Point()
        if (intent.extras?.get("selectedPoint.x") != null) {
            selectedPoint.x = intent.extras?.get("selectedPoint.x") as Int
        }
        if (intent.extras?.get("selectedPoint.y") != null) {
            selectedPoint.y = intent.extras?.get("selectedPoint.y") as Int
        }

        faceOverlayView.setOnClickListener({

        })

        faceOverlayView.setOnTouchListener { v: View, event: MotionEvent ->
            val location = IntArray(2)
            v.getLocationOnScreen(location)
            val viewX = location[0]
            val viewY = location[1]
            val x: Float = event.getRawX() - viewX
            val y: Float = event.getRawY() - viewY

            val p = Point(x.toInt(), y.toInt())

            if (checkPointCordinates(p))
                selectedPoint = p

            selectShapeAt(p)

            true
        }

        var polygonView = findViewById<FaceOverlayView>(R.id.polygon_details)
        polygonView.setOnClickListener(View.OnClickListener {
            if(selectedSurfaces!=null && selectedSurfaces.size>1) {
                val size = selectedSurfaces.size - 1
                selectedSurface = (selectedSurface+1)%size
            }
        })
    }

    private fun selectShapeAt(p: Point) {
         selectedSurfaces = ArrayList()
        if(googleFaceDetection!=null) {
            googleFaceDetection?.dataFaces?.forEach { faceData ->
                run {
                    faceData.faceSurfaces?.forEach(action = { surface ->
                        run {
                            val polygon = surface.polygon
                            if (polygon != null) {
                                val doubles = Lumiere.getDoubles(surface.colorFill)
                                val boundRect2d = polygon.boundRect2d
                                if (p.x >= boundRect2d.getElem(0).x && p.x <= boundRect2d.getElem(1).x
                                    && p.y >= boundRect2d.getElem(0).y && p.y <= boundRect2d.getElem(
                                        1
                                    ).y
                                ) {
                                    if (!surface.contours.getValues(p.x as Int, p.y as Int)
                                            .equals(doubles)
                                    ) {
                                        // point in polygon
                                        selectedSurfaces.add(surface)
                                        selectedSurface = 0
                                        drawPolygon()
                                    }
                                }
                            }
                        }
                    })
                }
            }
        } else {
            Toast.makeText(applicationContext, "Google face detection : data == null",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun drawPolygon() {
        val polygonView = findViewById<FaceOverlayView>(R.id.polygon_details)

        val selectedSurfaceObject = selectedSurfaces[selectedSurface]

        polygonView.setImageBitmap3(selectedSurfaceObject.contours.bitmap)
    }

    private fun checkPointCordinates(p: Point): Boolean {
        return true
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
            }
        }
    }


}