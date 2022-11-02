package one.empty3.feature20220726.snakes;

import one.empty3.feature20220726.M;
import one.empty3.feature20220726.PixM;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

import javaAnd.awt.*;

import java.util.ArrayList;

/*
E = λ1
XX
Ωin
(I(i, j) − µin)
2 + λ2
X X
Ωout
(I(i, j) − µout)
2 + λ3LΓ

s
 */
public class DipSnake /*extends ParametricCurve*/ {
    private ArrayList<Double> x = new ArrayList<Double>();
    private ArrayList<Double> y = new ArrayList<Double>();
    private ArrayList<Double> imsz = new ArrayList<Double>();
    private double alpha = 0.01;
    private double beta = 0.001;
    private double gamma = 1;

    public DipSnake resample(DipSnake s, double distance, boolean linearOrCube) {
        DipSnake dipSnake = new DipSnake();
        int i = x.size() - 1;
        while (i > 0) {
            dipSnake.add(i + 1, calculerPoint3D(i - 0.5));
            i++;
        }
        return dipSnake;
    }

    public Point3D getControlPoint(int i) {
        return new Point3D(x.get(i), y.get(i), 0.0);
    }

    public int size() {
        return x.size();
    }

    public double length() {
        return x.size();
    }

    public double length(int i1, int i2) {
        double d = 0;
        for (int i = i1; i < i2; i++) {
            d = calculerPoint3D(i2).moins(calculerPoint3D(i1)).norme();
        }
        return d;
    }

    public void add(int pos, Point3D p) {
        x.add(pos, p.get(0));
        y.add(pos, p.get(1));
    }


    //@Override
    public Point3D calculerPoint3D(double t) {
        int T = (int) t;
        double r = t - T;
        if (t > 1.0) {
            return new Point3D(x.get(x.size() - 1), y.get(y.size() - 1))
                    .plus(new Point3D(x.get(0), y.get(0), 0.0)).mult(0.5);
        }
        return new Point3D(x.get(T), y.get(T), 0.0);
    }

    public double pointsIn(PixM original) {
        PixM m = new PixM(original.getColumns(), original.getLines());
        //m.fillIn(this, Color.colorTexture(Color.BLACK), Color.colorTexture(Color.WHITE));
        double moy = 0.0;
        int countIn = 0;
        for (int l = 0; l < original.getLines(); l++)
            for (int c = 0; c < original.getColumns(); c++) {
                if (m.get(c, l) == 1.0) {
                    moy = original.get(c, l);
                    countIn++;
                }
            }
        moy /= countIn;

        double e = 0.0;
        for (int l = 0; l < original.getLines(); l++)
            for (int c = 0; c < original.getColumns(); c++) {
                if (m.get(c, l) == 1.0) {
                    e += (original.getIntensity(l, c) - moy)
                            * (original.getIntensity(l, c) - moy);
                }
            }
        e = e * alpha;
        return e;
    }

    public double pointsOut(M original) {
        PixM m = new PixM(original.getColumns(), original.getLines());

        //m.fillIn(this, Color.colorTexture(Color.WHITE), Color.colorTexture(Color.WHITE));
        for (int l = 0; l < original.getLines(); l++)
            for (int c = 0; c < original.getColumns(); c++) {
                m.set(c, l, 1 - m.get(c, l));
            }
        double moy = 0.0;
        int countOut = 0;
        for (int l = 0; l < original.getLines(); l++)
            for (int c = 0; c < original.getColumns(); c++) {
                if (m.get(c, l) == 1.0) {
                    moy = original.get(c, l);
                    countOut++;
                }
            }
        moy /= countOut;

        double e = 0.0;
        for (int l = 0; l < original.getLines(); l++)
            for (int c = 0; c < original.getColumns(); c++) {
                if (m.get(c, l) == 1.0) {
                    e += (original.getIntensity(l, c) - moy)
                            * (original.getIntensity(l, c) - moy);
                }
            }
        e = e * beta;
        return e;
    }

    public double energy(PixM image) {
        //return energyCurve() + energyGradient(image) - energyExt(image);
        return 0.0;
    }

    public double energyCurve() {
        return this.length() * gamma;

    }

    public double energyGradient(PixM image) {
        return pointsIn(image);

    }

    /*
        public double energyExt(PixM image) {
            return pointsOut(image);
        }
    */
    public void energyMinimization(PixM image) {
        double energy = energy(image);

        int N = 4;
        int n = 0;
        while (n < N) {
            double[] incrC = new double[size()];

            for (int i = 0; i < size(); i++) {
                incrC[i] = image.getLines() / 20.;
            }

            for (int c = 0; c < size(); c++) {
                Point3D normalC = getControlPoint(c);

                setControlPoint(c, getControlPoint(c).plus(normalC.mult(incrC[c])));

                double energy1 = energy(image);

                if (Math.abs(energy1) > Math.abs(energy)) {

                    setControlPoint(c, getControlPoint(c).moins(normalC.mult(incrC[c])));

                    incrC[c] -= incrC[c] / 2;

                } else {
                    setControlPoint(c, getControlPoint(c).moins(normalC.mult(incrC[c])));

                    energy1 = energy(image);

                    if (Math.abs(energy1) > Math.abs(energy)) {

                        setControlPoint(c, getControlPoint(c).plus(normalC.mult(incrC[c])));

                        incrC[c] -= incrC[c];

                    }
                }
                energy = energy1;
            }

            n++;
        }
    }

    private void setControlPoint(int c, Point3D moins) {
        x.set(c, moins.get(0));
        y.set(c, moins.get(1));
    }

    public Point3D normal(int i) {
        return getControlPoint((i + size()) % size())
                .moins(getControlPoint((i - 1 + size()) % size())).norme1();
    }
}