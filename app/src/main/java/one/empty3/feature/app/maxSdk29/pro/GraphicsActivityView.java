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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import one.empty3.apps.tree.altree.AlgebraicFormulaSyntaxException;
import one.empty3.apps.tree.altree.AlgebricTree;
import one.empty3.apps.tree.altree.TreeNodeEvalException;

public class GraphicsActivityView extends AppCompatActivity {
    String[] s = new String[]{"x", "y", "z", "r", "g", "b", "a", "t"};
    String[] formulas = new String[]{"x", "y", "z", "r", "g", "b", "a", "t"};
    double[] values = new double[]{0, 0, 0, 0, 0, 0, 0, 0};
    AlgebricTree[] algebricTree = new AlgebricTree[8];
    HashMap<String, Double> stringDoubleHashMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics_view);
        for (String cord : s) {
            String o = (String) getIntent().getExtras().get(cord);
        }
        draw();
        stringDoubleHashMap = new HashMap<>();

        for (int i = 0; i < values.length; i++) {
            stringDoubleHashMap.put(s[i], values[i]);
        }

        Button buttonDraw = findViewById(R.id.buttonDraw);
        buttonDraw.setOnClickListener(view -> {
            {
                draw();
            }
        });
    }

    private void draw() {
        ImageView image = findViewById(R.id.application_of_formulas);

        int w = image.getMaxWidth();
        int h = image.getMaxHeight();


        for (int i = 0; i < values.length; i++) {
            AlgebricTree tree = new AlgebricTree(formulas[i], stringDoubleHashMap);
            try {
                tree.construct();
            } catch (AlgebraicFormulaSyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        Bitmap current = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        for (int x = 0; x < w; x++)
            for (int y = 0; y < w; y++) {
                try {
                    algebricTree[0].setParameter("x", (double) x);
                    algebricTree[0].setParameter("y", (double) y);
                    double x2 = algebricTree[0].eval();
                    algebricTree[1].setParameter("y", (double) x);
                    algebricTree[1].setParameter("y", (double) y);
                    double y2 = algebricTree[1].eval();


                    for (int c = 0; c < 4; c++) {
                        algebricTree[c + 2].setParameter(s[c + 2], algebricTree[c + 2].eval());
                    }

                    image.setImageBitmap(current);

                } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
    }
}
