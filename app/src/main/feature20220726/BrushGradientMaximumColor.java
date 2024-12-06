/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;

import java.io.File;
import java.io.IOException;

import one.empty3.libs.Image;
import javaAnd.awt.image.imageio.ImageIO;
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
        matrix.PixM matrix.PixM = new matrix.PixM(new Image(in));
        matrix.PixM matrix.PixM2 = new matrix.PixM(matrix.PixM.getColumns(), matrix.PixM.getLines());
        for (int i = 0; i < matrix.PixM.getLines(); i++) {
            for (int j = 0; j < matrix.PixM.getColumns(); j++) {
                for (int i2 = -1; i2 <= 1; i2++)
                    for (int j2 = -1; j2 <= 1; j2++) {
                        int x = i + i2;
                        int y = j + j2;

                        M f = filterElem(x, y, 1);
                        double fd = f.determinant();
                        matrix.PixM2.setValues(x, y, fd, fd, fd);

                    }
            }
        }
        new Image(1,1,4).saveToFile(matrix.PixM2.normalize(0, 1).getImage(), "jpg", out);
        return true;
    }
}