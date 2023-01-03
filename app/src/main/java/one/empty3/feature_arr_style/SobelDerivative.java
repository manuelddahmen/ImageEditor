/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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

package one.empty3.feature_arr_style;

/*
sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?
*/
public class SobelDerivative extends FilterPixGMatrix {
    private final boolean isX;
    double[] sobelX = new double[]{-1, -2, -1, 0, 0, 0
            , 1, 2, 1};
    double[] sobelY = new double[]{-1, 0, -1,
            -2, 0, 2
            , 1, 0, 1};

    public SobelDerivative(boolean isX) {
        super(3, 3);
        this.isX = isX;
    }

    private void fill() {

    }

    public double x(int i, int j) {
        return sobelX[i * lines + j];
    }

    public double y(int i, int j) {
        return sobelY[i * lines + j];
    }

    public void theta(int i, int j) {

    }

    @Override
    public double filter(double x, double y) {
        int i = (int) (x + lines / 2);
        int j = (int) (x + columns / 2);
        return isX ? (sobelX[j * columns + i]) : (sobelY[j * columns + i]);
    }
}
