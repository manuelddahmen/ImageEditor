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
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.Main2022
import one.empty3.io.ProcessFile
import java.io.File

@ExperimentalCamera2Interop class ChooseEffectsActivity2 : ActivitySuperClass() {
    private var unautorized: Boolean = false
    private val READ_WRITE_STORAGE: Int = 15165516
    private var listEffects: HashMap<String, ProcessFile>? = null
    private lateinit var classnames: ArrayList<String>
    private lateinit var effectApply: Button
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        classnames = ArrayList()

        Main2022.effects = classnames
        Main2022.indices = ArrayList()

        setContentView(R.layout.recycler_view_effect_activity)

        var maxRes = Utils().getMaxRes(this, savedInstanceState)

        recyclerView = findViewById(R.id.recycler_view_effect)
        val processFileArrayAdapter = ProcessFileArrayAdapter()
        processFileArrayAdapter.setMainAnd(Main2022(), recyclerView, this)
        recyclerView.adapter = processFileArrayAdapter
        listEffects = Main2022.initListProcesses()
        Log.i("effects#logging", "create Effect Activity")
        effectApply = findViewById(R.id.applyEffects)
        init(savedInstanceState)
        maxRes = intent.extras?.get("maxRes") as Int
        initAuthorized()
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun complete() {
//        Log.i("autocomplete",
//            "Autocomplete search")
//        val split: List<String> = editText1.text.toString().split(",") as List<String>
//        val effects = editText.text.toString()
//        val splitEffectsList: List<String> = effects.split(",")
//        var autoStr : String = ""
//        for ((i, s1) in split.withIndex()) {
//            Log.i("filters' list", ""+i+" : "+s1)
//            if (s1.length > 2) {
//                val count: Long = splitEffectsList.stream().filter { it.contains(s1) }
//                    .count()
//                if (count == 1L) {
//                    Log.i("autocomplete",
//                        "Autocomplete replacement: {"+s1+"} by ")
//                    autoStr += splitEffectsList.stream().filter { it.contains(s1) }
//                        .findFirst().toString()
//
//                } else {
//                    autoStr += s1
//                }
//            }
//        }
//        var eff = findViewById<EditText>(R.id.effectsToApply)
//        eff.setText(autoStr)
//
//        return
//
//
//    }

    fun init(savedInstanceState: Bundle?) {
        requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
            ), READ_WRITE_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_WRITE_STORAGE && grantResults != null) {
            var g:Int = 0
            for (granted in grantResults)
                if((granted == PackageManager.PERMISSION_GRANTED))
                    g = g + 1


            if (g<2)
                unautorized = true;
            else
                unautorized = false;
        }
    }
    private fun initAuthorized() {
        var index = 0
        effectApply.setOnClickListener {
            classnames = Main2022.effects

            classnames.forEachIndexed { index1, it ->
                classnames[index1] = listEffects?.get(it)?.javaClass?.name
            }


            run {
                println("Clicked on Effect button, running effects")
                val fileIn: File = File(currentFile.toString())

                Log.d("Initial input file", fileIn.toString())
                Log.d(
                    "Initial input file exists?", "Exists?"
                            + ((fileIn.exists()).toString())
                )


                var dirRoot: String =
                    filesDir.absolutePath// + File.separator + "data/files"//!!!?
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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), READ_WRITE_STORAGE
                    )
                }
                var dir = ""

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
                    dir = "photoDir"
                    dirRoot = currentFile.toString()
                        .substring(0, currentFile.toString().lastIndexOf(File.separator))
                } else {
                    println("Error : no permission for read/write storage")
                    dir = "appDir"
                    this.requestPermissions(
                        arrayOf<String>(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), READ_WRITE_STORAGE
                    )

                }

                var currentProcessFile: File = fileIn
                val currentProcessDir = File(
                    fileIn.absolutePath.substring(
                        0,
                        fileIn.absolutePath.lastIndexOf("/")
                    )
                )
                var currentOutputFile: File = currentFile
                var currentOutputDir: File = currentFile
                index = -1
                val name = currentProcessFile.name
                //dir = "appDir"
                if (classnames.size == 0) {

                    return@setOnClickListener;
                }
                classnames.forEach {
                    if (it.isNullOrBlank()) {
                        return@setOnClickListener
                    }
                    val effectListStr: String = it
                    val trim = it.trim()
                    if (effectListStr.contains(trim)) {
                        val indexOf: Int = effectListStr.indexOf(trim)
                        val processFile: ProcessFile =
                            Class.forName(it).newInstance() as ProcessFile
                        if (index == -1) {
                            if (dir.equals("appDir")) {
                                currentOutputFile = File(
                                    nextFile(
                                        dirRoot +
                                                File.separator + it + index,
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
                                if (!processFile.process(
                                        currentProcessFile,
                                        currentOutputFile
                                    )
                                ) {
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
                            val errMessage = "Error processing file. Exception :$ex"
                            println(errMessage)
                            ex.printStackTrace()

                            return@setOnClickListener
                        }
                        currentProcessFile = currentOutputFile
                    }
                    index++

                    Toast.makeText(applicationContext, ("Applied effect:" + (currentProcessFile.absoluteFile?:"No file processed ??")), Toast.LENGTH_SHORT).show()
                }

                currentFile = Utils().writePhoto(this, ImageIO.read(currentProcessFile).bitmap, "effect-");

                val intent2 = Intent(it.context, MyCameraActivity::class.java)
                passParameters(intent2)

            }

        }
//
//    Log.i("effects#logging", "init Details Effect Activity")
//    effectList = Main.initListProcesses()
//    editText = findViewById(R.id.editText)
//    val l: List<String> = List<String>(effectList.size, init = {
//        val s0: String = ((effectList[it]).javaClass.toString())
//        val s: String = (editText.text.toString()
//                + s0 + ",")
//        editText.setText(
//            s.subSequence(0, s.length), TextView.BufferType.EDITABLE
//        ).toString()
//
//    })
//
//    editText1 = findViewById(R.id.effectsAutoCompleteTextView)
//    editText1.setText(savedInstanceState?.getString("classname"))
//    editText1.addTextChangedListener {
//        object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                complete()
//            }
//
//            override fun beforeTextChanged(
//                s: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            ) {
//                complete()
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                complete()
//            }
//        }
//    }
//
//

    }


    fun comaStringList(str: String): String {
        return "";
    }

    private fun nextFile(directory: String, filenameBase: String, extension: String): String {
        return directory + File.separator + filenameBase + "." + extension
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("effects#logging", "save Effect Activity")
        outState.putStringArrayList("classname", classnames)
        super.onSaveInstanceState(outState)
    }

    fun autoCompleteTextViewGettext(): String {
        var str: String = ""
        classnames.forEach {
            var it1 = it.trim().toString()
            if (str == "")
                str += it1
            else
                str += ", $it1"
        }
        return str;
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("effects#logging", "restore Effect Activity")
        val string = savedInstanceState.getStringArray("classnames")
        classnames = ArrayList()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        Log.i("effects#logging", "destroy Effect Activity")
        super.onDestroy()
    }
}
