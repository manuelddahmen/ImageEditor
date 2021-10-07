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
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.nurbs;

import one.empty3.library.*;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class NurbsSurface extends ParametricSurface {

    public static final int type_coordU = 0;
    public static final int type_coordV = 1;
    /*__
     * *
     * degreeU degré de la fonction de base B_spline pour U
     */
    private int degreeU;
    /*__
     * *
     * degreeV degré de la fonction de base B_spline pour V
     */
    private int degreeV;
    private Point3D[][] points;
    private double[][] poids;
    private double[][] T;
    private Intervalle intervalle;
    private Point3DPoids forme;

    public NurbsSurface() {
    }

    @Override
    public Point3D coordPoint3D(int x, int y) {
        return calculerNurbs(1.0 * x / getMaxX(), 1.0 * y / getMaxY());
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return calculerNurbs(u, v);
    }

    @Override
    public Point3D calculerVitesse3D(double u, double v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void creerNurbs() {
        if (points != null && T != null && poids != null) {
            intervalle = new Intervalle(T[0], T[1]);
            forme = new Point3DPoids(points, poids);

            for (int i = 0; i < forme.m; i++) {
                for (int j = 0; j < forme.n; j++) {
                    forme.set(i, j, points[i][j], poids[i][j]);
                }
            }

        }
    }

    public double f0sur0egal0(double t1, double t2) {
        if (t2 == 0 && t1 == 0) {
            return 0;
        } else {
            return t1 / t2;
        }
    }

    /*__
     * *
     * Méthode non utilisée
     */
    public int coefficients(int type_coord, double t) {
        if (t <= intervalle.get(type_coord, 0)) {
            return 0;
        }
        for (int i = 0; i < (type_coord == 0 ? intervalle.m : intervalle.n); i++) {
            if ((t >= intervalle.get(type_coord, i)) && (t < intervalle.get(type_coord, i + 1))) {
                return i;
            }
        }
        return 1;
    }

    public boolean estDansLIntervalle(int type_coord, double t, int borneInf) {
        if (borneInf < 0)
            return false;
        if (borneInf >= (type_coord == 0 ? intervalle.m : intervalle.n) - 1)
            return false;
        for (int i = 0; i < (type_coord == 0 ? intervalle.m : intervalle.n) - 1; i++) {
            if (intervalle.get(type_coord, i) >= t && intervalle.get(type_coord, i + 1) <= t)
                return true;
        }
        return false;
    }

    public void setMaillage(Point3D[][] points, double[][] poids) {
        this.points = points;
        this.poids = poids;
    }

    /*__
     * @param T Ligne 0: intervalle u Ligne 1: intervalle v
     */
    public void setReseauFonction(double[][] T) {
        this.T = T;
    }

    public double N(int type_coord, int i, int deg, double t) {
        if (!estDansLIntervalle(type_coord, t, i)) {
            return 0;
        }
        if (deg <= 0) {
            return 1;
        }
        return N(type_coord, i, deg - 1, t)
                * f0sur0egal0(t - intervalle.get(type_coord, i),
                intervalle.get(type_coord, i + deg) - intervalle.get(type_coord, i))
                + N(type_coord, i + 1, deg - 1, t)
                * f0sur0egal0(intervalle.get(type_coord, i + deg + 1) - t,
                intervalle.get(type_coord, i + deg + 1) - intervalle.get(type_coord, i + 1));
    }

    public long C(int i, int n) {
        return factorielle(n) / factorielle(i) / factorielle(n - i);
    }

    protected long factorielle(int n) {
        long sum = 1;
        for (int i = 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    public void setDegreU(int deg) {
        this.degreeU = deg;
    }

    public void setDegreV(int deg) {
        this.degreeV = deg;
    }

    public Point3D calculerNurbs(double u, double v) {
        double sum = 0;
        Point3D ret = Point3D.O0;
        for (int i = 0; i < forme.m; i++) {
            for (int j = 0; j < forme.n; j++) {
                double sumP = (double) (C(i, forme.m) * C(j, forme.n)) * N(type_coordU, i, degreeU, u) * N(type_coordV, j, degreeV, v);
                ret = ret.plus(forme.getPoint3D(i, j).mult(sumP));
                sum += sumP;
            }
        }
        return ret.mult(1 / sum);
    }

    @Override
    public String toString() {
        String s = "nurbs ( \n";
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < (i == 0 ? intervalle.m : intervalle.n); j++) {
                s += "knot [" + i + "][" + j + "] = " + intervalle.get(i, j) + "; \n\t";
            }
        }
        for (int i = 0; i < forme.m; i++) {
            for (int j = 0; j < forme.n; j++) {
                s += "point[" + i + "][" + j + "] = " + forme.getPoint3D(i, j) + "; w[" + i + "][" + j + "] = " + forme.getPoids(i, j) + ";\n\t";
            }
        }
        return s + "\n\n)";
    }

    /*__
     * *
     * "Knots"
     */
    class Intervalle {

        /*__
         * Data : tableau à 2 lignes longueur de la première ligne degreeU + 1 :
         * premiers nombres égaux à a (en particulier a==0) r points croissant
         * de a à b degreeU + 1 : derniers nombres égaux à b (en particulier
         * b==1) deuxième ligne: degreeV + 1 : premiers nombres égaux à c (en
         * particulier c==0) r points croissant de c à d degreeV + 1 : derniers
         * nombres égaux à d (en particulier d==1)
         */
        private final double[][] Data;
        private final int m, n;

        private Intervalle(double[] Tu, double[] Tv) {
            this.Data = new double[][]{Tu, Tv};
            m = Data[0].length;
            n = Data[1].length;
        }

        public double get(int i, int j) {
            return this.Data[i][j];
        }

        public void set(int i, int j, double v) {
            this.Data[i][j] = v;
        }
    }

    /*__
     * *
     * Point3D Weight associated
     */
    class Point3DPoids {

        final int m, n;
        private final Point3D[][] points;
        private final double[][] poids;

        public Point3DPoids(Point3D[][] poins, double[][] poids) {
            this.points = poins;
            this.poids = poids;
            m = points.length;
            n = points[0].length;
        }

        private double getPoids(int i, int j) {
            return poids[i][j];
        }

        public Point3D getPoint3D(int i, int j) {
            return points[i][j];
        }

        public void set(int i, int j, Point3D p, double w) {
            if (i >= 0 && i < m && j >= 0 && j < n) {
                points[i][j] = p;
                poids[i][j] = w;
            }

        }
    }

}
