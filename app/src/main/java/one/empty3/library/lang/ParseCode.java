package one.empty3.library.lang;

import java.util.*;

public class ParseCode {
    List<Token> tokens = new ArrayList();
    List<Node> nodes = new ArrayList();
    String special = "!%*&()+\\|/[]{}<>=.:,;?'\"";
    String[] keywords = new String[]{
            ""
    };
    String braces = "[](){}";
    String bracesCurrentQueue = "";
    String code;
    Tree tree;

    public ParseCode() {
        tree = new Tree();
        //removeComments();
        // separer en tokens typés
    }

    /* public boolean addToken() {
        Token t = new Token();
        return true;
    }
    */
    int i = 0;
    String brut;
    String uncomm;
    int start, end;

    public void removeComments() {
        StringBuilder sb = new StringBuilder();
        // Caractère par caractère
        int comm = 0;
        // Si /* sauter jusqu'à */
        // Si // sauter jusqu'à ¶

        for (i = 0; i < brut.length(); i++) {
            if (comm == 0 && brut.charAt(i) == '"') {
                comm = 3;
                i++;
            }
            if (comm == 3 && brut.charAt(i) == '"') {
                comm = 0;
                i++;
            }
            if (comm == 3 && brut.charAt(i) == '\\') {
                i += 2;
            }
            if (comm == 0 && brut.charAt(i) == '\'') {
                comm = 4;
                i++;
            }
            if (comm == 4 && brut.charAt(i) == '\'') {
                comm = 0;
                i++;
            }
            if (comm == 4 && brut.charAt(i) == '\\') {
                i += 2;
            }
            if (comm == 0 && brut.charAt(i) == '/'
                    && i < brut.length() - 1
                    && brut.charAt(i + 1) == '*')
                comm = 1;
            if (comm == 0 && brut.charAt(i) == '/'
                    && i < brut.length() - 1
                    && brut.charAt(i + 1) == '/') {

                comm = 2;
                while (i < brut.length()) {
                    if (brut.charAt(i) == '\n') {
                        comm = 0;
                        i++;
                    }
                }
            }
            if (comm == 1 && brut.charAt(i) == '*'
                    && i < brut.length() - 1
                    && brut.charAt(i + 1) == '/') {
                comm = 0;
                i += 2;
            }
            if (comm == 0)
                for (int brace = 0; brace < braces.length() / 2; brace++) {
                    if (braces.charAt(brace * 2) == brut.charAt(i)) {
                        //! lastBrace
                        bracesCurrentQueue
                                = bracesCurrentQueue + brut.charAt(i);
                    }
                    if (braces.charAt(brace * 2 + 1) == brut.charAt(i)) {
                        //! lastBrace
                        if (bracesCurrentQueue.length() <= 1) {
                            bracesCurrentQueue
                                    = "";
                        } else {
                            bracesCurrentQueue
                                    = bracesCurrentQueue.substring
                                    (0, bracesCurrentQueue.length() - 1);
                        }

                    }
                }


            if (comm > 0)

                end++;

            if (comm == 0) {

                start = i;
                end = i;
            }
            if (comm == 0) {


                // String forbidden = "\"\'\n\r/";
                uncomm = brut.substring
                        (start, end);
                boolean passed = parseSpace() ||
                        parseSpecialChar() ||
                        parseKeyword() ||
                        parseName() ||
                        parseLiteral();


                //sb.append(brut.charAt(i));

            }


            i++;
        }


    }

    public boolean parseSpace() {
        int pos = i;
        boolean b = false;
        do {
            char a = uncomm.charAt(pos);
            if (a == ' ' || a == '\n' || a == '\t' || a == '\r') {
                pos++;
                b = true;
            }
        } while (!b && i < uncomm.length());
        if (b) {
            tokens.add(new Token("space",
                    uncomm.substring(i, pos)));
            pos = i;
            return true;
        }
        return false;
    }

    public boolean parseSpecialChar() {
        boolean b = false;
        while (isSpecialChar(uncomm, i)) {
            char special = uncomm.charAt(i);
            i++;
            b = true;
        }
        if (b)
            return true;
        return false;
    }

    public boolean parseKeyword() {
        char a = uncomm.charAt(i);
        int j = 0;
        while (Character.isLetter(a)) {
            j++;
            a = uncomm.charAt(i + j);
        }
        if (isSpecialChar(uncomm, i + j) ||
                isWhitespace(uncomm, i + j)) {
            List<String> list = Arrays.asList(keywords);

            String k = uncomm.substring(i, i + j);
            if (k.length() > 0 && list.contains(k))
                tokens.add(new Token("keyword",
                        k));
            return true;
        }
        return false;
    }

    public boolean parseName() {
        char a = uncomm.charAt(i);
        int j = 0;
        while (Character.isLetter(a) || (j > 0
                && (Character.isLetterOrDigit(a) ||
                a == '_'))) {
            j++;
            a = uncomm.charAt(i + j);
        }
        List<String> list = Arrays.asList(keywords);

        String k = uncomm.substring(i, i + j);
        if (k.length() > 0 && !list.contains(k)) {
            tokens.add(new Token("name",
                    k));
            i = i + j;
            return true;
        }
        return false;
    }

    public boolean isSpecialChar(String uncomm,
                                 int pos) {
        return special.indexOf(uncomm.charAt(pos)) >= 0;

    }

    public boolean isWhitespace(String uncomm,
                                int pos) {
        char a = uncomm.charAt(pos);
        if (a == ' ' || a == '\n' || a == '\t' || a == '\r') {
            // i=i+j;
            //  i=i+pos;

            return true;
        }
        return false;
    }

    public int nextWhitespace(String uncomm,
                              int pos) {
        boolean b = false;
        do {
            char a = uncomm.charAt(pos);
            if (a == ' ' || a == '\n' || a == '\t' || a == '\r') {
                pos++;
                b = true;
            }
        } while (!b && pos < uncomm.length());
        return pos;
    }

    public int nextChar(String uncomm,
                        int pos) {

        char a = uncomm.charAt(pos);
        while ((a == ' ' || a == '\n' || a == '\t' || a == '\r')) {
            pos++;
        }

        return pos;
    }

    public boolean parseLiteral() {
        int pos = i;
        // bool
        if (uncomm.substring(i, i + "false".length()).equals("false")) {
            tokens.add(new Token("boolean:false",
                    uncomm.substring(i, i + "false".length())));

            return true;
        }
        if (uncomm.substring(i, i + "true".length()).equals("true")) {
            tokens.add(new Token("boolean:true",
                    uncomm.substring(i, i + "true".length())));
            return true;
        }

        //tokens.add(new Token("float|double|string|int|char|boolean|long",
        // k));
        return false;
    }

    public void block() {
    }

    public void tree() {
    }

    public void reduce() {
    }

    public void map() {
    }

    public void result() {
    }

    public static void main(String[] args) {
    }
}
