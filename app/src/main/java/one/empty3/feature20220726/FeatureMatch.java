/*
 * Copyright (c) 2023.
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

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

import java.io.File;
import java.util.ArrayList;
import one.empty3.library.StructureMatrix;

import java.util.List;

public class FeatureMatch extends ProcessFile {
    final Point3D point = new Point3D(1., 1., 1.);
    List<double[]> points1;
    List<double[]> points2;
    PixM img1copy;
    PixM img2copy;

    /***
     *
     * @param img1
     * @param img2
     * @return List of double[x1][y1][x2][y2]
     */
    public List<double[]> match(PixM img1, PixM img2) {
        TrueHarrisProcess trueHarrisProcess = new TrueHarrisProcess();

        img1copy = new PixM(img1.getColumns(), img1.getLines());
        //trueHarrisProcess.processMem(img1, img1copy);
        img2copy = new PixM(img2.getColumns(), img2.getLines());
        //trueHarrisProcess.processMem(img2, img2copy);

        points1 = features(img1copy);
        points2 = features(img2copy);

        List<double[]> pointsMatched = match();

        return pointsMatched;
    }

    private List<double[]> match() {
        // Éliminer les points choisis des sources de points candidats.
        List<double[]> points = new ArrayList<>();
        for (double[] p1 : points1) {
            double[] pp1 = new double[points2.size()];
            double[] p2c = null;
            for (double[] p2 : points2) {
                p2c = p2;
                int p = 0;
                pp1[p] = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = 1; j <= 1; j++) {
                        pp1[p] += (img1copy.getP((int) p1[0], (int) p1[1])
                                .moins(img2copy.getP((int) p2[0], (int) p2[1])).norme());
                    }
                }
                p++;
            }
            double min = 8;
            int p = -1;
            for (int i = 0; i < pp1.length; i++) {
                if (pp1[i] < min) {
                    min = pp1[i];//Minimum différence couleurs ((+maximum intensité couleurs?))
                    p = i;
                }
            }


            points.add(new double[]{p1[0], p1[1], points2.get(p)[0], points2.get(p)[1]});
            if (points2.contains(p2c))
                points2.remove(p2c);
        }
        return points;
    }

    private List<double[]> features(PixM img1copy) {

        ArrayList<double[]> points = new ArrayList<>();

        for (int i = 0; i < img1copy.getColumns(); i++) {
            for (int j = 0; j < img1copy.getLines(); j++) {
                for (int c = 0; c < img1copy.getCompCount(); c++) {
                    if (img1copy.getValues(i, j).equals(point)) {
                        points.add(new double[]{i, j});
                    }
                }
            }
        }
        return points;
    }


    @Override
    public boolean process(File in, File out) {
        return false;
    }
}
