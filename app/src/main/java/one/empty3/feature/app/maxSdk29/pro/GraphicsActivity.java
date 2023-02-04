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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GraphicsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics);

        Button x = findViewById(R.id.buttonX);
        Button y = findViewById(R.id.buttonY);
        Button z = findViewById(R.id.buttonZ);

        TextView textViewX = findViewById(R.id.textViewX);
        TextView textViewY = findViewById(R.id.textViewY);
        TextView textViewZ = findViewById(R.id.textViewZ);

        Button[] buttons = new Button[]{x, y, z};
        TextView[] textViews = new TextView[]{textViewX, textViewY, textViewZ};
        String[] cords = new String[]{"x", "y", "z"};
        for (int i = 0; i < 3; i++) {
            TextView textView = textViews[i];
            Button button = buttons[i];

            if (getIntent().getExtras() != null) {
                textView.setText(((String) getIntent().getExtras().get(cords[i])) != null ?
                        ((String) getIntent().getExtras().get(cords[i])) : "");
            }
            button.setOnClickListener(view -> {
                String cord = button.getText().toString().toLowerCase();

                Intent calculatorIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                calculatorIntent.setClass(getApplicationContext(), Calculator.class);
                calculatorIntent.getExtras().putString(cord, textView.getText().toString());

                startActivity(calculatorIntent);
            });
        }
    }
}
