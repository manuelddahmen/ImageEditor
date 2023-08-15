package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.OptIn
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
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
    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)


        return true
    }

    @OptIn(ExperimentalCamera2Interop::class) override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var myMenuIntent : Intent
        return when (item.itemId) {
            R.id.home -> {
                val myCameraActivityVerion5 = MyCameraActivityVerion5()
                myCameraActivityVerion5.activity = MyCameraActivity()
                supportFragmentManager.beginTransaction().replace(R.id.nestedScrollView,
                    myCameraActivityVerion5).commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}
