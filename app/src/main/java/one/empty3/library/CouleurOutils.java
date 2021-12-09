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

import java.awt.*;

/*__
 * @author manuel
 */
public class CouleurOutils {

    public static Color couleurFactio(Color c1, Color cFond, TRI t, Point3D lumiere, boolean plus) {
        Point3D v1 = t.normale().norme1();
        Point3D v2 = lumiere.norme1();

        double cos = v1.prodScalaire(v2);
        int signe = 1;
        if (!plus) {
            signe = -1;
        }
        int[] cFondC = new int[]{cFond.getRed(), cFond.getGreen(), cFond.getBlue()};
        int[] res = new int[]{c1.getRed(), c1.getGreen(), c1.getBlue()};

        for (int i = 0; i < 3; i++) {
            res[i] += signe * (int) (Math.abs(cFondC[i] * cos));
            if (res[i] < 0) {
                res[i] = 0;
            }
            if (res[i] > 255) {
                res[i] = 255;
            }
        }
        return new Color(res[0], res[1], res[2]);
    }

    public static String couleurID() {
        return "c";
    }

    public static Color couleurRatio(Color c1, Color c2, double r) {

        return new Color(
                (float) (c1.getRed() * r + c2.getRed() * (1 - r)),
                (float) (c1.getGreen() * r + c2.getGreen() * (1 - r)),
                (float) (c1.getBlue() * r + c2.getBlue() * (1 - r))
        );
    }

    public static String toStringColor(Color couleur) {
        return "(" + couleur.getRed() + ", " + couleur.getGreen() + ", "
                + couleur.getBlue() + ")";
    }

    public String couleurLongID() {
        return "Couleur";
    }

    public Color randomColor() {
        return new Color((float) Math.random(),
                (float) Math.random(),
                (float) Math.random());
    }
}
