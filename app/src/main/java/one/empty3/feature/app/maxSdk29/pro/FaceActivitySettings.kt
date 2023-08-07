package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.GoogleFaceDetection
import one.empty3.feature20220726.GoogleFaceDetection.FaceData.Surface
import one.empty3.feature20220726.PixM
import one.empty3.library.Lumiere

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

    private lateinit var polygonView: ImageViewSelection
    private var selectedSurface: Int = 0
    private lateinit var selectedPoint: Point
    private lateinit var faceOverlayView: FaceOverlayView
    private var googleFaceDetection: GoogleFaceDetection? = GoogleFaceDetection.getInstance()
    private lateinit var selectedSurfaces:ArrayList<Surface>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.face_draw_settings)

        faceOverlayView = findViewById<FaceOverlayView>(R.id.face_overlay)
        polygonView = findViewById<ImageViewSelection>(R.id.polygon_details)

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
            if (faceOverlayView.googleFaceDetection != null) {
                intentBack.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
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

        faceOverlayView.setOnClickListener {

        }

        faceOverlayView.setOnTouchListener { v: View, event: MotionEvent ->
            if(event.actionMasked==MotionEvent.ACTION_UP) {

                var p0 = coordCanvas(PointF(0f, 0f))

                var p = PointF(event.x, event.y)

                var p1 = PointF((p.x - p0.x) * getScale().x, (p.y - p0.y) * getScale().x)

                val p2 = Point(p1.x.toInt(), p1.y.toInt())

                selectedPoint = p2

                selectShapeAt(p2)

                drawPolygon()

            }
            true
        }

        val colorChooser : Button= findViewById<Button>(R.id.choose_color)

        colorChooser.setOnClickListener {
            val dialog: ColorChooser = ColorChooser()

            //dialog.open
        }

    }

    fun coordCanvas(p: PointF): PointF {
        val mBitmap = faceOverlayView.mBitmap
        if (faceOverlayView.mBitmap == null) return p
        val viewWidth: Double = faceOverlayView.getWidth().toDouble()
        val viewHeight: Double = faceOverlayView.getHeight().toDouble()
        val imageWidth: Double = mBitmap.getWidth().toDouble()
        val imageHeight: Double = mBitmap.getHeight().toDouble()
        val scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight)
        return PointF(
            ((-(imageWidth / 2) * scale).toInt() + faceOverlayView.getWidth() / 2 + p.x * scale).toInt()
                .toFloat(),
            ((-(imageHeight / 2) * scale).toInt() + faceOverlayView.getHeight() / 2 + p.y * scale).toInt()
                .toFloat()
        )
    }


    fun getScale(): PointF {
        val mBitmap = faceOverlayView.mBitmap
        if (mBitmap == null) return PointF(0f, 0f)
        val viewWidth: Double = faceOverlayView.getWidth().toDouble()
        val viewHeight: Double = faceOverlayView.getHeight().toDouble()
        val imageWidth: Double = mBitmap.getWidth().toDouble()
        val imageHeight: Double = mBitmap.getHeight().toDouble()
        val scaleMin = Math.min(
            (viewWidth / imageWidth).toFloat(),
            (viewHeight / imageHeight).toFloat()
        ).toDouble()
        return PointF(scaleMin.toFloat(), scaleMin.toFloat())
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
                                    && p.y >= boundRect2d.getElem(0).y && p.y <= boundRect2d.getElem(1).y) {
                                    if (!surface.contours.getValues(p.x as Int, p.y as Int)
                                            .equals(doubles)) {
                                        // point in polygon
                                        selectedSurfaces.add(surface)
                                        //drawPolygon()
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
        if (selectedSurfaces.size > 1) {
            val size = selectedSurfaces.size
            selectedSurface = (selectedSurface + 1)
            if(selectedSurface>=size)
                selectedSurface = 0

        }


        if(selectedSurface>=selectedSurfaces.size) {
            selectedSurface = 0
        }


        println("Size : " + selectedSurfaces.size)
        println("Current face : $selectedSurface")
    }

    private fun drawPolygon() {
        //polygonView.invalidate()

        if(selectedSurfaces.size>selectedSurface) {

            val selectedSurfaceObject = selectedSurfaces[selectedSurface]
            polygonView.setImageBitmap3(selectedSurfaceObject.contours.bitmap)
            //polygonView.setPixels(selectedSurfaceObject.contours)
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
            }
        }
    }


}