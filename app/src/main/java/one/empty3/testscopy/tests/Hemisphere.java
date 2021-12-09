package one.empty3.testscopy.tests;

import one.empty3.library.Axe;
import one.empty3.library.Point3D;
import one.empty3.library.Sphere;

public class Hemisphere extends Sphere {
    private Point3D north;

    public Hemisphere(Point3D center, double radius, Point3D north) {
        this(center, radius);
        this.north = north;
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return super.calculerPoint3D(u, v/2);
    }

    public Hemisphere() {
    }

    public Hemisphere(Axe axis, double radius) {
        super(axis, radius);
    }

    public Hemisphere(Point3D center, double radius) {
        super(center, radius);
    }
}
