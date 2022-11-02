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

import one.empty3.library.core.nurbs.ParametricCurve;

import java.util.List;

/*__
 * Created by manue on 20-06-19.
 */
public class PolyLine extends ParametricCurve {
    StructureMatrix<Point3D> points = new StructureMatrix<>(1, Point3D.class);

    public PolyLine() {
        super();

        //points.add(1, Point3D.random(1d));
        //points.add(1, Point3D.random(1d));
    }

    public List<Point3D> getPoints() {
        return points.getData1d();
    }

    public void setPoints(Point3D[] points) {

        this.points.setAll(points);
    }

    @Override
    public Point3D calculerPoint3D(double t) {
        int size = points.getData1d().size();
        int i = (int) t * size;
        if (i >= size)
            i = size - 1;
        int j = (i + 1) >= size ? i : i + 1;
        Point3D p1 = points.getData1d().get(i);
        Point3D p2 = points.getData1d().get(j);
        double d = t - 1.0 * i / size;
        Point3D plus = p1.plus(p2.moins(p1).mult(1 - d));
        return plus;
    }

    public void add(Point3D point3D) {
        int newLength;
        if (points == null)
            points = new StructureMatrix<>(1, Point3D.class);
        else {
            newLength = points.getData1d().size() + 1;
            List<Point3D> tmp = points.getData1d();
            points = new StructureMatrix<>();
            for (int i = 0; i < tmp.size(); i++)
                points.add(i, tmp.get(i));
            points.add(1, point3D);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\r\nPolyLine ( \r\n");
        for (int i = 0; i < points.getData1d().size(); i++)
            sb.append("\t" + points.getData1d().get(i) + "\r\n");
        sb.append(texture);
        sb.append("\r\n)\r\n");
        return sb.toString();
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("points/Points de la ligne brisée", points);
    }
}
