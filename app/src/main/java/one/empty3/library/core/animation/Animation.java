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

package one.empty3.library.core.animation;

import one.empty3.library.Representable;

public class Animation extends Representable {
/*    protected List<Point3D> points = new ArrayList<>();
    protected List<AnimationTime> time = new ArrayList<>();

    protected ECDim resolution;

    protected Scene scene;
    ZBuffer z;
    private ArrayList<AnimationMouvements> moves = new ArrayList<AnimationMouvements>();
    private double duration;
    private int currentTimeNo;

    public Animation(Scene s, ECDim dim) {
        this.resolution = dim;
        this.scene = s;
        z = new ZBufferImpl(resolution.getDimx(), resolution.getDimy());
    }

    public void registerPoint3D(Point3D p) {
        this.points.add(p);
    }

    public void generate() {
        AnimationGenerator gen = new AnimationGenerator(this);
        gen.start();
    }

    public ECDim getResolution() {
        return resolution;
    }

    public Scene getScene() {
        return scene;
    }

    public List<AnimationTime> getTime() {
        return time;
    }

    public AnimationTime getCurrentTime() {
        return time.get(currentTimeNo);
    }

    public void update(int numberOfFrames) {
        for (Point3D point : points) {
            time.forEach(new Consumer<AnimationTime>() {
                @Override
                public void accept(AnimationTime animationTime) {
                    double t = (animationTime.getTimeCurrentInAnimation() + numberOfFrames * animationTime.getFps()) / duration;


                }
            });
        }
    }

    public List<Point3D> getPoints() {
        return points;
    }

    public void setPoints(List<Point3D> points) {
        this.points = points;
    }


    public void setResolution(ECDim resolution) {
        this.resolution = resolution;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ZBuffer getZ() {
        return z;
    }

    public void setZ(ZBuffer z) {
        this.z = z;
    }

    public ArrayList<AnimationMouvements> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<AnimationMouvements> moves) {
        this.moves = moves;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

*/
}
