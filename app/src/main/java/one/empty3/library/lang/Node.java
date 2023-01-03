/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.library.lang;

import java.util.*;


public class Node {

    public static final int declaration = 1;
    public static final int instruction = 2;
    public static final int assignement = 4;

    int dec;
    Node parent;
    List<Node> children = new ArrayList();


    public enum TokenType {
        Name, Keyword, Comment, JavadocComment,
        Literal
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
    TokenType tt;

    public Node(TokenType tt, Literal l,
                String text, InstructionBlock ib,
                Declaration d) {
        this.tt = tt;
    }
}
