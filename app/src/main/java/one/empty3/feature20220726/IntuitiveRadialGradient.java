package one.empty3.feature20220726;

import one.empty3.library.*;

public class IntuitiveRadialGradient extends FilterPixM {
    private PixM pix;
    private int angles = 12;
    double rMaxPixel = 2.0, rMaxDiff = 5.0;
    private int angleCount = 2;

    public IntuitiveRadialGradient(PixM image) {
        super(1, 1);
        this.pix = image;
    }

    public void setMax(double rMax, double rMax2, int angleCount, int iterAngles) {
        rMaxPixel = rMax;
        rMaxDiff = rMax2;
        angles = iterAngles;
        angleCount = angleCount;
    }

    public double filter(double x, double y) {

        double pixel = 0.0;
        double[] pixelExtAngular
                = new double[12];
        double vp =
                arc(x, y, 0, rMaxPixel,
                        0., 2 * Math.PI);
        double vFarApprox = 400000;
        double angle = -1;
        for (int i = 0; i < angles; i++) {
            double v = arc((double) x, (double) y, rMaxPixel, rMaxDiff,
                    2 * Math.PI * i / angles, 2 * Math.PI * (i + 1) / angles);
            if (Math.abs(v - vp) < vFarApprox) {
                vFarApprox =
                        Math.abs(v - vp);
                angle = 2 * Math.PI * (i + 0.5) / angles;
            }
        }
        return angle;
    }


    public PixM filter(PixM o) {
        PixM c = new PixM(o.getColumns(), o.getLines());

        for (int comp = 0; comp < getCompCount(); comp++) {

            setCompNo(comp);
            c.setCompNo(comp);


            for (int i = 0; i < pix.getColumns(); i++) {
                for (int j = 0; j < pix.getLines(); j++) {

                    c.set(i, j, filter(i, j));
                }
            }
        }
        return c;
    }


    public Point2D pcircle(int x, int y,
                           double angle, double r) {
        return new Point2D(Math.cos(2 * Math.PI *
                angle), Math.sin(2 * Math.PI *
                angle))
                .mult(r);
    }

    public double arc(double x, double y, double r1, double r2,
                      double a1, double a2) {
        double eval = 0.0;
        int count = 0;
        double sum = 0.0;
        double dist = 0;
        for (double i = x - r2; i < x + r2; i++)
            for (double j = y - r2; j < y + r2; j++) {

                eval = Math.sqrt(1.0 * (x - i) * (x - i) + (y - j) * (y - j));
                if (eval <= r2 && eval >= r1 &&
                        Math.abs(Math.tan(-a1 + Math.abs((y - j) / (x - i)))) >= Math.tan(a1) &&
                        Math.abs(Math.tan(a2 - Math.abs((y - j) / (x - i)))) <= Math.tan(a2)
                ) {
                    sum += pix.get((int) i, (int) j);//*gauss? Derivate?
                    count++;
                    dist += eval;
                }
            }
        return sum / count;
    }

} 
