package one.empty3.feature20220726;

import one.empty3.library.Point3D;

import javaAnd.awt.*;
import javaAnd.awt.image.BufferedImage;

public class QuadTransform2D {
    private javaAnd.awt.image.BufferedImage i1, i2;
    private Point3D[] p1;
    private Point3D[] p2;

    public class Inter {
        Point3D p;
        android.graphics.Color c;

        public Inter(Point3D p, android.graphics.Color c) {
            this.p = p;
            this.c = c;
        }

        public Point3D getP() {
            return p;
        }

        public void setP(Point3D p) {
            this.p = p;
        }

        public android.graphics.Color getC() {
            return c;
        }

        public void setC(Color c) {
            this.c = c;
        }
    }

    public QuadTransform2D(BufferedImage i1, javaAnd.awt.image.BufferedImage i2, Point3D[] p1, Point3D[] p2) {
        this.i1 = i1;
        this.i2 = i2;
        this.p1 = p1;
        this.p2 = p2;
    }

    private Point3D[] quadTemp(double t) {
        Point3D[] pTemp = new Point3D[4];
        for (int i = 0; i < p1.length; i++) {
            pTemp[i] = p1[i].plus(p2[i].moins(p1[i]).mult(t));
        }
        return pTemp;
    }

    public Point3D pT(Point3D[] pTemp, double p1243, double p1423) {
        Point3D plus0 = pTemp[0].plus(pTemp[1].moins(pTemp[0]).mult(p1243));
        Point3D plus1 = pTemp[3].plus(pTemp[2].moins(pTemp[4]).mult(p1243));
        return plus0.plus((plus1.moins(plus0).mult(p1423)));
    }

    public Inter inter(double p1243, double p1423, double t) {
        // Color cp0+(cpa0-cpaT)*t ???
        // Texture???
        Point3D[] pTemp = quadTemp(t);
        Point3D[] p0 = quadTemp(0.0);
        Point3D pa0 = pT(p0, p1243, p1423);
        Point3D pa1 = pT(p1, p1243, p1423);
        Point3D ca0 = new Point3D(i1.getRGB((int) (double) (pa0.getX()), (int) (double) pa0.getY()));
        Point3D ca1 = new Point3D(i1.getRGB((int) (double) (pa0.getX()), (int) (double) pa0.getY()));
        Point3D pT = pT(pTemp, p1243, p1423);
        Point3D cAt = ca0.plus(ca1.moins(ca0).mult(t));
        android.graphics.Color color = Color.color((float) (double) (cAt.get(0)),
                (float) (double) (cAt.get(1)),
                (float) (double) (cAt.get(2)));// p.toColor()
        return new Inter(pT, new Color(color));
    }


}
