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
        this.sectionA.setElem( sectionA);
    }

    public boolean isSectionB() {
        return sectionB.getElem();
    }

    public void setSectionB(boolean sectionB) {
        this.sectionB .setElem(sectionB);
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
