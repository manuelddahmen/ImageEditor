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
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class InterpreteCouleur implements Interprete {

    private String repertoire;
    private int pos;

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Object interprete(String text, int pos) throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        pattern.add(ib.INTEGER);
        pattern.add(ib.BLANK);
        pattern.add(ib.COMA);
        pattern.add(ib.BLANK);
        pattern.add(ib.INTEGER);
        pattern.add(ib.BLANK);
        pattern.add(ib.COMA);
        pattern.add(ib.BLANK);
        pattern.add(ib.INTEGER);
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);
        ArrayList<Object> os = ib.read(text, pos);
        this.pos = ib.getPosition();

        return Color.valueOf((Integer) os.get(3), (Integer) os.get(7),
                (Integer) os.get(11));
    }

    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
