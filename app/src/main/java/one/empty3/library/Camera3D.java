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

package one.empty3.library;

/*__
 * @author Atelier
 */
public class Camera3D extends Camera {

    /*__
     *
     */
    private static final long serialVersionUID = 1907724549670145492L;
    protected Camera cGauche;
    protected Camera cDroite;
    double d = 1;
    private double angle3D = Math.PI / 360 * 20;
    private boolean enable = true;
    private Point3D ccg;
    private Point3D ccd;
    private double dist3D;

    public Camera3D(Point3D camera, Point3D lookAt, double dist3D) {
        enable3D();
        d = camera.moins(lookAt).norme();

        configure(dist3D);

        cGauche = new Camera(camera, lookAt);
        cDroite = new Camera(camera, lookAt);

    }

    public double angle3D() {
        return angle3D;
    }

    public void angle3D(double angle3D) {
        this.angle3D = angle3D;
    }
    public void calculerMatrice() {
        /*
         Point3D offsetGauche = cGauche.camera.prodVect(Point3D.Y).norme1()
         .mult(d * Math.atan(angle3D));
         Point3D offsetDroite = cDroite.camera.prodVect(Point3D.Y).norme1()
         .mult(-d * Math.atan(angle3D));
         */
        cGauche.eye.setElem(cGauche.eye.getElem()
                .plus(Point3D.X.mult(-dist3D / 2)));
        cDroite.eye.setElem(cDroite.eye.getElem()
                .plus(Point3D.X.mult(dist3D / 2)));

        calculerNouveauPoint();

        cGauche.calculerMatrice(null);
        cDroite.calculerMatrice(null);

    }

    protected void calculerNouveauPoint() {
    }

    public Point3D calculerPointDansRepereDROIT(Point3D p) {
        Point3D p2 = cDroite.calculerPointDansRepere(p);
        p2.texture(p.texture());
        return p2;
    }

    public Point3D calculerPointDansRepereGAUCHE(Point3D p) {
        Point3D p2 = cGauche.calculerPointDansRepere(p);
        p2.texture(p.texture());
        return p2;
    }

    public void configure(double dist3D) {
        this.dist3D = dist3D;
    }

    public boolean enable3D() {
        return enable;
    }

    public void enable3D(boolean d3) {
        this.enable = d3;
    }

    public Matrix33 oeilDroite() {
        return null;
    }

    public Matrix33 oeilGauche() {
        return null;
    }


}
