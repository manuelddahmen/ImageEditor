package one.empty3.library.objloader;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricSurface;

public class PolygonalSurface extends ParametricSurface {
    private StructureMatrix<Point3D> controls = new StructureMatrix<Point3D>();

    public PolygonalSurface(StructureMatrix<Point3D> s) {
        this.controls = s;
    }


    public StructureMatrix<Point3D> getControls() {
        return controls;
    }

    public void setControls(StructureMatrix<Point3D> controls) {
        this.controls = controls;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("controls/Points de contrôles (délimatiation des polygônes)", controls);
    }


}
