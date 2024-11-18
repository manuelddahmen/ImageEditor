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

package one.empty3.feature;

public class DefineFilter extends FilterPixM {


    public DefineFilter(double[][] matrix, double divider) {
        super(matrix.length, matrix[0].length);
        fill(matrix);
    }

    @Override
    public double filter(double x, double y) {
        return 1.0 * get((int) x - columns / 2, (int) y - lines / 2);
    }

    /*
     * Gaussian filter Matrix
     * @param matrix 2d double
     */


    public void fill(double[][] matrix) {
        for (int comp = 0; comp < getCompCount(); comp++) {
            setCompNo(comp);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    set(i, j, matrix[j][i]);
                }
            }
        }

    }
}
