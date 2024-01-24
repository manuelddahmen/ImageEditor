/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
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

package one.empty3.library1.tree;

import java.util.HashMap;
import java.util.Map;

import one.empty3.library.StructureMatrix;

/*__
 * Created by manuel on 16-12-16.
 */
public class VariableTreeNodeType extends TreeNodeType {
    String varName;
    Map<String, String> parametersValueVec;
    Map<String, Double> parametersValuesDouble;
    private HashMap<String, StructureMatrix<Double>> parametersValuesComputed;

    public VariableTreeNodeType(AlgebraicTree tree) {
        this.algebraicTree = tree;
    }

    @Override
    public void setValues(Object[] values) {
        super.setValues(values);
        varName = (String) values[0];
        if (values.length >= 4) {
            parametersValueVec = algebraicTree.getParametersValuesVec();
            parametersValuesDouble = algebraicTree.getParametersValues();
            parametersValuesComputed = algebraicTree.getParametersValuesVecComputed();
        }
    }

    @Override
    public StructureMatrix<Double> eval() {
        StructureMatrix<Double> doubleStructureMatrix = new StructureMatrix<>(0, Double.class);
        try {
            if (parametersValueVec != null && parametersValuesDouble != null && parametersValuesComputed != null) {
                if (parametersValueVec.get(varName) != null) {
                    doubleStructureMatrix = new StructureMatrix<>(1, Double.class);
                } else if (parametersValuesDouble.get(varName) != null) {
                    Double d = parametersValuesDouble.get(varName);
                    doubleStructureMatrix.setElem(d);
                } else if (algebraicTree.getParametersValuesVecComputed().containsKey(varName)) {
                    doubleStructureMatrix = algebraicTree.getParametersValuesVecComputed().get(varName);
                }
            } else {
                throw new TreeNodeEvalException("Eval: dictionary not attributed/initialized");
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return doubleStructureMatrix;
        } catch (TreeNodeEvalException e) {
            throw new RuntimeException(e);
        }
        return doubleStructureMatrix;
    }
}
/*
x=(1,2,3)
y=(5,6,7)
z=x+y

 */