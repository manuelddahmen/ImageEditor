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

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;


/*__
 * @author Manuel Dahmen
 */
public class ColorTexture extends ITexture {

    private StructureMatrix<Color> color = new StructureMatrix<Color>(0, Color.class);

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ColorTexture() {
        color.setElem(Color.valueOf(0, 0, 0));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ColorTexture(Color c) {
        this();
        if (c != null) {
            color.setElem(c);
        }
    }

    public ColorTexture(int white) {
        this(Color.valueOf(white));
    }

    public Color color() {
        return color.getElem();
    }

    public void color(Color c) {
        color.setElem(c);
    }

    @Override
    public void iterate() throws EOFVideoException {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getColorAt(double x, double y) {
        return color.getElem().toArgb();
    }

    public void timeNext() {
    }

    public void timeNext(long milli) {
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return color;
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }

    /*__
     * Quadrilatère numQuadX = 1, numQuadY = 1, coordArr, y 3----2 ^2y |\ | | 4 |
     * 0--\-1 1 -> 2x
     *
     * @param numQuadX nombre de maillage en coordArr
     * @param numQuadY nombre de maillage en y
     * @param x        valeur de coordArr
     * @param y        valeur de y
     * @return
     */
    public Color getMaillageTexturedColor(int numQuadX, int numQuadY, double x,
                                          double y) {
        return color.getElem();
    }

    public StructureMatrix<Color> getColor() {
        return color;
    }

    public void setColor(StructureMatrix<Color> color) {
        this.color = color;
    }
}
