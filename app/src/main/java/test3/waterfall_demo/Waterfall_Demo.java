/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

package test3.waterfall_demo;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.testing.TestObjetSub;

import java.util.ArrayList;
import java.util.List;

/*__
 * Created by manue on 02-02-20.
 *
 *
 * Idées : XTRIX
 * 1) Tetris : les courbes bougent but remplir l'écran
 * 2) Des gouttes ou des blocs tombent le long des courbes, remplir des bacs genre casse-brique
 * 3) Niveaux ? Règles de jeux différentes ?
 *
 */
public class Waterfall_Demo extends TestObjetSub {
    public int CURVE_VERTICAL  = 0;
    public int CURVE_RANDOM  = 1;
    public boolean OPTION_CURVE_CUT = true;

    int nCurves = 5;
    int nodeMeanY = 5;
    private StructureMatrix<ParametricCurve> curves = new StructureMatrix<>(1, ParametricCurve.class);
    private StructureMatrix<Double> ts = new StructureMatrix<>(1, Double.class);
    private Double vIncrFrame = 0.01;
    private RepresentableConteneur blocks = new RepresentableConteneur();
    private int curve_shape = 0;
    private boolean option_curve_cut;
    private int nPointsMax = 20;

    public void ginit() {
        int[] height = new int[10];
        for (int curveN = 0; curveN < nCurves; curveN++) {

            List<Point3D> pointsCurveN = new ArrayList<Point3D>();

            pointsCurveN.add(new Point3D(0.,0.,0.));

            pStart(pointsCurveN.get(0));

            int pointN = 1;

            boolean TRUE = true;


            Point3D pointNcandidate = (Point3D) pointsCurveN.get(0).clone();

            while (candidate(pointNcandidate, pointsCurveN, pointN)) {
                    // TODO MAKE IT WORK pointsCurveN.add((Point3D) pointNcandidate.copy());
                pointsCurveN.add(pointNcandidate);
                pointNcandidate = (Point3D) pointNcandidate.clone();
                pointN++;
            }

            //pointsCurveN.add(new Point3D(0., (double) getDimension().y(), 0.));

            Point3D[] c = new Point3D[pointN];

            curves.setElem(new CourbeParametriquePolynomialeBezier(pointsCurveN.toArray(c)), curveN);


            int mlc = 10;
            blocks.add(new Cube(mlc, Point3D.O0));


            ts.setElem(0., curveN);
        }

        curves.data1d.forEach(parametricCurve -> scene().add(parametricCurve));
        scene().add(blocks);

        scene().cameraActive().setEye(new Point3D(0., 0., -Math.max((double) getDimension().x(), (double) getDimension().y()) * 2));
    }

    public void finit() {
        for(int i=0; i<ts.data1d.size(); i++) {
            ts.data1d.set(i, ts.data1d.get(i) + vIncrFrame );
            if(ts.getElem(i)>1.)
                ts.setElem(0., i);
        }
        for(int i=0; i<blocks.getListRepresentable().size(); i++) {
            Representable representable =blocks.getListRepresentable().get(i);
            if(representable instanceof Cube) {
               ((Cube) representable).setPosition(curves.getElem(i).calculerPoint3D(
                      ts.getElem(i)));
                              ((Cube) representable).generate();
               }
            }

    }

    public void testScene() {
        for(int i = 0; i<blocks.getListRepresentable().size(); i++) {
          //  ((Cube)(blocks.getListRepresentable().get(i))).setPosition(
           //         curves.getData1d().get(i).calculerPoint3D(ts.data1d.get(i)));
        }

    }

    public static void main(String[] args) {
        Waterfall_Demo waterfall_demo0 = new Waterfall_Demo();
        waterfall_demo0.setMaxFrames(1*60*25);
        waterfall_demo0.setCurve_shape(waterfall_demo0.CURVE_VERTICAL);
        waterfall_demo0.setOption_curve_cut(!waterfall_demo0.OPTION_CURVE_CUT);
        waterfall_demo0.setName("waterfall_demos/0");
        new Thread(waterfall_demo0).start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Waterfall_Demo waterfall_demo1 = new Waterfall_Demo();
        waterfall_demo1.setMaxFrames(1*60*25);
        waterfall_demo1.setCurve_shape(waterfall_demo0.CURVE_VERTICAL);
        waterfall_demo1.setOption_curve_cut(waterfall_demo0.OPTION_CURVE_CUT);
        waterfall_demo1.setName("waterfall_demos/1");
        new Thread(waterfall_demo1).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Waterfall_Demo waterfall_demo2 = new Waterfall_Demo();
        waterfall_demo2.setMaxFrames(1*60*25);
        waterfall_demo2.setCurve_shape(waterfall_demo0.CURVE_RANDOM);
        waterfall_demo2.setName("waterfall_demos/2");
        new Thread(waterfall_demo2).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Waterfall_Demo waterfall_demo3 = new Waterfall_Demo();
        waterfall_demo3.setMaxFrames(1*60*25);
        waterfall_demo3.setCurve_shape(waterfall_demo0.CURVE_RANDOM);
        waterfall_demo3.setOption_curve_cut(!waterfall_demo0.OPTION_CURVE_CUT);
        waterfall_demo3.setName("waterfall_demos/3");
        new Thread(waterfall_demo3).start();

    }

    public boolean candidate(Point3D p, List<Point3D> pointsCurveN, int pointN)
    {
        if(pointsCurveN.size()>=nPointsMax)
            return false;
        Point3D pNext = null;
        switch (curve_shape)
        {
            case 1:

                pNext = pointsCurveN.get(pointN - 1).plus(new Point3D((Math.random() - 0.5) * getDimension().x() / nCurves,
                        (Math.random() - 0.5) * getDimension().y() / nodeMeanY, 0.));
                if(option_curve_cut== OPTION_CURVE_CUT &&!(pNext.getX() >= -this.getDimension().x() && pNext.getX() < this.getDimension().x()&&
                        (pNext.getY() >= -this.getDimension().y() && pNext.getY() < this.getDimension().y())))
                    return false;
                while(!(pNext.getX() >= -this.getDimension().x() && pNext.getX() < this.getDimension().x()&&
                        (pNext.getY() >= -this.getDimension().y() && pNext.getY() < this.getDimension().y())))
                    pNext = pointsCurveN.get(pointN - 1).plus(new Point3D((Math.random() - 0.5) * getDimension().x() / nCurves,
                            (Math.random() - 0.5) * getDimension().y() / nodeMeanY, 0.));
                break;
            case 0:
                pNext = pointsCurveN.get(pointN - 1).plus(new Point3D((Math.random() - 0.5) * getDimension().x() / nCurves,
                        Math.random() * getDimension().y() / nodeMeanY, 0.));
                if(option_curve_cut== OPTION_CURVE_CUT) {
                    if(!(pNext.getX() >= -this.getDimension().x() && pNext.getX() < this.getDimension().x()&&
                        (pNext.getY() >= -this.getDimension().y() && pNext.getY() < this.getDimension().y())))
                            return false;
                }
                if(p.getY()>=getDimension().y())
                    pNext = new Point3D(pointsCurveN.get(pointN - 1).getX(),
                            (double)getDimension().y(), 0.);
                else
                    pNext = pointsCurveN.get(pointN - 1).plus(new Point3D((Math.random() - 0.5) * getDimension().x() / nCurves,
                        Math.random() * getDimension().y() / nodeMeanY, 0.));


                break;
            default:
                break;
        }
        if(pNext==null)
            return false;
        p.changeTo(pNext);
        if(p.getCoordArr().data1d.size()!=3)
            throw new IndexOutOfBoundsException("pNext coordArr size != 3");
        return true;
    }
    public void pStart(Point3D p)
    {
        Point3D pNext;
        switch (curve_shape)
        {
            case 1:
                pNext = new Point3D((Math.random() - 0.5) * getDimension().x(),
                        (Math.random() - 0.5) * getDimension().y(), 0.);
                break;
            case 0:
                pNext = new Point3D(
                        (Math.random() - 0.5) * getDimension().x() * 2,
                        -0. + -getDimension().y(), 0.);
                break;
            default:
                pNext = new Point3D();
                break;
        }
        p.changeTo(pNext);
    }

    public int getCurve_shape() {
        return curve_shape;
    }

    public void setCurve_shape(int curve_shape) {
        this.curve_shape = curve_shape;
    }

    public boolean isOption_curve_cut() {
        return option_curve_cut;
    }

    public void setOption_curve_cut(boolean option_curve_cut) {
        this.option_curve_cut = option_curve_cut;
    }
}
