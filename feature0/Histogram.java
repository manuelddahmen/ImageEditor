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

<<<<<<<< HEAD:feature0/Histogram.java
package one.empty3.feature;

import one.empty3.ImageIO;
========
package one.empty3.androidFeature;

import one.empty3.ImageIO;
import one.empty3.featureAndroid.PixM;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram.java
import one.empty3.libs.*;
import one.empty3.libs.Image;

import java.io.File;
import java.util.*;
import java.util.List;

/*
 * radial density of region (x, y, r)
 * by mean or mean square or somewhat else.
 */
public class Histogram {
    static boolean shouldOverwrite = true;
    private final double diffLevel;
    private final double radiusIncr;
    private double min;
    private List<Circle> circles
            = new ArrayList<>();
    private one.empty3.featureAndroid.PixM m = null;
    private double levelMin;

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


    public Histogram(one.empty3.featureAndroid.PixM image, int levels, double min, double radiusIncr, double levelMin) {
        this.diffLevel = 1.0 / levels;
        this.min = min;
        //this.levels = new int[levels][image.getColumns()][image.getLines()];
        this.radiusIncr = radiusIncr;
        this.m = image;
        this.levelMin = levelMin;
    }

    public void makeHistogram(double r) {

    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }

    public Circle getLevel(Circle c) {
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

    public List<Circle> getPointsOfInterest(double heightMaxLevelI) {

        circles = new ArrayList<>();

        // gradient radial ???
        // X-x2 > li-li+-1
        // i(x2, y2, r2) > i(x, y, r) + leveldiffi|||
        // stop
        for (int i = 0; i < m.getColumns(); i++)
            for (int j = 0; j < m.getLines(); j++) {
                double r = radiusIncr;
                double diffI = 0;
                Circle c1 = null, c2;
                int iterates = 0;
                while (r < m.getColumns() && diffI < heightMaxLevelI) {
                    c1 = new Circle(i, j, r);
                    c2 = new Circle(i, j, r + radiusIncr);
                    diffI = Math.abs(getLevel(c1).i - getLevel(c2).i);
                    if (getLevel(c1).i < diffLevel) break;
                    c1 = c2;
                    r += radiusIncr;
                    iterates++;
                }
                if (iterates > 0 && c1.i > 0.0) {
                    circles.add(c1);
                }
            }
        return circles;
    }

    public static void testCircleSelect(Image file, File directory, int levels, double min, double radiusIncr) {
        for (int i = 0; i < levels; i++) {
<<<<<<<< HEAD:feature0/Histogram.java
            try {
                Image img = file;
                Image img2 = new one.empty3.libs.Color(img.getWidth(), img.getHeight(), one.empty3.libs.Color.TYPE_INT_RGB);
                Image img3 = new one.empty3.libs.Color(img.getWidth(), img.getHeight(), one.empty3.libs.Color.TYPE_INT_RGB);
                Histogram histogram = new Histogram(new PixM(img), levels, min, radiusIncr, 0.1);
                int finalI = i;
                List<Circle> pointsOfInterest = histogram.getPointsOfInterest(0.1);
                pointsOfInterest.stream().forEach(circle -> {
                    if (circle.i >= min /*<histogram.diffLevel* finalI*/) {
                        img.drawOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (circle.r * 2), (int) (circle.r * 2));
                        android.graphics.Color color = new Color((float) circle.i, 0f, (float) (circle.i / circle.r));
                        img3.drawOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (circle.r * 2), (int) (circle.r * 2));
                        img3.setRGB((int) (circle.x), (int) (circle.y), color.toArgb());
                    }
                });
                pointsOfInterest.sort(new Comparator<Circle>() {
                    @Override
                    public int compare(Circle o1, Circle o2) {
                        double v = o1.y - o1.y;
                        if (v < 0)
========
            Image img = file;
            Image img2 = new Image(img.getWidth(), img.getHeight());
            Image img3 = new Image(img.getWidth(), img.getHeight());
            Histogram histogram = new Histogram(new PixM(img), levels, min, radiusIncr, 0.1);
            int finalI = i;
            List<Circle> pointsOfInterest = histogram.getPointsOfInterest(0.1);
            pointsOfInterest.stream().forEach(circle -> {
                if (circle.i >= min /*<histogram.diffLevel* finalI*/) {
                    //img.drawOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (circle.r * 2), (int) (circle.r * 2));
                    Color color = Color.newCol((float) circle.i, 0f, (float) (circle.i / circle.r));
                    //img3.drawOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (circle.r * 2), (int) (circle.r * 2));
                    //img3.setRGB((int) (circle.x), (int) (circle.y), color.toArgb());
                }
            });
            pointsOfInterest.sort(new Comparator<Circle>() {
                @Override
                public int compare(Circle o1, Circle o2) {
                    double v = o1.y - o1.y;
                    if (v < 0)
                        return -1;
                    if (v > 0)
                        return 1;
                    if (v == 0) {
                        double v1 = o1.x - o1.x;
                        if (v1 < 0)
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram.java
                            return -1;
                        if (v1 > 0)
                            return 1;
                        if (v1 == 0)
                            return 0;
                    }
                    return 0;
                }
            });

<<<<<<<< HEAD:feature0/Histogram.java
                File fileToWrite = new File(directory.getAbsolutePath()
                        + "level" + finalI + ".jpg");
                File fileToWrite2 = new File(directory.getAbsolutePath()
                        + "level" + finalI + "_NEW.jpg");
                File fileToWrite3 = new File(directory.getAbsolutePath()
                        + "level" + finalI + "_NEW_RGB.jpg");
                fileToWrite.mkdirs();
                one.empty3.ImageIO.write(img, "JPEG", fileToWrite, shouldOverwrite);
                one.empty3.ImageIO.write(img, "JPEG", fileToWrite2, shouldOverwrite);
                one.empty3.ImageIO.write(img, "JPEG", fileToWrite3, shouldOverwrite);
========
            File fileToWrite = new File(directory.getAbsolutePath()
                    + "level" + finalI + ".jpg");
            File fileToWrite2 = new File(directory.getAbsolutePath()
                    + "level" + finalI + "_NEW.jpg");
            File fileToWrite3 = new File(directory.getAbsolutePath()
                    + "level" + finalI + "_NEW_RGB.jpg");
            fileToWrite.mkdirs();
            ImageIO.write(img, "JPEG", fileToWrite, shouldOverwrite);
            ImageIO.write(img, "JPEG", fileToWrite2, shouldOverwrite);
            ImageIO.write(img, "JPEG", fileToWrite3, shouldOverwrite);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Histogram.java

        }
    }

    public static void main(String[] args) {
        int levels = 10;
        //testCircleSelect(new File("resources/vg1.jpg"), new File("resources/res/"), levels, 0.3, 10);
    }
}
