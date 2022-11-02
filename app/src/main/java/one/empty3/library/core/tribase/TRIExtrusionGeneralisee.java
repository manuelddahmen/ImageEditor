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

package one.empty3.library.core.tribase;


import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.ParametricSurface;

public class TRIExtrusionGeneralisee extends ParametricSurface {

    public StructureMatrix<CourbeParametriquePolynomialeBezier> curve = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);
    public StructureMatrix<CurveSurface> surface = new StructureMatrix<>(0, CurveSurface.class);
    private StructureMatrix<Boolean> sectionA = new StructureMatrix<>(0, Boolean.class);
    private StructureMatrix<Boolean> sectionB = new StructureMatrix<>(0, Boolean.class);
    private Point3D normaleFixe;

    public TRIExtrusionGeneralisee() {
        setCirculaireY(true);
        setCirculaireX(false);

    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("curve/ame de l'extrusion", curve);
        getDeclaredDataStructure().put("surface/Surface à extruder", curve);
        getDeclaredDataStructure().put("sectionA/sectionA", sectionA);
        getDeclaredDataStructure().put("sectionB/sectionB", sectionB);

    }

    public boolean isSectionA() {
        return sectionA.getElem();
    }

    public void setSectionA(boolean sectionA) {
        this.sectionA.setElem(sectionA);
    }

    public boolean isSectionB() {
        return sectionB.getElem();
    }

    public void setSectionB(boolean sectionB) {
        this.sectionB.setElem(sectionB);
    }


    public Point3D calculerPoint3D(double u, double v) {

        Point3D Op, T, NX, NY, pO;

        Op = curve.getElem().calculerPoint3D(u);

        T = curve.getElem().tangente(u);


        /*__
         * Plan normal pour le chemin
         *
         */
        Point3D normale = curve.getElem().calculerNormale(u);
        /*if ((normale.norme() < 0.001 || normale.prodVect(T).norme() < 0.001)) {
            if (normaleFixe == null) {
                normaleFixe = T.prodVect(Point3D.r(1));
            }
            NX = normaleFixe.norme1();
        } else {
            NX = normale.norme1();
        }//*/
        T = T.norme1();
        NX = normale.norme1();
        NY = NX.prodVect(T).norme1();
/*
        System.err.println("\nT "+T );
        System.err.println("NX"+NX);
        System.err.println("NY"+NY);
 */
        pO = Op.plus(NX.mult(surface.getElem().calculerPoint3D(v))).plus(NY.mult(surface.getElem().calculerPoint3D(v)));
        return pO;

    }
}
