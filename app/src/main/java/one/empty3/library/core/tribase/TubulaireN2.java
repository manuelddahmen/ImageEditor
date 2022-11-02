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
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*__
 * *
 * Global license : * GNU GPL v3
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * Creation time 2015-03-25
 * <p>
 * *
 */
package one.empty3.library.core.tribase;

import android.graphics.Color;

import one.empty3.library.Point3D;
import one.empty3.library.TRIObject;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;


public class TubulaireN2<T extends ParametricCurve> extends ParametricSurface {
    public static double TAN_FCT_INCR = 0.000001;
    public static double NORM_FCT_INCR = 0.000001;

    private T surve;

    private double diam = 1.0;
    private int N_TOURS = 40;

    private TRIObject tris = null;


    public TubulaireN2() {
    }

    public TubulaireN2(T surve) {
        this.surve = surve;
    }

    public Point3D calculerNormale(double t) {
        return calculerTangente(t + NORM_FCT_INCR).moins(calculerTangente(t));
    }

    public Point3D calculerTangente(double t) {
        return surve.calculerPoint3D(t + TAN_FCT_INCR).moins(surve.calculerPoint3D(t));
    }

    public void diam(double diam) {
        this.diam = diam;
    }

    public TRIObject generate() {
        Color color = Color.valueOf(getTexture().getColorAt(0.5, 0.5));
        if (tris == null) {
            tris = new TRIObject();


            double length = 1;

        }
        return tris;
    }


    public void nbrAnneaux(int n) {
        surve.getParameters().setIncrU(1.0 / n);
        setMaxX(n);
    }

    public void nbrRotations(int r) {
        this.N_TOURS = r;
        setMaxY(r);
    }

    public void radius(double d) {
        diam = d * 2;
    }

    public double tMax() {
        return (double) 1;
    }

    @Override
    public String toString() {
        String s = "tubulaireN2 (\n\t("
                + surve.toString();
        s += "\n\n)\n\t" + diam + "\n\t" + getTexture().toString() + "\n)\n";
        return s;
    }


    private Point3D[] vectPerp(double t) {
        Point3D[] vecteurs = new Point3D[3];

        Point3D p = surve.calculerPoint3D(t);
        Point3D tangente = calculerTangente(t);

        Point3D ref1 = new Point3D(0d, 0d, 1d);
        Point3D ref2 = new Point3D(1d, 0d, 0d);
        Point3D ref3 = new Point3D(0d, 1d, 0d);

        tangente = tangente.norme1();

        if (tangente != null) {
            Point3D px = calculerNormale(t);///tangente.prodVect(ref1);

            if (px.norme() == 0) {
                px = tangente.prodVect(ref2);
            }
            if (px.norme() == 0) {
                px = tangente.prodVect(ref3);
            }

            Point3D py = px.prodVect(tangente);

            px = px.norme1();
            py = py.norme1();

            vecteurs[0] = tangente;
            vecteurs[1] = px;
            vecteurs[2] = py;

        }
        return vecteurs;
    }

    public void curve(T surve) {
        this.surve = surve;
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        Point3D[] vectPerp = vectPerp(u);
        return surve.calculerPoint3D(u).plus(
                vectPerp[1].mult(diam * Math.cos(2 * Math.PI * v)).plus(
                        vectPerp[2].mult(diam * Math.sin(2 * Math.PI * v))));
    }

    @Override
    public Point3D calculerVitesse3D(double u, double v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
