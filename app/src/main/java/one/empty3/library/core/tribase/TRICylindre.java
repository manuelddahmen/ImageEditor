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

import one.empty3.library.*;

public class TRICylindre extends TRIObjetGenerateurAbstract {

    private Point3D centre = new Point3D(0d, 0d, 0d);
    private double hauteur = 1.0;
    private double radius = 1.0;
    private boolean sectionA = true;
    private boolean sectionB = true;

    public TRICylindre(double h, double radius) {
        this.hauteur = h;
        this.radius = radius;
        this.centre = Point3D.O0;

        setCirculaireY(false);
        setCirculaireX(false);

    }

    public TRICylindre(Point3D c, double h, double radius) {
        this.centre = c;
        this.hauteur = h;
        this.radius = radius;

        setCirculaireY(false);
        setCirculaireX(false);
    }

    @Override
    public Point3D coordPoint3D(int x, int y) {
        double a = 1.0 * x / getMaxX() * hauteur;
        double b = 1.0 * y / getMaxY() * 2 * Math.PI;

        double radius = this.radius;
        if (x >= getMaxX() - 1) {
            radius = 0;
        }
        if (x <= 0) {
            radius = 0;
        }

        Point3D base = this.centre;
        Point3D p = base.plus(new Point3D(
                Math.cos(b) * radius,
                Math.sin(b) * radius,
                hauteur * a
        ));
        return p;
    }

    public Point3D getCentre() {
        return centre;
    }

    public void setCentre(Point3D centre) {
        this.centre = centre;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String id() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sectionAB(boolean sectionA, boolean sectionB) {
        this.sectionA = sectionA;
        this.sectionB = sectionB;
    }

    public void setId(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "Cylindre (\n\t" + centre.toString() + "\n\t" + radius + "\n\t\""
                + texture.toString() + "\"\n)\n";
    }

}
