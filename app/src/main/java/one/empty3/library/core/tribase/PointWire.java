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
package one.empty3.library.core.tribase;

import android.graphics.Bitmap;

import one.empty3.library.*;

import java.util.ArrayList;

public class PointWire extends Representable implements IFct1D3D {

    private String id;

    private ArrayList<BezierCubique> beziers;

    public PointWire(ArrayList<Point3D> base) {
        beziers = new ArrayList<BezierCubique>();

        base.add(0, base.get(0));
        base.add(base.get(base.size() - 1));

        for (int i = 0; i < base.size() - 1; i++) {

            BezierCubique bc = new BezierCubique();

            bc.add(base.get(i).plus(base.get(i + 1)).mult(0.5));
            bc.add(base.get(i + 1));
            bc.add(base.get(i + 1));
            bc.add(base.get(i + 1).plus(base.get(i + 2)).mult(0.1));

            beziers.add(bc);

        }
    }

    @Override
    public int iteres() {
        return 1000;
    }

    public double maxValue() {
        return beziers.size();
    }

    public Point3D normale(double t) {
        return tangente(t + 0.00001).plus(tangente(t).mult(-1d));
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

    public Point3D tangente(double t) {
        return beziers
                .get((int) (0.0001 + t))
                .calculerPoint3D((0.0000001 + t) - (int) (0.0001 + t))
                .plus(beziers.get((int) t).calculerPoint3D(t - (int) t)
                        .mult(-1d));
    }

    public TextureCol texture(Bitmap img) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point3D value(double t) {
        return beziers.get((int) t).calculerPoint3D(t - (int) t);
    }

}
