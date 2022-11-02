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

/*

 Vous êtes libre de :

 */
/*__
 *
 */
package one.empty3.library.core.script;

import one.empty3.library.BezierCubique;
import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;

import java.util.ArrayList;
import java.util.Hashtable;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         15 oct. 2011
 */
public class InterpreteFontes implements Interprete {

    private String repertoire;
    private int pos;
    /*
     * (non-Javadoc)
     *
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#constant()
     */

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#getPosition()
     */
    @Override
    public int getPosition() {
        // TODO Auto-generated method stub
        return pos;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#interprete(java
     * .lang.String, int)
     */
    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        Hashtable<Character, ArrayList<Representable>> fonte = new Hashtable<Character, ArrayList<Representable>>();

        ArrayList<Representable> objets;

        Character c;
        LineSegment s;
        BezierCubique bc;
        Point3D p;
        boolean eof1 = false;
        while (eof1) {

            eof1 = true;

            objets = new ArrayList<Representable>();

            InterpreteSegment is = new InterpreteSegment();
            InterpreteBezier ib = new InterpreteBezier();
            InterpretePoint3DBAK ip = new InterpretePoint3DBAK();

            InterpretesBase isb;
            ArrayList<Integer> pattern;
            isb = new InterpretesBase();
            pattern = new ArrayList<Integer>();
            pattern.add(isb.BLANK);
            pattern.add(isb.CARACTERE);
            pattern.add(isb.BLANK);
            pattern.add(isb.LEFTPARENTHESIS);
            pattern.add(isb.BLANK);
            isb.compile(pattern);

            ArrayList<Object> o = isb.read(text, pos);
            pos = isb.getPosition();

            if (o.size() != 5) {
                break;
            }
            c = (Character) o.get(1);

            if (eof1) {
                try {
                    s = (LineSegment) is.interprete(text, pos);
                    pos = is.getPosition();
                    objets.add(s);
                    eof1 = false;
                    continue;
                } catch (InterpreteException e) {
                }
            }
            if (eof1) {
                try {
                    bc = (BezierCubique) ib.interprete(text, pos);
                    pos = ib.getPosition();
                    objets.add(bc);
                    eof1 = false;
                    continue;
                } catch (InterpreteException e) {

                }
            }
            if (eof1) {
                try {
                    p = (Point3D) ip.interprete(text, pos);
                    pos = ip.getPosition();
                    objets.add(p);
                    eof1 = false;
                    continue;
                } catch (InterpreteException e) {

                }
            }

            isb = new InterpretesBase();
            pattern = new ArrayList<Integer>();
            pattern.add(isb.BLANK);
            pattern.add(isb.RIGHTPARENTHESIS);
            pattern.add(isb.BLANK);
            isb.compile(pattern);
            c = (Character) isb.read(text, pos).get(1);
            pos = isb.getPosition();

            fonte.put(c, objets);
        }

        return fonte;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#setConstant(be.
     * ibiiztera.md.pmatrix.pushmatrix.scripts.InterpreteConstants)
     */
    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
