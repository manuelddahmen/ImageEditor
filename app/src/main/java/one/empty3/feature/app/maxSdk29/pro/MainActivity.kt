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

package one.empty3.feature.app.maxSdk29.pro

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import one.empty3.feature.app.maxSdk29.pro.databinding.ActivityMain2Binding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var currentBitmap: File
    lateinit var currentFile: File
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main2)


        setSupportActionBar(findViewById(R.id.toolbar))

/*        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
*/
        val onClickListener: View = findViewById<View>(R.id.fab)

        val subs: ArrayList<View> = ArrayList<View>();
        subs.add(TextView(onClickListener.context))//
        subs.add(TextView(onClickListener.context))//

        (subs[0] as TextView).text = getString(R.string.add_picture_file_from_directory)
        (subs[1] as TextView).text = getString(R.string.add_picture_from_camera)
        onClickListener.setOnClickListener { view ->
            Snackbar.make(view, "Add a new picture ? ", Snackbar.LENGTH_LONG)
                .setAction("Add from directory or camera?", View.OnClickListener {
                    onClickListener.run {
                        onClickListener.addFocusables(
                            subs, onClickListener.textDirection
                        )
                    }

                })
                .show()
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.button_bar_open_save_share, ImagePreviewFragment()).commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.action_bar_container, ActionBarFragment()).commit()


        val currentActionFragment: Fragment = StartActivityFragment()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.currentFragmentViews, currentActionFragment
            ).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}