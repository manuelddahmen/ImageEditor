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

import one.empty3.library.Point3D;

/*__
 * Created by manue on 30-07-19.
 */
public class P3Matrix {
    private Point3D[][] points;

    public P3Matrix() {

    }

    public P3Matrix(int dims, int dim1, int dim2) {
        if (dims == 1) {
            points = new Point3D[1][dim1];
        } else if (dims == 2) {
            points = new Point3D[dim2][dim1];
        }
    }

    public void insert(int pos1, int pos2, int dim) {
        Point3D[][] copy = null;
        if (dim == 1) {
            copy = new Point3D[points.length + 1][points[0].length];
        } else if (dim == 2) {
            copy = new Point3D[points.length][points[0].length + 1];
        } else {
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                copy[i][j] = copy[i][j];
                if (pos1 == i)
                    i++;
                else if (pos2 == j)
                    j++;
            }
        }
        if (copy != null) {
            points = copy;
        }
    }
}
