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

/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package one.empty3.library;

import android.os.Build;

import androidx.annotation.RequiresApi;

import android.graphics.Color;

/*__
 * @author Atelier
 */
public abstract class Lumiere extends Representable {
    // ambient specular diffuse shinyness
    protected int La = Color.BLACK, Ls = Color.WHITE, Ld = Color.WHITE;

    protected double S = 0.5;

    public static int[] getInts(int rgb, int[] colorComponents) {
        for (int i = 0; i < 3; i++) {
            colorComponents[i] = (((rgb & (0xff << ((2 - i) * 8))) >> ((2 - i) * 8)));
        }
        return colorComponents;

    }

    public abstract int getCouleur(int base, Point3D p, Point3D n);

    public int getLa() {
        return La;
    }

    public int getLs() {
        return Ls;
    }

    public int getLd() {
        return Ld;
    }

    public static double[] getRgb(Color c) {
        return new double[]{(c.red()),
                (c.green()),
                (c.blue())};
    }
    /*
    *
    public static double[] getRgb(Color c) {
        return new double[]{(c.red()),
                (c.green()),
                (c.blue())};
    }*/

    public static int getInt(double[] d) {
        int res = 0;
        for (int i = 0; i < 3; i++) {
            res += ((int) (float) (d[i] * 256)) << ((2 - i) * 8);
        }
        return res;//|0xFF000000;
    }

    public static double[] getDoubles(int c) {
        double[] res = new double[3];
        for (int i = 0; i < 3; i++) {
            res[i] = (((c & (0xff << ((2 - i) * 8))) >> ((2 - i) * 8))) ;
        }
        return res;
    }
    public static double[] getDoubles(int c, double[] res) {
        for (int i = 0; i < 3; i++) {
            res[i] = (((c & (0xff << ((2 - i) * 8))) >> ((2 - i) * 8))) ;
        }
        return res;
    }

}
