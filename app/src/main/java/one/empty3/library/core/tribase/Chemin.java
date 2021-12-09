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

package one.empty3.library.core.tribase;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

public abstract class Chemin extends ParametricCurve {

    private int max = 100;

    /*__
     * *
     * Implémentation optionnelle pour l'instant
     *
     * @return Mesure de la longueur du chemin
     */
    public abstract double getLength();

    /*__
     * Point d'index i sur Max
     *
     * @param i index
     * @return Point3D point du chemin discret C
     */
    public Point3D getPoint(int i) {
        return calculerPoint3D(1.0 * i / getMax());
    }

    /*__
     * @return Nombre de points pour le chemin discret
     */
    public int getMax() {
        return max;
    }

    /*__
     * *
     * Définit le nombre maximal de points
     *
     * @param n
     */
    public void setMax(int n) {
        this.max = n;
    }

    /*__
     * *
     * Retourne la tangente au point d'index i
     *
     * @param i
     * @return
     */
    public Point3D tangent(int i) {
        if (i < max - 1 && i > 0) {
            return (getPoint(i).moins(getPoint(i - 1)));
        } else if (i == 0) {
            return tangent(i + 1);
        } else if (i == max - 1) {
            return tangent(i - 1);
        } else if (i == max) {
            return tangent(i - 2);
        }
        throw new UnsupportedOperationException("Index non permis: " + i + "." + max);
    }

    /*__
     * *
     * Retourne les vecteurs du plan normal au point d'index i
     *
     * @param i
     * @return
     */
    public Point3D normale(int i) {
        Point3D n = Point3D.O0;

        if (i > 0 && i < max - 1) {
            n = tangent(i + 1).moins(tangent(i));
        } else if (i == 0) {
            n = normale(i + 1);
        } else if (i == max - 1) {
            n = normale(i - 1);
        } else if (i == max) {
            n = normale(i - 2);
        } else {
            n = tangent(i).prodVect(Point3D.r(1d));
        }
        if (n.norme() == 0 || Double.isNaN(n.norme()))
            return Point3D.Z;
        else
            return n;
        //throw new UnsupportedOperationException("Index non permis: " + i + "." + max);
    }


}
