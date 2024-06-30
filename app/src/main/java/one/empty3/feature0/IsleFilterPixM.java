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

package one.empty3.feature0;

import android.graphics.Color;

public class IsleFilterPixM
        extends FilterPixM {
    private Color selColor;
    private Color oppositeColor;
    private double threshold;
    private float selComps[] = new float[4];
    private float oppComps[] = new float[4];

    public IsleFilterPixM(PixM pix) {
        super(pix);
    }

    public boolean selectPoint(int x, int y) {
        double[] sel = new double[3];
        getColor(x, y, sel);
        double d = 0;
        for (int i = 0; i < 3; i++)
            d += (sel[i] - selComps[i])
                    * (sel[i] - selComps[i]);
        return Math.sqrt(d) < threshold ? true : false;
    }

    public void setCValues(Color background,
                           Color sel, double threshold) {
        this.oppositeColor = background;
        this.selColor = sel;
        this.threshold = threshold;
        selColor.getComponents(this.selComps);
        oppositeColor.getComponents(this.oppComps);

    }

    public double filter(double x, double y) {
        return 0.0;
    }

    public void filter() {
    }
}
