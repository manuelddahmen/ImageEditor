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

import one.empty3.library.core.raytracer.tree.*;
import one.empty3.library.*;

/*__
 *  VecStack.
 * fonctions vecteurs
 * a parser à partir d'un fichirr xml ptmrporre
 *
 */
public class VecStack extends VecAlTree {
    private StructureMatrix<Integer> numsIn = new StructureMatrix(1, Integer.class);
    private StructureMatrix<Integer> numsOut = new StructureMatrix(1, Integer.class);


    /*__
     * @param in in() [i:int]
     * @param out out() [i : int]
     * @param formula f(in, out)
     */
    public VecStack(String formula, int dim) {
        super(formula, dim);
    }

    public StructureMatrix<Integer> getVecIn() {
        return numsIn;
    }

    public StructureMatrix<Integer> getVecOut() {
        return numsOut;
    }

    public String getFormula() {
        return formula;
    }

    public Double value() {/*
    for(int i=0;i<numsIn.getData1d().size(); i++) 
       tree.setParam("in("+i+ ") ", super.value()[i]) ;
    return tree.eval();*/
        return 0.0;
    }
} 
