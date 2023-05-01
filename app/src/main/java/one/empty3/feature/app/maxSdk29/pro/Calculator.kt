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
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.unit.max
import one.empty3.apps.tree.altree.*
import one.empty3.feature.app.maxSdk29.pro.ChooseFunctionDialogFragment.Companion
import java.io.File
import java.util.*

class Calculator : ActivitySuperClass() {
    private var index: Int = 0
    private var variable: String = ""
    private var text: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout_table)


        val variable = intent.extras?.getString("variable")
        val variableValue: String? = intent.extras?.getString(variable)

        for ((index, s) in cordsConsts.withIndex()) {
            cords[index] = intent.extras?.getString(s)
        }

        title = variable

        maxRes = Utils().getMaxRes(this, savedInstanceState)

        currentFile = Utils().getCurrentFile(intent)

        val buttonsNumbers = arrayListOf(
            R.id.button0,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.dotButton,
            R.id.equalButton,
            R.id.divideButton,
            R.id.multButton,
            R.id.addButton,
            R.id.substractButton,
            R.id.expButton,
            R.id.delButton,
            R.id.buttonParenthesis,
            R.id.buttonParenthesisA
        )

        val textAnswer: TextView = findViewById<EditText>(R.id.answerText)
        val editTextId = findViewById<EditText>(R.id.editTextCalculus)
        editTextId.setText(cords[cords.indexOf(variable)])

        for (j: Int in buttonsNumbers) {
            val findViewById: Button = findViewById(j)
            findViewById.setOnClickListener {
                if (findViewById == findViewById<Button>(R.id.delButton)) {
                    val toString: String = editTextId.text.toString()
                    if (toString.length > 1) {
                        editTextId.setText(toString.substring(0, toString.length - 1))
                    } else if (toString.length == 1) {
                        editTextId.setText("")
                    }
                    return@setOnClickListener
                }

                try {
                    editTextId.text = editTextId.text.append(findViewById.text)
                    val tree = AlgebricTree(editTextId.text.toString())
                    tree.construct()
                    val d: Double = tree.eval()
                    val labelAnswer: String = d.toString()
                    textAnswer.text = labelAnswer

                } catch (ex: AlgebraicFormulaSyntaxException) {
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                } catch (ex: NullPointerException) {
                    ex.printStackTrace()
                }
            }
        }
        val buttonFunctionAdd: Button = findViewById(R.id.buttonFunction)
        buttonFunctionAdd.setOnClickListener {
            val dialog = ChooseFunctionDialogFragment()
            val stringArrayAdapter = StringArrayAdapter()
            dialog.show(
                supportFragmentManager,
                one.empty3.feature.app.maxSdk29.pro.ChooseFunctionDialogFragment.TAG
            )
            Thread {
                run {
                    while (!dialog.isExited) {
                        Thread.sleep(100)
                    }

                    val result: String = dialog.function_name
                    if (result.isNotEmpty())
                        editTextId.text = editTextId.text.append(result)
                }
            }.start()
        }

        val ok = findViewById<Button>(R.id.buttonOk);
        ok.setOnClickListener {
            val intentGraphics = Intent()
            intentGraphics.setClass(applicationContext, GraphicsActivity::class.java)
            if (currentFile != null)
                Utils().addCurrentFileToIntent(intentGraphics, this, currentFile!!)
            intentGraphics.putExtra("maxRes", maxRes)
            var i = 0
            for (s in cordsConsts) {
                if (s.equals(variable)) {
                    intentGraphics.putExtra(s, editTextId.text.toString())
                } else {
                    intentGraphics.putExtra(s, cords[i])
                }
                i++
            }
            startActivity(intentGraphics)
        }

    }
}
