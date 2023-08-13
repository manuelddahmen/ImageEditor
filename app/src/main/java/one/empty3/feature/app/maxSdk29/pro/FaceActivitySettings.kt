package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.feature20220726.GoogleFaceDetection
import one.empty3.feature20220726.GoogleFaceDetection.FaceData.Surface
import one.empty3.library.ColorTexture
import one.empty3.library.Lumiere
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File

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
    private val OPTION_SELECT_WHERE_CLICKED = 1
    private val OPTION_SELECT_ALL = 2
    private val SELECTED_OPTION_COLOR = 1
    private val SELECTED_OPTION_BITMAP = 2
    private var option: Int = OPTION_SELECT_ALL
    private var currentSurfaceSize: Int = 0
    var selectedSurfaceAllPicture: Surface? = null
    private lateinit var originalImage: File
    private lateinit var polygonView: ImageViewSelection
    private var selectedSurface: Int = 0
    private lateinit var selectedPoint: Point
    private lateinit var faceOverlayView: FaceOverlayView
    private var googleFaceDetection: GoogleFaceDetection? = GoogleFaceDetection.getInstance()
    private lateinit var selectedSurfaces: ArrayList<Surface>
    private var currentSurface = 0
    private var selectedColor = Color.White
    private lateinit var selectedImage: Bitmap
    private var selectedOption = SELECTED_OPTION_COLOR

    class ColorDialogListener(var selectedColor2: Color, var activity: FaceActivitySettings) :
        AmbilWarnaDialog.OnAmbilWarnaListener {
        override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
            activity.selectedColor = Color(color)
            val findViewById = activity.findViewById<Button>(R.id.choose_color)
            findViewById.setBackgroundColor(color)
            // color is the color selected by the user.

        }

        fun setFaceActivitySettings(faceActivitySettings: FaceActivitySettings) {
            this.activity = faceActivitySettings
        }

        override fun onCancel(dialog: AmbilWarnaDialog) {
            // cancel was selected by the user
        }
    }


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


        drawIfBitmap()

        if (intent.hasExtra("originalImage") != null) {
            originalImage = intent.extras!!.get("originalImage") as File
        }

        if (currentFile != null) {
            if (currentBitmap == null)
                currentBitmap = ImageIO.read(currentFile).getBitmap()

            var originalBitmap: Bitmap = ImageIO.read(originalImage).getBitmap()

            Utils().loadImageInImageView(currentBitmap, faceOverlayView)

            faceOverlayView.setBitmap(currentBitmap)

            faceOverlayView.setActivity(this)
        }

        val back = findViewById<Button>(R.id.face_draw_settings_back)

        back.performClick()

        back.setOnClickListener {
            faceOverlayView.isFinish = true

            faceOverlayView.isDrawing = false

            val intentBack = Intent(applicationContext, FaceActivity::class.java)

            if (selectedPoint != null) {
                intentBack.putExtra("point.x", selectedPoint.x)
                intentBack.putExtra("point.y", selectedPoint.y)
            }
            if (faceOverlayView.googleFaceDetection != null) {
                intentBack.putExtra("googleFaceDetect", faceOverlayView.googleFaceDetection)
            }

            originalImage = currentFile

            if (originalImage != null) {
                intentBack.putExtra("originalImage", originalImage)
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
            if (event.actionMasked == MotionEvent.ACTION_UP && option == OPTION_SELECT_WHERE_CLICKED) {

                var p0 = coordCanvas(PointF(0f, 0f))

                var p = PointF(event.x, event.y)

                var p1 = PointF((p.x - p0.x) * getScale().x, (p.y - p0.y) * getScale().y)

                val p2 = Point(p1.x.toInt(), p1.y.toInt())

                selectedPoint = p2

                selectShapeAt(p2)

                drawPolygon()

            } else if (event.actionMasked == MotionEvent.ACTION_UP && option == OPTION_SELECT_ALL) {
                currentSurface++
                if (currentSurface >= currentSurfaceSize)
                    currentSurface = 0

                selectShapeAt(null)


                drawSurface()
            }



            true
        }

        val colorChooser: Button = findViewById<Button>(R.id.choose_color)

        val colorChooserDialog: ColorDialogListener = ColorDialogListener(selectedColor, this)

        colorChooser.setOnClickListener {

            val dialog: AmbilWarnaDialog = AmbilWarnaDialog(/* context = */ this, /* color = */
                selectedColor.toArgb(),
                /* listener = */
                colorChooserDialog
            )
            dialog.show()
        }

        val fileChooser = findViewById<Button>(R.id.choose_image)

        fileChooser.setOnClickListener({

        })

        val applyColor = findViewById<Button>(R.id.applyColor)
        applyColor.setOnClickListener {

            var sel = selectedSurfaceAllPicture

            if (sel == null && selectedSurface < selectedSurfaces.size) sel =
                selectedSurfaces[selectedSurface]

            if (selectedColor != null && sel != null) {
                val oldColorFill = sel.colorFill
                val newColorFill = selectedColor
                sel.filledContours.replaceColor(oldColorFill, newColorFill.toArgb(), 0.1)
                sel.contours.replaceColor(oldColorFill, newColorFill.toArgb(), 0.1)
                sel.polygon.texture(ColorTexture(newColorFill.toArgb()))
                sel.colorFill = newColorFill.toArgb()
                drawSurface()
                drawSurfaces()
            }
        }
    }

    fun coordCanvas(p: PointF): PointF {
        val mBitmap = faceOverlayView.mBitmap
        if (faceOverlayView.mBitmap == null) return p
        val viewWidth: Double = faceOverlayView.width.toDouble()
        val viewHeight: Double = faceOverlayView.height.toDouble()
        val imageWidth: Double = mBitmap.width.toDouble()
        val imageHeight: Double = mBitmap.height.toDouble()
        val scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight)
        if (viewHeight / imageHeight < viewWidth / imageWidth) {
            return PointF(
                ((-(imageWidth / 2) * scale).toInt() + viewWidth / 2 + p.x * scale).toInt()
                    .toFloat(),
                ((-(imageHeight / 2) * scale).toInt() + viewHeight / 2 + p.y * scale).toInt()
                    .toFloat()
            )
        } else {
            return PointF(
                ((-(imageWidth / 2) * scale).toInt() + viewWidth / 2 + p.x * scale).toInt()
                    .toFloat(),
                ((-(imageHeight / 2) * scale).toInt() + viewHeight / 2 + p.y * scale).toInt()
                    .toFloat()
            )

        }
    }


    fun getScale(): PointF {
        val mBitmap = faceOverlayView.mBitmap
        if (mBitmap == null) return PointF(0f, 0f)
        val viewWidth: Double = faceOverlayView.width.toDouble()
        val viewHeight: Double = faceOverlayView.height.toDouble()
        val imageWidth: Double = mBitmap.width.toDouble()
        val imageHeight: Double = mBitmap.height.toDouble()
        val scaleMin = Math.min(
            (viewWidth / imageWidth).toFloat(),
            (viewHeight / imageHeight).toFloat()
        ).toDouble()
        return PointF(scaleMin.toFloat(), scaleMin.toFloat())
    }

    private fun equalsArrays(
        arr1: DoubleArray?,
        arr2: DoubleArray?,
        inter: Double
    ): Boolean {
        if (arr1 != null && arr2 != null && arr1.size == 3 && arr2.size == 3) {
            for (i1 in 0..2) {
                if (arr1[i1] > arr2[i1] + inter || arr1[i1] < arr2[i1] - inter)
                    return false
            }
            return true
        }
        return false
    }

    private fun selectShapeAt(p: Point?) {
        var i: Int = 0
        selectedSurfaces = ArrayList()
        if (googleFaceDetection != null) {
            googleFaceDetection?.dataFaces?.forEach { faceData ->
                run {
                    faceData.faceSurfaces?.forEach(action = { surface ->
                        run {
                            val polygon = surface.polygon
                            if (polygon != null) {
                                val doubles = Lumiere.getDoubles(surface.colorFill)
                                val boundRect2d = polygon.boundRect2d
                                val pPolygonPoint0 = Point(
                                    boundRect2d.getElem(0).x.toInt(),
                                    boundRect2d.getElem(0).y.toInt()
                                )
                                var pBounds = pPolygonPoint0//Point(0,0)

                                if (option == OPTION_SELECT_ALL || p == null) {
                                    if (i == currentSurface)
                                        selectedSurfaceAllPicture = surface

                                    drawSurface()
                                    selectedSurfaces.add(surface)
                                    i++
                                } else if (p.x >= boundRect2d.getElem(0).x && p.x <= boundRect2d.getElem(
                                        1
                                    ).x
                                    && p.y >= boundRect2d.getElem(0).y && p.y <= boundRect2d.getElem(
                                        1
                                    ).y
                                ) {
                                    if (equalsArrays(
                                            surface.filledContours.getValues(
                                                p.x - pBounds.x,
                                                p.y - pBounds.y
                                            ),
                                            doubles, 1.0 / 256
                                        )
                                    ) {
                                        // point in polygon
                                        selectedSurfaces.add(surface)
                                        //surface.filledContours.setValues(p.x-pBounds.x as Int, p.y-pBounds.y, 1.0, 1.0, 1.0)
                                        //drawPolygon()
                                    }
                                }
                            }
                        }
                    })
                }
            }
        } else {
            Toast.makeText(
                applicationContext, "Google face detection : data == null",
                Toast.LENGTH_LONG
            ).show()
        }
        currentSurfaceSize = i

        println("Size : " + selectedSurfaces.size)
        println("Current face : $selectedSurface")
    }

    private fun drawPolygon() {
        if (selectedSurfaces.size > selectedSurface) {
            val selectedSurfaceObject = selectedSurfaces[selectedSurface]
            polygonView.setImageBitmap3(
                selectedSurfaceObject
                    .filledContours.bitmap.copy(
                        Bitmap.Config.ARGB_8888, true
                    )
            )
        }
    }

    private fun drawSurface() {
        if (selectedSurfaceAllPicture != null) {
            polygonView.setImageBitmap3(
                selectedSurfaceAllPicture!!
                    .filledContours.bitmap.copy(
                        Bitmap.Config.ARGB_8888, true
                    )
            )
        }
    }

    fun drawSurfaces() {
        faceOverlayView.fillPolygons(googleFaceDetection)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var g = 0
        for (granted in grantResults) {
            g += if (granted == PackageManager.PERMISSION_GRANTED) 1 else 0
        }

        if (g > 0 && requestCode == 4232403) {
            run {
            }
        }
    }


}