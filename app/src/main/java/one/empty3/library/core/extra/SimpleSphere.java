
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

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.*;


/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         17 oct. 2011
 */
public class SimpleSphere extends Representable implements TRIGenerable {

    public static float DMIN = 0.5f;
    public static float DMAX = 1.5f;
    protected PObjet po;
    protected double radius;
    protected Point3D centre;
    protected int color;
    protected int numLatQuad = 150;
    protected int numLongQuad = 150;
    Color map[][];
    double[][] zmap;
    Point3D[][] pmap;
    private String id;
    private double incrLat;
    private double incrLong;

    public SimpleSphere(Point3D c, double r, int col) {
        this.radius = r;
        this.centre = c;
        this.color = col;
    }
    /*
     * (non-Javadoc)
     *
     * @see be.ibiiztera.md.pmatrix.pushmatrix.PGenerator#generatePO()
     */

    public Point3D CoordPoint(double a, double b) {
        return new Point3D(centre.getX() + Math.cos(a) * Math.cos(b) * radius,
                centre.getY() + Math.cos(a) * Math.sin(b) * radius,
                centre.getZ() + Math.sin(a) * radius);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public TRIObject generate() {
        TRIObject t = new TRIObject();
        po = new PObjet();

        double a = -Math.PI / 2;
        double b;
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
                t.add(new TRI(pCur[0], pCur[1], pCur[3], android.graphics.Color.valueOf(color)));
                t.add(new TRI(pCur[0], pCur[2], pCur[3], android.graphics.Color.valueOf(color)));

                b += incrLong;
            }
            a += incrLat;
        }
        return t;
    }

    public int getNumLatQuad() {
        return numLatQuad;
    }

    public void setNumLatQuad(int numLatQuad) {
        this.numLatQuad = numLatQuad;
    }

    public int getNumLongQuad() {
        return numLongQuad;
    }

    public void setNumLongQuad(int numLongQuad) {
        this.numLongQuad = numLongQuad;
    }

    public Barycentre position() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public boolean supporteTexture() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TextureCol texture(Bitmap img) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "\nSimpleSphere(\n\t" + centre.toString() + "\n\t" + radius + " \n\t" + "(" + color + ")\n)\n";
    }

}
