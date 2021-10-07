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

import one.empty3.library.Barycentre;
import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;

import java.util.ArrayList;

public class InterpreteHomothetie implements Interprete {

    private String rep;
    private Representable r;
    private int position;

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        Barycentre positionObject = new Barycentre();

        InterpretesBase ib;
        ArrayList<Integer> pattern;

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.AROBASE);

        ib.compile(pattern);

        ArrayList<Object> o = ib.read(text, pos);

        if (o.size() == 1) {
            InterpretePoint3D ip3 = new InterpretePoint3D();

            Point3D p = (Point3D) ip3.interprete(text, pos);

            positionObject.position = p;
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.MULTIPLICATION);
        pattern.add(ib.DECIMAL);

        ib.compile(pattern);

        o = ib.read(text, pos);

        if (o.size() == 2) {
            double m = (Double) o.get(1);

            positionObject.agrandissement = m;
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.PERCENT);

        ib.compile(pattern);

        o = ib.read(text, pos);

        if (o.size() == 1) {
            InterpreteMatrix33 im33 = new InterpreteMatrix33();

            Matrix33 m33 = (Matrix33) im33.interprete(text, pos);

            positionObject.rotation = m33;
        }
        this.position = pos;

        return positionObject;
    }

    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRepertoire(String r) {
        this.rep = r;

    }

    public void setRepresentable(Representable r) {
        this.r = r;
    }

}
