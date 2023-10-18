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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import java.util.logging.Level;
import java.util.logging.Logger;

;


public class GraphicsActivity extends ActivitySuperClass {
    private TextView[] textViews;

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

        Button[] buttons = new Button[]{x, y, z, r, g, b, a, t, u, v};
        textViews = new TextView[]{textViewX, textViewY, textViewZ, textViewR, textViewG, textViewB, textViewA, textViewT, textViewU, textViewV};

         SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        String variableName1 = "";
        if(getIntent().hasExtra("variableName")) {
             variableName1 = getIntent().getExtras().getString("variableName");
         }
        for (int i = 0; i < cords.length; i++) {
            if(variableName1.equals(cordsConsts[i])) {
                if(getIntent().hasExtra("variable")) {
                    cords[i] = getIntent().getExtras().getString("variable");
                    textViews[i].setText(cords[i]);
                }
            } else {
                String string = prefs.getString("autoSave" + cords[i], cordsConsts[i]);
                textViews[i].setText(string);
            }
         }

        for (int k1 = 0; k1 < cordsConsts.length; k1++) {

            Button value = buttons[k1];

            TextView textValue = textViews[k1];

            value.setOnClickListener(view -> {

                variable = textValue.getText().toString();
                variableName = value.getText().toString();

                for (int j = 0; j < cords.length; j++) {
                    cords[j] = textViews[j].getText().toString();
                }

                Intent calculatorIntent = new Intent(getApplicationContext(), Calculator.class);

                passParameters(calculatorIntent);

            });
            int finalK = k1;
            textValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            prefs.edit().putString("autoSave"+cordsConsts[finalK], charSequence.toString()).apply();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        View buttonView = findViewById(R.id.buttonView);
        buttonView.setOnClickListener(view -> {
            Logger.getAnonymousLogger().log(Level.INFO,
                    "currentFile=" + getClass().toString() + " " + currentFile);
            Intent graphicsViewIntent = new Intent(getApplicationContext(), GraphicsActivityView.class);
            for (int i1 = 0; i1 < textViews.length; i1++) {
                cords[i1] = textViews[i1].getText().toString();
            }
            passParameters(graphicsViewIntent);
        });
        Button buttonMain = findViewById(R.id.buttonBack);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.getAnonymousLogger().log(Level.INFO,
                        "currentFile=" + getClass().toString() + " " + currentFile);
                Intent mainIntent = new Intent(getApplicationContext(), MyCameraActivity.class);
                for (int i1 = 0; i1 < textViews.length; i1++) {
                    cords[i1] = textViews[i1].getText().toString();
                }
                passParameters(mainIntent);
            }
        });
    }
}
