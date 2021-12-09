package one.empty3.tests;

import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.RepresentableConteneur;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.tribase.Tubulaire3;

public class RoueVoiture extends RepresentableConteneur {

    public RoueVoiture(Voiture voiture) {

        FctXY fctXY = new FctXY();
        fctXY.setFormulaX("" + voiture.getRayonRoue());


        Tubulaire3 t = new Tubulaire3();
        t.texture(new ColorTexture(Colors.random()));
        t.getDiameterFunction().setElem(fctXY);
        t.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(-voiture.getEspacementRoues()/2, voiture.getRayonRoue(), voiture.getLargeur() - voiture.getEpaisseurRoue()),0);
        t.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(-voiture.getEspacementRoues()/2, voiture.getRayonRoue(), voiture.getLargeur()),1);

        add(t);


        Tubulaire3 t2 = new Tubulaire3();
        t2.texture(new ColorTexture(Colors.random()));
        t2.getDiameterFunction().setElem(fctXY);
        t2.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(-voiture.getEspacementRoues()/2, voiture.getRayonRoue(), -voiture.getLargeur() + voiture.getEpaisseurRoue()),0);
        t2.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(-voiture.getEspacementRoues()/2, voiture.getRayonRoue(), -voiture.getLargeur()),1);
        add(t2);


        Tubulaire3 t3 = new Tubulaire3();
        t3.texture(new ColorTexture(Colors.random()));
        t3.getDiameterFunction().setElem(fctXY);
        t3.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(voiture.getEspacementRoues()/2, + voiture.getRayonRoue(), voiture.getLargeur() - voiture.getEpaisseurRoue()),0);
        t3.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(voiture.getEspacementRoues()/2, + voiture.getRayonRoue(), voiture.getLargeur()),1);

        add(t3);


        Tubulaire3 t4 = new Tubulaire3();
        t4.texture(new ColorTexture(Colors.random()));
        t4.getDiameterFunction().setElem(fctXY);
        t4.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(voiture.getEspacementRoues()/2, + voiture.getRayonRoue(), -voiture.getLargeur() + voiture.getEpaisseurRoue()),0);
        t4.getSoulCurve().getElem().getCoefficients().setElem(new Point3D(voiture.getEspacementRoues()/2, + voiture.getRayonRoue(), -voiture.getLargeur()), 1);
        add(t4);
    }
}
