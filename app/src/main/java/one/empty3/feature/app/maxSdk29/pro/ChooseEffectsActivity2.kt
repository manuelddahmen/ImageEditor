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
import android.content.pm.PermissionGroupInfo
import android.content.pm.PermissionInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.PermissionRequest
import android.widget.Button
import android.widget.EditText
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.recyclerview.widget.RecyclerView
import one.empty3.Main2022
import one.empty3.io.ProcessFile
import java.io.File
import java.security.Permission
import java.security.Permissions

class ChooseEffectsActivity2 : AppCompatActivity() {
    private val INT_READ_STORAGE: Int = 5152112
    private val INT_WRITE_STORAGE: Int = 5152113
    private var listEffects: HashMap<String, ProcessFile>? = null
    private lateinit var classnames: ArrayList<String>
    private lateinit var effectApply: Button
    private val appDataPath = "/one.empty3.feature.app.maxSdk29.pro/"
    private lateinit var mediaFile: File
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var effectListStr: Array<String>
    private lateinit var editText1: EditText
    private lateinit var videoEffectPreview: VideoView
    private var maxRes: Int = 1200;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        classnames = ArrayList()

        setContentView(R.layout.recycler_view_effect_activity)

        maxRes = Utils().getMaxRes(this, savedInstanceState)

        recyclerView = findViewById(R.id.recycler_view_effect)
        val processFileArrayAdapter = ProcessFileArrayAdapter()
        processFileArrayAdapter.setMainAnd(Main2022(), recyclerView, this)
        recyclerView.adapter = processFileArrayAdapter
        listEffects = Main2022.initListProcesses()
        Log.i("effects#logging", "create Effect Activity")
        effectApply = findViewById(R.id.applyEffects)
        init(savedInstanceState)
        mediaFile = intent.extras?.get("data") as File
        if (mediaFile == null) {
            mediaFile = intent.extras?.get("currentFile") as File
        }
        maxRes = intent.extras?.get("maxRes") as Int
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
        var index = 0
        effectApply.setOnClickListener {
            classnames = Main2022.effects

            classnames.forEachIndexed { index1, it ->
                classnames[index1] = listEffects?.get(it)?.javaClass?.name
            }


            run {
                val intent = Intent(Intent.ACTION_EDIT)
                println("Click on Effect button")
                val fileIn: File = File(mediaFile.toString())

                Log.d("Initial input file", fileIn.toString())
                Log.d(
                    "Initial input file exists?", "Exists?"
                            + ((fileIn.exists()).toString())
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
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), INT_WRITE_STORAGE
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
                    dirRoot = mediaFile.toString()
                        .substring(0, mediaFile.toString().lastIndexOf(File.separator))
                } else {
                    println("Error : no permission for read/write storage")
                    dir = "appDir"
                    this.requestPermissions(
                        arrayOf<String>(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), INT_WRITE_STORAGE
                    )

                }

                var currentProcessFile: File = fileIn
                val currentProcessDir = File(
                    fileIn.absolutePath.substring(
                        0,
                        fileIn.absolutePath.lastIndexOf("/")
                    )
                )
                var currentOutputFile: File
                var currentOutputDir: File
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
                            val errMessage = "Error processing file. Exception :$ex"
                            println(errMessage)
                            ex.printStackTrace()

                            return@setOnClickListener
                        }
                        currentProcessFile = currentOutputFile
                    }
                    index++


                }
                val intent2 = Intent()
                intent2.setDataAndType(Uri.fromFile(currentProcessFile), "image/jpg")
                Utils().addCurrentFileToIntent(intent2, currentProcessFile)
                intent2.extras?.putInt("maxRes", maxRes)
                intent2.setClass(
                    applicationContext,
                    Class.forName("one.empty3.feature.app.maxSdk29.pro.MyCameraActivity")
                )
                startActivity(intent2)

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == INT_WRITE_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }
}
