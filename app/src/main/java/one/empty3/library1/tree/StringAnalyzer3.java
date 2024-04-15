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
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

/**
 * The StringAnalyzer3 class is responsible for analyzing string inputs and performing parsing operations.
 * It contains methods for parsing and retrieving constructs.
 *
 * @see AlgebraicTree
 */
public class StringAnalyzer3 {
    protected HashMap<Integer, Token> definitions = new HashMap<>();
    protected int mPosition;
    private HashMap<String, Class> classes;
    private int index = 0;
    private List<Construct> constructs = new ArrayList<>();

    public List<Construct> getConstructs() {
        return constructs;
    }

    /**
     * Retrieves the Construct object from the current instance.
     *
     * @return the Construct object
     */
    @NotNull
    protected Construct getConstruct() {
        if (construct == null && !constructs.isEmpty())
            construct = constructs.getLast();
        if (construct == null)
            construct = new Construct();
        return construct;
    }

    /**
     * Represents a token in a parsing process.
     */
    public abstract class Token {
        protected Action3 action;
        protected Class aClass;
        protected Method method;
        protected Variable variable;
        private boolean successful = false;
        private final StructureMatrix<Token> nextTokens = new StructureMatrix<>(1, Token.class);

        public Token() {
            index++;
            definitions.put(index, this);
            if (constructs.isEmpty())
                constructs.add(construct);
        }

        public Token addToken(Token token) {
            this.nextTokens.setElem(token, this.nextTokens.getData1d().size());
            return this;
        }

        private int nextToken(String input, int position) {
            int parse = position;
            if (!nextTokens.getData1d().isEmpty()) {
                position = nextTokens.getData1d().get(0).parse(input, position);
                setSuccessful(true);
            } else {
                setSuccessful(true);
            }
            return position;
        }

        private Token nextToken() {
            if (!nextTokens.getData1d().isEmpty()) {
                return nextTokens.getData1d().getFirst();
            }
            return null;
        }

        public Token setAction(Action3 action) {
            this.action = action;
            return this;
        }

        /***
         * Skips over any blank spaces in the input string starting from the given position.
         *
         * @param input    the input string
         * @param position the starting position
         * @return the new position after skipping the blank spaces
         */
        public int skipBlanks(String input, int position) {
            boolean passed = false;
            int position1 = position;
            position1 = new TokenComment().parse(input, position1);
            while (position1 < input.length() && (Character.isSpaceChar(input.charAt(position1)) || Character.isWhitespace(input.charAt(position1)))) {
                position1++;
                passed = true;
            }
            position1 = new TokenComment().parse(input, position1);
            int position2 = passed ? position1 : position;
            mPosition = position2;
            return position2;
        }

        /***
         * Parses the input string starting from the given position and skips over any blank spaces.
         *
         * @param input    the input string
         * @param position the starting position
         * @return the new position after skipping the blank spaces
         */
        public int parse(String input, int position) {
            position = skipBlanks(input, position);
            return position;
        }

        /**
         * Determines whether the current operation was successful or not.
         *
         * @return true if the operation was successful, false otherwise
         */
        protected boolean isSuccessful() {
            return successful;
        }

        /**
         * Sets the success state of the current operation.
         *
         * @param successful the flag indicating whether the operation was successful
         */
        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        /**
         * Retrieves the action associated with the token.
         *
         * @return the action associated with the token
         */
        public Action3 getAction3() {
            return action;
        }

        /**
         * Executes the action associated with the token, if it is not null.
         */
        public void action() {
            if (getAction3() != null) getAction3().action();
        }

        /**
         * Processes the next token in the input string starting from the given position.
         *
         * @param input    the input string
         * @param position the starting position
         * @return the new position after processing the next token
         */
        protected int processNext(String input, int position) {
            if (action != null && action.on == Action3.ON_NEXT_TOKEN_CALL) action();
            if (nextToken() != null) {
                int nextToken = nextToken(input, position);
                if (Objects.requireNonNull(nextToken()).isSuccessful()) {
                    setSuccessful(true);
                    if (action != null && action.getOn() == Action3.ON_RETURNS_TRUE_NEXT_TOKEN) action();
                    return nextToken;
                } else {
                    setSuccessful(false);
                    ///????
                    if (action != null && action.getOn() == Action3.ON_RETURNS_TRUE_NEXT_TOKEN) action();
                    return position;
                }
            } else {
                setSuccessful(true);
                if (action != null) action();
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

        /**
         * Creates a copy of the current Token object.
         *
         * @return a new Token object that is a copy of the current Token (further step: make a deep copy with
         * nextTokens and Token type fields.
         */
        public Token copy(Token token0) {
            token0.nextTokens.data1d.forEach(new Consumer<Token>() {
                @Override
                public void accept(Token token) {
                    Token copy = token.copy(token);
                    token0.addToken(copy);
                }
            });
            return token0;
        }

        public Construct getConstruct() {
            return this.clones().get(clones().size() - 1);
        }

        public void setConstruct(Construct clone) {
            this.clones().add(clone);
            construct = clone;
        }

        List<Construct> clones() {
            return constructs;
        }

        public Construct getFirstConstructVersion() {
            return clones().getFirst();
        }
    }

    class TokenComment extends Token {

        private String sstring = null;

        public TokenComment() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            int position1 = position;

            if (position1 + 2 <= input.length() && input.substring(position1, position1 + 2).equals("//")) {
                position1 = position + 2;
                while (position1 + 1 < input.length() && !input.substring(position1, position1 + 1).equals("\n")) {
                    position1++;
                }
                setSuccessful(true);
                return position1 + 1;
            } else if (position1 + 2 <= input.length() && input.substring(position1, position1 + 2).equals("/*")) {
                position1 = position + 2;
                while (position1 + 2 < input.length() && !input.substring(position1, position1 + 2).equals("*/")) {
                    position1++;
                }
                setSuccessful(true);
                return position1 + 2;
            }
            setSuccessful(false);
            return position;
        }
    }

    class MultiTokenExclusiveXor extends MultiToken {

        public MultiTokenExclusiveXor(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        public MultiTokenExclusiveXor(ArrayList<Token> objects) {
            this.choices = objects;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
//                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            int i = 0;
            int position0 = position1;
            boolean passed = false;
            boolean next = true;
            boolean first = true;
            while (next) {
                next = false;
                for (int j = 0; j < choices.size(); j++) {
                    Token token = choices.get(j);
                    position1 = token.parse(input, position0);
                    if (token.isSuccessful()) {
                        position0 = position1;
                        next = true;
                        break;
                    }
                }

                if (first && !next) {
                    setSuccessful(false);
                    return position;
                }
                first = false;
            }
            setSuccessful(true);
            return processNext(input, position0);

        }

        @Override
        public Token copy(Token token) {
            token = new SingleTokenExclusiveXor();
            super.copy(token);
            return token;

        }
    }

    class SingleTokenInclusiveXor extends MultiToken {

        public SingleTokenInclusiveXor(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            int i = 0;
            int position0 = position1;
            boolean passed = false;
            boolean first = true;
            for (Token token : choices) {
                position1 = token.parse(input, position0);
                if (token.isSuccessful()) {
                    setSuccessful(true);
                    return processNext(input, position1);
                }
            }
            setSuccessful(true);
            return processNext(input, position0);
        }

        @Override
        public Token copy(Token token) {
            token = new SingleTokenExclusiveXor();
            super.copy(token);
            return token;

        }
    }

    /**
     * The SingleTokenExclusiveXor class represents a token that matches only one of the specified tokens in an exclusive XOR manner.
     * It extends the Token class and overrides the parse method to implement the exclusive XOR logic.
     */
    class SingleTokenExclusiveXor extends MultiToken {
        public SingleTokenExclusiveXor(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            int i = 0;
            int position0 = position1;
            for (Token token : choices) {
                position1 = token.parse(input, position0);
                if (token.isSuccessful()) {
                    int i1 = processNext(input, position1);
                    if (getNextToken().data1d.isEmpty() || getNextToken().getElem(0).isSuccessful()) {
                        setSuccessful(true);
                        return i1;
                    }
                }
            }
            setSuccessful(false);
            return position0;

        }


        @Override
        public Token copy(Token token) {
            token = new SingleTokenExclusiveXor();
            super.copy(token);
            return token;
        }
    }

    /**
     * Represents a mandatory choice token, which matches one of the given string values.
     * Extends the {@link Token} class.
     */
    class TokenChoiceStringMandatory extends Token {
        protected String[] names = new String[0];
        protected String choice = "";

        public TokenChoiceStringMandatory(String[] values) {
            super();
            this.names = values;
        }

        public TokenChoiceStringMandatory(ArrayList<String> objects) {
            names = new String[objects.size()];
            objects.toArray(this.names);
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int position2 = position1;
            for (String s : names) {
                if (position2 < input.length() && input.substring(position2).startsWith(s)) {
                    this.choice = s;
                    position2 = position2 + s.length();
                    return processNext(input, position2);
                }
            }

            setSuccessful(false);
            return position1;
        }

        public String getChoice() {
            return choice;
        }

        @Override
        public String toString() {
            return "TokenChoiceStringMandatory{" +
                    "names=" + Arrays.toString(names) +
                    ", choice='" + choice + '\'' +
                    "}\n";
        }

        @Override
        public Token copy(Token token) {
            token = new TokenChoiceStringMandatory(this.names);
            super.copy(token);
            return token;
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
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            return processNext(input, position);

        }

    }


    public class MultiTokenInclusiveXor extends MultiToken {
        public MultiTokenInclusiveXor(Token... choices) {
            super();
            this.choices = Arrays.stream(choices).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            boolean chosen = true;
            boolean passedOne = false;
            while (chosen) {
                chosen = false;
                for (Token token : choices) {
                    position1 = token.parse(input, position);
                    if (token.isSuccessful() && position1 >= position) {
                        chosen = true;
                        passedOne = true;
                        position = position1;
                        break;
                    } else {
                        position1 = position;
                    }
                }
            }
            setSuccessful(true);
            if (passedOne) {
                return processNext(input, position1);
            } else
                return processNext(input, position1);
        }

        @Override
        public String toString() {
            return "TokenChoiceInclusive{" +
                    "choices=" + choices +
                    "}\n";
        }

        @Override
        public Token copy(Token token) {
            token = new TokenChoiceInclusive();
            super.copy(token);
            return token;
        }
    }

    public class MultiTokenInclusiveXorAndOne extends MultiToken {
        private final Token or;

        public MultiTokenInclusiveXorAndOne(Token or, Token... choices) {
            super();
            this.or = or;
            this.choices = Arrays.stream(choices).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            boolean chosen = true;
            boolean passedOne = false;
            while (chosen) {
                chosen = false;
                for (Token token : choices) {
                    position1 = token.parse(input, position);
                    if (token.isSuccessful() && position1 >= position) {
                        chosen = true;
                        passedOne = true;
                        position = position1;
                        break;
                    } else {
                        position1 = position;
                    }
                }
            }
            setSuccessful(true);
            if (passedOne) {
                return or.parse(input, position1);
            } else {
                return or.parse(input, position1);
            }
        }

        @Override
        public String toString() {
            return "TokenChoiceInclusive{" +
                    "choices=" + choices +
                    "}\n";
        }

        @Override
        public Token copy(Token token) {
            token = new TokenChoiceInclusive();
            super.copy(token);
            return token;
        }
    }

    /**
     * Represents a token that provides inclusive choices for parsing.
     */
    class TokenChoiceInclusive extends MultiToken {
        public TokenChoiceInclusive(Token... choices) {
            super();
            this.choices = Arrays.stream(choices).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            for (Token token : choices) {
                position1 = token.parse(input, position);
                if (token.isSuccessful()) {
                    position1 = processNext(input, position1);
                }
            }
            setSuccessful(true);
            return position1;
        }

        @Override
        public String toString() {
            return "TokenChoiceInclusive{" +
                    "choices=" + choices +
                    "}\n";
        }

        @Override
        public Token copy(Token token) {
            token = new TokenChoiceInclusive();
            super.copy(token);
            return token;
        }
    }


    /***
     * Tous les tokens choisis et aucun autre.
     */
    class TokenChoiceExclusive extends MultiToken {
        public TokenChoiceExclusive(Token... choices) {
            super();
            this.choices.addAll(Arrays.stream(choices).toList());
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            for (Token token : choices) {
                position1 = token.skipBlanks(input, position1);
                if (!token.isSuccessful()) {
                    setSuccessful(false);
                    return position;
                }
            }
            return processNext(input, position1);
        }

        @Override
        public String toString() {
            return "TokenChoiceExclusive{" +
                    "choices=" + Arrays.toString(choices.toArray()) +
                    "}\n";
        }

        @Override
        public Token copy(Token token) {
            token = new TokenChoiceExclusive();
            super.copy(token);
            return token;
        }

    }

    class TokenPackage extends TokenString {
        public TokenPackage() {
            super("package");
        }
    }

    class TokenQualifiedName extends Token {
        protected String name = null;

        public TokenQualifiedName() {
            super();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                passed = true;
                i++;
            }
            if (passed && i < input.length() && containsNoKeyword(input.substring(position1, i))) {
                if (!input.substring(position1, i).isEmpty()) {
                    setName(input.substring(position1, i));
                    return processNext(input, i);

                }
            }
            setSuccessful(false);
            return position1;
        }

        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }
    }

    class TokenClassKeyword extends TokenString {

        public TokenClassKeyword() {
            super("class");
        }

    }

    /**
     * Represents a token for matching a specific string in a parsing process.
     */
    class TokenString extends Token {
        protected String name;

        public void setName(String name) {
            this.name = name;
        }

        public TokenString(String name) {
            super();
            setName(name);
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                throw new RuntimeException(getClass() + " : position>=input.length()");
                //return position;

            }
            position = super.skipBlanks(input, position);
            if (position < input.length() && input.substring(position).startsWith(name)) {
                int position1 = position + name.length();
                setSuccessful(true);///????
                return processNext(input, position1);
            } else if (position >= input.length()) {
                setSuccessful(true);
                return position;
            } else {
                setSuccessful(false);
                return position;
            }
        }

        @Override
        public String toString() {
            return getClass().getName() + "<=TokenString{" +
                    "name='" + name + '\'' + ", sucessful=" + isSuccessful() +
                    "}\n";
        }

        @Override
        public Token copy(Token token) {

            return new TokenString(this.name);
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

    /**
     * Represents a token class for handling multiple optional tokens.
     */
    class MultiTokenOptional extends MultiToken {

        public MultiTokenOptional(Token... choices) {
            super();
            this.choices.addAll(Arrays.stream(choices).toList());
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            boolean allNotOk = false;
            int position1 = position;
            int position2 = position1;
            int i = 0;
            while (!allNotOk) {
                allNotOk = true;
                for (Token token : choices) {
                    position2 = token.parse(input, position1);
                    if (position2 != position1 && token.isSuccessful()) {
                        allNotOk = false;
                        position1 = position2;

                    } else if (!token.isSuccessful()) {
                        position2 = position1;
                    }
                }
                i++;
            }
            return processNext(input, position1);
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("MultiTokenOptional{" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choices=");
            for (Token choice : choices) {
                s.append(choice.toString());
            }
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy(Token token0) {
            token0 = new MultiTokenOptional();
            super.copy(token0);
            return token0;
        }
    }

    /**
     * Represents a single optional token in a parsing process.
     * Extends the base class Token.
     */
    class SingleTokenOptional extends SingleToken {
        public SingleTokenOptional(Token choice) {
            super(choice);
            this.choice = choice;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            int position2 = choice.parse(input, position1);
            if (choice.isSuccessful()) {
                setSuccessful(true);
                return processNext(input, position2);
            } else {
                setSuccessful(true);
                return processNext(input, position1);
            }
        }


        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("SingleTokenOptional {" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choice=");
            s.append(choice.toString());
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy(Token token0) {
            token0 = new SingleTokenOptional(choice.copy(choice));
            super.copy(token0);
            return token0;
        }
    }

    /**
     * Represents a multi-token that is mandatory for the parsing process.
     */
    class MultiTokenMandatory extends MultiToken {

        public MultiTokenMandatory(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                return position;
            }
            assert choices.size() > 0;
            position = skipBlanks(input, position);
            boolean allOk = true;
            int position1 = position;
            int i = 0;
            int position0 = position;
            int j = 0;
            while (allOk) {
                position0 = position1;
                for (j = 0; j < choices.size(); j++) {
                    Token token = choices.get(j);
                    position1 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                        position1 = position0;
                        if (i > 0) {
                            break;
                        } else {
                            setSuccessful(false);
                            return position;
                        }
                    }
                }
//                if (allOk)
//                    action();
                i++;
            }
            if (i >= 1) {
                return processNext(input, position0);
            }
            return position0;


        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("MultiTokenMandatory {" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choices=");
            for (int i = 0; i < choices.size(); i++) {
                s.append(choices.get(i).toString());
            }
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy(Token token) {
            Token[] tokens = new Token[this.choices.size()];
            this.choices.toArray(tokens);
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].copy(tokens[i]);
            }
            return new MultiTokenMandatory(tokens);
        }
    }

    /***
     *
     */
    class SingleTokenMandatory extends MultiToken {

        public SingleTokenMandatory(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            assert !choices.isEmpty();
            position = skipBlanks(input, position);
            boolean allOk = true;
            int position1 = position;
            int j = 0;
            for (j = 0; j < choices.size(); j++) {
                Token token = choices.get(j);
                position1 = token.parse(input, position1);
                if (!token.isSuccessful()) {
                    setSuccessful(false);
                    return position;
                }
            }
            setSuccessful(true);
            return processNext(input, position1);


        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("SingleTokenMandatory {" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choices=");
            for (int i = 0; i < choices.size(); i++) {
                s.append(choices.get(i).toString());
            }
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy(Token token) {
            Token[] tokens = new Token[this.choices.size()];
            this.choices.toArray(tokens);
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].copy(tokens[i]);
            }
            return new SingleTokenMandatory(tokens);
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


    /**
     * Represents a token that identifies a name in a parsing process.
     */
    class TokenName extends Token {
        private String name;

        public TokenName() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                //mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '.')
            ) {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0 && containsNoKeyword(input.substring(position1, i))) {
                this.setName(input.substring(position1, i));
                return processNext(input, i);
            } else if (i >= input.length()) {
                setSuccessful(true);
                return input.length();
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
                    "}\n";
        }

        @Override
        public Token copy(Token token) {

            TokenName tokenName = new TokenName();
            tokenName.name = name;
            return tokenName;
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


    /**
     * Represents a specific type of Token.
     */
    class TokenExpression1 extends Token {
        protected String expression;
        protected AlgebraicTree algebraicTree;

        public TokenExpression1() {
            super();
        }

        protected boolean isValid(String input, int p) {
            return p < input.length() && (input.charAt(p) != ';');
        }

        protected boolean isNotValid2(String input, int p) {
            return !(p < input.length() && input.charAt(p) != '{' && input.charAt(p) != '}');
        }

        protected boolean containsNoKeyword(String input, int pos1, int pos2) {
            if (pos2 <= input.length() && !input.substring(pos1, pos2).contains("if") && !input.substring(pos1, pos2).contains("for")
                    && !input.substring(pos1, pos2).contains("else") && !input.substring(pos1, pos2).contains("while")) {
                return true;
            }
            return false;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
            }
            position = skipBlanks(input, position);
            int i = position;
            boolean passed = false;


            int countParenthesis = 0;
            if (input.charAt(i) == '(')
                countParenthesis++;
            if (input.charAt(i) == ')') { // Empty expression ???
                setSuccessful(false);
                return position;
            }
            while ((i < input.length() && (isValid(input, i) && !isNotValid2(input, i))
                    && containsNoKeyword(input, position, i)) || countParenthesis > 0) {
                passed = true;
                i++;
                if (input.charAt(i) == '(')
                    countParenthesis++;
                else if (input.charAt(i) == ')' && countParenthesis == 0) {
                    passed = true;
                    break;
                } else if (input.charAt(i) == ')' && countParenthesis > 0) {
                    countParenthesis--;
                }
            }
            if (passed && (i >= input.length() || !isValid(input, i) || isNotValid2(input, i) || i - position <= 0
                    || !containsNoKeyword(input, position, i) || countParenthesis <= 0)) {
                if (i < input.length()) {
                    expression = input.substring(position, i);
                    try {
                        AlgebraicTree algebraicTree = new AlgebraicTree(expression);
                        this.algebraicTree = algebraicTree;
                        algebraicTree.construct();
                    } catch (AlgebraicFormulaSyntaxException e) {
                        System.err.println("Error constructing : " + expression);
                    }
                    setSuccessful(true);
                    //      System.out.println("(TokenExpression1)current expression: " + expression + "\n(TokenExpression1)Char at next position:" + "***" + input.substring(i, i + 10) + "***");
                    return processNext(input, i);
                }
            }
            //System.out.println("FAILED(TokenExpression1)current expression: " + expression + "\nFAILED(TokenExpression1)Char at next position:" + input.substring(i, i + 10) + "***");
            setSuccessful(false);
            return position;
        }

        /*

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
            }
            position = skipBlanks(input, position);
            int i = position;
            boolean passed = false;

            int countParenthesis = 0;
            if (input.charAt(i) == '(')
                countParenthesis++;
            if (input.charAt(i) == ')')
                countParenthesis--;
            while (i < input.length() && (isValid(input, i) && !isNotValid2(input, i))
                    && containsNoKeyword(input, position, i) && countParenthesis != 0) {
                i++;
                passed = true;
                if (input.charAt(i) == '(')
                    countParenthesis++;
                if (input.charAt(i) == ')')
                    countParenthesis--;
            }
            if (passed && (i >= input.length() || !isValid(input, i) || isNotValid2(input, i)) && (i - position > 0
                    && containsNoKeyword(input, position, i))) {
                if (i < input.length()) {
                    expression = input.substring(position, i);
                    try {
                        AlgebraicTree algebraicTree = new AlgebraicTree(expression);
                        this.algebraicTree = algebraicTree;
                        algebraicTree.construct();
                    } catch (AlgebraicFormulaSyntaxException e) {
                        System.err.println("Error constructing : " + expression);
                    }
                    return processNext(input, i);
                }
                setSuccessful(false);
                return position;
            } else {
                setSuccessful(false);
                return position;
            }
        }
*/
        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public AlgebraicTree getAlgebraicTree() {
            return algebraicTree;
        }

        public void setAlgebraicTree(AlgebraicTree algebraicTree) {
            this.algebraicTree = algebraicTree;
        }

        @Override
        public Token copy(Token token) {
            super.copy(token);
            token = new TokenExpression1();
            ((TokenExpression1) token).expression = expression;
            return token;
        }
    }

    class TokenSemiColon extends TokenString {
        public TokenSemiColon() {
            super(";");
        }
    }

    /**
     * This class represents a StringAnalyzer3 object that can perform parsing operations on a string input.
     */
    public StringAnalyzer3() {

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
        public Variable currentField = new Variable();
        public Method currentMethod = new Method();
        protected String packageName = "";
        protected Class currentClass = new Class();
        protected HashMap<String, Class> cited = new HashMap<>();
        protected HashMap<String, Variable> fieldMembers = new HashMap<>();
        protected ArrayList<Class> classes = new ArrayList<>();
        private List<InstructionBlock> currentInstructions = new ArrayList<>();

        public Construct() {
        }

        public void pushInstructions(InstructionBlock instructionBlock) {
            currentInstructions.add(instructionBlock);
        }

        public InstructionBlock popInstructions() {
            if (!currentInstructions.isEmpty()) {
                InstructionBlock instructionBlock = currentInstructions.get(currentInstructions.size() - 1);
                currentInstructions.remove(currentInstructions.size() - 1);
                return instructionBlock;
            }
            return null;
        }

        @NotNull
        public InstructionBlock getCurrentInstructions() {
            if (currentInstructions == null) {
                currentInstructions = new ArrayList<>();
                currentInstructions.add(new InstructionBlock());
            }
            if (currentInstructions.isEmpty() || currentInstructions.getLast() == null) {
                currentInstructions.add(new InstructionBlock());
            }
            return currentInstructions.getLast();
        }

        public void setCurrentInstructions(InstructionBlock instructionBlock) {
            if (currentInstructions == null) {
                currentInstructions = new ArrayList<>();
            }
            currentInstructions.add(instructionBlock);
        }

        @Override
        public String toString() {
            final String[] fieldsStr = {""};
            fieldMembers.forEach((s, variable) -> fieldsStr[0] += "\n" + s + "\n" + variable.toString());
            final String[] classesStr = {""};
            classes.forEach(aClass -> classesStr[0] += "\n" + "\n" + aClass.toString());
            final String[] citedStr = {""};
            cited.forEach((s, aClass) -> citedStr[0] += aClass.toString());
            final String[] methodStr = {""};
            String[] string = {"Construct{" +
                    "packageName='" + packageName +
                    "', classes=[" + classesStr[0] +
                    "], fieldMembers=['" + fieldsStr[0] +
                    "], currentClass={" + currentClass +
                    "], currentMethod={" + currentMethod +
                    "], currentField={" + currentField +
                    "}, cited=[" + citedStr[0] +
                    "]}"};

            return string[0];
        }

        private String debugString(boolean isDebug, String tokenLangString) {
            return isDebug ? "{" + tokenLangString + "}" : tokenLangString;
        }


        public String toLangStringJava(boolean debug) {
            StringBuilder sb = new StringBuilder();
            if (!this.packageName.isEmpty()) {
                sb.append(debugString(debug, "package "))
                        .append(debugString(debug, packageName)).append(debugString(debug, ";\n"));
            }
            if (classes.isEmpty() && currentClass.getName() != null && classesWithName(currentClass.getName()).isEmpty()) {
                classes.add(currentClass);
            }///
            classes.forEach(aClass -> {
                sb.append(debugString(debug, "class ")).append(debugString(debug, aClass.getName()))
                        .append(debugString(debug, " {\n"));
                aClass.getVariableList().forEach(variable -> sb.append(debugString(debug, "\t"))
                        .append(debugString(debug, variable.getClassStr()))
                        .append(" ").append(debugString(debug, variable.getName()))
                        .append(debugString(debug, ";\n")));
                aClass.getMethodList().forEach(method -> {
                    sb.append("\t")
                            .append(method.getOfClass() != null ? debugString(debug, method.getOfClass().getClassStr()) : "").append(" ").append(debugString(debug, method.getName())).append(" ( ");
                    if (!method.getParameterList().isEmpty()) {
                        Variable variable = method.getParameterList().get(0);
                        sb.append(debugString(debug, variable.getClassStr())).append(" ").append(debugString(debug, variable.getName()));
                        for (int i = 1; i < method.getParameterList().size(); i++) {
                            variable = method.getParameterList().get(i);
                            sb.append(debugString(debug, ", "))
                                    .append(debugString(debug, variable.getClassStr()))
                                    .append(" ").append(debugString(debug, variable.getName()));
                        }
                    }
                    sb.append(debugString(debug, " )"));
                    InstructionBlock instructions = method.getInstructions();
                    instructions.deepth++;
                    sb.append(instructions.toLangStringJava(debug));
                    instructions.deepth--;
                    /*instructionList.forEach(instruction0 -> {
                        sb.append(instruction0.toLangStringJava(debug));
                    });*/
                });
            });
            sb.append("}");
            return sb.toString();
        }

        public List<Class> classesWithName(String name) {
            List<Class> selectedClasses = new ArrayList<>();
            classes.forEach(new Consumer<Class>() {
                @Override
                public void accept(Class aClass) {
                    if (aClass.getName().equals(name)) {
                        selectedClasses.add(aClass);
                    }
                }
            });
            return selectedClasses;
        }


        @Override
        protected Object clone() throws CloneNotSupportedException {
            Construct clone = new Construct();
            clone.classes = (ArrayList<Class>) this.classes.clone();
            clone.currentClass = (Class) this.currentClass.clone();
            clone.cited = (HashMap<String, Class>) cited.clone();
            clone.currentField = (Variable) currentField.clone();
            clone.currentInstructions = (List<InstructionBlock>) ((ArrayList<InstructionBlock>) currentInstructions).clone();
            clone.currentMethod = (Method) currentMethod.clone();
            clone.fieldMembers = (HashMap<String, Variable>) fieldMembers.clone();
            clone.packageName = this.packageName;
            return clone;
        }
    }

    private Construct construct = new Construct();

    public int parse(@NotNull String input) {
        /*new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                }
                System.exit(1);
            }
        }.start();
        */
        int position1 = 0;
        try {
            Token token = definitions.get(0);
            position1 = token.parse(input, 0);
            return position1;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return position1;
    }

    public int parse(@NotNull Token token1, @Nullable String input) {
        int position1 = 0;
        position1 = token1.parse(input, position1);
        return position1;
    }

    public class TokenAttribute extends TokenString {
        private String requiredName;
        private String attributeName;
        private String attributeValue;

        public TokenAttribute(String version) {
            super(version);
            this.requiredName = version;
        }

        public String getRequiredName() {
            return requiredName;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public String getAttributeValue() {
            return attributeValue;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                //mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '-')) {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0) {
                this.setAttributeName(input.substring(position1, i));
                position1 = i;
                passed = false;
                position1 = skipBlanks(input, position1);
                if (input.charAt(position1) == '=') {
                    position1 = skipBlanks(input, position1 + 1);
                    char start = 0;
                    i = position1;
                    int i1 = i;
                    char end = 0;
                    if (input.charAt(i) == '\'' || input.charAt(i) == '"') {
                        start = input.charAt(i);
                        i++;
                        while (i < input.length() && (input.charAt(i) != /*'\\' +*/ start)) {
                            i++;
                            passed = true;
                        }
                        if (passed)
                            end = input.charAt(i);
                    }
                    if (passed && start != 0 && end == start) {
                        setAttributeValue(input.substring(i1 + 1, i));
                    }
                }
                return processNext(input, position1);
            } else if (i >= input.length()) {
                setSuccessful(true);
                return input.length();
            } else {
                setSuccessful(false);
                return position1;
            }

        }

        private void setAttributeValue(String substring) {
            this.attributeValue = substring;
        }

        private void setAttributeName(String substring) {
            this.attributeName = substring;
        }

        @Override
        public Token copy(Token token) {
            TokenAttribute tokenAttribute = new TokenAttribute(requiredName);
            tokenAttribute.setAttributeValue(attributeValue);
            tokenAttribute.setAttributeName(attributeName);
            return super.copy(token);
        }


    }

    class TokenElementOpening extends Token {
        private final String requiredName;
        private String elementName;

        public TokenElementOpening(String requiredName) {
            this.requiredName = requiredName;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                //mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int i = position1;
            boolean passed = false;
            if (i >= input.length() || (!Character.isLetterOrDigit(input.charAt(i)) && input.charAt(i) != '_' && input.charAt(i) != '-')) {
                i++;
                setSuccessful(false);
                return position1;
            }
            i = position1;
            if (i < input.length() && input.charAt(i) == '<') {
                position1 = i + 1;
                position1 = skipBlanks(input, position1);
                i = position1;
                while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '-')) {
                    i++;
                    passed = true;
                }
            }
            if (passed && i - position1 > 0) {
                this.setElementName(input.substring(position1, i));
                return processNext(input, i);
            } else if (i >= input.length()) {
                setSuccessful(true);
                return input.length();
            } else {
                setSuccessful(false);
                return position1;
            }

        }

        private void setElementName(String substring) {
            this.elementName = substring;
        }

    }

    class TokenCloseElement extends TokenString {
        public TokenCloseElement() {
            super(">");
        }
    }

    class TokenCloseElementShort extends TokenString {
        public TokenCloseElementShort() {
            super("/>");
        }
    }

    class TokenCloseElementShortXmlDeclaration extends TokenString {
        public TokenCloseElementShortXmlDeclaration() {
            super("?>");
        }
    }

    @Deprecated
    public class TokenLogicalExpression extends Token {
        public String expression;

        public TokenLogicalExpression() {
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return position;
            }
            int position1 = skipBlanks(input, position);
            if (input.charAt(position1) == '(') {
                int position2 = position1;
                position1++;
                int countParenthesis = 1;
                while (position1 < input.length() && countParenthesis > 0) {
                    if (input.charAt(position1) == '(')
                        countParenthesis++;
                    if (input.charAt(position1) == ')')
                        countParenthesis--;
                    position1++;
                }

                position1 = skipBlanks(input, position1);

                if (position1 < input.length() && countParenthesis == 0) {
                    expression = input.substring(position2, position1);
                    setSuccessful(true);
                    //System.out.println("SUCCEEDED(TokenLogicalExpression)current expression: " + expression + "\nSUCCEEDED(TokenExpression1)Char at next position:" + input.charAt(position2));
                    return processNext(input, position1);
                }
            }
            setSuccessful(false);
            //System.out.println("FAILED(TokenLogicalExpression)current expression: " + expression + "\nFAILED(TokenExpression1)Char at next position:" + input.charAt(position));
            return position;
        }

        public Token copy(Token token) {
            TokenLogicalExpression tokenLogicalExpression = new TokenLogicalExpression();
            super.copy(tokenLogicalExpression);
            return tokenLogicalExpression;
        }

        @Override
        public String toString() {
            return "TokenLogicalExpression{" +
                    "successful=" + isSuccessful() +
                    ", expression='" + expression + '\'' +
                    '}';
        }
    }

    public class TokenLogicalExpression1 extends Token {
        public String expression;

        public TokenLogicalExpression1() {
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return position;
            }
            int position1 = skipBlanks(input, position);
            int position2 = position1;
            int countParenthesis = 0;
            if (input.charAt(position1) == ')')
                countParenthesis--;
            while (position1 < input.length() && countParenthesis >= 0) {
                if (input.charAt(position1) == '(')
                    countParenthesis++;
                if (input.charAt(position1) == ')')
                    countParenthesis--;
                if (countParenthesis < 0) {
                    countParenthesis = 0;
                    break;
                }
                if (input.charAt(position1) == ';')
                    break;
                position1++;
            }

            position1 = skipBlanks(input, position1);

            if (position1 < input.length() && countParenthesis == 0 && position1 - position2 > 0) {
                expression = input.substring(position2, position1);
                setSuccessful(true);
                //System.out.println("SUCCEEDED(TokenLogicalExpression1)current expression: " + expression + "\n(TokenLogicalExpression1)Char at next position:" + input.charAt(position1));
                return processNext(input, position1);
            }
            setSuccessful(false);
            //System.out.println("FAILED(TokenLogicalExpression1)current expression: " + expression + "\nFAILED(TokenLogicalExpression1)Char at next position:" + input.charAt(position));
            return position;
        }

        public Token copy(Token token) {
            TokenLogicalExpression tokenLogicalExpression = new TokenLogicalExpression();
            super.copy(tokenLogicalExpression);
            return tokenLogicalExpression;
        }

        @Override
        public String toString() {
            return "TokenLogicalExpression1{" +
                    "successful=" + isSuccessful() +
                    ", expression='" + expression + '\'' +
                    '}';
        }
    }

    public class MultiToken extends Token {
        protected List<Token> choices = new ArrayList<>();


        @Override
        public Token copy(Token token0) {
            Token copy = super.copy(token0);
            choices.forEach(token -> {
                Token copy1 = token.copy(token);
                ((MultiToken) copy).choices.add(copy1);

            });
            return token0;
        }
    }

    public class SingleToken extends Token {
        protected Token choice;

        public SingleToken(Token choice) {
            this.choice = choice;
        }

        @Override
        public Token copy(Token token0) {
            super.copy(token0);
            ((SingleToken) token0).choice = this.choice;
            return token0;
        }
    }

    public boolean containsNoKeyword(String substring) {
        String[] keywords = {"if", "else", "while", "do", "for", "class", "package", "import"};
        for (String keyword : keywords) {
            if (substring.contains(keyword)) {
                int i1 = substring.indexOf(keyword);
                int i = i1 + keyword.length();
                boolean caseStart = false, caseEnd = false;
                if (i >= substring.length() || !Character.isAlphabetic(substring.charAt(i))) {
                    caseEnd = true;
                }
                if (i1 == 0 || !Character.isAlphabetic(substring.charAt(i1 - 1))) {
                    caseStart = true;
                }
                if (caseStart && caseEnd)
                    return false;
            }
        }
        return true;
    }

}
