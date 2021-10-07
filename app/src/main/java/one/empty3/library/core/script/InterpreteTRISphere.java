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

import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.tribase.TRISphere;

import java.util.ArrayList;

/*__
 * @author DAHMEN Manuel
 *         <p>
 *         dev
 * @date 23-mars-2012
 */
public class InterpreteTRISphere implements Interprete {

    private String repertoire;

    private int pos;

    public InterpreteConstants constant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getPosition() {
        return pos;
    }

    public Object interprete(String text, int pos) throws InterpreteException {
        Point3D ps = null;

        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();

        InterpretePoint3D pp = new InterpretePoint3D();
        ps = (Point3D) pp.interprete(text, pos);
        pos = pp.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        ib = new InterpretesBase();
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.DECIMAL);
        pattern.add(ib.BLANK);
        ib = new InterpretesBase();
        ib.compile(pattern);
        Double r = (Double) ib.read(text, pos).get(1);
        pos = ib.getPosition();

        TextureCol tc = null;

        InterpreteTColor tci = new InterpreteTColor();

        tci.setRepertoire(repertoire);

        tc = (TextureCol) tci.interprete(text, pos);

        pos = tci.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib = new InterpretesBase();
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();

        this.pos = pos;

        TRISphere s = new TRISphere(ps, r);

        s.texture(tc);

        return s;
    }

    public void setConstant(InterpreteConstants c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
