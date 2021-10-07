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
package one.empty3.library.core.script;

import one.empty3.library.*;

import java.util.ArrayList;

/*__
 * @author manuel
 */
public class InterpreteModdificateur implements Interprete {

    private String repertoire;
    private int pos;

    public InterpreteConstants constant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getPosition() {
        return pos;
    }

    public Object interprete(String text, int pos) throws InterpreteException {
        boolean set_mr = false;
        boolean set_mh = false;
        boolean set_mt = false;
        MODHomothetie mh = new MODHomothetie();
        MODRotation mr = new MODRotation();
        MODTranslation mt = new MODTranslation();

        ArrayList<Integer> pattern;
        InterpretesBase ib;
        MODObjet mo = new MODObjet();

        // Interprete homothetie
        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.MULTIPLICATION);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        if (ib.read(text, pos).get(1) instanceof InterpretesBase.CODE) {
            pos = ib.getPosition();
            ib = new InterpretesBase();
            pattern = new ArrayList<Integer>();
            pattern.add(ib.BLANK);
            pattern.add(ib.LEFTPARENTHESIS);
            pattern.add(ib.BLANK);
            ib.compile(pattern);
            if (ib.read(text, pos).get(1) instanceof InterpretesBase.CODE) {
                pos = ib.getPosition();
                InterpretePoint3D pp = new InterpretePoint3D();
                mh.centre((Point3D) pp.interprete(text, pos));

                pos = pp.getPosition();

                ib = new InterpretesBase();
                pattern = new ArrayList<Integer>();
                pattern.add(ib.BLANK);
                pattern.add(ib.DECIMAL);
                pattern.add(ib.BLANK);
                pattern.add(ib.RIGHTPARENTHESIS);
                pattern.add(ib.BLANK);

                ib.compile(pattern);
                ib.read(text, pos);
                pos = ib.getPosition();

                mh.facteur((Double) ib.read(text, pos).get(1));

                set_mh = true;
            }

        }

        // Interprete rotation
        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.DIESE);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        if (ib.read(text, pos).get(1) instanceof InterpretesBase.CODE) {
            pos = ib.getPosition();
            ib = new InterpretesBase();
            pattern = new ArrayList<Integer>();
            pattern.add(ib.BLANK);
            pattern.add(ib.LEFTPARENTHESIS);
            pattern.add(ib.BLANK);
            ib.compile(pattern);
            if (ib.read(text, pos).get(1) instanceof InterpretesBase.CODE) {
                pos = ib.getPosition();
                Point3D vAxe[] = new Point3D[3];
                for (int vecteur = 0; vecteur < 3; vecteur++) {
                    InterpretePoint3D pp = new InterpretePoint3D();
                    vAxe[vecteur] = (Point3D) pp.interprete(text, pos);

                    pos = pp.getPosition();
                }

                mr.matrice(new Matrix33(vAxe));

                InterpretePoint3D pp = new InterpretePoint3D();
                mr.centre((Point3D) pp.interprete(text, pos));

                ib = new InterpretesBase();
                pattern = new ArrayList<Integer>();
                pattern.add(ib.BLANK);
                pattern.add(ib.RIGHTPARENTHESIS);
                pattern.add(ib.BLANK);

                ib.compile(pattern);
                ib.read(text, pos);
                pos = ib.getPosition();

                set_mr = true;
            }

        }

        // Interprete translation
        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.AROBASE);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        if (ib.read(text, pos).get(1) instanceof InterpretesBase.CODE) {
            pos = ib.getPosition();
            ib = new InterpretesBase();
            pattern = new ArrayList<Integer>();
            pattern.add(ib.BLANK);
            pattern.add(ib.LEFTPARENTHESIS);
            pattern.add(ib.BLANK);
            ib.compile(pattern);
            if (ib.read(text, pos).get(1) instanceof InterpretesBase.CODE) {
                pos = ib.getPosition();
                InterpretePoint3D pp = new InterpretePoint3D();
                mt.translation((Point3D) pp.interprete(text, pos));

                ib = new InterpretesBase();
                pattern = new ArrayList<Integer>();
                pattern.add(ib.BLANK);
                pattern.add(ib.RIGHTPARENTHESIS);
                pattern.add(ib.BLANK);

                ib.compile(pattern);
                ib.read(text, pos);
                pos = ib.getPosition();

                set_mt = true;
            }

        }
        mo.modificateurs(mr, mt, mh);

        this.pos = pos;

        return mo;
    }

    public void setConstant(InterpreteConstants c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }
}
