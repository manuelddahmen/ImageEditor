package one.empty3.library.lang;

import java.io.File;
import java.util.List;

public class Runner {
    public static void main(String [] args) {
        Classes classes = new Classes();
        File zip = new File(args[0]);
        ParseCode parseCode = new ParseCode();
        List<Token> tokens = parseCode.parseFile(zip);
        classes.add(new Node(tokens));
        run(classes);
    }

    private static void run(Classes classes) {
        Node node = searchForMainMethod(classes);
        if(node.canExec()) {
            node.run();
        }
    }

    private static Node searchForMainMethod(Classes classes) {
        return new EntryPoint(null);
    }
}
