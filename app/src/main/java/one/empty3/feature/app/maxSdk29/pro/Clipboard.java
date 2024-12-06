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

package one.empty3.feature.app.maxSdk29.pro;


import android.graphics.RectF;

import matrix.matrix.PixM;

class Clipboard {
    public static Clipboard defaultClipboard;
    public boolean copied = false;
    private matrix.PixM source;
    private RectF destination;

    public Clipboard(matrix.PixM source) {
        this.source = source;
    }

    public matrix.PixM getSource() {
        return source;
    }

    public void setSource(matrix.PixM source) {
        this.source = source;
    }

    public RectF getDestination() {
        return destination;
    }

    public void setDestination(RectF destination) {
        this.destination = destination;
    }
}