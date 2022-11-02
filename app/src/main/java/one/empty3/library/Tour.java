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

@Deprecated
public class Tour extends Representable implements Generator {

    private Point3D orig;
    private Point3D dest;
    private IColorFunction color;
    private IPoint3DFunction points;

    public Tour(Point3D orig2, Point3D dest2, Function function,
                ColorFunction colorFunction) {
    }

    public Tour(Point3D orig, Point3D dest, IColorFunction color,
                IPoint3DFunction points) {
        super();
        this.orig = orig;
        this.dest = dest;
        this.color = color;
        this.points = points;
    }

    public TRIObject generate() {
        TRIObject tour = new TRIObject();

        /*		Point3D[][] p = new Point3D[this.points.getNbrPoints()][points
         .getNbrRotations()];

         for (int axe = 0; axe < this.points.getNbrPoints(); axe++) {
         int a = 0;
         for (double angle = 0; angle < 360; angle += 1.0 / points
         .getNbrRotations()) {
         Point3D p0 = orig.plus(orig.mult(-1).plus(dest)
         .mult(1.0 * axe / points.getNbrPoints()));
         Point3D p1 = p0.plus(orig.mult(-1).plus(dest)
         .prodVect(new Point3D(0, 0, 1)).norme1()
         .mult(points.getDiameter(axe, angle)));
         p[axe][a] = rotation(new Axe(this.orig, this.dest), angle, p1);
         a++;
         }
         }

         for (int i = 0; i < p.length; i++)
         for (int j = 0; j < p[0].length; j++) {
         double axeCoordinate = 1.0 * i / points.getNbrPoints();
         double theta = 2 * Math.PI * j / points.getNbrRotations();
         tour.add(new TRI(p[i][j], p[i + 1][j], p[i][j + 1], color
         .getColor(axeCoordinate, theta)));
         tour.add(new TRI(p[i][j + 1], p[i + 1][j], p[i][j + 1], color
         .getColor(axeCoordinate, theta)));
         }
         */
        return tour;
    }

    public IColorFunction getColor() {
        return color;
    }

    public void setColor(IColorFunction color) {
        this.color = color;
    }

    public Point3D getDest() {
        return dest;
    }

    public void setDest(Point3D dest) {
        this.dest = dest;
    }

    public Point3D getOrig() {
        return orig;
    }

    public void setOrig(Point3D orig) {
        this.orig = orig;
    }

    public IPoint3DFunction getPoints() {
        return points;
    }

    public void setPoints(IPoint3DFunction points) {
        this.points = points;
    }

    public interface IColorFunction {

        Color getColor(double axeCoordinate, double theta);
    }

    public interface IPoint3DFunction {

        double getDiameter(double axeCoordinate, double theta);

        int getNbrPoints();

        int getNbrRotations();

        void setNbrPoints();

        void setNbrRotation();
    }

}
