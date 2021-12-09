package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;

public abstract class ParametricVolume extends Representable {

    public static double incrU() {
        return 0.001;
    }

    public static double incrW() {
        return 0.01;
    }

    public static double incrV() {
        return 0.01;
    }

    public abstract Point3D calculerPoint3D(Point3D p0);
}
