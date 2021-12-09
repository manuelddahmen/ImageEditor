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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

/*__
 * @author Atelier
 */
public class POINT3D_RIO extends Point3D {

    public static final Point3D X = new Point3D(1d, 0d, 0d);
    public static final Point3D Y = new Point3D(0d, 1d, 0d);
    public static final Point3D Z = new Point3D(0d, 0d, 1d);
    public static final Point3D O0 = new Point3D(0d, 0d, 0d);
    /*__
     *
     */
    private static final long serialVersionUID = -5729435529487300122L;
    private double[] x;

    public POINT3D_RIO(double x0, double y0, double z0) {
        super(x0, y0, z0);
    }

    public POINT3D_RIO(Point3D p) {
        super();
        x[0] = p.get(0);
        x[1] = p.get(1);
        x[2] = p.get(2);
    }

    public POINT3D_RIO(POINT3D_RIO p0) {
        x = new double[3];
        x[0] = p0.getX();
        x[1] = p0.getY();
        x[2] = p0.getZ();
    }

    @Override
    public Object clone() {
        return new POINT3D_RIO(x[0], x[1], x[2]);
    }

    @Override
    public Point3D moins(Point3D p) {
        setX(getX() - p.getX());
        setY(getY() - p.getY());
        setZ(getZ() - p.getZ());
        return this;
    }

    public Point3D mult(double xFactor) {
        setX(getX() * xFactor);
        setY(getY() * xFactor);
        setZ(getZ() * xFactor);
        return this;
    }

    public Point3D plus(double i) {
        setX(getX() + i);
        setY(getY() + i);
        setZ(getZ() + i);
        return this;
    }

    @Override
    public Point3D plus(Point3D p) {
        setX(getX() + p.getX());
        setY(getY() + p.getY());
        setZ(getZ() + p.getZ());
        return this;
    }

}
