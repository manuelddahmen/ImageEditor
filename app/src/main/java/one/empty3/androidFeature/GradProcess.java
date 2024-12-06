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

package one.empty3.androidFeature;


import java.io.File;
import java.util.Objects;

import one.empty3.feature.app.pro.M3;
import matrix.PixM;
import one.empty3.io.ProcessFile;

public class GradProcess extends ProcessFile {

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public boolean process(File in, File out) {
        File file = in;
        one.empty3.featureAndroid.matrix.PixM pix;
        try {
            pix = one.empty3.featureAndroid.matrix.PixM.getmatrix.PixM(Objects.requireNonNull(one.empty3.ImageIO.read(file)), maxRes);
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            one.empty3.featureAndroid.matrix.PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();

            Linear linear = new Linear(imagesMatrix[0][0], imagesMatrix[0][1],
                    new one.empty3.featureAndroid.matrix.PixM(imagesMatrix[0][0].getColumns(), imagesMatrix[0][0].getLines()));

            boolean b = linear.op2d2d(new char[]{'-'}, new int[][]{{0}, {1}}, new int[]{2});

            matrix.PixM image = linear.getImages()[2];

            one.empty3.ImageIO.write(image.normalize(0.0, 1.0).getImage().getImage(), "jpg", out, shouldOverwrite);

            addSource(out);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
