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

import one.empty3.feature.app.pro.M3;

public class LocalPattern extends FilterMatmatrix.PixM {
    private M3 sr;
    public static String formulaXvLogical = "count(x==v)==columns()*lines()/2";

    /*
   
        Ligne 1 searchPattern value = x [0,1] 
           - 1 : ignore 
        Ligne 2 replacePattern value x' [0,1] 
        comp4 => rgb' = rgb *(1-opacity) si x ! =-1
       
    */
    public LocalPattern(M3 searchReplace) {
        // super(searchReplace.getColumns(), searchReplace.getLines(), 1,1) ;
        this.sr = searchReplace;

    }

    /*
     */
    public M3 filter(

            M3 original
    ) {
        M3 copy = new M3(original.getColumns(), original.getLines(),

                1, 1);

        for (int i = 0; i < original.getColumns(); i++) {

            for (int j = 0; j < original.getLines(); j++) {

                //copy.setCompNo(c);

                //boolean isMaximum = true;
                double v = 0.;
                double maxLocal = original.getIntensity(i, j, 0, 0);
//v = maxLocal;
                int countOut = 0;

                int countIn = 0;

                for (int s = 0; s < sr.getColumnsIn(); s++) {


                    for (int ii = -sr.getColumns() / 2; ii <= sr.getColumns(); ii++) {

                        for (int ij = -sr.getLines() / 2; ij <= sr.getLines() / 2; ij++) {

                            v = original.get(i + ii, j + ij, 0, 0);

                            if (sr.get(ii + sr.getColumns() / 2, ij + sr.getLines() / 2, 0, s)
                                    == v) {

                                countIn++;

                            }

                        }

                    }


                    if (countIn == sr.getLines() * sr.getColumns() / 2.0) {

                        copy.setCompNo(0);

                        copy.set(i, j, 0, 0, 1.0);//1 au lieu value

                        copy.setCompNo(1);

                        copy.set(i, j, 0, 0, 1.0);//1 au lieu value

                        copy.setCompNo(2);

                        copy.set(i, j, 0, 0, 1.0);//1 au lieu value
                    }
                }

            }

        }

        return copy;


    }


    @Override
    public void element(M3 source, M3 copy, int i, int j, int ii, int ij) {

    }

    @Override
    public M3 norm(M3 m3, M3 copy) {
        return m3.copy();
    }
} 
