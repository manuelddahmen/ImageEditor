package one.empty3.feature.app.maxSdk29.pro

import android.os.Bundle


class FaceEffectListActivity : ActivitySuperClass() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.live_effects_recycler_view)


        if (currentFile != null) {
            drawIfBitmap()
        } else {
            restoreInstanceState()
        }



    }
}