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

package one.empty3.library.core.move;

import one.empty3.library.*;

/*__
 * Created by manuel on 29-06-17.
 */
public class LineTrajectory extends SimpleTrajectory {
    protected Point3D a;
    private Point3D b;
    private double speed = 0.0;

    LineTrajectory(LineSegment sd, double speed) {
        a = sd.getOrigine();
        b = sd.getExtremite();
        this.speed = speed;
    }

    public void swapAB() {
        Point3D w = a;
        a = b;
        b = w;
    }

    public Point3D calculerPoint3D(double timeEllapsedNano) {
        Point3D point3D = a.plus(b.moins(a).mult(speed * timeEllapsedNano));
        return point3D;

    }
}
