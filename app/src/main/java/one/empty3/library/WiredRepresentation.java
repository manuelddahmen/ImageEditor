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

/*__
 * @author Se7en
 */
public class WiredRepresentation extends RepresentableConteneur {

    private Point3D[][] pts;

    public WiredRepresentation(Point3D[][] pts) {
        this.pts = pts;
    }

    public RepresentableConteneur getRP() {
        RepresentableConteneur rp = new RepresentableConteneur();

        for (int i = 0; i < pts.length; i++) {
            for (int j = 0; j < pts[0].length; j++) {
                if (j + 1 < pts[0].length) {
                    this.add(
                            new LineSegment(pts[i][j], pts[i][j + 1], pts[i][j].getTexture()));
                }

                if (i + 1 < pts.length) {
                    this.add(
                            new LineSegment(pts[i][j], pts[i + 1][j], pts[i][j].getTexture()));
                }

            }
        }
        return rp;
    }

}
