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

package one.empty3.library.core.physics;

import one.empty3.library.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Force {
    public double amortissement = 0;
    public double intensiteRepulsion = 0;
    private double distMinFusion;
    private boolean fusion;
    private double G = 10;
    private ArrayList<Bille> courant = null;
    private ArrayList<Bille> next = null;
    private double dt = 1.0 / 25;
    private Point3D cm;
    private double cmd;
    private double distMax = 0.0;
    private double distMin = Double.MAX_VALUE;
    private double[] dMin;
    private double[] dMax;

    public void configurer(ArrayList<Bille> courant) {
        this.courant = courant;
        this.dMax = new double[courant.size()];
        this.dMin = new double[courant.size()];


    }

    public Point3D centreMasse() {
        return cm;
    }

    public Point3D attractionRepulsion(Bille other, Bille p) {
        if (p != other) {
            double r = other.position.moins(p.position).norme();
            if (r > distMax)
                distMax = r;
            if (r < distMin)
                distMin = r;

            Point3D vu = other.position.moins(p.position).norme1();
            return vu.mult(
                            intensiteRepulsion * other.masse * p.masse / r / r / r
                    )

                    .plus(

                            vu.mult(
                                    G * other.masse * p.masse / r / r
                            )
                    );
        }
        return Point3D.O0;
    }

    public Point3D frottement(Bille p) {
        Point3D fvp = p.vitesse.mult(p.amortissement * amortissement * -1);

        return fvp;
    }

    private void delete1(int ind) {
        ArrayList<Bille> courantMinus1 = new ArrayList<Bille>(courant.size() - 1);

        int i = 0;
        for (int a = 0; a < courant.size() - 1; a++) {

            if (a == ind)
                continue;
            courantMinus1.set(a, courant.get(i));
            i++;
        }

        this.courant = courantMinus1;
    }

    public double dMin(int ind) {
        return dMin[ind];
    }

    public double dMax(int ind) {
        return dMax[ind];
    }

    public Point3D force(int ind) {
        Point3D f = Point3D.O0;
        dMin[ind] = Double.MAX_VALUE;
        dMax[ind] = Double.MIN_VALUE;
        for (int i = 0; i < courant.size(); i++) {
            if (courant.get(i) != courant.get(ind)) {

                double dTmp = courant.get(ind).position.moins(courant.get(i).position).norme();
                if (dTmp < dMin[ind])
                    dMin[ind] = dTmp;
                if (dTmp > dMax[ind])
                    dMax[ind] = dTmp;

                f = f.plus(attractionRepulsion(courant.get(i), courant.get(ind)).plus(frottement(courant.get(i))));
                if (isFusion()) {
                    courant.get(ind).masse += courant.get(i).masse;
                    courant.get(ind).vitesse = courant.get(ind).vitesse.plus(courant.get(i).vitesse);

                    courant.set(i, courant.get(ind));

                    delete1(ind);
                }
            }
        }
        f = f.plus(frottement(courant.get(ind)));

        return f;
    }


    public Point3D acc(int ind) {
        return force(ind).mult(1 / courant.get(ind).masse);
    }


    public Point3D vitesse(int ind) {
        return (next.get(ind).vitesse = courant.get(ind).vitesse.plus(acc(ind).mult(dt)));
    }


    public void populateList(ArrayList<Bille> billes) {
        for (int i = 0; i < courant.size(); i++)
            billes.add(new Bille());
    }

    public void calculer() {
        cm = Point3D.O0;
        cmd = 0.0;


        next = new ArrayList<Bille>(courant.size());
        populateList(next);
        distMax = 0.0;
        distMin = Double.MAX_VALUE;

        for (int i = 0; i < courant.size(); i++) {
            next.set(i, new Bille(courant.get(i)));

            next.get(i).setPosition(courant.get(i).position.changeTo(courant.get(i).position.plus(vitesse(i).mult(dt))));
            cm = cm.plus(next.get(i).position.mult(next.get(i).masse));
            cmd += next.get(i).masse;

        }

        cm = cm.mult(1 / cmd);

        courant = next;
    }


    public double getDistMax() {
        return distMax;
    }


    public void setDistMax(double distMax) {
        this.distMax = distMax;
    }


    public double getDistMin() {
        return distMin;
    }


    public void setDistMin(double distMin) {
        this.distMin = distMin;
    }

    public List<Bille> getCourant() {
        return courant;
    }

    public void setCourant(ArrayList<Bille> courant) {
        this.courant = courant;
    }


    public List<Bille> getNext() {
        return next;
    }


    public void setNext(ArrayList<Bille> next) {
        this.next = next;
    }


    public double getDt() {
        return dt;
    }


    public void setDt(double dt) {
        this.dt = dt;
    }


    public double getG() {
        return G;
    }


    public void setG(double g) {
        G = g;
    }


    public double getDistMinFusion() {
        return distMinFusion;
    }


    public void setDistMinFusion(double distMinFusion) {
        this.distMinFusion = distMinFusion;
    }


    public boolean isFusion() {
        return fusion;
    }


    public void setFusion(boolean fusion) {
        this.fusion = fusion;
    }

}
