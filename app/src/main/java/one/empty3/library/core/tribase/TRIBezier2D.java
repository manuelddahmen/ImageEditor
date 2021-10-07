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

import one.empty3.library.Barycentre;
import one.empty3.library.BezierCubique2D;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;

@SuppressWarnings("serial")
public class TRIBezier2D extends ParametricSurface {

    private BezierCubique2D bez;
    private Barycentre position;

    public TRIBezier2D(BezierCubique2D bez) {
        this.bez = bez;
        setCirculaireX(false);
        setCirculaireY(false);
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return bez.calculerPoint3D(u, v);
    }

    @Override
    public Point3D coordPoint3D(int a, int b) {
        return bez.calculerPoint3D(1.0 * a / getMaxX(), 1.0 * b / getMaxY());
    }

}
