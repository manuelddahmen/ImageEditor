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

<<<<<<<< HEAD:feature0/motion/Motion.java
package one.empty3.feature.motion;

import one.empty3.feature.PixM;

========
package one.empty3.androidFeature.motion;

import matrix.PixM;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/Motion.java
import one.empty3.libs.Image;

import java.util.ArrayList;


/*
 * Motion
 * resize part (+/-/show/hide), move part, rotate part
 */
public abstract class Motion /*extends ProcessFile */ {
    public static final int BUFFER_MAX_FRAMES = 26;
    public ArrayList<Image> frames = new ArrayList<>();


    public boolean addFrame(Image bufferedImage) {
        if (bufferedImage != null) {
            this.frames.add(bufferedImage);
        }
        return frames.size() > BUFFER_MAX_FRAMES;
    }

<<<<<<<< HEAD:feature0/motion/Motion.java
    public one.empty3.libs.Color processFrame() {
========
    public Image processFrame() {
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/Motion.java
        PixM frame1 = null;
        PixM frame2 = null;
        if (frames.size() == 0 || frames.get(0) == null)
            return null;
        if (frames.size() >= 2 && frames.size() < BUFFER_MAX_FRAMES) {

            frame1 = new PixM(frames.get(0));
            frame2 = new PixM(frames.get(1));
            frames.remove(0);
        } else if (frames.size() >= BUFFER_MAX_FRAMES) {
            frame1 = new PixM(frames.get(0));
            frame2 = new PixM(frames.get(1));
            frames.remove(0);
        } else {
            return null;
        }

        return process(frame1, frame2);
    }

<<<<<<<< HEAD:feature0/motion/Motion.java
    public abstract one.empty3.libs.Color process(PixM frame1, PixM frame2);
========
    public abstract Image process(PixM frame1, PixM frame2);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/motion/Motion.java

}
