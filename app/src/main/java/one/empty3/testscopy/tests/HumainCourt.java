package one.empty3.testscopy.tests;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.tribase.Tubulaire3;

import java.awt.*;

public class HumainCourt extends RepresentableConteneur {
    protected double t;
    public void rotateZ(Tubulaire3 tubulaire3, int index0, int indexP, double angle, double fract) {
        Point3D elem0 = tubulaire3.getSoulCurve().getElem().getCoefficients().getElem(index0);
        int size = tubulaire3.getSoulCurve().getElem().getCoefficients().getData1d().size();
        int signum = (int) Math.signum(indexP - index0);

        Matrix33 matrix33 = Matrix33.rotationZ(angle*fract);

        for(int i=index0+signum; i<size&&i>=0; i+=signum) {
            Point3D elemI = tubulaire3.getSoulCurve().getElem().getCoefficients().getElem(i);
            Point3D mult = matrix33.mult(elemI.moins(elem0)).plus(elem0);
            tubulaire3.getSoulCurve().getElem().getCoefficients().getData1d().set(i, mult);
            System.out.print("i:" + i+" p:" + mult);
        }
    }

    /**
     * Marche move0
     */
    public void move0(ParametricSurface parametricSurface, double u0, double v0, double pas0, double u1, double v1) {

    }
    public void tubeAddPoint(Tubulaire3 tube, Point3D p) {
        tube.getSoulCurve().getElem().getCoefficients().getData1d().add(p);
    }

    public HumainCourt() {
    }
    public void init() {
        getListRepresentable().clear();
        Tubulaire3[] patte = new Tubulaire3[4];
        Point3D tete = new Point3D(0., 21., 0.); //tête
        Point3D queue = new Point3D(1., 0., 1.); // queue
        Sphere tetes = new Sphere(tete, 2.); //sphère
        tetes.texture(new TextureCol(Color.RED));
        queue.texture(new TextureCol(Color.BLACK));

        Cube ventre = new Cube(5.0, P.n(0., 15., 0));
        for (int i = 0; i < 4; i++) {
            patte[i] = new Tubulaire3();
            patte[i].getSoulCurve().getElem().getCoefficients().getData1d().clear();
            patte[i].texture(new TextureCol(Color.ORANGE));
            patte[i].getDiameterFunction().getElem().setFormulaX("0.6");
        }
        Tubulaire3 corps;
        corps = new Tubulaire3();
        corps.getSoulCurve().getElem().getCoefficients().getData1d().clear();
        corps.getSoulCurve().getElem().getCoefficients().setElem(P.n(0., 1., 0.), 0);
        corps.getSoulCurve().getElem().getCoefficients().setElem(P.n(1., 1., 0.), 1);
        corps.texture(new TextureCol(Color.ORANGE));
        corps.getDiameterFunction().getElem().setFormulaX("1.5");
// JAMBE AVANT DROIT
// §1
        for (int i = 0; i < 2; i++) {
            tubeAddPoint(patte[i], new Point3D(-1., 0., 2. * (2 * i - 1)));
            tubeAddPoint(patte[i], new Point3D(0., 0., 2. * (2 * i - 1)));
            tubeAddPoint(patte[i], new Point3D(0., 5., 2. * (2 * i - 1)));
            tubeAddPoint(patte[i], new Point3D(0., 10., 2. * (2 * i - 1)));
            tubeAddPoint(patte[i], new Point3D(0., 11., 0.));
            tubeAddPoint(patte[i], new Point3D(0., 15., 0.));
            tubeAddPoint(patte[i], new Point3D(0., 20., 0.));
            tubeAddPoint(patte[i], new Point3D(0., 21., 0.));
            rotateZ(patte[i], 3, 1, Math.signum(i - 0.5), Math.IEEEremainder(2*t, 4)/2);

        }
        for (int i = 0; i < 2; i++) {
            tubeAddPoint(patte[i+2], new Point3D(0., 20., 1. * (2 * i - 1)));
            tubeAddPoint(patte[i+2], new Point3D(0., 20., 2. * (2 * i - 1)));
            tubeAddPoint(patte[i+2], new Point3D(0., 15., 2. * (2 * i - 1)));
            tubeAddPoint(patte[i+2], new Point3D(0., 10., 2. * (2 * i - 1)));
            tubeAddPoint(patte[i+2], new Point3D(0., 9., 2. * (2 * i - 1)));
            rotateZ(patte[i+2], 1, 3, Math.signum(i - 0.5), Math.IEEEremainder(2*t, 4)/2);
        }
        add(corps);
        add(tetes);
        add(ventre);
        for (int i = 0; i < 4; i++) {
            add(patte[i]);

        }
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }
}
