package one.empty3.library.shader.language.cpp;

public class Operation {
    String[] uo = new String[]{"+", "-", "!"};
    String[] bo = new String[]{"+", "-", "*", "/",
            "==", "!=", "<", ">", "<=", ">=", "&", "|",
            "&&", "||"};
    String[] enclosingChars = new String[]{"(", ")"};
    String[] ao = new String[]{"=", "+=", "-=", "/="};
}
