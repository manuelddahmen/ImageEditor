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
import java.util.Objects;
import java.util.function.Consumer;

import one.empty3.library.StructureMatrix;

/*__
 * Created by manuel on 16-12-16.
 */
public class VariableTreeNodeType extends TreeNodeType {
    String varName;
    Map<String, String> parametersValueVec;
    private Map<String, Double> parametersValues;
    private HashMap<String, StructureMatrix<Double>> parametersValuesVecComputed;

    public VariableTreeNodeType(AlgebraicTree tree) {
        this.algebraicTree = tree;
    }

    @Override
    public void setValues(Object[] values) {
        super.setValues(values);
        varName = (String) values[0];
        if (values.length >= 4) {
            parametersValueVec = algebraicTree.getParametersValuesVec();
            parametersValues = algebraicTree.getParametersValues();
            parametersValuesVecComputed = algebraicTree.getParametersValuesVecComputed();
        }
    }

    @Override
    public StructureMatrix<Double> eval() {
        StructureMatrix<Double> doubleStructureMatrix = new StructureMatrix<>(0, Double.class);
        try {
            if (parametersValues != null)
                if (parametersValues.get(varName) != null) {
                    doubleStructureMatrix = new StructureMatrix<>(0, Double.class);
                    Double d = parametersValues.get(varName);
                    doubleStructureMatrix.setElem(d);
                } else if (parametersValuesVecComputed.containsKey(varName)) {
                    doubleStructureMatrix = new StructureMatrix<>(0, Double.class);
                    StructureMatrix<Double> finalDoubleStructureMatrix = doubleStructureMatrix;
                    switch (parametersValuesVecComputed.get(varName).getDim()) {
                        case 0 -> {
                            double d = parametersValuesVecComputed.get(varName).getElem();
                            doubleStructureMatrix = new StructureMatrix<>(0, Double.class);
                            doubleStructureMatrix.setElem(d, 0);
                        }
                        case 1 -> {
                            doubleStructureMatrix = new StructureMatrix<>(1, Double.class);
                            for (int i = 0; i < parametersValuesVecComputed.get(varName).data1d.size(); i++) {
                                doubleStructureMatrix.setElem(parametersValuesVecComputed.get(varName).data1d.get(i), i);
                            }
                        }
                    }
                    return doubleStructureMatrix;
                }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return doubleStructureMatrix;
        }
        return doubleStructureMatrix;
    }
}
