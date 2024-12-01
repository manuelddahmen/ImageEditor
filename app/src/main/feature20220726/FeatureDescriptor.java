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

import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;

import java.util.*;
import java.io.File;
/*
public class FeatureDescriptor extends PixM {


    private PixM m;
    private PixM pi;
    private List<Point3D> poi;

    public FeatureDescriptor() {
        super(3, 3);
    }

    public boolean setDescription(double[][] values) {
        for (int i = 0; i < lines; i++)
            for (int j = 0; j < columns; j++)
                set(i, j, values[i][j]);

        return true;
    }

    public void setPixM(PixM m) {
        this.m = m;
        HarrisToPointInterest h = new HarrisToPointInterest(
                m.getColumns(), m.getLines());
        pi = m.applyFilter(h);
        poi = h.getPoi();
    }

    // table line
    // featuredescriptor, image, imagelocation, matchscore*
    // *e min
    public List<FeatureImageLocationMatchScore> matchesAll(FeatureDescriptor[] fd, File[] set) {
        List<FeatureImageLocationMatchScore> l = new ArrayList<>();

        for (File file : set) {
            try {
                PixM pm = new PixM(new Image(file));
                for (FeatureDescriptor f : fd) {
                    f.setPixM(pm);
                    for (int i = 0; i < poi.size(); i++) {
                        double de = f.deltaEnergy((int) (double) (poi.get(i).getX()), (int) (double) (poi.get(i).getY()));
                        if (de < 0.5) {
                            l.add(new FeatureImageLocationMatchScore());
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return l;

    }


    public double deltaEnergy(int i, int j) {
        return pi.getColorsRegion(i - columns / 2, j - lines / 2, columns, lines, columns, lines).distance(this);
    }


    public double filter(double x, double y) {


        return 0.0;
    }


    public static double[][][] getFeatureSampleDescr(int mSize,
                                                     double max, double[][] anglesIntensitiesRatio) {
        double[][][] m = new double[anglesIntensitiesRatio.length][mSize][mSize];
        return m;
    }
}
*/