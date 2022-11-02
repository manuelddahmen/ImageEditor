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

/*
 * 2013 Manuel Dahmen
 */
package one.empty3.library.core.tribase;

import one.empty3.library.Point3D;
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
        setCirculaireX(false);
        setCirculaireY(false);
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
        Point3D o, x, y;
        o = p0.getElem();
        x = vX.getElem();
        y = p0.getElem();
        return x.moins(o).prodVect(y.moins(o));
    }

    public Point3D coordPoint3D(int x, int y) {
        return calculerPoint3D(1. * x / getMaxX(), 1. * y / getMaxY());
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
