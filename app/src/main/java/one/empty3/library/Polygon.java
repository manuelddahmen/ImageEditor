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
 * 2013 Manuel Dahmen
 */
package one.empty3.library;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.core.nurbs.SurfaceElem;


/*__
 * @author Manuel
 */
public class Polygon extends Representable implements SurfaceElem, ClosedCurve {

    /*__
     *
     */
    private StructureMatrix<Point3D> points = new StructureMatrix<>(1, Point3D.class);

    public Polygon() {
        super();
        declareProperties();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Polygon(Color c) {
        this();
        texture(new TextureCol(c));
    }

    public Polygon(ITexture c) {
        this();
        texture(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Polygon(Point3D[] list, Color c) {
        this(list, new TextureCol(c));
    }

    public Polygon(Point3D[] list, ITexture c) {
        this();
        this.texture = c;
        points.setAll(list);
    }

    public void add(Point3D point3D) {
        int newLength;
        if (points == null) {
            points = new StructureMatrix<>(1, Point3D.class);
        } else {
            newLength = points.getData1d().size() + 1;
            java.util.List<Point3D> tmp = points.getData1d();
            points = new StructureMatrix<>(1, Point3D.class);
            for (int i = 0; i < tmp.size(); i++) {
                points.setElem(tmp.get(i), i);
            }
            points.setElem(point3D, newLength - 1);
        }
        declareProperties();
    }


    public StructureMatrix<Point3D> getPoints() {
        return points;
    }

    public void setPoints(Point3D[] points) {
        this.points.setAll(points);
        declareProperties();
    }


    @Override
    public String toString() {
        String t = "poly (\n\t(";
        for (Point3D p : points.getData1d()) {
            t += "\n\t\t" + (p == null ? "null" : p.toString());
        }
        t += "\n\t)\n\t" + (texture == null ? "" : texture.toString()) + "\n)\n\n";
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polygon polygone = (Polygon) o;

        return getPoints() != null ? getPoints().equals(polygone.getPoints()) : polygone.getPoints() == null;

    }

    @Override
    public int hashCode() {
        return getPoints() != null ? getPoints().hashCode() : 0;
    }

    public Point3D getIsocentre() {
        Point3D p = Point3D.O0;

        for (Point3D p0 : points.getData1d()) {
            p = p.plus(p0);
        }
        return p.mult(1. / points.getData1d().size());
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("points/point 0 à N du Polygone", points);

    }

    public Rect getBoundingRect() {
        Rect rect = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Point3D point3D : getPoints().getData1d()) {
            if(point3D.get(0)<=rect.left)
                rect.left = (int) (double) point3D.get(0);
            if(point3D.get(0)>=rect.right)
                rect.right = (int) (double) point3D.get(0);
            if(point3D.get(1)<=rect.top)
                rect.top = (int) (double) point3D.get(1);
            if(point3D.get(1)>=rect.bottom)
                rect.bottom = (int) (double) point3D.get(1);
        }


        return rect;
    }
}
