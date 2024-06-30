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

package one.empty3.library1.tree;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import one.empty3.library.StructureMatrix;

public class ListInstructions {
    private HashMap<String, Double> currentParamsValues = new HashMap<>();
    private HashMap<String, String> currentParamsValuesVec = new HashMap<>();
    private HashMap<String, StructureMatrix<Double>> currentParamsValuesVecComputed = new HashMap<>();

    public String evaluate(String s) {
        return "";
    }

    public static class Instruction {
        private int id;
        private String leftHand;
        private String expression;
        StringAnalyzerJava1.TokenExpression2 tokenExpression2;

        public Instruction(int id, String leftHand, String expression) {
            this.id = id;
            this.leftHand = leftHand;
            setExpression(expression);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLeftHand() {
            return leftHand;
        }

        public void setLeftHand(String leftHand) {
            this.leftHand = leftHand;
        }

        public String getExpression() {

            return tokenExpression2.toString();
        }
        public String getExpressionAlgebraicTree() {

            return expression;
        }

        public void setExpression(String expression) {
            if (expression != null) {
                this.expression = expression;
                StringAnalyzerJava1 stringAnalyzerJava1 = new StringAnalyzerJava1();
                tokenExpression2 = stringAnalyzerJava1.new TokenExpression2();
                tokenExpression2.parse(expression, 0);
            }
        }

        public StringAnalyzerJava1.TokenExpression2 getExpressionTokenString() {
            return tokenExpression2;
        }

        public void setTokenExpression2(StringAnalyzerJava1.TokenExpression2 expression) {
            this.tokenExpression2 = expression;
        }

        @Override
        public String toString() {
            if (tokenExpression2 != null && tokenExpression2.isSuccessful()) {
                return tokenExpression2.toString();
            } else {
                return expression;
            }
        }
        public String toStringAlgebraicTree() {
            return expression;
        }
    }

    private ArrayList<Instruction> assignations;

    public ListInstructions() {

    }

    public ArrayList<Instruction> getAssignations() {
        return assignations;
    }

    public void setAssignations(ArrayList<Instruction> assignations) {
        this.assignations = assignations;
    }

    public void addInstructions(@NotNull String toString) {
        if (assignations == null)
            assignations = new ArrayList<>();


        if (toString != null && !toString.isEmpty()) {
            assignations = new ArrayList<>();

            String text = toString;

            String[] splitLines = text.split("\n");

            for (int i = 0; i < splitLines.length; i++) {

                String line = splitLines[i];

                String[] splitInstructionEquals = line.split("=");

                String value = null;
                String variable = null;
                if (splitInstructionEquals.length == 1) {
                    value = splitInstructionEquals[0].trim();
                }
                if (splitInstructionEquals.length == 2) {
                    variable = splitInstructionEquals[0].trim();
                    value = splitInstructionEquals[1].trim();
                }
                boolean assigned = false;
                if (splitInstructionEquals.length >= 1) {

                    if ((variable != null ? variable.length() : 0) > 0 && Character.isLetter(variable.toCharArray()[0])) {
                        int j = 0;
                        while (j < variable.length() && (Character.isLetterOrDigit(variable.toCharArray()[j])
                                || variable.toCharArray()[j] == '_')) {
                            j++;
                        }
                        if (j == variable.length()) {
                            assignations.add(new Instruction(i, variable, value));
                            assigned = true;
                        }
                    } else {
                        assignations.add(new Instruction(i, null, value));
                        assigned = true;
                    }
                }
                /*if (!assigned) {
                    if (splitInstructionEquals.length == 1) {
                        if (!value.startsWith("#")) {
                            assignations.add(new Instruction(i, "", splitInstructionEquals[0]));
                        }
                    }
                }*/
            }
        }
    }

    public List<String> runInstructions() {
        List<String> returnedCode = new ArrayList<>();
        Instruction[] instructions = new Instruction[assignations.size()];

        assignations.toArray(instructions);

        if (currentParamsValues == null)
            currentParamsValues = new HashMap<>();
        if (currentParamsValuesVec == null)
            currentParamsValuesVec = new HashMap<>();
        if (currentParamsValuesVecComputed == null)
            currentParamsValuesVecComputed = new HashMap<>();
        int i = 0;
        int countInstructions = 0;
        for (Instruction instruction : instructions) {
            String key = instruction.getLeftHand();
            String value = instruction.getExpressionAlgebraicTree();

            if (key != null)
                key = key.trim();
            if (value != null)
                value = value.trim();

            StructureMatrix<Double> resultVec = null;
            Double resultDouble = null;

            if (key != null) {
                try {
                    if (value.startsWith("#") ) {
                        i++;
                        continue;
                    }
                    AlgebraicTree tree = new AlgebraicTree(value);
                    tree.setParametersValues(currentParamsValues);
                    tree.setParametersValuesVec(currentParamsValuesVec);
                    tree.setParametersValuesVecComputed(currentParamsValuesVecComputed);


                    tree.construct();

                    resultVec = tree.eval();

                    if (resultVec != null) {
                        //Logger.getAnonymousLogger().log(Level.INFO, "key: " + key + " value: " + value + " computed: " + resultVec);
                        if (resultVec.getDim() == 1) {
                            currentParamsValuesVecComputed.put(key, resultVec);
                            currentParamsValuesVec.put(key, value);
                        } else if (resultVec.getDim() == 0) {
                            currentParamsValuesVecComputed.put(key, resultVec);
                            currentParamsValuesVec.put(key, value);
                            currentParamsValues.put(key, resultVec.getElem());
                        }
                    } else {
                        if (getCurrentParamsValuesVecComputed().get(key) != null)
                            resultVec = getCurrentParamsValuesVecComputed().get(key);
                        else
                            ;//throw new AlgebraicFormulaSyntaxException("Result was null");
                    }
                    //System.err.println("AlgebraicTree result : " + tree);
                } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException |
                         NullPointerException e) {
                    //e.printStackTrace();
                    //i++;
                    //continue;
                }
                String errors1 = "";
                boolean b = !value.startsWith("##") && !key.startsWith("##");
                if (b) {
                    errors1 += "\n" + (key.isBlank() ? "" : key + "=") + value;
                    countInstructions++;
                }
                if (resultVec != null && b && !value.isBlank() && !value.equals("null")) {
                    errors1 += String.format(Locale.getDefault(),
                            "\n#line : (%d)%s=%s ", countInstructions, value, resultVec.toStringLine());
                }
                if (!(errors1.isBlank() || errors1.equals("null"))) {
                    returnedCode.add(errors1);
                }
            }
            i++;
        }

        return returnedCode;
    }

    public HashMap<String, Double> getCurrentParamsValues() {
        return currentParamsValues;
    }

    public HashMap<String, String> getCurrentParamsValuesVec() {
        return currentParamsValuesVec;
    }

    public HashMap<String, StructureMatrix<Double>> getCurrentParamsValuesVecComputed() {
        return currentParamsValuesVecComputed;
    }

    public void setCurrentParamsValues(HashMap<String, Double> currentVars) {
        this.currentParamsValues = currentVars;
    }

    public void setCurrentParamsValuesVec(HashMap<String, String> currentVecs) {
        this.currentParamsValuesVec = currentVecs;
    }

    public void setCurrentParamsValuesVecComputed(HashMap<String, StructureMatrix<Double>> currentVecsComputed) {
        this.currentParamsValuesVecComputed = currentVecsComputed;
    }
}
