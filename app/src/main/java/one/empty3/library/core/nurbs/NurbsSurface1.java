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

import one.empty3.library.Point3D;
import one.empty3.library.core.tribase.TRIObjetGenerateurAbstract;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class NurbsSurface1 extends TRIObjetGenerateurAbstract {

    public static final int type_coordU = 0;
    public static final int type_coordV = 1;
    /*__
     * *
     * <p>
     * degree:
     * <p>
     * Degré de la fonction de base
     * <p>
     * params[]:
     * <p>
     * Tableau de noeuds weights[]: * Tableau de pondérations pour des courbes
     * NURBS rationnelles ; sinon NULL ou 1.0 pour une b-spline polynomiale.
     * c_pnts[][3]:
     * <p>
     * Tableau de points de contrôle Définition : k = degree of basis function N
     * = number of knots, degree -2 wi = weights Ci = control points (coordArr, y, z) *
     * wi Bi,k = basis functions Par cette équation, le nombre de points de
     * contrôle est égal à N+1.
     */
    private int degreeU;
    private int degreeV;
    private Point3D[][] maillage;
    private Point3D[][] points;
    private double[][] poids;
    private double[][] T;
    private Intervalle intervalle;
    private Point3DPoids forme;

    public NurbsSurface1() {
    }

    @Override
    public Point3D coordPoint3D(int u, int v) {

        return maillage[u][v];
    }

    public void creerNurbs() {
        if (points != null && T != null && poids != null) {
            intervalle = new Intervalle(T);
            forme = new Point3DPoids(points, poids);

            for (int i = 0; i < forme.m; i++) {
                for (int j = 0; j < forme.n; j++) {
                    forme.set(i, j, points[i][j], poids[i][j]);
                }
            }
            maillage = maillage();

        }
    }

    public double f0sur0egal0(double t1, double t2) {
        if (t2 == 0 && t1 == 0) {
            return 0;
        } else {
            return t1 / t2;
        }
    }

    public int coefficients(int type_coord, double t) {
        if (t <= intervalle.get(type_coord, 0)) {
            return 0;
        }
        for (int i = 0; i < intervalle.m; i++) {
            if ((t >= intervalle.get(type_coord, i)) && (t < intervalle.get(type_coord, i + 1))) {
                return i;
            }
        }
        return 1;
    }

    public void setMaillage(Point3D[][] points, double[][] poids) {
        this.points = points;
        this.poids = poids;
    }

    public void setReseauFonction(double[][] T) {
        this.T = T;
    }

    public double N(int type_coord, int i, int deg, double t) {
        if (i >= intervalle.m || i < 0) {
            return 0;
        }
        if (deg <= 0) {
            if (coefficients(type_coord, t) == i) {
                return 1;
            } else {
                return 0;
            }
        }
        return N(type_coord, i, deg - 1, t)
                * f0sur0egal0(t - intervalle.get(type_coord, i),
                intervalle.get(type_coord, i + deg - 1) - intervalle.get(type_coord, i))
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

    public Point3D[][] maillage() {
        Point3D[][] qres = new Point3D[getMaxX() + 1][getMaxY() + 1];

        int i;
        int npts, mpts;
        int k, l;
        int p1, p2;

        npts = points.length;
        mpts = points[0].length;
        k = degreeU;
        l = degreeV;

        double[] b = new double[npts * mpts * 4 + 1];
        double[] q = new double[getMaxX() * getMaxY() * 4 + 4];

        char[] header = new char[80];
        int hdrlen;
        /*
         Data for the standard test control net.
         Comment out to use file input.
         */

        p1 = getMaxX();
        p2 = getMaxY();

        System.out.printf("k,l,npts,mpts,p1,p2 = %d %d %d %d %d %d \n", k, l, npts, mpts, p1, p2);

        for (i = 1; i <= b.length - 1; i++) {
            b[i] = 0.;
        }

        for (i = 1; i <= q.length - 1; i++) {
            q[i] = 0.;
        }
        for (i = 1; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                for (int k2 = 0; k2 < 3; k2++) {
                    b[(j * points[i].length + i) * 4 + k2] = forme.getPoint3D(i, j).get(k2);
                }
                b[(j * points[i].length + i) * 4 + 3] = forme.getPoids(i, j);
            }
        }

        Nurbs.rbspsurf(k, l, npts, mpts, p1, p2, b, q);

        /*        for(i=1; i<q.length-2; i+=3)
         {
         System.out.printf("Q[%d] =%f, %f, %f\n", i, q[i], q[i+1], q[i+2]);
         }
         for (i = 1; i <= 3*p1*p2; i=i+3){
         System.out.printf("%f, %f, %f \n",q[i],q[i+1],q[i+2]);
         }
         */
        for (i = 0; i < getMaxX() + 1; i++) {
            for (int j = 0; j < getMaxY() + 1; j++) {
                qres[i][j] = new Point3D();
                for (int k2 = 0; k2 <= 2; k2++) {
                    int qindex = (j * getMaxX() + i) * 3 + k2 + 1;
                    //if(i<qres.length&&j<qres[i].length&&k2<=2&&qindex<q.length)
                    qres[i][j].set(k2, q[qindex]);
                }
            }
        }
        return qres;
    }

    @Override
    public String toString() {
        String s = "nurbs ( \n";
        for (int i = 0; i < intervalle.m; i++) {
            for (int j = 0; j < intervalle.n; j++) {
                s += "knot (" + i + "," + j + ")=" + intervalle.get(i, j) + "\n\t";
            }
        }
        for (int i = 0; i < forme.m; i++) {
            for (int j = 0; j < forme.n; j++) {
                s += "point (" + i + "," + j + ")=" + forme.getPoint3D(i, j) + "  Poids : (" + i + "," + j + ")" + forme.getPoids(i, j) + "\n\t";
            }
        }
        return s + "\n\n)";
    }

    /*__
     * *
     * "Knots"
     */
    class Intervalle {

        private final double[][] Data;
        private final int m, n;

        private Intervalle(double[][] T) {
            this.Data = T;
            m = T.length;
            n = T[0].length;
        }

        public double get(int i, int j) {
            try {
                return this.Data[i][j];
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                return 0;
            }
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
