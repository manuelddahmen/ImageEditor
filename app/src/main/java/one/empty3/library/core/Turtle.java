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

package one.empty3.library.core;

import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricCurve;

/*__
 * Created by manue on 07-09-19.
 */
public class Turtle extends ParametricCurve {
    private StructureMatrix<Representable> matrixAndPoint = new StructureMatrix<>(1, Representable.class);
    private StructureMatrix<Matrix33> actualOrientation = new StructureMatrix<>(0, Matrix33.class);
    private StructureMatrix<Point3D> actualPosition = new StructureMatrix<>(0, Point3D.class);

    private Matrix33 rX(double angle) {
        return new Matrix33(
                new Double[]
                        {
                                1d, 0d, 0d,
                                0d, Math.cos(angle), Math.sin(angle),
                                0d, -Math.sin(angle), Math.cos(angle),
                        }
        );
    }

    private Matrix33 rY(double angle) {
        return new Matrix33(
                new Double[]
                        {
                                Math.cos(angle), 0d, Math.sin(angle),
                                0d, 1d, 0d,
                                -Math.sin(angle), 0d, Math.cos(angle),
                        }
        );
    }

    private Matrix33 rZ(double angle) {
        return new Matrix33(
                new Double[]
                        {
                                Math.cos(angle), Math.sin(angle), 0d,
                                -Math.sin(angle), Math.cos(angle), 0d,
                                0d, 0d, 1d,
                        }
        );
    }

    private Matrix33 matriceAngulaire(int axe, double angle) {
        if (axe == 0) {
            return actualOrientation.getElem().mult(rX(angle));
        }
        if (axe == 1) {
            return actualOrientation.getElem().mult(rY(angle));
        }
        if (axe == 2) {
            return actualOrientation.getElem().mult(rZ(angle));
        }
        return null;
    }


    public Turtle() {
        actualOrientation.setElem(new Matrix33());
        actualPosition.setElem(new Point3D(Point3D.O0));

        matrixAndPoint.add(1, actualPosition.getElem());
        matrixAndPoint.add(1, actualOrientation.getElem());
    }

    public void left(double angle, double distance) {

        Matrix33 matrix33 = matriceAngulaire(2, angle);
        matrixAndPoint.add(1, matrix33);
    }

    public void right(double angle, double distance) {
        Matrix33 matrix33 = matriceAngulaire(2, angle);
        matrixAndPoint.add(1, matrix33);
    }

    public void up(double angle, double distance) {
        Matrix33 matrix33 = matriceAngulaire(0, angle);
        matrixAndPoint.add(1, matrix33);
    }

    public void down(double angle, double distance) {
        Matrix33 matrix33 = matriceAngulaire(0, angle);
        matrixAndPoint.add(1, matrix33);
    }

    public void rear(double angle, double distance) {
        Point3D plus = actualOrientation.getElem().mult(Point3D.Z.mult(distance));
        matrixAndPoint.add(1, plus);
        actualPosition.setElem(actualPosition.getElem().plus(plus));

    }

    public void forwards(double angle, double distance) {
        Point3D plus = actualOrientation.getElem().mult(Point3D.Z.mult(distance));
        matrixAndPoint.add(1, plus);
        actualPosition.setElem(actualPosition.getElem().plus(plus));

    }

    public void rotate(double angleHorizLR, double angleVertDU, double angleForwardsSquiv) {

    }

    public Point3D getPosition() {
        return actualPosition.getElem();
    }

    public Point3D getDirection() {
        return actualOrientation.getElem().mult(Point3D.Z).norme1();
    }

    public void clearPointsBefore() {

    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("actualPosition", actualPosition);
        getDeclaredDataStructure().put("actualOrientation", actualOrientation);
        getDeclaredDataStructure().put("matrixAndPoint", matrixAndPoint);
    }

    public void computeAll() {
        Point3D pos = Point3D.O0;
        Matrix33 orient = Matrix33.I;
        for (int i = 0; i < matrixAndPoint.getData1d().size(); i++) {
            Object mp = matrixAndPoint.getData1d().get(i);
            if (mp instanceof Double) {
                pos = pos.plus(orient.mult((Point3D.Z.mult((Double) mp))));
            } else if (mp instanceof Matrix33) {
                orient = orient.mult((Matrix33) mp);
            } else if (mp instanceof Point3D) {
                pos = pos.plus(orient.mult((Point3D) mp));
            }
        }
    }
}
