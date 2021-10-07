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

package one.empty3.library.core.raytracer;

public abstract class RtNode {
    public static final int LIGHT = 0x0001000;
    public static final int OMNILIGHT = 0x0001002;
    public static final int CAMERA = 0x0010004;
    public static final int TARGETCAMERA = 0x0010008;

    protected int mNodeType;
    protected String mName;

    // constructeurs et destructeur
    public RtNode() {
        mName = null;
    }

    public RtNode(int nodeType, String name) {
        mNodeType = nodeType;
        mName = name;
    }

    // raytrace related
    public abstract boolean intersectsNode(RtRay ray, RtIntersectInfo intersectInfo);

    // get
    public final int getNodeType() {
        return mNodeType;
    }

    // set
    public void setNodeType(int nodeType) {
        mNodeType = nodeType;
    }

    public final String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public final RtNode getNodeInstance() {
        return this;
    }
}