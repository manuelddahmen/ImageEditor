package one.empty3.feature.app

import android.app.Activity
import java.io.File

class ThreadOpenActivityWithImage : Runnable {

    protected lateinit var activityToLaunch: Class<Activity>
    protected lateinit var inputFile: File
    protected lateinit var outputFile: File

    fun params(inputFile: File, outputFile: File, activityToLaunch: Class<Activity>) {
        this.inputFile = inputFile
        this.outputFile = outputFile
        this.activityToLaunch = activityToLaunch
    }

    override fun run() {
    }
}