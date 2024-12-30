/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import matrix.PixM;
import one.empty3.libs.Image;
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library1.tree.AlgebraicTree;
import one.empty3.library1.tree.TreeNodeEvalException;

public class GraphicsActivityView extends ActivitySuperClass {
    final double[] values = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    final AlgebraicTree[] AlgebraicTree = new AlgebraicTree[cords.length];
    HashMap<String, Double> stringDoubleHashMap;
    private matrix.PixM current;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParameters(getIntent());

        setContentView(R.layout.graphics_view);

        stringDoubleHashMap = new HashMap<>();

        for (int i = 0; i < cordsConsts.length; i++) {
            stringDoubleHashMap.put(cordsConsts[i], values[i]);
        }


        Button buttonDraw = findViewById(R.id.buttonDraw);
        buttonDraw.setOnClickListener(view -> {
            {
                draw();
            }
        });
/*
        Button back = findViewById(R.id.buttonMainActivity);
        back.setOnClickListener(view -> {
            Intent intentGraphics = new Intent(getApplicationContext(), MyCameraActivity.class);
            passParameters(intentGraphics);

        });

*/
        printValues();
        Logger.getAnonymousLogger().log(Level.INFO,
                "currentFile=" + getClass().toString() + " " + currentFile);
    }

    private void printValues() {

        if(stringDoubleHashMap!=null) {
            stringDoubleHashMap.forEach((s, aDouble) -> System.out.println(s + ":=" + aDouble));
            for (int i = 0; i < cords.length; i++) {
                System.out.println(cordsConsts[i] + ":=" + cords[i]);
            }
        }
    }


    private void draw() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_EXTERNAL_STORAGE},
                38203820);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(stringDoubleHashMap==null) {
            stringDoubleHashMap = new HashMap<>();
        }

        Logger.getAnonymousLogger().log(Level.INFO,
                "currentFile=" + getClass().toString() + " " + currentFile);


        ImageViewSelection image = findViewById(R.id.currentImageView);

        int w = getMaxRes();
        int h = getMaxRes();

        current = null;

        if (currentFile.getCurrentFile() != null) {
            File currentFile1 = currentFile.getCurrentFile();
            try {
                Image read = one.empty3.ImageIO.read(currentFile1);
                if (getMaxRes() > 0 && read!=null) {
                    current = PixM.getPixM(Objects.requireNonNull(read).getImage(), getMaxRes());
                } else if(read!=null){
                    current = new PixM(Objects.requireNonNull(read));
                }
            } catch (NullPointerException ignored) {

            }
        }
        if (current == null) {
            if (getMaxRes() <= 0) {
                current = new PixM(getMaxRes(), getMaxRes());
            } else {
                current = new PixM(w, h);
            }
        }
        w = current.getColumns();
        h = current.getLines();

        for (int i = 0; i < cordsConsts.length; i++) {
            AlgebraicTree[i] = new AlgebraicTree(cords[i], stringDoubleHashMap);
            try {
                AlgebraicTree[i].construct();
            } catch (AlgebraicFormulaSyntaxException e) {
                printValues();
                e.printStackTrace();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        double[] rgba = new double[4];

        double t = 0;
        int progress = 0;
        try {
            for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                    double[] v = current.getValues(x, y);
                    for (int j = 0; j < AlgebraicTree.length; j++) {
                        AlgebraicTree[j].setParameter("r", v[0]);
                        AlgebraicTree[j].setParameter("g", v[1]);
                        AlgebraicTree[j].setParameter("b", v[2]);
                        AlgebraicTree[j].setParameter("u", 1.0 * x / w);
                        AlgebraicTree[j].setParameter("v", 1.0 * y / h);
                        AlgebraicTree[j].setParameter("w", (double) w);
                        AlgebraicTree[j].setParameter("h", (double) h);
                        AlgebraicTree[j].setParameter("x", (double) x);
                        AlgebraicTree[j].setParameter("y", (double) y);
                        AlgebraicTree[j].setParameter("z", (double) 0.0);
                        AlgebraicTree[j].setParameter("t", (double) t);
                        AlgebraicTree[j].setParameter("a", 0.0);
                    }

                    double x2 = AlgebraicTree[0].eval().getElem();
                    double y2 = AlgebraicTree[1].eval().getElem();

                    for (int c = 0; c < 4; c++) {
                        rgba[c] = AlgebraicTree[c + 3].eval().getElem();
                    }
                    double[] finalColors = new double[3];
                    for (int i = 0; i < 3; i++) {
                        finalColors[i] = v[i] * rgba[3] + rgba[i] * (1 - rgba[3]);
                    }
                    current.setValues((int) Math.round(x2), (int) Math.round(y2),
                            finalColors[0], finalColors[1], finalColors[2]);

            }
            progress = (int) (100. * y / h);
        }
        progress = 100;

            if(current!=null) {
                Bitmap bitmap = current.normalize(0, 1).getBitmap().getBitmap();
                File graphics_math = new Utils().writePhoto(this, new Image(bitmap), "graphics_math");
                this.currentFile.add(new DataApp(graphics_math));
                new Utils().setImageView(image, bitmap);
            }
        } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException|RuntimeException e) {
            //printValues();
            //throw new RuntimeException(e);
        }
    }
}
