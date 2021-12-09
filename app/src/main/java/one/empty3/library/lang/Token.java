package one.empty3.library.lang;

public class Token {
    TokenTypeTxt typeTxt;
    String literal;
    String tt;
    public Token(String tt, String literal, TokenTypeTxt typeTxt) {
        this.typeTxt = typeTxt;
        literal = literal;
        this.tt = tt;
    }
    public enum TokenTypeTxt {
        Space,
        SpecialChar,
        Keyword,
        Name,
        Literal
    }

    public TokenTypeTxt getTypeTxt() {
        return typeTxt;
    }

    public void setTypeTxt(TokenTypeTxt typeTxt) {
        this.typeTxt = typeTxt;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "literal='" + literal + '\'' +
                ", tt='" + tt + '\'' +
                ", typeTxt='" + typeTxt + '\'' +
                '}';
    }
}
