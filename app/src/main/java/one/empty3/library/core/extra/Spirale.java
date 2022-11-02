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
package one.empty3.library.core.extra;

import one.empty3.library.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Spirale implements ISpirale {

    private ArrayList<TRI> triangles;
    private Axe axe;
    private double angle;
    private double radius;
    private TRIObject obj;

    public Spirale(Axe axe, double radius) {
        this.axe = axe;
        this.setRadius(radius);

        /*
         * Tour tour = new Tour(axe.getP1(), axe.getP2(), new
         * Tour.IColorFunction() {
         *
         * @Override public Color getColor(double axeCoordinate, double theta) {
         * return Color.valueOf(255, 0, 0); } }, new Tour.IPoint3DFunction() {
         *
         * @Override public double getDiameter(double axeCoordinate, double
         * theta) {
         *
         * return 100; }
         *
         * @Override public int getNbrPoints() { return 20; }
         *
         * @Override public void setNbrPoints() {
         *
         *
         * }
         *
         * @Override public int getNbrRotations() { return 360; }
         *
         * @Override public void setNbrRotation() { } });
         *
         * FObjet o = tour.generate(); this.obj = o;
         */
    }

    @Override
    public void addToScene(Scene sc) {
        Iterator<TRI> it = triangles.iterator();
        TRIObject o = new TRIObject();
        while (it.hasNext()) {
            TRI t = it.next();
            o.add(t);
        }
        sc.add(o);
    }

    public TRIObject getObj() {
        return obj;
    }

    public void setObj(TRIObject obj) {
        this.obj = obj;
    }

    @Override
    public Point3D getObjectDeviation(Point3D position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Point3D getObjectDeviation(Point3D position, Point3D speed,
                                      Point3D rotation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Point3D getObjectRotation(Point3D position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Point3D getObjectRotation(Point3D position, Point3D speed,
                                     Point3D rotation) {
        // TODO Auto-generated method stub
        return null;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void rotate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void rotate(double deg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
