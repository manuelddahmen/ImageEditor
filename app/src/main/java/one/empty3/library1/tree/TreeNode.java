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

import androidx.annotation.NonNull;

import java.util.ArrayList;


import javaAnd.awt.Point;
import one.empty3.library.Point2D;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class TreeNode {
    protected Object[] objects;
    protected TreeNodeType type = null;
    private TreeNodeValue value;
    protected ArrayList<TreeNode> children = new ArrayList<TreeNode>();
    protected TreeNode parent;
    protected String expressionString;

    public TreeNode(String expStr) {
        this.parent = null;
        if (expStr.trim().isEmpty())
            expressionString = "0.0";
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
            System.out.println("cType Ident=" + getChildren().size());
            System.out.println("cType Ident=" + getChildren().get(0).eval());
            return getChildren().get(0).eval();
        } else if (cType instanceof EquationTreeNodeType) {
            switch (getChildren().get(0).eval().getDim()) {
                case 0:
                    return evalRes.setElem(getChildren().get(0).eval().getElem());
                case 1:
                    StructureMatrix<Double> eval = getChildren().get(0).eval();
                    for (int i = 0; i < eval.data1d.size(); i++)
                        evalRes.setElem(eval.getElem(i), i);
                    return evalRes;
                default:
                    break;
            }
            switch (getChildren().get(1).eval().getDim()) {
                case 0:
                    evalRes.setElem(getChildren().get(1).eval().getElem());
                    break;
                case 1:
                    int size = evalRes.data1d.size();
                    StructureMatrix<Double> eval = getChildren().get(1).eval();
                    for (int i = 0; i < eval.data1d.size(); i++)
                        evalRes.setElem(eval.getElem(i), i + size);
                    break;
                default:
                    break;
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
                for(int i=0; i<getChildren().size(); i++) {
                    if(dim1==dim2 && dim1==3) {
                        Point3D point3D1 = new Point3D(eval1.data1d.get(0), eval1.data1d.get(1), eval1.data1d.get(2));
                        Point3D point3D2 = new Point3D(eval2.data1d.get(0), eval2.data1d.get(1), eval2.data1d.get(2));
                        Point3D point3Dres = point3D1.prodVect(point3D2);
                        evalRes = new StructureMatrix<>(1, Double.class);
                        evalRes.setElem(point3Dres.get(0), 0);
                        evalRes.setElem(point3Dres.get(1), 1);
                        evalRes.setElem(point3Dres.get(2), 2);

                        return evalRes;
                    } else if(dim1==dim2 && dim1==2) {
                        Point2D point2D1 = new Point2D(eval1.data1d.get(0), eval1.data1d.get(1));
                        Point2D point2D2 = new Point2D(eval2.data1d.get(0), eval2.data1d.get(1));
                        Point2D point3Dres = new Point2D(eval1.data1d.get(0)*eval2.data1d.get(1), eval1.data1d.get(1)*eval2.data1d.get(0));
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
                if (dim == 1)
                    evalRes = new StructureMatrix<>(1, Double.class);
                else if(dim==0){
                    evalRes = new StructureMatrix<>(0, Double.class);
                    evalRes.setElem(1.0);
                }
                int dim0 = dim;
                for (int i = 0; i < getChildren().size(); i++) {
                    TreeNode treeNode = getChildren().get(i);
                    StructureMatrix<Double> treeNodeEval = treeNode.eval();
                    dim = treeNodeEval.getDim();
                    double op1;
                    Double mult = 1.0;
                    if (treeNodeEval.getDim() == 1) {
                        if(dim0==0 && dim==1) {
                            mult = getChildren().get(0).eval().getElem();
                            dim0 = -1;
                        }
                        if(evalRes.data1d.size()!=treeNodeEval.data1d.size()) {
                            int leftOp = evalRes.data1d.size();
                            int rightOp = treeNodeEval.data1d.size();

                        }
                        for (int j = 0; j < treeNodeEval.data1d.size(); j++) {
                            if (treeNode.type instanceof FactorTreeNodeType) {
                                double e = 1.0;
                                if (evalRes.data1d != null && j < evalRes.data1d.size()) {
                                    e = evalRes.getElem(j);
                                }
                                op1 = ((FactorTreeNodeType) treeNode.type).getSignFactor();
                                if (op1 == 1) {
                                    dot = ((Double) treeNode.eval().getElem(j)) * e;
                                    evalRes.setElem(dot*mult, j);
                                } else {
                                    dot = 1. / ((Double) (Double) treeNode.eval().getElem(j)) * e;///treeNode.type.getSign1()) *
                                    evalRes.setElem(dot*mult, j);
                                }
                            }
                        }
                    } else if (treeNodeEval.getDim() == 0) {
                        if (treeNode.type instanceof FactorTreeNodeType) {
                            op1 = ((FactorTreeNodeType) treeNode.type).getSignFactor();
                            if (op1 == 1) {
                                dot = ((Double) treeNodeEval.getElem()*treeNode.type.getSign1());
                                evalRes.setElem(dot * evalRes.getElem());
                            } else {
                                dot = 1. / ((Double) treeNodeEval.getElem()*treeNode.type.getSign1());///treeNode.type.getSign1()) *
                                evalRes.setElem(dot * evalRes.getElem());
                            }
                        }
                    }
                    dim0 = dim;
                }
            }
            return evalRes;
        } else if (cType instanceof TermTreeNodeType) {
            if (getChildren().size() == 1) {
                return evalRes.setElem(((Double) getChildren().get(0).eval().getElem()) * getChildren().get(0).type.getSign1());
            }
            double sum = 0.0;
            int dimChild0 = getChildren().get(0).eval().getDim();
            if (dimChild0 == 0) {
                evalRes = new StructureMatrix<>(0, Double.class);
            } else {
                evalRes = new StructureMatrix<>(1, Double.class);
                for(int j=0; j<evalRes.data1d.size(); j++)
                    evalRes.setElem(0.0, j);
            }
            for (int i = 0; i < getChildren().size(); i++) {
                TreeNode treeNode1 = getChildren().get(i);
                double op1 = treeNode1.type.getSign1();
                StructureMatrix<Double> eval = treeNode1.eval();
                if(treeNode1.type instanceof TermTreeNodeType) {
                    if (eval.getDim() == 1) {
                        for (int j = 0; j < eval.data1d.size(); j++) {
                            double e = 0.0;
                            if (evalRes.data1d != null && j < evalRes.data1d.size()) {
                                e = evalRes.getElem(j);
                            } else {
                                evalRes.setElem(e, j);
                            }
                            evalRes.setElem(e + eval.getElem(j)*op1, j);
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
                if (!getChildren().get(0).getChildren().isEmpty() &&
                        getChildren().get(0).getChildren().get(0).type instanceof VectorTreeNodeType) {
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


    @NonNull
    public String toString() {
        String s = "TreeNode " + this.getClass().getSimpleName() +
                "\nExpression string: " + expressionString +
                (type == null ? "Type null" :
                        "Type: " + type.getClass() + "\n " + type.toString()) +
                "\nChildren: " + getChildren().size() + "\n";
        int i = 0;
        for (TreeNode t :
                getChildren()) {
            s += "Child (" + i++ + ") : " + t.toString() + "\n";
        }
        return s + "\n";
    }
}
