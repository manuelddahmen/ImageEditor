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

<<<<<<<< HEAD:feature0/histograms/Histogram.java
package one.empty3.feature.histograms;

import one.empty3.feature.PixM;
========
package one.empty3.androidFeature.histograms;

>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/histograms/Histogram.java
import one.empty3.io.ProcessFile;
import matrix.PixM;
import one.empty3.library.Point3D;

import one.empty3.ImageIO;

import java.io.File;
import java.util.Objects;

/*
 * radial density of region (x, y, r)
 * by mean or mean square or somewhat else.
 */
public class Histogram extends ProcessFile {

    private int kMax = 3;
    private double fractMax = 0.4;

    public class Circle {
        public double x, y, r;
        public double i;

        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    ", i=" + i +
                    '}';
        }
    }

    //private final int[][][] levels;


    public void makeHistogram(double r) {

    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }

    public Circle getLevel(Circle c, PixM m) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (Math.sqrt((i - c.x) * (i - c.x) + (j - c.y) * (j - c.y)) <= c.r
                        && c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.x + c.r < m.getLines()) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                }
            }
        }

        if (count > 0)
            c.i = intensity / count;
        else {
            c.i = 0.0;
            c.r = 1;
        }


        return c;
    }


    @Override
    public boolean process(File in, File out) {
        if (isImage(in))
            return false;
        PixM inP;
        inP = PixM.getPixM(Objects.requireNonNull(one.empty3.ImageIO.read(in)), maxRes);
        PixM outP = inP.copy();
        double maxR = Math.min(inP.getLines(), inP.getColumns()) / 5.;
        for (int i = 0; i < inP.getColumns(); i++) {
            for (int j = 0; j < inP.getLines(); j++) {
                double maxIJ = 0.0;
                int rIJ = 3;
                for (int k = 3; k < maxR * fractMax; k += this.kMax) {
                    Circle c = getLevel(new Circle(i, j, k), inP);
                    double candidateI = c.i;
                    double candidateR = c.r;
                    if (candidateI > maxIJ) {
                        maxIJ = candidateI;
                        rIJ = k;
                    }
                }
                outP.setP(i, j, Point3D.n(maxIJ, maxIJ, maxIJ).mult(maxIJ * maxR / rIJ));
            }
        }


<<<<<<<< HEAD:feature0/histograms/Histogram.java
        try {
            one.empty3.ImageIO.write(outP.normalize(0, 1).getImage().getBitmap(), "jpg", out, shouldOverwrite);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
========
        ImageIO.write(outP.normalize(0, 1).getImage().getBitmap(), "jpg", out, shouldOverwrite);
        return true;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/histograms/Histogram.java

    }

}
