/*
 * This file is part of Plants-Growth-2
 *     Plants-Growth-2 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Plants-Growth-2 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Plants-Growth-2.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Plants-Growth-2
 *     Plants-Growth-2 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Plants-Growth-2 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Plants-Growth-2.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Plants-Growth-2
 *     Foobar is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Foobar is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Plants-Growth-2
 *     Foobar is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Foobar is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Plants-Growth-2
 *     Foobar is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Foobar is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package one.empty3.growth.graphics;

import one.empty3.library.*;

import java.awt.*;

public class Turtle3D_2 {

    private Matrix33 repereTurtle;
    private ZBuffer zBuffer;
    private Point3D positionTurtle = Point3D.O0;

    private double diametre;
    private Color color = Color.BLACK;

    private Point3D matriceRepereULH(Point3D p3dUnite) {
        return /* RZ */new Matrix33(new double[]{
                Math.cos(p3dUnite.getX()), -Math.sin(p3dUnite.getX()), 0,
                Math.sin(p3dUnite.getX()), Math.cos(p3dUnite.getX()), 0,
                0, 0, 1
        }).mult( /* RY */new Matrix33(new double[]{
                Math.cos(p3dUnite.getX()), 0, Math.sin(p3dUnite.getX()),
                0, 1, 0,
                -Math.sin(p3dUnite.getX()), 0, Math.cos(p3dUnite.getX())
        })).mult( /* RX */new Matrix33(new double[]{
                1, 0, 0,
                0, Math.cos(p3dUnite.getX()), -Math.sin(p3dUnite.getX()),
                0, Math.sin(p3dUnite.getX()), Math.cos(p3dUnite.getX())
        })).mult(p3dUnite);
    }

    private Point3D transformForRotation(double angleU, double angleL, double angleH, Point3D p3d) {

        Matrix33[] ulhMatrices =
                new Matrix33[]{
                        new Matrix33(new double[]{
                                Math.cos(angleU), Math.sin(angleU), 0,
                                -Math.sin(angleU), Math.cos(angleU), 0,
                                0, 0, 1

                        }),
                        new Matrix33(new double[]{
                                Math.cos(angleL), 0, -Math.sin(angleL),
                                0, 1, 0,
                                Math.sin(angleL), 0, Math.cos(angleL)
                        }),
                        new Matrix33(new double[]{
                                1, 0, 0,
                                0, Math.cos(angleH), -Math.sin(angleH),
                                0, Math.sin(angleH), Math.cos(angleH)
                        })};


        if (p3d == null) {
            Matrix33 newMatriceRepereTurtle = ulhMatrices[2].mult(ulhMatrices[1]).mult(ulhMatrices[0]);
            this.repereTurtle = newMatriceRepereTurtle.mult(repereTurtle);
            return null;
        } else {
            Point3D p3dTrans = ulhMatrices[2].mult(ulhMatrices[1]).mult(ulhMatrices[0]).mult(repereTurtle).mult(p3d);
            return p3dTrans;
        }
        // TODO Check order
    }

    public Turtle3D_2() {
        this.positionTurtle = Point3D.O0;
        this.repereTurtle = Matrix33.I;
        this.diametre = 1.0;
    }

    public Turtle3D_2(ZBuffer zBuffer) {
        this();
        this.zBuffer = zBuffer;
        if (zBuffer.scene() == null)
            zBuffer.scene(new Scene());
    }

    public Point3D getPosition() {
        return positionTurtle;
    }

    public void setPosition(Point3D position) {
        this.positionTurtle = position;
    }

    public double getDiametre() {
        return diametre;
    }

    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }

    public void line(double distance) {
        Point3D newPosition = positionTurtle.plus(matriceRepereULH(Point3D.X.mult(distance)));
        LineSegment droite = new LineSegment (positionTurtle, newPosition);
        droite.texture(new ColorTexture(color));
        zBuffer.scene().add(droite);
        positionTurtle = newPosition;
        System.out.println(positionTurtle);
    }

    public void move(double distance) {
        positionTurtle = positionTurtle.plus(repereTurtle.mult(Point3D.X.mult(distance)));

    }

    public void rotateU(double rads) {
        transformForRotation(rads, 0, 0, Point3D.X);

    }

    public void rotateL(double rads) {
        transformForRotation(0, rads, 0, Point3D.X);

    }

    public void rotateH(double rads) {
        transformForRotation(0, 0, rads, Point3D.X);

    }

    public Image returnImage() {
        zBuffer.draw();
        ECBufferedImage image = zBuffer.image();
        return image;

    }

    public void setColor(Color color) {
        this.color = color;
    }
}
