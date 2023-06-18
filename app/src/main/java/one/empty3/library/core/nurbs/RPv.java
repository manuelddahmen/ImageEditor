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

package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.Representable;
import one.empty3.library.ZBuffer;

public class RPv extends ParametricVolume {
    private final ZBuffer zBuffer;
    private Representable representable;

    public RPv(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public Representable getRepresentable() {
        return representable;
    }

    public void setRepresentable(Representable representable) {
        this.representable = representable;
    }

    @Override
    public Point3D calculerPoint3D(Point3D p0) {
        return null;
    }
}
