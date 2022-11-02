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

package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricCurve;

/*__*
 *
 * TODO Check all the constructors.
 *
 */


public class Circle extends ParametricCurve {
    protected StructureMatrix<Axe> axis = new StructureMatrix<>(0, Axe.class);
    //public Point3D center;
    protected StructureMatrix<Double> radius = new StructureMatrix<>(0, Double.class);
    protected Point3D vectX;
    protected Point3D vectY;
    protected Point3D vectZ;
    private boolean isCalculerRepere1 = false;
    private Point3D center;

    public Circle() {
        axis.setElem(new Axe());
        radius.setElem(10.0);
    }

    public Circle(Axe axis, double radius) {
        this.axis.setElem(axis);
        this.radius.setElem(radius);

    }

    /*
        public Circle(Point3D center, Point3D vAxis, double radius) {

            this.vAxis = vAxis.norme1();
            this.axis = new Axe(
                    center.plus(vAxis),
                    center.moins(vAxis)
            );
            this.radius = radius;
            calculerRepere2();
        }
    */
/*
    private void calculerRepere2() {

    }

    public Circle(Point3D center, Point3D[] vAxis, double radius) {

        this.vAxis = vAxis[2];
        vectY = vAxis[2];
        vectX = vAxis[0];
        vectZ = vAxis[1];
        this.radius = radius;
        calculerRepere3();
    }

    private void calculerRepere3() {

    }
*/
    public void calculerRepere1() {
        boolean success = false;
        int i = 0;
        while (!success && i < 3) {
            Point3D pRef = new Point3D(i == 0 ? 1d : 0d, i == 1 ? 1d : 0d, i == 2 ? 1d : 0d);

            Point3D mult = axis.getElem().getVector().norme1().prodVect(axis.getElem().getVector().norme1().prodVect(pRef).norme1());
            double d = mult.prodScalaire(pRef);
            vectY = axis.getElem().getVector().norme1();
            vectZ = mult.norme1();
            vectX = vectY.prodVect(vectZ);
            if (mult.norme() > 0.8 || d > 0.8) {
                success = true;
                break;
            }
            i++;
        }
        if (!success) {
            isCalculerRepere1 = false;
            throw new NullPointerException("Cannot compute axis");
        }
        isCalculerRepere1 = true;
    }

    public boolean isCalculerRepere1() {
        return isCalculerRepere1;
    }

    @Override
    public Point3D calculerPoint3D(double t) {
        if (!isCalculerRepere1())
            calculerRepere1();
        return getCenter().plus(
                (
                        vectX.mult(
                                        Math.cos(2.0 * Math.PI * t))
                                .plus(
                                        vectY.mult(
                                                Math.sin(2.0 * Math.PI * t)))
                )
                        .mult(radius.getElem())
        );
    }

    public StructureMatrix<Axe> getAxis() {
        return axis;
    }

    public void setAxis(StructureMatrix<Axe> axis) {
        this.axis = axis;
    }

    public Point3D getCenter() {
        return axis.getElem().getCenter();
    }

    public Double getRadius() {
        return radius.getElem();
    }

    public void setRadius(Double radius) {
        this.radius.setElem(radius);
    }

    public Point3D getVectX() {
        return vectX;
    }

    public void setVectX(Point3D vectX) {
        this.vectX = vectX;
    }

    public Point3D getVectY() {
        return vectY;
    }

    public void setVectY(Point3D vectY) {
        this.vectY = vectY;
    }

    public Point3D getVectZ() {
        return vectZ;
    }

    public void setVectZ(Point3D vectZ) {
        this.vectZ = vectZ;
    }

    public Point3D getvAxis() {
        return axis.getElem().getVector();
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("axis/axe du cercle (perpendiculaire au plan)", axis);
        getDeclaredDataStructure().put("radius/rayon", radius);
    }

    @Override
    public String toString() {
        return "circle (\n" + axis.toString() + "\n";
    }

    public void setCenter(Point3D center) {
        this.center = center;
    }
}
