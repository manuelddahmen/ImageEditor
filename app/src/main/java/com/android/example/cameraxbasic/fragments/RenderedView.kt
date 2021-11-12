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

class RenderedView(contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("renderedView#create", "create RenderedView Activity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.processed_images)
        val findViewById = findViewById<Button>(R.id.buttonBack)
        findViewById.setOnClickListener(View.OnClickListener {
            run {
                val intent = Intent(Intent.ACTION_EDIT)
                println("Cick on Back to effects'list")
                /*intent.setDataAndType(
                    Uri.fromFile(),
                    "image/jpg")
                intent.setClass(view.context,/*EffectsFragment()
                                .createPackageContext("com.android.example.cameraxbasic.fragments",*/
                    Class.forName("com.android.example.cameraxbasic.fragments.EffectFragment"))
                intent.putExtra("data", mediaFile.absolutePath)
                */
                startActivity(intent)
            }
        })
    }
    fun init() {

    }
}