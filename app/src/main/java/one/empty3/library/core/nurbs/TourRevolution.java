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

package one.empty3.library.core.nurbs;

import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

/*__
 * Created by manue on 29-12-19.
 */
public class TourRevolution extends ParametricSurface {
    private StructureMatrix<CourbeParametriquePolynomialeBezier> baseCurveXy = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);
    private StructureMatrix<Matrix33> rotationBase = new StructureMatrix(0, Matrix33.class);
    private StructureMatrix<Point3D> positionBase = new StructureMatrix(0, Point3D.class);
    private StructureMatrix<Double> scaleYheight = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> scaleXradius = new StructureMatrix<>(0, Double.class);

    public Point3D calculerPoint3D(double u, double v) {
        return rotationBase.getElem().mult(positionBase.getElem().plus(baseCurveXy.getElem().calculerPoint3D(u).mult(Matrix33.rot(u, v).mult(
                Point3D.X
        ).mult(Point3D.n(1, 1, 1)))));
    }


    public TourRevolution() {
        baseCurveXy.add(0, new CourbeParametriquePolynomialeBezier());
        baseCurveXy.getElem().getCoefficients().setElem(baseCurveXy.getElem().getCoefficients().getElem(0), 0);
        baseCurveXy.getElem().getCoefficients().setElem(new Point3D(10., 0., 1.), 0);
        baseCurveXy.getElem().getCoefficients().setElem(new Point3D(10., 10., 1.), 1);
        rotationBase.add(0, new Matrix33());
        positionBase.add(0, new Point3D());
        scaleXradius.add(0, new Double(1.0));
        scaleYheight.add(0, new Double(1.0));
    }

    @Override
    public void declareProperties() {
        getDeclaredDataStructure().put("baseCurveXy/Curve Base XY. X=(radius) Y=(cordoninate)", baseCurveXy);
        getDeclaredDataStructure().put("rotationBase/rotationCuve.position of S=(0, 0)", rotationBase);
        getDeclaredDataStructure().put("positionBase/positionBase.rotation of S=(0, 0)", positionBase);
        getDeclaredDataStructure().put("scaleXradius/Factor for X=(radius)", scaleXradius);
        getDeclaredDataStructure().put("scaleYheight/Factor for Y=(cordinate)", scaleYheight);
    }


}
