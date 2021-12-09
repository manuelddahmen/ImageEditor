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

package one.empty3.apps.opad;

import one.empty3.library.Camera;
import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;

public class CompletePositionMobile extends SimplePositionMobile {

    public CompletePositionMobile(PositionUpdate positionUpdate) {
        super(positionUpdate);
        positionMobile = new Point3D(0.5, 0.5, 0.007);
        getPositionMobile().setZ(Double.parseDouble(bundle.getString("hauteur")));
        angleVueMobile = Point3D.X;
    }


    //TODO Project les points sur le système d'axes (non orthonormé) à partir des coordonnées 3D sur le Terrain(surface(u,v)+hauteur)
    //TODO Et projection inverse (si possible non)
    public Point3D calcRepere2(Terrain terrain, Point3D positionTerrain) {
        return null;
    }

    public Point3D calcRepere2Inv(Terrain terrain, Point3D positionEspace) {
        return null;
    }

    public Matrix33 calcRepere(Terrain t, Point3D position)

    {
        Point3D[] point3DS = new Point3D[3];
        Point3D O = t.p3(position);
        point3DS[0] = t.p3(position.plus(new Point3D(positionIncrement2, 0.0, 0.0))).moins(O).norme1();
        point3DS[1] = t.p3(position.plus(new Point3D(0.0, positionIncrement2, 0.0))).moins(O).norme1();
        point3DS[2] = t.p3(position.plus(new Point3D(0.0, 0.0, positionIncrement2))).moins(O).norme1();
        return new Matrix33(point3DS);
    }

    public Camera calcCameraMobile() {
        Point3D calcDir = calcDirection(getPositionMobile(), getAngleVueMobile());
        Point3D pointA = terrain.p3(positionMobile);
        Point3D pointB = terrain.p3(positionSol).plus(calcDir);
        Camera camera = new Camera(pointA, pointB);
        return camera;
    }

    public Point3D calcDirectionMobile() {
        Point3D calcDir = calcDirection(getPositionMobile(), getAngleVueMobile());
        Point3D point3D = terrain.p3(positionSol).plus(calcDir);
        return point3D;
    }

    public Point3D getPositionMobile() {
        return positionMobile;
    }

    public Point3D calcDirection(Point3D point, Point3D angles) {
        Point3D point3D = null;
        Matrix33 matrix33 = null;
        try {
            Matrix33 matrix331 = calcRepere(getTerrain(), angles);
            Matrix33 matrix332 = matrix331.tild();

            matrix33 = new Matrix33(new Double[]{
                    Math.cos(angles.getZ() * 2 * Math.PI),
                    -Math.sin(angles.getZ() * 2 * Math.PI),
                    0d,
                    Math.sin(angles.getZ() * 2 * Math.PI),
                    Math.cos(angles.getZ() * 2 * Math.PI),
                    0d,
                    0d,
                    0d,
                    1d
            }).mult(new Matrix33(new Double[]{
                    Math.cos(angles.getY() * 2 * Math.PI),
                    0d,
                    Math.sin(angles.getY() * 2 * Math.PI),
                    0d,
                    1d,
                    0d,
                    -Math.sin(angles.getY() * 2 * Math.PI),
                    0d,
                    Math.cos(angles.getY() * 2 * Math.PI),

            })).mult(new Matrix33(new Double[]{
                    1d,
                    0d,
                    0d,
                    0d,
                    Math.cos(angles.getX() * 2 * Math.PI),
                    -Math.sin(angles.getX() * 2 * Math.PI),
                    0d,
                    Math.sin(angles.getX() * 2 * Math.PI),
                    Math.cos(angles.getX() * 2 * Math.PI),

            }));

            //point3D = matrix332.mult(matrix33).mult(matrix331).mult(point.moins(calcPosition()));
            point3D = matrix33.mult(point.moins(calcPosition())).plus(calcPosition());

        } catch (NullPointerException ex) {
            System.out.println("Null");
        }
        System.out.println("point3D" + point3D);

        return point3D;
    }
}
