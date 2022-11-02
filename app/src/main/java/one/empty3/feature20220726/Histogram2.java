package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.*;
import javaAnd.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * radial density of region (x, y, r)
 *
 */
public class Histogram2 extends ProcessFile {
    private int numLevels = 10;
    private PixM m = null;
    private double[] max;
    private double[] min;

    public class Circle {
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

    //private final int[][][] levels;
    public void setM(PixM m2) {
        this.m = m2;
    }


    public Histogram2(int numLevels) {


        min = new double[numLevels];
        max = new double[numLevels];

        for (int i = 0; i < numLevels; i++) {
            min[i] = 1.0 * i / numLevels;
            max[i] = 1.0 * (i + 1) / numLevels;
        }
        this.numLevels = numLevels;

    }

    public Histogram2() {
        this(15);

    }

    public void makeHistogram(double r) {

    }

    public Circle getLevel(Histogram2.Circle c) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        for (double i = c.x - c.r; i <= c.x + c.r; i++) {
            for (double j = c.y - c.r; j <= c.y + c.r; j++) {
                if (Math.sqrt((i - c.x) * (i - c.x) + (j - c.y) * (j - c.y)) <= c.r * c.r
                        && c.x - c.r >= 0 && c.y - c.r >= 0 && c.x + c.r < m.getColumns() && c.y + c.r < m.getLines()) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                }
            }
        }

        if (count > 0) {
            c.i = intensity / count;
        } else {
            c.i = 0.0;
            c.r = 0;
        }


        return c;
    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }


    public List<Circle> getPointsOfInterest(double rMin0) {
        ArrayList<Circle> circles;
        circles = new ArrayList<>();

        // Classer les points par intensité et rayon

        for (double intensity = 1.0; intensity >= 0.0; intensity -= 0.1) {
            for (int i = 0; i < m.getColumns(); i++) {
                for (int j = 0; j < m.getLines(); j++) {
                    double rMin = rMin0;
                    Circle level = getLevel(new Circle(i, j, rMin));
                    level.i = intensity;
                    //int index = Math.max(((int) (level.i * numLevels)), 0);
                    //index = Math.min(numLevels-1, index);
                    double iOrigin = intensity;

                    double maxI = max[1];
                    double minI = min[0];
                    int
                            index0 = (int) (intensity * (numLevels - 1));
                    //if(index0<0) index0 = 0;
                    //if(index0<=min.length) index0 = min.length-1;
                    while (level.i >= 0 && level.i < 1.0 && level.i >= min[index0] && level.i < max[index0] && rMin < Math.max(m.getColumns(), m.getLines())) {

                        rMin *= 1.3;
                        //index = Math.max(((int) (level.i * numLevels)), 0);
                        //index = Math.min(numLevels-1, index);
                        //maxI = max[index];
                        //minI = min[index];
                        getLevel(level);
                    }
                    level.r /= 1.3;
                    if (level.r > 1) {
                        circles.add(level);
                    }
                }

            }
        }


        return circles;
    }

    public List<List<Circle>> group(List<Circle> circles) {
        List<List<Circle>> out = new ArrayList<>();


        return out;

    }

    public boolean process(File in, File out) {
        try {
            File directory = new File(out.getParent());
            PixM imageCoutours = new PixM(ImageIO.read(in));
            this.m = imageCoutours;
            BufferedImage file = m.getImage();

            int levels = 10;
            double min = 0.0;
            double radiusIncr = 2;


            BufferedImage img = file;
            BufferedImage img2 = new javaAnd.awt.image.BufferedImage(img.getWidth(), img.getHeight(), javaAnd.awt.image.BufferedImage.TYPE_INT_RGB);
            BufferedImage img3 = new javaAnd.awt.image.BufferedImage(img.getWidth(), img.getHeight(), javaAnd.awt.image.BufferedImage.TYPE_INT_RGB);


            List<Circle> pointsOfInterest = getPointsOfInterest(4.0);
            pointsOfInterest.stream().forEach(circle -> {
                if (circle.i >= min && circle.r > 1.0) {
                        /*
                        Graphics graphics = img.getGraphics();
                        graphics.setColor(Color.WHITE);
                        graphics.drawOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (circle.r * 2), (int) (circle.r * 2));
                        graphics = img2.getGraphics();
                        */
                    android.graphics.Color color = Color.color((float) (circle.i < 1.0 ? (circle.i) : 1f), 0f, (float) (circle.i / circle.r < 1.0
                            ? circle.i / circle.r : 1.0));
                    //graphics.setColor(color);
                    //graphics.drawOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (circle.r * 2), (int) (circle.r * 2));
                    img3.setRGB((int) (circle.x), (int) (circle.y), color.toArgb());
                }
            });
            // grands;cercles = grandes iles les separer
            // verifier les distances et constantes i
            // petits cercles successifs entoures 
            // de grands ou plus grands cercles = 
            // coins, corners et possibles features.
            pointsOfInterest.sort(new Comparator<Circle>() {
                @Override
                public int compare(Circle o1, Circle o2) {
                    double v = o2.r - o1.r;
                    if (v < 0)
                        return -1;
                    if (v > 0)
                        return 1;
                    return 0;
                }
            });
            // grouper les points par similarites et distances
              /*  group(pointsOfInterest);
                File fileToWrite = new File(directory.getAbsolutePath()
                        + "level" + ".jpg");
                File fileToWrite2 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW.jpg");
                File fileToWrite3 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW_RGB.jpg");
                //fileToWrite.mkdirs();*/
            ImageIO.write(new PixM(img3).normalize(0., 1.).getImage(), "JPEG", out);
                /*
                ImageIO.write(img, "JPEG", fileToWrite);
                ImageIO.write(img, "JPEG", fileToWrite2);
                ImageIO.write(img, "JPEG", fileToWrite3);
*/


        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
