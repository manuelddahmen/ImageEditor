/*
 * Copyright (c) 2023.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LicenceUserData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_licence_user_data)
        findViewById<Button>(R.id.buttonBack).setOnClickListener {
            backToMain(it)
        }
        findViewById<Button>(R.id.AboutButton).setOnClickListener {
            openLink(it)
        }
    }

    private fun backToMain(view: View) {

        val intent: Intent = Intent(view.context, MyCameraActivity::class.java).apply {
        }
        startActivity(intent)
    }


    fun openLink(view: View) {
        val uri: Uri = Uri.parse(getString(R.string.POLICY_URI))
        val intent: Intent = Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}