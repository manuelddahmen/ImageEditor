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

 Vous êtes libre de :

 */
package one.empty3.library;

public class Perspective {

    public static final int P_CUBIQUE_ISOMETRIQUE = 0;
    public static final int P_CUBIQUE_LINEAIRE = 1;
    public static final int P_CUBIQUE_FONCTION = 2;
    private int type = P_CUBIQUE_ISOMETRIQUE;

    private Perspective(int t) {
        this.type = t;
    }

    public static final Perspective getInstance(int type) {
        return new Perspective(type);
    }

    javaAnd.awt.Point coordonneeEcran(Point3D p) {
        if (type == P_CUBIQUE_ISOMETRIQUE) {
            return new javaAnd.awt.Point((int) (double) p.getX(), (int) (double) p.getY());
        } else if (type == P_CUBIQUE_LINEAIRE) {
            if (p.getZ() == 0) {
                return new javaAnd.awt.Point(0, 0);
            } else {
                return new javaAnd.awt.Point((int) (p.getX() / p.getZ()),
                        (int) (p.getY() / p.getZ()));
            }
        } else if (type == P_CUBIQUE_FONCTION) {
            return new javaAnd.awt.Point(
                    (int) (p.getX() / f(p.getZ())),
                    (int) (p.getY() / f(p.getZ())));
        }
        return null;
    }

    protected double f(double z) {
        return z;
    }
}
