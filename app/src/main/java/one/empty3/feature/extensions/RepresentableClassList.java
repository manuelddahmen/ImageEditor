/*
 *
 *  *  This file is part of Empty3.
 *  *
 *  *     Empty3 is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 2 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     Empty3 is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 *
 *
 */

package one.empty3.feature.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import one.empty3.library.Circle;
import one.empty3.library.HeightMapSurface;
import one.empty3.library.LineSegment;
import one.empty3.library.LumierePonctuelle;
import one.empty3.library.Point3D;
import one.empty3.library.PolyLine;
import one.empty3.library.Polygon;
import one.empty3.library.RepresentableConteneur;
import one.empty3.library.Sphere;
import one.empty3.library.TRI;
import one.empty3.library.core.nurbs.BSpline;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.ExtrusionB1B1;
import one.empty3.library.core.nurbs.ExtrusionCurveCurve;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.nurbs.FunctionCurve;
import one.empty3.library.core.nurbs.FunctionSurface;
import one.empty3.library.core.nurbs.SurfaceParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.TourRevolution;
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.raytracer.tree.AlgebricTree;
import one.empty3.library.core.raytracer.tree.TreeNodeEvalException;
import one.empty3.library.core.script.InterpreteException;
import one.empty3.library.core.script.InterpretePoint3D;
import one.empty3.library.core.tribase.TRIEllipsoide;
import one.empty3.library.core.tribase.Tubulaire3;

/**
 * Created by manue on 27-06-19.
 */
public class RepresentableClassList {
    private static TreeMap scenes;
    private static boolean isInitScenes;
    static MyObservableList<ObjectDescription>
            listClasses = new MyObservableList<>();
    public static ArrayList containers;
    public static ArrayList surfaces;
    public static ArrayList points;
    public static ArrayList curves;

    public static void add(String name, Class clazz) {
        ObjectDescription objectDescription = new ObjectDescription();
        objectDescription.setName(name);
        objectDescription.setR(clazz);
        listClasses.add(objectDescription);
    }

    public static MyObservableList<ObjectDescription> myList(Object object) {
        return null;
    }

    public static MyObservableList<ObjectDescription> myList() {

        points = new ArrayList();
        curves = new ArrayList();
        surfaces = new ArrayList();
        containers = new ArrayList();
        listClasses = new MyObservableList<>();


        add("point", Point3D.class);
        points.add(Point3D.class);
        add("container (group)", RepresentableConteneur.class);
        containers.add(RepresentableConteneur.class);
        add("line", LineSegment.class);
        curves.add(LineSegment.class);
        add("bezier", CourbeParametriquePolynomialeBezier.class);
        curves.add(CourbeParametriquePolynomialeBezier.class);
        add("bezier2", SurfaceParametriquePolynomialeBezier.class);
        surfaces.add(SurfaceParametriquePolynomialeBezier.class);
        add("triangle", TRI.class);
        surfaces.add(TRI.class);
        add("polygon", Polygon.class);
        surfaces.add(Polygon.class);
        add("polyline", PolyLine.class);
        curves.add(PolyLine.class);
        add("sphere", Sphere.class);
        surfaces.add(Sphere.class);
        add("tube", Tubulaire3.class);
        surfaces.add(Tubulaire3.class);
        add("surface (P = f(u,v))", FunctionSurface.class);
        surfaces.add(FunctionSurface.class);
        add("curve   (P = f(u))", FunctionCurve.class);
        curves.add(FunctionCurve.class);
        add("ellipsoid", TRIEllipsoide.class);
        surfaces.add(TRIEllipsoide.class);
        add("fct y = f(x)", FctXY.class);
        curves.add(FctXY.class);
//        add("heightSurfaceXYZ", HeightMapSurfaceXYZ.class);
        add("B-Spline", BSpline.class);
        curves.add(BSpline.class);
        add("LumierePonctuelle", LumierePonctuelle.class);

//        add("extrusion", TRIExtrusionGeneralisee.class);
        add("circle", Circle.class);
        surfaces.add(Circle.class);
        add("extrusion2+", ExtrusionB1B1.class);///???
        surfaces.add(ExtrusionB1B1.class);
        surfaces.add(ExtrusionCurveCurve.class);
        add("carte de niveaux, surface déformable", HeightMapSurface.class);///???
        surfaces.add(HeightMapSurface.class);
        add("tour de revolution", TourRevolution.class);
        surfaces.add(TourRevolution.class);
        // courbe et surface par défaut à ajouter dans un objet.
        //add("move", Move.class);
        //add("paramCurve", ParametricCurve.class);
        //add("paramSurface", ParametricSurface.class);

        return listClasses;
    }

    public static Point3D pointParse(String x, String y, String z) throws AlgebraicFormulaSyntaxException, TreeNodeEvalException {
        Map<String, Double> hashMap = new HashMap<String, Double>();

        AlgebricTree treeX = new AlgebricTree(x, hashMap);
        treeX.construct();
        AlgebricTree treeY = new AlgebricTree(y, hashMap);
        treeY.construct();
        AlgebricTree treeZ = new AlgebricTree(z, hashMap);
        treeZ.construct();

        Point3D point3D = new Point3D((Double) treeX.eval(), (Double) treeY.eval(), (Double) treeZ.eval());

        return point3D;
    }


    public static Point3D pointParse(String toStringRepresentation) throws InterpreteException {
        InterpretePoint3D interpretePoint3D = new InterpretePoint3D();
        return (Point3D) interpretePoint3D.interprete(toStringRepresentation, 0);
    }
}
