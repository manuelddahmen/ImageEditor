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
package one.empty3.library.core.extra;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.*;

import java.util.Random;

/*__
 * @author Manuel
 */
public class CollineModele1 extends Representable implements TRIGenerable {

    Random r = new Random();
    private TRIObject tris = new TRIObject();
    private String id;
    private double deltaInterne = 100;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CollineModele1(int numTRIS) {

        Point3D p0 = new Point3D(0d, 0d, 0d);
        Color c0 = Color.valueOf(128, 0, 255);

        for (int i = 0; i < numTRIS; i++) {
            Point3D[] p = new Point3D[3];

            p[0] = p0.plus(new Point3D(aleatoireSigne(deltaInterne),
                    aleatoireSigne(deltaInterne), aleatoireSigne(deltaInterne)));
            p[1] = p[0].plus(new Point3D(aleatoireSigne(deltaInterne),
                    aleatoireSigne(deltaInterne), aleatoireSigne(deltaInterne)));
            p[2] = p[1].plus(new Point3D(aleatoireSigne(deltaInterne),
                    aleatoireSigne(deltaInterne), aleatoireSigne(deltaInterne)));

            p0 = p[2];

            TRI t = new TRI(p[0], p[1], p[2], c0);

            tris.add(t);
        }
    }

    private double aleatoireSigne(double maxAmpl) {

        return (r.nextDouble() - 0.5) * maxAmpl;

    }

    @Override
    public TRIObject generate() {
        return tris;
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        return "colline()\n";
    }

}
