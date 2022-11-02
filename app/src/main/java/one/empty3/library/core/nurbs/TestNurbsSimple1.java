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
import one.empty3.library.core.testing.TestObjetSub;


/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class TestNurbsSimple1 extends TestObjetSub {

    public static void main(String[] args) {

        TestNurbsSimple1 n = new TestNurbsSimple1();

        n.setGenerate(GENERATE_MODEL | GENERATE_IMAGE);

        n.setMaxFrames(1);

        n.loop(true);

        new Thread(n).start();

    }

    @Override
    public void testScene() throws Exception {
        scene().getObjets().getData1d().clear();

        NurbsSurface1 n = new NurbsSurface1();

        n.setMaillage(new Point3D[][]{{
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
        }, new double[][]{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}});
        /* n.setMaillage(new Point3D[][]{
         {
         new Point3D(-1, -1, 0),
         new Point3D(-1, 1, 0)
         },
         {
         new Point3D(1, -1, 0),
         new Point3D(1, 1, 0)}
         }
         ,
         new double[][]{
         {1, 1},
         {1, 1}
         });
         */
        n.setDegreU(3);
        n.setDegreV(3);

        n.setReseauFonction(new double[][]{
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1}
        });

        n.texture(new TextureCol(Color.WHITE));

        n.creerNurbs();

        n.setMaxX(5);
        n.setMaxY(5);

        scene().add(n);
        System.out.println(n);

        scene().cameraActive(new Camera(Point3D.Z.mult(-1000d), Point3D.O0));
    }

    @Override
    public void finit() {
    }

    @Override
    public void ginit() {
    }
}
