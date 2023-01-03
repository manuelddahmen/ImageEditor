/*
 * Copyright (c) 2023.
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

package one.empty3.feature_arr_style;


import java.io.File;
import java.util.Objects;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;

public class GradProcess extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        PixM pix;
        try {
            if (maxRes == 0) {
                pix = new PixM((Objects.requireNonNull(Objects.requireNonNull(ImageIO.read(in)).bitmap)), true);
            } else {
                pix = PixM.getPixM(Objects.requireNonNull(Objects.requireNonNull(ImageIO.read(in)).bitmap), maxRes);
            }
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            PixM[][] imagesMatrix = gf.filter(
                    new M3(
                            pix, 2, 2)
            ).getImagesMatrix();
            Linear linear = new Linear(imagesMatrix[0][0], imagesMatrix[0][1], new PixM(pix.getColumns(), pix.getLines()));
            linear.op2d2d(new char[]{'+'}, new int[][]{{1, 0}}, new int[]{2});
            ImageIO.write(linear.getImages()[2].normalize(0.0, 1.0).getImage(), "jpg", out);

            imagesStack.add(out);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
