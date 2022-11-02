package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

public class SurfaceParametriquePolynomiale extends ParametricSurface {

    protected StructureMatrix<Point3D> coefficients = new StructureMatrix<>(2, Point3D.class);
    ;
    protected StructureMatrix<Integer> power1 = new StructureMatrix<>(0, Integer.class);
    protected StructureMatrix<Integer> power2 = new StructureMatrix<>(0, Integer.class);
    ;


    public SurfaceParametriquePolynomiale(Point3D[][] coefficients) {
        this();
        this.coefficients.setAll(coefficients);
        power1.setElem(coefficients.length);
        power2.setElem(coefficients[0].length);
    }

    public SurfaceParametriquePolynomiale() {
        this.coefficients = new StructureMatrix<>(2, Point3D.class);
        this.coefficients.setAll(
                new Point3D[][]
                        {

                                {Point3D.n(2d, -2d, 0d), Point3D.n(2, -1, 0), Point3D.n(2, 0, 0), Point3D.n(2, 1, 0), Point3D.n(2, 2, 0)},
                                {Point3D.n(1d, -2d, 0d), Point3D.n(1d, -1d, 0d), Point3D.n(1, 0, 0), Point3D.n(1, 1, 0), Point3D.n(1, 2, 0)},
                                {Point3D.n(0d, -2d, 0d), Point3D.n(0d, -1d, 0d), Point3D.n(0, 0, 0), Point3D.n(0, 1, 0), Point3D.n(0, 2, 0)},
                                {Point3D.n(-1d, -2d, 0d), Point3D.n(-1d, -1d, 0d), Point3D.n(-1, 0, 0), Point3D.n(-1, 1, 0), Point3D.n(-1, 2, 0)},
                                {Point3D.n(-2d, -2d, 0d), Point3D.n(-2d, -1d, 0d), Point3D.n(-2, 0, 0), Point3D.n(-2, 1, 0), Point3D.n(-2, 2, 0)}

                        });
        power1.setElem(coefficients.getData2d().size());
        power2.setElem(coefficients.getData2d().get(0).size());
    }

    public void setCoefficients(StructureMatrix<Point3D> coefficients) {
        this.coefficients = coefficients;
    }

    public StructureMatrix<Point3D> getCoefficients() {
        return coefficients;
    }

}
