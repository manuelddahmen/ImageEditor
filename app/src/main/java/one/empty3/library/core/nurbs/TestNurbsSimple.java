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
public class TestNurbsSimple extends TestObjetSub {

    public static void main(String[] args) {

        TestNurbsSimple n = new TestNurbsSimple();

        n.setGenerate(GENERATE_MODEL | GENERATE_IMAGE);

        n.setMaxFrames(30);

        n.loop(true);

        new Thread(n).start();

    }

    @Override
    public void testScene() throws Exception {
        scene().getObjets().getData1d().clear();

        NurbsSurface n = new NurbsSurface();
        n.setMaillage(new Point3D[][]{
                        {
                                new Point3D(-1d, -1d, 0d),
                                new Point3D(-1d, 1d, 0d)
                        },
                        {
                                new Point3D(1d, -1d, 0d),
                                new Point3D(1d, 1d, 0d)}
                },
                new double[][]{
                        {1, 1},
                        {1, 1}
                });

        n.setDegreU(2);
        n.setDegreV(2);

        n.setReseauFonction(new double[][]{
                {0, 0, 1, 1},
                {0, 0, 1, 1}
        });

        n.texture(new TextureCol(Color.WHITE));

        n.setStartU(0.0);
        n.setStartV(0.0);
        n.setEndU(1.0);
        n.setEndV(1.0);
        n.setIncrU(0.01);
        n.setIncrV(0.01);

        n.creerNurbs();

        scene().add(n);
        System.out.println(n);

        scene().cameraActive(new Camera(Point3D.Z.mult(-2d), Point3D.O0));
    }

    @Override
    public void finit() {
    }

    @Override
    public void ginit() {
    }
}
