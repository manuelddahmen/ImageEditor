package one.empty3.library.lang;

import java.util.*;

public class Scope {

    public static final int declaration = 1;
    public static final int instruction = 2;
    public static final int assignement = 4;

    public enum TokenType {
        Name, Keyword, Comment, JavadocComment
    }

    ;

    public enum Literal {
        StringLiteral,
        FloatLiteral, DoubleLiteral, CharLiteral
    }

    ;

    public enum InstructionBlock {Unnamed, For, While, Do, Method}

    ;

    public enum Declaration {Package, Imports, Classes, Interfaces, MethodMember, VarMember, Variable, Param}

    ;

    Scope parentScope;
    List<Node> nodes;
    String modifier; // static
    String privacy; // private public protected package
    boolean iDo;
    boolean iWhile;
    boolean iUnnamed;
    boolean iFor;
    boolean iForEach;

    Tree params;
    Tree cond;
    Tree instructions;

}
