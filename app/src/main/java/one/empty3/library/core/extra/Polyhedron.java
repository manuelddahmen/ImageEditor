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

import java.util.ArrayList;
import java.util.function.Consumer;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         27 déc. 2011
 */
public class Polyhedron extends Representable implements TRIConteneur {

    private TRIObject tris = new TRIObject();
    private ArrayList<Point3D> points;
    private Object co;

    public Polyhedron() {
        this.points = new ArrayList<Point3D>();
    }

    public Polyhedron(ArrayList<Point3D> points) {

        this.points = points;

        steps();


    }

    private void steps() {
        step1();
        //step2();
        //step1();
    }

    private void step1() {
        // Phase 1 Ajouter toutes les faces
        for (int a = 0; a < points.size(); a++) {
            Point3D pa = points.get(a);
            for (int b = 0; b < points.size(); b++) {
                Point3D pb = points.get(b);
                for (int c = 0; c < points.size(); c++) {
                    Point3D pc = points.get(c);
                    if (pa != pb && pb != pc && pc != pa) {
                        TRI t = new TRI(pa, pb, pc, texture);
                        tris.add(t);
                    }
                }
            }
        }
    }

    private void step2() {
        ArrayList<Point3D> points2 = new ArrayList<>();

        // Phase 2 Suppression des points internes

        // 2.1 copie des points.
        tris.getTriangles().forEach(new Consumer<TRI>() {
            @Override
            public void accept(TRI tri) {
                for (int i = 0; i < 3; i++)
                    points2.add(tri.getSommet().getElem(i));
            }
        });

        // 2.2 recherche d'un tétrahèdre incluant le point A
        points2.forEach(A -> {
            boolean[] coordsOk = new boolean[27];

            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    for (int k = 0; k < 2; k++)
                        coordsOk[i * 9 + j * 3 + k] = false;

            points2.forEach(entourage -> {
                if (!A.equals(entourage)) {
                    int arrayIndx = 0;
                    for (int i = 0; i < 2; i++)
                        for (int j = 0; j < 2; j++)
                            for (int k = 0; k < 2; k++) {
                                int cCount = 0;
                                for (int coord = 0; coord < 3; coord++) {

                                    if (A.get(coord) > entourage.get(coord)
                                            || A.get(coord) < entourage.get(coord)) {

                                        arrayIndx += ((coord == 2 ? 9 : (coord == 1 ? 3 : (coord == 0 ? 1 : 0))));

                                        cCount++;
                                    }

                                }
                                if (cCount == 3) {
                                    coordsOk[arrayIndx] = true;
                                }

                            }
                }
                int cCount = 0;
                for (int i = 0; i < 2; i++)
                    for (int j = 0; j < 2; j++)
                        for (int k = 0; k < 2; k++) {
                            int arrayIndex = k + j * 2 + i * 4;
                            if (coordsOk[arrayIndex]) {
                                cCount++;
                            }

                        }
                if (cCount == 8) {
                    points.remove(A);
                }
            });
        });

    }


    public Polyhedron(ArrayList<Point3D> list, TextureCol tColor) {
        this.points = list;
        this.texture = tColor;
        steps();
    }

    /*__
     * @param p 0
     */
    public void add(Point3D p) {
        tris.clear();
        points.add(p);
        steps();
    }

    public void delete(Point3D p) {
        points.remove(p);
        steps();
    }

    /*__
     *
     */
    public void deleteAll() {
        points.clear();

    }

    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.TRIConteneur#getObj()
     */
    @Override
    public Representable getObj() {
        return tris;
    }
    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.TRIConteneur#iterable()
     */

    @Override
    public Iterable<TRI> iterable() {
        return tris.getTriangles();
    }

}
