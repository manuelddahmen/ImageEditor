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

package one.empty3.library;


/*__
 * Created by manue on 07-03-19.
 */
public class Render {
    public static final int TYPE_POINT = 0;
    public static final int TYPE_CURVE = 1;
    public static final int TYPE_SURFACE = 2;
    public static final int POINTS = 4;
    public static final int LINES = 8;
    public static final int FILL = 16;
    protected int objectType = -1;
    protected int renderingType = -1;

    public Render(int objectType, int renderingType) {
        this.objectType = objectType;
        this.renderingType = renderingType;
    }


    public static Render getInstance(int objectType, int renderingType) {
        return new Render(objectType, renderingType);
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getRenderingType() {
        return renderingType;
    }

    public void setRenderingType(int renderingType) {
        this.renderingType = renderingType;
    }
}
