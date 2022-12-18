package one.empty3.feature.app.maxSdk29.pro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ChooseEffectList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_effect_activity)

        val findViewById: RecyclerView = findViewById<RecyclerView>(R.id.recycler_view_effect)
        findViewById.adapter = ProcessFileArrayAdapter()
        findViewById.setOnClickListener(View.OnClickListener {})
    }


}