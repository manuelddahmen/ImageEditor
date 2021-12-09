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

package one.empty3.library;

public class ZBufferFactory {

    private static ZBufferImpl insta = null;
    private static int la = -1, ha = -1;

    public static ZBufferImpl instance(int x, int y) {
        if (la == x && ha == y && insta != null) {
            return insta;
        }
        la = x;
        ha = y;
        insta = new ZBufferImpl(x, y);
        return insta;
    }

    public static ZBufferImpl instance(int x, int y, boolean D3) {
        if (la == x && ha == y && insta != null)//&& (D3 && insta instanceof ZBuffer3D || !D3))
        {
            return insta;
        }
        la = x;
        ha = y;
        if (D3) {
            // insta = new ZBuffer3DImpl(coordArr, y);
        } else {
            insta = new ZBufferImpl(x, y);
        }

        return insta;
    }

    public static ZBufferImpl instance(int x, int y, Scene s) {

        ZBufferImpl z = new ZBufferImpl(x, y);
        z.scene(s);
        return z;
    }
}
