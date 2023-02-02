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

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import one.empty3.Main2022
import one.empty3.io.ProcessFile
import java.io.File

class ChooseEffectsActivity : AppCompatActivity() {
    private val appDataPath = "/one.empty3.feature.app.maxSdk29.pro/"
    lateinit var mediaFile: File
    lateinit var autoCompleteTextView: MultiAutoCompleteTextView
    lateinit var editText: EditText
    lateinit var effectList: ArrayList<ProcessFile>
    lateinit var effectListStr: Array<String>
    lateinit var editText1: EditText
    lateinit var videoEffectPreview: VideoView
    var maxRes: Int = 1200;

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("effects#logging", "create Effect Activity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_effects)
        init(savedInstanceState)
        mediaFile = intent.extras?.get("data") as File
        maxRes = intent.extras?.get("maxRes") as Int
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun complete() {
        /*Log.i("autocomplete",
            "Autocomplete search")
        val split: List<String> = editText1.text.toString().split(",") as List<String>
        val effects = editText.text.toString()
        val splitEffectsList: List<String> = effects.split(",")
        var autoStr : String = ""
        for ((i, s1) in split.withIndex()) {
            Log.i("filters' list", ""+i+" : "+s1)
            if (s1.length > 2) {
                val count: Long = splitEffectsList.stream().filter { it.contains(s1) }
                    .count()
                if (count == 1L) {
                    Log.i("autocomplete",
                        "Autocomplete replacement: {"+s1+"} by ")
                    autoStr += splitEffectsList.stream().filter { it.contains(s1) }
                        .findFirst().toString()

                } else {
                    autoStr += s1
                }
            }
        }
        var eff = findViewById<EditText>(R.id.effectsToApply)
        eff.setText(autoStr)

        return

         */
    }

    fun init(savedInstanceState: Bundle?) {
        autoCompleteTextView =
            findViewById<MultiAutoCompleteTextView>(R.id.effectsAutoCompleteTextView)
        val effectList = Main2022.initListProcesses()
        val indexes = Main2022.indices
        effectListStr = Array<String>(effectList.size * 3, { "" })
        var i = 0
        for (i in 0..effectList.size - 1) {
            effectListStr[i * 3] =
                effectList.get(indexes[i])!!.javaClass.name//.javaClass.simpleName.substring(
            //"class ".length, initListProcesses[i].javaClass.simpleName.lastIndexOf('.') )
            effectListStr[i * 3 + 1] = effectList.get(indexes[i])!!.javaClass.name.substring(
                effectList.get(indexes[i])!!.javaClass.name.lastIndexOf('.') + 1,
                effectList.get(indexes[i])!!.javaClass.name.length
            )
            effectListStr[i * 3 + 2] = effectListStr[i * 3 + 1].lowercase()
        }
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, effectListStr)
        autoCompleteTextView.setAdapter(arrayAdapter)
        autoCompleteTextView.threshold = 0
        autoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        val applyEffectAction = findViewById<Button>(R.id.effectsToApply)
        applyEffectAction.setOnClickListener {
            run {
                val intent = Intent(Intent.ACTION_EDIT)
                println("Click on Effect button")
                intent.setDataAndType(Uri.fromFile(mediaFile), "image/jpg")
                intent.putExtra("data", mediaFile)
                intent.setClass(
                    autoCompleteTextView.context,
                    Class.forName("one.empty3.feature.app.maxSdk29.pro.MyCameraActivity")
                )
                val fileIn: File = File(mediaFile.toString())

                Log.d("Initial input file", fileIn.toString())
                Log.d(
                    "Initial input file exists?", "Exists?"
                            + ((fileIn.exists()).toString())
                )

                intent.putExtra(
                    "data", fileIn
                )
                var dirRoot: String = filesDir.absolutePath// + File.separator + "data/files"//!!!?
                /*uri = FileProvider.getUriForFile(
                    this@MyCameraActivity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    currentFile
                )
                dirRoot =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
*/
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ), PackageManager.PERMISSION_GRANTED
                    )
                }
                var dir = ""
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    dir = "photoDir"
                    dirRoot = mediaFile.toString()
                        .substring(0, mediaFile.toString().lastIndexOf(File.separator))
                } else {
                    println("Error : no permission for read/write storage")
                    dir = "appDir"
                }
                val strEffectsList: String = autoCompleteTextView.text.toString()
                var currentProcessFile: File = fileIn
                var currentProcessDir: File = File(
                    fileIn.absolutePath.substring(
                        0,
                        fileIn.absolutePath.lastIndexOf("/")
                    )
                )
                var currentOutputFile: File
                var currentOutputDir: File
                var index = -1
                val name = currentProcessFile.name
                //dir = "appDir"
                val split = strEffectsList.split(",").toMutableList();
                // Split given string and compares to effects'classnames
                // Replace split substring in split string
                var elI: Int = 0
                for (elStr in split) {
                    for (cl in effectList) {
                        if (cl.javaClass.name.contains(elStr))
                            split[elI] = cl.javaClass.name
                    }
                    elI++
                }
                var classnamesComa: String = ""
                split.forEach {
                    classnamesComa += it.toString() + ","
                }
                classnamesComa = classnamesComa.trim(',')

                //autoCompleteTextView.text. = classnamesComa

                split.forEach {
                    val trim = it.trim()
                    if (effectListStr.contains(trim)) {
                        val indexOf: Int = effectListStr.indexOf(trim)
                        val processFile: ProcessFile = effectList.get(indexes[indexOf / 3])!!
                        if (index == -1) {
                            if (dir.equals("appDir")) {
                                currentOutputFile = File(
                                    nextFile(
                                        dirRoot +
                                                File.separator + trim + index,
                                        name.substring(0, name.lastIndexOf(".")),
                                        "jpg"
                                    )
                                )
                                currentOutputDir = File(
                                    currentOutputFile.absolutePath.substring(
                                        0,
                                        currentOutputFile.absolutePath.lastIndexOf("/")
                                    )
                                )
                            } else {
                                currentOutputFile = File(
                                    currentProcessFile.absolutePath.substring(
                                        0,
                                        currentProcessFile.absolutePath.lastIndexOf("/")
                                    )
                                )
                                currentOutputFile = File(
                                    currentProcessFile.absolutePath.substring(
                                        0,
                                        currentProcessFile.absolutePath.lastIndexOf("/")
                                    )
                                )
                                currentOutputFile = File(
                                    currentOutputFile.absolutePath.substring(
                                        0, currentProcessFile.absolutePath
                                            .lastIndexOf(File.separator)
                                    ) + File.separator + trim + index +
                                            File.separator + name
                                )
                                currentOutputDir = File(
                                    currentOutputFile.absolutePath.substring(
                                        0,
                                        currentOutputFile.absolutePath.lastIndexOf("/")
                                    )
                                )
                            }

                        } else {
                            if (dir.equals("appDir")) {
                                currentOutputFile = File(
                                    nextFile(
                                        dirRoot, name.substring(0, name.lastIndexOf(".")),
                                        ".jpg"
                                    )
                                )
                                currentOutputDir = File(
                                    currentOutputFile.absolutePath.substring(
                                        0,
                                        currentOutputFile.absolutePath.lastIndexOf("/")
                                    )
                                )

                            } else {
                                currentOutputFile = File(
                                    currentProcessFile
                                        .absolutePath.substring(
                                            0, currentProcessFile
                                                .absolutePath.lastIndexOf('/')
                                                    - 1
                                        )
                                )
                                currentOutputFile = File(
                                    currentOutputFile.absolutePath.substring(
                                        0, currentOutputFile.absolutePath
                                            .lastIndexOf(File.separator)
                                    ) + File.separator + trim + index +
                                            File.separator + name
                                )
                                currentOutputDir = File(
                                    currentOutputFile.absolutePath.substring(
                                        0,
                                        currentOutputFile.absolutePath.lastIndexOf("/")
                                    )
                                )

                            }

                        }
                        currentOutputDir.mkdirs()
                        println("Effect class           : " + trim)
                        println("In picture             : " + currentProcessFile)
                        println("In picture directory   : " + currentProcessDir)
                        println("Out  picture           : " + currentOutputFile)
                        println("Out picture directory  : " + currentOutputDir)
                        try {
                            if (currentProcessFile.exists()
                                && !currentOutputFile.exists()
                            ) {
                                processFile.setMaxRes(maxRes)
                                if (!processFile.process(currentProcessFile, currentOutputFile)) {
                                    println("Error processing file.")
                                    println("Error in " + processFile.javaClass.name)
                                    return@setOnClickListener
                                }
                            } else {
                                println(
                                    "File in doesn't exists, or File out exists\n" +
                                            "\nPrecision currentProcessDir  exists?: " + currentProcessDir.exists() +
                                            "\nPrecision currentProcessFile exists?: " + currentProcessFile.exists() +
                                            "\nPrecision currentOutputDir   exists?: " + currentOutputDir.exists() +
                                            "\nPrecision currentOutputFile  exists?: " + currentOutputFile.exists()
                                )
                                return@setOnClickListener
                            }
                        } catch (ex: Exception) {
                            println("Error processing file.")
                            ex.printStackTrace()
                            return@setOnClickListener
                        }
                        currentProcessFile = currentOutputFile
                    }
                    index++


                }
                intent.data = Uri.fromFile(currentProcessFile)
                intent.putExtra("data", currentProcessFile)
                startActivity(intent)

            }
        }

        /*
            Log.i("effects#logging", "init Details Effect Activity")
            effectList = Main.initListProcesses()
            editText = findViewById(R.id.editText)
            val l: List<String> = List<String>(effectList.size, init = {
                val s0: String = ((effectList[it]).javaClass.toString())
                val s: String = (editText.text.toString()
                        + s0 + ",")
                editText.setText(
                    s.subSequence(0, s.length), TextView.BufferType.EDITABLE
                ).toString()

            })

            editText1 = findViewById(R.id.effectsAutoCompleteTextView)
            editText1.setText(savedInstanceState?.getString("classname"))
            editText1.addTextChangedListener {
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        complete()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        complete()
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        complete()
                    }
                }
            }
      */


    }

    private fun nextFile(directory: String, filenameBase: String, extension: String): String {
        return directory + File.separator + filenameBase + "." + extension
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("effects#logging", "save Effect Activity")
        outState.putString("classname", autoCompleteTextView.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("effects#logging", "restore Effect Activity")
        val string = savedInstanceState?.getString("classname")
        autoCompleteTextView = findViewById(R.id.effectsAutoCompleteTextView)
        autoCompleteTextView.setText(string)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        Log.i("effects#logging", "destroy Effect Activity")
        super.onDestroy()
    }

}
