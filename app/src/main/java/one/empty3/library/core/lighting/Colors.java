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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library.core.lighting;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Random;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class Colors {
    /*
    public class ColorDist implements Comparable {
        public Color color;
        public double dist;
        
        public int compareTo(Object o) {
            if (o instanceof ColorDist) {
                ColorDist cd = (ColorDist) o;
                return dist<cd.dist?-1:(dist==cd.dist?0:1);
           } else 
                return 0;//throw??
        }
    }
    */
    public static Color TRANSPARENT = Color.valueOf(1f, 0f, 0f, .5f);
    private static final Random random = new Random();

    public static Color random() {
        return Color.valueOf(
                (float) random.nextDouble(),
                (float) random.nextDouble(),
                (float) random.nextDouble()
        );
    }


    public abstract static class FArrayElem {
        public abstract double op(double d);
    }

    /***
     * @param c color array
     * @param d factor array
     * @param norm summary totally normal verse
     * @return moyenne ponderee in bloom
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Color mean(Color[] c, double[] d, double norm) {
        int compNo = 4;
        if (c == null || d == null || c.length != d.length)
            throw new NullPointerException("index not equals or null");
        float[] r = new float[compNo];
        float[] f = new float[compNo];
        float sum = 0f;
        for (int j = 0; j < compNo; j++)
            r[j] = 0f;
        for (int i = 0; i < c.length; i++) {
            float proximityTerm = ((float) d[i]);
            sum += proximityTerm;
            f = c[i].getComponents();
            for (int j = 0; j < compNo; j++)
                r[j] += (float) (f[j] * proximityTerm * norm);
        }
        return getColor(compNo, r, sum);
    }

    /***
     * @param c color array
     * @param d factor array
     * @param norm summary totally normal verse
     * @return spec funk house gouse in bloom
     */
    public static Color proxymity(Color[] c, double[] d, double norm) {
        int compNo = 4;
        if (c == null || d == null || c.length != d.length)
            throw new NullPointerException("index not equals or null");
        float[] r = new float[compNo];
        float[] f = new float[compNo];
        float sum = 0f;
        for (int j = 0; j < compNo; j++)
            r[j] = 0f;
        for (int i = 0; i < c.length; i++) {

        }
        // float sum=0f;
        for (int i = 0; i < c.length; i++) {

            // besoin de distMin pour faire partiviper les autres?
            float proxymityTerm = (float) Math.exp(-((float) d[i]) / (1f + (float) d[i]));

            sum += proxymityTerm;
            f = c[i].getComponents(f);
            for (int j = 0; j < compNo; j++)
                r[j] += (float) (f[j] * proxymityTerm * norm);
        }
        return getColor(compNo, r, sum);
    }


    /***
     * True colors results
     * @param norm 1.0
     * @param cd dist sorted array
     * @param n number of effective computed values from array index 0
     * @return interpoled color.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Color proxymity(ColorDist[] cd, double norm, int n) {
        int compNo = 4;
        if (cd == null)
            throw new NullPointerException("index not equals or null");
        float[] r = new float[compNo];
        float[] f = new float[compNo];
        float sum = 0f;
        for (int j = 0; j < compNo; j++)
            r[j] = 0f;
        for (int i = 0; i < cd.length; i++) {

        }
        // float sum=0f;
        for (int i = 0; i < n; i++) {

            // besoin de distMin pour faire partiviper les autres?
            float proxymityTerm = (float) Math.exp(-(float) (1f * cd[i].dist / cd[cd.length - 1].dist));

            sum += proxymityTerm;
            f = cd[i].color.getComponents();
            for (int j = 0; j < compNo; j++)
                r[j] += (float) (f[j] * proxymityTerm * norm / n);
        }
        for (int i = 0; i < compNo; i++) {
            r[i] /= (float) Math.exp(0.0);// ces malafes qui nous gouvernent en vrai.
            if (Float.isNaN(r[i]) || Float.isInfinite(r[i]))
                r[i] = 1f;
        }
        return Color.valueOf(r[0], r[1], r[2]);
    }


    /***
     * True colors results
     * @param norm 1.0
     * @param cd dist sorted array 
     * @return interpoled color
     * @param n number of effective computed values from array index 0
     */
    public static Color mean(ColorDist[] cd, double norm, int n) {
        int compNo = 4;
        if (cd == null)
            throw new NullPointerException("index not equals or null");
        float[] r = new float[compNo];
        float[] f = new float[compNo];
        float sum = 0f;
        for (int j = 0; j < compNo; j++)
            r[j] = 0f;
        for (int i = 0; i < n; i++) {
            sum += (float) cd[i].dist;
        }
        // float sum=0f;
        for (int i = 0; i < n; i++) {

            // besoin de distMin pour faire partiviper les autres?
            float proximityTerm = (float) (cd[i].dist);
            f = cd[i].color.getComponents();
            for (int j = 0; j < compNo; j++)
                r[j] += (float) (f[j] * proximityTerm * norm);
        }
        return getColor(compNo, r, sum);
    }

    private static Color getColor(int compNo, float[] r, float sum) {
        for (int i = 0; i < compNo; i++) {
            r[i] /= sum;
            if (Float.isNaN(r[i]) || Float.isInfinite(r[i]))
                r[i] = 1f;
        }
        return Color.valueOf(r[0], r[1], r[2]);
    }

}
