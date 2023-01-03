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

package one.empty3.feature.app.maxSdk29.pro;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;

import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.core.nurbs.ParametricSurface;

public class ShapeSurface extends Shape {
    private ParametricSurface parametricSurface;

    public void setSurface(ParametricSurface parametricSurface) {
        this.parametricSurface = parametricSurface;
    }

    public void drawVertex(Canvas canvas, Paint paint) {
        int sizeU = (int) ((parametricSurface.getEndU() - parametricSurface.getStartU())
                / parametricSurface.getIncrU());
        int sizeV = (int) ((parametricSurface.getEndV() - parametricSurface.getStartV())
                / parametricSurface.getIncrV());
        float[] vertices = new float[sizeU * sizeV * 6];
        int[] texts = new int[sizeU * sizeV * 2];

        int vIdx = 0;
        int cIdx = 0;
        for (double u = parametricSurface.getStartU(); u < parametricSurface.getEndU();
             u = u + parametricSurface.getIncrU()) {
            for (double v = parametricSurface.getStartV(); v < parametricSurface.getEndV();
                 v = v + parametricSurface.getIncrV()) {

                Polygon points = parametricSurface.getElementSurface(u, parametricSurface.getIncrU(),
                        v, parametricSurface.getIncrV());


                Object[] objects = points.getPoints().getData1d().toArray();

                float[] values = new float[objects.length * 2];

                for (int i = 0; i < values.length; i += 2) {
                    values[i] = (float) (double) ((Point3D) objects[i / 2]).get(0);
                    values[i + 1] = (float) (double) ((Point3D) objects[i / 2]).get(1);
                }

                vertices[vIdx * 3] = values[0];
                vertices[vIdx * 3 + 1] = values[1];
                vertices[vIdx * 3 + 2] = values[2];
                vertices[vIdx * 3 + 3] = values[3];
                vertices[vIdx * 3 + 4] = values[4];
                vertices[vIdx * 3 + 1 + 5] = values[5];

                vIdx += 2;

                texts[cIdx] = parametricSurface.texture().getColorAt(u, v);
                cIdx++;

                vertices[vIdx * 3] = values[0];
                vertices[vIdx * 3 + 1] = values[1];
                vertices[vIdx * 3 + 2] = values[6];
                vertices[vIdx * 3 + 3] = values[7];
                vertices[vIdx * 3 + 4] = values[4];
                vertices[vIdx * 3 + 1 + 5] = values[5];

                vIdx += 2;

                texts[cIdx] = parametricSurface.texture().getColorAt(u, v);
                cIdx++;

            }
        }
        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, sizeU * sizeV,
                vertices, 0, null, 0, texts, 0,
                null, 0, 0, paint);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Point3D p1 = parametricSurface.calculerPoint3D(parametricSurface.getStartU(),
                parametricSurface.getStartV());
        Point3D p2 = parametricSurface.calculerPoint3D(parametricSurface.getStartU(),
                parametricSurface.getStartV());

        for (double u = parametricSurface.getStartU(); u < parametricSurface.getEndU();
             u = u + parametricSurface.getIncrU()) {
            for (double v = parametricSurface.getStartV(); v < parametricSurface.getEndV();
                 v = v + parametricSurface.getIncrV()) {

                Polygon points = parametricSurface.getElementSurface(u, parametricSurface.getIncrU(),
                        v, parametricSurface.getIncrV());


                Object[] objects = points.getPoints().getData1d().toArray();

                float[] values = new float[objects.length * 2];

                for (int i = 0; i < values.length; i += 2) {
                    values[i] = (float) (double) ((Point3D) objects[i / 2]).get(0);
                    values[i + 1] = (float) (double) ((Point3D) objects[i / 2]).get(1);
                }
                canvas.drawPoints(values, paint);

                p1 = p2;

            }
        }
    }
}

