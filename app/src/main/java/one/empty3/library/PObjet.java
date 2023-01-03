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

import java.util.ArrayList;
import java.util.Iterator;

public class PObjet extends Representable {

    protected ArrayList<Point3D> points;
    private Barycentre position;

    public PObjet() {
        points = new ArrayList<Point3D>();
    }

    public void add(int arg0, Point3D arg1) {
        points.add(arg0, position == null ? arg1 : position.calculer(arg1));
    }

    public Point3D get(int arg0) {
        return points.get(arg0);
    }


    public void add(Point3D arg0) {
        points.add(position == null ? arg0 : position.calculer(arg0));
    }

    public ArrayList<Point3D> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point3D> points) {
        this.points = points;
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public Iterable<Point3D> iterable() {
        return points;
    }

    @Deprecated
    public Iterator<Point3D> iterator() {
        return points.iterator();
    }

    public Point3D remove(int arg0) {
        return points.remove(arg0);
    }

    public boolean remove(Object arg0) {
        return points.remove(arg0);
    }

    public int size() {
        return points.size();
    }


    public String toString() {
        String s = "tri \n(\n\n";
        Iterator<Point3D> tris = iterator();
        while (tris.hasNext()) {
            s += "\n" + tris.next().toString() + "\n";
        }
        return s + "\n)\n";
    }
}
