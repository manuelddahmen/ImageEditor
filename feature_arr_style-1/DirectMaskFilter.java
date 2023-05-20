package one.empty3.feature_arr_style;

import one.empty3.library.core.raytracer.tree.*;

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
        AlgebricTree[] treeA = new AlgebricTree[formulaColorComps.length];
        try {
            for (int c = 0; c < formulaColorComps.length; c++) {
                treeA[c] = new AlgebricTree(formulaColorComps[c]);

                for (int i = 0; i < m1.getColumns(); i++)
                    for (int j = 0; j < m1.getLines(); j++) {
                        AlgebricTree tree = treeA[c];
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

                        double value = (double) (Double) (tree.eval());
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
