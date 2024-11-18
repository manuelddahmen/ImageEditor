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

package one.empty3.feature;

import one.empty3.library.Point3D;

/*
 Le pinceau manchot.
 **/
public class BrushGradient extends FilterPixM {
    double angle = 0.0;
    double length = 1.0;
    double large = 1.0;
    PixM m;

    public BrushGradient(PixM p) {
        super(p.getColumns(), p.getLines());
        this.m = p;
    }

    public void setData(double angle, double
            length, double large) {
        this.angle = angle;
        this.length = length;
        this.large = large;

    }

    public double filter(double x,
                         double y) {
        double i, j;
        double a = 1.0, b = 1.0, c = 0.0, d = 0.0, e = 1.0, g = 1.0, h = 1.0;
        d = angle;
        a = 1 / Math.PI / length;
        h = 1 / Math.PI / large;
        double f = 0.0;
        double val = 15.0;
        for (i = -val; i < val; i++) {
            for (j = -val; j < val; j++) {
                f += new Point3D(
                        c + b * Math.cos(a * Math.atan(j / i) + d),
                        c + b * Math.cos(h * Math.atan(j / i) + d),
                        0.0
                ).mult(
                        m.get((int) (x + i), (int) (y + j)) *

                                e * Math.sqrt(i * i + j * j)

                                / (i * i + j * j + 1.0) *
                                Math.exp(-(i * i + j * j) * g)
                ).dot(new Point3D(i, j, 0.0).norme1());
            }
        }
        return f / val / val;
    }
} 
