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
 * Global license :  *
 * Microsoft Public Licence
 *
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 *
 Creation time 05-nov.-2014
 *
 **
 */
package one.empty3.apps.opad;

import one.empty3.library.ColorTexture;
import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;
import one.empty3.library.RepresentableConteneur;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.awt.*;

/*__
 *
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class SolPP {

    public double increment = 0.01;
    private ParametricSurface sol;

    public SolPP() {
    }

    public ParametricSurface getObject() {
        return sol;
    }

    public SolPP(ParametricSurface ps) {
        this.sol = ps;
    }

    public RepresentableConteneur generateWire() {
        RepresentableConteneur rc = new RepresentableConteneur();


        for (double i = 0; i < 1; i += increment) {
            for (double j = 0; j < 1; j += increment) {
                Point3D p1 = sol.calculerPoint3D(j, i);
                Point3D p2 = sol.calculerPoint3D(j + increment, i);

                rc.add(new LineSegment(p1, p2, new ColorTexture(Color.WHITE)));

                p1 = sol.calculerPoint3D(j, i);
                p2 = sol.calculerPoint3D(j, i + increment);

                rc.add(new LineSegment(p1, p2, new ColorTexture(Color.WHITE)));
            }
        }


        return rc;
    }
}
