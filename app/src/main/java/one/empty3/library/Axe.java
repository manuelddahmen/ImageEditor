/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

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

/*

 Vous êtes libre de :

 */
package one.empty3.library;

import android.graphics.Rect;

public class Axe extends Representable {

    private StructureMatrix<Point3D> p1 = new StructureMatrix<>(0, Point3D.class);
    private StructureMatrix<Point3D> p2 = new StructureMatrix<>(0, Point3D.class);
    private Point3D center;


    public Axe() {
        super();
        p1.setElem(Point3D.Y);
        p2.setElem(Point3D.Y.mult(-1d));
    }

    public Axe(Point3D p1, Point3D p2) {
        this.p1.setElem(p1);
        this.p2.setElem(p2);
    }

    public StructureMatrix<Point3D> getP1() {
        return p1;
    }

    public void setP1(StructureMatrix<Point3D> p1) {
        this.p1 = p1;
    }

    public StructureMatrix<Point3D> getP2() {
        return p2;
    }

    public void setP2(StructureMatrix<Point3D> p2) {
        this.p2 = p2;
    }

    public Point3D getVectAxe() {
        return p2.getElem().plus(p1.getElem().mult(-1d));
    }

    public Point3D rotation(double angle, Point3D point) {
        return point;
    }

    public Point3D getCenter() {
        return p1.getElem().plus(p2.getElem()).mult(0.5);
    }

    public Point3D getVector() {
        return p2.getElem().moins(p1.getElem());
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("p1/p1", p1);
        getDeclaredDataStructure().put("p2/p2", p2);

    }

    @Override
    public String toString() {
        return "axis (\np1" + getP1() + "\np2" + getP2() + "\n)\n";
    }

    public Axe setCenter(Point3D center) {
        Point3D currentCenter = p2.getElem().moins(p1.getElem()).mult(0.5);
        Point3D p12 = center.plus(p1.getElem());
        Point3D p22 = center.plus(p2.getElem());

        getP1().setElem(p12);
        getP2().setElem(p22);

        return this;
    }

    public static Axe defaut() {
        return new Axe(Point3D.Y, Point3D.Y.mult(-1.));
    }

    @Override
    protected Rect getBoundRect2d() {
        Point3D center1 = getCenter();
        double length = p1.getElem().moins(p2.getElem()).getLength()*2;
        return new Rect((int) (center1.get(0)-length), (int) (center1.get(1)-length),
                (int) (center1.get(0)+length), (int) (center1.get(1)-length));
    }
}
