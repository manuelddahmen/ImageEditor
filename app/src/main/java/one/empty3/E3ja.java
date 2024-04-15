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

package one.empty3;

import one.empty3.library1.tree.*;

/***
 programme de calcul vectoriel matriciel ou en
 nombres réels


 */
public class E3ja {
    public E3ja(


    ) {

    }

    /***
     *  example: setFormulaFunctionA(AlgebraicTree al) { ... }
     *           setFormulaFunctionB(...
     * @param o Pojo Object. set{PropertyName} get{PropertyName}
     * @param propertyFunction {propertyName}=propertyValue
     * formulaFunctionA=sin(x):formulaFunctionB=cos(x)"
     */
    public static void setFormula(Object o, String propertyFunction) {
        String[] pf = propertyFunction.split(":");
        for (String i : pf) {
            String[] pair = i.split("=");
            String propertyName = pair[0];
            String formula = pair[1];
            try {
                AlgebraicTree tree = new AlgebraicTree(formula);
                tree.construct();
                Pojo.setProperty(o, propertyName, (Object) tree, tree.getClass());
            } catch (Exception | AlgebraicFormulaSyntaxException ex) {
                ex.printStackTrace();
            }

        }
    }
}
