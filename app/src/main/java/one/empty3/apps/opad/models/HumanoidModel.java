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

package one.empty3.apps.opad.models;

import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;

/*__
 * Created by Win on 17-10-18.
 */
public class HumanoidModel extends Model {
    private long timeStart;
    private long animationTime = 0;
    private Point3D position;
    private Point3D vectorVectical;
    private double feetSize, legSize,
        armSize, bodySize, headSize
        ;


    public HumanoidModel()
    {
        timeStart = System.nanoTime();
    }

    private long realTimeNano()
    {
        return System.nanoTime()
                - timeStart; // TODO
    }
    private long animationTimeNano(long timeEllapsed)
    {
        animationTime += timeEllapsed;
        return animationTime;
    }
    public class Arete
    {
        LineSegment segmentDroite;

    }
    public class BodyPart
    {

    }

    public class BodyLink
    {

    }
    public class Movement
    {

    }

}
