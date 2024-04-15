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

package one.empty3.feature20220726;

import one.empty3.library1.tree.*;

public class DirectMaskFilter {
    PixM m1;
    PixM m2;

    public DirectMaskFilter(PixM m1, PixM m2) {

        this.m1 = m1;
        this.m2 = m2;
    }

    /* (M3.p =) = p1x, p1y,
    
, p2x, p2y,c1r,c2g b a, w, h, ww, wh */
    public PixM applyOperator(String[] formulaColorComps) {
        PixM m3 = new PixM(m1.getColumns(), m1.getLines());
        AlgebraicTree[] treeA = new AlgebraicTree[formulaColorComps.length];
        try {
            for (int c = 0; c < formulaColorComps.length; c++) {
                treeA[c] = new AlgebraicTree(formulaColorComps[c]);

                for (int i = 0; i < m1.getColumns(); i++)
                    for (int j = 0; j < m1.getLines(); j++) {
                        AlgebraicTree tree = treeA[c];
                        tree.setParameter("p1x", (double) i);
                        tree.setParameter("p2x", (double) i);
                        tree.setParameter("p1y", (double) j);
                        tree.setParameter("p2y", (double) j);

                        m1.setCompNo(0);
                        tree.setParameter("c1r", m1.get(i, j));
                        m1.setCompNo(1);
                        tree.setParameter("c1g", m1.get(i, j));
                        m1.setCompNo(2);
                        tree.setParameter("c1b", m1.get(i, j));
                        m2.setCompNo(0);
                        tree.setParameter("c2r", m2.get(i, j));
                        m2.setCompNo(1);
                        tree.setParameter("c2g", m2.get(i, j));
                        m2.setCompNo(2);
                        tree.setParameter("c2b", m2.get(i, j));
                        m1.setCompNo(3);
                        tree.setParameter("c1a", m1.get(i, j));
                        m2.setCompNo(3);
                        tree.setParameter("c2a", m2.get(i, j));

                        tree.setParameter("w", (double) m1.getColumns());

                        tree.setParameter("h", (double) m1.getLines

                                ());

                        tree.setParameter("ww", (double) m2.getColumns());

                        tree.setParameter("wh", (double) m2.getLines());


                        tree.construct();

                        double value = (double) (Double) (tree.eval().getElem());
                        m3.setCompNo(c);
                        m3.set(i, j, value);

                    }
            }
        } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException ex) {
            ex.printStackTrace();
        }


        return m3;

    }


}
