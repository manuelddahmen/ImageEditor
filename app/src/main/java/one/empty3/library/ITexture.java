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
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library;

//import com.xuggle.mediatool.MediaListenerAdapter;

import java.util.HashMap;

/*__
 * @author manu
 */
public abstract class ITexture implements MatrixPropertiesObject {
    public static final int COLOR_IDENT = 0;
    public static final int COLOR_MIROR_X = 1;
    public static final int COLOR_MIROR_Y = 2;
    public static final int COLOR_MIROR_XY = 4;
    public static final int COLOR_ROT_090 = 8;
    public static final int COLOR_ROT_180 = 16;
    public static final int COLOR_ROT_270 = 32;
    public int onTextureEnds = 0;
    protected int colorMask = 0;
    DeformMap dm;

    public int getColorMask() {
        return colorMask;
    }

    public void setColorMask(int colorMask) {
        this.colorMask = colorMask;
    }

    public Point2D getCoord(double x, double y) {
        Point2D p = new Point2D(x, y);
        if (getColorMask() == COLOR_IDENT)
            return p;
        if ((getColorMask() & COLOR_MIROR_X) > 0) {
            p = new Point2D(1.0 - p.x, p.y);
        }
        if ((getColorMask() & COLOR_MIROR_Y) > 0) {
            p = new Point2D(p.x, 1.0 - p.y);
        }
        if ((getColorMask() & COLOR_MIROR_XY) > 0) {
            p = new Point2D(p.y, p.x);
        }
        if ((getColorMask() & COLOR_ROT_090) > 0) {
            p = new Point2D(1.0 - p.y, p.x);
        }
        if ((getColorMask() & COLOR_ROT_180) > 0) {
            p = new Point2D(1.0 - p.x, 1.0 - p.x);
        }
        if ((getColorMask() & COLOR_ROT_270) > 0) {
            p = new Point2D(p.y, 1.0 - p.x);
        }
        return p;
    }

    public void setDeformMap(DeformMap map) {
        dm = map;
    }

    public DeformMap getDeformMap(DeformMap map) {
        return dm;
    }

    /*__
     * Retourne color at point (coordArr*textresx, y*textresy)
     *
     * @param x 0..1
     * @param y 0..1
     * @return color;
     */
    public abstract int getColorAt(double x, double y);


    public void timeNext() {
    }

    public void timeNext(long milli) {
    }

    public void iterate() throws EOFVideoException {

    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return null;
    }

    @Override
    public void declareProperties() {

    }

    @Override
    public HashMap<String, StructureMatrix> declarations() {
        return null;
    }

}

