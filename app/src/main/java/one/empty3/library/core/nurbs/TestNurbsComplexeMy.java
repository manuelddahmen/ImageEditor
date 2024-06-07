/*
 * Copyright (c) 2024. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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

import java.util.logging.Level;
import java.util.logging.Logger;

import javaAnd.awt.Color;
import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.move.Trajectoires;
import one.empty3.library.core.testing.TestObjetSub;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class TestNurbsComplexeMy extends TestObjetSub {

    private final double[][] longpc = new double[4][4];
    private final double[][] latpc = new double[4][4];
    Point3D[][] pp;

    public static void main(String[] args) {

        TestNurbsComplexeMy n = new TestNurbsComplexeMy();

        n.setGenerate(GENERATE_MODEL | GENERATE_IMAGE);

        n.setMaxFrames(200);

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
        NurbsSurface n = new NurbsSurface();

        n.setMaillage(pp, new double[][]{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}});

        n.setDegreU(3);
        n.setDegreV(3);

        n.setReseauFonction(new double[][]{
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        });

        n.texture(new TextureCol(Color.WHITE));

        n.creerNurbs();

        scene().add(n);
        Logger.getAnonymousLogger().log(Level.INFO,""+ n);

        scene().cameraActive(new Camera(Point3D.Z.mult(-30d), Point3D.O0));
    }

    @Override
    public void finit() {
    }
}
