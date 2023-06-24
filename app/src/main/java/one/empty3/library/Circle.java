/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
    private boolean isCalculerRepere1 = false;
    private Point3D center;

    public Circle() {
        axis.setElem(new Axe());
        radius.setElem(10.0);
        declareProperties();
    }

    public Circle(Axe axis, double radius) {
        this.axis.setElem(axis);
        this.radius.setElem(radius);

    }


    public Circle(Point3D center, Point3D vAxis, double radius) {
        this(new Axe(center.plus(vAxis.norme1()), center.moins(vAxis.norme1())),
                radius);
    }
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

            Point3D mult = axis.getElem().getVector().norme1()
                    .prodVect(axis.getElem().getVector().norme1()
                            .prodVect(pRef).norme1());
            double d = mult.prodScalaire(pRef);
            setVectY(axis.getElem().getVector().norme1());
            setVectZ(mult.norme1());
            setVectX(getVectY().prodVect(getVectZ()));
            success = (getVectX().norme() > 0.8)
                    && (getVectY().norme() > 0.8)
                    && (getVectZ().norme() > 0.8)
                    && (getVectX().prodVect(getVectY()).norme() > 0.8)
                    && (getVectY().prodVect(getVectZ()).norme() > 0.8)
                    && (getVectZ().prodVect(getVectX()).norme() > 0.8);
            if (success)
                break;
            i++;

        }
        if (!success) {
            isCalculerRepere1 = false;
            //throw new NullPointerException("Cannot compute axis");
        }
        isCalculerRepere1 = true;
    }

    public boolean isCalculerRepere1() {
        return isCalculerRepere1;
    }

    public void setCalculerRepere1(boolean calculerRepere1) {
        isCalculerRepere1 = calculerRepere1;
    }

    @Override
    public Point3D calculerPoint3D(double t) {
        if (!isCalculerRepere1())
            calculerRepere1();
        return getCenter().plus(
                (
                        getVectX().mult(
                                        Math.cos(2.0 * Math.PI * t))
                                .plus(
                                        getVectY().mult(
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
