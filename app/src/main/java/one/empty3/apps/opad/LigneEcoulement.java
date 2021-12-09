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

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;

/*__
 * Created by Win on 12-09-18.
 *
 * <a href="https://www.mathcurve.com/courbes3d/topographic/ecoulement.shtml">Lignes d'écoulement</a>
 *
 * ->     ->        ->
 * P  = A*v     + B*v    + C (à intervzlles dt constants
 *  N       N-1      N-1
 *
 *  ->
 *  v(u,v) <- Input (keyboard)
 *  ???
 */
public class LigneEcoulement implements Runnable {
    private final PositionMobile positionMobile;
    private ParametricSurface ps;
    public static final double g = 9.8;
    private boolean running = true;

    public LigneEcoulement(PositionMobile positionMobile, ParametricSurface parametricSurface)
    {
        this.ps = parametricSurface;
        this.positionMobile = positionMobile;
        new Thread(this).start();
    }

    public void setSurface(ParametricSurface ps)
    {
        this.ps = ps;
    }


    @Override
    public void run() {
        while (isRunning()) {
            Point3D position = positionMobile.calcPosition();
            Point3D deplacement = positionMobile.calcDirection();


        }
    }

    public boolean isRunning() {
        return running;
    }

    public ParametricSurface getPs() {
        return ps;
    }

    public static double getG() {
        return g;
    }
}
