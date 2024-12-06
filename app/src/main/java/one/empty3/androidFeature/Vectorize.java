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

import one.empty3.io.ProcessFile;
import matrix.PixM;
import one.empty3.library.Point3D;

import one.empty3.ImageIO;
import one.empty3.libs.*;
import one.empty3.libs.Image;

import java.io.File;
import java.util.ArrayList;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Vectorize extends ProcessFile {
    private matrix.PixM matrix.PixM;
    private double distMax = 40.;
    private final Random random = new Random();
    Point3D[][] mapPoints;
    private ArrayList<ArrayList<Point3D>> lists;
    private int[][] p;

    public Vectorize() {
        lists = new ArrayList<ArrayList<Point3D>>();
    }

    public double r() {
        return (random.doubles().iterator().nextDouble() + 1.) / 2;
    }

    @Override
    public boolean process(File in, File out) {
        lists = new ArrayList<ArrayList<Point3D>>();
        Image read = ImageIO.read(in);
        if (read == null)
            return false;
        matrix.PixM = new matrix.PixM(read);
        matrix.PixM o = new matrix.PixM(matrix.PixM.getColumns(), matrix.PixM.getLines());

        p = new int[matrix.PixM.getColumns()][matrix.PixM.getLines()];
        mapPoints = new Point3D[matrix.PixM.getColumns()][matrix.PixM.getLines()];

        for (int x = 0; x < matrix.PixM.getColumns(); x++)
            for (int y = 0; y < matrix.PixM.getLines(); y++) {
                p[x][y] = 0;
                if (matrix.PixM.luminance(x, y) > 0.4)
                    mapPoints[x][y] = new Point3D((double) x, (double) y, matrix.PixM.luminance(x, y));
            }

        for (int i = 0; i < matrix.PixM.getColumns(); i++) {
            for (int j = 0; j < matrix.PixM.getLines(); j++) {
                if (mapPoints[i][j] != null) {
                    lists.add(new ArrayList<>());
                    lists.get(lists.size() - 1).add(mapPoints[i][j]);
                }
            }
        }
        AtomicBoolean hasMoved = new AtomicBoolean(true);
        int i = 0, j = 0, k = 0, l = 0;

        ArrayList<ArrayList<Point3D>> lists2 = new ArrayList<ArrayList<Point3D>>();
        ArrayList<Point3D> current;

        while (hasMoved.get()) {
            hasMoved.set(false);
            i = 0;
            int credI = 0;
            while (i < lists.size() && !hasMoved.get()) {
                ArrayList<Point3D> subList1 = lists.get(i);
                j = 0;
                int credJ = 0;
                while (j < lists.get(i).size() && !hasMoved.get()) {
                    Point3D p1 = subList1.get(j);
                    k = 0;
                    int credK = 0;
                    while (k < lists.size() && !hasMoved.get()) {
                        ArrayList<Point3D> subList2 = lists.get(k);
                        int credL = 0;
                        while (l < subList2.size() && !hasMoved.get()) {
                            Point3D p2 = subList2.get(l);
                            if (neighborhood(p1, p2) && subList1 != subList2 && p1 != p2
                                /*&& p1.norme() > 0.4 && p2.norme() > 0.4*/) {
                                subList2.remove(p2);
                                credL--;
                                subList1.add(p2);
                                credJ++;
                                hasMoved.set(true);

                                if (subList2.isEmpty()) {
                                    lists.remove(subList2);
                                    credK--;
                                }
                            }

                            l = l + 1 + credL;
                        }
                        k = k + 1 + credK;
                    }
                    j = j + 1 + credJ;
                }
                i = i + 1 + credI;
            }

        }

        System.out.println("Number of blocks = " + lists.size());
        for (ArrayList<Point3D> p3s : lists) {
            if (p3s.size() > 1) {
                android.graphics.Color r = new Color(p3s.get(0).texture().getColorAt(0.5, 0.5));
                if (p3s.size() > 0)
                    p3s.forEach(point3D -> o.setValues((int) (double) (point3D.getX()), (int) (double) (point3D.getY()), r.red() / 255., r.green() / 255., r.blue() / 255.));
            }
        }
        ImageIO.write(o.normalize(0.0, 1.0).getImage().getImage(), "jpg", out, shouldOverwrite);
        return true;

    }

    private boolean neighborhood(Point3D p1, Point3D p2) {
        return Point3D.distance(p1, p2) < 1.5;
    }

    public double getDistMax() {
        return distMax;
    }

    public void setDistMax(double distMax) {
        this.distMax = distMax;
    }

}
