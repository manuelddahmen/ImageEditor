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

package one.empty3.feature.app.maxSdk29.pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import java.util.logging.Level;
import java.util.logging.Logger;

@ExperimentalCamera2Interop public class GraphicsActivity extends ActivitySuperClass {
    private TextView[] textViews;
    private Button[] buttons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics);

        Button x = findViewById(R.id.buttonX);
        Button y = findViewById(R.id.buttonY);
        Button z = findViewById(R.id.buttonZ);
        Button r = findViewById(R.id.buttonR);
        Button g = findViewById(R.id.buttonG);
        Button b = findViewById(R.id.buttonB);
        Button a = findViewById(R.id.buttonA);
        Button t = findViewById(R.id.buttonT);
        Button u = findViewById(R.id.buttonU);
        Button v = findViewById(R.id.buttonV);

        TextView textViewX = findViewById(R.id.textViewX);
        TextView textViewY = findViewById(R.id.textViewY);
        TextView textViewZ = findViewById(R.id.textViewZ);
        TextView textViewR = findViewById(R.id.textViewR);
        TextView textViewG = findViewById(R.id.textViewG);
        TextView textViewB = findViewById(R.id.textViewB);
        TextView textViewA = findViewById(R.id.textViewA);
        TextView textViewT = findViewById(R.id.textViewT);
        TextView textViewU = findViewById(R.id.textViewU);
        TextView textViewV = findViewById(R.id.textViewV);

        buttons = new Button[]{x, y, z, r, g, b, a, t, u, v};
        textViews = new TextView[]{textViewX, textViewY, textViewZ, textViewR, textViewG, textViewB, textViewA, textViewT, textViewU, textViewV};
        String cord1 = "x";


        for (int i = 0; i < cords.length; i++) {
            TextView textView = textViews[i];
            Button button = buttons[i];
            String cord = button.getText().toString().toLowerCase();
            if (textView != null) {
                String variable = getIntent().getExtras().getString("variable");

                if (variable != null && variable.equals(cord)) {
                    cord1 = cord;
                    textView.setText(cord);
                    textView.setText((getIntent().getExtras().get(cords[i])) != null ? ((String) getIntent().getExtras().get(cords[i])) : cords[i]);
                } else if (getIntent().getExtras() != null) {
                    textView.setText((getIntent().getExtras().get(cords[i])) != null ? ((String) getIntent().getExtras().get(cords[i])) : cords[i]);
                }
                String finalCord1 = cord1 != null ? cord1 : "?";
                button.setOnClickListener(view -> {

                    Intent calculatorIntent = new Intent();
                    for (int i1 = 0; i1 < textViews.length; i1++) {
                        calculatorIntent.putExtra(cordsConsts[i1], textViews[i1].getText().toString());
                        if (button.getText().toString().equals(cordsConsts[i1])) {
                            calculatorIntent.putExtra("variable", cordsConsts[i1]);
                        }
                    }
                    calculatorIntent.setClass(getApplicationContext(), Calculator.class);
                    new Utils().addCurrentFileToIntent(calculatorIntent, null, currentFile);
                    startActivity(calculatorIntent);
                });
            }
        }
        View buttonView = findViewById(R.id.buttonView);
        String finalCord = cord1;
        buttonView.setOnClickListener(view -> {
            Logger.getAnonymousLogger().log(Level.INFO,
                    "currentFile=" + getClass().toString() + " " + currentFile);
            Intent graphicsIntent = new Intent();
            graphicsIntent.setClass(getApplicationContext(), GraphicsActivityView.class);
            for (int i1 = 0; i1 < textViews.length; i1++) {
                graphicsIntent.putExtra(cords[i1], textViews[i1].getText());
            }
            new Utils().putExtra(graphicsIntent, cords, finalCord, variableName, variable);
            graphicsIntent.putExtra("maxRes", new Utils().getMaxRes(this, savedInstanceState));
            new Utils().addCurrentFileToIntent(graphicsIntent, null, currentFile);
            startActivity(graphicsIntent);
        });

    }
}
