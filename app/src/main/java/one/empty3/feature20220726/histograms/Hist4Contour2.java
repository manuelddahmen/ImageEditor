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

package one.empty3.feature20220726.histograms;
import one.empty3.feature20220726.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.util.Objects;


public class Hist4Contour2 extends ProcessFile {

    private int kMax = 3;
    private double fractMax = 0.05;

    public static class Circle {
        public double x = 0.0, y = 0.0, r = 0.0;
        public double i = 0.0;
        public Point3D maxColor = Point3D.O0;
        public double count = 0.0;

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
                    "n maxColor = p " + maxColor.toString() +
                    '}';
        }
    }



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
        double sum = 0;
        int count = 0;
        c.maxColor = Point3D.O0;
        double intensity = 0.0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.x + c.r < m.getLines()
                        && (i == c.x - c.r || j == c.y - c.r || i == c.x + c.r || j == c.y + c.r)) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                    Point3D p = m.getP((int) i, (int) j);
                    if (p.norme() > 0.3 && p.moins(c.maxColor).norme() > 0.3) {
                        c.maxColor = p;
                        sum++;
                    }
                }
            }
        }
        c.maxColor = c.maxColor.mult(1 / (sum + 1));
        if (count > 0) {
            c.i = intensity;
            c.count = count;
        } else {
            c.i = 0.0;
            // c.r = 1;
        }


        return c;
    }


    @Override
    public boolean process(File in, File out) {
        kMax = 3;
        fractMax = 0.05;
        if (!isImage(in)) {
            return false;
        }
        PixM inP;
        inP = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)), maxRes);




        double max = 0.0;
        PixM outP = new PixM(inP.getColumns(), inP.getLines());
        PixM outP0 = new PixM(inP.getColumns(), inP.getLines());
        double maxR = Math.min(inP.getLines(), inP.getColumns()) * fractMax;
        Circle c = null;
        Point3D maxP = Point3D.O0.mult(1);
        for (int i = 0; i < inP.getColumns(); i++) {
            for (int j = 0; j < inP.getLines(); j++) {
                for (int k = 1; k < maxR; k += 1) {
                    if (outP.getP(i, j).equals(Point3D.O0)) {
                        c = getLevel(new Circle(i, j, k), inP);
                        if (c.i > 0.0) {
                            Point3D n = new Point3D(c.i, c.r, c.count);
                            outP.setP(i, j, n);
                        }
                    }
                }
                Point3D n = outP.getP(i, j);
                if(!n.equals(Point3D.O0)) {
                    for (int l = 0; l < 3; l++) {
                        if (maxP.get(l) < n.get(l)) {
                            maxP.set(l, n.get(l));
                        }
                    }
                }
            }
        }
        for (int i = 0; i < inP.getColumns(); i++) {
            for (int j = 0; j < inP.getLines(); j++) {
                for (int l = 0; l < 3; l++) {
                    outP.setCompNo(l);
                    outP.set(i, j, outP.get(i, j) / maxP.get(l));
                }
            }
        }
        //ImageIO.write(outP.normalize(0, 1).getImage(), "jpg", out);

        //ImageIO.write(outP0.normalize(0, 1).getImage(), "jpg", out);
        return ImageIO.write(outP.getImage().getBitmap(), "jpg", out);

    }


}

