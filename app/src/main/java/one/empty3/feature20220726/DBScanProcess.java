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

package one.empty3.feature20220726;

import java.io.File;
import java.util.ArrayList;
import one.empty3.library.StructureMatrix;

import java.util.HashMap;
import java.util.List;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

public class DBScanProcess extends ProcessFile {
    private double[] size;
    private int countCentroids;

    public List<double[]> ns(List<double[]> points, double eps, double[] ps) {
        List<double[]> n = new ArrayList<>();
        for (double[] p : points) {
            if (distance(p, ps) < eps) {
                n.add(p);
            }
        }
        return n;
    }


    List<double[]> points;
    HashMap<Integer, List<double[]>> clusters;
    HashMap<double[], Integer> centroids;
    int pointsMax;
    double eps;
    int minPts;
    int c;


    PixM pix;

    public void dbscan() {
        countCentroids = 9;
        for (int i = 0; i < countCentroids; i++) {
            centroids.put(new double[]{Math.random() * pix.getColumns(),
                    Math.random() * pix.getLines()}, i);
        }
        size = new double[]{
                pix.getColumns(), pix.getLines(), 1.0, 1.0, 1.0
        };
        int count = 0;
        while (count < pointsMax) {
            for (double[] p : points) {
                if (centroids.get(p) != null && centroids.get(p) > -1) {
                    List<double[]> n = ns(points, eps, p);
                    if (n.size() < minPts) {
                        centroids.put(p, -1);
                        continue;
                    }
                    c = c + 1;
                    centroids.put(p, c);
                }

                List<double[]> N = ns(points, eps, p);
                for (double[] q : N) {
                    if (N.size() > minPts) {
                        centroids.put(q, c);
                    } else {
                        centroids.put(q, -1);
                    }
                }
                count++;
            }
        }
    }

    public double distance(double[] p1,
                           double[] p2) {
        double d = 0.0;
        for (int i = 0; i < Math.min(p1.length, p2.length); i++)
            d += (p1[i] - p2[i]) * (p1[i] - p2[i]);
        return Math.sqrt(d);
    }

    public double[] density(List<double[]> cluster, double[] centroid) {
        double[] den = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        for (double[] item : cluster) {
            for (int i = 0; i < 5; i++) {
                double abs = Math.abs(item[i] - centroid[i]);
                if (abs > 0)
                    den[i] += item[i] / Math.abs(item[i] - centroid[i]);
            }
        }
        return den;
    }

    //main method
    public boolean process(File in, File out) {
        points = new ArrayList();
        clusters = new HashMap<>();
        centroids = new HashMap<>();
        pointsMax = 10000;
        eps = 2.0;
        minPts = 10;
        c = 0;


        try {
            pix = PixM
                    .getPixM(ImageIO.read(in), maxRes);
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return false;
        }


        PixM pix2 = new PixM(
                pix.getColumns(),
                pix.getLines()
        );


        clusters.put(0, new ArrayList<double[]>());

        for (int i = 0; i < pix.getColumns(); i++) {
            for (int j = 0; j < pix.getLines(); j++) {
                double[] values = new double[5];
                Point3D values1 = pix.getP(i, j);
                values[0] = i;
                values[1] = j;
                values[2] = values1.get(0);
                values[3] = values1.get(1);
                values[4] = values1.get(2);


                clusters.get(0).add(values);
            }

        }
        points = clusters.get(0);

        dbscan();


        clusters.forEach((i, l) -> {
            for (double[] p : l)
                for (int j = 0; j < 3; j++) {
                    pix2.setCompNo(j);
                    pix2.set((int) (float) (p[0]),
                            (int) (float) (p[1]), p[j + 2]);
                }
        });
        try {
            ImageIO.write(pix2.normalize(0.0, 1.0).getImage(), "jpg", out, shouldOverwrite);
        } catch (Exception ex1) {
            ex1.printStackTrace();

        }
        return true;
    }
}
