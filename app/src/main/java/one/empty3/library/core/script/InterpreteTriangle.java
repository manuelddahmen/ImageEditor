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

import android.graphics.Color;

import one.empty3.library.*;


import java.util.ArrayList;

public class InterpreteTriangle implements Interprete {

    private String repertoire;
    private int pos = 0;

    @Override
    public InterpreteConstants constant() {
        return null;
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        ArrayList<Object> objects = new ArrayList<Object>();
        InterpretePoint3D pp = new InterpretePoint3D();
        InterpreteCouleur pc = new InterpreteCouleur();
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();
        objects.add(pp.interprete(text, pos));
        pos = pp.getPosition();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.COMA);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();
        objects.add(pp.interprete(text, pos));
        pos = pp.getPosition();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.COMA);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();
        objects.add(pp.interprete(text, pos));
        pos = pp.getPosition();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.COMA);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();
        objects.add(pc.interprete(text, pos));
        pos = pc.getPosition();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();
        this.pos = pos;
        return new TRI((Point3D) objects.get(0), (Point3D) objects.get(1), (Point3D) objects.get(2), (Color) objects.get(3));
    }

    @Override
    public void setConstant(InterpreteConstants c) {

    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
