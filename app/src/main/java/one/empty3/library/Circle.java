/*
 * Copyright (c) 2024. Manuel Daniel Dahmen
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

    @Override
    public StructureMatrix<Point3D> getBoundRect2d() {
        StructureMatrix<Point3D> boundRect2d1 = super.getBoundRect2d();
//FLAGIT        RectF boundRect2d = new RectF();
        center = axis.getElem().getCenter();
        boundRect2d1.setElem(new Point3D((double) (float) (center.get(0) - radius.getElem()),
                (double) (float) (center.get(1)-radius.getElem()), 0.));
        boundRect2d1.setElem(new Point3D((double) (float) (center.get(0)+radius.getElem()),
                (double) (float) (center.get(1)+radius.getElem()), 0.));

        return boundRect2d1;
    }

    // Function to find the circle on
// which the given three points lie
    public Circle(int x1, int y1,
                  int x2, int y2,
                  int x3, int y3)
    {
        this();

        int x12 = x1 - x2;
        int x13 = x1 - x3;

        int y12 = y1 - y2;
        int y13 = y1 - y3;

        int y31 = y3 - y1;
        int y21 = y2 - y1;

        int x31 = x3 - x1;
        int x21 = x2 - x1;

        // x1^2 - x3^2
        int sx13 = (int)(Math.pow(x1, 2) -
                Math.pow(x3, 2));

        // y1^2 - y3^2
        int sy13 = (int)(Math.pow(y1, 2) -
                Math.pow(y3, 2));

        int sx21 = (int)(Math.pow(x2, 2) -
                Math.pow(x1, 2));

        int sy21 = (int)(Math.pow(y2, 2) -
                Math.pow(y1, 2));

        int f = ((sx13) * (x12)
                + (sy13) * (x12)
                + (sx21) * (x13)
                + (sy21) * (x13))
                / (2 * ((y31) * (x12) - (y21) * (x13)));
        int g = ((sx13) * (y12)
                + (sy13) * (y12)
                + (sx21) * (y13)
                + (sy21) * (y13))
                / (2 * ((x31) * (y12) - (x21) * (y13)));

        int c = -(int)Math.pow(x1, 2) - (int)Math.pow(y1, 2) -
                2 * g * x1 - 2 * f * y1;

        // eqn of circle be x^2 + y^2 + 2*g*x + 2*f*y + c = 0
        // where centre is (h = -g, k = -f) and radius r
        // as r^2 = h^2 + k^2 - c
        int h = -g;
        int k = -f;
        int sqr_of_r = h * h + k * k - c;

        // r is the radius
        double r = Math.sqrt(sqr_of_r);

        Point3D cPoint3D = new Point3D((double) h, (double) k, 0.0);
        StructureMatrix<Axe> axeSm = new StructureMatrix<>(1, Point3D.class);
        Axe axe = new Axe(cPoint3D.moins(Point3D.Z.mult(-r)), cPoint3D.moins(Point3D.Z.mult(r)));
        axeSm.setElem(axe);
        setAxis(axeSm);
        setRadius(r);

    }

}
