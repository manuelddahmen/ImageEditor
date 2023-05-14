package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.util.Vector;

/*
 * radial density of region (x, y, r)
 * by mean or mean square or somewhat else.
 */
public class Histogram extends ProcessFile {

    private int kMax = 3;
    private double fractMax = 0.4;

    public class Circle {
        public double x, y, r;
        public double i;

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
        int count = 0;
        double intensity = 0.0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (Math.sqrt((i - c.x) * (i - c.x) + (j - c.y) * (j - c.y)) <= c.r
                        && c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.x + c.r < m.getLines()) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                }
            }
        }

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
        if (!isImage(in))
            return false;
        PixM inP;
        inP = PixM.getPixM(ImageIO.read(in).getBitmap(), maxRes);
        PixM outP = inP.copy();
        double maxR = Math.min(inP.getLines(), inP.getColumns()) / 5.;
        for (int i = 0; i < inP.getColumns(); i++) {
            for (int j = 0; j < inP.getLines(); j++) {
                double maxIJ = 0.0;
                int rIJ = 3;
                for (int k = 3; k < maxR * fractMax; k += this.kMax) {
                    Circle c = getLevel(new Circle(i, j, k), inP);
                    double candidateI = c.i;
                    double candidateR = c.r;
                    if (candidateI > maxIJ) {
                        maxIJ = candidateI;
                        rIJ = k;
                    }
                }
                outP.setP(i, j, Point3D.n(maxIJ, maxIJ, maxIJ).mult(maxIJ * maxR / rIJ));
            }
        }


        ImageIO.write(outP.normalize(0, 1).getImage(), "jpg", out);
        return true;
    }


}
