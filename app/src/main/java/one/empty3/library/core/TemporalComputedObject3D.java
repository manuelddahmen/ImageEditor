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

package one.empty3.library.core;

import one.empty3.library.Point3D;
import one.empty3.library.T;

/*__
 * Created by manue on 05-01-20.
 */
public interface TemporalComputedObject3D {
    public double T(T t);

    public Point3D calculerPointT(double t);

    public Point3D calculerCurveT(double u, double t);

    public Point3D calculerSurfaceT(double u, double v, double t);
}
