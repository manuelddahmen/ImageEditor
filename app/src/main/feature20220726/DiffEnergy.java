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

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.libs.Image;
import one.empty3.libs.Image;

import java.io.*;
import java.util.Objects;

public class DiffEnergy extends ProcessFile {
    static PrintWriter pw;
    double[] energy = new double[3];
    private PixM i2;
    private PixM i1;

    public DiffEnergy() {
        super();
    }

    public void setPixMS(int img1, int img2) {
        i1 = PixM.getPixM(Objects.requireNonNull(new Image(getStackItem(img1))), maxRes);
        i2 = PixM.getPixM(Objects.requireNonNull(new Image(getStackItem(img2))), maxRes);


    }

    public double diffEnergy() {
        energy = new double[]{0.0, 0.0, 0.0};
        for (int i = 0; i < i1.getColumns(); i++)
            for (int j = 0; j < i1.getLines(); j++)
                for (int c = 0; c < 3; c++) {
                    i1.setCompNo(c);
                    i2.setCompNo(c);
                    energy[c] += Math.abs(
                            i1.get((int) i, (int) j) - i2.get((int) i, (int) j)
                    );
                }
        return energy[0] + energy[1] + energy[2];
    }

    @Override
    public boolean process(File in, File out) {
        return false;
    }
}
