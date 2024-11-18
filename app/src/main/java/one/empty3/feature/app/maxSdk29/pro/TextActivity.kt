/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import javaAnd.awt.Point
import one.empty3.libs.Image
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.UUID


class TextActivity() : ActivitySuperClass() {
    private val INT_WRITE_STORAGE: Int = 9247492
    private var text: String = ""
    private var currentImage: Bitmap? = null
    private lateinit var rect: RectF
    private var drawTextPointA: Point? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)


        val prefs = PreferenceManager
            .getDefaultSharedPreferences(this)

        System.err.println("CurrentFile="+currentFile);

        maxRes = Utils().getMaxRes(this)


        imageView = findViewById<ImageViewSelection>(R.id.currentImageView)
/*
        val currentFile1 = Utils().getCurrentFile(intent, this)
        if (currentFile1 != null && currentFile1.exists()) {
            currentFile = currentFile1
            currentImage = BitmapFactory.decodeStream(FileInputStream(currentFile))
            if(currentImage!=null) {
                Utils().setImageView(imageView, currentImage!!)
            }
        } else {
            currentImage = BitmapFactory.decodeStream(FileInputStream(currentFile))
        }*/
        if(currentFile!=null&&currentFile.currentFile.exists()) {
            currentImage = BitmapFactory.decodeStream(FileInputStream(currentFile.currentFile))
            if(currentImage!=null) {
                Utils().setImageView(imageView, currentImage!!)
            }
        }
        val backButton = findViewById<Button>(R.id.buttonTextToMain)
        backButton.setOnClickListener {
            try {
                val textIntent = Intent(applicationContext, MyCameraActivity::class.java)
                val name: String = ("" + UUID.randomUUID())
//android:enableOnBackInvokedCallback="true"

                textIntent.setDataAndType(Uri.fromFile(currentFile.currentFile), "image/jpg")
                passParameters(textIntent);
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        val textApply = findViewById<Button>(R.id.textApplyButton)
        textApply.setOnClickListener {
            applyText()
        }

        val editText: EditText =
            findViewById<EditText>(R.id.textViewOnImage)

        var string: String? = prefs.getString("autoSaveEditTextTextApply", "")
        if(string!=null)
            editText.setText(string)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var toString = editText.text?.toString()
                if(toString==null)
                    toString = ""
                prefs.edit().putString("autoSaveEditTextTextApply", toString).apply();
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
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
                    if(currentImage!=null) {
                        val x =
                            (event.rawX - viewX - outRect.left) / imageView.width * currentImage!!.width
                        val y =
                            (event.rawY - viewY - outRect.top) / imageView.height * currentImage!!.height
                        drawTextPointA = Point(x.toInt(), y.toInt())
                        return true
                    }
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
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED &&
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
        else {
            applyTextNext()
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == INT_WRITE_STORAGE && grantResults != null) {
            var g = 0
            for (granted in grantResults) {
                g = g + if (granted == PackageManager.PERMISSION_GRANTED) 1 else 0
            }

            if (g > 0) applyTextNext()
        }
    }

    private fun applyTextNext(): Boolean {

        val textString: String =
            (findViewById<EditText>(R.id.textViewOnImage).text.toString())

        currentImage = BitmapFactory.decodeStream(FileInputStream(currentFile.currentFile))
        val drawTextToBitmap: File? = drawTextToBitmap(textString)
        if (drawTextToBitmap != null && currentImage!=null) {
            currentFile.add(DataApp(drawTextToBitmap))
            imageView = findViewById<ImageViewSelection>(R.id.currentImageView)
            Utils().setImageView(imageView, currentImage!!)
            System.out.println("ImageView Text UPDATED")
        }
        return true
        //}

    }

    private fun drawTextToBitmap(textToPrint: String): File? {
        try {

            val resources: Resources = applicationContext.resources
            val scale: Float = resources.displayMetrics.density
            // resource bitmaps are immutable,
            // so we need to convert it to mutable one
            if(currentImage!=null) {
                val currentImage2 = currentImage!!.copy(currentImage!!.config!!, true)
                val canvas = Canvas(currentImage2)
                // new antialised Paint
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                // text color - #3D3D3D
                paint.color = Color.rgb(110, 110, 110)


                val dpText: EditText = findViewById(R.id.font_size)

                var fontSize : Float = 24f
                try {
                    fontSize = java.lang.Float.parseFloat(dpText.text.toString()) / 4
                } catch (_:RuntimeException) {
                    return null
                }

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
                return Utils().writePhoto(this, Image(currentImage2), "drawtext-")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            return null
        } catch (ex1 : RuntimeException ) {
            ex1.printStackTrace()
        }
        return null
    }


}