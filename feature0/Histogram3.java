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

<<<<<<<< HEAD:feature0/Histogram3.java
package one.empty3.feature;

import one.empty3.io.ProcessFile;

import one.empty3.ImageIO;
import one.empty3.libs.*;
========
package one.empty3.androidFeature;

import one.empty3.io.ProcessFile;

import one.empty3.library.Lumiere;
import matrix.PixM;
import one.empty3.libs.Color;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram3.java
import one.empty3.libs.Image;

import java.io.File;
import java.util.ArrayList;

import java.util.List;

/*
 * radial density of region (x, y, r)
 *
 */
public class Histogram3 extends ProcessFile {
    public final double rFract = 2.5;

    public static class Circle {
        public double x, y, r;
        public double i;

        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
            i = 0.0;
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


    public void init() {
    }

    public Histogram3() {
        init();

    }

    public void makeHistogram(double r) {

    }

    public Circle getLevel(PixM m, Circle c) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        for (double i = -c.r; i < c.r; i++) {
            for (double j = -c.r; j < c.r; j++) {
                if (Math.sqrt((i) * (i) + (j) * (j)) < c.r * c.r
                        && c.x + i >= 0 && c.y + j >= 0 && c.x + i < m.getColumns() && c.y + j < m.getLines()) {
                    //intensity += m.mean((int) (c.x + i), (int) (c.y + j), (int)(2*c.r), (int)(2*c.r));
                    intensity += m.getIntensity((int) (c.x + i), (int) (c.y + j));
                    count++;
                }
            }
        }

        if (count > 0) {
            c.i = intensity / count;
        } else {
            c.i = 0.0;
            //    c.r = 0;
        }


        return c;
    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }


    public List<Circle> getPointsOfInterest(PixM m, final double rMin0, double iMin) {
        ArrayList<Circle> circles;
        circles = new ArrayList<>();

        // Classer les points par intensité et rayon

        // for(double intensity=1.0; intensity>=0.0; intensity-=0.1) {
        for (int i = 0; i < m.getColumns(); i++) {
            for (int j = 0; j < m.getLines(); j++) {
                Circle level = getLevel(m, new Circle(i, j, rMin0));
                // level.i = intensity;
                //int index = Math.max(((int) (level.i * numLevels)), 0);
                //index = Math.min(numLevels-1, index);
                double iOrigin = level.i;
                int numLevels = 3;
                int index0 = (int) (level.i * (numLevels - 1));
                //if(index0<0) index0 = 0;
                //if(index0<=min.length) index0 = min.length-1;
                while (level.i >= iMin && level.i > iOrigin - 1.0 / numLevels && level.i < iOrigin + 1.0 / numLevels && level.r < Math.max(m.getColumns(), m.getLines()) / 20.) {
                    level.r *= rFract;
                    getLevel(m, level);
                }

                level.r /= rFract;

                if (level.r >= rMin0 && level.r < Math.max(m.getColumns(), m.getLines())) {
                    //level.i = iOrigin;
                    circles.add(level);
                }
            }

            //   }
        }


        return circles;
    }

    public List<List<Circle>> group(List<Circle> circles) {


        return new ArrayList<>();

    }

    public boolean process(File in, File out) {

        init();

<<<<<<<< HEAD:feature0/Histogram3.java
        try {
            PixM m = new PixM(one.empty3.ImageIO.read(in));
            Image image = m.getImage().getBitmap();
========
        PixM m = new PixM(one.empty3.ImageIO.read(in));
        Image image = m.getImage();
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram3.java


        final double radiusIncr = 1;


<<<<<<<< HEAD:feature0/Histogram3.java
            Image img2 = new one.empty3.libs.Color(image.getWidth(), image.getHeight(), one.empty3.libs.Color.TYPE_INT_RGB);
            List<Circle> pointsOfInterest;
            pointsOfInterest = getPointsOfInterest(m, radiusIncr, 0.5);
            // grands;cercles = grandes iles les separer
            // verifier les distances et constantes i
            // petits cercles successifs entoures 
            // de grands ou plus grands cercles = 
            // coins, corners et possibles features.
========
        Image img2 = new Image(image.getWidth(), image.getHeight());
        List<Circle> pointsOfInterest;
        pointsOfInterest = getPointsOfInterest(m, radiusIncr, 0.5);
        // grands;cercles = grandes iles les separer
        // verifier les distances et constantes i
        // petits cercles successifs entoures
        // de grands ou plus grands cercles =
        // coins, corners et possibles features.
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram3.java

        System.out.println("getPointsOfInterest ");


        double[] i_ir = new double[]{0, 0};
        for (int i = 0; i < pointsOfInterest.size(); i++) {
            Circle c1 = pointsOfInterest.get(i);
            //if (c1.r <= 0 || c1.i <= 0)
            //    pointsOfInterest.remove(i);
            if (i_ir[0] < c1.i)
                i_ir[0] = c1.i;
            if (i_ir[1] < c1.i / c1.r)
                i_ir[1] = c1.i / c1.r;

        }
/*            pointsOfInterest.sort((o1, o2) -> {
                double v = (o2.i/o2.r - o1.i/o1.r)/i_ir[1];
                if (v < 0)
                    return -1;
                if (v > 0)
                    return 1;
                return (int) Math.signum((o2.i - o1.i) / Math.abs(o2.r - o1.r));
            });
*/
        System.out.println("draw ");

<<<<<<<< HEAD:feature0/Histogram3.java
            for (int i = 0; i < pointsOfInterest.size(); i++) {
                Circle circle = pointsOfInterest.get(i);
                double v = circle.i / circle.r / i_ir[1];
                android.graphics.Color color = new Color((float) (v), (float) (v), (float) (v));
                img2.setRGB((int) (circle.x), (int) (circle.y), color.toArgb());
            }
            // grouper les points par similarités et distances
========
        for (int i = 0; i < pointsOfInterest.size(); i++) {
            Circle circle = pointsOfInterest.get(i);
            double v = circle.i / circle.r / i_ir[1];
            Color color = new Color(Lumiere.getInt(new double[]{(float) (v), (float) (v), (float) (v)}));
            img2.setRgb((int) (circle.x), (int) (circle.y), color.getRGB());
        }
        // grouper les points par similarités et distances
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram3.java
              /*  group(pointsOfInterest);
                File fileToWrite = new File(directory.getAbsolutePath()
                        + "level" + ".jpg");
                File fileToWrite2 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW.jpg");
                File fileToWrite3 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW_RGB.jpg");
                //fileToWrite.mkdirs();*/
<<<<<<<< HEAD:feature0/Histogram3.java
            one.empty3.ImageIO.write(new PixM(img2).normalize(0., 1.).getImage().getBitmap(), "JPEG", out, shouldOverwrite);
========
        one.empty3.ImageIO.write(new PixM(img2).normalize(0., 1.).getImage().getBitmap(), "JPEG", out, shouldOverwrite);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram3.java
                /*
                one.empty3.ImageIO.write(img, "JPEG", fileToWrite);
                one.empty3.ImageIO.write(img, "JPEG", fileToWrite2);
                one.empty3.ImageIO.write(img, "JPEG", fileToWrite3);
*/


        return true;
    }
}
