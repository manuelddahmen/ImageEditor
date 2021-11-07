package com.android.example.cameraxbasic.fragments

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.example.cameraxbasic.R
import one.empty3.Main
import one.empty3.io.ProcessFile
import java.io.File

class EffectsFragment : AppCompatActivity() {
    lateinit var effectList: ArrayList<ProcessFile>
    lateinit var auto: AutoCompleteTextView
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_effects)
        effectList = Main.initListProcesses()
        val editText:EditText = findViewById(R.id.editText)
        val l: List<String> = List<String>(effectList.size, init = {
            val s0 : String = ((effectList[it]).javaClass.toString())
            val s: String = (editText.text.toString()
                    +s0+",")
            editText.setText(
                s.subSequence(0, s.length), TextView.BufferType.EDITABLE
            ).toString()

        })
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.select_dialog_item, l
        )
        auto = findViewById(R.id.effectsAutoCompleteTextView)
        auto.setThreshold(1)
        auto.setAdapter(adapter)



    }
    override fun onSaveInstanceState(outState : Bundle)
    {
        outState.putString("classname", auto.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
    /*
    fun process(strsFile: File)
    {
        //BitmapFactory.decodeFile(filePath)
    }

     */
}
