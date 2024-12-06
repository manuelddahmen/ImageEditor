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

<<<<<<<< HEAD:feature0/motion/WebcamMotion.java
package one.empty3.feature.motion;

import matrix.PixM;
========
package one.empty3.androidFeature.motion;

import matrix.PixM;
import one.empty3.libs.Image;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/WebcamMotion.java

public class WebcamMotion extends Motion {

    @Override
<<<<<<<< HEAD:feature0/motion/WebcamMotion.java
    public one.empty3.libs.Color process(PixM frame1, PixM frame2) {
========
    public Image process(PixM frame1, PixM frame2) {
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/WebcamMotion.java
        return null;
    }
}
