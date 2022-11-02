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

import one.empty3.library.core.nurbs.ParametricSurface;

/*__
 * Created by Win on 28-08-18.
 */
public class Citron extends ParametricSurface {
    private Circle circle;

    public Citron(Axe axis, Point3D center, double radius) {

        circle = new Circle(axis, radius);
    }

    public Point3D calculerPoint3D(double u, double v) {
        Circle c = circle;
        return c.getCenter().plus(
                c.vectX.mult(circle.getRadius() *
                        Math.cos(2.0 * Math.PI * u) * Math.cos(2.0 * Math.PI * v)).plus(
                        c.vectY.mult(circle.getRadius() *
                                        Math.sin(2.0 * Math.PI * u) * Math.cos(2.0 * Math.PI * v))
                                        .
                                plus(c.vectZ.mult((v - 0.5) / 2 * Math.sin(2 * Math.PI * v)))
                ).mult(c.radius.getElem()));
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
