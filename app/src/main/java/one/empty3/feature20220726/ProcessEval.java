package one.empty3.feature20220726;

import javaAnd.awt.Color;
import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public abstract class ProcessEval {
    private int width = 24;
    private int height = 24;
    private ArrayList<File> files = new ArrayList<>();
    private ArrayList<PixM> pixMaps = new ArrayList<>();
    private ArrayList<double[]> features = new ArrayList<>();
    private double[] x;
    private double[] y;
    private double[] w;
    private double[] w1;
    private double[] b;
    private double[][] candidatesVectors;
    private double[][] detectedAsPositives;
    private double[][] detectedAsNegatives;

    public ProcessEval(int width, int height) {

    }
    // y values  -1 à 1
    // xi = pixels

    /***
     * f(x) = w . x  + b // Hyperplane separating POS and NEG
     * w'= SOMME ( d'i yi xi)
     *f(x) = SOMME (d'yi * (xi . x ) + b
     * w' = SOMME (d'i yi xi)
     *
     * f(x) = SOMME (d'i yi (phi(xi) . phi(x)) + B
     *
     *
     */
    public void addFile(File file) {
        files.add(file);
    }

    public void addFiles(File[] files) {
        for (int i = 0; i < files.length; i++) {
            this.files.add(files[i]);
        }
    }

    public void setFeature(double[] f) {
        this.features.add(f);
    }

    public double value(File file) {
        PixM pixM = new PixM(Objects.requireNonNull(ImageIO.read(file)));
        x = pixM.x;
        y = add(dotOuter(x, w), b);
        return Double.NaN;
    }

    private double[] add(double[] a, double[] b) {
        double[] c = new double[x.length];
        for (int i = 0; i < a.length; i++)
            c[i] = a[i] + b[i];
        return a;
    }

    private double[] dotOuter(double[] x, double[] w) {
        double[] a = new double[x.length];
        for (int i = 0; i < a.length; i++)
            a[i] = x[i] * w[i];
        return a;
    }

    public double matchFeature(double[] x, double[] f) {
        double sumFeaturing = 0;
        for (int i = 0; i < x.length; i++) {
            sumFeaturing += f[i] * x[i];
        }
        double sumWithoutFeaturing = 0;
        for (int i = 0; i < x.length; i++) {
            sumWithoutFeaturing += f[i] * x[i];
        }
        return sumFeaturing - sumWithoutFeaturing;
    }
}
