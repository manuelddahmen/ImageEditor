package one.empty3.feature.app.maxSdk29.pro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivityFragments : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity_fragments)
        if (savedInstanceState == null) {
            //val myCameraActivityVerion5 = MyCameraActivityVerion5();
            //val myCameraActivity = MyCameraActivity();
            //myCameraActivityVerion5.activity2 = myCameraActivity
/*
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, myCameraActivityVerion5)
                //.replace(R.id.container, MainActivityFragmentsFragment.getDeclaredConstructor().newInstance())
                .commitNow()
*/        }
    }
}