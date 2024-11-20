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

package one.empty3.androidFeature;

import android.graphics.BitmapFactory;

import java.io.File;

import one.empty3.feature.M;
import one.empty3.featureAndroid.PixM;
import one.empty3.io.ProcessFile;

public class BrushGradientMaximumColor extends ProcessFile {
    public BrushGradientMaximumColor() {
        super();
    }

    public int[][] circle = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public int[] values = {1, 0, 0, 0};
    public int[][] turn = {{0, 1, 2, 3}, {1, 2, 3, 0}, {2, 3, 0, 1}, {3, 0, 1, 2}};

    public M filterElem(int x, int y, int size) {
        M filter = new M(size * 2 + 1, size * 2 + 1);
        for (int i = x - size; i <= x + size; i++) {
            for (int j = y - size; j <= y + size; j++) {
                filter.setValues(i, j, distance(i, j, i - x, j - y));
            }
        }
        return filter;
    }

    private double distance(int x, int y, int i, int i1) {
        return Math.sqrt((x - i) * (x - i) + (y - i1) * (y - i1));
    }

    @Override
    public boolean process(File in, File out) {
        one.empty3.featureAndroid.PixM pixM = new one.empty3.featureAndroid.PixM(BitmapFactory.decodeFile(in.getAbsolutePath()));
        one.empty3.featureAndroid.PixM pixM2 = new PixM(pixM.getColumns(), pixM.getLines());
        for (int i = 0; i < pixM.getLines(); i++) {
            for (int j = 0; j < pixM.getColumns(); j++) {
                for (int i2 = -1; i2 <= 1; i2++)
                    for (int j2 = -1; j2 <= 1; j2++) {
                        int x = i + i2;
                        int y = j + j2;

                        M f = filterElem(x, y, 1);
                        double fd = f.determinant();
                        pixM2.setValues(x, y, fd, fd, fd);

                    }
            }
        }
        one.empty3.ImageIO.write(pixM2.normalize(0, 1).getBitmap().getImage(), "jpg", out);
        return true;
    }
}