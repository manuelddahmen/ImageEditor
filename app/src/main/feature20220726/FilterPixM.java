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

package one.empty3.feature20220726;

import one.empty3.libs.Image;

public abstract class FilterPixM extends PixM {
    public final static int NORM_NONE = 0;
    public final static int NORM_MEAN = 1;
    public final static int NORM_MAX = 2;
    public final static int NORM_FLOOR_0 = 4;
    public final static int NORM_FLOOR_1 = 8;
    public final static int NORM_CUSTOM = 16;

    public int getNormalize() {
        return normalize;
    }

    public FilterPixM setNormalize(int normalize) {
        this.normalize = normalize;
        return this;
    }

    private int normalize = NORM_NONE;

    public FilterPixM(int l, int c) {
        super(l, c);
    }

    public FilterPixM(one.empty3.libs.Image image) {
        super(image);
    }

    public FilterPixM(PixM image) {
        super(image.getImage());
    }

    public abstract double filter(double i, double i1);

}
