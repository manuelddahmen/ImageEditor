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

/*__
 * *
 * Global license :  *
 * CC Attribution
 *
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 *
 **
 */
package one.empty3.apps.opad;

import one.empty3.library.ColorTexture;
import one.empty3.library.Cube;

import java.awt.*;
import java.util.ResourceBundle;

/*__
 *
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
class Vaisseau {
    private final ResourceBundle bundle;
    private final double mlc;

    {
        bundle = ResourceBundle.getBundle("one.empty3.apps.opad.Bundle"); // NOI18N
        mlc = Double.parseDouble(bundle.getString("persoCube.mlc"));
    }
    private final PositionUpdate gm;

    public Vaisseau(PositionUpdate gm) {
        this.gm = gm;
    }

    public Cube getObject() {
        Cube cube = new Cube(mlc, gm.getPositionMobile().calcPosition(), new ColorTexture(Color.YELLOW));
        cube.generate();
        return cube;
    }
}
