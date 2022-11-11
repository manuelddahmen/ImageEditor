package one.empty3.feature.app.maxSdk29.pro

import android.app.Activity
import java.io.File

class ThreadOpenActivityWithImage : Runnable {

    private lateinit var activity: Class<Activity>
    private lateinit var file: File

    fun params(f: File, activity: Class<Activity>) {
        this.file = f
        this.activity = activity
    }

    override fun run() {
    }
}