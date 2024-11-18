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

package one.empty3.feature.selection;

import one.empty3.feature.PixM;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;

import java.util.ArrayList;

import java.util.List;

public class SelectionImpl extends Selection {
    public List<Point3D> select(List<Point3D> preSelection, PixM pix, int rgb, double threshold) {
        List<Point3D> selection = new ArrayList();
        double[] doubles = Lumiere.getDoubles(rgb);
        if (preSelection != null) {
            for (Point3D point3D : preSelection) {
                int c = 0;
                double[] doubles1 = Lumiere.getDoubles(point3D.texture().getColorAt(0.0, 0.0));
                for (int j = 0; j < 3; j++) {
                    if (doubles1[j] <= doubles[j] - threshold &&
                            doubles1[j] >= doubles[j] + threshold) {
                        c++;
                    }
                }
                if (c == 3) {
                    selection.add(point3D);
                }
            }
        } else {
            for (int x1 = 0; x1 < pix.getColumns(); x1++) {
                for (int y1 = 0; y1 < pix.getLines(); y1++) {
                    int c = 0;
                    double[] doubles1 = pix.getValues(x1, y1);
                    for (int j = 0; j < 3; j++) {
                        if (doubles1[j] <= doubles[j] - threshold &&
                                doubles1[j] >= doubles[j] + threshold) {
                            c++;
                        }
                    }
                    if (c == 3) {
                        selection.add(pix.getP(x1, y1));
                    }
                }
            }
        }
        return selection;
    }

    @Override
    public List<Point3D> selectColorPoint(List<Point3D> preSelection, PixM pix, int rgb,
                                          int x, int y, double threshold) {
        ArrayList<Point3D> selection = new ArrayList();
        double[] doubles = Lumiere.getDoubles(rgb);
        if (preSelection != null) {
            for (Point3D point3D : preSelection) {
                int c = 0;
                double[] doubles1 = Lumiere.getDoubles(point3D.texture().getColorAt(0.0, 0.0));
                for (int j = 0; j < 3; j++) {
                    if (doubles1[j] <= doubles[j] - threshold &&
                            doubles1[j] >= doubles[j] + threshold) {
                        c++;
                    }
                }
                if (c == 3) {
                    selection.add(point3D);
                }
            }
        } else {
            for (int x1 = 0; x1 < pix.getColumns(); x1++) {
                for (int y1 = 0; y1 < pix.getLines(); y1++) {
                    int c = 0;
                    double[] doubles1 = pix.getValues(x1, y1);
                    for (int j = 0; j < 3; j++) {
                        if (doubles1[j] <= doubles[j] - threshold &&
                                doubles1[j] >= doubles[j] + threshold) {
                            c++;
                        }
                    }
                    if (c == 3) {
                        selection.add(pix.getP(x1, y1));
                    }
                }
            }
        }
        return selection;
    }

    @Override
    public List<Point3D> selectPoint(List<Point3D> preSelection, PixM pix, int x, int y) {
        return null;
    }

    @Override
    public List<Point3D> selectInRect(List<Point3D> preSelection, PixM pix, int x1, int y1, int x2, int y2) {
        return null;
    }
}
