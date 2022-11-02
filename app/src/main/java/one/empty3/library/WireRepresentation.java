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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.SurfaceParametricPolygonalBezier;

/*__
 * @author Se7en
 */
public class WireRepresentation extends RepresentableConteneur {

    private final ParametricSurface surface;
    private Point3D[][] pts;

    public WireRepresentation(ParametricSurface parametricSurface) {

        this.surface = parametricSurface;
        getRP();
    }

    public WireRepresentation(Point3D[][] point3DS) {

        this.surface = new SurfaceParametricPolygonalBezier(point3DS);
        getRP();
    }

    public void getRP() {
        this.clear();
        for (double i = surface.getStartU(); i < surface.getEndU()
                ; i += surface.getIncrU()) {
            for (double j = surface.getStartV();
                 j < surface.getEndV()
                    ; j += surface.getIncrV()) {
                this.add(
                        new LineSegment(
                                surface.calculerPoint3D(i, j),
                                surface.calculerPoint3D(i, j +
                                        surface.getIncrV()),
                                surface.calculerPoint3D(i, j).getTexture()));
                this.add(
                        new LineSegment(
                                surface.calculerPoint3D(i, j),
                                surface.calculerPoint3D(i + surface.getIncrU(), j + 1),
                                surface.calculerPoint3D(i, j).getTexture()));

            }
        }
    }

}
