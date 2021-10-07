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

package one.empty3.library;

public class Position extends Representable {

    public Point3D position = Point3D.O0;
    public Matrix33 rotation = Matrix33.I;
    public double agrandissement = 1.0;

    public Position() {
    }

    public Point3D calculer(Point3D p0) {
        if (p0 == null) {
            System.err.println("Erreur de positionnement p0==null");
        }
        Point3D res = p0;
        if (position != null) {
            res = res.plus(position);
        }
        if (rotation != null) {
            res = rotation.mult(res);
        }
        if (agrandissement != 1d && position != null) {
            res = position.plus(res.moins(position).mult(agrandissement));
        }
        return res;
    }

    @Override
    public String toString() {
        return "position (\t\t@ " + position.toString()
                + "\t\t* " + rotation.toString()
                + "\t\t*" + agrandissement
                + " \t)\n";
    }
}
