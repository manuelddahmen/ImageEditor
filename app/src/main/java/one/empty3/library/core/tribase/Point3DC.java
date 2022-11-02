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
import one.empty3.library.core.nurbs.ParametricCurve;

/*__
 * Created by manue on 23-07-19.
 */
public class Point3DC extends ParametricCurve {
    private StructureMatrix<Point3D> o;

    public Point3DC() {
        super();
        this.o.setElem(new Point3D(Point3D.O0));
        getDeclaredDataStructure().put("o/Point::Curve", o);
    }

    public Point3DC(Point3D o0) {
        this();
        o.setElem(o0);

    }

    @Override
    public Point3D calculerPoint3D(double t) {
        return o.getElem();
    }
}
