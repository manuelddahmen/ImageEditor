package one.empty3.feature20220726.shape;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

public class Rectangle extends ParametricCurve {
    private final double height;
    private final double width;
    private final double y;
    private final double x;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setIncrU(.2 / (4 + width + height));
    }

    public Point3D calculerPoint3D(double t) {
        Point3D point3D = new Point3D(
                (t >= 0 && t < 0.25) ? x + t * width * 4 : (t >= 0.25 && t < 0.50) ? x + width : (t >= 0.50 && t < 0.75) ? x + width + (0.50 - t) * width * 4
                        : (t >= 0.75 && t < 1) ? x : Double.NaN,

                (t >= 0 && t < 0.25) ? y : (t >= 0.25 && t < 0.5) ? y + height * (t - 0.25) * 4 : (t >= 0.5 && t < 0.75) ? y + height :
                        (t >= 0.75 && t < 1) ? y + height + height * (0.75 - t) * 4 : Double.NaN,
                0.0
        );
        if (point3D.get(0).equals(Double.NaN) || point3D.get(1).equals(Double.NaN))
            throw new UnsupportedOperationException("Double NaN Rectangle calculerPoint3D");
        return point3D;
    }

    public double getX() {
        return x;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getY() {
        return y;
    }
}
