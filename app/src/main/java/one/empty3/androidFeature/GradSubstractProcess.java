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

import one.empty3.feature.app.pro.M3;
import one.empty3.featureAndroid.PixM;
import one.empty3.io.ProcessFile;

public class GradSubstractProcess extends ProcessFile {

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public boolean process(File in, File out) {

        //if (!in.getName().endsWith(".jpg"))
        //    return false;
        File file = in;
        one.empty3.featureAndroid.PixM pix;
        try {
            pix = one.empty3.featureAndroid.PixM.getPixM(one.empty3.ImageIO.read(file), maxRes);
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            one.empty3.featureAndroid.PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();
            Linear linear = new Linear(imagesMatrix[0][0], imagesMatrix[0][1], new PixM(pix.getColumns(), pix.getLines()));
            linear.op2d2d(new char[]{'-'}, new int[][]{{1}, {0}}, new int[]{2});
            one.empty3.ImageIO.write(linear.getImages()[2].normalize(0.0, 1.0).getImage().getImage(), "jpg", out, shouldOverwrite);

            addSource(out);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
