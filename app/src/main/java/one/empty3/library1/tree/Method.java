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
import java.util.Objects;
import java.util.function.Consumer;

public class Method {
    private String name;
    private Variable ofClass = new Variable();
    private List<Variable> parameterList = new ArrayList<>();
    private List<Variable> variableList = new ArrayList<>();
    private InstructionBlock instructions = new InstructionBlock();
    private String scope;

    public Method() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Variable getOfClass() {
        return ofClass;
    }

    public void setOfClass(Variable ofClass) {
        this.ofClass = ofClass;
    }

    public List<Variable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<Variable> variableList) {
        this.variableList = variableList;
    }

    public InstructionBlock getInstructions() {
        return instructions;
    }

    public void setInstructions(InstructionBlock instructions) {
        this.instructions = instructions;
    }

    public List<Variable> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Variable> parameterList) {
        this.parameterList = parameterList;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Method method = (Method) o;

        if (!Objects.equals(name, method.name)) return false;
        if (!Objects.equals(ofClass, method.ofClass)) return false;
        if (!Objects.equals(variableList, method.variableList))
            return false;
        return Objects.equals(instructions, method.instructions);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (ofClass != null ? ofClass.hashCode() : 0);
        result = 31 * result + (variableList != null ? variableList.hashCode() : 0);
        result = 31 * result + (instructions != null ? instructions.hashCode() : 0);
        return result;
    }

    public void setScope(String choice) {
        this.scope = choice;
    }

    @Override
    public String toString() {
        String[] string = {""};

        String[] parameterListStr = {""};
        parameterList.forEach(new Consumer<Variable>() {
            @Override
            public void accept(Variable variable) {
                parameterListStr[0] += variable.toString();
            }
        });
        String[] variableListStr = {""};
        variableList.forEach(new Consumer<Variable>() {
            @Override
            public void accept(Variable variable) {
                variableListStr[0] += variable.toString();
            }
        });
        String[] instructionsListStr = {""};
        instructions.instructionList.forEach(instructionBlock -> instructionsListStr[0] += instructionBlock.toString());
        string[0] = "Method{" +
                "name='" + name + '\'' +
                ", ofClass=" + ofClass +
                ", parameterList=[" + parameterListStr[0] +
                "], variableList=[" + variableListStr[0] +
                "], instructions=[" + instructionsListStr[0] +
                "], scope='" + scope + '\'' +
                '}';
        return string[0];
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Method method = new Method();
        method.parameterList = parameterList;
        method.variableList = variableList;
        method.instructions = instructions;
        method.scope = scope;
        return method;
    }
}
