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

import android.graphics.Point;

/*__
 * @author Manuel Dahmen
 */
public class Camera extends CameraBox {

    /*__
     *
     */
    public static final int PERSPECTIVE_ISOM = 0;
    public static final int PERSPECTIVE_OEIL = 1;
    private static final long serialVersionUID = 2743860672948547944L;
    public int type_perspective = PERSPECTIVE_OEIL;

    public static Camera PARDEFAULT = new Camera();

    protected StructureMatrix<Point3D> eye = new StructureMatrix<>(0, Point3D.class);
    protected StructureMatrix<Point3D> lookat = new StructureMatrix<>(0, Point3D.class);

    protected StructureMatrix<Boolean> imposerMatrice = new StructureMatrix<>(0, Point3D.class);
    protected StructureMatrix<Matrix33> matrice = new StructureMatrix<>(0, Matrix33.class);
    private StructureMatrix<Point3D> verticale = new StructureMatrix<>(0, Point3D.class);

    {

        matrice.setElem(Matrix33.I);
    }

    private Barycentre position;

    public Camera() {
        this(new Point3D(0d, 0d, -100d), Point3D.O0, Point3D.Y);
        verticale.setElem(new Point3D(Point3D.Y));
    }

    public StructureMatrix<Point3D> getVerticale() {
        return verticale;
    }

    public void setVerticale(StructureMatrix<Point3D> verticale) {
        this.verticale = verticale;
    }

    public Camera(Point3D eye, Point3D lookat) {
        this(eye, lookat, null);
    }

    public Camera(Point3D eye, Point3D lookat, Point3D up) {
        imposerMatrice.setElem(false);
        this.eye.setElem(eye);
        this.lookat.setElem(lookat);
        calculerMatrice(up);

    }

    protected void rotateMatrixXaxis(double angle) {
        matrice.setElem(Matrix33.rotationX(angle).mult(matrice.getElem()));
    }

    protected void rotateMatrixYaxis(double angle) {
        matrice.setElem(Matrix33.rotationY(angle).mult(matrice.getElem()));

    }

    protected void rotateMatrixZaxis(double angle) {
        matrice.setElem(Matrix33.rotationZ(angle).mult(matrice.getElem()));

    }


    protected Point3D calculerVerticaleParDefaut(Point3D senseAxeCamera) {
        Point3D z = senseAxeCamera.norme1();
        return Point3D.Y.prodVect(z).prodVect(z).mult(-1d).norme1();
    }

    protected Point3D calculerHorizontaParDefaut(Point3D senseAxeCamera) {
        Point3D z = senseAxeCamera.norme1();
        return z.prodVect(Point3D.X).prodVect(z).norme1();
    }

    public void setMatrix(Point3D x, Point3D y, Point3D z) {
        Matrix33 m = new Matrix33();

        // Z SORT DE L4ECRAN
        for (int j = 0; j < 3; j++) {
            m.set(j, 2, z.get(j));
        }
        // X HORIZONTALE VERS LA GAUCHE
        for (int j = 0; j < 3; j++) {
            m.set(j, 0, x.get(j));
        }
        // Y VERTICALE VERS LE BAS
        for (int j = 0; j < 3; j++) {
            m.set(j, 1, y.get(j));
        }
        this.matrice.setElem(m.tild());
    }

    public void setMatrix(Matrix33 m) {
        // Z SORT DE L4ECRAN

        this.matrice.setElem(m.tild());
    }

    public void calculerMatrice(Point3D verticale) {
        if (!imposerMatrice.getElem()) {
            if (verticale == null)
                verticale = calculerVerticaleParDefaut(getLookat().moins(eye.getElem()));


            Point3D z = getLookat().moins(eye.getElem()).norme1();
            Point3D x = z.prodVect(verticale/* Y */).norme1();
            Point3D y = verticale;

            setMatrix(x, y, z);
        }
    }

    public Point3D calculerPointDansRepere(Point3D p) {
        Point3D p2 = matrice.getElem().mult(p.moins(getEye()));
        return p2;
    }

    /*__
     * @return
     */
    public Point3D eye() {
        return getEye();
    }

    public Point3D getEye() {
        return calculerPoint(eye.getElem());
    }

    private Point3D calculerPoint(Point3D elem) {
        return elem;
    }

    public void setEye(Point3D eye) {
        this.eye.setElem(eye);
    }

    public Point3D getLookat() {
        return calculerPoint(lookat.getElem());
    }

    public void setLookat(Point3D lookat) {
        this.lookat.setElem(lookat);
    }

    public void imposerMatrice(boolean im) {
        this.imposerMatrice.setElem(im);
    }

    public void imposerMatrice(Matrix33 mat) {
        this.imposerMatrice.setElem(true);
        this.matrice.setElem(mat);
    }


    @Override
    public String toString() {
        return "camera (\n\t" + eye.toString() + "\n\t" + lookat.toString()
                + "\n\t)";
    }

    public Matrix33 getMatrice() {
        return matrice.getElem();
    }

    public Matrix33 getMatrix() {
        return matrice.getElem();
    }

    public void setMatrice(Matrix33 matrice) {
        this.matrice.setElem(matrice);
    }

    {
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("eye/eye", eye);
        getDeclaredDataStructure().put("lookat/lookAt", lookat);
        getDeclaredDataStructure().put("matrice/matrice", matrice);
        getDeclaredDataStructure().put("verticale/verticale", verticale);
        calculerMatrice(verticale.getElem());

    }

    public Point coordonneesPointEcranPerspective(Point3D x3d, int la, int ha) {


        if (x3d.getZ() > 0 && -getAngleX() < Math.atan(x3d.getX() / x3d.getZ())
                && Math.atan(x3d.getX() / x3d.getZ()) < getAngleX() && -getAngleY() < Math.atan(x3d.getY() / x3d.getZ())
                && Math.atan(x3d.getY() / x3d.getZ()) < getAngleY()) {

            double scale = (1.0 / (x3d.getZ()));
            return new Point((int) (x3d.getX() * scale * la + la / 2), (int) (-x3d.getY() * scale * ha + ha / 2));
        }
        return null;

    }

    public Point coordonneesPointEcranIsometrique(Point3D p, ZBufferImpl.Box2D box, int la, int ha) {
        Point p2 = new Point(
                (int) (1.0 * la / (box.getMaxx() - box.getMinx()) * (p.getX() - box.getMinx())),
                ha - (int) (1.0 * ha / (box.getMaxy() - box.getMiny()) * (p.getY() - box.getMiny())));
        if (p2.x >= 0.0 && p2.x < la && p2.y >= 0 && p2.y < ha) {
            return p2;
        } else {
            return null;
        }
    }


    public Point coordonneesPoint2D(Point3D p, ZBufferImpl impl) {
        switch (type_perspective) {
            case PERSPECTIVE_ISOM:
                return coordonneesPointEcranIsometrique(coordonneesPoint3D(p), impl.box, impl.la, impl.ha);
            case PERSPECTIVE_OEIL:
                return coordonneesPointEcranPerspective(coordonneesPoint3D(p), impl.la, impl.ha);
            default:
                throw new UnsupportedOperationException("Type de perspective non reconnu");
        }
    }

    public Point3D coordonneesPoint3D(Point3D p) {
        return calculerPointDansRepere(p);
    }

    public double distanceCamera(Point3D x3d) {
        switch (type_perspective) {
            case PERSPECTIVE_ISOM:
                return x3d.getZ() - eye.getElem().getZ();
            case PERSPECTIVE_OEIL:
                return x3d.moins(eye.getElem()).norme();
            default:
                throw new UnsupportedOperationException("Type de perspective non reconnu");
        }

    }

}
