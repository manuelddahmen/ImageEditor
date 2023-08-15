package one.empty3.feature.app.maxSdk29.pro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import one.empty3.feature.app.maxSdk29.pro.ui.main.MainActivityFragmentsFragment

class MainActivityFragments : AppCompatActivity() {

    @OptIn(ExperimentalCamera2Interop::class) override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity_fragments)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MyCameraActivityVerion5())
                //.replace(R.id.container, MainActivityFragmentsFragment.newInstance())
                .commitNow()
        }
    }
}