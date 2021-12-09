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
import one.empty3.library.Representable;
import one.empty3.library.core.nurbs.ParametricCurve;

public abstract class Mouvement extends Thread {
    protected Representable representable;
    private Timer timer;
    private Thread thread;

    public Mouvement(Representable representable, long timeNano, ParametricCurve parametricCurve) {
        this.representable = representable;
        move(timeNano, parametricCurve);
    }

    public void threadMove() {

    }

    public void run() {
        timer = new Timer();
        if (thread != null) {

            thread.start();


        }
    }

    public void move(long timeNano, ParametricCurve parametricCurve) {
        thread = new Thread() {
            public void run() {
                startMoveAction();

                while (timeNano <= timer.timeEllapsed) {
                    double moveTimeRatio = 1.0 * timeNano / timer.timeEllapsed;
                    setPosition(parametricCurve.calculerPoint3D(moveTimeRatio));
                    action(representable, moveTimeRatio);
                }

                endMoveAction();
            }
        };
    }

    protected abstract void action(Representable representable, double moveTimeRatio);

    protected abstract void setPosition(Point3D position);

    protected abstract void startMoveAction();

    protected abstract void endMoveAction();
}
