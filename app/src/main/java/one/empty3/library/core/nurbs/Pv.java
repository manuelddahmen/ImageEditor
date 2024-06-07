/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.StructureMatrix;

public class Pv extends ParametricVolume {
    private StructureMatrix<ParametricSurface> surfaceAb = new StructureMatrix<>(1, ParametricSurface.class);

    public Pv() {

    }

    @Override
    public Point3D calculerPoint3D(Point3D p0) {
        Point3D pB = surfaceAb.getElem(1).calculerPoint3D(p0.get(0), p0.get(1));
        Point3D pA = surfaceAb.getElem(0).calculerPoint3D(p0.get(0), p0.get(1));
        return pA.plus(pB.moins(pA).mult(p0.get(2)));

    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("surfaceAb/surfaceAb", surfaceAb);
    }

    public StructureMatrix<ParametricSurface> getSurfaceAb() {
        return surfaceAb;
    }

    public void setSurfaceAb(StructureMatrix<ParametricSurface> surfaceAb) {
        this.surfaceAb = surfaceAb;
    }
}
