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
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.nurbs;

import android.os.Build;

import androidx.annotation.RequiresApi;

import android.graphics.Color;

import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.tribase.TRIObjetGenerateurAbstract;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class ParametricSurface extends TRIObjetGenerateurAbstract {


    private static Globals globals;


    private StructureMatrix<Double> incrU = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> incrV = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> incrVitesse = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> incrNormale = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> startU = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> endU = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> startV = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> endV = new StructureMatrix<>(0, Double.class);
    private ParametricSurface.Parameters parameters = new ParametricSurface.Parameters(true);

    static {
        if (globals == null) {
            Globals globals1 = new Globals();
            ParametricSurface.setGlobals(globals1);
            globals1.setIncrU(0.1);
            globals1.setIncrV(0.1);
        }


    }


    public static Globals getGlobals() {
        return globals;
    }

    public static void setGlobals(Globals globals) {
        ParametricSurface.globals = globals;
    }

    public Double getIncrU() {
        return incrU.getElem();
    }

    public void setIncrU(Double incr1) {
        if (parameters.isGlobal()) {
            parameters.setIncrU(incr1);
        } else {
            globals.setIncrU(incr1);
        }
        this.incrU.setElem(incr1);
    }

    public void setIncrV(Double incr2) {
        if (parameters.isGlobal()) {
            parameters.setIncrV(incr2);
        } else {
            globals.setIncrV(incr2);
        }
        this.incrV.setElem(incr2);
    }

    public Double getIncrV() {
        return incrV.getElem();
    }


    public Point3D calculerPoint3D(double u, double v) {
        return new Point3D(0d, 0d, 0d);
    }

    public Point3D calculerVitesse3D(double u, double v) {
        Point3D moins = calculerPoint3D(u + incrVitesse.getElem(), v).moins(calculerPoint3D(u, v));
        Point3D moins1 = calculerPoint3D(u, v + incrVitesse.getElem()).moins(calculerPoint3D(u, v));
        return moins.plus(moins1).mult(0.5 / incrVitesse.getElem() / incrVitesse.getElem()).norme1();
    }

    public Point3D calculerNormale3D(double u, double v) {
        Point3D moins = calculerPoint3D(u + incrNormale.getElem(), v).plus(calculerPoint3D(u, v));
        Point3D moins1 = calculerPoint3D(u, v + incrNormale.getElem()).plus(calculerPoint3D(u, v));
        return moins.prodVect(moins1).mult(0.5 / incrNormale.getElem() / incrNormale.getElem()).norme1();
    }

    public Point3D calculerTangenteU(double u, double v) {
        Point3D moins = calculerPoint3D(u + incrVitesse.getElem(), v).moins(calculerPoint3D(u, v));
        return moins.mult(1.0 / incrVitesse.getElem() / incrVitesse.getElem()).norme1();
    }

    public Point3D calculerTangenteV(double u, double v) {
        Point3D moins1 = calculerPoint3D(u, v + incrVitesse.getElem()).moins(calculerPoint3D(u, v));
        return moins1.mult(1.0 / incrVitesse.getElem()).norme1();
    }

    public Double incr1() {
        return incrU.getElem();
    }

    public Double incr2() {
        return incrV.getElem();
    }

    public Double getStartU() {
        return startU.getElem();
    }

    public void setStartU(Double s1) {
        this.startU.setElem(s1);
    }

    public Double getStartV() {
        return startV.getElem();
    }

    public void setStartV(Double s2) {
        this.startV.setElem(s2);
    }

    public Double getEndU() {
        return endU.getElem();
    }

    public void setEndU(Double e1) {
        this.endU.setElem(e1);
    }

    public Double getEndV() {
        return endV.getElem();
    }

    public void setEndV(Double e2) {
        this.endV.setElem(e2);
    }

    public Point3D velocity(Double u1, Double v1, Double u2, Double v2) {
        return calculerPoint3D(u2, v2).moins(calculerPoint3D(u1, v1));
    }

    public Point3D coordPoint3D(int x, int y) {
        return calculerPoint3D(1.0 * x / getMaxX(), 1.0 * y / getMaxY());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Polygon getElementSurface(Double u, Double incrU, Double v, Double incrV) {
        Double[][] uvincr = new Double[][]{
                {u, v},
                {u + incrU, v},
                {u + incrU, v + incrV},
                {u, v + incrV}
        };
        Polygon polygon = new Polygon(new Point3D[]{
                calculerPoint3D(uvincr[0][0], uvincr[0][1]),
                calculerPoint3D(uvincr[1][0], uvincr[1][1]),
                calculerPoint3D(uvincr[2][0], uvincr[2][1]),
                calculerPoint3D(uvincr[3][0], uvincr[3][1])},
                (Color) Color.valueOf(0, 0, 0));
        return polygon;
    }

    public int getNormale3D(double v, double v1) {
        return 0;
    }

    public static class Globals {
        private Double incrU;
        private Double incrV;

        public Double getIncrU() {
            return incrU;
        }

        public void setIncrU(Double incrU) {
            this.incrU = incrU;
        }

        public Double getIncrV() {
            return incrV;
        }

        public void setIncrV(Double incrV) {
            this.incrV = incrV;
        }
    }

    public class Parameters {

        private boolean isGlobal = true;
        private Double incrV = 0.1;
        private Double incrU = 0.1;

        public Parameters(Double incrU, Double incrV) {
            this.setIncrU(incrU);
            this.setIncrV(incrV);
        }

        public Parameters(boolean isGlobal) {
            setGlobal(isGlobal);
        }

        public Double getIncrU() {
            return incrU;
        }

        public void setIncrU(Double incrU) {
            this.incrU = incrU;
        }

        public Double getIncrV() {
            return incrV;
        }

        public void setIncrV(Double incrV) {
            this.incrV = incrV;
        }

        public boolean isGlobal() {
            return isGlobal;
        }

        public void setGlobal(boolean global) {
            this.isGlobal = global;
        }
    }

    public ParametricSurface() {
        startU.setElem(0.0);
        startV.setElem(0.0);
        incrU.setElem(0.1);
        incrV.setElem(0.1);
        endU.setElem(1.0);
        endV.setElem(1.0);
        incrVitesse.setElem(0.0001);
        incrNormale.setElem(0.000001);

    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("startU/startU", startU);
        getDeclaredDataStructure().put("startV/startV", startV);
        getDeclaredDataStructure().put("incrU/incrU", incrU);
        getDeclaredDataStructure().put("incrV/incrV", incrV);
        getDeclaredDataStructure().put("endU/endU", endU);
        getDeclaredDataStructure().put("endV/endV", endV);
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ParametricSurface()\n";
    }

    @Override
    public Point3D calculerSurfaceT(double u, double v, double t) {
        return calculerPoint3D(u, v);
    }

}
