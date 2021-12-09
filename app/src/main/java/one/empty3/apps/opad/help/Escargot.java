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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.apps.opad.help;

import one.empty3.apps.opad.Bonus;
import one.empty3.library.core.gdximports.gdx_BSplineCurve;

/*__
 *  Gros escargot mais lent. Fonctionne pendant 40 sec.
 * Champ d'action plus large. Cf. licorne se déplace le
 * long d'une BSpline pour attraper les objets.
 * @author Se7en
 */
public class Escargot extends BonusClass {
    public Escargot() {
        super();
        value = Double.parseDouble(bundle.getString("escargot.point"));

    }

    public gdx_BSplineCurve bspline(Bonus b, double escargotSize, double escargotVelocity) {
        throw new UnsupportedOperationException("Pas encore implanté");
    }
}
