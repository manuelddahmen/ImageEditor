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

package one.empty3.pointset;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;

import java.util.ArrayList;
import java.util.Iterator;

/*__
 * Created by manue on 16-07-19.
 */
public class PCont<T extends Point3D> extends Representable {

    protected ArrayList<T> points;

    public PCont() {
        points = new ArrayList<T>();
    }

    public void add(T p) {
        points.add(p);
    }

    public T get(int index) {
        return
                points.get(index);
    }

    public ArrayList<T> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<T> points) {
        this.points = points;
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public Iterator<T> iterator() {
        return points.iterator();
    }

    public Point3D remove(int arg0) {
        return points.remove(arg0);
    }

    public boolean remove(T arg0) {
        return points.remove(arg0);
    }

    public int size() {
        return points.size();
    }


    public String toString() {
        String s = "tri \n(\n\n";
        Iterator<T> tris = iterator();
        while (tris.hasNext()) {
            s += "\n" + tris.next().toString() + "\n";
        }
        return s + "\n)\n";
    }
}



