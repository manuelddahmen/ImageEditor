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

package one.empty3.feature.app.maxSdk29.pro;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricCurve;

public class ShapeCurve extends Shape {
    private ParametricCurve parametricCurve;

    public void setCurve(ParametricCurve parametricCurve) {
        this.parametricCurve = parametricCurve;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Point3D p1 = parametricCurve.calculerPoint3D(parametricCurve.getStartU());
        Point3D p2 = parametricCurve.calculerPoint3D(parametricCurve.getStartU());

        for (double t = parametricCurve.getStartU(); t < parametricCurve.getEndU();
             t = t + parametricCurve.getIncrU().getData0d()) {

            canvas.drawLine((int) (double) p1.getX(), (int) (double) p1.getY(),
                    (int) (double) p2.getX(), (int) (double) p2.getY(), paint);

            p1 = p2;

        }
    }
}
