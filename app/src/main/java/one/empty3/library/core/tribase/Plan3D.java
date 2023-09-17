/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * 2013 Manuel Dahmen
 */
package one.empty3.library.core.tribase;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricSurface;

public class Plan3D extends ParametricSurface {

    private StructureMatrix<Point3D> p0 = new StructureMatrix<>(0, Point3D.class);
    private StructureMatrix<Point3D> vX = new StructureMatrix<>(0, Point3D.class);
    private StructureMatrix<Point3D> vY = new StructureMatrix<>(0, Point3D.class);

    public Plan3D() {
        p0.setElem(new Point3D(0.0, 0.0, 0.0));
        vX.setElem(new Point3D(1.0, 0.0, 0.0));
        vY.setElem(new Point3D(0.0, 1.0, 0.0));
        //setMaxX(1);
        //setMaxY(1);
    }
    public Plan3D(Point3D p0, Point3D pX, Point3D pY) {
        this.p0.setElem(p0);
        this.vX.setElem(pX);
        this.vY.setElem(pY);
        //setMaxX(1);
        //setMaxY(1);
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return p0.getElem().plus(vX.getElem().moins(p0.getElem()).mult(u)
                .plus(vY.getElem().moins(p0.getElem()).mult(v)));
    }
    @Override
    public Point3D calculerNormale3D(double u, double v) {
        Point3D o,x,y;
        o = p0.getElem();
        x = vX.getElem();
        y = p0.getElem();
        return x.moins(o).prodVect(y.moins(o));
    }

    public String id() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point3D pointOrigine() {
        return p0.getElem();
    }

    public void pointOrigine(Point3D p0) {
        this.p0.setElem(p0);
    }

    public Point3D pointXExtremite() {
        return p0.getElem().plus(vX.getElem());
    }

    public void pointXExtremite(Point3D vX) {
        this.vX.setElem(vX);
    }

    public Point3D pointYExtremite() {
        return p0.getElem().plus(vY.getElem());
    }
//Implements TRIObjetGenerateurAbstract.coordPoint3D

    public void pointYExtremite(Point3D vY) {
        this.vY.setElem(vY);
    }

    public void setId(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "Plan (\n\t" + p0
                + "\n\t" + vX
                + "\n\t" + vY
                + "\n\t\"" + texture
                + "\"\n)\n";
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("p0", p0);
        getDeclaredDataStructure().put("vX", vY);
        getDeclaredDataStructure().put("vY", vY);
    }

    public StructureMatrix<Point3D> getP0() {
        return p0;
    }

    public void setP0(StructureMatrix<Point3D> p0) {
        this.p0 = p0;
    }

    public StructureMatrix<Point3D> getvX() {
        return vX;
    }

    public void setvX(StructureMatrix<Point3D> vX) {
        this.vX = vX;
    }

    public StructureMatrix<Point3D> getvY() {
        return vY;
    }

    public void setvY(StructureMatrix<Point3D> vY) {
        this.vY = vY;
    }
}
