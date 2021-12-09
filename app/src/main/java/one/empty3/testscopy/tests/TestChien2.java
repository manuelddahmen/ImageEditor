package one.empty3.testscopy.tests;

import one.empty3.library.*;
import one.empty3.library.core.move.Trajectoires;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.core.tribase.Tubulaire3;

import java.awt.*;

public class TestChien2 extends TestObjetSub {

    public void ginit() {
        z.setDisplayType(ZBufferImpl.SURFACE_DISPLAY_COL_TRI);
     //   scene().cameraActive().eye().changeTo(new Point3D(16., 0., 0.));
       Tubulaire3 [] patte = new Tubulaire3[4];
      // ts[0] =
//Artifact : empty3-library-3d-gui ...
       Point3D tete = new Point3D(0.,0.,0. ); //tête 
       Point3D queue = new Point3D(1.,0.,1.); // queue
       Sphere tetes = new Sphere(tete, 0.4); //sphère 
tetes.texture(new TextureCol(Color.RED));
queue.texture(new TextureCol(Color.BLACK));

/*Parallelepiped corps = new Parallelepiped(tete,
   new Point3D(0.,0.,0.5), 
   new Point3D( 1.,0.,0.5),
   new Point3D(0.,0.,0.5 ),
            new TextureCol(Color.BLUE)
                   );*///parallel polyèdres largeur y 0.5
   for(int i=0;i<4; i++) {
       patte[i] = new Tubulaire3();
       patte[i].texture(new TextureCol(Color.ORANGE));
      ((FctXY)( patte[i].getDiameterFunction().getElem())).setFormulaX("0.6");
   }
Tubulaire3 corp;
  corp = new Tubulaire3();
       corp.texture(new TextureCol(Color.ORANGE));
        ((FctXY)( corp.getDiameterFunction().getElem())).setFormulaX("0.6");
       ((CourbeParametriquePolynomialeBezier)(patte[0].getSoulCurve().getElem())). getCoefficients().setElem(new Point3D(0.,0.25,0.), 0);
       ((CourbeParametriquePolynomialeBezier)(patte[0].getSoulCurve().getElem())).getCoefficients().setElem( new Point3D(0.,0.25,1.), 1); //patte avant
        ((CourbeParametriquePolynomialeBezier)(patte[0].getSoulCurve().getElem())).getCoefficients().setElem( new Point3D(0.,-0.25,1.), 2);
        ((CourbeParametriquePolynomialeBezier)(patte[0].getSoulCurve().getElem())).getCoefficients().setElem( new Point3D(0.,-0.25,0.), 3);
// patte avant #2
((CourbeParametriquePolynomialeBezier)(patte[2].getSoulCurve().getElem())).getCoefficients().setElem( new Point3D(1.,0.25,0.), 0);
((CourbeParametriquePolynomialeBezier)(patte[2].getSoulCurve().getElem())).getCoefficients().setElem( new Point3D(1.,0.25,1.), 1); //patte arrière #1 
((CourbeParametriquePolynomialeBezier)(patte[2].getSoulCurve().getElem())).getCoefficients().setElem( new Point3D(1.,-0.25,1.), 2);// patte avant #2
((CourbeParametriquePolynomialeBezier)(patte[2].getSoulCurve().getElem())).getCoefficients().setElem(new Point3D(1.,-0.25,0.), 3);
        //1,0,0 //etx queue.
    
        //scene().add(corps);
        scene().add(tetes);
        for(int i=0;i<4; i+=2) {
            scene().add(patte[i]);

        } 
//        scene().cameraActive().getEye().setZ(-10.)

         scene().lumieres().add(new LumiereIdent()) ;
   }
    public void finit() {
        Point3D sphere = Trajectoires.sphere(/*Point3D.O0, Point3D.Z, Point3D.X,*/
          1.0 * frame() / getMaxFrames(),
                0.0, 16.0);
        Point3D circlePoint = P.n(Math.cos(1.0 * frame() / getMaxFrames()/10.),
            Math.sin(1.0 * frame() / getMaxFrames()/10.), 
            Math.cos(1.0*frame()/getMaxFrames())).mult(12.);
        //scene().cameras().clear();
        Camera c = new Camera(circlePoint, Point3D.O0, Point3D.Y);
        //c.setMatrix(c.getMatrice().tild());
        c.declareProperties();
        scene().cameraActive(c);
    }
}
