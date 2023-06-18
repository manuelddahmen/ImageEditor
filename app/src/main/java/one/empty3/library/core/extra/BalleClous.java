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

/*
 * 2013 Manuel Dahmen
 */
package one.empty3.library.core.extra;

import one.empty3.library.HeightMapSurface;
import one.empty3.library.Point2D;
import one.empty3.library.Point3D;
import one.empty3.library.Sphere;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.ArrayList;

/*__
 * @author Se7en
 */
public class BalleClous extends Sphere {

    private ArrayList<Point2D> points = new ArrayList<Point2D>();
    private double d;

    public BalleClous(Point3D c, double r) {
        super(c, r);
    }

    public void addPoint(Point2D p) {
        this.points.add(p);
    }


    public double dmindist(Point2D p0, Point2D p1) {

        double[] x = new double[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};
        double[] y = new double[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
        double min = 100;
        for (int i = 0; i < 9; i++) {
            double cur;
            cur = Point2D.dist(p0, Point2D.plus(p1, new Point2D(x[i], y[i])));
            if (cur < min && cur > 0) {
                min = cur;
            }
        }
        return min;
    }

    public double param() {
        return d;
    }

    public void param(double d) {
        this.d = d;
    }

    public ArrayList<Point2D> points() {
        return points;
    }

    public Point3D calculerPoint3D(double u, double v)
    {
        Point2D p0 = new Point2D(u, v);

        double mult = 1.0;

        for (int i = 0; i < points.size(); i++) {

            mult += 1 / (d + dmindist(p0, points.get(i)));

        }

        return super.calculerPoint3D(u, v).mult(mult / points.size());

    }
}
