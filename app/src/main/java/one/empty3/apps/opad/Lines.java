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

package one.empty3.apps.opad;

import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

public class Lines extends ParametricCurve {
    private final Path path;

    public Lines(Path path) {
        this.path = path;
    }


    @Override
    public Point3D calculerPoint3D(double t) {
        try {
            int segm = (int) (t * path.size());

            if (path.size() >= 2) {
                LineSegment segmentDroite = new LineSegment(path.get(segm), path.get(segm + 1));

                double v = Math.IEEEremainder(t * path.size(), segm);

                return segmentDroite.calculerPoint3D(v);
            } else
                return Point3D.O0;
        } catch (Exception ex) {
            return Point3D.O0;
        }
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        return null;
    }
}
