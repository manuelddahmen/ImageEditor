package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*** 
 * radial density of region (x, y, r)
 *
 */
public class Histogram3 extends ProcessFile {
    public final double rFract = 2.5;

    public static class Circle {
        public double x, y, r;
        public double i;

        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
            i = 0.0;
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


    public void init() {
    }

    public Histogram3() {
        init();

    }

    public void makeHistogram(double r) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Circle getLevel(PixM m, Circle c) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        for (double i = -c.r; i < c.r; i++) {
            for (double j = -c.r; j < c.r; j++) {
                if (Math.sqrt((i) * (i) + (j) * (j)) < c.r * c.r
                        && c.x + i >= 0 && c.y + j >= 0 && c.x + i < m.getColumns() && c.y + j < m.getLines()) {
                    //intensity += m.mean((int) (c.x + i), (int) (c.y + j), (int)(2*c.r), (int)(2*c.r));
                    intensity += m.getIntensity((int) (c.x + i), (int) (c.y + j));
                    count++;
                }
            }
        }

        if (count > 0) {
            c.i = intensity / count;
        } else {
            c.i = 0.0;
            //    c.r = 0;
        }


        return c;
    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Circle> getPointsOfInterest(PixM m, final double rMin0, double iMin) {
        ArrayList<Circle> circles;
        circles = new ArrayList<>();

        // Classer les points par intensité et rayon

        // for(double intensity=1.0; intensity>=0.0; intensity-=0.1) {
        for (int i = 0; i < m.getColumns(); i++) {
            for (int j = 0; j < m.getLines(); j++) {
                Circle level = getLevel(m, new Circle(i, j, rMin0));
                // level.i = intensity;
                //int index = Math.max(((int) (level.i * numLevels)), 0);
                //index = Math.min(numLevels-1, index);
                double iOrigin = level.i;
                int numLevels = 3;
                int index0 = (int) (level.i * (numLevels - 1));
                //if(index0<0) index0 = 0;
                //if(index0<=min.length) index0 = min.length-1;
                while (level.i >= iMin && level.i > iOrigin - 1.0 / numLevels && level.i < iOrigin + 1.0 / numLevels && level.r < Math.max(m.getColumns(), m.getLines()) / 20.) {
                    level.r *= rFract;
                    getLevel(m, level);
                }

                level.r /= rFract;

                if (level.r >= rMin0 && level.r < Math.max(m.getColumns(), m.getLines())) {
                    //level.i = iOrigin;
                    circles.add(level);
                }
            }

            //   }
        }


        return circles;
    }

    public List<List<Circle>> group(List<Circle> circles) {


        return new ArrayList<>();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean process(File in, File out) {

        init();

        PixM m = new PixM(Objects.requireNonNull(ImageIO.read(in)).bitmap);
        Bitmap image = m.getImage();


        final double radiusIncr = 1;


        Bitmap img2 = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
        List<Circle> pointsOfInterest;
        pointsOfInterest = getPointsOfInterest(m, radiusIncr, 0.5);
        // grands;cercles = grandes iles les separer
        // verifier les distances et constantes i
        // petits cercles successifs entoures
        // de grands ou plus grands cercles =
        // coins, corners et possibles features.

        System.out.println("getPointsOfInterest ");


        double[] i_ir = new double[]{0, 0};
        for (int i = 0; i < pointsOfInterest.size(); i++) {
            Circle c1 = pointsOfInterest.get(i);
            //if (c1.r <= 0 || c1.i <= 0)
            //    pointsOfInterest.remove(i);
            if (i_ir[0] < c1.i)
                i_ir[0] = c1.i;
            if (i_ir[1] < c1.i / c1.r)
                i_ir[1] = c1.i / c1.r;

        }
/*            pointsOfInterest.sort((o1, o2) -> {
                double v = (o2.i/o2.r - o1.i/o1.r)/i_ir[1];
                if (v < 0)
                    return -1;
                if (v > 0)
                    return 1;
                return (int) Math.signum((o2.i - o1.i) / Math.abs(o2.r - o1.r));
            });
*/
        System.out.println("draw ");

        //Graphics graphics = img2.getGraphics();
        for (int i = 0; i < pointsOfInterest.size(); i++) {
            Circle circle = pointsOfInterest.get(i);
            double v = circle.i / circle.r / i_ir[1];
            Color color = Color.valueOf((float) (v), (float) (v), (float) (v));
            //graphics.setColor(color);
            //graphics.fillRect((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (2 * circle.r), (int) (2 * circle.r));
            img2.setPixel((int) (circle.x), (int) (circle.y), color.toArgb());
        }
        // grouper les points par similarités et distances
              /*  group(pointsOfInterest);
                File fileToWrite = new File(directory.getAbsolutePath()
                        + "level" + ".jpg");
                File fileToWrite2 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW.jpg");
                File fileToWrite3 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW_RGB.jpg");
                //fileToWrite.mkdirs();*/
        ImageIO.write(new PixM(img2).normalize(0., 1.).getImage(), "JPEG", out);
                /*
                ImageIO.write(img, "JPEG", fileToWrite);
                ImageIO.write(img, "JPEG", fileToWrite2);
                ImageIO.write(img, "JPEG", fileToWrite3);
*/


        return true;
    }
}
