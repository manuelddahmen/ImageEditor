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

package one.empty3.library.core.nurbs;

import one.empty3.library.*;

/*__
 * @author Manuel Dahmen
 */
public class CameraInPath extends Camera {
    //private double angleA = 0;
    //private double angleB = 0;

    /*__
     * Need to get a position of the camera since it's a Z-axis
     * Rotate, position angles around the camera.
     */
    /*public void rotateSphere(double angleARad, double angleBRad) {
        this.angleA = angleARad;
        this.angleB = angleBRad;
    }*/

    private StructureMatrix<ParametricCurve> courbe = new StructureMatrix<>(0, ParametricCurve.class);
    private final StructureMatrix<Double> t = new StructureMatrix(0, Double.class);

    public CameraInPath() {
        super();
        t.setElem(0.0);
        calculerMatriceT(Point3D.Y);
    }

    public CameraInPath(ParametricCurve maCourbe) {

        t.setElem(0.0);
        this.courbe.setElem(maCourbe);
        calculerMatriceT(Point3D.Y);
    }

    public CameraInPath(ParametricCurve maCourbe, Point3D vectVert) {

        t.setElem(0.0);
        this.courbe.setElem(maCourbe);
        this.getVerticale().setElem(vectVert);
        calculerMatriceT(vectVert);
    }

    public ParametricCurve getCourbe() {
        return courbe.getElem();
    }

    public void setCourbe(ParametricCurve maCourbe) {
        this.courbe.setElem(maCourbe);
    }

    /*
        public void calculerMatriceT(Point3D verticale) {
            double t = this.t.getElem() ;
            Point3D eye = courbe.getElem().calculerPoint3D(t);
            //TODO DEV
            //setLookat(courbe.getElem().calculerPoint3D(t+ t* 1.001));

            Point3D dir = getLookat().moins(eye).norme1();
            Point3D tan = eye.moins(courbe.getElem().calculerPoint3D(t - 0.001)).norme1();
            Point3D vert = getVerticale().getElem()==null?(tan.prodVect(dir).norme1());//:getVerticale().data0d;
            matrice.setElem(new Matrix33(new Point3D[] { tan, tan.prodVect(dir).norme1(),  dir }));
            System.out.println("Matrice "+matrice.getElem().toString());
            setEye(eye);

        }
    */


    public Point3D pProjVerticale(Point3D vVert) {
        vVert = vVert.norme1();
        double a, b, c, d;
        Point3D dir = getEye().moins(getLookat()).norme1();
        d = dir.dot(eye());
        double k = vVert.dot(dir) - eye().dot(dir);
        Point3D projeté = vVert.plus(dir.mult(k));

        return projeté;
    }

    public void calculerMatriceT(Point3D verticale) {
        double t = this.t.getElem();
        Point3D eye = courbe.getElem().calculerPoint3D(t);
        //TODO DEV
        //setLookat(courbe.getElem().calculerPoint3D(t+ t* 1.001));

        Point3D dir = getLookat().moins(eye).norme1();
        Point3D tan = eye.moins(courbe.getElem().calculerPoint3D(t - 0.001)).norme1();
        Point3D vert = /*getVerticale().getElem()==null?*/(tan.prodVect(dir).norme1());//:getVerticale().data0d;
        Point3D vert2 = pProjVerticale(verticale).moins(eye().norme1());
        matrice.setElem(new Matrix33(new Point3D[]{tan, vert2,
                tan.prodVect(vert2).norme1()}).tild());

        System.out.println("Matrice (tan, vert, dir)" + matrice.getElem().toString());
        setEye(eye);
    }

    @Override
    public Point3D eye() {
        return courbe.getElem().calculerPoint3D(t.getElem());
    }

    @Override
    public Point3D getEye() {
        return courbe.getElem().calculerPoint3D(t.getElem());
    }

    @Override
    public Point3D calculerCurveT(double u, double t) {
        this.t.setElem((Double) t);
        calculerMatrice(null);
        return courbe.getElem().calculerPoint3D((Double) t);
    }

    @Override
    public Point3D calculerPointDansRepere(Point3D p) {
        Point3D p2 = matrice.getElem().mult(p.moins(getEye()));
        return p2;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("courbe", courbe);

    }

    public void setCourbe(StructureMatrix<ParametricCurve> courbe) {
        this.courbe = courbe;
    }

    public void setT(double t) {
        this.t.setElem(t);
        calculerMatriceT(getVerticale().getElem());
    }
}
