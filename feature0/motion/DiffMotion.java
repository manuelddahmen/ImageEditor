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

<<<<<<<< HEAD:feature0/motion/DiffMotion.java
package one.empty3.feature.motion;

import one.empty3.feature.Linear;
import one.empty3.feature.PixM;

public class DiffMotion extends Motion {
    @Override
    public one.empty3.libs.Color process(PixM frame1, PixM frame2) {
========
package one.empty3.androidFeature.motion;

import one.empty3.androidFeature.Linear;
import one.empty3.featureAndroid.PixM;
import one.empty3.libs.Image;

public abstract class DiffMotion extends Motion {
    public Image process(PixM frame1, PixM frame2) {
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/DiffMotion.java

        Linear linear = new Linear(frame1, frame2, frame1.copy());
        linear.op2d2d(new char[]{'-'}, new int[][]{{1, 0, 2}}, new int[]{2});

<<<<<<<< HEAD:feature0/motion/DiffMotion.java
        return linear.getImages()[2].normalize(-1, 1, 0, 1).normalize(0, 1).getImage().getBitmap();
========
        //TOFIX
        return linear.getImages()[2].normalize(-1, 1, 0, 1).normalize(0, 1).getImage();
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/DiffMotion.java
    }
}
