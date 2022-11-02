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


public class Sphere extends ParametricSurface {
    protected StructureMatrix<Circle> circle = new StructureMatrix<>(0, Circle.class);

    public Sphere() {
        super();
        circle.setElem(new Circle());


    }

    public Sphere(Axe axis, double radius) {
        this();
        circle.setElem(new Circle(axis, radius));
    }

    public Sphere(Point3D center, double radius) {
        this();
        getCircle().getAxis().setElem(new Axe(center.plus(Point3D.Y.mult(radius)), center.plus(Point3D.Y.mult(-radius))));
        getCircle().setRadius(radius);
    }

    public Point3D calculerPoint3D(double u, double v) {
        Circle c = circle.getData0d();
        if (!c.isCalculerRepere1()) {
            c.calculerRepere1();
        }
        return c.getCenter().plus(
                c.vectX.mult(
                        Math.cos(2.0 * Math.PI * u) * Math.cos(-Math.PI / 2 + Math.PI * v)).plus(
                        c.vectY.mult(
                                        Math.sin(2.0 * Math.PI * u) * Math.cos(-Math.PI / 2 + Math.PI * v))
                                .plus(c.vectZ.mult(Math.sin(-Math.PI / 2 + Math.PI * v)))
                ).mult(c.radius.getElem()));
    }

    public Circle getCircle() {
        return circle.getData0d();
    }

    public void setCircle(Circle circle) {
        this.circle.setElem(circle);
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("circle/circle", circle);

    }

    @Override
    public String toString() {
        return "sphere (\n\t" + circle.toString() + "\n\t" + texture.toString() + "\n)\t";
    }
}
