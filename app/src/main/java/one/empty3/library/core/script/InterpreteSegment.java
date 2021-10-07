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

import one.empty3.library.*;


import java.util.ArrayList;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         15 oct. 2011
 */
public class InterpreteSegment implements Interprete {

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
        InterpretesBase isb;
        ArrayList<Integer> pattern;
        isb = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(isb.BLANK);
        pattern.add(isb.LEFTPARENTHESIS);
        pattern.add(isb.BLANK);
        isb.compile(pattern);
        isb.read(text, pos);
        pos = isb.getPosition();

        InterpretePoint3DBAK ip3 = new InterpretePoint3DBAK();
        Point3D p1 = (Point3D) ip3.interprete(text, pos);
        pos = ip3.getPosition();
        ip3 = new InterpretePoint3DBAK();
        Point3D p2 = (Point3D) ip3.interprete(text, pos);
        pos = ip3.getPosition();

        InterpreteCouleur ic = new InterpreteCouleur();
        Color c = (Color) ic.interprete(text, pos);
        pos = ic.getPosition();

        isb = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(isb.BLANK);
        pattern.add(isb.RIGHTPARENTHESIS);
        pattern.add(isb.BLANK);
        isb.compile(pattern);
        isb.read(text, pos);
        pos = isb.getPosition();

        this.pos = pos;

        LineSegment sd = new LineSegment(p1, p2);
        sd.texture(new TextureCol(c));

        return sd;
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
