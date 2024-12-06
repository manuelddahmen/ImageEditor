/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

<<<<<<<< HEAD:feature0/GradProcess.java
package one.empty3.feature;
========
package one.empty3.androidFeature;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/GradProcess.java


import java.io.File;
import java.util.Objects;

<<<<<<<< HEAD:feature0/GradProcess.java
import one.empty3.ImageIO;
========
import one.empty3.feature.app.pro.M3;
import matrix.PixM;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/GradProcess.java
import one.empty3.io.ProcessFile;

public class GradProcess extends ProcessFile {

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public boolean process(File in, File out) {
        File file = in;
        matrix.PixM pix;
        try {
<<<<<<<< HEAD:feature0/GradProcess.java
            pix = PixM.getPixM(Objects.requireNonNull(one.empty3.ImageIO.read(file)), maxRes);
========
            pix = PixM.getPixM(Objects.requireNonNull(one.empty3.ImageIO.read(file)), maxRes);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/GradProcess.java
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            matrix.PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();

            Linear linear = new Linear(imagesMatrix[0][0], imagesMatrix[0][1],
                    new PixM(imagesMatrix[0][0].getColumns(), imagesMatrix[0][0].getLines()));

            boolean b = linear.op2d2d(new char[]{'-'}, new int[][]{{0}, {1}}, new int[]{2});

            PixM image = linear.getImages()[2];

            one.empty3.ImageIO.write(image.normalize(0.0, 1.0).getImage().getBitmap(), "jpg", out, shouldOverwrite);

            addSource(out);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
