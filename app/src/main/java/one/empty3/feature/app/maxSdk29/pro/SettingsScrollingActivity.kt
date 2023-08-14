package one.empty3.feature.app.maxSdk29.pro

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import one.empty3.feature.app.maxSdk29.pro.databinding.ActivitySettingsScrollingBinding

class SettingsScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            System.out.println("Clicked on binding.fab")
        }
        val settingsFragment: SettingsFragment = SettingsFragment()
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction();
        ft.add(R.id.nestedScrollView, settingsFragment).commit();
    }
}
