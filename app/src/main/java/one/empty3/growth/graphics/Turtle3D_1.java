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

package one.empty3.growth.graphics;

import one.empty3.library.*;

import java.awt.*;

public class Turtle3D_1 {

    private Matrix33 repereTurtle;
    private ZBuffer zBuffer;
    private Point3D position = Point3D.O0;
    private Point3D anglesXYZ = Point3D.X;

    private double diametre;
    private Color color = Color.BLACK;

    private Matrix33 matriceRepereULH() {
        return /* RZ */new Matrix33(new double[]{
                Math.cos(anglesXYZ.getX()), -Math.sin(anglesXYZ.getX()), 0,
                Math.sin(anglesXYZ.getX()), Math.cos(anglesXYZ.getX()), 0,
                0, 0, 1
        }).mult( /* RY */new Matrix33(new double[]{
                Math.cos(anglesXYZ.getX()), 0, Math.sin(anglesXYZ.getX()),
                0, 1, 0,
                -Math.sin(anglesXYZ.getX()), 0, Math.cos(anglesXYZ.getX())
        })).mult( /* RX */new Matrix33(new double[]{
                1, 0, 0,
                0, Math.cos(anglesXYZ.getX()), -Math.sin(anglesXYZ.getX()),
                0, Math.sin(anglesXYZ.getX()), Math.cos(anglesXYZ.getX())
        }));
    }

    private void angleRot(double angleU, double angleL, double angleH) {

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
                        })
                };
        Matrix33 matriceRepereTurtle = ulhMatrices[2].mult(ulhMatrices[1]).mult(ulhMatrices[0]);
        Matrix33 baseRepere = matriceRepereULH();
        this.repereTurtle = matriceRepereTurtle.mult(baseRepere); // TODO Check order
    }

    public Turtle3D_1(Point3D position, Point3D angleXYZ, double diametre) {
        this.position = position;
        this.anglesXYZ = angleXYZ;
        this.diametre = diametre;
    }

    public Turtle3D_1(ZBuffer zBuffer) {
        repereTurtle = matriceRepereULH();
        this.zBuffer = zBuffer;
        if (zBuffer.scene() == null)
            zBuffer.scene(new Scene());
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Point3D getAnglesXYZ() {
        return anglesXYZ;
    }

    public void setAnglesXYZ(Point3D anglesXYZ) {
        this.anglesXYZ = anglesXYZ;
    }

    public double getDiametre() {
        return diametre;
    }

    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }

    public void line(double distance) {
        Point3D newPosition = position.plus(matriceRepereULH().mult(Point3D.X.mult(distance)));
        LineSegment droite = new LineSegment(position, newPosition);
        droite.texture(new ColorTexture(color));
        zBuffer.scene().add(droite);
        position = newPosition;
        System.out.println(position);
    }

    public void move(double distance) {
        position = position.plus(repereTurtle.mult(Point3D.X.mult(distance)));

    }

    public void rotateU(double rads) {
        angleRot(rads, 0, 0);

    }

    public void rotateL(double rads) {
        angleRot(0, rads, 0);

    }

    public void rotateH(double rads) {
        angleRot(0, 0, rads);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turtle3D_1)) return false;

        Turtle3D_1 turtle = (Turtle3D_1) o;

        return (getPosition() != null ? getPosition().equals(turtle.getPosition()) : turtle.getPosition() == null) && (getAnglesXYZ() != null ? getAnglesXYZ().equals(turtle.getAnglesXYZ()) : turtle.getAnglesXYZ() == null);
    }

    @Override
    public int hashCode() {
        int result = getPosition() != null ? getPosition().hashCode() : 0;
        result = 31 * result + (getAnglesXYZ() != null ? getAnglesXYZ().hashCode() : 0);
        return result;
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
