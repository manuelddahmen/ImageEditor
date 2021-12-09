package one.empty3.tests;

import one.empty3.library.*;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.ExtrusionCurveCurve;
import one.empty3.library.core.tribase.TubulaireN2;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Human extends RepresentableConteneur {

    Animation animation;
    protected RepresentableConteneur actualBody = new RepresentableConteneur();
    protected Membre troncHaut;
    protected Membre troncBas;
    protected Membre tronc;
    protected Membre jambeHautGauche;
    protected Membre jambeHautDroite;
    protected Membre jambeBasGauche;
    protected Membre jambeBasDroite;
    protected Membre piedGauche;
    protected Membre piedDroit;
    protected Membre brasHautGauche;
    protected Membre brasHautDroit;
    protected Membre brasBasGauche;
    protected Membre brasBasDroit;
    protected Membre sexe;
    protected Membre mainGauche;
    protected Membre mainDroite;
    protected Membre[] doigtsMainGauche;
    protected Membre[] doigtsMainDroite;
    protected Membre tete;
    private final double hauteurSommet = 2.0;
    private final double hauteurTete = 1.8;
    private final double hauteurTroncHaut = 1.4;
    private final double hauteurTroncBas = 1.05;
    private final double hauteurBassin = 0.9;
    private final double hauteurRotule = 0.5;
    private final double hauteurCheville = 0.1;
    private final double hauteurSol = 0.0;
    private final double largeurBassin = 0.3;
    private final double largeurTete = 0.4;
    private final double largeurEpaule = largeurBassin * 3 / 2;
    private final double rayonMembres = 0.25;
    private final double rayonMembresHaut = 0.15;

    private boolean man = true;

    public Human() {

    }

    public void init(boolean isMan) {

        troncBas = new Membre(new Sphere(new Axe(pos().plus(Point3D.Z.mult(hauteurTroncHaut).plus(Point3D.Z)),
                pos().plus(Point3D.Z.mult(hauteurTroncHaut).moins(Point3D.Z))),
                (largeurBassin) / 3.));

        TubulaireN2 tronc2 = new TubulaireN2(new LineSegment(pos().plus(Point3D.Z.mult(hauteurBassin).plus(Point3D.Y.mult(0))),
                pos().plus(Point3D.Z.mult(hauteurTete).plus(Point3D.Y.mult(0)))
        ), largeurBassin / 2);
        tronc = new Membre(tronc2);


        RepresentableConteneur t = new RepresentableConteneur();
        for (Axe axe : Arrays.asList(
                new Axe(pos().plus(new Point3D(0.0, largeurBassin * 2 / 3, hauteurTroncHaut)),
                        pos().plus(new Point3D(0.0, largeurBassin * 2 / 3, hauteurTroncBas))),
                new Axe(pos().plus(new Point3D(0.0,-largeurBassin * 2 / 3, hauteurTroncHaut)),
                        pos().plus(new Point3D(0.0, -largeurBassin * 2 / 3, hauteurTroncBas))))) {

            Sphere sphere = new Sphere(axe,
                    largeurBassin * 2 / 3);

            sphere.texture(new ColorTexture(Colors.random()));


            t.getListRepresentable().add(sphere);
        }

        troncHaut = new Membre(t);


        TubulaireN2 bhg = new TubulaireN2(new LineSegment(
                pos().plus(new Point3D(0.0, largeurEpaule, hauteurTroncHaut)),
                        pos().plus(new Point3D(0.0, largeurEpaule, hauteurTroncBas))), rayonMembresHaut);
        TubulaireN2 bhd = new TubulaireN2(new LineSegment(
                pos().plus(new Point3D(0.0, -largeurEpaule, hauteurTroncHaut)),
                        pos().plus(new Point3D(0.0, -largeurEpaule, hauteurTroncBas))), rayonMembresHaut);
        brasHautGauche = new Membre(bhg);
        brasHautDroit = new Membre(bhd);


        TubulaireN2 bbg = new TubulaireN2(new LineSegment(pos().plus(new Point3D(0.0, largeurEpaule, hauteurTroncBas)),
                pos().plus(new Point3D(0.0, largeurEpaule, hauteurBassin))), rayonMembresHaut);
        TubulaireN2 bbd = new TubulaireN2(new LineSegment(
                pos().plus(new Point3D(0.0, -largeurEpaule, hauteurTroncBas)),
                pos().plus(new Point3D(0.0, -largeurEpaule, hauteurBassin))), rayonMembresHaut);
        brasBasGauche = new Membre(bbg);
        brasBasDroit = new Membre(bbd);


        TubulaireN2 jhgT = new TubulaireN2(new LineSegment(pos().plus(Point3D.Z.mult(hauteurRotule).plus(Point3D.Y.mult(largeurBassin))),
                pos().plus(Point3D.Z.mult(hauteurBassin).plus(Point3D.Y.mult(largeurBassin)))
        ), rayonMembres);
        TubulaireN2 jhdT = new TubulaireN2(new LineSegment(pos().plus(Point3D.Z.mult(hauteurRotule).plus(Point3D.Y.mult(-largeurBassin))),
                pos().plus(Point3D.Z.mult(hauteurBassin).plus(Point3D.Y.mult(-largeurBassin)))
        ), rayonMembres);
        jambeHautGauche = new Membre(jhgT);
        jambeHautDroite = new Membre(jhdT);
        TubulaireN2 jambeBasG = new TubulaireN2(new LineSegment(pos().plus(Point3D.Z.mult(hauteurCheville).plus(Point3D.Y.mult(largeurBassin))),
                pos().plus(Point3D.Z.mult(hauteurRotule).plus(Point3D.Y.mult(largeurBassin)))
        ), rayonMembres);
        TubulaireN2 jambeBasD = new TubulaireN2(new LineSegment(pos().plus(Point3D.Z.mult(hauteurCheville).plus(Point3D.Y.mult(-largeurBassin))),
                pos().plus(Point3D.Z.mult(hauteurRotule).plus(Point3D.Y.mult(-largeurBassin)))
        ), rayonMembres);
        jambeBasGauche = new Membre(jambeBasG);
        jambeBasDroite = new Membre(jambeBasD);


        TubulaireN2 piedG = new TubulaireN2(new LineSegment(pos().plus(Point3D.Z.mult(hauteurCheville).plus(Point3D.Y.mult(largeurBassin))),
                pos().plus(Point3D.Z.mult(hauteurSol).plus(Point3D.Y.mult(largeurBassin)).plus(Point3D.X.mult(0.2)))), rayonMembres);
        TubulaireN2 piedD = new TubulaireN2(new LineSegment(pos().plus(Point3D.Z.mult(hauteurCheville).plus(Point3D.Y.mult(-largeurBassin))),
                pos().plus(Point3D.Z.mult(hauteurSol).plus(Point3D.Y.mult(-largeurBassin)).plus(Point3D.X.mult(0.2)))), rayonMembres);
        piedGauche = new Membre(piedG);
        piedDroit = new Membre(piedD);

        // Tete
        tete = new Membre(new Sphere(new Axe(pos().plus(Point3D.Z.mult(hauteurSommet)),
                pos().plus(Point3D.Z.mult(hauteurTete))),largeurTete));
        // Sexe
        this.man = isMan;

        addSexe();
    }

    private Point3D pos() {
        return Point3D.O0;
    }

    public synchronized void update() {
        for (Membre membre : Arrays.asList(tronc, troncBas, troncHaut,
                jambeBasDroite, jambeBasGauche, jambeHautDroite, jambeHautGauche,
                brasBasDroit, brasBasGauche, brasHautGauche, brasHautDroit,
                tete,
                piedGauche, piedDroit,
                sexe)) {
            actualBody.add(membre.getRepresentable());
        }
        add(actualBody);

        actualBody.getListRepresentable().forEach(new Consumer<Representable>() {
            @Override
            public void accept(Representable representable) {
                representable.texture(new ColorTexture(Color.BLACK));
            }
        });

    }


    public void addSexe() {
        man = (int) (Math.random() * 2)==0;

        if(man) {
            TubulaireN2 sexe1 = new TubulaireN2(new LineSegment(
                    new Point3D(0.2, 0.0, hauteurTroncBas),
                    new Point3D(0.3, 0.0, hauteurBassin)

            ), 0.13);
            Sphere [] spheres = new Sphere[] {
                    new Sphere(new Axe(
                            new Point3D( 1.0,largeurBassin/5*1, (hauteurBassin+hauteurTroncBas)/2),
                            new Point3D( -1.0,largeurBassin/5*1, (hauteurBassin+hauteurTroncBas)/2)
                        ), 0.13),

                    new Sphere(new Axe(
                            new Point3D(1.0,- largeurBassin/5*1, (hauteurBassin+hauteurTroncBas)/2),
                            new Point3D(-1.0,- largeurBassin/5*1, (hauteurBassin+hauteurTroncBas)/2)
                    ), 0.13)
            };
            RepresentableConteneur rc = new RepresentableConteneur();
            rc.add(sexe1);
            rc.add(spheres[0]);
            rc.add(spheres[1]);

            sexe = new Membre(rc);
        } else {
            ExtrusionCurveCurve extrusionCurveCurve = new ExtrusionCurveCurve();
            extrusionCurveCurve.getBase().setElem(new Circle(new Axe(

                            new Point3D(0.0, largeurBassin/8, (hauteurBassin+hauteurTroncBas)/2).plus(Point3D.Z),
                            new Point3D(0.0, largeurBassin/8, (hauteurBassin+hauteurTroncBas)/2).moins(Point3D.Z)
                    ), 0.4));

            List<Point3D> point3DS = Arrays.asList(new Point3D(0.0, -largeurBassin / 5 * 1, (hauteurBassin)),
                    new Point3D(0.0, largeurBassin / 5 * 1, (hauteurBassin)),
                    new Point3D(0.0, largeurBassin / 5 * 1, (hauteurBassin + hauteurTroncBas) / 2),
                    new Point3D(0.0, -largeurBassin / 5 * 1, (hauteurBassin + hauteurTroncBas) / 2));

            CourbeParametriquePolynomialeBezier c;
            extrusionCurveCurve.getPath().setElem(c = new CourbeParametriquePolynomialeBezier());
            c.getCoefficients().getData1d().addAll(point3DS);
            sexe = new Membre(extrusionCurveCurve);
        }
    }
    public void walking() {
        double distancePas2 = 1.7;
        MoveCollection moveCollection = new MoveCollection(0.0, 3.0);
        Move teteBas = new Move("humanWalks1", tete.getRepresentable(), "axe,origine",
                0.0, 3.0, new Point3D(distancePas2,  0.0, 0.0));
        Move teteHaut = new Move("humanWalks2", tete.getRepresentable(), "axe,extremite",
                0.0, 3.0, new Point3D(distancePas2,  0.0, 0.0));
        Move piedGaucheWalk0 = new Move("humanWalks3", piedGauche.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move piedDroiteWalk0 = new Move("humanWalks4", piedDroit.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move piedGaucheWalk1 = new Move("humanWalks5", piedGauche.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(0.0,0.0, distancePas2));
        Move piedDroiteWalk1 = new Move("humanWalks6", piedDroit.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5, new Point3D(0.0,  0.0, distancePas2));
        Move jbg1 = new Move("humanWalks7", jambeBasGauche.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move jbg2 = new Move("humanWalks8", jambeBasGauche.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move jbd1 = new Move("humanWalks9", jambeBasDroite.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move jbd2 = new Move("humanWalks10", jambeBasDroite.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move jhg1 = new Move("humanWalks11", jambeHautGauche.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move jhg2 = new Move("humanWalks12", jambeHautGauche.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move jhd1 = new Move("humanWalks13", jambeHautDroite.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move jhd2 = new Move("humanWalks14", jambeHautDroite.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5, new Point3D(distancePas2,  0.0, 0.0));
        Move bhg1 = new Move("humanWalks15", brasHautGauche.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(0., 0., distancePas2));
        Move bhg2 = new Move("humanWalks16", brasHautGauche.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5,new Point3D(0., 0.0,distancePas2));
        Move bhd1 = new Move("humanWalks17", brasHautDroit.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5,new Point3D(0., 0.0,distancePas2));
        Move bhd2 = new Move("humanWalks18", brasHautDroit.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5,new Point3D(0., 0.0,distancePas2));
        Move bbg1 = new Move("humanWalks19", brasBasGauche.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5, new Point3D(0., 0.0,distancePas2));
        Move bbg2 = new Move("humanWalks20", brasBasDroit.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5,new Point3D(0., 0.0,distancePas2));
        Move bbd1 = new Move("humanWalks21", brasBasGauche.getRepresentable(), "soulCurve:0,origine:0",
                0.0, 1.5,new Point3D(0., 0.0,distancePas2));
        Move bbd2 = new Move("humanWalks22", brasBasDroit.getRepresentable(), "soulCurve:0,origine:1",
                0.0, 1.5,new Point3D(0., 0.0,distancePas2));

        moveCollection.addAll("humanWalks", 0.0, 3.0, teteBas, teteHaut, piedDroiteWalk0, piedDroiteWalk1, piedGaucheWalk0, piedGaucheWalk1, jbd1, jbd2, jbg1, jbg2,
                jhd1, jhd2, jhg1, jhg2, bhg1, bhd2, bbg1, bbd1, bbd2, bbg2, bhd1, bhg2);
        tete.getRepresentable().texture(new ColorTexture(Color.BLACK));

        animation = new Animation(this.getClass(), moveCollection);
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        StructureMatrix<Representable> structureMatrix = new StructureMatrix<>(0, Representable.class);
        structureMatrix.setElem(tete.getRepresentable());
        getDeclaredDataStructure().put("tete/Tête", structureMatrix);
        structureMatrix = new StructureMatrix<>(0, Representable.class);
        structureMatrix.setElem(piedGauche.getRepresentable());
        getDeclaredDataStructure().put("piedGauche/Pied gauche", structureMatrix);
        structureMatrix = new StructureMatrix<>(0, Representable.class);
        structureMatrix.setElem(piedDroit.getRepresentable());
        getDeclaredDataStructure().put("piedDroit/Pied droit", structureMatrix);
    }

    public void move(double frame, double fps) {
        animation.anime(this, frame/fps);
    }
}
