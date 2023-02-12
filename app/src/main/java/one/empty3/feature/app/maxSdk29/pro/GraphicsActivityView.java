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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.HashMap;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.apps.tree.altree.*;
import one.empty3.feature20220726.PixM;

public class GraphicsActivityView extends AppCompatActivity {
    final String[] cord = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    final String[] formulas = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    final double[] values = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    final AlgebricTree[] algebricTree = new AlgebricTree[cord.length];
    HashMap<String, Double> stringDoubleHashMap;
    private int maxRes = 300;
    private File currentFile;
    private PixM current;
    private int MAX_RES = maxRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics_view);

        maxRes = new Utils().getMaxRes(this, savedInstanceState);


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
            if (currentFile != null)
                intentGraphics.setDataAndType(Uri.fromFile(currentFile), "image/jpg");

            intentGraphics.setClass(getApplicationContext(), GraphicsActivity.class);
            intentGraphics.putExtra("maxRes", maxRes);
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

        current = null;

        if (currentFile != null) {
            if (getMaxRes() > 0) {
                current = PixM.getPixM(ImageIO.read(currentFile), getMaxRes());
            } else {
                current = new PixM(ImageIO.read(currentFile));
            }
        }
        if (current == null) {
            if (getMaxRes() <= 0) {
                current = new PixM(MAX_RES, MAX_RES);
            } else {
                current = new PixM(w, h);
            }
        }
        w = current.getColumns();
        h = current.getLines();

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

        double t = 0;
        int progress = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                try {
                    for (int j = 0; j < algebricTree.length; j++) {
                        double[] v = current.getValues(x, y);
                        algebricTree[j].setParameter("r", v[0]);
                        algebricTree[j].setParameter("g", v[1]);
                        algebricTree[j].setParameter("b", v[2]);
                        algebricTree[j].setParameter("u", 1.0 * x / w);
                        algebricTree[j].setParameter("v", 1.0 * y / h);
                        algebricTree[j].setParameter("w", (double) w);
                        algebricTree[j].setParameter("h", (double) h);
                        algebricTree[j].setParameter("x", (double) x);
                        algebricTree[j].setParameter("y", (double) y);
                        algebricTree[j].setParameter("t", (double) t);
                        algebricTree[j].setParameter("a", 1.0);
                    }

                    for (int j = 0; j < algebricTree.length; j++) {
                        algebricTree[j].setParameter(cord[j], algebricTree[j].eval());
                    }

                    double x2 = algebricTree[0].eval();
                    double y2 = algebricTree[1].eval();

                    for (int c = 0; c < 4; c++) {
                        rgba[c] = algebricTree[c + 3].eval();
                    }
                    current.setValues((int) Math.round(x2), (int) Math.round(y2),
                            rgba[0], rgba[1], rgba[2]);

                } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
                    //printValues();
                    //throw new RuntimeException(e);
                }
            }
            progress = (int) (100. * y / h);
        }
        progress = 100;

        Bitmap bitmap = current.normalize(0, 1).getBitmap();
        File graphics_math = new Utils().writePhoto(this, bitmap, "graphics_math");
        this.currentFile = graphics_math;
        image.setImageBitmap(bitmap);
    }

    private int getMaxRes() {
        return maxRes;
    }
}
