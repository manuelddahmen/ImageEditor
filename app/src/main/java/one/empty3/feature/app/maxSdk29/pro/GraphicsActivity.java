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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GraphicsActivity extends AppCompatActivity {
    private String[] cords;
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
        textViews = new TextView[]{textViewX, textViewY, textViewZ,
                textViewR, textViewG, textViewB, textViewA, textViewT, textViewU, textViewV};
        cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
        for (int i = 0; i < cords.length; i++) {
            TextView textView = textViews[i];
            Button button = buttons[i];
            String cord = button.getText().toString().toLowerCase();
            if (textView != null && button != null) {
                if (getIntent().getExtras() != null) {
                    textView.setText((getIntent().getExtras().get(cords[i])) != null ?
                            ((String) getIntent().getExtras().get(cords[i])) : cords[i]);
                }
                button.setOnClickListener(view -> {

                    Intent calculatorIntent = new Intent(Intent.ACTION_EDIT);
                    calculatorIntent.setClass(getApplicationContext(), Calculator.class);
                    for (String s : cords) {
                        if (calculatorIntent.getExtras() != null) {
                            calculatorIntent.getExtras().putString(cord, textView.getText().toString());
                            if (s.equals(cord)) {
                                calculatorIntent.getExtras().putString("variable", cord);
                            }
                        }
                    }
                    startActivity(calculatorIntent);
                });
            } else {
                textView.setText(cord);
            }
        }

        View buttonView = findViewById(R.id.buttonView);
        buttonView.setOnClickListener(view -> {
            Intent graphicsIntent = new Intent(Intent.ACTION_VIEW);
            graphicsIntent.setClass(getApplicationContext(), GraphicsActivityView.class);
            for (int i = 0; i < cords.length; i++) {
                TextView textView = textViews[i];
                Button button = buttons[i];
                for (String s : cords) {
                    graphicsIntent.putExtra(s, textView.getText().toString());
                }
            }
            startActivity(graphicsIntent);
        });
    }


}
