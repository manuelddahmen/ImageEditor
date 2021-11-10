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
    lateinit var auto: EditText

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_effects)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun init(savedInstanceState : Bundle?) {
        effectList = Main.initListProcesses()
        val editText: EditText = findViewById(R.id.editText)
        val l: List<String> = List<String>(effectList.size, init = {
            val s0: String = ((effectList[it]).javaClass.toString())
            val s: String = (editText.text.toString()
                    + s0 + ",")
            editText.setText(
                s.subSequence(0, s.length), TextView.BufferType.EDITABLE
            ).toString()

        })

        auto = findViewById(R.id.effectsAutoCompleteTextView)
        auto.setText(savedInstanceState?.getString("classname"))
        auto.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val split: MutableList<String> = s.toString().split(",") as MutableList<String>
                    val effects = editText.text.toString()
                    val splitEffectsList: List<String> = effects.split(",")
                    var i: Int = 0
                    var autoStr : String = ""
                    for (s1 in split) {
                        if (s1.length > 2) {
                            val count: Long = splitEffectsList.stream().filter { it.contains(s1) }
                                .count()
                            if (count == 1L) {
                                autoStr += splitEffectsList.stream().filter { it.contains(s1) }
                                    .findFirst().toString()

                            } else {
                                autoStr += s1
                            }
                        }
                        i++
                    }
                    auto.setText(autoStr)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("classname", auto.text.toString())
        super.onSaveInstanceState(outState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        init(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    /*
    fun process(strsFile: File)
    {
        //BitmapFactory.decodeFile(filePath)
    }

     */
}
