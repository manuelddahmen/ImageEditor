package one.empty3.library.shader;

import one.empty3.library.core.raytracer.tree.*;

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
