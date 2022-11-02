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
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class CourbeParametriquePolynomialeBezier extends CourbeParametriquePolynomiale {

    public CourbeParametriquePolynomialeBezier() {

        super();
    }

    public CourbeParametriquePolynomialeBezier(Point3D[] coefficients) {
        super(coefficients);
    }

    public double B(int i, int n, double t) {
        return factorielle(n) / factorielle(i) / factorielle(n - i)
                * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }


    @Override
    public Point3D calculerPoint3D(double t) {
        Point3D sum = Point3D.O0;
        int N = coefficients.getData1d().size();
        //double bSum = 0.0;
        for (int i = 0; i < N; i++) {
            //bSum += B(i, N, t);
            sum = sum.plus(coefficients.getElem(i).mult(B(i, N - 1, t)));
        }
        return sum;
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        throw new UnsupportedOperationException("pas encore implanté");
    }

    protected double factorielle(int n) {
        double sum = 1;
        for (int i = 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    public void declareProperties() {
        super.declareProperties();
    }

    @Override
    public String toString() {
        String s = "bezier(";
        for (int i = 0; i < coefficients.getData1d().size(); i++)
            s += "\n" + coefficients.getElem(i).toString() + "\n";
        return s + ")\n";
    }

}
