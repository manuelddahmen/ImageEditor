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

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;


/*__
 * @author Manuel Dahmen
 */
//#TODO Repair
public class TextureCol extends ITexture {

    private StructureMatrix<Integer> color = new StructureMatrix<>(0, Integer.class);

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TextureCol() {
        color.setElem(Color.valueOf(0f, 0f, 0f).toArgb());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TextureCol(Color c) {
        if (c != null) {
            color.setElem(c.toArgb());
        } else
            color.setElem(Color.valueOf(0, 0, 0, 1).toArgb());
    }

    public TextureCol(int c) {
        color.setElem(c);
    }

    public int color() {
        return color.getElem();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void color(Color c) {
        color.setElem(c.toArgb());
    }

    @Override
    public void iterate() throws EOFVideoException {

    }

    public int getColorAt(double x, double y) {
        return color.getElem();
    }

    public void timeNext() {
    }

    public void timeNext(long milli) {
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
        return Color.valueOf(color.getElem());
    }

    public String toString() {
        long c = this.color.getElem();
        return "texture ( red:" + ((c & 0x000000FF) >> 0) + "; green:" +
                ((c & 0x0000FF00) >> 8) + "; blue:" + ((c & 0x00FF0000) >> 16) +
                "; alpha:" + ((c & 0xFF000000) >> 24) + ")\n";
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }
}
