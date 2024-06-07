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

package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

/*__
 * Created by manue on 29-12-19.
 */
public class Extrude extends ExtrusionCurveCurve {
    private StructureMatrix<CourbeParametriquePolynomialeBezier> base = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);
    private StructureMatrix<CourbeParametriquePolynomialeBezier> path = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);

    public Extrude() {
        base.setElem(new CourbeParametriquePolynomialeBezier());
        path.setElem(new CourbeParametriquePolynomialeBezier());
    }


    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return base.getElem().calculerPoint3D(u).plus(path.getElem().calculerPoint3D(v).moins(path.getElem().calculerPoint3D(0.0)));
    }

    public void declareProperties() {
        getDeclaredDataStructure().put("base/courbe a extruder", base);
        getDeclaredDataStructure().put("path/courbe normale d'extrusion", path);
    }
}
