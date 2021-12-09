package one.empty3.library.lang;

import one.empty3.library.lang.Token.TokenTypeTxt;

import java.util.*;


public class Node {

    public static final int declaration = 1;
    public static final int instruction = 2;
    public static final int inference   = 4;
    public static final int declarationTypeClass = 1;
    public static final int declarationTypeMethod = 2;
    public static final int declarationTypeScalar = 4;
    public static final int declarationTypeScalarInteger = 8;
    public static final int declarationTypeScalarLongInteger = 16;
    public static final int declarationTypeScalarShortInteger = 32;
    public static final int declarationTypeScalarFloatingPoint = 64;
    public static final int declarationTypeScalarFloatingPointDoublePrecision = 128;
    public static final int declarationTypeScalarCharacter = 256;
    public static final int declarationTypeScalarCharCodeInt = 512;
    public static final int declarationTypeScalarBoolean = 512;
    public static final int declarationTypeObject = 2048;
    public static final int declarationTypeString = 4096;

    int dec;
    Node parent;
    List<Node> children = new ArrayList();

    public Node(List<Token> tokens) {
        int index = 0;
        while((index < tokens.size())) {
            Token t = tokens.get(index);
            switch (t.getTypeTxt()) {
                case Space:
                    break;
                case SpecialChar:
                    switch (t.getLiteral()) {
                        case "=":
                            break;
                        case "<":
                            break;
                        case ">":
                            break;
                        case "!":
                            break;
                        case "+":
                            break;
                        case "-":
                            break;
                        case "*":
                            break;
                        case "/":
                            break;
                        case "(":
                            break;
                        case ")":
                            break;
                        case "{":
                            break;
                        case "}":
                            break;
                        case "[":
                            break;
                        case "]":
                            break;
                        case "&":
                            break;
                        case "|":
                            break;
                        case "\'":
                            break;
                        case "\"":
                            break;
                        case "\\":
                            break;
                        case ":":
                            break;
                        case ",":
                            break;
                        case ";":
                            break;
                        case ".":
                            break;
                        case "?":
                            break;
                    }
                    break;
                case Keyword:
                    break;
                case Name:
                    break;
                case Literal:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + t.getTypeTxt());

            }
            index++;
        }
    }

    public boolean canExec() {
        return false;
    }

    public void run() {
        int index = 0;
        TokenType tt = children.get(index).tt;


        while(!isExiting()) {
            if(children.get(index).canExec())
                children.get(index).run();
            else {
                addToDeclaration(children.get(index));
            }
        }
    }

    private void addToDeclaration(Node node) {

    }

    private boolean isExiting() {
        return false;
    }


    public enum TokenType {Name, Keyword,  Comment, JavadocComment,
        Literal};
    public enum Literal {StringLiteral,
        FloatLiteral, DoubleLiteral, CharLiteral };
    public enum InstructionBlock { Unnamed, For, While, Do, Method };
    public enum Declaration {Package, Imports, Classes, Interfaces, MethodMember, VarMember, Variable, Param};
    TokenType tt;
    public Node(TokenType tt, Literal l, 
            String text, InstructionBlock ib,
            Declaration d) {
        this.tt = tt;
    }

}
