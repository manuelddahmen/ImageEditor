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


import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

/*__
 * @author Atelier
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class LumierePointSimple extends LumierePoint {

    public static final Lumiere PARDEFAUT = new LumierePointSimple(Color.valueOf(Color.WHITE),
            Point3D.O0, 2.0);
    float[] f = new float[3];
    private Color couleur;
    private Point3D point;
    private double intensite;
    private float[] comp = new float[3];

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LumierePointSimple(Color c, Point3D pl, double intensite) {
        this.couleur = c;
        this.point = pl;
        this.intensite = intensite;
        comp = couleur.getComponents();
    }

    @Override
    public int getCouleur(int base, Point3D p, Point3D n) {
        if (p != null && n != null) {
            return mult(
                    (float) (Math.abs(n.norme1().prodScalaire(
                            p.moins(point).norme1())) * intensite), base);
        } else {
            return base;
        }
    }

    public int getCouleur(Point3D p) {
        return getCouleur(p.texture.getColorAt(0.5, 0.5)
                , p, p.getNormale());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int mult(float d, int base1) {
        f = Color.valueOf(base1).getComponents();
        for (int i = 0; i < 3; i++) {
            f[i] = (float) (f[i] * comp[i] * intensite);
            if (f[i] > 1f) {
                f[i] = 1f;
            }
            if (f[i] < 0f) {
                f[i] = 0f;
            }
        }
        return Color.valueOf(f[0], f[1], f[2]).toArgb();
    }

}
