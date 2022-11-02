package one.empty3.library.lang;

import one.empty3.library.core.raytracer.*;

import java.util.HashMap;


public class Tree {
    String name;
    String text;
    HashMap<Scope, Tree> trees;
    Tree parent;

    public Tree() {
    }

    public boolean add(Token token) {
        return false;
    }
}
