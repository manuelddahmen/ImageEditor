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

import one.empty3.libs.Image;

public class TrueHarris extends FilterPixM {

    public TrueHarris(PixM pix) {
        super(pix);
    }

    public double filter(double x, double y) {
        int i = (int) (float) x, j = (int) (float) y;
        double gx = get(i + 1, j) - get(i, j);
        double gy = get(i, j + 1) + get(i, j);
        double Sx2 = ((get(i + 1, j) - get(i, j)) - (get(i, j) - get(i - 1, j))) * get(i, j);
        double Sy2 = ((get(i, j + 1) - get(i, j)) - (get(i, j) - get(i, j - 1))) * get(i, j);
        double Ix = gx * get(i, j);
        double Iy = gy * get(i, j);
        double Ix2 = Ix * Ix;
        double Iy2 = Iy * Iy;
        double Ixy = Ix * Iy;
        double sSx2 = gx * Ix;
        double sSy2 = gy * Iy;
        double sSxy = Math.sqrt((gx + gy) / 2 * get(i, j));// Robert Collins

        //double r = (sSx2 * sSy2 - sSxy * sSxy);// / (sSx2 + sSy2);
        //double r = (Ix2 + Iy2 - Ix * Iy - Ixy) ;// / (sSx2 + sSy2);
        double r = Math.abs(0.5 - get(i, j));
        return r;
    }

    public double pow2(double a) {
        return a * a;
    }
}
