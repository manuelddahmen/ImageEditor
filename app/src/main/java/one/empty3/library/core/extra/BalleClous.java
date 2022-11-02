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
 * 2013 Manuel Dahmen
 */
package one.empty3.library.core.extra;

import one.empty3.library.HeightMapSurface;
import one.empty3.library.Point2D;
import one.empty3.library.Point3D;
import one.empty3.library.Sphere;

import java.util.ArrayList;

/*__
 * @author Se7en
 */
public class BalleClous extends HeightMapSurface {

    private ArrayList<Point2D> points = new ArrayList<Point2D>();
    private double d;

    public BalleClous(Point3D c, double r) {
        super(new Sphere(c, r), null);
    }

    public void addPoint(Point2D p) {
        this.points.add(p);
    }


    public double dmindist(Point2D p0, Point2D p1) {

        double[] x = new double[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};
        double[] y = new double[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
        double min = 100;
        for (int i = 0; i < 9; i++) {
            double cur;
            cur = Point2D.dist(p0, Point2D.plus(p1, new Point2D(x[i], y[i])));
            if (cur < min && cur > 0) {
                min = cur;
            }
        }
        return min;
    }

    public double param() {
        return d;
    }

    public void param(double d) {
        this.d = d;
    }

    public ArrayList<Point2D> points() {
        return points;
    }


    public Point3D calculerPoint3D(double u, double v) {
        return super.calculerPoint3D(u, v);
    }

    public Point3D height(double u, double v) {
        Point2D p0 = new Point2D(u, v);

        double mult = 1.0;

        for (int i = 0; i < points.size(); i++) {

            mult += 1 / (d + dmindist(p0, points.get(i)));

        }

        return surface.data0d.calculerPoint3D(u, v).mult(mult / points.size());

    }
}
