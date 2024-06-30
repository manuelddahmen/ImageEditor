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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InstructionBlock {
    static int deepth = 0;
    protected List<InstructionBlock> instructionList = new ArrayList<>();

    public InstructionBlock() {
    }

    public List<InstructionBlock> getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(List<InstructionBlock> instructionList) {
        this.instructionList = instructionList;
    }

    private String debugString(boolean isDebug, String tokenLangString) {
        if (tokenLangString != null && !tokenLangString.isBlank()) {
            return isDebug ? "{" + tokenLangString.trim() + "}" : tokenLangString;
        } else
            return (tokenLangString == null || tokenLangString.isEmpty()) ? "" : tokenLangString;
    }

    private String tabs() {
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < deepth; i++) {
            t.append("\t");
        }
        return t.toString();
    }

    private String tabs(int j) {
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < deepth + j; i++) {
            t.append("\t");
        }
        return t.toString();
    }

    public String toLangStringJava(boolean debug) {
        deepth++;
        StringBuilder stringBuilder = new StringBuilder();


        StringBuilder array1 = new StringBuilder();
        if (!getInstructionList().isEmpty() && !getClass().equals(Instruction.class)) {
            array1.append(debugString(debug, " {"));
            for (InstructionBlock instruction : getInstructionList()) {
                array1.append(tabs(1)).append(instruction.toLangStringJava(debug));
            }
            array1.append(debugString(debug, tabs() + "} \n"));
        }
        switch (getClass().getCanonicalName()) {
            case "one.empty3.library1.tree.ControlledInstructions.If" -> {
                ControlledInstructions.If anIf = (ControlledInstructions.If) this;
                StringBuilder array3 = new StringBuilder();


                if (!anIf.getInstructionsList().isEmpty()) {
                    array3.append(debugString(debug, tabs() + " {\n"));
                    for (InstructionBlock instruction : anIf.getInstructionsList()) {
                        array3.append(instruction.toLangStringJava(debug));
                    }
                    array3.append(debugString(debug, tabs() + "}\n"));
                }

                StringBuilder array2 = new StringBuilder();


                if (!anIf.instructionsElse.instructionList.isEmpty()) {
                    array2.append(debugString(debug, tabs() + " {\n"));
                    for (InstructionBlock instruction : anIf.instructionsElse.instructionList) {
                        array2.append(instruction.toLangStringJava(debug));
                    }
                    array2.append(debugString(debug, tabs() + "}\n"));
                }


                if (!anIf.instructionList.isEmpty()) {
                    stringBuilder.append(debugString(debug, "\n" + tabs() + "if"))
                            .append(debugString(debug, anIf.controlExpression))
                            .append(debugString(debug, array3.toString())).append("\n");
                    if (!anIf.instructionsElse.getInstructionList().isEmpty()) {
                        stringBuilder.append(debugString(debug, tabs() + "else")).append("\n")
                                .append(debugString(debug, array2.toString()));
                    }
                }
            }
            case "one.empty3.library1.tree.ControlledInstructions.While" -> {
                StringBuilder array2 = new StringBuilder();
                array2.append(debugString(debug, tabs() + "  {\n"));
                for (InstructionBlock instruction : this.instructionList) {
                    array2.append(instruction.toLangStringJava(debug)).append("\n");
                }
                array2.append(debugString(debug, tabs() + "}\n"));
                ControlledInstructions.While aWhile = (ControlledInstructions.While) this;
                stringBuilder.append(debugString(debug, "\n" + tabs() + "while"))
                        .append(debugString(debug, aWhile.controlExpression)).append("\n")
                        .append(debugString(debug, array2.toString())).append("\n");
            }
            case "one.empty3.library1.tree.ControlledInstructions.DoWhile" -> {
                stringBuilder.append(debugString(debug, "do \n")).append(debugString(debug, array1.toString())).append(debugString(debug, " while"))
                        .append(debugString(debug, ((ControlledInstructions.DoWhile) this).controlExpression))
                        .append(debugString(debug, "\n"));
            }
            case "one.empty3.library1.tree.ControlledInstructions.For" -> {
                ControlledInstructions.For aFor = (ControlledInstructions.For) this;
                if (aFor.forEachType) {
                    StringBuilder array3 = new StringBuilder();
                    Instruction firstForInstruction = aFor.getFirstForInstruction();
                    array3.append(tabs()).append("for (").append(firstForInstruction.getType()).append(" ").append(firstForInstruction.getExpression().getLeftHand())
                            .append(" : ").append(aFor.controlExpression).append(")\n").append(array1);
                    stringBuilder.append(array3);
                } else if (!aFor.forEachType) {
                    StringBuilder array3 = new StringBuilder();
                    Instruction third = aFor.getLoopInstruction();
                    Instruction first = aFor.getFirstForInstruction();
                    String second = aFor.getControlExpression();

                    String firstString = (first != null ? first.toLangStringJava(debug) : "").trim();
                    String secondString = second.trim();
                    String thirdString = (third != null ? third.toLangStringJava(debug) : "").trim();

                    firstString = firstString.substring(0, firstString.lastIndexOf(';')).trim();
                    secondString = secondString.trim();//.substring(0, secondString.lastIndexOf(';'));
                    thirdString = thirdString.substring(0, thirdString.lastIndexOf(';')).trim();

                    array3.append("\n" + tabs()).append("for ").append('(').append(firstString)
                            .append("; ").append(secondString)
                            .append("; ").append(thirdString)
                            .append(") ").append(array1);
                    stringBuilder.append(array3);
                }
            }
            case "one.empty3.library1.tree.Instruction" -> {
                Instruction instruction = (Instruction) this;
                String s = instruction.getType() != null ? debugString(debug, instruction.getType()) : "";
                if (!s.isBlank()) {
                    s = s + " ";
                }
                String s1 = instruction.getName() != null ? debugString(debug, instruction.getName()) : "";
                if (!s1.isBlank()) {
                    s1 = s1 + " ";
                }
                String s2 = instruction.getExpression() != null ? " " +
                        debugString(debug, instruction.getExpression().toString()) : "";
                stringBuilder.append("\n").append(tabs()).append(s).append(s1).append(s2).append(";\n");
            }
            case "one.empty3.library1.tree.InstructionBlock" -> {
                stringBuilder.append(array1);
            }
        }
        deepth--;
        return stringBuilder.toString();

    }
}
