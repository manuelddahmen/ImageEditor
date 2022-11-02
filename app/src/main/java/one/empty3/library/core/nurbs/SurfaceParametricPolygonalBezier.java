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

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class SurfaceParametricPolygonalBezier extends ParametricSurface implements SurfaceElem {

    protected final StructureMatrix<Point3D> coefficients;
    protected StructureMatrix<Integer> power1 = new StructureMatrix<>(0, Integer.class), power2 = new StructureMatrix<>(0, Integer.class);

    public SurfaceParametricPolygonalBezier(Point3D[][] coefficients) {
        this.coefficients = new StructureMatrix<>(2, Integer.class);
        this.coefficients.setAll(coefficients);
        power1.setElem(coefficients.length);
        power2.setElem(coefficients[0].length);
    }

    public double B(int i, int n, double t) {
        return factorielle(n) / factorielle(i) / factorielle(n - i)
                * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        Point3D sum = Point3D.O0;
        for (int i = 0; i < power1.getElem(); i++) {
            for (int j = 0; j < power2.getElem(); j++) {
                sum = sum.plus(coefficients.getElem(i, j).mult(B(i, power1.getElem() - 1, u) * B(j, power2.getElem() - 1, v)));
            }
        }
        return sum;
    }

    @Override
    public Point3D calculerVitesse3D(double u, double v) {
        throw new UnsupportedOperationException("pas encore implanté");
    }

    protected double factorielle(int n) {
        double sum = 1;
        for (int i = 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    public StructureMatrix<Point3D> getCoefficients() {
        return coefficients;
    }

    public Integer getPower1() {
        return power1.getElem();
    }

    public void setPower1(Integer power1) {
        this.power1.setElem(power1);
    }

    public Integer getPower2() {
        return power2.getElem();
    }

    public void setPower2(Integer power2) {
        this.power2.setElem(power2);
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("power1/degré en ligne", power1);
        getDeclaredDataStructure().put("power2/degré en colomnne", power1);
        getDeclaredDataStructure().put("coefficients", coefficients);
    }
}
