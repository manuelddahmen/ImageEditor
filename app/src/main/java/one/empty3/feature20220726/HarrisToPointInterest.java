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

package one.empty3.feature20220726;

import one.empty3.library.Point3D;

import java.util.*;

public class HarrisToPointInterest extends FilterPixM {
    private PixM m;

    public HarrisToPointInterest(int c, int l) {
        super(c, l);
    }
    // nabla
    // sobel x sobel y
    // ou gxgx gxgy
    //    gygx gygy
    // p ij
    // mat Ip, Ip~
    // trace/determinant extract
    // m(p) at ij
    // localextrema filter
    // !=0 => points of interest

    public double filter(double x, double y) {
        m.setCompNo(getCompNo());
        int i = (int) x, j = (int) y;
        //double gxgx, gxgy, gygx, gygy;
        double gpgx = m.get(i + 1, j) - m.get(i, j);
        double gpgy = m.get(i, j + 1) - m.get(i, j);
        double gpgx1 = m.get(i, j) - m.get(i - 1, j);
        double gpgy1 = m.get(i, j) - m.get(i, j - 1);
        double gxgx = gpgx - gpgx1;
        double gygx = gpgy1 - gpgx;
        double gxgy = gpgx1 - gpgy;
        double gygy = gpgy1 - gpgy;

        return (gxgx * gygy - gxgy * gygx) / (gxgx * gygy);
    }

    public List<Point3D> getPoi() {
        LocalExtrema le = new LocalExtrema(m.getColumns(),
                lines, 3, 0);
        PixM m2 = le.filter(new M3(m, 1, 1)).getImagesMatrix()[0][0];
        List<Point3D> poi = new ArrayList<>();
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < lines; j++)
                if (m2.get(i, j) != 0 && !Double.isNaN(m2.get(i, j))
                        && !Double.isInfinite(m2.get(i, j)))
                    poi.add(new Point3D(
                            (double) i,
                            (double) j,
                            m2.get(i, j)
                    ));
        return poi;
    }
}
