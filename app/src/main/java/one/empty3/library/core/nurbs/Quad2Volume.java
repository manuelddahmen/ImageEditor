package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.StructureMatrix;

public class Quad2Volume extends ParametricVolume {
    private StructureMatrix<Polygon> polygons = new StructureMatrix<>(1, Polygon.class);

    /***
     *
     * @param p0 point3D coordonnées du point
     * @return point de l'espace transformé
     */
    @Override
    public Point3D calculerPoint3D(Point3D p0) {
        double u = p0.get(0);
        double v = p0.get(1);
        double w = p0.get(2);

        Polygon polygon1 = polygons.getData1d().get(0);
        Polygon polygon2 = polygons.getData1d().get(1);

        Point3D pp1 = polygon1.getPoints().getElem(0);
        Point3D pp2 = polygon1.getPoints().getElem(1);
        Point3D pp3 = polygon1.getPoints().getElem(2);
        Point3D pp4 = polygon1.getPoints().getElem(3);

        Point3D p21 = polygon2.getPoints().getElem(0);
        Point3D p22 = polygon2.getPoints().getElem(1);
        Point3D p23 = polygon2.getPoints().getElem(2);
        Point3D p24 = polygon2.getPoints().getElem(3);


        Point3D pElevation1 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(u));
        Point3D pElevation2 = pp4.plus(pp4.mult(-1d).plus(pp3).mult(u));

        Point3D pFinal = (pElevation1.plus(pElevation1.mult(-1d).plus(pElevation2).mult(v)));


        Point3D pElevation21 = p21.plus(p21.mult(-1d).plus(p22).mult(u));
        Point3D pElevation22 = p24.plus(p24.mult(-1d).plus(p23).mult(u));

        Point3D pFinal2 = (pElevation1.plus(pElevation21.mult(-1d).plus(pElevation22).mult(v)));

        Point3D p = pFinal.plus(pFinal2.moins(pFinal).mult(w));

        p.texture(texture);
        p.setNormale(pFinal2.moins(pFinal));


        return p;
    }


    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("polygons/polygons", polygons);
    }

    public StructureMatrix<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(StructureMatrix<Polygon> polygons) {
        this.polygons = polygons;
    }
}
