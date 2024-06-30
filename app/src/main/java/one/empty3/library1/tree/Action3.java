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

/***
 * The Action class represents an action that can be performed in a parsing process.
 * It is an abstract class that must be subclassed to implement the action logic.
 */
public class Action3 {
    public static final int NONE = 0;
    public static final int ON_NEXT_TOKEN_CALL = 1;
    public static final int ON_RETURNS_TRUE_NEXT_TOKEN = 2;
    protected int on = ON_NEXT_TOKEN_CALL;
    protected StringAnalyzer3.Token token;

    /**
     * Retrieves the token associated with the current action.
     *
     * @return the token associated with the current action
     */
    public StringAnalyzer3.Token getToken() {
        return token;
    }

    /**
     * This class represents an action that can be performed in a parsing process.
     * It is an abstract class that must be subclassed to implement the action logic.
     */
    public Action3(StringAnalyzer3.Token token) {
        this.token = token;
        token.setAction(this);

    }

    public void setToken(StringAnalyzer3.Token token) {
        this.token = token;
        token.setAction(this);
    }

    /**
     * Retrieves the value of the 'on' property.
     * (time of the call of action method)
     *
     * @return the value of the 'on' property
     */
    public int getOn() {
        return on;
    }

    /**
     * Sets the value for the 'on' property of the Action class.
     * (time of the call of action method)
     *
     * @param on the value to set for the 'on' property
     */
    public void setOn(int on) {
        this.on = on;
    }

    /**
     * Executes the action associated with the token.
     *
     * @return true if the action was executed successfully, false otherwise
     */
    public boolean action() {
        return false;
    }

    public void commitTokenVersion() {
        try {
            token.setConstruct((StringAnalyzer3.Construct) token.clones().get(token.clones().size()-1).clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void revertOneVersionAhead() {
        StringAnalyzer3.Construct last = token.clones().get(token.clones().size()-1);
        token.clones().remove(last);
        token.setConstruct(token.clones().get(token.clones().size()-1));
    }

    public void revertTokenVersionFirst() {
        StringAnalyzer3.Construct first = token.clones().get(token.clones().size()-1);
        token.clones().clear();
        token.setConstruct(first);
    }

    public void cloneTokenVersion() {
        try {
            token.clones().add((StringAnalyzer3.Construct) token.clones().get(token.clones().size()-1).clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
