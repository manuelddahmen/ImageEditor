package com.android.example.cameraxbasic.fragments

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.core.widget.addTextChangedListener
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
        auto.setText(savedInstanceState?.getString("classname"))
        auto.addTextChangedListener {object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var split:MutableList<String> = s.toString().split(",") as MutableList<String>
                var s1:String
                val effects = editText.text.toString()
                val splitEffectsList:List<String> = effects.split(",")
                var i:Int = 0
                for(s1 in split ) {
                    if(s1.length>2 && splitEffectsList.contains(s1)) {
                       var count:Long = splitEffectsList.stream().filter { s1==it }
                        .count()
                        if(count==1L) {
                            split[i] = splitEffectsList.stream().filter{it==s1}.findFirst().toString()
                        }
                    }
                    i++
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        }
    }
    override fun onSaveInstanceState(outState : Bundle)
    {
        outState.putString("classname", auto.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        auto.setText( savedInstanceState.getString("classname"))
        super.onRestoreInstanceState(savedInstanceState)
    }

    /*
    fun process(strsFile: File)
    {
        //BitmapFactory.decodeFile(filePath)
    }

     */
}
