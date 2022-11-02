/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */
package one.empty3.library.core.script;

import java.util.ArrayList;

public class InterpretesBase {

    public final int BLANK = 0;
    public final int DECIMAL = 1;
    public final int INTEGER = 2;
    public final int LEFTPARENTHESIS = 3;
    public final int RIGHTPARENTHESIS = 4;
    public final int COMA = 5;
    public final int CARACTERE = 6;
    public final int DIESE = 7;
    public final int AROBASE = 8;
    public final int MULTIPLICATION = 9;
    public final int PERCENT = 10;
    int size = 0;
    boolean elementParsed = false;
    private int pos;
    private ArrayList<Integer> pattern;
    private ArrayList<Object> objects = new ArrayList<Object>();

    public InterpretesBase() {
        objects = new ArrayList<Object>();
        pos = 0;
    }

    public void compile(ArrayList<Integer> pattern) {
        this.pattern = pattern;
    }

    public ArrayList<Object> get() {
        return objects;
    }

    public int getPosition() {
        return pos;
    }

    public void setPosition(int pos) {
        this.pos = pos;
    }

    private Object read(Integer integer2, String substring) {
        size = 0;
        elementParsed = false;
        if (substring.length() == 0 & integer2 != BLANK) {
            return null;
        } else if (substring.length() == 0 & integer2 == BLANK) {
            elementParsed = true;
            return " ";
        }
        switch (integer2) {
            case BLANK:
                int pos = 0;
                char c = substring.charAt(0);
                while (pos < substring.length() && (c == ' ' | c == '\n' | c == '\t' | c == '\r')) {
                    pos++;
                    if (pos < substring.length()) {
                        c = substring.charAt(pos);
                    } else {
                        break;
                    }

                }
                size = pos;
                elementParsed = true;
                return " ";
            case DECIMAL:
                pos = 0;
                c = substring.charAt(0);
                while (pos < substring.length() && ((c >= '1' & c <= '9') | c == '0' | c == '.' | c == '-')) {
                    pos++;
                    size = pos;
                    if (pos < substring.length()) {
                        c = substring.charAt(pos);
                    } else {
                        break;
                    }
                    elementParsed = true;
                }
                if (pos < substring.length() - 2) {
                    c = substring.charAt(pos);
                    if (pos < substring.length() && (c == 'E' || c == 'e')) {
                        pos++;
                        c = substring.charAt(pos);
                        while (pos < substring.length() && ((c >= '1' & c <= '9') | c == '0') | c == '-') {
                            pos++;
                            size = pos;
                            if (pos < substring.length()) {
                                c = substring.charAt(pos);
                            } else {
                                break;
                            }
                            elementParsed = true;
                        }

                    }
                }
                if (elementParsed) {
                    return Double.parseDouble(substring.substring(0, pos));
                } else {
                    return null;
                }

            case INTEGER:
                pos = 0;
                c = substring.charAt(0);
                while (pos < substring.length() & ((c >= '1' & c <= '9') | c == '0' | c == '-')) {
                    pos++;
                    size = pos;
                    if (pos < substring.length()) {
                        c = substring.charAt(pos);
                    } else {
                        break;
                    }
                    elementParsed = true;
                }
                if (elementParsed) {
                    return Integer.parseInt(substring.substring(0, pos));
                } else {
                    return null;
                }
            case COMA:
                pos = 0;
                if (pos < substring.length() & substring.charAt(0) == ',') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(COMA);
                } else if (pos < substring.length()) {
                    size = 0;
                    elementParsed = true;
                    return new CODE(COMA);
                }
                return null;
            case LEFTPARENTHESIS:
                pos = 0;
                if (pos < substring.length() & substring.charAt(0) == '(') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(LEFTPARENTHESIS);
                }
                return null;
            case RIGHTPARENTHESIS:
                pos = 0;
                if (pos < substring.length() & substring.charAt(0) == ')') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(RIGHTPARENTHESIS);
                }
                return null;
            case DIESE:
                pos = 0;
                if (pos < substring.length() & substring.charAt(0) == '#') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(DIESE);
                }
                return null;
            case AROBASE:
                pos = 0;
                if (pos < substring.length() & substring.charAt(0) == '@') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(AROBASE);
                }
                return null;
            case MULTIPLICATION:
                pos = 0;
                if (pos < substring.length() & substring.charAt(0) == '*') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(MULTIPLICATION);
                }
                return null;
            case PERCENT:
                pos = 0;
                if (pos < substring.length() & substring.charAt(0) == '%') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(PERCENT);
                }
                return null;
            case CARACTERE:
                pos = 0;
                if (pos < substring.length()) {
                    return substring.charAt(0);
                }
                return null;
        }
        return substring;

    }

    public ArrayList<Object> read(String chaine, int pos) throws InterpreteException {
        int ppos = 0;
        while (ppos < pattern.size()) {
            Object o = read(pattern.get(ppos), chaine.substring(pos));
            if (elementParsed && o != null) {
                objects.add(o);
                ppos++;
                pos += size;
                this.pos = pos;
            } else {
                if (o != null) {
                    throw new InterpreteException("Parser Error : " + o.toString());
                } else {
                    throw new InterpreteException("Parser Error : ");
                }
            }

        }
        return objects;
    }

    public class CODE {

        private int CODE;

        public CODE(int cODE) {
            super();
            CODE = cODE;
        }

        public int getCODE() {
            return CODE;
        }

        public void setCODE(int cODE) {
            CODE = cODE;
        }
    }

}
