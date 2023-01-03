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

package one.empty3.feature_arr_style;

public class V extends GMatrix {
    public V(int l, int c) {
        super(l, c);
    }

    public V(int lc) {
        super(lc, 1);


    }

    public GMatrix outerProduct(V vec1, V vec2) {
        GMatrix GMatrix1 = new GMatrix(vec2.columns, vec1.columns);
        for (int m = 0; m < vec1.columns; m++) { // line incr

            for (int n = 0; n < vec2.columns; n++) {
                GMatrix1.set(m, n, vec1.get(1, m) * vec2.get(1, n));
            }

        }
        return GMatrix1;
    }
}
