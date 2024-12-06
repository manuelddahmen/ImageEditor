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

import android.graphics.Color;

import one.empty3.feature.app.pro.M3;
import matrix.PixM;
import one.empty3.library.core.lighting.Colors;
import one.empty3.io.ProcessFile;

import one.empty3.libs.Image;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public class ExtractIntensityInfo extends
        ProcessFile {
    int colorsLevels = 15;


    public ExtractIntensityInfo() {
    }


    @Override
    public boolean process(File in, File out) {
        Image img = null;
        try {
            img = one.empty3.ImageIO.read(in);
        } catch (Exception rx) {
        }
        one.empty3.featureAndroid.matrix.PixM pix = one.empty3.featureAndroid.matrix.PixM.getmatrix.PixM(img, -1);


        one.empty3.featureAndroid.matrix.PixM matrix.PixMOriginal = pix;

        final one.empty3.libs.Image[] img3 = new one.empty3.libs.Image[]{pix.getImage()};


        GradientFilter gradientMask = new GradientFilter(matrix.PixMOriginal.getColumns(), matrix.PixMOriginal.getLines());
        M3 imgForGrad = new M3(matrix.PixMOriginal,
                2, 2);
        M3 filter = gradientMask.filter(imgForGrad);
        one.empty3.featureAndroid.matrix.PixM[][] imagesMatrix = filter.getImagesMatrix();//.normalize(0, 1);


//                    image1 = null;

        // Zero. +++Zero orientation variation.
        Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                new one.empty3.featureAndroid.matrix.PixM(matrix.PixMOriginal.getColumns(), matrix.PixMOriginal.getLines()));
        linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
        one.empty3.featureAndroid.matrix.PixM smoothedGrad = linear.getImages()[2];


        double min = 0.3;
        double rMin = 2.0;
        // varier rMin et min

        matrix.PixM pix2 = smoothedGrad.copy();

        Histogram2 histogram = new Histogram2(15);
        histogram.setM(pix2);
        List<Histogram2.Circle> pointsOfInterest = histogram.getPointsOfInterest(rMin);


        double isumtot[] = new double[]{0, 0};
        double[] iSum = {0.0, 0.0, 1.0};
        pointsOfInterest.stream().filter(new Predicate<Histogram2.Circle>() {
            @Override
            public boolean test(Histogram2.Circle circle) {
                iSum[0] += circle.i;
                iSum[1] = Math.min(circle.i, iSum[1]);
                iSum[2] = Math.max(circle.i, iSum[2]);

                return true;
            }
        }).forEach(circle -> {
            circle.i = (circle.i - iSum[1]) / (iSum[2] - iSum[1]);
            isumtot[0] += circle.i;
            isumtot[1] += circle.r;
        });
        Color[] colors = new Color[colorsLevels];
        for (int i = 0; i < colors.length; i++)
            colors[i] = Colors.random();

        double finalMin = min;
        pointsOfInterest.stream().filter(new Predicate<Histogram2.Circle>() {
            @Override
            public boolean test(Histogram2.Circle circle) {
                return circle.i > isumtot[0] / pix.getColumns() / pix.getLines();
            }
        }).forEach(circle -> {
            //System.out.println(circle.toString());
            pix2.setCompNo(0);

            pix2.set((int) circle.x, (int) circle.y, 1.0);
            pix2.setCompNo(2);
            pix2.set((int) circle.x, (int) circle.y, circle.r);
            Color color = colors[(int) ((circle.i - iSum[1]) / (iSum[2] - iSum[1])
                    * colorsLevels - 1)];
                  /*  Graphics graphics = img3[0].getGraphics();
                    graphics.setColor(color);
                    graphics.drawRect((int) (circle.x-10), (int) (circle.y-10), (int) (10), (int) (10));
*/
        });


        pix2.normalize(0.0, 1.0);

        //


        try {
            one.empty3.ImageIO.write(pix.getImage().getImage(),
                    "JPEG", out, shouldOverwrite);
        } catch (Exception ex) {
        }


        return true;


    }

}
