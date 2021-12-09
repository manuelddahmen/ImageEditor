package one.empty3.tests;

import one.empty3.library.Polygon;
import one.empty3.library.*;
import one.empty3.library.core.testing.TestObjet;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.core.tribase.TubulaireN2;

import java.awt.*;

public class TestHuman extends TestObjetSub {

    private Human humanModel;

    public static void main(String[] args) {
        TestHuman testHumanModel = new TestHuman();
        testHumanModel.setPublish(true);
        testHumanModel.setGenerate(testHumanModel.getGenerate() | TestObjet.GENERATE_MODEL);
        testHumanModel.setMaxFrames(100);

        testHumanModel.setDimension(TestObjet.VGA);
        new Thread(testHumanModel).start();
    }

    @Override
    public void ginit() {
        scene().clear();

        //z().setDisplayType(ZBufferImpl.DISPLAY_ALL);


        humanModel = new Human();

        humanModel.init(frame() == 1);

        humanModel.update();

        humanModel.walking();

        Polygon polygon = new Polygon();
        polygon.getPoints().add(new Point3D(-10., -10., 0.));
        polygon.getPoints().add(new Point3D(10., -10., 0.));
        polygon.getPoints().add(new Point3D(10., 10., 0.));
        polygon.getPoints().add(new Point3D(-10., 10., 0.));

        polygon.texture(new ColorTexture(Color.GRAY));

        scene().add(humanModel);
        Camera c = new Camera(new Point3D(-5.0, 1.0, 1.0), new Point3D(0., 0.0, 0.0), new Point3D(0.0, 0.0, 1.0));
        c.setMatrice(c.getMatrice());
        scene().cameraActive(c);

        //humanModel.add(polygon);
    }

    @Override
    public void finit() {

        if (humanModel.animation != null) {
            humanModel.move(frame(), 25.);
        } else
            System.err.println("Human animation == null");

        System.out.println(((TubulaireN2) humanModel.jambeHautGauche.getRepresentable()).getSoulCurve().getElem()
                .calculerPoint3D(0.0));
        System.out.println(((TubulaireN2) humanModel.jambeHautGauche.getRepresentable()).getSoulCurve().getElem()
                .calculerPoint3D(1.0));
    }

}
