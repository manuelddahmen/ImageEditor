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

package one.empty3.library.core.nurbs;

import one.empty3.library.*;

/*__
 * Created by manue on 13-02-19.
 */
public abstract class ThickSurface extends ParametricSurface {
    private boolean isThick;
    private double innerWidth = 0.0;
    private double outerWidth = 0.0;
    private boolean isInnerWidth;
    private boolean isOuterWidth;
    private double sign = 1;

    public Point3D computeExt(double u, double v) {
        return
                calculerPoint3D(u + getIncrU(), v)
                        .moins(calculerPoint3D(u - getIncrU(), v))
                        .prodVect(
                                calculerPoint3D(u, v + getIncrV()).
                                        moins(calculerPoint3D(u, v - getIncrV())))
                        .norme1().mult(outerWidth).
                        plus(calculerPoint3D(
                                u, v
                        ));

    }

    public Point3D computeInt(double u, double v) {
        return
                calculerPoint3D(u + getIncrU(), v)
                        .moins(calculerPoint3D(u - getIncrU(), v))
                        .prodVect(
                                calculerPoint3D(u, v + getIncrV()).
                                        moins(calculerPoint3D(u, v - getIncrV())))
                        .norme1().mult(-innerWidth).
                        plus(calculerPoint3D(
                                u, v
                        ));
    }

    public void setOuterWidth(double outerWidth) {
        this.outerWidth = outerWidth;
    }

    public void setInnerWidth(double innerWidth) {
        this.innerWidth = innerWidth;
    }
}
