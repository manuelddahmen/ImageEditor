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

import static one.empty3.feature.app.maxSdk29.pro.MyCameraActivity.MAX_RES_DEFAULT;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;

public class GraphicsActivity extends AppCompatActivity {
    private String[] cords;
    private TextView[] textViews;
    private Button[] buttons;
    private File currentFile;
    private int maxRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics);


        if ((savedInstanceState == null) || savedInstanceState.getInt("maxRes") <= 0) {
            maxRes = MAX_RES_DEFAULT;
        } else {
            maxRes = savedInstanceState.getInt("maxRes");
        }

        Intent intent = getIntent();
        if (getIntent() != null && getIntent().getData() != null) {
            if (intent.getData() != null) {
                Uri data = intent.getData();
                currentFile = new File(data.getPath());
                System.err.println("File returned from effects' list = " + data);

                if (currentFile != null) {
                    BufferedImage bi = ImageIO.read(currentFile);
                    if (bi != null) {
                        Bitmap bitmap = bi.getBitmap();
                        if (bitmap != null) {
                        }
                    }
                }
            }
        } else {
            System.err.println("intent data Main==null");
        }


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

/*
        String imageStr = getIntent().getStringExtra("currentFile");
        if (imageStr != null)
            currentFile = new File(imageStr);
  */
        buttons = new Button[]{x, y, z, r, g, b, a, t, u, v};
        textViews = new TextView[]{textViewX, textViewY, textViewZ, textViewR, textViewG, textViewB, textViewA, textViewT, textViewU, textViewV};
        cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
        for (int i = 0; i < cords.length; i++) {
            TextView textView = textViews[i];
            Button button = buttons[i];
            String cord = button.getText().toString().toLowerCase();
            if (textView != null && button != null) {
                if (getIntent().getExtras() != null) {
                    textView.setText((getIntent().getExtras().get(cords[i])) != null ? ((String) getIntent().getExtras().get(cords[i])) : cords[i]);
                }
                button.setOnClickListener(view -> {

                    Intent calculatorIntent = new Intent(Intent.ACTION_EDIT);
                    calculatorIntent.setClass(getApplicationContext(), Calculator.class);
                    for (String s : cords) {
                        calculatorIntent.putExtra(s, textView.getText().toString());
                        if (s.equals(cord)) {
                            calculatorIntent.putExtra("variable", cord);
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
                graphicsIntent.putExtra(cords[i], textView.getText().toString());
            }
            if (currentFile != null)
                graphicsIntent.putExtra("currentFile", currentFile);
            startActivity(graphicsIntent);
        });

        Button back = findViewById(R.id.buttonBack);
        back.setOnClickListener(view -> {
            Intent mainActivity = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            if (currentFile != null)
                mainActivity.setDataAndType(Uri.fromFile(currentFile), "image/jpg");
        });

    }


}
