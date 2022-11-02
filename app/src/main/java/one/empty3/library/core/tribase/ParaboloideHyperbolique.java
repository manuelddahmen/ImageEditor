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
 * Global license :
 * <p>
 * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 ***/


package one.empty3.library.core.tribase;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class ParaboloideHyperbolique extends ParametricSurface {
    private double a;
    private double b;
    private double h;

    {
        setStartU(-1.0);
        setStartV(-1.0);
        setEndU(1.0);
        setEndV(1.0);
    }

    public ParaboloideHyperbolique(double a, double b, double h) {
        this.a = a;
        this.b = b;
        this.h = h;
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return new Point3D(a / 2 * (u + v), b / 2 * (u - v), h * u * v);
    }


}
