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


public class StringAnalyzerJava1 extends StringAnalyzer3 {


    public class TokenType2 extends TokenExpression2 {
    }

    public class TokenName2 extends TokenExpression2 {

    }


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

    /*
            public class TokenType2 extends TokenQualifiedName {
                protected String[] brackets = new String[7];
                protected int passBrackets;
                protected int bracketsCount;

                public TokenType2() {
                    super();
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
                        setSuccessful(true);
                        throw new RuntimeException(getClass() + " : position>=input.length()");
                    }
                    position = skipBlanks(input, position);
                    int position1 = position;
                    int i = position1;
                    int[] i1 = new int[14];
                    boolean passed = false;
                    int iWord = -1;
                    passBrackets = 0;
                    while (i < input.length() && (((Character.isLetterOrDigit(input.charAt(i))
                            || Character.isAlphabetic(input.charAt(i))
                            || input.charAt(i) == '_' || input.charAt(i) == '.' || Character.isWhitespace(input.charAt(i))
                            || input.charAt(i) == '[')))) {
                        passed = true;
                        if (Character.isWhitespace(input.charAt(i)) || input.charAt(i) == '[') {
                            passed = true;
                            iWord = i;
                            break;
                        }
                        i++;
                    }
                    boolean bStart = false;
                    while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))
                            && !nextTokenCharsListConditionTrue(input, i)) {
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

                    if (iWord > 0) {
                        if (passed && containsNoKeyword(input.substring(position1, iWord))) {
                            if (!input.substring(position1, iWord).isEmpty()) {
                                setName(input.substring(position1, iWord));
                            }
                        }
                    } else {
                        setSuccessful(false);
                        return position;
                    }
                    for (int j = 0; j < passBrackets; j += 2) {
                        brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
                        bracketsCount++;
                    }
                    if (i == position1 || name == null || name.isEmpty()) {
                        setSuccessful(false);
                        return position1;
                    } else {
                        setSuccessful(true);
                        return processNext(input, i);
                    }

                }

                @Override
                public Token copy(Token token) {
                    return new TokenQualifiedName();
                }

                public int getBracketsCount() {
                    return bracketsCount;
                }

                public void setBracketsCount(int bracketsCount) {
                    this.bracketsCount = bracketsCount;
                }

                @Override
                public String toString() {
                    return "TokenType2{" +
                            "name='" + name + '\'' +
                            ", bracketsCount=" + bracketsCount +
                            ", passBrackets=" + passBrackets +
                            ", brackets=" + Arrays.toString(brackets) +
                            '}';
                }
            }
    */
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


    private boolean nextTokenCharsListConditionTrue(String input, int i) {
        return i >= input.length() || ((input.charAt(i) == '}'
                || input.charAt(i) == ';' || input.charAt(i) == ','));
    }

    public class TokenExpression2 extends TokenName {
        protected List<DataExpression> expressions = new ArrayList<DataExpression>();
        protected List<DataExpression> bracketsExpressions = new ArrayList<DataExpression>();
        public String[] brackets = new String[7];
        public static final int methodCallArgument = 1;
        public static final int variable = 2;
        public static final int memberVariable = 4;
        public static final int dotCall = 8;
        public static final int classNameReference = 16;
        public static final int classArrayAccess = 32;
        public static final int classArrayNew = 64;
        public static final int classClassNew = 128;
        public static final int methodName = 256;
        public static final int argumentValue = 512;
        public boolean operator;
        public boolean numberDouble;
        public boolean numberFloat;
        public boolean numberBoolean;
        public boolean numberString;
        public boolean numberChar;
        protected int passBrackets;
        private int current = -1;


        public TokenExpression2() {

            super();
            //Logger.getLogger("parser").setFilter(record -> record.getLevel().intValue() < Level.SEVERE.intValue());
        }

        void reinit() {
            expressions = new ArrayList<DataExpression>();
            bracketsExpressions = new ArrayList<DataExpression>();
            brackets = new String[7];
            int passBrackets = -1;
            name = null;
        }


        @Override
        public int parse(String input, int position) {
            int recursions = 0;
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                name = "";
                return position;
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            int[] i1 = new int[14];
            boolean passed = false;
            int iWord = -1;
            passBrackets = 0;

            recursions++;

            if (nextTokenCharsListConditionTrue(input, i)) {
                System.err.println("Char not allowed");
                setSuccessful(false);
                recursions--;
                return i;
            }
            boolean newOperator = false;
            int i0 = i;
            /*+ '='*/
            String charsAlgebraic = "+-*/<>";
            int hasNextPossible = 0;
            boolean lastToken = true;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || !nextTokenCharsListConditionTrue(input, i)
                    || Character.isAlphabetic(input.charAt(i)) || charsAlgebraic.contains("" + input.charAt(i))
                    || input.charAt(i) == '_') && input.charAt(i) != '.' && !Character.isWhitespace(input.charAt(i))) {
                if (i < input.length() - 4 && input.substring(i, i + 3).equals("new") && Character.isWhitespace(input.charAt(i + 4))) {
                    i += 4;
                    newOperator = true;
                    i0 = i;
                }
                passed = true;
                i++;
                iWord = i;
            }

            boolean bStart = false;
            boolean pStart = false;
            int bCount = 0;
            int pCount = 0;
            int iStart = 0;
            int iEnd = 0;
            if (i >= input.length()) {
                setName(input.substring(i0, iWord));
                lastToken = true;
            }
            if (iWord >= i0) {
                setName(input.substring(i0, iWord));
                current = -1;
                bStart = false;
                pStart = false;
                bCount = 0;
                pCount = 0;
                int pStartI = 0;
                int pEndI = 0;
                int bStartI = 0;
                int bEndI = 0;


                //i = skipBlanks(input, i);


                boolean firstAdded = false;

                hasNextPossible = 1;
                /*if (i >= input.length() || Character.isWhitespace(input.charAt(i))) {
                    hasNextPossible = 0;
                }
*/
                while (i < input.length()) {
                    if (i == input.length() - 1) {
                        lastToken = true;

                    }

                    if ((input.charAt(i) == '(' || input.charAt(i) == ')') && (!isNotName2() || !isNotType2()))
                        break;
                    if ((input.charAt(i) == '[' || input.charAt(i) == ']') && !isNotName2())
                        break;
                    if (input.charAt(i) == ';') {
                        lastToken = true;
                        break;
                    }

                    if (input.charAt(i) == '[' && !bStart && isNotName2()) {
                        if (!firstAdded)
                            firstAdded = expressions.add(new DataExpression(variable, name));
                        bCount++;
                        bStart = true;
                        bStartI = i;
                        hasNextPossible = 2;
                    } else if (input.charAt(i) == ']' && bStart && isNotName2()) {
                        bEndI = i;
                        bCount--;
                        hasNextPossible = 1;
                        if (bCount < 0)
                            break;
                    } else if (input.charAt(i) == '(' && !pStart && isNotName2OrType2()) {
                        pCount++;
                        pStart = true;
                        pStartI = i;
                        if (!firstAdded)
                            firstAdded = expressions.add(new DataExpression(methodName, name));
                        //Logger.getLogger("parser").log(Level.INFO, "End of call fct arg : " + input.substring(i));
                        hasNextPossible = 2;
                    } else if (input.charAt(i) == ')' && pStart && isNotName2OrType2()) {
                        pCount--;
                        pEndI = i;
                        hasNextPossible = 1;
                        if (pCount < 0)
                            break;
                    } else if (Character.isWhitespace(input.charAt(i)) && hasNextPossible < 2) {
                        hasNextPossible = 0;
                    } else if ((Character.isAlphabetic(input.charAt(i)) || Character.isLetterOrDigit(input.charAt(i)))
                            && !Character.isWhitespace(input.charAt(i))
                            && hasNextPossible == 0) {
                        lastToken = false;
                        setSuccessful(true);
                        return processNext(input, i);
                    } else if (charsAlgebraic.contains("" + input.charAt(i))) {
                        hasNextPossible = 2;
                    }


                    if (input.charAt(i) == ')' && pStart && pCount == 0 && isNotName2OrType2()) {
                        String substring = input.substring(pStartI + 1, pEndI);
                        int parse = parse(substring, 0);
                        recursions--;
                        //Logger.getLogger("parser").log(Level.INFO, "fct arg : " + substring);
                        current = methodCallArgument;
                        expressions.add(new DataExpression(methodCallArgument, substring.substring(0, parse)));
                        i = parse + pStartI + 1;
                        //Logger.getLogger("parser").log(Level.INFO, "After add function argument : " + input.substring(i));
                        //return i;
                    } else if (input.charAt(i) == ']' && bStart && bCount == 0 && isNotName2()) {
                        String substring = input.substring(bStartI + 1, bEndI);
                        current = classArrayAccess;
                        int parse = parse(substring, 0);
                        recursions--;
                        expressions.add(new DataExpression(classArrayAccess, substring.substring(0, parse)));
                        i = bStartI + parse + 1;
                        //Logger.getLogger("parser").log(Level.INFO, "Next tokens remainder (1): " + input.substring(i));
                        bStart = false;
                    } else if (input.charAt(i) == '.' && pCount == 0 && bCount == 0 && isNotName2()) {
                        if (!firstAdded)
                            firstAdded = expressions.add(new DataExpression(variable, name));
                        i = parse(input, i + 1);
                        setSuccessful(true);
                        return processNext(input, i);
                    } else if (lastToken && pCount == 0 && bCount == 0 && isNotName2()) {
                        if (!firstAdded)
                            firstAdded = expressions.add(new DataExpression(variable, name));
                        //i = parse(input, i + 1);
                        setSuccessful(true);
                        return processNext(input, i);
                    }

                    i++;


                }

            }
            if (lastToken) {
                expressions.add(new DataExpression(variable, name));
            }
            setSuccessful(true);
            recursions--;
            return processNext(input, i);

        }

        public boolean isNotName2OrType2() {
            return !(this instanceof TokenType2) && !(this instanceof TokenName2);
        }

        public boolean isNotName2() {
            return !(this instanceof TokenName2);
        }

        public boolean isNotType2() {
            return !(this instanceof TokenType2);
        }


        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }

        public String[] getBrackets() {
            return brackets;
        }

        public void setBrackets(String[] brackets) {
            this.brackets = brackets;
        }

        public void next(StringBuilder sb, DataExpression it, StringAnalyzerJava1.TokenExpression2 current) {
            if (it.type() == StringAnalyzerJava1.TokenExpression2.methodName)
                sb.append(getConstruct().debugString(isDebug, ".")).append(getConstruct().debugString(isDebug, it.expression()));
            else if (it.type() == StringAnalyzerJava1.TokenExpression2.methodCallArgument) {
                sb.append(getConstruct().debugString(isDebug, "("));
                sb.append(getConstruct().debugString(isDebug, it.expression()));
                sb.append(getConstruct().debugString(isDebug, ")"));
            } else if (it.type() == StringAnalyzerJava1.TokenExpression2.classArrayAccess) {
                sb.append(getConstruct().debugString(isDebug, "[")).append(getConstruct().debugString(isDebug, it.expression())).append(getConstruct().debugString(isDebug, "]"));
                if (current.passBrackets > 0) {
                    for (int n = 0; n < current.passBrackets; n += 2) {
                        sb.append(getConstruct().debugString(isDebug, "["));
                        sb.append(getConstruct().debugString(isDebug, it.expression()));
                        sb.append(getConstruct().debugString(isDebug, "]"));
                    }
                }
            } else if (it.type() == StringAnalyzerJava1.TokenExpression2.variable) {
                sb.append(getConstruct().debugString(isDebug, ".")).append(getConstruct().debugString(isDebug, it.expression()));
            } else if (it.type() == StringAnalyzerJava1.TokenExpression2.dotCall) {
                //sb.append("." + it.expression())
            }
        }


        public String toString() {
            TokenExpression2 te = this;
            StringBuilder sb = new StringBuilder();
            sb.append(isDebug ? "{" : "");
            boolean hasNext = true;
            te.expressions.forEach(it -> {
                //println("Type      :" + it.type() + "\nExpression:" + it.expression())
            });
            te.expressions.forEach(it -> {
                next(sb, it, te);
            });
            sb.append(isDebug ? "}" : "");

            if (hasNextToken()) {
                sb.append(getConstruct().debugString(isDebug, " "))
                        .append(getNextToken().getElem(0).toString());
            }
            return sb.toString();
        }
    }


    public class TokenNameDeclaration2 extends TokenName {
        private String[] brackets = new String[7];

        public TokenNameDeclaration2() {
            super();
        }

        /*

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
                            || input.charAt(i) == '_' || input.charAt(i) == '.' //|| Character.isWhitespace(input.charAt(i)
                            || input.charAt(i) == '[')))) {
                        passed = true;
                        i++;
                    }
                    if (i > position1 && Character.isLetter(input.charAt(position1)))
                        iWord = i;
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

                    if (passed && containsNoKeyword(input.substring(position1, iWord))) {
                        if (!input.substring(position1, iWord).isEmpty()) {
                            setName(input.substring(position1, iWord));

                        }
                    }
                    for (int j = 0; j < passBrackets; j += 2) {
                        brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
                    }
                    if (i == position1 || name == null || name.isEmpty()) {
                        setSuccessful(false);
                        return position1;
                    } else {
                        setSuccessful(true);
                        return processNext(input, i);
                    }
                }
        */
        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }

    }
}
