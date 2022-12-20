package one.empty3.feature.app.maxSdk29.pro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.net.toFile
import com.google.android.material.slider.Slider
import javaAnd.awt.Point
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*


class TextActivity() : Activity(), Parcelable {
    private var currentFile: File? = null
    private var text: String = ""
    private lateinit var currentImage: Bitmap
    private lateinit var rect: RectF
    private var drawTextPointA: Point? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)

        val imageView = findViewById<ImageViewSelection>(R.id.imageViewOnImage)
        if (intent != null) run {
            val currentImageUri: Uri = intent.data!!
            currentImage = BitmapFactory.decodeFile(currentImageUri.toFile().toString())
            currentFile = currentImageUri.toFile()

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

                textIntent.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
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
            val textEdit = findViewById<TextView>(R.id.textViewOnImage)
            text = (textEdit as TextView).text.toString()
            val bmpFile = drawTextToBitmap(this, R.id.imageViewOnImage, text)
            if (bmpFile != null) {
                currentFile = bmpFile
                currentImage = BitmapFactory.decodeStream(
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

        imageView.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (v != null && event != null) {
                    var location = IntArray(2)
                    v?.getLocationOnScreen(location)
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


    private fun drawTextToBitmap(mContext: Context, resourceId: Int, mText: String): File? {
        try {
            val resources: Resources = mContext.resources
            val scale: Float = resources.displayMetrics.density

            val file = Utils().writePhoto(this, currentImage, "text")
            // resource bitmaps are imutable,
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
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY)

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

            }
            canvas.drawText(mText, x * scale, y * scale, paint)

            return Utils().writePhoto(this, currentImage2, "draw-text")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            return null
        }
    }

}