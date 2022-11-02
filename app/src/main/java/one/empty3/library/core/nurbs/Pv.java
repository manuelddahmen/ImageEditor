package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.StructureMatrix;

public class Pv extends ParametricVolume {
    private StructureMatrix<ParametricSurface> surfaceAb = new StructureMatrix<>(1, ParametricSurface.class);

    public Pv() {

    }

    @Override
    public Point3D calculerPoint3D(Point3D p0) {
        Point3D pB = surfaceAb.getElem(1).calculerPoint3D(p0.get(0), p0.get(1));
        Point3D pA = surfaceAb.getElem(0).calculerPoint3D(p0.get(0), p0.get(1));
        return pA.plus(pB.moins(pA).mult(p0.get(2)));

    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("surfaceAb/surfaceAb", surfaceAb);
    }

    public StructureMatrix<ParametricSurface> getSurfaceAb() {
        return surfaceAb;
    }

    public void setSurfaceAb(StructureMatrix<ParametricSurface> surfaceAb) {
        this.surfaceAb = surfaceAb;
    }
}
