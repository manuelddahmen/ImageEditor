package one.empty3.feature.app.maxSdk29.pro;

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

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import one.empty3.Main2022
import one.empty3.io.ProcessFile
import java.io.File
import java.util.UUID


class ChooseEffectsActivity2 : ActivitySuperClass() {
    private var started: Boolean = false
    private lateinit var applyEffects1: Button
    private var progressBar: ProgressBar? = null
    private var cancelButton: Button? = null
    private var seeFileButton: Button? = null
    private var goButton: Button? = null
    private var mViewModel: EffectsViewModel? = null
    private var unauthorized: Boolean = false
    private val READ_WRITE_STORAGE: Int = 15165516
    var listEffects: HashMap<String, ProcessFile>? = null
    private lateinit var classnames: ArrayList<String>
    private lateinit var effectApply: Button
    private lateinit var recyclerView: RecyclerView
    private var hasRun = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        classnames = ArrayList()

        Main2022.effects = classnames
        Main2022.indices = ArrayList()
        Main2022.listOfFactors()
        setContentView(R.layout.recycler_view_effect_activity)

        //maxRes = Utils().getMaxRes(this)

        recyclerView = findViewById(R.id.recycler_view_effect)
        val processFileArrayAdapter = ProcessFileArrayAdapter()
        processFileArrayAdapter.setMainAnd(Main2022(), recyclerView, this)
        processFileArrayAdapter.setCurrentActivity(this)
        recyclerView.adapter = processFileArrayAdapter
        listEffects = Main2022.initListProcesses()
        Log.i("effects#logging", "create Effect Activity")

        // Get the ViewModel
        mViewModel = EffectsViewModel(application)


        // Setup blur image file button
        goButton = findViewById<Button>(R.id.go_button)
        seeFileButton = findViewById<Button>(R.id.see_file_button)
        cancelButton = findViewById<Button>(R.id.cancel_button)
        progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        //applyEffects1 = findViewById<Button>(R.id.applyEffects)

        goButton!!.setOnClickListener { _ ->
            if(currentFile.currentFile!=null) {
                Main2022.setListEffects(listEffects)
                mViewModel!!.setImageUri(currentFile.currentFile.absolutePath)
                started = true
                mViewModel!!.applyEffect(currentFile.currentFile)
            }
        }

        mViewModel!!.outputWorkInfo.observe(this) { listOfWorkInfo ->

            // If there are no matching work info, do nothing
            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()|| !started
                /*|| listOfWorkInfo.get(0).state == WorkInfo.State.ENQUEUED*/) {
                println("Not started")

            } else  {

                // We only care about the first output status.
                // Every continuation has only one worker tagged TAG_OUTPUT
                val workInfo: WorkInfo = listOfWorkInfo[0]

                val finished = workInfo.state.isFinished // || listOfWorkInfo[0].state != WorkInfo.State.RUNNING
                if (!finished && Main2022.getOutputFIle()==null) {
                    showWorkInProgress()
                    println("In Progress")
                } else {
                    showWorkFinished()
                    println("Finished")
                    val outputData = workInfo.outputData

                    val outputImageUri = outputData.getString(Constants.KEY_IMAGE_URI)

                    // If there is an output file show "See File" button
                    if (!TextUtils.isEmpty(outputImageUri)) {
                        seeFileButton!!.setVisibility(View.VISIBLE)
                    }
                    started = false
                    this.currentFile.addAtCurrentPlace(DataApp(Main2022.getOutputFIle()))
                    val actionView = Intent(applicationContext, MyCameraActivity::class.java)
                    passParameters(actionView)
                }
            }
        }

        seeFileButton!!.setOnClickListener { view ->
            val currentUri: File = Main2022.getOutputFIle()  //= mViewModel!!.getOutputUri()
            if (currentUri != null) {
                currentFile.addAtCurrentPlace(DataApp( currentUri))
                Handler(Looper.getMainLooper()).post {
                    val actionView = Intent(applicationContext, MyCameraActivity::class.java)
                    passParameters(actionView)
                }
            }
            println("After apply effects, seeFileButton OnClick thread")
        }

        cancelButton!!.setOnClickListener {
            view -> mViewModel!!.cancelWork()
            //mViewModel!!.setImageUri(null)
        }

        // set OnClick
        applyEffects()

        init(savedInstanceState)

    }


    fun init(savedInstanceState: Bundle?) {
        requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
            ), READ_WRITE_STORAGE
        )
        if (!hasRun) {
            ;//;applyEffects()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_WRITE_STORAGE) {
            var g: Int = 0
            for (granted in grantResults)
                if ((granted == PackageManager.PERMISSION_GRANTED))
                    g = g + 1


            try {

                unauthorized = false


                //Main2022.setListEffects(listEffects)
                //initAuthorized()
                hasRun = true

            } catch (ex: RuntimeException) {

                ex.printStackTrace()

            }

        }
    }


    private fun initAuthorized() {
        var index = 0
        /*applyEffects1.setOnClickListener {

            if(currentFile.currentFile==null)
                return@setOnClickListener
            try {
                currentFile.addAtCurrentPlace(DataApp(Utils().writePhoto(
                    this, ImageIO.read(currentFile.currentFile).getBitmap(),
                    "before-effect"
                )))
            } catch (ex:RuntimeException ) {
                return@setOnClickListener
            }
            classnames = Main2022.effects

            classnames.forEachIndexed { index1, it1 ->
                classnames[index1] = listEffects?.get(it1)?.javaClass?.name
            }


            run {
                var totalOutput: File = currentFile.currentFile

                println("Clicked on Effect button, running effects")
                val fileIn: File = File(currentFile.currentFile.toString())

                Log.d("Initial input file", fileIn.toString())
                Log.d(
                    "Initial input file exists?", "Exists?"
                            + ((fileIn.exists()).toString())
                )
                Log.d(packageName, "\"Effects' list size:" + classnames.size)


                var dirRoot: String = filesDir.absolutePath
                // + File.separator + "data/files"//!!!?
                /*uri = FileProvider.getUriForFile(
            this@MyCameraActivity,
            BuildConfig.APPLICATION_ID + ".provider",
            currentFile.currentFile
        )
        dirRoot =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
*/


                var dir = ""

                dir = "photoDir"
                dirRoot = currentFile.currentFile.toString()
                    .substring(0, currentFile.currentFile.toString().lastIndexOf(File.separator))

                var currentProcessFile: File = fileIn
                val currentProcessDir = File(
                    fileIn.absolutePath.substring(
                        0,
                        fileIn.absolutePath.lastIndexOf("/")
                    )
                )
                var currentOutputFile: File = currentFile.currentFile
                val currentOutputDir: File = currentProcessDir
                index = -1
                val name = currentProcessFile.name
                //dir = "appDir"
                if (classnames.size == 0) {

                    return@setOnClickListener;
                }

                var processFile: ProcessFile? = null

                var currentProcessInFile: File = currentFile.currentFile
                classnames.forEach { it1 ->
                    if (it1 == null || it1.isBlank()) {
                        return@forEach
                    }
                    val effectListStr: String = it1
                    val trim = it1.trim()
                    if (effectListStr.contains(trim)) {
                        processFile = Class.forName(it1).newInstance() as ProcessFile
                        currentProcessFile = currentProcessInFile
                        if (processFile != null) {
                            currentOutputFile = File(
                                nextFile(
                                    currentProcessInFile.parentFile!!.absolutePath,
                                    "effect-" + UUID.randomUUID(), "jpg"
                                )
                            )
                            currentOutputDir.mkdirs()
                            println("Effect class           : $trim")
                            println("In picture             : $currentProcessFile")
                            println("In picture directory   : $currentProcessDir")
                            println("Out  picture           : $currentOutputFile")
                            println("Out picture directory  : $currentOutputDir")
                            try {
                                if (currentProcessFile.exists()
                                // &&!currentOutputFile.exists()
                                ) {
                                    val lastCurrentProcessFile = currentProcessFile

                                    processFile!!.setMaxRes(maxRes)
                                    if (!(processFile!!.process(
                                            currentProcessFile,
                                            currentOutputFile
                                        ))
                                    ) {

                                        println("Error processing file.Error processing file.")
                                        println("Error in " + processFile!!.javaClass.name)
                                        Toast.makeText(
                                            applicationContext,
                                            ("Error while applying filter" + (processFile!!.javaClass.name)),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
                                    } else {

                                        try {
                                            val mix = Mix()
                                            val currentOutputFile1 = File(
                                                nextFile(
                                                    currentProcessInFile.parentFile!!.absolutePath,
                                                    "alpha-" + UUID.randomUUID(),
                                                    "jpg"
                                                )
                                            )
                                            mix.progressColor = Mix.MAX_PROGRESS
                                            val pf = processFile!!.javaClass.simpleName
                                            if (Main2022.effectsFactors != null && Main2022.effectsFactors[pf] != null)
                                                mix.progressColor = Main2022.effectsFactors[pf]!!

                                            println("mix.progressColor="+mix.progressColor);
                                            mix.processFiles(
                                                currentOutputFile1,
                                                lastCurrentProcessFile,
                                                currentOutputFile
                                            )

                                            //System.err.println(""+javaClass+" "+it1+" progress : "+mix.progressColor)

                                            currentOutputFile = currentOutputFile1
                                            totalOutput = currentOutputFile
                                        } catch (ex: RuntimeException) {
                                            ex.printStackTrace()
                                        }
                                    }
                                } else {
                                    println(
                                        "Success\n" +
                                                "File in doesn't exists, or File out exists\n" +
                                                "\ncurrentProcessDir  exists?: " + currentProcessDir.exists() +
                                                "\ncurrentProcessFile exists?: " + currentProcessFile.exists() +
                                                "\ncurrentOutputDir   exists?: " + currentOutputDir.exists() +
                                                "\ncurrentOutputFile  exists?: " + currentOutputFile.exists()
                                    )
                                    Toast.makeText(
                                        applicationContext,
                                        ("Source file doesn't exist" + (processFile!!.javaClass.name)),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@setOnClickListener
                                }
                            } catch (ex: Exception) {
                                val errMessage = "Error processing file. Exception :$ex"
                                println(errMessage)
                                ex.printStackTrace()

                                return@setOnClickListener
                            }
                            currentProcessFile = currentOutputFile
                            currentProcessInFile = currentProcessFile
                        }

                    }
                    index++


                }
                if (processFile != null && totalOutput != null) {
                    Toast.makeText(
                        applicationContext,
                        ("Applied effect:" + (processFile!!.javaClass.name)),
                        Toast.LENGTH_LONG
                    ).show()

                    currentFile.addAtCurrentPlace(DataApp(
                        Utils().writePhoto(this, ImageIO.read(totalOutput).bitmap, "effect-")
                    ))

                    val intent2 = Intent(applicationContext, MyCameraActivity::class.java)
                    passParameters(intent2)
                }
            }
        }

         */
    }
    fun comaStringList(str: String): String {
        return "";
    }

    private fun nextFile(directory: String, filenameBase: String, extension: String): String {
        return directory + File.separator + filenameBase + "--" + UUID.randomUUID() + "." + extension
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

    private fun applyEffects() {
        initAuthorized()

//        currentFile.currentFile =
//            Utils().writePhoto(this, ImageIO.read(totalOutput).bitmap, "effect-");
//
//        val intent2 = Intent(applicationContext, MyCameraActivity::class.java)
//        passParameters(intent2)
    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private fun showWorkInProgress() {
        progressBar!!.setVisibility(View.VISIBLE)
        cancelButton!!.setVisibility(View.VISIBLE)
        goButton!!.setVisibility(View.GONE)
        seeFileButton!!.setVisibility(View.GONE)
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        progressBar!!.setVisibility(View.GONE)
        cancelButton!!.setVisibility(View.GONE)
        goButton!!.setVisibility(View.VISIBLE)
    }

}


