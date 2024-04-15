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

import one.empty3.library.StructureMatrix;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StringAnalyzer2 {
    protected HashMap<Integer, Token> definitions = new HashMap<>();
    private HashMap<String, Class> classes;
    private int index = 0;

    @NotNull
    protected Construct getConstruct() {
        return construct;
    }

    public abstract class Token {
        protected Action action;
        protected Class aClass;
        protected Method method;
        protected Variable variable;
        private boolean successful = false;
        private final StructureMatrix<Token> nextTokens = new StructureMatrix<>(1, Token.class);

        public Token() {
        }

        public Token addToken(Token token) {
            index++;
            definitions.put(index, token);
            this.nextTokens.setElem(token, this.nextTokens.getData1d().size());
            return this;
        }

        public int nextToken(String input, int position) {
            if (!nextTokens.getData1d().isEmpty()) {
                return nextTokens.getData1d().get(0).parse(input, position);
            }
            return position;
        }

        public Token nextToken() {
            if (!nextTokens.getData1d().isEmpty()) {
                return nextTokens.getData1d().get(0);
            }
            return null;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public int skipBlanks(String input, int position) {
            boolean passed = false;
            int position1 = position;
            while (position1 < input.length() && (Character.isSpaceChar(input.charAt(position1)) || Character.isWhitespace(input.charAt(position1)))) {
                position1++;
                passed = true;
            }
            int position2 = passed ? position1 : position;//(input.length() > position2 ? input.substring(position2) : "")+
            System.out.printf("\n\n\nClasse\t" + getClass() + "\nPosition\t" + position2 + "\nReste <<<<\n" +
                    (input.length() > position2 ? input.substring(position2) : "")
                    + "\n>>>>\ntoString()\n" + this + "\n\n");
            return position2;
        }

        public int parse(String input, int position) {
            position = skipBlanks(input, position);
            return position;
        }

        protected boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
            if (successful && action != null)
                action();
            if (!successful) {
                System.err.println("<<<Error : " + toString() + ">>>");
            }
        }

        public Action getAction() {
            return action;
        }

        public void action() {
            if (action != null) getAction().action();
        }

        protected int processNext(String input, int position) {
            if (nextToken() != null) {
                int nextToken = nextToken(input, position);
                if (nextToken().isSuccessful()) {
                    setSuccessful(true);
                    return nextToken;
                } else {
                    setSuccessful(false);
                    return position;
                }
            } else {
                setSuccessful(true);
                return position;
            }
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + successful +
                    "}\n";
        }

        public StructureMatrix<Token> getNextToken() {
            return nextTokens;
        }
    }

    public abstract class Action {
        protected final Token token;

        public Token getToken() {
            return token;
        }

        public Action(Token token) {
            this.token = token;
            token.action = this;

        }

        public abstract boolean action();
    }

    class TokenPrivacyModifier extends TokenChoiceStringMandatory {

        public TokenPrivacyModifier() {
            super(new String[]{"private", "public", "protected"/*, ""*/});

        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input.substring(position), position) + position;

            for (String searched : names) {
                if (input.substring(position).startsWith(searched)) {
                    setSuccessful(true);
                    return position + searched.length();
                }
            }
            return 0;
        }
    }

    /***
     * Il faut choisir une des strings c'est obligatoire.
     */
    class TokenChoiceStringMandatory extends Token {
        protected final String[] names;
        protected String choice = "";

        public TokenChoiceStringMandatory(String[] values) {
            super();
            this.names = values;
        }

        @Override
        public int parse(String input, int position) {
            int position1 = super.parse(input, position);
            int position2 = position1;
            boolean success = false;
            for (int i = 0; i < names.length; i++) {
                String s = names[i];
                if (position2 < input.length() && input.substring(position2).startsWith(s)) {
                    this.choice = s;
                    position2 += s.length();
                    success = true;
                    break;
                }
            }
            if (success) {
                return processNext(input, position2);
            } else {
                return processNext(input, position1);
//                setSuccessful(false);
//                return position1;
            }
        }

        public String getChoice() {
            return choice;
        }

        @Override
        public String toString() {
            return "TokenChoiceStringMandatory{" +
                    "names=" + Arrays.toString(names) +
                    ", choice='" + choice + '\'' +
                    '}';
        }
    }

    class TokenClassScope extends TokenChoiceStringMandatory {
        public TokenClassScope() {
            super(new String[]{"public", "private", "package", "protected"/*, ""*/});
        }
    }

    class TokenConstantModifier extends TokenChoiceStringMandatory {
        public TokenConstantModifier() {
            super(new String[]{"final"/*, ""*/});
        }

    }


    class TokenCodeFile extends Token {
        public TokenCodeFile() {
            super();
            aClass = new Class();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            return processNext(input, position);

        }
    }

    class TokenChoiceInclusive extends Token {
        protected List<Token> choices;

        public TokenChoiceInclusive(Token... choices) {
            super();
            this.choices = Arrays.stream(choices).toList();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input.substring(position), position) + position;
            for (Token token : choices) {
                int position1 = position;
                position1 = token.parse(input.substring(position1), position);
                if (token.isSuccessful()) {
                    setSuccessful(true);
                    return position1;
                }
            }
            setSuccessful(false);
            return position;
        }

        @Override
        public String toString() {
            return "TokenChoiceInclusive{" +
                    "choices=" + choices +
                    '}';
        }
    }


    /***
     * Tous les tokens choisis et aucun autre.
     */
    class TokenChoiceExclusive extends Token {
        protected final Token[] choices;

        public TokenChoiceExclusive(Token... choices) {
            super();
            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input.substring(position), position) + position;
            int position1 = position;
            for (Token token : choices) {
                position1 = position;
                position1 = token.parse(input.substring(position1), position);
                if (!token.isSuccessful()) {
                    setSuccessful(false);
                    return position;
                }
            }
            setSuccessful(true);
            return position;
        }

        @Override
        public String toString() {
            return "TokenChoiceExclusive{" +
                    "choices=" + Arrays.toString(choices) +
                    '}';
        }
    }

    class TokenPackage extends TokenString {
        public TokenPackage() {
            super("package");
        }
    }

    class TokenQualifiedName extends TokenName {
        public TokenQualifiedName() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            position = super.skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            boolean passed = false;
            while (i < input.charAt(i) && (Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                passed = true;
                i++;
            }
            if (passed) {
                if (!input.substring(position1, i).isEmpty()) {
                    setName(input.substring(position1, i));
                    return processNext(input, i);

                }
            }
            setSuccessful(false);
            return position1;
        }
    }

    class TokenClassKeyword extends TokenString {

        public TokenClassKeyword() {
            super("class");
        }

    }

    class TokenString extends Token {
        protected String name;

        public TokenString(String name) {
            super();
            this.name = name;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            if (position < input.length() && input.substring(position).startsWith(name)) {
                int position1 = position + name.length();
                if (!getNextToken().getData1d().isEmpty()) {
                    int position2 = nextToken(input, position1);
                    if (nextToken().isSuccessful()) {
                        setSuccessful(true);
                        return position2;
                    } else {
                        setSuccessful(false);
                        return position1;
                    }
                } else {
                    setSuccessful(true);
                    return position1;
                }
            } else {
                setSuccessful(false);
                return position;
            }
        }

        @Override
        public String toString() {
            return getClass().getName() + "<=TokenString{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    class TokenOpenBracket extends TokenString {
        public TokenOpenBracket() {
            super("{");

        }

    }

    class TokenComa extends TokenString {
        public TokenComa() {
            super(",");
        }

    }

    class TokenCloseBracket extends TokenString {
        public TokenCloseBracket() {
            super("}");

        }

    }

    class TokenOpenParenthesized extends TokenString {
        public TokenOpenParenthesized() {
            super("(");
        }

    }

    class TokenCloseParenthesized extends TokenString {

        public TokenCloseParenthesized() {
            super(")");
        }

    }

    class MultiTokenOptional extends Token {

        private final Token[] choices;

        public MultiTokenOptional(Token... choices) {
            super();
            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            boolean allNotOk = false;
            int position1 = position;
            int position2 = position1;
            while (!allNotOk) {
                allNotOk = true;
                for (Token token : choices) {
                    position2 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        position2 = position1;
                    } else {
                        allNotOk = false;
                        position1 = position2;
                    }
                }
            }
            setSuccessful(true);
            return position1;
        }
    }

    class SingleTokenOptional extends Token {

        private final Token choice;

        public SingleTokenOptional(Token choice) {
            super();
            this.choice = choice;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            int position1 = position;
            int position2 = choice.parse(input, position1);
            return processNext(input, position2);
        }
    }


    class MultiTokenMandatory extends Token {

        private final List<Token> choices;

        public MultiTokenMandatory(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            boolean allOk = true;
            int position1 = position;
            int i = 0;
            int position0 = position1;
            while (allOk) {
                for (int j = 0; j < choices.size(); j++) {
                    Token token = choices.get(j);
                    position1 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                        if (i > 0) {
                            break;
                        } else {
                            setSuccessful(false);
                            return position;
                        }
                    }
                }
                if (allOk)
                    position0 = position1;
                i++;
            }
            return processNext(input, position0);


        }

        @Override
        public String toString() {
            return "MultiTokenMandatory{" +
                    "choices=" + choices +
                    ", successful=" + isSuccessful() + '}';
        }
    }

    class MultiTokenExclusiveXor extends Token {
        private final List<Token> choices;

        public MultiTokenExclusiveXor(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            int position1 = position;
            int i = 0;
            int position0 = position1;
            for (Token token : choices) {
                position1 = token.parse(input, position0);
                if (token.isSuccessful()) {
                    return processNext(input, position1);
                }
            }
            setSuccessful(false);
            return position0;

        }
    }

    class TokenMethodMemberDefinition extends TokenName {
        public TokenMethodMemberDefinition() {
            super();
        }
    }

    class TokenVariableMemberDefinition extends TokenName {
        public TokenVariableMemberDefinition() {
            super();
        }
    }


    class TokenName extends Token {
        private String name;

        public TokenName() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            int position1 = super.parse(input, position);
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0) {
                this.setName(input.substring(position1, i));
                return processNext(input, i);
            } else {
                setSuccessful(false);
                return position1;
            }

        }


        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    class TokenVariableMemberDefinitionClassName extends TokenName {
        public TokenVariableMemberDefinitionClassName() {
            super();
        }
    }

    class TokenVariableInMethodName extends TokenName {
        public TokenVariableInMethodName() {
            super();
        }
    }

    class TokenEquals extends TokenString {
        public TokenEquals() {
            super("=");
        }
    }

    class TokenExpression extends Token {
        public TokenExpression() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            int i = position;
            boolean passed = false;
            while (i < input.length() && input.charAt(i) != ';') {
                i++;
                passed = true;
            }
            if (passed && i < input.length()) {
                return processNext(input, i);

            } else {
                setSuccessful(false);
                return position;
            }
        }
    }

    class TokenSemiColon extends TokenString {
        public TokenSemiColon() {
            super(";");
        }
    }

    public StringAnalyzer2() {
    }


    static class ActualContext {
        private enum ContextType {Classname, FieldName, MethodName, Instruction}

        private ContextType currentContextType;
        private Class currentClassname;
        private Variable currentFieldName;
        private Method currentMethodName;

        public ActualContext() {

        }

        public ActualContext(ContextType currentContextType, Class currentClassname, Variable currentFieldName, Method currentMethodName) {
            this.currentContextType = currentContextType;
            this.currentClassname = currentClassname;
            this.currentFieldName = currentFieldName;
            this.currentMethodName = currentMethodName;
        }

        public ContextType getCurrentContextType() {
            return currentContextType;
        }

        public void setCurrentContextType(ContextType currentContextType) {
            this.currentContextType = currentContextType;
        }

        public Class getCurrentClassname() {
            return currentClassname;
        }

        public void setCurrentClassname(Class currentClassname) {
            this.currentClassname = currentClassname;
        }

        public Variable getCurrentFieldName() {
            return currentFieldName;
        }

        public void setCurrentFieldName(Variable currentFieldName) {
            this.currentFieldName = currentFieldName;
        }

        public Method getCurrentMethodName() {
            return currentMethodName;
        }

        public void setCurrentMethodName(Method currentMethodName) {
            this.currentMethodName = currentMethodName;
        }
    }

    /*
        static class ActualContext {
            private enum ContextType {Classname, FieldName, MethodName, Instruction}

            private ContextType currentContextType;
            private String currentClassname;
            private String currentFieldName;
            private String currentMethodName;

            public ActualContext() {

            }

            public ActualContext(ContextType currentContextType, String currentClassname, String currentFieldName, String currentMethodName) {
                this.currentContextType = currentContextType;
                this.currentClassname = currentClassname;
                this.currentFieldName = currentFieldName;
                this.currentMethodName = currentMethodName;
            }

            public ContextType getCurrentContextType() {
                return currentContextType;
            }

            public void setCurrentContextType(ContextType currentContextType) {
                this.currentContextType = currentContextType;
            }

            public String getCurrentClassname() {
                return currentClassname;
            }

            public void setCurrentClassname(String currentClassname) {
                this.currentClassname = currentClassname;
            }

            public String getCurrentFieldName() {
                return currentFieldName;
            }

            public void setCurrentFieldName(String currentFieldName) {
                this.currentFieldName = currentFieldName;
            }

            public String getCurrentMethodName() {
                return currentMethodName;
            }

            public void setCurrentMethodName(String currentMethodName) {
                this.currentMethodName = currentMethodName;
            }
        }
    */
    public class Construct {
        public Variable currentField;
        public Method currentMethod;
        protected String packageName = "";
        protected Class currentClass = new Class();
        protected HashMap<String, Class> cited = new HashMap<>();
        protected HashMap<String, Variable> fieldMembers = new HashMap<>();
        protected HashMap<String, Method> methodMembers = new HashMap<>();


        public void construct() {
            Token token = definitions.get(0);
        }

        @Override
        public String toString() {
            return "Construct{" +
                    "currentField=" + currentField +
                    ", currentMethod=" + currentMethod +
                    ", packageName='" + packageName + '\'' +
                    ", currentClass=" + currentClass +
                    ", cited=" + cited +
                    ", fieldMembers=" + fieldMembers +
                    ", methodMembers=" + methodMembers +
                    '}';
        }
    }

    private final Construct construct = new Construct();
    private final ActualContext actualContext = new ActualContext();

    public int parse(String input) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                }
                System.exit(1);
            }
        }.start();
        Token token = definitions.get(0);
        int position1 = token.parse(input, 0);
        if (token.isSuccessful()) {
            return position1;
        } else {
            return -1;
        }
    }
}
