package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.ParametricVolume;
import one.empty3.library.core.nurbs.Quad2Volume;

import java.awt.*;

public class Camera2Quad extends Camera {
    protected final StructureMatrix<Polygon> near = new StructureMatrix(0, Polygon.class);
    protected final StructureMatrix<Polygon>  far = new StructureMatrix(0, Polygon.class);
    protected ZBuffer z;


    public Camera2Quad(ZBuffer z, Polygon near, Polygon far) {
        this.near.setElem(near);
        this.far.setElem(far);
        this.z = z;

        init();
    }
    public void init() {
        //(pa1,nb1,pc1,pd1)->pa2,pb2,pc2,pd2
        Point3D pa1 = near.getElem().getPoints().getElem(0);
        Point3D pb1 = near.getElem().getPoints().getElem(1);
        Point3D pc1 = near.getElem().getPoints().getElem(2);
        Point3D pd1 = near.getElem().getPoints().getElem(3);
        Point3D pa2 = far.getElem().getPoints().getElem(0);
        Point3D pb2 = far.getElem().getPoints().getElem(1);
        Point3D pc2 = far.getElem().getPoints().getElem(2);
        Point3D pd2 = far.getElem().getPoints().getElem(3);

        Point3D ex1 = pb1.moins(pa1).norme1();
        Point3D ex2 = pc1.moins(pd1).norme1();
        Point3D ey1 = pc1.moins(pb1).norme1();
        Point3D ey2 = pd1.moins(pc1).norme1();
        Point3D ez1 = far.getElem().getIsocentre().moins(near.getElem().getIsocentre()).norme1();

        matrice.setElem(new Matrix33(new Point3D[]{ex1, ey1, ez1}).tild());

        setEye(near.getElem().getIsocentre());
        setLookat(far.getElem().getIsocentre());


    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("near/Near Polygon", near);
        getDeclaredDataStructure().put("far/Far polygon", far);
    }


    public void tracerQuad(Point3D pp1, Point3D pp2, Point3D pp3, Point3D pp4, ITexture texture, double u0, double u1,
                           double v0, double v1, ParametricSurface n) {


        Point p1, p2, p3, p4;
        p1 = coordonneesPoint2D(pp1, z);
        p2 = coordonneesPoint2D(pp2, z);
        p3 = coordonneesPoint2D(pp3, z);
        p4 = coordonneesPoint2D(pp4, z);
        if (!z.checkScreen(p1))
            return;
        if (!z.checkScreen(p2))
            return;
        if (!z.checkScreen(p3))
            return;
        if (!z.checkScreen(p4))
            return;
        int col = texture.getColorAt(u0, v0);

        if (p1 == null || p2 == null || p3 == null || p4 == null)
            return;
        TRI triBas = new TRI(pp1, pp2, pp3, texture);
        Point3D normale = triBas.normale();
        double inter = 1 / (z.maxDistance(p1, p2, p3, p4) + 1) / 3;
        for (double a = 0; a < 1.0; a += inter) {
            Point3D pElevation1 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            Point3D pElevation2 = pp4.plus(pp4.mult(-1d).plus(pp3).mult(a));
            for (double b = 0; b < 1.0; b += inter) {
                Point3D pFinal = (pElevation1.plus(pElevation1.mult(-1d).plus(pElevation2).mult(b)));
                pFinal.setNormale(normale);
                pFinal.texture(texture);
                if (n != null) {
                    if (z.getDisplayType()== DISPLAY_ALL) {
                        pFinal = n.calculerPoint3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b);
                        pFinal.setNormale(normale);
                        pFinal.texture(texture);
                    } else {
                        pFinal.setNormale(normale);
                        pFinal.texture(texture);

                    }
                }
                if (z.getDisplayType() <= SURFACE_DISPLAY_TEXT_QUADS) {
                    //Point3D point3D = n.calculerNormale3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b);
                    double u = u0 + (u1 - u0) * a;
                    double v = v0 + (v1 - v0) * b;
                    assert n != null;
                    z.testDeep(pFinal, n.texture(),
                            u, v, n);
                } else {
                    z.testDeep(pFinal, col);
                    //ime.testDeep(pFinal, texture.getColorAt(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b), n);

                }
            }
        }

    }

}
