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

/*

 Vous êtes libre de :

 */
package one.empty3.library.core.extra;

import one.empty3.library.*;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         27 oct. 2011
 */
public class SimpleSphereAvecTexture extends SimpleSphere {

    private Bitmap img;
    private Axe axe;
    private double angle;
    private String fichier;

    /*__
     * @param c
     * @param r
     * @param col
     */
    public SimpleSphereAvecTexture(Point3D c, double r, int col) {
        super(c, r, col);
    }

    public SimpleSphereAvecTexture(Point3D c, double r, int col,
                                   Bitmap bufferedImage) {
        super(c, r, col);
        texture(bufferedImage);
    }

    public SimpleSphereAvecTexture(Point3D c, Matrix33 m3d, double angle, double r,
                                   int col, Bitmap img) {
        super(c, r, col);
        this.axe = axe;
        this.angle = angle;
        getTexture();
    }

    public void fichier(String f) {
        fichier = f;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public TRIObject generate() {
        TRIObject t = new TRIObject();
        po = new PObjet();

        double a = -Math.PI / 2;
        double b;
        double incrLat;
        double incrLong;
        Point3D[] pCur = new Point3D[4];

        incrLat = 2 * Math.PI / numLatQuad;
        while (a < Math.PI / 2) {
            incrLong = 2 * Math.PI * Math.cos(a) / numLongQuad;
            b = 0;
            while (b < 2 * Math.PI && incrLong > 0.0001) {
                //System.out.println("a;b " + a +";"+b);
                pCur[0] = CoordPoint(a, b);
                pCur[1] = CoordPoint(a + incrLat, b);
                pCur[2] = CoordPoint(a, b + incrLong);
                pCur[3] = CoordPoint(a + incrLat, b + incrLong);
                try {
                    Color color = Color.valueOf(img.getPixel(
                            (int) ((a + Math.PI) / Math.PI * img.getHeight()),
                            (int) ((b) / 2 / Math.PI * img.getWidth())));
                    t.add(new TRI(pCur[0], pCur[1], pCur[3], color));
                    t.add(new TRI(pCur[0], pCur[2], pCur[3], color));
                } catch (Exception ex) {
                }
                b += incrLong;
            }
            a += incrLat;
        }
        return t;
    }


    @Override
    public String toString() {
        return "\nsimpleSphereAvecTexture(\n\t" + centre.toString() + "\n\t" + radius + " \n\t\"" + fichier + "\"\n)\n";
    }
}
