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
package one.empty3.library.core.nurbs;

import android.graphics.Color;

import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.move.Trajectoires;
import one.empty3.library.core.testing.TestObjetSub;


/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class TestNurbsComplexe1 extends TestObjetSub {

    Point3D[][] pp;
    private double[][] longpc = new double[4][4];
    private double[][] latpc = new double[4][4];

    public static void main(String[] args) {

        TestNurbsComplexe1 n = new TestNurbsComplexe1();

        n.setGenerate(GENERATE_MODEL | GENERATE_IMAGE);

        n.setMaxFrames(2000);

        n.loop(true);

        new Thread(n).start();

    }

    public void changeValue(int i, int j) {
        longpc[i][j] = longpc[i][j] + Math.random() / 100;
        latpc[i][j] = latpc[i][j] + Math.random() / 100;
        pp[i][j] = Trajectoires.sphere(longpc[i][j], latpc[i][j], pp[i][j].norme());
    }

    public void updateValues(Point3D[][] ppp) {
        for (int i = 0; i < ppp.length; i++) {
            for (int j = 0; j < ppp[i].length; j++) {
                changeValue(i, j);
            }
        }
    }

    @Override
    public void ginit() {
        pp = new Point3D[][]{{
                new Point3D(-15.0, 0.0, 15.0),
                new Point3D(-15.0, 5.0, 5.0),
                new Point3D(-15.0, 5.0, -5.0),
                new Point3D(-15.0, 0.0, -15.0)
        }, {
                new Point3D(-5.0, 5.0, 15.0),
                new Point3D(-5.0, 10.0, 5.0),
                new Point3D(-5.0, 10.0, -5.0),
                new Point3D(-5.0, 5.0, -15.0)
        }, {
                new Point3D(5.0, 5.0, 15.0),
                new Point3D(5.0, 10.0, 5.0),
                new Point3D(5.0, 10.0, -5.0),
                new Point3D(5.0, 0.0, -15.0)
        }, {
                new Point3D(15.0, 0.0, 15.0),
                new Point3D(15.0, 5.0, 5.0),
                new Point3D(15.0, 5.0, -5.0),
                new Point3D(15.0, 0.0, -15.0)
        }
        };
    }

    @Override
    public void testScene() throws Exception {
        scene().getObjets().getData1d().clear();

        updateValues(pp);
        NurbsSurface1 n = new NurbsSurface1();

        n.setMaillage(pp, new double[][]{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}});

        n.setDegreU(4);
        n.setDegreV(4);

        n.setReseauFonction(new double[][]{
                {0, 0, 0, 0, 0.5, 0.5, 1, 1, 1, 1},
                {0, 0, 0, 0, 0.5, 0.5, 1, 1, 1, 1}
        });

        n.texture(new TextureCol(Color.WHITE));

        n.setMaxX(30);
        n.setMaxY(30);

        n.creerNurbs();

        scene().add(n);
        System.out.println(n);

        scene().cameraActive(new Camera(Point3D.Z.mult(-30d), Point3D.O0));
    }

    @Override
    public void finit() {
    }
}
