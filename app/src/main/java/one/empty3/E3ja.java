package one.empty3;

import one.empty3.library.core.raytracer.tree.*;

/***
 programme de calcul vectoriel matriciel ou en
 nombres réels


 */
public class E3ja {
    public E3ja(


    ) {

    }

    /***
     *  example: setFormulaFunctionA(AlgebricTree al) { ... }
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
                AlgebricTree tree = new AlgebricTree(formula);
                tree.construct();
                Pojo.setProperty(o, propertyName, (Object) tree, tree.getClass());
            } catch (Exception | AlgebraicFormulaSyntaxException ex) {
                ex.printStackTrace();
            }

        }
    }
}
