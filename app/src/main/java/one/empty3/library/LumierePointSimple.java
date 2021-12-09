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

import java.awt.*;

/*__
 * @author Atelier
 */
public class LumierePointSimple extends LumierePoint {

    public static final Lumiere PARDEFAUT = new LumierePointSimple(Color.WHITE,
            Point3D.O0, 2.0);
    float[] f = new float[3];
    private Color couleur;
    private Point3D point;
    private double intensite = 1.0;
    private float[] comp = new float[3];

    public LumierePointSimple(Color c, Point3D pl, double intensite) {
        this.couleur = c;
        this.point = pl;
        this.intensite = intensite;
        couleur.getColorComponents(comp);
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
        return getCouleur(p.texture.getColorAt(0.5,0.5)
                , p, p.getNormale());
    }

    private int mult(float d, int base1) {
        new Color(base1).getColorComponents(f);
        for (int i = 0; i < 3; i++) {
            f[i] = (float) (f[i] * comp[i] * intensite);
            if (f[i] > 1f) {
                f[i] = 1f;
            }
            if (f[i] < 0f) {
                f[i] = 0f;
            }
        }
        return new Color(f[0], f[1], f[2]).getRGB();
    }

}
