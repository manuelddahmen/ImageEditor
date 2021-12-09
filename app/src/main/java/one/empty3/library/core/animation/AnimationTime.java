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
 * Global license : * CC Attribution
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.animation;

public class AnimationTime {
    protected long fps;
    protected long frame;
    protected double timeCurrentInAnimation;

    public AnimationTime(long fps) {
        this.fps = (fps);
    }

    protected double timeMin;
    protected double timeMax;

    public void avanceUneFrame() {
        timeCurrentInAnimation += 1.0 / fps;
    }

    public long getFps() {
        return fps;
    }

    public void setFps(long fps) {
        this.fps = fps;
    }

    public long getFrame() {
        return frame;
    }

    public void setFrame(long frame) {
        this.frame = frame;
    }

    public double getTimeCurrentInAnimation() {
        return timeCurrentInAnimation;
    }

    public void setTimeCurrentInAnimation(double timeCurrentInAnimation) {
        this.timeCurrentInAnimation = timeCurrentInAnimation;
    }

    public void increase(double time) {
        // TODO
    }

    public void increase(int numberOfFrames) {
        // TODO
    }

    public double getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(double timeMin) {
        this.timeMin = timeMin;
    }

    public double getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(double timeMax) {
        this.timeMax = timeMax;
    }
}
