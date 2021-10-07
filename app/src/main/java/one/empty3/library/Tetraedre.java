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


import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Tetraedre extends Representable implements TRIGenerable {

    private String id;
    private Point3D[] points;
    private TRIObject obj;
    private Color color;

    public Tetraedre(Point3D[] points) {
        super();
        this.points = points;
        obj = new TRIObject();
    }

    /*__
     * @param ps
     * @param c
     */
    public Tetraedre(Point3D[] ps, Color c) {
        super();
        this.points = ps;
        this.color = c;
        obj = new TRIObject();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public TRIObject generate() {
        int i, j, k;
        obj = new TRIObject();
        i = 0;
        j = 1;
        k = 2;
        obj.add(new TRI(points[i], points[j], points[k], color));
        i = 0;
        j = 1;
        k = 3;
        obj.add(new TRI(points[i], points[j], points[k], color));
        i = 0;
        j = 2;
        k = 3;
        obj.add(new TRI(points[i], points[j], points[k], color));
        i = 1;
        j = 2;
        k = 3;
        obj.add(new TRI(points[i], points[j], points[k], color));
        return obj;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TRIObject getObj() {
        return obj;
    }

    public void setObj(TRIObject obj) {
        this.obj = obj;
    }

    public Point3D[] getPoints() {
        return points;
    }

    public void setPoints(Point3D[] points) {
        this.points = points;
    }

    public String toString() {
        return "tetraedre(\n\n" + points[0] + " " + points[1] + " " + points[2] + " " + points[3]
                + "\n\n)\n";
    }

}
