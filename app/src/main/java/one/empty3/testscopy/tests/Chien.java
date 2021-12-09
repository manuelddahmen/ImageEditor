package one.empty3.testscopy.tests;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.tribase.Tubulaire3;

import java.awt.*;

public class Chien extends RepresentableConteneur {
    public Chien() {
        Tubulaire3[] patte = new Tubulaire3[4];
        Point3D tete = new Point3D(0., 1., 0.); //tête
        Point3D queue = new Point3D(1., 0., 1.); // queue
        Sphere tetes = new Sphere(tete, 0.8); //sphère
        tetes.texture(new TextureCol(Color.RED));
        queue.texture(new TextureCol(Color.BLACK));

        for (int i = 0; i < 4; i++) {
            patte[i] = new Tubulaire3();
            patte[i].getSoulCurve().getElem().getCoefficients().getData1d().clear();
            patte[i].texture(new TextureCol(Color.ORANGE));
            ((FctXY) (patte[i].getDiameterFunction().getElem())).setFormulaX("0.6");
        }
        Tubulaire3 corps;
        corps = new Tubulaire3();
        corps.getSoulCurve().getElem().getCoefficients().getData1d().clear();
        corps.getSoulCurve().getElem().getCoefficients().setElem(P.n(0., 1., 0.));
        corps.getSoulCurve().getElem().getCoefficients().setElem(P.n(1., 1., 0.));
        corps.texture(new TextureCol(Color.ORANGE));
        ((FctXY) (corps.getDiameterFunction().getElem())).setFormulaX("1.5");
// patte AVANT
// §1
        ((CourbeParametriquePolynomialeBezier) (patte[0].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(0., -1., 0.), 0);
        ((CourbeParametriquePolynomialeBezier) (patte[0].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(0., 1., 0.), 1); //patte avant
        ((CourbeParametriquePolynomialeBezier) (patte[0].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(0., 1., -1.), 2);
        ((CourbeParametriquePolynomialeBezier) (patte[0].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(0., -1., -1.), 3);
// patte arrière
// §2
        ((CourbeParametriquePolynomialeBezier) (patte[2].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(1., -1., 0.), 0);
        ((CourbeParametriquePolynomialeBezier) (patte[2].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(1., 1., 0.), 1); //patte arrière #1
        ((CourbeParametriquePolynomialeBezier) (patte[2].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(1., 1., -1.), 2);// patte avant #2
        ((CourbeParametriquePolynomialeBezier) (patte[2].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(1., -1., -1.), 3);
        //1,0,0 //etx queue.

        add(corps);
        add(tetes);
        for (int i = 0; i < 4; i += 2) {
            add(patte[i]);

        }

    }

    public static void main(String[] args) {
        new Thread(new TestChien()).start();
    }

}
