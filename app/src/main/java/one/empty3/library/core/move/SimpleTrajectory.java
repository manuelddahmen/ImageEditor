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

/*__
 * *
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.move;

import one.empty3.library.*;

import java.util.ArrayList;
import java.util.Collection;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public abstract class SimpleTrajectory implements Trajectory {

    public long nanoTime;
    private ArrayList<Point3D> liste = new ArrayList<Point3D>();

    public boolean hasMorePoints() {
        return !liste.isEmpty();
    }

    public Point3D getNextPointAndRemove() {
        Point3D p = liste.get(0);
        liste.remove(p);
        return p;
    }

    public Point3D[] getIntermediatePointsUntilNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addPoints(Collection<Point3D> points) {
        liste.addAll(points);
    }

    public void addPoints(Point3D[] points) {
        for (int i = 0; i < points.length; i++) {
            liste.add(points[i]);
        }
    }

    public abstract Point3D calculerPoint3D(double timeEllapsedNano);
}
