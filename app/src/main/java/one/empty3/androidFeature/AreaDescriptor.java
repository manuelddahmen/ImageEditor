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

package one.empty3.androidFeature;/*

import one.empty3.library.Point3D;
import one.empty3.library.RepresentableConteneur;

import java.util.Arrays;
import java.util.function.Consumer;

public abstract class AreaDescriptor {
    RepresentableConteneur area = new RepresentableConteneur();

    public AreaDescriptor(int x, int y, int sizeX, int sizeY) {

    }

    public void setRegion(Point3D... p) {
        final int[] i = new int[1];
        Arrays.stream(p).sequential().forEach(new Consumer<Point3D>() {
            @Override
            public void accept(Point3D point3D) {
                area.add(point3D);
                i[0] = i[0] + 1;
                //this.c = p[i].texture().getColorAt(0.5, 0.5);
                //this.p = p;
            }
        });
    }

    public abstract Filtermatrix.PixM getFilter();

    public abstract double match();
}
*/