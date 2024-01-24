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

import one.empty3.library.Point2D;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class TreeNode {
    protected AlgebraicTree algebraicTree;
    protected Object[] objects;
    protected TreeNodeType type = null;
    protected ArrayList<TreeNode> children = new ArrayList<TreeNode>();
    protected TreeNode parent;
    protected String expressionString;
    private TreeNodeValue value;

    public TreeNode(AlgebraicTree algebraicTree, String expStr) {
        this.algebraicTree = algebraicTree;
        this.parent = null;
        if (expStr.trim().isEmpty()) expressionString = "0.0";
        this.expressionString = expStr;
    }

    /***
     * Base constructor for all TreeNodes
     * @param src parent (!=null)
     * @param objects [0] = expressionString
     * @param clazz type associated to this TreeNode
     */
    public TreeNode(TreeNode src, Object[] objects, TreeNodeType clazz) {
        this.parent = src;
        this.algebraicTree = src.algebraicTree;
        this.objects = objects;
        clazz.instantiate(objects);
        this.type = clazz;
        expressionString = (String) objects[0];
    }

    public Object getValue() {
        return value;
    }

    public void setValue(TreeNodeValue value) {
        this.value = value;
    }


    public StructureMatrix<Double> eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        /*if(this instanceof TreeNode) {
            return Double.parseDouble(((TreeNode) this).expressionString);
        }*/
        TreeNodeType cType = (getChildren().size() == 0) ? type : getChildren().get(0).type;

        StructureMatrix<Double> evalRes = null;
        if (cType instanceof VectorTreeNodeType) {
            evalRes = new StructureMatrix<>(1, Double.class);
        } else {
            evalRes = new StructureMatrix<>(0, Double.class);
            evalRes.setElem(0.0);
        }
        if (cType instanceof IdentTreeNodeType) {
            return getChildren().get(0).eval();
        } else if (cType instanceof EquationTreeNodeType) {
            //System.out.println(cType);
            //System.out.println(getChildren().get(0));
            switch (getChildren().get(0).getChildren().get(0).eval().getDim()) {
                case 0:
                    evalRes = new StructureMatrix<>(0, Double.class);
                    evalRes.setElem(getChildren().get(0).getChildren().get(0).eval().getElem());
                    break;
                case 1:
                    int sum = 0;
                    evalRes = new StructureMatrix<>(1, Double.class);
                    for (int j = 0; j < getChildren().get(0).getChildren().size(); j++) {
                        StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(j).eval();
                        for (int i = 0; i < eval.data1d.size(); i++) {
                            evalRes.setElem(eval.getElem(i), sum);
                            sum++;
                        }
                    }
                    break;
                default:
                    break;
            }
            switch (getChildren().get(0).getChildren().get(1).eval().getDim()) {
                case 0:
                    evalRes.setElem(getChildren().get(0).getChildren().get(1).eval().getElem());
                    break;
                case 1:
                    StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(1).eval();
                    evalRes = new StructureMatrix<>(1, Double.class);
                    int size = eval.data1d.size();
                    for (int i = 0; i < eval.data1d.size(); i++)
                        evalRes.setElem(eval.getElem(i), i);
                    break;
                default:
                    break;
            }
            ArrayList<TreeNode> childrenVars = getChildren().get(0).getChildren().get(0).getChildren();
            ArrayList<TreeNode> childrenValues = getChildren().get(0).getChildren().get(1).getChildren();
            for (int i = 0; i < childrenVars.size(); i++) {
                if (childrenVars.get(i).type.getClass().equals(VariableTreeNodeType.class)) {
                    String varName = childrenVars.get(i).expressionString;
                    if (varName != null) {
                        StructureMatrix<Double> put = algebraicTree.getParametersValuesVecComputed().put(varName, childrenValues.get(i).eval());
                    }
                }
            }
            return evalRes;//TO REVIEW
        } else if (cType instanceof DoubleTreeNodeType) {
            return cType.eval();
        } else if (cType instanceof VariableTreeNodeType) {
            try {
                return cType.eval();
            } catch (RuntimeException ex) {
                return getChildren().get(0).eval();//cType.eval();
            }
        } else if (cType instanceof PowerTreeNodeType) {
            StructureMatrix<Double> eval1 = getChildren().get(0).eval();
            StructureMatrix<Double> eval2 = getChildren().get(1).eval();
            int dim1, dim2;
            if (eval1.getDim() == 1 && eval2.getDim() == 1) {
                dim1 = eval1.data1d.size();
                dim2 = eval2.data1d.size();
                for (int i = 0; i < getChildren().size(); i++) {
                    if (dim1 == dim2 && dim1 == 3) {
                        Point3D point3D1 = new Point3D(eval1.data1d.get(0), eval1.data1d.get(1), eval1.data1d.get(2));
                        Point3D point3D2 = new Point3D(eval2.data1d.get(0), eval2.data1d.get(1), eval2.data1d.get(2));
                        Point3D point3Dres = point3D1.prodVect(point3D2);
                        evalRes = new StructureMatrix<>(1, Double.class);
                        evalRes.setElem(point3Dres.get(0), 0);
                        evalRes.setElem(point3Dres.get(1), 1);
                        evalRes.setElem(point3Dres.get(2), 2);

                        return evalRes;
                    } else if (dim1 == dim2 && dim1 == 2) {
                        Point2D point2D1 = new Point2D(eval1.data1d.get(0), eval1.data1d.get(1));
                        Point2D point2D2 = new Point2D(eval2.data1d.get(0), eval2.data1d.get(1));
                        Point2D point3Dres = new Point2D(eval1.data1d.get(0) * eval2.data1d.get(1), eval1.data1d.get(1) * eval2.data1d.get(0));
                        evalRes = new StructureMatrix<>(1, Double.class);
                        evalRes.setElem(point3Dres.get(0), 0);
                        evalRes.setElem(point3Dres.get(1), 1);

                        return evalRes;
                    }

                }
            } else if (evalRes.getDim() == 0) {
                return evalRes.setElem(Math.pow((Double) getChildren().get(0).eval().getElem(), (Double) getChildren().get(1).eval().getElem()));
            }
        } else if (cType instanceof FactorTreeNodeType) {
            if (getChildren().size() == 1) {
                evalRes = getChildren().get(0).eval();///SIGN
                if (evalRes.getDim() == 1) {
                    return evalRes;
                } else if (evalRes.getDim() == 0) {
                    double v = ((Double) getChildren().get(0).eval().getElem()) * getChildren().get(0).type.getSign1();
                    return evalRes.setElem(v);
                }
                return evalRes;
            } else if (getChildren().size() > 1) {
                double dot = 1.0;
                TreeNode treeNode1 = getChildren().get(0);
                int dim = treeNode1.eval().getDim();
                if (dim == 1) {
                    evalRes = new StructureMatrix<>(1, Double.class);
                    evalRes.setElem(1.0, 0);
                } else if (dim == 0) {
                    evalRes = new StructureMatrix<>(0, Double.class);
                    evalRes.setElem(1.0);
                }
                TreeNode treeNode = getChildren().get(0);
                evalRes = initVoid(treeNode, 1.0);
                for (int i = 0; i < getChildren().size(); i++) {
                    treeNode = getChildren().get(i);
                    StructureMatrix<Double> treeNodeEval = treeNode.eval();
                    double op1;
                    Double mult = 1.0;
                    int leftOp = evalRes.getDim() == 1 ? evalRes.data1d.size() : 0;
                    int rightOp = treeNodeEval.getDim() == 1 ? treeNodeEval.data1d.size() : 0;
                    int finalSize = Math.max(leftOp, rightOp);
                    double argMultiIndices = (double) leftOp / rightOp;

                    StructureMatrix<Double> argLeft = evalRes;
                    StructureMatrix<Double> argRight = treeNodeEval;
                    if (leftOp == 0) {
                        StructureMatrix<Double> e2 = new StructureMatrix<>(1, Double.class);
                        e2.setElem(argLeft.getDim() == 1 ? argLeft.getElem(0) : argLeft.getElem(), 0);
                        evalRes = e2;
                        leftOp = 1;
                    }
                    if (rightOp == 0) {
                        StructureMatrix<Double> e2 = new StructureMatrix<>(1, Double.class);
                        e2.setElem(argRight.getDim() == 1 ? argRight.getElem(0) : argRight.getElem(), 0);
                        treeNodeEval = e2;
                        rightOp = 1;
                    }
                    finalSize = Math.max(leftOp, rightOp);
                    argMultiIndices = (double) (leftOp / rightOp);
                    for (int j = 0; j < finalSize; j++) {
                        double argJ0 = 1.0;
                        double argJ1 = 1.0;
                        if (argLeft.getDim() == 0) {
                            argJ0 = argLeft.getElem() == null ? 1.0 : argLeft.getElem();
                        } else if (argLeft.getDim() == 1) {
                            argJ0 = argLeft.getElem((int) ((rightOp > leftOp) ? (j * argMultiIndices) : j));
                        }

                        if (argRight.getDim() == 0) {
                            argJ1 = argRight.getElem() == null ? 1.0 : argRight.getElem();
                        } else if (argRight.getDim() == 1) {
                            argJ1 = argRight.getElem((int) ((rightOp > leftOp) ? (j) : (j / argMultiIndices)));
                        }


                        if (treeNode.type instanceof FactorTreeNodeType) {
                            op1 = ((FactorTreeNodeType) treeNode.type).getSignFactor();
                        } else if (treeNode.type instanceof SignTreeNodeType) {
                            op1 = ((SignTreeNodeType) treeNode.type).getSign();
                        } else {
                            op1 = treeNode.type.sign1;
                        }
                        if (op1 == 1) {
                            evalRes.setElem(argJ0 * argJ1, j);
                        } else {
                            evalRes.setElem(argJ0 / argJ1, j);
                        }
                    }
                    if (evalRes.getDim() == 1 && evalRes.getData1d().size() <= 1) {
                        StructureMatrix<Double> evalRes1 = new StructureMatrix<Double>(0, Double.class);
                        evalRes1.setElem(evalRes.getElem(0));
                        evalRes = evalRes1;
                    }
                }
                return evalRes;

            }
        } else if (cType instanceof TermTreeNodeType) {
            /*
            if (getChildren().size() == 1) {

                evalRes = getChildren().get(0).eval();///SIGN
                if (evalRes.getDim() == 1) {
                    return evalRes;
                } else if (evalRes.getDim() == 0) {
                    double v = ((Double) getChildren().get(0).eval().getElem()) * getChildren().get(0).type.getSign1();
                    return evalRes.setElem(v);
                }
                return evalRes;
            } else if (getChildren().size() > 1) {
                TreeNode treeNode1 = getChildren().get(0);
                int dim = treeNode1.eval().getDim();
                if (dim == 1) {
                    evalRes = new StructureMatrix<>(1, Double.class);
                    evalRes.setElem(0.0, 0);
                } else if (dim == 0) {
                    evalRes = new StructureMatrix<>(0, Double.class);
                    evalRes.setElem(0.0);
                }
                evalRes = initVoid(getChildren().get(0), 0.0);
                TreeNode treeNode = getChildren().get(0);
                for (int i = 0; i < getChildren().size(); i++) {
                    treeNode = getChildren().get(i);
                    StructureMatrix<Double> treeNodeEval = treeNode.eval();
                    double op1 = 1.0;
                    int leftOp = evalRes.getDim() == 1 ? evalRes.data1d.size() : 0;
                    int rightOp = treeNodeEval.getDim() == 1 ? treeNodeEval.data1d.size() : 0;

                    int finalSize = Math.max(leftOp, rightOp);
                    double argMultiIndices = (double) leftOp / rightOp;
                    StructureMatrix<Double> argLeft = evalRes;
                    StructureMatrix<Double> argRight = treeNodeEval;
                    if (leftOp == 0) {
                        StructureMatrix<Double> e2 = new StructureMatrix<>(1, Double.class);
                        e2.setElem(argLeft.getDim() == 1 ? (argLeft.getData1d().isEmpty() ? 0.0 : argLeft.getElem(0)) : argLeft.getElem(), 0);
                        evalRes = e2;
                        leftOp = 1;
                    }
                    if (rightOp == 0) {
                        StructureMatrix<Double> e2 = new StructureMatrix<>(1, Double.class);
                        e2.setElem(argRight.getDim() == 1 ? (argRight.getData1d().isEmpty() ? 0.0 : argLeft.getElem(0)) : argRight.getElem(), 0);
                        rightOp = 1;
                    }
                    finalSize = Math.max(leftOp, rightOp);
                    argMultiIndices = (((double) leftOp) / rightOp);
                    for (int j = 0; j < finalSize; j++) {
                        double argJ0 = 0.0;
                        double argJ1 = 0.0;
                        if (argLeft.getDim() == 0) {
                            argJ0 = argLeft.getElem() == null ? 0.0 : argLeft.getElem();
                        } else if (argLeft.getDim() == 1) {
                            int i1 = (int) ((rightOp > leftOp) ? (j * argMultiIndices) : j);
                            if (i1 < argLeft.getData1d().size()) argJ0 = argLeft.getElem(i1);
                            else argJ0 = 0.0;
                        }

                        if (argRight.getDim() == 0) {
                            argJ1 = argRight.getElem() == null ? 0.0 : argRight.getElem();
                        } else if (argRight.getDim() == 1) {
                            int i1 = (int) ((rightOp > leftOp) ? (j * argMultiIndices) : (j));
                            if (i1 < argRight.getData1d().size()) argJ1 = argRight.getElem(i1);
                            else argJ1 = 0.0;
                        }

                        if (treeNode.type instanceof TermTreeNodeType) {
                            op1 = ((TermTreeNodeType) treeNode.type).getSign1();
                        } else if (treeNode.type != null) {
                            op1 = treeNode.type.getSign1();
                        } else op1 = 1.0;
                        if (op1 == 0)
                            op1 = 1.0;
                        if (op1 == 1) {
                            evalRes.setElem(argJ0 + op1 * argJ1, j);
                        } else {
                            evalRes.setElem(argJ0 + op1 * argJ1, j);
                        }

                        if (op1 == 1) {
                            evalRes.setElem(argJ0 + op1 * argJ1, j);
                        } else {
                            evalRes.setElem(argJ0 + op1 * argJ1, j);
                        }
                    }
                    if (evalRes.getDim() == 1 && evalRes.getData1d().size() <= 1) {
                        StructureMatrix<Double> evalRes1 = new StructureMatrix<Double>(0, Double.class);
                        evalRes1.setElem(evalRes.getData1d().size() > 0 ? evalRes.getElem(0) : evalRes.getElem());
                        evalRes = evalRes1;
                    }
                }

                return evalRes;
            }
            */
            if (getChildren().size() == 1) {
                return evalRes.setElem(((Double) getChildren().get(0).eval().getElem()) * getChildren().get(0).type.getSign1());
            }
            double sum = 0.0;
            int dimChild0 = getChildren().get(0).eval().getDim();
            if (dimChild0 == 0) {
                evalRes = new StructureMatrix<>(0, Double.class);
            } else {
                evalRes = new StructureMatrix<>(1, Double.class);
                for (int j = 0; j < evalRes.data1d.size(); j++)
                    evalRes.setElem(0.0, j);
            }
            for (int i = 0; i < getChildren().size(); i++) {
                TreeNode treeNode1 = getChildren().get(i);
                double op1 = treeNode1.type.getSign1();
                StructureMatrix<Double> eval = treeNode1.eval();
                if (treeNode1.type instanceof TermTreeNodeType) {
                    if (eval.getDim() == 1) {
                        for (int j = 0; j < eval.data1d.size(); j++) {
                            double e = 0.0;
                            if (evalRes.data1d != null && j < evalRes.data1d.size()) {
                                e = evalRes.getElem(j);
                            } else {
                                evalRes.setElem(e, j);
                            }
                            evalRes.setElem(e + eval.getElem(j) * op1, j);
                        }
                        System.err.println("In TreeNode.eval #TermTreeNodeType");
                    } else if (eval.getDim() == 0) {
                        sum = op1 * (Double) ((eval.getElem() == null) ? 0.0 : eval.getElem());
                        double evalSum = (evalRes.getElem() == null) ? 0.0 : evalRes.getElem();
                        evalRes.setElem(sum + evalSum);
                    }
                }
            }
            return evalRes;
        } else if (cType instanceof TreeTreeNodeType) {
            if (!getChildren().isEmpty()) {
                if (!getChildren().get(0).getChildren().isEmpty() && getChildren().get(0).getChildren().get(0).type instanceof VectorTreeNodeType) {
                    evalRes = new StructureMatrix<>(1, Double.class);
                    switch (evalRes.getDim()) {
                        case 0:
                            for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
                                StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(i).eval();
                                evalRes.setElem(eval.getElem(i), i);
                            }
                            break;
                        case 1:
                            int k = 0;
                            for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
                                StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(i).eval();
                                if (eval.getDim() == 1) {
                                    for (int j = 0; j < eval.data1d.size(); j++) {
                                        evalRes.setElem(eval.getElem(j), k++);
                                    }
                                } else if (eval.getDim() == 0) {
                                    evalRes.setElem(eval.getElem(), k++);
                                }
                            }
                            break;
                    }
                    return evalRes;
                } else {
                    StructureMatrix<Double> eval = getChildren().get(0).eval();
                    if (eval.getDim() == 1) {
                        for (int i = 0; i < eval.data1d.size(); i++) {
                            evalRes.setElem(eval.getElem(i), i);
                        }
                    } else if (eval.getDim() == 0) {
                        evalRes.setElem(eval.getElem());
                    }
                }
                return evalRes;
            }
            return null;
        } else if (cType instanceof SignTreeNodeType) {
            double s1 = ((SignTreeNodeType) cType).getSign();
            StructureMatrix<Double> eval;
            if (!getChildren().isEmpty()) {
                eval = getChildren().get(0).eval();
            } else {
                eval = evalRes.setElem(0.0);
            }
            if (eval.getDim() == 1) {
                for (int i = 0; i < eval.data1d.size(); i++) {
                    evalRes.setElem(eval.getElem(i) * s1, i);
                }
            } else if (eval.getDim() == 0) {
                evalRes.setElem(s1 * eval.getElem());
            }
            return evalRes;
        } else if (cType instanceof VectorTreeNodeType) {
            evalRes = new StructureMatrix<>(1, Double.class);
            /*
            int dim = 0;
            for (int i = 0; i <getChildren().get(0).getChildren().size() ; i++) {
                if(dim==0) dim = 1;
                if(getChildren().get(0).getChildren().get(i).type.equals(VectorTreeNode.class)) {
                    dim = 2;
                }
            }
            if(dim==2) evalRes = new StructureMatrix<>(2, Double.class);
            for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
                StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(i).eval();///!!!
                evalRes.setElem(eval.getElem(i), i);
            }
             */
            for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
                StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(i).eval();///!!!
                evalRes.setElem(eval.getElem(i), i);
            }
            return evalRes;
        }

        StructureMatrix<Double> eval = new StructureMatrix<>(0, Double.class);

        if (type != null) {
            eval = type.eval();
        } else if (!getChildren().isEmpty()) {
            eval = getChildren().get(0).eval();
        }

        return eval == null ? evalRes.setElem(0.0) : eval;

    }

    private StructureMatrix<Double> initVoid(TreeNode treeNode, double val) throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        StructureMatrix<Double> evalRes = treeNode.eval();
        switch (evalRes.getDim()) {
            case 0 -> {
                return evalRes.setElem(val);
            }
            case 1 -> {
                for (int i = 0; i < evalRes.getData1d().size(); i++) {
                    evalRes.setElem(val, i);
                }
                return evalRes;
            }
        }
        return null;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getExpressionString() {
        return expressionString;
    }

    public void setExpressionString(String expressionString) {
        this.expressionString = expressionString;
    }


    @NotNull
    public String toString() {
        String s = "TreeNode " + this.getClass().getSimpleName() + "\nExpression string: " + expressionString + (type == null ? "Type null" : "Type: " + type.getClass() + "\n " + type.toString()) + "\nChildren: " + getChildren().size() + "\n";
        int i = 0;
        for (TreeNode t : getChildren()) {
            s += "Child (" + i++ + ") : " + t.toString() + "\n";
        }
        return s + "\n";
    }
}
