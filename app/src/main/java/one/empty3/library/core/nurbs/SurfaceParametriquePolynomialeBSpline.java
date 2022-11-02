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
 * Creation time 17-sept.-2014
 * <p>
 * *
 */
package one.empty3.library.core.nurbs;

import one.empty3.library.*;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class SurfaceParametriquePolynomialeBSpline extends ParametricSurface implements SurfaceElem {

    private final Point3D[][] P;
    private final int uDegree, vDegree;
    private final double[][] intervalles;
    protected double[] U, V;

    public SurfaceParametriquePolynomialeBSpline(double[] U, double[] V, Point3D[][] P, int uDegree, int vDegree) {
        this.U = U;
        this.V = V;
        this.intervalles = new double[2][];
        intervalles[0] = U;
        intervalles[1] = U;
        this.P = P;
        this.uDegree = uDegree;
        this.vDegree = vDegree;
    }

    public Point3D calculerPoint3D(double t) {
        Point3D sum = Point3D.O0;
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                sum = sum.plus(P[i][j].mult(N(i, uDegree, t, 0) * N(j, vDegree, t, 1)));
            }
        }
        return sum;
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point3D calculerVitesse3D(double u, double v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double N(int i, int degree, double t, int dim01) {
        if (degree == 0) {

            if (t >= intervalles[dim01][0] && t <= intervalles[dim01][intervalles.length - 1]
                    && t >= intervalles[dim01][i] && t < intervalles[dim01][i + 1]) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return (t - intervalles[dim01][i]) / (intervalles[dim01][i + degree] - intervalles[dim01][i])
                    * N(i, degree - 1, t, dim01)
                    + (intervalles[dim01][i + degree + 1] - t) / (intervalles[dim01][i + degree + 1] - intervalles[dim01][i + 1])
                    * N(i + 1, degree - 1, t, dim01);
        }
    }

}
