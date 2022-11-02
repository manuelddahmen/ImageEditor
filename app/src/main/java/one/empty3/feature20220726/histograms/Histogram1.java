package one.empty3.feature20220726.histograms;

import one.empty3.feature20220726.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

/*
 * radial density of region (x, y, r)
 * by mean or mean square or somewhat else.
 */
public class Histogram1 extends ProcessFile {

    private int kMax = 3;
    private double fractMax = 0.2;

    public class Circle {
        public double x, y, r;
        public double i;
        public Point3D maxColor;

        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    ", i=" + i +
                    "n maxColor = p " + maxColor.toString() +
                    '}';
        }
    }

    //private final int[][][] levels;


    public void makeHistogram(double r) {

    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }

    public Circle getLevel(Circle c, PixM m) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        double sum = 0;
        int count = 0;
        c.maxColor = Point3D.O0;
        double intensity = 0.0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (Math.sqrt((i - c.x) * (i - c.x) + (j - c.y) * (j - c.y)) <= c.r
                        && c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.x + c.r < m.getLines()) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                    Point3D p = m.getP((int) i, (int) j);
                    if (p.norme() > 0.3 && p.moins(c.maxColor).norme() > 0.3) {
                        c.maxColor = p;
                        sum++;
                    }
                }
            }
        }
        c.maxColor = c.maxColor.mult(1 / (sum + 1));
        if (count > 0)
            c.i = intensity / count;
        else {
            c.i = 0.0;
            c.r = 1;
        }


        return c;
    }

    public Circle getLevel(Circle c, PixM m, double r0) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        double sum = 0.0;
        c.maxColor = Point3D.O0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (Math.sqrt((i - c.x) * (i - c.x) + (j - c.y) * (j - c.y)) <= c.r
                        && Math.sqrt((i - c.x) * (i - c.x) + (j - c.y) * (j - c.y)) >= r0
                        && c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.x + c.r < m.getLines()) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                    if (m.getP((int) i, (int) j).norme() > c.maxColor.norme()) {
                        c.maxColor = m.getP((int) i, (int) j);
                        sum++;
                    }
                }
            }
        }
        c.maxColor = c.maxColor.mult(1 / (sum + 1));

        if (count > 0)
            c.i = intensity / count;
        else {
            c.i = 0.0;
            c.r = 1;
        }


        return c;
    }

    @Override
    public boolean process(File in, File out) {
        PixM inP;
        inP = PixM.getPixM(ImageIO.read(in), maxRes);
        PixM outP = inP.copy();
        double maxR = Math.min(inP.getLines(), inP.getColumns()) * fractMax;
        for (int i = 0; i < inP.getColumns(); i++) {
            for (int j = 0; j < inP.getLines(); j++) {
                double maxIJ = 0.0;
                int rIJ = 3;
                Circle cc = null;
                for (int k = 3; k < maxR; k += this.kMax) {
                    Circle c = getLevel(new Circle(i, j, k), inP);
                    double candidateI = c.i;
                    double candidateR = c.r;
                    if (cc == null)
                        cc = c;
                    if (candidateI > maxIJ) {
                        maxIJ = candidateI;
                        rIJ = k;
                        cc = c;
                    }
                }

                Point3D intensityColor = Point3D.n(maxIJ, maxIJ, maxIJ).mult(maxIJ * maxR / rIJ).multDot(
                        inP.getP(i, j));
                outP.setP(i, j, cc.maxColor);
            }
        }

// Colorier en fonction des pixels voisins
//        Circle c2 = getLevel(cc, inP, cc.r/2);

        try {
            ImageIO.write(outP.normalize(0, 1).getImage(), "jpg", out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

}
