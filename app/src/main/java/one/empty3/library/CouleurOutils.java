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

 Vous êtes libre de :

 */
package one.empty3.library;


import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

/*__
 * @author manuel
 */
public class CouleurOutils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Color couleurFactio(Color c1, Color cFond, TRI t, Point3D lumiere, boolean plus) {
        Point3D v1 = t.normale().norme1();
        Point3D v2 = lumiere.norme1();

        double cos = v1.prodScalaire(v2);
        int signe = 1;
        if (!plus) {
            signe = -1;
        }
        float[] cFondC = new float[]{cFond.red(), cFond.green(), cFond.blue()};
        float[] res = new float[]{c1.red(), c1.green(), c1.blue()};

        for (int i = 0; i < 3; i++) {
            res[i] += signe * (int) (Math.abs(cFondC[i] * cos));
            if (res[i] < 0) {
                res[i] = 0;
            }
            if (res[i] > 255) {
                res[i] = 255;
            }
        }
        return Color.valueOf(res[0], res[1], res[2]);
    }

    public static String couleurID() {
        return "c";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Color couleurRatio(Color c1, Color c2, double r) {

        return Color.valueOf(
                (float) (c1.red() * r + c2.red() * (1 - r)),
                (float) (c1.green() * r + c2.green() * (1 - r)),
                (float) (c1.blue() * r + c2.blue() * (1 - r))
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String toStringColor(Color couleur) {
        return "(" + couleur.red() + ", " + couleur.green() + ", "
                + couleur.blue() + ")";
    }

    public String couleurLongID() {
        return "Couleur";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Color randomColor() {
        return Color.valueOf((float) Math.random(),
                (float) Math.random(),
                (float) Math.random());
    }
}
