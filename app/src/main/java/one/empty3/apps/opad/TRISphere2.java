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

package one.empty3.apps.opad;

import one.empty3.apps.opad.help.BonusClass;
import one.empty3.library.Point3D;
import one.empty3.library.Sphere;
import one.empty3.library.core.tribase.TRISphere;

/*__
 * Created by manuel on 19-05-17.
 */
class TRISphere2<T extends BonusClass> extends Sphere {

    private Point3D coords;
    private Bonus bonus;
    double u;
    double v;
    BonusClass clazz;
    private BonusClass gameObject;

    public TRISphere2(Bonus bonus, Point3D c, double r) {
        super(c, r);
        setIncrU(0.19);
        setIncrV(0.19);
        setStartU(0.0);
        setStartV(0.0);
        setEndU(1.0);
        setEndV(1.0);
        this.coords = c;
        this.bonus = bonus;
        clazz = new BonusClass();
    }

    public BonusClass getBonusClass() {
        return clazz;
    }

    public void setGameObject(BonusClass gameObject) {
        this.gameObject = gameObject;
    }

    public BonusClass getGameObject() {
        return this.gameObject;
    }

    public Point3D getCoords() {
        return coords;
    }

    public void setCoords(Point3D c) {
        this.coords = c;
    }

}
