package one.empty3.library.lang;

import java.util.*;
public class Scope {
    
    public static final int declaration = 1;
    public static final int instruction = 2;
    public static final int assignement = 4;
    public HashMap<TokenType, List<Object>> follows = new HashMap<>();
    public HashMap<TokenType, List<Object>> followsIn = new HashMap<>();
    public HashMap<TokenType, List<Object>> followsOut = new HashMap<>();


    {
        follows.put(TokenType.Name, new ArrayList<>());
        follows.get(TokenType.Name).add(TokenType.ParamsDeclaration);
        follows.get(TokenType.Name).add(TokenType.Params);
        follows.get(TokenType.Name).add(TokenType.MethodBody);
        follows.get(TokenType.Name).add(TokenType.EndOfInstruction);
        follows.get(TokenType.Name).add(TokenType.Operator);
        follows.get(TokenType.Name).add(TokenType.Coma);
        follows.get(TokenType.Name).add(TokenType.CallMethodDot);
        followsIn.put(TokenType.ParamsDeclaration, new ArrayList<>());

    }
    public enum TokenType {
        Name, Keyword,  Comment,
        JavadocComment,StringLiteral,
        FloatLiteral, DoubleLiteral, CharLiteral,
        Unnamed, For, While, Do, Method,
        Package, Imports, Classes, Interfaces,
        MethodMember, VarMember, Variable, ParamsDeclaration,
        Params,
        EndOfInstruction, Operator, MethodBody, Coma,
        CallMethodDot, ParamDeclaration, Param};
    
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
