/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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

package one.empty3.library.shader;

import one.empty3.apps.tree.altree.*;

public class VecAlTree extends Vec {
    protected String formula;
    AlgebricTree tree;
    private boolean invalidTree = true;

    public VecAlTree(String formula, int dim) {
        super(dim);
        String[] formulas = formula.split(",");


        this.formula = formula;

        try {
            tree = new AlgebricTree(formula)
            ;
            tree.construct();
            invalidTree = false;
        } catch (AlgebraicFormulaSyntaxException t) {
            System.out.println("error vecaltreecondtruct\n" + tree);
            invalidTree = true;
        }

    }

    public void setParameter(String p, Double d) {
        tree.setParameter(p, d);
    }

    public Double[] getValue() {
        try {
            return new Double[]{
                    tree.eval()};
        } catch (TreeNodeEvalException ex) {
            ex.printStackTrace();
            return new Double[]{0.0};
        } catch (AlgebraicFormulaSyntaxException ex) {
            ex.printStackTrace();
            return new Double[]{0.0};
        }


    }
} 
