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

package one.empty3.library;

import java.util.HashMap;

/*__
 * Created by Win on 26-01-16.
 */
public class Rotation implements MatrixPropertiesObject {
    protected StructureMatrix<Matrix33> rot;
    protected StructureMatrix<Point3D> centreRot;
    protected boolean unmodified = true;

    public Rotation() {
        rot = new StructureMatrix<>(0, Matrix33.class);
        centreRot = new StructureMatrix<>(0, Point3D.class);
        this.rot.setElem(Matrix33.I);
        this.centreRot.setElem(Point3D.O0);
    }

    public Rotation(Matrix33 rot, Point3D centreRot) {
        this();
        if (rot == null)
            rot = Matrix33.I;
        if (centreRot == null)
            centreRot = Point3D.O0;
       /* if(this.rot==null||this.rot.getElem()==null)
            this.rot = rot1;
        if(this.centreRot==null||this.centreRot.getElem()==null)
            this.centreRot = centreRot1;
            */
        this.rot.setElem(rot);
        this.centreRot.setElem(centreRot);
    }

    public boolean isUnmodified() {
        return unmodified;
    }

    public void setUnmodified(boolean unmodified) {
        this.unmodified = unmodified;
    }

    public Point3D rotation(Point3D p) {
        if (p != null && centreRot != null && rot != null) {
            try {
                return centreRot.getElem().plus(rot.getElem().mult(p.moins(centreRot.getElem())));
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return p;
    }

    public Point3D rotationAxe(Point3D p, int axe, double angle) {

        return Matrix33.rotationX(angle).mult(p);
    }

    public StructureMatrix<Matrix33> getRot() {
        return rot;
    }

    public void setRot(StructureMatrix<Matrix33> rot) {
        this.rot = rot;
    }

    public StructureMatrix<Point3D> getCentreRot() {
        return centreRot;
    }

    public void setCentreRot(StructureMatrix<Point3D> centreRot) {
        this.centreRot = centreRot;
    }

    private HashMap<String, StructureMatrix> declaredDataStructures = new HashMap<>();

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return declaredDataStructures.get(name);
    }

    @Override
    public void declareProperties() {
        declaredDataStructures.put("rot/Matrice de rotation 3x3", getRot());
        declaredDataStructures.put("centreRot/Centre de rotation", getCentreRot());
    }

    @Override
    public HashMap<String, StructureMatrix> declarations() {
        declareProperties();
        return declaredDataStructures;
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return new Rotation((Matrix33) getRot().copy().getElem(), (Point3D) (getCentreRot().copy().getElem()));
    }
}
