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

import com.google.googlejavaformat.Input;
import one.empty3.library.T;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StringAnalyzerJava1 extends StringAnalyzer3 {


    public class TokenTypeArrayOrNot extends Token {
        public TokenTypeArrayOrNot() {
            super();
        }
    }

    public class TokenArrayAccessor extends Token {
        public TokenArrayAccessor() {

        }
    }

    public class TokenConstructorArray extends Token {
        public TokenConstructorArray() {
            super();
        }
    }

    public class TokenMethodCall extends Token {
        public TokenMethodCall() {
            super();
        }
    }

    public class TokenConstructorCall extends Token {
        public TokenConstructorCall() {
            super();
        }
    }

    public class TokenConstructor extends Token {
        public TokenConstructor() {

        }

    }

    public class TokenType2 extends Token {
        protected String name = null;
        protected String[] brackets = new String[7];
        private int passBrackets;

        public TokenType2() {
            super();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getBrackets() {
            return brackets;
        }

        public void setBrackets(String[] brackets) {
            this.brackets = brackets;
        }

        public int getPassBrackets() {
            return passBrackets;
        }

        public void setPassBrackets(int passBrackets) {
            this.passBrackets = passBrackets;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            int[] i1 = new int[14];
            boolean passed = false;
            int iWord = i;
            passBrackets = 0;
            while (i < input.length() && (((Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || input.charAt(i) == '.' || Character.isWhitespace(input.charAt(i))
                    || input.charAt(i) == '[')))) {
                passed = true;
                if (Character.isWhitespace(input.charAt(i))) {
                    passed = true;
                    iWord = i;
                    break;
                }
                i++;
            }
            boolean bStart = false;
            while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))) {
                if (input.charAt(i) == '[' && !bStart) {
                    bStart = true;
                    i1[passBrackets] = i;
                }
                if (input.charAt(i) == ']' && bStart) {
                    bStart = false;
                    i1[passBrackets + 1] = i;
                    passBrackets += 2;
                }
                i++;
            }

            if (passed && iWord < input.length() && containsNoKeyword(input.substring(position1, iWord))) {
                if (!input.substring(position1, iWord).isEmpty()) {
                    setName(input.substring(position1, iWord));

                }
            }
            for (int j = 0; j < passBrackets; j += 2) {
                brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
            }
            return i == position1 ? position1 : processNext(input, i);
        }

        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }

    }

    public class MultiTokenSequence extends Token {
        private final Token start;
        private final Token end;
        private final Token separator;
        private final Token tokenType;

        public MultiTokenSequence(Token start, Token end, Token separator, Token tokenType) {
            this.start = start;
            this.end = end;
            this.separator = separator;
            this.tokenType = tokenType;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = start.parse(input, position);
            boolean passOne = false;
            int betweenTokens = 0;
            if (start.isSuccessful()) {
                betweenTokens = 1;
                do {
                    position1 = start.parse(input, position1);
                    while (start.isSuccessful()) {
                        betweenTokens++;
                        position1 = start.parse(input, position1);
                    }
                    position1 = end.parse(input, position1);
                    while (end.isSuccessful() && betweenTokens > 0) {
                        betweenTokens--;
                        position1 = end.parse(input, position1);
                    }
                    if (!passOne && betweenTokens == 0) {
                        position1 = separator.parse(input, position1);
                        if (separator.isSuccessful()) {
                            passOne = false;
                        }
                    }
                    if (betweenTokens == 0) {
                        position1 = tokenType.parse(input, position1);
                        if (tokenType.isSuccessful()) {
                            position = position1;
                        }
                    }
                    position1 = start.parse(input, position1);
                    while (start.isSuccessful()) {
                        betweenTokens++;
                        position1 = start.parse(input, position1);
                    }
                    position1 = end.parse(input, position1);
                    while (end.isSuccessful() && betweenTokens > 0) {
                        betweenTokens--;
                        position1 = end.parse(input, position1);
                    }
                } while (betweenTokens > 0 || !end.isSuccessful());
            }

            return processNext(input, position1);
            
        }
    }


    public class TokenExpression2 extends Token {
        protected List<DataExpression> expressions = new ArrayList<DataExpression>();
        protected String name = null;
        private String[] brackets = new String[7];
        public static int methodCall = 1;
        public int variable = 2;
        public int memberVariable = 4;
        public int memberFunction = 8;
        public int classNameReference = 16;
        public int classArrayAccess = 32;
        public int classArrayNew = 64;
        public int classClassNew = 128;
        public boolean operator;
        public boolean numberDouble;
        public boolean numberFloat;
        public boolean numberBoolean;
        public boolean numberString;
        public boolean numberChar;
        private boolean next = false;

        public TokenExpression2() {
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
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            int[] i1 = new int[14];
            boolean passed = false;
            int iWord = -1;
            int passBrackets = 0;
            if (input.charAt(i) == '.') {
                next = true;
            } else {
                next = false;
            }
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || Character.isWhitespace(input.charAt(i)))) {
                passed = true;
                iWord = i;
                i++;
            }
            i = skipBlanks(input, i);
            if (input.charAt(i) == '.') {
                setName(input.substring(position1, iWord));
                TokenExpression2 tokenExpression2 = new TokenExpression2();
                return tokenExpression2.parse(input, i);
            }
            boolean bStart = false;
            boolean pStart = false;
            int bCount = 0;
            int pCount = 0;
            while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))) {
                if (input.charAt(i) == '[' && bStart) {
                    bCount++;
                } else if (input.charAt(i) == ']' && bStart) {
                    bCount--;
                }
                if (input.charAt(i) == '[' && !bStart && !pStart) {
                    bStart = true;
                    i1[passBrackets] = i;
                } else if (input.charAt(i) == ']' && bStart) {
                    bStart = false;
                    i1[passBrackets + 1] = i;
                    passBrackets += 2;
                } else if (input.charAt(i) == '(' && pStart) {
                    pCount++;
                } else if (input.charAt(i) == ')' && pStart) {
                    pCount--;
                }
                if (bStart) {
                    TokenExpression2 tokenName2 = new TokenExpression2();
                    int parse = tokenName2.parse(input, i + 1);
                    if (tokenName2.isSuccessful()) {
                        i = parse;
                    }
                }
                if (input.charAt(i) == '(' && !pStart && !bStart) {
                    pStart = true;
                } else if (input.charAt(i) == ')' && pStart) {
                    MultiTokenSequence multiTokenSequence = new MultiTokenSequence(new TokenOpenParenthesized(), new TokenCloseParenthesized(),
                            new TokenComa(), new TokenExpression2());
                    int parse = multiTokenSequence.parse(input, i + 1);
                    i = parse;
                    pStart = false;
                }
                i++;
            }

            i = skipBlanks(input, i);
            if (input.charAt(i) == '.') {
                TokenExpression2 tokenExpression2 = new TokenExpression2();
                return tokenExpression2.parse(input, i);
            }

            for (int j = 0; j < passBrackets; j += 2) {
                brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
            }
            return i == position1 ? position1 : processNext(input, i);
        }

        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }
    }


    public class TokenNameDeclaration2 extends Token {
        protected String name = null;
        private String[] brackets = new String[7];

        public TokenNameDeclaration2() {
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
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            int[] i1 = new int[14];
            boolean passed = false;
            int iWord = i;
            int passBrackets = 0;
            while (i < input.length() && (((Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || input.charAt(i) == '.' || Character.isWhitespace(input.charAt(i))
                    || input.charAt(i) == '[')))) {
                passed = true;
                if (Character.isWhitespace(input.charAt(i))) {
                    passed = true;
                    iWord = i;
                    break;
                }
                i++;
            }
            boolean bStart = false;
            while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))) {
                if (input.charAt(i) == '[' && !bStart) {
                    bStart = true;
                    i1[passBrackets] = i;
                }
                if (input.charAt(i) == ']' && bStart) {
                    bStart = false;
                    i1[passBrackets + 1] = i;
                    passBrackets += 2;
                }
                i++;
            }

            if (passed && iWord < input.length() && containsNoKeyword(input.substring(position1, iWord))) {
                if (!input.substring(position1, iWord).isEmpty()) {
                    setName(input.substring(position1, iWord));

                }
            }
            for (int j = 0; j < passBrackets; j += 2) {
                brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
            }
            return i == position1 ? position1 : processNext(input, i);
        }

        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }

    }
}
