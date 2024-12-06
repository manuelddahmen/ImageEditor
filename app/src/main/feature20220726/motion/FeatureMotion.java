/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.feature20220726.motion;

import one.empty3.libs.*;

import one.empty3.feature20220726.FeatureMatch;
import one.empty3.feature20220726.matrix.PixM;
import one.empty3.library.Lumiere;

import javaAnd.awt.*;
import one.empty3.libs.Image;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FeatureMotion extends Motion {
    @Override
    public Image process(matrix.PixM frame1, matrix.PixM frame2) {
        FeatureMatch featureMatch = new FeatureMatch();

        List<double[]> match = featureMatch.match(frame1, frame2);

        Image bufferedImage = new javaAnd.awt.image.Image(frame1.getColumns(), frame1.getLines(), javaAnd.awt.image.Image.TYPE_INT_RGB);

        for (int i = 0; i < frame1.getColumns(); i++) {
            for (int j = 0; j < frame1.getLines(); j++) {
                for (int c = 0; c < frame1.getCompCount(); c++) {
                    bufferedImage.setRGB(i, j, Lumiere.getInt(frame1.getValues(i, j)));
                }
            }
        }
        for (int i = 0; i < match.size(); i++) {
            bufferedImage.setRGB((int) match.get(i)[0], (int) match.get(i)[1], Color.WHITE.getRGB());
        }
        return bufferedImage.bufferedImage;
    }
}
