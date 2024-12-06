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

package one.empty3.feature20220726.lbp;

import one.empty3.feature20220726.matrix.PixM;
import one.empty3.library.Point3D;

import javaAnd.awt.*;

public class OriginalLBP {
    private final matrix.PixM matrix.PixM;
    private final float radius;

    public OriginalLBP(matrix.PixM matrix.PixM, float radius) {
        this.radius = radius;
        this.matrix.PixM = matrix.PixM;
    }

    public void computeCircle() {
        float[] values = new float[1 + (int) (2 * Math.PI * radius)];
        matrix.PixM m = new matrix.PixM(matrix.PixM.getColumns(), matrix.PixM.getLines());
        for (int i = 0; i < matrix.PixM.getColumns(); i++)
            for (int j = 0; j < matrix.PixM.getLines(); j++) {
                values[0] = (float) matrix.PixM.luminance(i, j);
                for (float angle = 0; angle < 2 * Math.PI * radius; angle++) {
                    Point3D p = new Point3D((double) i, (double) j, 0.0).plus(
                            new Point3D(radius * Math.cos(angle), -radius * Math.sin(angle), 0.0));
                    Point point = new Point((int) (double) p.getX(), (int) (double) p.getY());
                    double v = matrix.PixM.luminance((int) point.getX(), (int) point.getY());
                    values[(int) angle + 1] = (float) v;
                }
                long binary = 0;
                for (int k = 1; i < values.length; i++) {
                    if (values[k] > values[0]) {
                        binary += Math.pow(2, i);
                    } else if (values[k] < values[0]) {

                    }
                }
                m.set(i, j, binary);
            }
        m.compCount = 1;
    }
}
