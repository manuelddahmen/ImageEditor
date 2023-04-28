
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

package one.empty3.library.core.raytracer.tree;

import one.empty3.library.core.raytracer.tree.functions.MathFunctionTreeNodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


import one.empty3.library.lang.*;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class AlgebricTree extends Tree {

    private String formula = "0.0";
    Map<String, Double> parametersValues = new HashMap<>();
    private Tree t;
    private TreeNode root;
    private List<Objet> classes;

    public AlgebricTree(String formula) throws AlgebraicFormulaSyntaxException {
        this.formula = formula;
    }

    public AlgebricTree(String formula, Map<String, Double> parametersValues) {
        this.formula = formula;
        this.parametersValues = parametersValues;
    }

    public void setParameter(String s, Double d) {
        this.parametersValues.put(s, d);
    }

    public AlgebricTree construct() throws AlgebraicFormulaSyntaxException {
        root = new TreeNode(formula);
        add(root, formula);
        return this;
    }

    public boolean add(TreeNode src, String subformula) throws AlgebraicFormulaSyntaxException {

        if (src == null || subformula == null || subformula.length() == 0)
            return false;

        if (
                addFormulaSeparator(src, subformula) ||
                        addTerms(src, subformula) ||
                        addFactors(src, subformula) ||
                        addPower(src, subformula) ||
                        addSingleSign(src, subformula) ||
                        addDouble(src, subformula) ||
                        addFunction(src, subformula) ||
                        addVariable(src, subformula) ||
                        addBracedExpression(src, subformula)

        ) {
            /*Iterator<TreeNode> it = src.getChildren().iterator();
            while (it.hasNext()) {
                TreeNode children = it.next();
                if (!add(children, children.getExpressionString())) {
                    //throw new AlgebraicFormulaSyntaxException();
                }
            }*/
        } else
            throw new AlgebraicFormulaSyntaxException(this);
        return true;
    }

    private boolean addFormulaSeparator(TreeNode src, String subformula) {
        String[] s;
        s = subformula.split("=");
        if (s.length > 1) {
            EquationTreeNode tt = new EquationTreeNode(subformula);
            tt.getChildren().add(new EquationTreeNode(s[0]));
            tt.getChildren().add(new EquationTreeNode(s[1]));
        } else
            return false;
        return true;
    }

    private boolean addVariable(TreeNode src, String subformula)
            throws AlgebraicFormulaSyntaxException {
        if (Character.isLetter(subformula.charAt(0))) {
            int i = 1;
            while (i < subformula.length() && Character.isLetterOrDigit(subformula.charAt(i))) {
                i++;
            }

            VariableTreeNodeType variableTreeNodeType = new VariableTreeNodeType();
            variableTreeNodeType.setValues(new Object[]{subformula.substring(0, i), parametersValues});
            src.getChildren().add(new TreeNodeVariable(src, new Object[]{subformula.substring(0, i), parametersValues}, variableTreeNodeType));

            if (subformula.length() > i)
                throw new AlgebraicFormulaSyntaxException("var tree node test failed. error in formula+ \n" +
                        subformula.substring(0, i) + " of " + subformula
                );


        }
        return src.getChildren().size() > 0;
    }


    private boolean addDouble(TreeNode src, String subformula) {
        try {
            Double d = Double.parseDouble(subformula);
            DoubleTreeNodeType doubleTreeNodeType = new DoubleTreeNodeType();
            doubleTreeNodeType.setValues(new Object[]{subformula, d});
            src.getChildren().add(new TreeNodeDouble(src, new Object[]{subformula, d}, doubleTreeNodeType));

            return true;
        } catch (NumberFormatException ex) {
            return src.getChildren().size() > 0;
        }
    }

    private boolean addSingleSign(TreeNode src, String subformula) {
        if (subformula.charAt(0) == '-') {
            src.getChildren().add(new TreeNode(src, new Object[]{subformula.substring(1)}, new SignTreeNodeType(-1.0)));
            return true;
        }
        return false;

    }

    public boolean addPower(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int countTerms = 0;

        TreeNode t2;
        int i = 0;
        boolean firstTermFound = false;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = '*';
        double newFactorSign = 1;
        double oldFactorSign = 1;
        while (i < values.length()) {
            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (values.charAt(i) == '^' && /*9(i < values.length() - 1 || values.charAt(i + 1) != '*') &&*/ count == 0) {
                newFactor = '^';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (i == values.length() - 1 && count == 0 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
                /*if (values.charAt(oldFactorPos - 1) == '/') {
                    newFactorSign = -1;
                    newFactor = '/';//??
                } else if (values.charAt(oldFactorPos - 1) == '*') {
                    newFactorSign = 1;
                    newFactor = '*';//??
                } else throw new AlgebraicFormulaSyntaxException("Ni + ni -");
            */
            }


            if (isNewFactor) {
                countTerms++;
                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    t2 = new TreeNode(t, new Object[]{subsubstring}, new PowerTreeNodeType(oldFactorSign));
                    t.getChildren().add(t2);
                    if (!add(t2, subsubstring)) {
                        return false;
                    }
                }


                isNewFactor = false;
                count = 0;
                newFactorPos = i + 1;
                oldFactorPos = i + 1;
                newFactor = 0;
                oldFactorSign = newFactorSign;
            }

            i++;


        }
        return t.getChildren().size() > 0 && countTerms > 0;
    }

    public boolean addFactors(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int countTerms = 0;

        TreeNode t2;
        int i = 0;
        boolean firstTermFound = false;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = '*';
        double newFactorSign = 1;
        double oldFactorSign = 1;
        while (i < values.length()) {
            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (values.charAt(i) == '*' && /*9(i < values.length() - 1 || values.charAt(i + 1) != '*') &&*/ count == 0) {
                newFactor = '*';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (values.charAt(i) == '/' && count == 0) {
                newFactor = '/';
                isNewFactor = true;
                newFactorPos = i;
                firstTermFound = true;
                newFactorSign = -1;
            } else if (i == values.length() - 1 && count == 0 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
                /*if (values.charAt(oldFactorPos - 1) == '/') {
                    newFactorSign = -1;
                    newFactor = '/';//??
                } else if (values.charAt(oldFactorPos - 1) == '*') {
                    newFactorSign = 1;
                    newFactor = '*';//??
                } else throw new AlgebraicFormulaSyntaxException("Ni + ni -");
            */
            }


            if (isNewFactor) {
                countTerms++;
                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    t2 = new TreeNode(t, new Object[]{subsubstring}, new FactorTreeNodeType(oldFactorSign));
                    t.getChildren().add(t2);
                    if (!add(t2, subsubstring)) {
                        return false;
                    }
                }


                isNewFactor = false;
                count = 0;
                newFactorPos = i + 1;
                oldFactorPos = i + 1;
                newFactor = 0;
                oldFactorSign = newFactorSign;
            }

            i++;


        }
        return t.getChildren().size() > 0 && countTerms > 0;
    }

    public boolean addTerms(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int countTerms = 0;

        TreeNode t2;
        int i = 0;
        boolean firstTermFound = false;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = '+';
        double newFactorSign = 1;
        double oldFactorSign = 1;
        while (i < values.length()) {
            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            } else if (values.charAt(i) == '+' /*&& (i < values.length() - 1 || values.charAt(i + 1) != '+')*/ && count == 0) {
                newFactor = '+';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (values.charAt(i) == '-' && count == 0) {
                newFactor = '-';
                isNewFactor = true;
                newFactorPos = i;
                firstTermFound = true;
                newFactorSign = -1;
            }
            if (i == values.length() - 1 && count == 0 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
/*                if (values.charAt(oldFactorPos - 1) == '-') {
                    newFactorSign = -1;
                    newFactor = '-';
                } else if (values.charAt(oldFactorPos - 1) == '+') {
                    newFactorSign = 1;
                    newFactor = '+';
                }
*/               // else throw new AlgebraicFormulaSyntaxException("Ni + ni -");

            }


            if (isNewFactor) {
                countTerms++;
                isNewFactor = false;
                char op = newFactor;

                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    t2 = new TreeNode(t, new Object[]{subsubstring}, new TermTreeNodeType(oldFactorSign));
                    t.getChildren().add(t2);
                    if (!add(t2, subsubstring)) {
                        return false;
                    }
                }


                isNewFactor = false;
                newFactorPos = i + 1;
                oldFactorPos = i + 1;
                newFactor = 0;
                oldFactorSign = newFactorSign;
            }

            i++;


        }

        return t.getChildren().size() > 0 && countTerms > 0;
    }


    public boolean addFunction(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int i = 1;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = 0;
        int countLetters = 0;

        while (i < values.length()) {
            if (Character.isLetter(values.charAt(0)) && Character.isLetterOrDigit(values.charAt(i)) && count == 0) {
                countLetters++;
            } else if (values.charAt(i) == '(') {
                if (count == 0) {
                    newFactorPos = i + 1;
                }
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }


            if (count == 0 && values.charAt(i) == ')') {


                String fName = values.substring(oldFactorPos, newFactorPos - 1);
                String fParamString = values.substring(newFactorPos, i);


                MathFunctionTreeNodeType mathFunctionTreeNodeType = new MathFunctionTreeNodeType(
                        fParamString, parametersValues
                );

                TreeNode t2 = new TreeNode(t, new Object[]{fName}, mathFunctionTreeNodeType);
                add(t2, fParamString);

                t.getChildren().add(t2);

            }


            i++;

        }

        return t.getChildren().size() > 0;
    }

    /*
     * add method calls
     * examples
     * a = new Point(0.0, y/this.getResY());
     * b.x >= p.plus(p2.mult(3.0).add(p3)).getY();

     * ajouter {; , .}
     */

    public boolean addMethodCall(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int i = 1;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = 0;
        int countLetters = 0;

        while (i < values.length()) {
            if (Character.isLetter(values.charAt(0)) && Character.isLetterOrDigit(values.charAt(i)) && count == 0) {
                countLetters++;
            } else if (values.charAt(i) == '(') {
                if (count == 0) {
                    newFactorPos = i + 1;
                }
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }


            if (count == 0 && values.charAt(i) == ')') {


                String fName = values.substring(oldFactorPos, newFactorPos - 1);
                String fParamString = values.substring(newFactorPos, i);


                MathFunctionTreeNodeType mathFunctionTreeNodeType = new MathFunctionTreeNodeType(
                        fParamString, parametersValues
                );

                TreeNode t2 = new TreeNode(t, new Object[]{fName}, mathFunctionTreeNodeType);
                add(t2, fParamString);

                t.getChildren().add(t2);

            }


            i++;

        }

        return t.getChildren().size() > 0;
    }

    public boolean addBracedExpression(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int i = 1;
        int count = 0;
        if (values.charAt(0) == '(') {
            count++;
            while (i < values.length()) {
                if (values.charAt(i) == ')') {
                    count--;
                }
                if (values.charAt(i) == '(') {
                    count++;
                }

                if (i == values.length() - 1 && count == 0 && values.charAt(i) == ')') {
                    String subsubstring = values.substring(1, values.length() - 1);

                    TreeNode t2 = new TreeNode(t, new Object[]{subsubstring}, new IdentTreeNodeType());

                    //System.err.println(subsubstring);

                    t.getChildren().add(t2);

                    if (!add(t2, subsubstring)) // (add () parameters)
                    {
                        throw new AlgebraicFormulaSyntaxException();
                    } else {
                        return true;
                    }

                }


                i++;

            }

        }

        return t.getChildren().size() > 0;
    }

    private void grammar() {
        t = new Tree();

    }


    public Double eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        //System.out.println(parametersValues.size());
        return root.eval();
    }

    public String toString() {
        String s = "Arbre algébrique\n" +
                "Racine: " + root.getClass() + root.toString();
        return s;
    }

    public void setParametersValues(Map<String, Double> parametersValues) {
        this.parametersValues = parametersValues;
    }

    public Map<String, Double> getParametersValues() {
        return parametersValues;
    }
}
