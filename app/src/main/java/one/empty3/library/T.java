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

package one.empty3.library;

import one.empty3.library.core.nurbs.Fct1D_1D;

/*__
 * Created by manue on 05-01-20.
 */
public class T {
    protected double t0;
    protected double t1;
    protected double t;
    protected double fps;
    protected double noFrames;
    protected Fct1D_1D curveT;


    public T() {

    }


    public void set(double fps, double t1, double t0, double tInit, double noFrames) {
        this.fps = fps;
        this.t0 = t0;
        this.t1 = t1;
        this.noFrames = noFrames;
        t = tInit;
    }

    public double incrT() {
        return curveT.result((t1) * fps / noFrames) - curveT.result((t0) * fps / noFrames);
    }

    public double tPP() {
        this.t = curveT.result(t) + curveT.result((1 - t0) * fps / noFrames);
        return t;
    }

    public double noFrame() {
        return t / incrT();
    }


    public double getT0() {
        return t0;
    }

    public void setT0(double t0) {
        this.t0 = t0;
    }

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public double getNoFrames() {
        return noFrames;
    }

    public void setNoFrames(double noFrames) {
        this.noFrames = noFrames;
    }


    /*__*
     * Set function for the object X
     * @param curveT curveT = curve t=f(t0)
     */
    public void setCurve(Fct1D_1D curveT) {
        this.curveT = curveT;
    }
}
