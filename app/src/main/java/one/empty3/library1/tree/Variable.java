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

public class Variable {
    private String name;
    private String classStr;
    private Class value;
    private String scope;

    public Variable() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getValue() {
        return value;
    }

    public void setValue(Class value) {
        this.value = value;
    }

    public void setScope(String choice) {
        this.scope = choice;
    }

    public String getClassStr() {
        return classStr;
    }

    public void setClassStr(String classStr) {
        this.classStr = classStr;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", class=" + classStr +
                ", scope='" + scope + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Variable v = new Variable();
        v.name = this.name;
        v.classStr = this.classStr;
        v.value = this.value;
        v.scope = this.scope;
        return v;
    }
}
