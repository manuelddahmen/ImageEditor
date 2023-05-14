package one.empty3.feature_arr_style;

import one.empty3.library.*;

/*

Sum [x+i, y+j, dist<sizeK] (I ij *
    gauss derivate ij) 

sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?


 Somme pondérée Gradient(i, j) * dérivée gauss(x-i, y-j) 
= M3 cols, lines, sigmas
 Map of vectors. 
*/
public class SizedGradient extends FilterPixGMatrix {
    private double dist = 2.0;

    public SizedGradient(double dist) {
        super((int) dist, (int) dist);
        this.dist = dist;
    }

    private void fill() {

    }


    public Point3D xy(Point3D p,
                      double dist) {
        double x = p.getX();
        return new Point3D((x * 2) / (x * x + 1) * 4,
                p.getY() - dist, 0.0);
    }

    public Point3D formula(int i, int j) {
        double d = Math.sqrt(i * i + j * j);
        double angle = Math.atan(1.0 * j / i);
        Point3D vecX = new Point3D(
                d * Math.cos(2 * Math.PI * angle),
                d * Math.sin(2 * Math.PI * angle), 0.0
        );
        Point3D vecY = new Point3D(
                d * Math.sin(2 * Math.PI * angle),
                -d * Math.cos(2 * Math.PI * angle), 0.0
        );
        Point3D p2 = new Matrix33(new double[]{
                vecX.getX(), vecX.getY(), 0.0,
                vecY.getX(), vecY.getY(), 0.0,
                0.0, 0.0, 1.0}
        ).mult(new Point3D((double) i, (double) j, 0.0));
        return xy(p2, dist);
    }

    @Override
    public double filter(double x, double y) {
        int count = 0;
        int avg = 0;
        double dxy = 0.0;
        // For for
        // ±± (x*2)/(x*x+1)*k
        for (int i = -avg; i < avg; i++)
            for (int j = -avg; j < avg; j++) {
                dxy += formula(i, j).getX();
                count++;
            }
        return 1.0;
    }
}
