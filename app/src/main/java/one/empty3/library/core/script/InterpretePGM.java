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

import android.graphics.Bitmap;

import one.empty3.library.*;

import java.util.ArrayList;

public class InterpretePGM implements Interprete {

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        ECBufferedImage ec;

        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> p = new ArrayList<Integer>();
        p.add(ib.BLANK);
        ib.compile(p);

        ib.read(text, pos);
        pos = ib.getPosition();

        if ("P3\n".equals(text.substring(pos, pos + 2))) {
            pos += "P3\n".length();
        } else {
            return null;
        }
        while ("#".equals(text.substring(pos, 1))) {
            pos = text.indexOf("\n", pos) + 1;

        }

        Integer x = Integer.parseInt(text.substring(pos, text.indexOf(" ", pos)));

        pos += ("" + x).length() + 1;

        while (text.charAt(pos) < 0 || text.charAt(pos) > 9) {
            pos++;
        }

        Integer y = Integer.parseInt(text.substring(pos, text.indexOf(" ", pos)));

        while (text.charAt(pos) < 0 || text.charAt(pos) > 9) {
            pos++;
        }

        pos = text.indexOf("\n", pos) + 1;

        Integer depth = Integer.parseInt(text.substring(pos, text.indexOf(" ", pos)));

        pos = text.indexOf("\n", pos) + 1;

        Bitmap bitmap = Bitmap.createBitmap(x, y, Bitmap.Config.RGB_565);
        return bitmap;

    }

    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRepertoire(String r) {
        // TODO Auto-generated method stub

    }

}
