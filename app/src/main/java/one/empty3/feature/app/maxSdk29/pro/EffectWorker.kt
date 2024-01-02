/*package one.empty3.feature.app.maxSdk29.pro

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.Main2022
import one.empty3.feature20220726.Mix
import one.empty3.io.ProcessFile
import java.io.File
import java.util.UUID

class EffectWorker(
    context: Context,
    workerParams: WorkerParameters,
    val effectActivity: ChooseEffectsActivity2
) :
    Worker(context, workerParams) {
    private var maxRes: Int = effectActivity.maxRes
    private var listEffects: java.util.HashMap<String, ProcessFile>? = effectActivity.listEffects
    private var activity: ChooseEffectsActivity2 = effectActivity
    private var currentFile: File = effectActivity.currentFile

    override fun doWork(): ListenableWorker.Result {
        var index = 0

        if (currentFile == null)
            return ListenableWorker.Result.failure()
        try {
            currentFile = Utils().writePhoto(
                activity, ImageIO.read(currentFile).getBitmap(),
                "before-effect"
            )!!
        } catch (ex: RuntimeException) {
            return ListenableWorker.Result.failure()
        }
        var classnames = Main2022.effects


        classnames.forEachIndexed { index1, it1 ->
            classnames[index1] = listEffects?.get(it1)?.javaClass?.name
        }


        run {
            var totalOutput: File = currentFile

            println("Clicked on Effect button, running effects")
            val fileIn: File = File(currentFile.toString())

            Log.d("Initial input file", fileIn.toString())
            Log.d(
                "Initial input file exists?", "Exists?"
                        + ((fileIn.exists()).toString())
            )


            var dirRoot: String = activity.getFilesDir().absolutePath
            // + File.separator + "data/files"//!!!?
            /*uri = FileProvider.getUriForFile(
        this@MyCameraActivity,
        BuildConfig.APPLICATION_ID + ".provider",
        currentFile
    )
    dirRoot =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
*/


            var dir = ""

            dir = "photoDir"
            dirRoot = currentFile.toString()
                .substring(0, currentFile.toString().lastIndexOf(File.separator))

            var currentProcessFile: File = fileIn
            val currentProcessDir = File(
                fileIn.absolutePath.substring(
                    0,
                    fileIn.absolutePath.lastIndexOf("/")
                )
            )
            var currentOutputFile: File = currentFile
            val currentOutputDir: File = currentProcessDir
            index = -1
            val name = currentProcessFile.name
            //dir = "appDir"
            if (classnames.size == 0) {

                return ListenableWorker.Result.failure()
            }

            var processFile: ProcessFile? = null

            var currentProcessInFile: File = currentFile
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
                                    return ListenableWorker.Result.failure()
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

                                        println("mix.progressColor=" + mix.progressColor);
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
                                            "\ncurrentOutputFile  exists?: " + currentOutputFile.exists() +
                                            "\ncurrentOutputFile  exists?: " + currentOutputFile.exists()
                                )
                                Toast.makeText(
                                    applicationContext,
                                    ("Source file doesn't exist" + (processFile!!.javaClass.name)),
                                    Toast.LENGTH_LONG
                                ).show()
                                return ListenableWorker.Result.failure()
                            }
                        } catch (ex: Exception) {
                            val errMessage = "Error processing file. Exception :$ex"
                            println(errMessage)
                            ex.printStackTrace()

                            return ListenableWorker.Result.failure()
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

                currentFile =
                    Utils().writePhoto(activity, ImageIO.read(totalOutput).bitmap, "effect-")!!;

                return ListenableWorker.Result.success( )
            }

        }
        return ListenableWorker.Result.failure()

    }

    fun comaStringList(str: String): String {
        return "";
    }

    private fun nextFile(directory: String, filenameBase: String, extension: String): String {
        return directory + File.separator + filenameBase + "--" + UUID.randomUUID() + "." + extension
    }

}*/