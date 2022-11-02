package one.empty3.feature20220726;


import java.io.*;
import java.util.*;

import one.empty3.io.ProcessFile;
import one.empty3.library.*;
import one.empty3.library.core.nurbs.*;

import javaAnd.awt.image.imageio.ImageIO;

/*
 1 prendre les points entre t=0.0 et t=1.0
 extraire un polygone interieur et exterieur,
 comme gauche/droite.
 2 calculer somme(i-moyenneI)2 a' chaque
 point.
 3 de?
 ***/
public class Snake extends ProcessFile {

    private CourbeParametriquePolynomialeBezier spline;
    private List<double[]> in, out;

    PixM pix;
    PixM pix3;

    public void initCurve() {
        double c = (double) (pix.getColumns());
        double l = (double) (pix.getLines());
        spline = new CourbeParametriquePolynomialeBezier();
        spline.getCoefficients().getData1d().add(P.n(c / 2, l / 3, 0.));
        spline.getCoefficients().getData1d().add(P.n(2 * c / 3, l / 2, 0.));
        spline.getCoefficients().getData1d().add(P.n(c / 2, 2 * l / 3, 0.));
        spline.getCoefficients().getData1d().add(P.n(c / 3, l / 2, 0.));
        spline.getCoefficients().getData1d().add(P.n(c / 2, l / 3, 0.));
    }

    public void classification() {
        in = new ArrayList<>();
        out = new ArrayList<>();
        double[] avg = new double[2];
        int[] cpt = new int[2];
        Point3D vecTan0, vecTan;
        Point3D vecNor0, vecNor;
        PixM pix2 = new PixM(pix.getColumns(), pix.getLines());
        List<Point3D> p = spline.getCoefficients().getData1d();

        double sumOut = 0.0;
        double sumIn = 0.0;
        double[] energy = new double[]{0.0, 0.0};
        for (double t = 0.; t < 1.; t += 1. / pix.getColumns()) {
            pix2.setCompNo(0);
            Point3D p2 = spline.calculerPoint3D(t);
            pix2.set((int) (double) (p2.getX()), (int) (double) (p2.getY()), 1.0);// si get(x,y)>0 ??? separer les courbes
        }
        for (int i = 0; i < pix.getColumns(); i++) {
            boolean pOut = true;
            for (int j = 0; j < pix.getLines(); j++) {
                if (pix2.get(i, j) == 1.0) {
                    pOut = !pOut;

                }
                if (pOut) {
                    pix2.setValues(i, j, 0.0, 0.0, 0.0);

                    out.add(M.getVector(1, new double[][]{pix.getValues(i, j), pix2.getValues(i, j)}));

                    avg[1] += pix.get(i, j);
                    cpt[1]++;
                } else {
                    pix2.setValues(i, j, 1.0, 1., 1.);

                    in.add(M.getVector(0, new double[][]{pix.getValues(i, j), pix2.getValues(i, j)}));

                    avg[0] += pix.get(i, j);
                    cpt[0]++;
                }

            }
        }
        avg[1] /= cpt[1];
        avg[0] /= cpt[0];


        //       pix3 = new PixM(pix2.getColumns(), pix2.getLines());


        for (double[] v : in) {

            double e = Math.pow(pix.getIntensity((int) (v[0]),
                    (int) (v[1])) - avg[0], 2);
            pix3.setCompNo(1);
            pix3.set((int) (double) (v[0]), (int) (double) (v[1]), e);
            energy[0] += e;
        }

        for (double[] v : out) {
            double e = Math.pow(pix.getIntensity((int) (v[0]),
                    (int) (v[1])) - avg[1], 2);
            pix3.setCompNo(2);
            pix3.set((int) (double) (v[0]), (int) (double) (v[1]), -e);
            energy[1] -= e;
        }
    }

    public boolean process(File in, File out) {
        try {
            pix = PixM.getPixM(ImageIO.read(in), 500);
            pix3 = new PixM(pix.getColumns(), pix.getLines());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        initCurve();
        classification();

        try {
            ImageIO.write(pix3.normalize(0., 1.).getImage(),
                    "jpg", out);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
