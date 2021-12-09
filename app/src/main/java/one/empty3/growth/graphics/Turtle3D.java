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

package one.empty3.growth.graphics;

import one.empty3.library.*;

import java.awt.*;

public class Turtle3D {
    private Rotation2 rotation;
    private Point3D positionTete;
    private Point3D vecteurDirecteur;
    private Point3D vecteurHauteur;
    private Point3D vecteurLargeur;
    private ZBuffer zBuffer;
    private Color color;
    private Matrix33 rot;

    public Turtle3D() {
        init();
    }

    public Turtle3D(ZBuffer zBuffer) {
        init();
        this.zBuffer = zBuffer;
        if (zBuffer.scene() == null)
            zBuffer.scene(new Scene());
    }

    protected void init() {
        rotation = new Rotation2();
        positionTete = Point3D.O0;
        vecteurDirecteur = Point3D.Z;
        vecteurHauteur = Point3D.Y;
        vecteurLargeur = Point3D.X;
    }

    private void updateRot() {
        rot = new Matrix33(new Point3D[]{vecteurDirecteur, vecteurHauteur, vecteurLargeur});

    }

    public void rotationTete(double angle) {
        vecteurHauteur = rotation.rotation(vecteurHauteur.plus(positionTete), positionTete,
                positionTete.plus(vecteurDirecteur), angle);
        vecteurLargeur = rotation.rotation(vecteurLargeur.plus(positionTete), positionTete,
                positionTete.plus(vecteurLargeur), angle);
    }

    public void rotationHauteur(double angle) {
        vecteurDirecteur = rotation.rotation(vecteurDirecteur.plus(positionTete), positionTete,
                positionTete.plus(vecteurHauteur), angle);
        vecteurLargeur = rotation.rotation(vecteurLargeur.plus(positionTete), positionTete,
                positionTete.plus(vecteurHauteur), angle);

    }

    public void rotationLargeur(double angle) {
        vecteurHauteur = rotation.rotation(vecteurHauteur.plus(positionTete), positionTete,
                positionTete.plus(vecteurLargeur), angle);
        vecteurDirecteur = rotation.rotation(vecteurDirecteur.plus(positionTete), positionTete,
                positionTete.plus(vecteurLargeur), angle);

    }

    public void line(double to) {
        Point3D extremite = positionTete.plus(vecteurDirecteur.mult(to));
        positionTete = extremite;
    }

    public void move(double to) {
        Point3D extremite = positionTete.plus(vecteurDirecteur.mult(to));
        zBuffer.scene().add(new LineSegment(positionTete, extremite));
        positionTete = extremite;
    }

    public void placeObjectHere(Representable representable) {

    }

    public Image returnImage() {
        zBuffer.draw();
        ECBufferedImage image = zBuffer.image();
        return image;

    }

    public Rotation2 getRotation() {
        return rotation;
    }

    public void setRotation(Rotation2 rotation) {
        this.rotation = rotation;
    }

    public Point3D getPositionTete() {
        return positionTete;
    }

    public void setPositionTete(Point3D positionTete) {
        this.positionTete = positionTete;
    }

    public Point3D getVecteurDirecteur() {
        return vecteurDirecteur;
    }

    public void setVecteurDirecteur(Point3D vecteurDirecteur) {
        this.vecteurDirecteur = vecteurDirecteur;
    }

    public Point3D getVecteurHauteur() {
        return vecteurHauteur;
    }

    public void setVecteurHauteur(Point3D vecteurHauteur) {
        this.vecteurHauteur = vecteurHauteur;
    }

    public Point3D getVecteurLargeur() {
        return vecteurLargeur;
    }

    public void setVecteurLargeur(Point3D vecteurLargeur) {
        this.vecteurLargeur = vecteurLargeur;
    }

    public ZBuffer getzBuffer() {
        return zBuffer;
    }

    public void setzBuffer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void angle(double angleU, double angleL, double angleH) {

        Matrix33 ulhMatrices =

                new Matrix33(new double[]{
                        Math.cos(angleU), Math.sin(angleU), 0,
                        -Math.sin(angleU), Math.cos(angleU), 0,
                        0, 0, 1

                }).mult(
                        new Matrix33(new double[]{
                                Math.cos(angleL), 0, -Math.sin(angleL),
                                0, 1, 0,
                                Math.sin(angleL), 0, Math.cos(angleL)
                        })).mult(
                        new Matrix33(new double[]{
                                1, 0, 0,
                                0, Math.cos(angleH), -Math.sin(angleH),
                                0, Math.sin(angleH), Math.cos(angleH)
                        }));

        updateRot();
        rot = ulhMatrices.mult(rot);
    }
}
