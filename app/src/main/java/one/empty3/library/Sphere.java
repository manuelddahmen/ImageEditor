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

import android.graphics.Rect;

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.Point2Point;


public class Sphere extends ParametricSurface {
    protected StructureMatrix<Circle> circle = new StructureMatrix<>(0, Circle.class);

    public Sphere()
    {
        super();
        circle.setElem(new Circle());


    }
    public Sphere(Axe axis, double radius) {
        this();
        circle.setElem(new Circle(axis, radius));
    }

    public Sphere(Point3D center, double radius) {
        this();
        getCircle().getAxis().setElem(new Axe(center.plus(Point3D.Y.mult(radius)), center.plus(Point3D.Y.mult(-radius))));
        getCircle().setRadius(radius);
        terminalU.setElem(new Point2Point() {
            @Override
            public Point3D result(Point3D p) {
                return new Point3D(0.0, p.get(1), p.get(2));
            }
        });
        terminalV.setElem(new Point2Point() {
            @Override
            public Point3D result(Point3D p) {
                return calculerPoint3D(p.get(0), Math.PI/2);
            }
        });

    }

    public Point3D calculerPoint3D(double u, double v) {
        Circle c = circle.getData0d();
        if (!c.isCalculerRepere1())
        {
            c.calculerRepere1();
        }
        double cos = Math.cos(-Math.PI / 2 + Math.PI * v);
        return c.getCenter().plus(
                c.getVectX().mult(
                                Math.cos(2.0 * Math.PI * u) * cos).plus(
                                c.getVectY().mult(
                                        Math.sin(2.0 * Math.PI * u) * cos))
                        .plus(c.getVectZ().mult(Math.sin(-Math.PI / 2 + Math.PI * v))
                        ).norme1().mult(c.radius.getElem()));
    }

    public Circle getCircle() {
        return circle.getData0d();
    }

    public void setCircle(Circle circle) {
        this.circle.setElem(circle);
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("circle/circle", circle);

    }

    @Override
    public String toString() {
        return "sphere (\n\t"+circle.toString()+"\n\t"+texture.toString()+"\n)\t";
    }

    @Override
    protected Rect getBoundRect2d() {
        return circle.getElem().axis.data0d.getBoundRect2d();
    }
}
