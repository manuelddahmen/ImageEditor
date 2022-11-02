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

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.Point3D;
import one.empty3.library.core.extra.SimpleSphere;


import java.util.ArrayList;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         21 oct. 2011
 */
public class InterpreteSimpleSphere implements Interprete {

    private String repertoire;
    private int pos;
    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#constant()
     */

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#getPosition()
     */

    @Override
    public int getPosition() {
        // TODO Auto-generated method stub
        return pos;
    }

    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#interprete(java.lang.String, int)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        Point3D c = null;
        double r = 1;
        Color col = Color.valueOf(Color.BLACK);

        InterpretesBase ib;
        InterpretePoint3DBAK ip;
        InterpreteCouleur pc;
        ArrayList<Integer> patt = null;

        ib = new InterpretesBase();
        patt = new ArrayList<Integer>();
        patt.add(ib.BLANK);
        patt.add(ib.LEFTPARENTHESIS);
        patt.add(ib.BLANK);
        ib.compile(patt);

        ib.read(text, pos);
        pos = ib.getPosition();

        ip = new InterpretePoint3DBAK();
        c = (Point3D) ip.interprete(text, pos);
        pos = ip.getPosition();

        ib = new InterpretesBase();
        patt = new ArrayList<Integer>();
        patt.add(ib.BLANK);
        patt.add(ib.DECIMAL);
        patt.add(ib.BLANK);
        ib.compile(patt);

        ib.read(text, pos);
        pos = ib.getPosition();
        r = (Double) ib.get().get(1);

        pc = new InterpreteCouleur();
        col = (Color) pc.interprete(text, pos);
        pos = pc.getPosition();

        ib = new InterpretesBase();
        patt = new ArrayList<Integer>();
        patt.add(ib.BLANK);
        patt.add(ib.RIGHTPARENTHESIS);
        patt.add(ib.BLANK);
        ib.compile(patt);

        ib.read(text, pos);
        pos = ib.getPosition();

        this.pos = pos;
        return new SimpleSphere(c, r, col.toArgb());
    }

    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#setConstant(be.ibiiztera.md.pmatrix.pushmatrix.scripts.InterpreteConstants)
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
