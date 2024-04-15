/*
 * Copyright (c) 2023.
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

package one.empty3.library.core;

import one.empty3.library.Representable;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

/***
 * curve. rotation. straight, curved. turtle
 * motif repeat at angle, copy paste
 * ex from center flow rotate draw
 * borders in patterns 


 */
public class Mandala extends Representable {
    private final double resX;
    private final double resY;
    private ParametricSurface p;

    public Mandala(ParametricSurface plan, double resX, double resY) {
        this.p = plan;
        this.resX = resX;
        this.resY = resY;
    }

    public void addCurveX0_1__Y0_1(
            ParametricCurve pc, double radius1,
            double radius2, double angleRepeatDegree) {

    }
}
