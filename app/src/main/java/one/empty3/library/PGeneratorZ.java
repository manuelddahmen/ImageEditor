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
package one.empty3.library;

import android.graphics.Point;


/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         17 oct. 2011
 */
public abstract class PGeneratorZ extends Representable {

    public abstract void dessine(ZBuffer z);

    public void dessineStructure(ZBuffer zBufferImpl) {
        // TODO Auto-generated method stub

    }

    public abstract void generate(ZBuffer z);

    public int hauteurImage(ZBuffer z) {
        return z.resY();
    }

    public int largeurImage(ZBuffer z) {
        return z.resX();
    }

    public Point point(ZBuffer z, Point3D p) {
        return z.camera().coordonneesPoint2D(p, ((ZBufferImpl) z));
    }
}
