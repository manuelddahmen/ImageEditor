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

public class GaussFiltermatrix.PixM extends Filtermatrix.PixM {
    private final matrix.PixM in;
    public double sigma = 0.8;

    public GaussFiltermatrix.PixM() {
        super(3, 3);
        in = null;
    }

    public GaussFiltermatrix.PixM(matrix.PixM in, int squareSize) {
        super(squareSize, squareSize);
        this.in = in;
    }

    @Override
    public double filter(double x, double y) {
        double ret = 0.0;
        for (double i = x - in.getLines() / 2; i < x + in.getLines() / 2; i++)
            for (double j = y - in.getColumns() / 2; j < y + in.getColumns() / 2; j++) {
                ret += in.get((int) i, (int) j) * Math.exp(
                        -((x - i) * (x - i) + (y - j) * (y - j))
                                / 2 / sigma / sigma) / 2 / Math.PI / sigma / sigma;
            }
        return ret;
    }

    /*
     * Gaussian filter Matrix
     * @param halfSquareSizeMinus1 n*n square distribution
     * @param sigma gauss parameter
     * @param matrix.PixM image
     */
    public GaussFiltermatrix.PixM(matrix.PixM matrix.PixM, int halfSquareSizeMinus1, double sigma) {
        this(matrix.PixM, halfSquareSizeMinus1 * 2 + 1);
        this.sigma = sigma;
        for (int comp = 0; comp < getCompCount(); comp++) {
            setCompNo(comp);
            fill();
        }
        setCompNo(0);
    }

    public void fill() {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < lines; j++) {
                set(i, j, filter(i - columns / 2,
                        j - lines / 2)
                );
            }
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }
}
