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

import java.io.File;

public class ProxyValue2 extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        matrix.PixM original = null;

        try {
            original = PixM.getPixM(one.empty3.ImageIO.read(in), maxRes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
            // assertTrue(false);

        }
        int p = 0;
        matrix.PixM copy = original.copy();
        for (int i = 0; i < original.getColumns(); i++)

            for (int j = 0; j < original.getLines(); j++)


                if (copy.luminance(i, j) < 0.3) {

                    searchFromTo(original, copy, i, j, 0.3);
                    p++;

                }


        try {
            one.empty3.ImageIO.write(copy.getImage().getImage(), "jpg", out, shouldOverwrite);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


        System.out.println("point " + p);

        return true;
    }


    public void searchFromTo(
            matrix.PixM original, matrix.PixM copy, int i, int j, double min) {
        Point3D p = null;
        int i2 = i, j2 = j;

     /*   for(int k=0; k<original.getColumns()*original.getLines();k++)
                {

              int [] k1 = new int[] {incr[(k/2)%8],
                                     incr[(k/2+1)%8]};
                i2+= k1[0];
                j2 += k1[1];

           */
        for (int l = 1; l < original.getColumns(); l++) {
            int[] incr = new int[]{

                    -l, -l, 0, 1,
                    l, -l, 0, 1,
                    -l, l, 1, 0,
                    -l, -l, 1, 0

            };

            for (int sq = 0; sq < incr.length; sq += 4) {
                int pass = 0;
                for (int i3 = incr[sq]; i3 < l && pass > -1; i3 += incr[(sq + 2)]) {

                    for (int j3 = incr[(sq + 1)]; j3 < l && pass > -1; j3 += incr[(sq + 3)]) {

                        pass++;
                        i2 = i + i3;
                        j2 = j + j3;
                        p = null;


                        if (original.luminance(i2, j2) >= min) {


                            copyPixel(original, i2,
                                    j2,
                                    copy, i, j);
                            return;
                        }


                        if (pass > 2 * l) pass = -1;

                    }
                }
            }
        }
        // System.out.println("error not found");

        return;
    }


    public void copyPixel(matrix.PixM m1, int i, int j,
                          matrix.PixM m2, int i2, int j2) {
        for (int c = 0; c < 3; c++) {

            m1.setCompNo(c);
            m2.setCompNo(c);

            m2.set(i2, j2, m1.get(i, j));
        }

    }
} 
