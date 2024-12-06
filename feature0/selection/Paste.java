/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

<<<<<<<< HEAD:feature0/selection/Paste.java
package one.empty3.feature.selection;

import matrix.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.*;
import one.empty3.library.core.nurbs.ParametricCurve;

import one.empty3.ImageIO;
import one.empty3.libs.Image;
========
package one.empty3.androidFeature.selection;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/selection/Paste.java

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import one.empty3.ImageIO;
import matrix.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.ITexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.Scene;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.libs.Image;

public class Paste extends ProcessFile {

    /*
     *
     * @param points Sélection de points
     * @param img Image sur laquelle dessiner
     * @param col Couleur ou texture de dessin
     */
    public void pasteList(List<Point3D> points, PixM img, ITexture col) {

        for (int i = 0; i < points.size(); i++) {

            int x = (int) (double) points.get(i).getX();
            int y = (int) (double) points.get(i).getY();

            int rgb = col.getColorAt(
                    points.get(i).getX() / img.getColumns(),
                    points.get(i).getY() / img.getLines());

            double[] rgbD = Lumiere.getDoubles(rgb);
            for (int i1 = 0; i1 < 3; i1++) {
                img.setCompNo(i1);
                img.set(x, y, rgbD[i1]);
            }

        }

    }

    /*
     *
     * @param points Sélection de points
     * @param img Image sur laquelle dessiner
     * @param objets Objets à dessiner sur l'image (3d-2d)
     */
    public void pasteList(List<Point3D> points, PixM img, Scene objets) {

        for (int i = 0; i < points.size(); i++) {

            int x = (int) (double) points.get(i).getX();
            int y = (int) (double) points.get(i).getY();

            objets.getObjets().getData1d().forEach(new Consumer<Representable>() {
                @Override
                public void accept(Representable representable) {
                    if (representable instanceof ParametricCurve)
                        img.plotCurve((ParametricCurve) representable, representable.texture());///Clip curve for moving, rotating, scaling
                    else if (representable instanceof Point3D) {
                        img.setValues((int) (double) ((Point3D) representable).getX(),
                                (int) (double) ((Point3D) representable).getY(),
                                Lumiere.getDoubles(representable.texture().getColorAt(0.5, 0.5)));
                    }
                }
            });

        }

    }

    @Override
    public boolean process(File in, File out) {
<<<<<<<< HEAD:feature0/selection/Paste.java
        Image read = one.empty3.ImageIO.read(in);
========
        Image read = ImageIO.read(in);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/selection/Paste.java
        PixM pixM = PixM.getPixM(read, maxRes);
        return true;
    }
}
