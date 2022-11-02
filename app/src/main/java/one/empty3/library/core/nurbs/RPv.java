package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.ZBuffer;
import one.empty3.library.ZBufferImpl;

public class RPv extends ParametricVolume {
    private final ZBuffer zBuffer;
    private Representable representable;

    public RPv(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public Representable getRepresentable() {
        return representable;
    }

    public void setRepresentable(Representable representable) {
        this.representable = representable;
    }

    @Override
    public Point3D calculerPoint3D(Point3D p0) {
        return null;
    }
}
