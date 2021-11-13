package com.android.example.cameraxbasic.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.example.cameraxbasic.R
import java.io.File

class RenderedView(contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {
    private var mediaFile: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("renderedView#create", "create RenderedView Activity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.processed_images)
        mediaFile = intent.data
        var findViewById = findViewById<Button>(R.id.buttonBack)
        findViewById.setOnClickListener(View.OnClickListener {
            run {
                        println("Cick on Back to effects'list")
                        val intent = Intent(Intent.ACTION_EDIT)
                        intent.setDataAndType(mediaFile, "image/jpg")
                        intent.setClass(
                            findViewById.context,/*EffectsFragment()
                                .createPackageContext("com.android.example.cameraxbasic.fragments",*/
                            Class.forName("com.android.example.cameraxbasic.fragments.EffectFragment")
                        )
                        intent.putExtra(
                            "data", File(mediaFile.toString())
                        )
                        startActivity(intent)
                    }

        })
        findViewById = findViewById<Button>(R.id.buttonGallery)
        findViewById.setOnClickListener(View.OnClickListener {
            run {
                println("Cick on Back to gallery")
                val intent = Intent(Intent.ACTION_EDIT)
                intent.setDataAndType(mediaFile, "image/jpg")
                intent.setClass(
                    findViewById.context,/*EffectsFragment()
                                .createPackageContext("com.android.example.cameraxbasic.fragments",*/
                    Class.forName("com.android.example.cameraxbasic.fragments.GalleryFragment")
                )
                intent.putExtra(
                    "data", File(mediaFile.toString())
                )
                startActivity(intent)
            }

        })
    }
    fun init() {

    }
}