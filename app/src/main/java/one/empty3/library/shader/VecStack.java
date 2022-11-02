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
