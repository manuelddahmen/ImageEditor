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

import android.graphics.Color;

import one.empty3.library.*;


public class Tourbillon extends Representable implements
        TRIConteneur {

    private String id;
    private double diametre;
    private double hauteur;
    // private Axe axe;
    private PObjet obj;
    private TRIObject tri;
    private double tours;

    public Tourbillon() {
        this.diametre = 1.0;
        this.hauteur = 1.0;
        // this.axe = new Axe(new Point3D(0, 0, 0), new Point3D(0, 1, 0));
        this.obj = new PObjet();
        this.tours = 4.0;
        int[] colors = new int[]{Color.RED, Color.GREEN, Color.BLUE,
                Color.RED/*ORANGE*/, Color.CYAN, Color.DKGRAY, Color.BLACK,
                Color.GRAY, Color.LTGRAY, Color.MAGENTA, Color.RED/*PINK,ROSE*/,
                Color.YELLOW};

        double angle = 0.0;

        int dimx = 100;
        int dimy = colors.length;

        Point3D[] points = new Point3D[dimx * dimy];

        for (int j = 0; j < dimy; j++) {

            int c = colors[j];

            angle += 2.0 * Math.PI / (dimy - 1);

            for (int i = 0; i < dimx; i++) {

                double h = hauteur * i / dimx;
                double d = h * h * diametre;
                Point3D p = new Point3D(-d
                        * Math.sin(2 * Math.PI * tours * h + angle), -h, d
                        * Math.cos(2 * Math.PI * tours * h + angle));
                p.texture(new TextureCol(c));

                obj.add(p);

                points[dimx * j + i] = p;

            }
        }
        tri = TRIGeneratorUtil.P32DTriQuad(points, dimx, dimy);
    }

    @Override
    public Representable getObj() {
        return tri;
    }

    /*
     * public Iterable<Point3D> iterable() { return obj.getPoints(); }
     */
    @Override
    public Iterable<TRI> iterable() {
        return tri.getTriangles();
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "\ttourbillon(\n\t)\n";
        // return
        // "tourbillon(\n\n  diametre("+diametre+")\n\n  hauteur(\n\n"+hauteur+")\n\n  triobjet  (\n\n"+tri.toString()+"\n\n)\n\n\n)\n";
    }

}
