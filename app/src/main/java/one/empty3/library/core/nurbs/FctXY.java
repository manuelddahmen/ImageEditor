/*
 * Copyright (c) 2023.
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

package one.empty3.library.core.nurbs;

import one.empty3.library1.tree.*;
import one.empty3.library.StructureMatrix;

/*__
 * Created by manue on 28-05-19.
 */
public class FctXY extends Fct1D_1D {
    private StructureMatrix<String> formulaX = new StructureMatrix<>(0, String.class);
    private AlgebraicTree treeX;

    public FctXY() {

        formulaX.setElem("10.0");
        setFormulaX(formulaX.getElem());

    }

    public void setFormulaX(String formulaX) {
        this.formulaX.setElem(formulaX);

        try {
            treeX = new AlgebraicTree(formulaX);
            treeX.getParametersValues().put("x", 0.0);
            treeX.construct();
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getFormulaX() {
        return formulaX.getElem();
    }

    public double result(double input) {
        treeX.getParametersValues().put("x", input);
        try {
            return treeX.eval().getElem();
        } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
        return Double.NaN;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("formulaX/fonction Y=f(x)", formulaX);
    }

    @Override
    public String toString() {
        return "fctXY( \"" + formulaX + "\" )\n";
    }
}
