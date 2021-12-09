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

public class BezierCubique2D extends Representable {

    public static final int DIM2 = 200;
    public static final int DIM1 = 200;
    /*__
     *
     */
    private final int ordreU = 4;
    private final int ordreV = 4;
    private final int ordre = 4;
    private String id;
    private TextureCol color;
    /*__
     * *
     * 11 12 13 14 0123 21 22 23 24 4567 31 32 33 34 8901 41 42 43 44 2345
     */
    private Point3D[][] controle;

    private Barycentre position;

    public BezierCubique2D() {
    }

    public BezierCubique2D(Point3D[][] controle) {
        this.controle = controle;

    }

    private double b(double u, double n, double i) {
        return factorielle(n) / factorielle(i) / factorielle(n - i)
                * Math.exp(Math.log(u) * i)
                * Math.exp(Math.log(1 - u) * (n - i));
    }

    private double b2(double u, double n, double i) {
        return factorielle(n) / factorielle(i) / factorielle(n - i)
                * Math.pow(u, i)
                * Math.pow(1 - u, n - i);
    }

    @SuppressWarnings("unused")
    private Point3D bernstein(double u, double v) {
        int n = ordre;
        Point3D p = new Point3D(0d, 0d, 0d);
        for (int i = 0; i < ordre; i++) {
            for (int j = 0; j < ordre; j++) {
                p = p.plus(controle[i][j].mult(b(u, n, i) * b(v, n, j)));
            }
        }
        return p;
    }

    /*private double b(int n, int i, double u) {
     return 1.0 * factorielle(n) / factorielle(i) / factorielle(n - i)
     * Math.exp(Math.log(u) * i)
     * Math.exp(Math.log(1 - u) * (n - i));
     }
     �*/
    public Point3D calculerPoint3D(double tx, double ty) {
        return update(tx, ty);
        /*}
         else
         {
         return bernstein(tx, ty);
         }*/
    }

    private double factorielle(double n) {
        double ret = 1d;
        for (int i = 1; i < n; i++) {
            ret *= i;
        }
        return ret;
    }

    public Point3D[][] getControle() {
        //updateToDate = false;
        return controle;
    }

    public Point3D getControle(int l, int c) {
        return controle[l][c];
    }

    public String getId() {
        return id;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setControle(int l, int c, Point3D p) {
        controle[l][c] = p;
        //updateToDate = false;
    }

    @Override
    public String toString() {
        String s = "bezier2d ( \n\t";
        s += color.toString() + " \n\t\t(\n";
        for (int l = 0; l < 4; l++) {
            for (int c = 0; c < 4; c++) {
                s += "\t\t\t" + controle[l][c].toString() + " \n";
            }
        }
        return s + "\t)\n";
    }

    public Point3D update(double u, double v) {
        Point3D res = Point3D.O0;
        Point3D[] q = new Point3D[ordre];
        for (int i = 0; i < ordreU; i++) {
            q[i] = Point3D.O0;
            for (int j = 0; j < ordreV; j++) {
                q[i] = q[i].plus(getControle(i, j).mult(b2(u, 3, j)));
            }
            res = res.plus(q[i].mult(b2(v, 3, i)));
        }
        return res;

    }

}
