/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
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
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import javaAnd.awt.Point
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*


@ExperimentalCamera2Interop class TextActivity() : ActivitySuperClass() {
    private val INT_WRITE_STORAGE: Int = 9247492
    private var text: String = ""
    private lateinit var currentImage: Bitmap
    private lateinit var rect: RectF
    private var drawTextPointA: Point? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)

        System.err.println("CurrentFile="+currentFile);

        maxRes = Utils().getMaxRes(this, savedInstanceState)


        imageView = findViewById<ImageViewSelection>(R.id.currentImageView)

        val currentFile1 = Utils().getCurrentFile(intent)
        if (currentFile1 != null && imageView!=null && currentFile1.exists()) {
            currentFile = currentFile1
            currentImage = BitmapFactory.decodeStream(FileInputStream(currentFile))
            Utils().setImageView(imageView, this.currentImage)

        }
        val backButton = findViewById<Button>(R.id.buttonTextToMain)
        backButton.setOnClickListener {
            try {
                val textIntent = Intent(it.context, MyCameraActivity::class.java)
                val name: String = ("" + UUID.randomUUID())


                textIntent.setDataAndType(Uri.fromFile(currentFile), "image/jpg")
                passParameters(textIntent);
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        val textApply = findViewById<Button>(R.id.textApplyButton)
        textApply.setOnClickListener {
            applyText()
        }


        initImageView()
    }


    fun initImageView() {
        imageView = findViewById<ImageViewSelection>(R.id.currentImageView)
        imageView.setOnClickListener {
        }
        imageView.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (v != null && event != null) {
                    var location = IntArray(2)
                    v.getLocationOnScreen(location)
                    val viewX = location[0]
                    val viewY = location[1]
                    val outRect = Rect()
                    imageView.getDrawable().copyBounds(outRect)
                    //location = intArrayOf(0, 0)
                    val x =
                        (event.rawX - viewX - outRect.left) / imageView.width * currentImage.width
                    val y =
                        (event.rawY - viewY - outRect.top) / imageView.height * currentImage.height
                    drawTextPointA = Point(x.toInt(), y.toInt())
                    return true
                }
                return false
            }

        })

    }

    private fun applyText(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES
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
            val textString: String =
                (findViewById<TextView>(R.id.textViewOnImage).text.toString())

            currentImage = BitmapFactory.decodeStream(FileInputStream(currentFile))
            val drawTextToBitmap: File? = drawTextToBitmap(textString)
            if (drawTextToBitmap != null) {
                currentFile = drawTextToBitmap
                imageView = findViewById<ImageViewSelection>(R.id.currentImageView)
                Utils().setImageView(imageView, this.currentImage)
                System.out.println("ImageView Text UPDATED")
            }
            return true
            //}
        }
        return false
    }

    private fun drawTextToBitmap(textToPrint: String): File? {
        try {

            val resources: Resources = applicationContext.resources
            val scale: Float = resources.displayMetrics.density
            // resource bitmaps are immutable,
            // so we need to convert it to mutable one
            val currentImage2 = currentImage.copy(currentImage.config, true)
            val canvas = Canvas(currentImage2)
            // new antialised Paint
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            // text color - #3D3D3D
            paint.color = Color.rgb(110, 110, 110)


            val dpText: EditText = findViewById(R.id.font_size)

            val fontSize: Float = java.lang.Float.parseFloat(dpText.text.toString()) / 4
            // text size in pixels
            paint.textSize = (fontSize * scale).toInt().toFloat()
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY)

            // draw text to the Canvas center
            val bounds = Rect()
            paint.getTextBounds(textToPrint, 0, textToPrint.length, bounds)

            var x = 0
            var y = 0


            if (drawTextPointA != null) {
                x = drawTextPointA!!.x.toInt() - bounds.width() / 2
                y = drawTextPointA!!.y.toInt() + bounds.height() / 2

            } else {
                // Default : center
                x = (-bounds.width()) / 2
                y = (+bounds.height()) / 2
            }
            //canvas.drawText(textToPrint, x * scale, y * scale, paint)
            canvas.drawText(textToPrint, x.toFloat(), y.toFloat(), paint)
            currentImage = currentImage2
            return Utils().writePhoto(this, currentImage2, "drawtext-")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            return null
        } catch (ex1 : RuntimeException ) {
            ex1.printStackTrace()
        }
        return null
    }


    fun writeText() {
        var imageView: ImageViewSelection =
            findViewById<ImageViewSelection>(R.id.currentImageView)
        var text = findViewById<EditText>(R.id.textViewOnImage)?.text

        var bmpFile = imageView.drawable

        if (bmpFile != null) {
            currentImage = BitmapFactory.decodeStream(
                FileInputStream(currentFile)
            )


            Utils().setImageView(imageView, currentImage)

            initImageView()

            imageView.invalidate()

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