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

package one.empty3.growth.graphics;

import one.empty3.library.*;

import java.awt.*;

public class Turtle3D_3 {
    private Point3D position;
    private Point3D vU;
    private Point3D vH;
    private Point3D vL;
    private Scene scene;
    private Color color;
    private ZBuffer zBuffer;

    public Turtle3D_3() {
        setPosition(Point3D.O0);
        vU = Point3D.X;
        vH = Point3D.Y;
        vL = Point3D.Z;
    }

    public void rotU(double a) {
        vH = new Rotation(vU, getPosition(), a).rotate(vH);
        vL = new Rotation(vU, getPosition(), a).rotate(vL);
    }

    public void rotH(double a) {
        vU = new Rotation(vH, getPosition(), a).rotate(vU);
        vL = new Rotation(vH, getPosition(), a).rotate(vL);

    }

    public void rotL(double a) {
        vU = new Rotation(vL, getPosition(), a).rotate(vU);
        vH = new Rotation(vL, getPosition(), a).rotate(vH);
    }

    public void line(double dist) {
        Point3D newPosition = getPosition().plus(vU.mult(dist));
        LineSegment seg = new LineSegment(getPosition(), newPosition);
        seg.texture(new ColorTexture(this.color));
        scene().add(seg);
        setPosition(newPosition);
    }

    private Scene scene() {
        return scene;
    }

    public void move(double dist) {
        position = position.plus(vU.mult(dist));

    }


    public Point3D getPosition() {
        System.out.println("return position" + position);
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
        System.out.println("this.position=" + position);
    }

    public Point3D getvU() {
        return vU;
    }

    public void setvU(Point3D vU) {
        this.vU = vU;
    }

    public Point3D getvH() {
        return vH;
    }

    public void setvH(Point3D vH) {
        this.vH = vH;
    }

    public Point3D getvL() {
        return vL;
    }

    public void setvL(Point3D vL) {
        this.vL = vL;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ZBuffer getzBuffer() {
        return zBuffer;
    }

    public void setzBuffer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
        zBuffer.scene(this.scene = new Scene());
    }
}
