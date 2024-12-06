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

<<<<<<<< HEAD:feature0/motion/FeatureMotion.java
package one.empty3.feature.motion;

import android.graphics.Color;

import one.empty3.feature.FeatureMatch;
import one.empty3.feature.PixM;
========
package one.empty3.androidFeature.motion;

import android.graphics.Color;

import one.empty3.androidFeature.FeatureMatch;
import matrix.PixM;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/FeatureMotion.java
import one.empty3.library.Lumiere;

import one.empty3.libs.Image;

import java.util.List;

public class FeatureMotion extends Motion {
    @Override
<<<<<<<< HEAD:feature0/motion/FeatureMotion.java
    public one.empty3.libs.Color process(PixM frame1, PixM frame2) {
========
    public Image process(PixM frame1, PixM frame2) {
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/FeatureMotion.java
        FeatureMatch featureMatch = new FeatureMatch();

        List<double[]> match = featureMatch.match(frame1, frame2);

<<<<<<<< HEAD:feature0/motion/FeatureMotion.java
        Image bufferedImage = new one.empty3.libs.Color(frame1.getColumns(), frame1.getLines(), one.empty3.libs.Color.TYPE_INT_RGB);
========
        Image bufferedImage = new one.empty3.libs.Image(frame1.getColumns(), frame1.getLines());
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/FeatureMotion.java

        for (int i = 0; i < frame1.getColumns(); i++) {
            for (int j = 0; j < frame1.getLines(); j++) {
                for (int c = 0; c < frame1.getCompCount(); c++) {
                    bufferedImage.setRgb(i, j, Lumiere.getInt(frame1.getValues(i, j)));
                }
            }
        }
        for (int i = 0; i < match.size(); i++) {
            bufferedImage.setRgb((int) match.get(i)[0], (int) match.get(i)[1], Color.WHITE);
        }
        return bufferedImage;
    }
}
