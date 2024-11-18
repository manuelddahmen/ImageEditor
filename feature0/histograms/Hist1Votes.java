<<<<<<<< HEAD:feature0/histograms/Hist1Votes.java
package one.empty3.feature.histograms;
========
package one.empty3.androidFeature.histograms;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/histograms/Hist1Votes.java

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Objects;

<<<<<<<< HEAD:feature0/histograms/Hist1Votes.java
import one.empty3.ImageIO;
import one.empty3.feature.PixM;
========
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/histograms/Hist1Votes.java
import one.empty3.io.ProcessFile;
import one.empty3.featureAndroid.PixM;
import one.empty3.library.Point3D;

public class Hist1Votes extends ProcessFile {
    @NonNull
    private Point3D pickedColor = Point3D.O0;
    private int kMax = 3;
    private double fractMax = 0.05;//0.05;
    private int maxR = kMax;

    public static class Circle {
        public double x = 0.0, y = 0.0, r = 0.0;
        public double i = 0.0;
        public Point3D maxColor = Point3D.O0;
        public Point3D mincolor = Point3D.n(1, 1, 1);
        public double count = 0.0;

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
        c.mincolor = Point3D.n(1, 1, 1);
        double intensity = 0.0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.x + c.r < m.getLines()
                        && (i == c.x - c.r || j == c.y - c.r || i == c.x + c.r || j == c.y + c.r)) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                    Point3D p = m.getP((int) i, (int) j);
                    if (p.norme() > 0.3 && p.moins(c.maxColor).norme() > 0.3) {
                        c.maxColor = p;
                        sum++;
                    }
                    if (p.norme() > 0.3 && p.moins(c.mincolor).norme() > 0.3) {
                        c.mincolor = p;
                        sum++;
                    }
                }
            }
        }
        c.maxColor = c.maxColor.mult(1 / (sum + 1));
        if (count > 0) {
            c.i = intensity;
            c.count = count;
        } else {
            c.i = 0.0;
            // c.r = 1;
        }


        return c;
    }


    @Override
    public boolean process(File in, File out) {
        PixM inP = new PixM(Objects.requireNonNull(one.empty3.ImageIO.read(in)));
<<<<<<<< HEAD:feature0/histograms/Hist1Votes.java
        PixM outP= new PixM(inP.getColumns(), inP.getLines());
========
        PixM outP = new PixM(inP.getColumns(), inP.getLines());
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/histograms/Hist1Votes.java

        int[] ints = new int[inP.getLines() * inP.getColumns() * 3];
        double maxR = Math.min(inP.getLines(), inP.getColumns()) * fractMax;
        for (int j = 0; j < inP.getLines(); j++) {
            for (int i = 0; i < inP.getColumns(); i++) {
                for (int c = 0; c < inP.getCompCount(); c++) {
                    inP.setCompNo(c);
                    outP.setCompNo(c);

                    for (int k = 1; k < maxR; k += 1) {
                        if (outP.getP(i, j).equals(Point3D.O0)) {
                            Circle c1 = getLevel(new Circle(i, j, maxR), inP);
                            if (c1.i > 0.0) {
                                //Point3D n = new Point3D(c.i, c.r, c.count);
                                Point3D n = new Point3D(0.0, 0.0, 0.0);
                                n.set(c, c1.maxColor.mult(c1.i).moins(c1.mincolor.mult(c1.r)).get(c));
                                ;
                                outP.set(i, j, n.get(c) + outP.get(i, j));
                            }
                        }
                    }
                }
            }
        }
        try {
            //one.empty3.ImageIO.write(outP.normalize(0, 1).getImage().getBitmap(), "jpg", out);
<<<<<<<< HEAD:feature0/histograms/Hist1Votes.java
            one.empty3.ImageIO.write(outP.normalize(0,1).getBitmap(), "jpg", out);
========
            one.empty3.ImageIO.write(outP.normalize(0, 1).getBitmap().getBitmap(), "jpg", out);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/histograms/Hist1Votes.java
            //one.empty3.ImageIO.write(outP0.normalize(0, 1).getImage().getBitmap(), "jpg", out);
            return true;

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return true;
    }

    private void getExtrema(Hist4Contour3.Circle circle, Point3D min, Point3D max) {

    }
}
