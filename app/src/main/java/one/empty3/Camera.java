/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3;

import javaAnd.awt.Point;
import one.empty3.library.Barycentre;
import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.StructureMatrix;
import one.empty3.library.ZBufferImpl;

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
    protected int type_perspective = PERSPECTIVE_OEIL;

    protected StructureMatrix<Point3D> eye = new StructureMatrix<>(0, Point3D.class);
    protected StructureMatrix<Point3D> lookat = new StructureMatrix<>(0, Point3D.class);

    protected StructureMatrix<Double> scale = new StructureMatrix<>(1, Double.class);
    protected StructureMatrix<Boolean> imposerMatrice = new StructureMatrix<>(0, Point3D.class);
    protected StructureMatrix<Matrix33> matrice = new StructureMatrix<>(0, Matrix33.class);
    protected final StructureMatrix<Point3D> verticale = new StructureMatrix<>(0, Point3D.class);

    {

        matrice.setElem(Matrix33.I);
    }

    private Barycentre position;
    public Camera(boolean pass) {
        imposerMatrice.setElem(false);
    }
    public Camera() {
        this(new Point3D(100d, 0d, 0.0), Point3D.O0, Point3D.Y);
    }

    public StructureMatrix<Point3D> getVerticale() {
        return verticale;
    }

    public Camera(Point3D eye, Point3D lookat) {

        this(eye, lookat, null);

    }

    public Camera(Point3D eye, Point3D lookat, Point3D up) {
        imposerMatrice.setElem(false);
        this.eye.setElem(eye);
        this.lookat.setElem(lookat);
        if(up!=null)
            verticale.setElem(up);
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
        return Point3D.Z.prodVect(z).mult(-1d).prodVect(z).norme1();
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

    @Deprecated
    public void calculerMatrice(Point3D vertical) {
        if (!imposerMatrice.getElem()) {
            if (vertical == null) {
                verticale.setElem( calculerVerticaleParDefaut(getLookat().moins(eye.getElem())) );
            } else {
                verticale.setElem(vertical);
            }

            Point3D z = getLookat().moins(getEye()).norme1();
            Point3D x = z.prodVect(verticale.getElem()).norme1();
            Point3D y = z.prodVect(x);

            setMatrix(x, y, z);
        }
    }

    public Point3D calculerPointDansRepere(Point3D p) {
        //Point3D p2 = matrice.getElem().mult(p.moins(getEye()));
        Point3D p2 = matrice.getElem().mult(p.moins(eye()).plus(getLookat()))
                .plus(getLookat().moins(p));
        p2.texture(p.texture());
        return p2;
    }

    /*__
     * @return
     */
    public Point3D eye() {
        return getEye();
    }

    public Point3D getEye() {
        return eye.getElem();
    }
    public void setEye(Point3D eye) {
        this.eye.setElem(eye);
    }

    public Point3D getLookat() {
        return lookat.getElem();
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

    private Point coordonneesPointEcranPerspective(Point3D x3d, int la, int ha) {

        if (x3d.getZ() > 0 && -getAngleX() < Math.atan(x3d.getX() / x3d.getZ())
                && Math.atan(x3d.getX() / x3d.getZ()) < getAngleX() && -getAngleY() < Math.atan(x3d.getY() / x3d.getZ())
                && Math.atan(x3d.getY() / x3d.getZ()) < getAngleY()) {

            double scale = (1.0 / (x3d.getZ()));

            double x = Math.atan(x3d.getX() / x3d.getZ())/x3d.getZ()*la;
            double y = Math.atan(x3d.getX() / x3d.getZ())/x3d.getZ()*ha;

            double r = (angleY.getElem()/angleX.getElem());
            return new Point(
                    (int) ((   x3d.getX() * scale * la * r + la / 2)),
                    (int) (( - x3d.getY() *scale * ha + ha / 2)));
/*
            Point p = new Point((int) (x + la / 2), (int) (y + ha / 2));
            Logger.getAnonymousLogger().log(Level.INFO, p.toString());

            return p;
  */      }
        return null;

    }

    public Point coordonneesPointEcranIsometrique(Point3D p, ZBufferImpl.Box2D box, int la, int ha) {
        Point p2 = new Point(
                (int) (1.0 * la / (box.getMaxx() - box.getMinx()) * (p.getX() - box.getMinx())),
                ha - (int) (1.0 * ha / (box.getMaxy() - box.getMiny()) * (p.getY() - box.getMiny())));
        if (p2.getX() >= 0.0 && p2.getX() < la && p2.getY() >= 0 && p2.getY() < ha) {
            return p2;
        } else {
            return null;
        }
    }


    public Point coordonneesPoint2D(Point3D p, ZBuffer impl) {
        switch (type_perspective) {
            case PERSPECTIVE_ISOM:
                //return coordonneesPointEcranIsometrique(coordonneesPoint3D(p), impl.box, impl.la, impl.ha);
            case PERSPECTIVE_OEIL:
                return coordonneesPointEcranPerspective(calculerPointDansRepere(p), impl.la(), impl.ha());
            default:
                throw new UnsupportedOperationException("Type de perspective non reconnu");
        }
    }

    public double distanceCamera(Point3D x3d) {
        switch (type_perspective) {
            case PERSPECTIVE_ISOM:
                return x3d.getZ() - eye.getElem().getZ();
            case PERSPECTIVE_OEIL:
                return x3d.moins(eye.getElem()).getZ();
            default:
                throw new UnsupportedOperationException("Type de perspective non reconnu");
        }

    }

    public StructureMatrix<Double> getScale() {
        return scale;
    }

    public void setScale(StructureMatrix<Double> scale) {
        this.scale = scale;
    }

    protected void setVerticale(Point3D vert2) {
        this.verticale.setElem(vert2);
    }
}
