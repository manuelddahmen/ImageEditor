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

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LicenceUserData2 : AppCompatActivity() {
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

        val intent: Intent = Intent(view.context, Calculator::class.java).apply {
        }
        startActivity(intent)
    }


    fun openLink(view: View) {
        val uri: Uri = Uri.parse(getString(R.string.POLICY_URI))
        val intent: Intent = Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}