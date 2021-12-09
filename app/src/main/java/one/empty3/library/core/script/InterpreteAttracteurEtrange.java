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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library.core.script;

import one.empty3.library.core.extra.AttracteurEtrange;

import java.util.ArrayList;

/*__
 * @author Manuel
 */
public class InterpreteAttracteurEtrange implements Interprete {

    private int pos;
    private String repertoire;

    @Override
    public InterpreteConstants constant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern;

        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);

        ib.read(text, pos);
        pos = ib.getPosition();

        ArrayList<Integer> patternBL = new ArrayList<Integer>();
        patternBL.add(ib.BLANK);
        ArrayList<Integer> patternDEC = new ArrayList<Integer>();
        patternDEC.add(ib.DECIMAL);

        InterpretesBase ibBL = new InterpretesBase();
        InterpretesBase ibDE = new InterpretesBase();
        ibBL.compile(patternBL);
        ibDE.compile(patternDEC);
        ArrayList<Object> o = new ArrayList<Object>();

        o.add(ibDE.read(text, pos).get(0));
        pos = ibDE.getPosition();
        ibBL.read(text, pos);
        pos = ibBL.getPosition();
        ibBL = new InterpretesBase();
        ibDE = new InterpretesBase();
        ibBL.compile(patternBL);
        ibDE.compile(patternDEC);
        o.add(ibDE.read(text, pos).get(0));
        pos = ibDE.getPosition();
        ibBL.read(text, pos);
        pos = ibBL.getPosition();
        ibBL = new InterpretesBase();
        ibDE = new InterpretesBase();
        ibBL.compile(patternBL);
        ibDE.compile(patternDEC);
        o.add(ibDE.read(text, pos).get(0));
        pos = ibDE.getPosition();
        ibBL.read(text, pos);
        pos = ibBL.getPosition();
        ibBL = new InterpretesBase();
        ibDE = new InterpretesBase();
        ibBL.compile(patternBL);
        ibDE.compile(patternDEC);
        o.add(ibDE.read(text, pos).get(0));
        pos = ibDE.getPosition();
        ibBL.read(text, pos);
        pos = ibBL.getPosition();

        Double A = (Double) o.get(0);
        Double B = (Double) o.get(1);
        Double C = (Double) o.get(2);
        Double D = (Double) o.get(3);

        pattern = new ArrayList<Integer>();
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);

        ib.read(text, pos);

        this.pos = ib.getPosition();

        return new AttracteurEtrange(A, B, C, D);

    }

    @Override
    public void setConstant(InterpreteConstants c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
