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
import one.empty3.library.StructureMatrix;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class CourbeParametriquePolynomiale extends ParametricCurve {

    protected StructureMatrix<Point3D> coefficients;
    protected StructureMatrix<Integer> power = new StructureMatrix<>(0, Integer.class);

    public CourbeParametriquePolynomiale(Point3D[] coefficients) {
        super();
        this.coefficients = new StructureMatrix<Point3D>(1, Point3D.class);
        this.coefficients.setAll(coefficients);
        this.power.setElem(coefficients.length);
    }

    public CourbeParametriquePolynomiale() {
        super();
        coefficients = new StructureMatrix<>(1, Point3D.class);
        coefficients.setElem(new Point3D(0d, 0d, 0d), 0);
        coefficients.setElem(new Point3D(10.0, 0.0, 0.0), 1);
        power.setElem(2);
    }

    public void declareProperties() {
        super.declareProperties();
        power.setElem(coefficients.getData1d().size());
        getDeclaredDataStructure().put(("coefficients/points de contrôle"), coefficients);
        getDeclaredDataStructure().put(("power/puissance du polynone"), power);
    }

    @Override
    public Point3D calculerPoint3D(double t) {
        Point3D sum = Point3D.O0;
        for (int i = 0; i < coefficients.getData1d().size(); i++) {
            sum = sum.plus(coefficients.getElem(i).mult(Math.pow(t, i)));
        }
        return sum;
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        Point3D sum = Point3D.O0;
        for (int i = 0; i < coefficients.getData1d().size() - 1; i++) {
            sum = sum.plus(coefficients.getElem(i).mult(Math.pow(t, i - 1) * i));
        }
        return sum;
    }

    public StructureMatrix<Point3D> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(StructureMatrix<Point3D> coefficients) {
        this.coefficients = coefficients;
    }

    public Integer getPower() {
        return power.getElem();
    }

    public void setPower(Integer pow) {
        power.setElem(pow);
    }
}
