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
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricSurface;

/*__
 * @author DAHMEN Manuel
 *         <p>
 *         dev
 * @date 22-mars-2012
 */
public class TRIEllipsoide extends ParametricSurface {

    private StructureMatrix<Point3D> centre = new StructureMatrix<>(0, Point3D.class);
    private StructureMatrix<Double> radius = new StructureMatrix<>(1, Double.class);

    public TRIEllipsoide() {
        centre.setElem(Point3D.O0);
        radius.add(1, 10.0);
        radius.add(1, 10.0);
        radius.add(1, 10.0);
        setCirculaireY(true);
        setCirculaireX(false);
    }

    public TRIEllipsoide(Point3D c, Double rx, Double ry, Double rz) {
        this();
        this.centre.setElem(c);
        this.radius.setElem(rx, 0);
        this.radius.setElem(ry, 1);
        this.radius.setElem(rz, 2);
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        Double b = 1.0 * u * Math.PI - Math.PI / 2;
        Double a = 1.0 * v * 2 * Math.PI;

        Point3D centre = this.centre.getElem();

        Point3D p
                =
                new Point3D(centre.getX() + radius.getElem(0) * Math.sin(a) * Math.sin(b), centre.getY() + radius.getElem(1) * Math.sin(a) * Math.cos(b),
                        centre.getZ() + radius.getElem(2) * Math.cos(a));
        return p;
    }


    public Point3D getCentre() {
        return centre.getElem();
    }

    public void setCentre(Point3D centre) {
        this.centre.setElem(centre);
    }


    @Override
    public String toString() {
        return "Ellipsoide(\n\t" + centre.toString()
                + "\n\t" + radius.getElem(0)
                + "\n\t" + radius.getElem(1)
                + "\n\t" + radius.getElem(2)
                + "\n\t"
                + texture.toString() + "\n)\n";
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("centre/centre", centre);
        getDeclaredDataStructure().put("radius/radius(x,y,z)", radius);

    }

    public void setCentre(StructureMatrix<Point3D> centre) {
        this.centre = centre;
    }

    public StructureMatrix<Double> getRadius() {
        return radius;
    }

    public void setRadius(StructureMatrix<Double> radius) {
        this.radius = radius;
    }
}
