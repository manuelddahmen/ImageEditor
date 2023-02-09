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
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import one.empty3.feature20220726.PixM;
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.raytracer.tree.AlgebricTree;
import one.empty3.library.core.raytracer.tree.TreeNodeEvalException;

public class GraphicsActivityView extends AppCompatActivity {
    final String[] cord = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    final String[] formulas = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    final double[] values = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    final AlgebricTree[] algebricTree = new AlgebricTree[cord.length];
    HashMap<String, Double> stringDoubleHashMap;
    private int maxRes = 300;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics_view);

        stringDoubleHashMap = new HashMap<>();

        for (int i = 0; i < cord.length; i++) {
            formulas[i] = getIntent().getStringExtra(cord[i]);
            stringDoubleHashMap.put(cord[i], values[i]);
        }


        Button buttonDraw = findViewById(R.id.buttonDraw);
        buttonDraw.setOnClickListener(view -> {
            {
                draw();
            }
        });

        Button back = findViewById(R.id.buttonBack2);
        back.setOnClickListener(view -> {
            Intent intentGraphics = new Intent(Intent.ACTION_EDIT);
            intentGraphics.setClass(getApplicationContext(), GraphicsActivity.class);
            int j = 0;
            for (j = 0; j < cord.length; j++) {
                if (intentGraphics.getExtras() != null) {
                    intentGraphics.putExtra(cord[j], formulas[j]);
                }
            }
            startActivity(intentGraphics);

        });
        printValues();
    }

    private void printValues() {

        stringDoubleHashMap.forEach((s, aDouble) -> System.out.println(s + ":=" + aDouble));
        for (int i = 0; i < formulas.length; i++) {
            System.out.println(cord[i] + ":=" + formulas[i]);
        }

    }

    private void draw() {
        ImageView image = findViewById(R.id.application_of_formulas);

        int w = getMaxRes();
        int h = getMaxRes();


        for (int i = 0; i < values.length; i++) {
            algebricTree[i] = new AlgebricTree(formulas[i], stringDoubleHashMap);
            try {
                algebricTree[i].construct();
            } catch (AlgebraicFormulaSyntaxException e) {
                printValues();
                throw new RuntimeException(e);
            }
        }
        double[] rgba = new double[4];

        PixM current = new PixM(w, h);
        double t = 0;
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++) {
                try {
                    for (int j = 0; j < algebricTree.length; j++) {
                        algebricTree[j].setParameter("x", (double) x);
                        algebricTree[j].setParameter("y", (double) y);
                        algebricTree[j].setParameter("t", (double) t);
                    }
                    for (int c = 0; c < 4; c++) {
                        algebricTree[c + 2].setParameter(cord[c + 2], algebricTree[c + 2].eval());
                    }

                    double x2 = algebricTree[0].eval();
                    double y2 = algebricTree[1].eval();
                    for (int c = 0; c < 4; c++) {
                        rgba[c] = algebricTree[c + 2].eval();
                    }
                    current.setValues((int) x2, (int) y2, rgba[0], rgba[1], rgba[2]);

                } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
                    //printValues();
                    //throw new RuntimeException(e);
                }
            }
        image.setImageBitmap(current.normalize(0, 1).getBitmap());
    }

    private int getMaxRes() {
        return maxRes;
    }
}
