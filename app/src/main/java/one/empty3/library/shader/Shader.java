package one.empty3.library.shader;

import java.io.*;
import java.nio.file.*;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.*;

/***
 * vn(int n)
 * vn(vn...)
 * vn(double... ) 
 * glsl light. like.
 *
 * chaque token doit etre identifie a
 * un type et plus
 */

public class Shader {
    String keywords = "attribute uniform varying layout centroid flat smooth noperspective patch sample subroutine in out inout invariant discard mat2 mat3 mat4 dmat2 dmat3 dmat4 mat2x2 mat2x3 mat2x4 dmat2x2 dmat2x3 dmat2x4 mat3x2 mat3x3 mat3x4 dmat3x2 dmat3x3 dmat3x4 mat4x2 mat4x3 mat4x4 dmat4x2 dmat4x3 dmat4x4 vec2 vec3 vec4 ivec2 ivec3 ivec4 bvec2 bvec3 bvec4 dvec2 dvec3 dvec4 uvec2 uvec3 uvec4 lowp mediump highp precision sampler1D sampler2D sampler3D samplerCube sampler1DShadow sampler2DShadow samplerCubeShadow sampler1DArray sampler2DArray sampler1DArrayShadow sampler2DArrayShadow isampler1D isampler2D isampler3D isamplerCube isampler1DArray isampler2DArray usampler1D usampler2D usampler3D usamplerCube usampler1DArray usampler2DArray sampler2DRect sampler2DRectShadow isampler2DRect usampler2DRect samplerBuffer isamplerBuffer usamplerBuffer sampler2DMS isampler2DMS usampler2DMS sampler2DMSArray isampler2DMSArray usampler2DMSArray samplerCubeArray samplerCubeArrayShadow isamplerCubeArray usamplerCubeArray";
    String vq = "attribute const uniform varying";
    String centroid = "centroid";
    String loop = " break continue do for while";
    String ifelse = "if else";
    String param = "in out inout";
    String scalar = " float int void bool true false";
    String invariant = "invariant";
    String returns = "discard return";
    String fmath = "cross dot sum fract floor round distance min max exp pow sin cos tan acos asin atan";
    String mat = "mat2 mat3 mat4";
    String mat2 = "mat2x2 mat2x3 mat2x4";
    String mat3 = "mat3x2 mat3x3 mat3x4";
    String mat4 = "mat4x2 mat4x3 mat4x4";
    String vec = "vec2 vec3 vec4 ivec2 ivec3 ivec4 bvec2 bvec3 bvec4";
    String sampler = "sampler1D sampler2D sampler3D sammpler samplerCube";
    String sash = "sampler1DShadow sampler2DShadow";
    String struct = "struct";
    List<String> lines = new ArrayList<>();

    enum DataType {Void, Scalar, Object}

    // Datadim?
    enum ParseContext {ClassDef, MemberDef, MethodDef, ListArgs, MethodBodyDef, VariableDef, Instruction, Operation}

    public ParseContext context;

    class SymbolTreeNode {
        public SymbolTreeNode(String name,
                              Items itype, String text, Object value) {
            this.name = name;
            this.itemType = itype;
            this.jValue = value;

        }

        public void comment(String value) {
        }

        public void function(String name, String itype, SymbolTreeNode values) {
        }

        public void variable(String name, String itype, Expression expression) {
        }

        public void method(String name, String[] paramNames, String[] paramValues) {
        }

        public void classType(String superclass) {
        }

        public List<SymbolTreeNode> getChildren() {
            return children;
        }

        String name;
        Items itemType;
        String text;
        Object jValue;
        List<SymbolTreeNode> children = new ArrayList<>();
    }

    class Assignment extends SymbolTreeNode {
        public Assignment(String name,
                          Items itype, String text, Object value) {
            super(name, itype, text, value);

        }
    }

    class MethodCall extends SymbolTreeNode {
        public MethodCall(String name,
                          Items itype, String text, Object value) {

            super(name, itype, text, value);
        }
    }

    class Value extends SymbolTreeNode {
        public Value(String name,
                     Items itype, String text, Object value) {
            super(name, itype, text, value);
        }
    }

    class Expression extends SymbolTreeNode {
        public Expression(String name,
                          Items itype, String text, Object value) {
            super(name, itype, text, value);
        }
    }

    class Var extends SymbolTreeNode {
        public Var(String name,
                   Items itype, String text, Object value) {
            super(name, itype, text, value);
        }
    }

    class Tree {
        SymbolTreeNode root;

        Iterator<SymbolTreeNode> iterate() {
            return null;
        }

        SymbolTreeNode current;

        public void add(SymbolTreeNode s) {
            current.getChildren().add(s);

        }
    }

    enum Type {Line, Block, Doc}

    enum Items {Comment, Macro, Function, FunctionArgumentList, Keyword, FunctionDeclaration, FunctionBody, MemberVariable, ClassDeclaration, VariableName, VariableType, Literal, Scalar, ClassName}

    class Comment extends SymbolTreeNode {
        public Comment(String name,
                       Items itype, String text, Object value) {
            super(name, itype, text, value);
        }
    }

    enum MacroType {Macro, Include}

    class Macro extends SymbolTreeNode {
        public Macro(String name,
                     Items itype, String text, Object value) {
            super(name, itype, text, value);
        }

        String name;
        String def;
    }

    class Class extends SymbolTreeNode {
        public Class(String name,
                     Items itype, String text, Object value) {
            super(name, itype, text, value);
        }
    }

    public int readBlank(String shStr, int i) {
        int j = 0;
        while (Character.isWhitespace(shStr.charAt(i)) || shStr.charAt(j) == '\t' || shStr.charAt(j) == '\n'
                || shStr.charAt(j) == '\r') {
            j++;
        }
        return j;
    }

    public int readChar(String shStr, char c, int i) {
        return shStr.charAt(i) == c ? i + 1 : i;
    }

    public int readComment(String shStr, int i) {
        if (shStr.charAt(i) == '/') {
            i++;
            if (shStr.charAt(i) == '/') {
                while (shStr.charAt(i) != '\n')
                    i++;
                return i;
            }
            if (shStr.charAt(i) == '*') {
                i++;
                while (i < shStr.length() - 1 && shStr.charAt(i) != '*' && shStr.charAt(i + 1) == '/') {
                    i++;
                }
                i++;
                return i;
            }
        }
        return i;
    }

    enum LiteralType {S, D, L, I, F, O,}

    class Literal {


    }

    private Tree tree;

    public int parseString(String shStr, int i) {
        if (shStr.charAt(i) == '\"') {
            i++;
            while (shStr.charAt(i) != '\"') {
                if (shStr.charAt(i) == '\\')
                    i++;
                i++;
            }
        }
        return i;
    }

    public int parseInt(String shStr, int i) {
        return i;

    }

    public int parseFloat(String shStr, int i) {
        return i;

    }

    public int parseBoolean(String shStr, int i) {
        if (shStr.substring(i, i + 4).equals("true"))
            return i + 4;
        if (shStr.substring(i, i + 4).equals("false"))
            return i + 4;
        return i;
    }

    public int parseDouble(String shStr, int i) {
        return i;

    }

    public int readContainer(String shStr, int i) {
        return i;
    }

    public int readLiteral(String shStr, int i) {
        int j = i;
        j = parseString(shStr, i);
        if (i == j)
            j = parseInt(shStr, i);
        if (i == j)
            j = parseFloat(shStr, i);
        if (i == j)
            j = parseDouble(shStr, i);
        if (i == j)
            j = parseBoolean(shStr, i);
        return j;
    }

    public int parseArgumentList(String shStr, int i) {
        return i;

    }

    public int parseMethodBody(String shStr, int i) {
        return i;

    }

    public int readEquals(String shStr, int i) {
        return i;
    }

    public int readOperation(String shStr, int i) {
        return i;
    }

    public int readMethodCall(int i) {
        return i;
    }

    public List<String> split(String shStr) {


        int i = 0;
        int j = -1;
        while (j != i) {
            j = i;
            j = readBlank(shStr, i);
            if (j == i)
                j = readComment(shStr, i);
            else
                lines.add(shStr.substring(i, j));
            if (j == i)
                j = parseString(shStr, i);
            else
                lines.add(shStr.substring(i, j));
            if (j == i)
                j = readToken(shStr, i);
            else
                lines.add(shStr.substring(i, j));
            if (j == i)
                return null;
            i = j;
        }
        return lines;
    }

    public int readMacro(String shStr, int i) {
        return i;
    }

    public int readPredefinedDeclaration(int i) {
        ArrayList<String> dec = new ArrayList<>();
        dec.add("uniform");
        dec.add("varying");
        if (dec.contains(lines.get(i))) {
            tree.current.itemType = Items.MemberVariable;
            tree.current.getChildren().add(new SymbolTreeNode("predef member variable attribute",
                    Items.Keyword, lines.get(i), null));
            return i + 1;
        }
        return i;
    }

    public int readMethod(int i) {
        return i;
    }

    public Expression buildExpression(int i) {
        return null;
    }

    public int readVariableDeclaration(int i) {

// type1 var1[=expression][,var2[=expression]][varn[=exprn]];
        String itype = lines.get(i);
        String varName = lines.get(i + 1);
        SymbolTreeNode stn;
        Expression e = null;
        if (lines.get(i + 2).equals("=")) {

            e = buildExpression(i + 3);

        } else {

        }
        stn = new Var(varName,
                Items.Scalar, "", e);// scalar or class instance
        tree.add(e);

        return i;
    }

    public int readInstruction(int i) {
        return i;
    }

    public int readToken(String shStr, int i) {
        while (i < shStr.length() && readBlank(shStr, i) == i)
            i++;
        return i;
    }

    public void buildTree(List<String> lines) {
    }

    public boolean splitInTypes(String shStr) {
        int i = 0;
        lines = split(shStr);
        int posTry = -1;
        buildTree(lines);


        return true;
    }

    List<Tree> shaderFiles = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Shader(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (String sf : fileOrDirectory.list()) {
                try {
                    File f = new File(fileOrDirectory.getAbsolutePath() + File.separator + sf);
                    tree = new Tree();
                    String shStr = stripComment(new String(Files.readAllBytes(Paths.get(f.getAbsolutePath()))));

                    splitInTypes(shStr);

                    shaderFiles.add(tree);
                    //  shStr = replaceMacro(shStr);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    }

    public void setOutput(File directory) {
    }

    public void shaders(File file) {
    }

    public String stripComment(String brut) {
        return brut;
    }

    public String replaceMacro(String shStr) {
        return shStr;
    }

    //   public String categoriser(String contentStripped) {}
//    public String parseHeaders() {}
    //   public String parseMemberVars() {}
    //  public String parseClass() {}
    // public String parseMemberMethod() {}
//    public String parseMethodDeclaration(String name, Object... arguments) {}
    //   public String parseMethodImplementation() {}
    public void errorBeforeExecution(String shStr, int charPos) {
    }

    public void errorExecute(String type, int line) {
    }

    public void runCode() {
    }

    public Bitmap getOutput() {
        return null;
    }
}
