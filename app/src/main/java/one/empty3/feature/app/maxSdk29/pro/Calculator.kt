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
import one.empty3.apps.tree.altree.*
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException
import one.empty3.library.core.raytracer.tree.AlgebricTree
import java.util.*

class Calculator : AppCompatActivity() {
    private var index: Int = 0
    private var variable: String = ""
    private var text: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        val variable = this.intent.extras?.getString("variable")
        val variableValue: String? = this.intent.extras?.getString(variable)
        val cords = arrayOf<String>("x", "y", "z", "r", "g", "b", "a", "t")
        val formula = arrayOfNulls<String>(8)

        var i = 0
        for (s in cords) {
            formula[i] = intent.extras?.getString(s)
            i += 1
        }


        title = variable

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout_table)
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

        for (j: Int in buttonsNumbers) {
            val findViewById: Button = findViewById(j)
            findViewById.setOnClickListener {
                if (findViewById == findViewById<Button>(R.id.delButton)) {
                    val toString: String = editTextId.text.toString()
                    if (toString.length > 1) {
                        editTextId.setText(editTextId.text.substring(0, toString.length - 1))
                    } else if (toString.length == 1) {
                        editTextId.setText("")
                    }
                    return@setOnClickListener
                }
                editTextId.text = editTextId.text.append(findViewById.text)
                val tree = AlgebricTree(editTextId.text.toString())

                try {
                    tree.construct()
                    val d: Double = tree.eval()
                    val labelAnswer: String = d.toString()
                    textAnswer.text = labelAnswer
                    Toast.makeText(applicationContext, "Valide V", Toast.LENGTH_LONG).show()

                } catch (ex: AlgebraicFormulaSyntaxException) {
                    //Toast.makeText(getApplicationContext(), "Syntaxe invalide", Toast.LENGTH_SHORT).show()
                } catch (ex: IndexOutOfBoundsException) {
                    //Toast.makeText(getApplicationContext(), "Erreur autre (array index)", Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                } catch (ex: NullPointerException) {
                    //Toast.makeText(getApplicationContext(), "Erreur : null", Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                }
            }
        }
        val buttonFunctionAdd: Button = findViewById(R.id.buttonFunction)
        buttonFunctionAdd.setOnClickListener {
//            val stringFragment : StringFragment= StringFragment()
//                val ft: FragmentTransaction = supportFragmentManager.beginTransaction();
//                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            val dialog = ChooseFunctionDialogFragment()
            val stringArrayAdapter = StringArrayAdapter()
            dialog.show(
                supportFragmentManager,
                "one.empty3.feature.app.maxSdk29.pro.ChooseFunctionDialogFragment"
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


        editTextId.setText(variableValue)

        val back = findViewById<Button>(R.id.buttonBak);
        back.setOnClickListener {
            val intentBack = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intentBack.setClass(applicationContext, GraphicsActivity::class.java)
            for (s in cords) {
                intentBack.putExtra(s, formula[i])
            }
            startActivity(intentBack)
        };
        val ok = findViewById<Button>(R.id.buttonOk);
        ok.setOnClickListener {
            val intentGraphics = Intent(Intent.ACTION_EDIT)

            for (s in cords) {
                intentGraphics.putExtra(s, formula[i])
                if (s.equals(variable)) {
                    intentGraphics.putExtra(s, formula[i])
                }
            }
            startActivity(intentGraphics)
        }

    }
}
