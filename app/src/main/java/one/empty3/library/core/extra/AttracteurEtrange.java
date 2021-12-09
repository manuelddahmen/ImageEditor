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
package one.empty3.library.core.extra;

import one.empty3.library.*;

/*__
 * @author manuel
 */
public final class AttracteurEtrange extends Representable implements POConteneur {

    public static int NOMBRE_POINTS = 1000000;
    private final double A;
    private final double B;
    private final double C;
    private final double D;
    private PObjet po;
    private String id;
    private Barycentre position;

    public AttracteurEtrange(double A, double B, double C, double D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;

        po = new PObjet();
        Point3D actuel = new Point3D(1d, 2d, 3d);
        Point3D precedent;
        int i = 0;
        while (i < NOMBRE_POINTS) {
            precedent = actuel;
            actuel = formule(precedent);
            po.add(actuel);
            i++;
        }
    }

    public Point3D formule(Point3D precedent) {

        Point3D ref = new Point3D(Math.sin(A * precedent.getY()) - precedent.getZ()
                * Math.cos(B * precedent.getX()), precedent.getZ()
                * (Math.sin(C * precedent.getX()) - Math.cos(D
                * precedent.getY())), Math.sin(precedent.getX()));
        return position == null ? ref : position.calculer(ref);
    }

    @Override
    public Iterable<Point3D> iterable() {
        return po.iterable();
    }


    @Override
    public String toString() {
        return "attracteurEtrange ( " + A + " " + B + " " + " " + C + " " + D
                + ")\n";
    }

}
