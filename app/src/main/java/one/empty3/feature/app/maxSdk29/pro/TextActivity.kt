package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import javaAnd.awt.Point
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*


class TextActivity() : Activity(), Parcelable {
    private val INT_WRITE_STORAGE: Int = 9247492
    private lateinit var currentFile: File
    private var text: String = ""
    private lateinit var currentImage: Bitmap
    private lateinit var rect: RectF
    private var drawTextPointA: Point? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)

        val imageView = findViewById<ImageViewSelection>(R.id.imageViewOnImage)
        if (intent != null && intent.data != null) run {
            val currentImageUri: Uri = intent.data!!
            this.currentImage = BitmapFactory.decodeFile(currentImageUri.toFile().toString())
            this.currentFile = currentImageUri.toFile()

            imageView.setImageBitmap(currentImage)
        }

        imageView.setOnClickListener {
        }

        val backButton = findViewById<Button>(R.id.buttonTextBack)

        backButton.setOnClickListener {
            try {
                val textIntent = Intent(Intent.ACTION_VIEW)
                val name: String = ("" + UUID.randomUUID())

                //val file = Utils().writePhoto(this, currentImage, name)

                textIntent.setDataAndType(Uri.fromFile(this.currentFile), "image/jpg")
                textIntent.setClass(
                    applicationContext,
                    Class.forName("one.empty3.feature.app.maxSdk29.pro.MyCameraActivity")
                )
                startActivity(textIntent)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        val textApply = findViewById<Button>(R.id.textApplyButton)
        textApply.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), INT_WRITE_STORAGE
                )
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {


                val textEdit = findViewById<TextView>(R.id.textViewOnImage)
                this.text = (textEdit as TextView).text.toString()
                val bmpFile = drawTextToBitmap(applicationContext, R.id.imageViewOnImage, text)
                if (bmpFile != null && bmpFile.exists()) {
                    this.currentFile = bmpFile
                    this.currentImage = BitmapFactory.decodeStream(
                        FileInputStream(currentFile)
                    )
                    imageView.setImageBitmap(currentImage)

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Error while writing text: method returned null",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }
        }

        imageView.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (v != null && event != null) {
                    var location = IntArray(2)
                    v.getLocationOnScreen(location)
                    val viewX = location[0]
                    val viewY = location[1]
                    location = intArrayOf(0, 0)
                    val x: Float = event.getRawX() - viewX
                    val y: Float = event.getRawY() - viewY

                    drawTextPointA = Point(x.toInt(), y.toInt())

                    return true
                }
                return false
            }
        })
    }


    constructor(parcel: Parcel) : this() {
        text = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return text.length
    }

    companion object CREATOR : Parcelable.Creator<TextActivity> {
        override fun createFromParcel(parcel: Parcel): TextActivity {
            return TextActivity(parcel)
        }

        override fun newArray(size: Int): Array<TextActivity?> {
            return arrayOfNulls(size)
        }
    }


    private fun drawTextToBitmap(context: Context, resourceId: Int, mText: String): File? {
        try {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), INT_WRITE_STORAGE
                )
            }

            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val resources: Resources = context.resources
                var scale: Float = resources.displayMetrics.density

                val file = Utils().writePhoto(this, currentImage, "text")
                // resource bitmaps are immutable,
                // so we need to convert it to mutable one
                val currentImage2 = currentImage.copy(currentImage.config, true)
                val canvas = Canvas(currentImage2)
                // new antialised Paint
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                // text color - #3D3D3D
                paint.setColor(Color.rgb(110, 110, 110))


                val dpText: EditText = findViewById(R.id.font_size)

                val fontSize: Float = java.lang.Float.parseFloat(dpText.text.toString()) / 4
                // text size in pixels
                paint.textSize = (fontSize * scale).toInt().toFloat()
                // text shadow
                //paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY)

                // draw text to the Canvas center
                val bounds = Rect()
                paint.getTextBounds(mText, 0, mText.length, bounds)

                var x = 0
                var y = 0

                x = (currentImage2.width - bounds.width()) / 6
                y = (currentImage2.height + bounds.height()) / 5

                if (drawTextPointA != null) {
                    x = drawTextPointA!!.x.toInt()
                    y = drawTextPointA!!.y.toInt()
                } else {
                    scale = 1f
                }
                canvas.drawText(mText, x * scale, y * scale, paint)

                return Utils().writePhoto(this, currentImage2, "drawtext-")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            return null
        }
        return null
    }

}