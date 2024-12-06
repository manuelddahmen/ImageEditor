/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;

import java.util.Arrays;
import java.util.function.Consumer;

/*
 * Multi-Image Matching using Multi-Scale Oriented Patches
 */
public class MIMmops {
    public static matrix.PixM applyMultipleFilters(PixM pixM, int level, FilterpixM... filter) {

        final matrix.PixM[] res = {matrix.PixM};
        for (int i = 0; i < level; i++) {
            // Hl(x, y) = ∇σd Pl(x, y)∇σd Pl(x, y)T∗ gσi(x, y)
            // g      -> Gauss filter
            //  Sigma -> filter size
            //  level -> filter iterations
            // i      -> Iteration value of sigma (end condition?)
            // ∇σd Pl     -> Picture "derivative" (iteration x (gradient(image)))
            //               at level l and at sigma
            // ^T ??? -> Transpose smoothed matrix ?

            // La dérivée et le filtre ne sont pas les mêmes. sommeMatrice(e-..)   et
            // (get(x+1)-2*get(x)+get(x-1) + get(y+1)+2*get(y)-get(y))/4/4 ou /1/1 ??
            Arrays.stream(filter).sequential().forEach(new Consumer<FilterPixM>() {
                @Override
                public void accept(FilterPixM filtermatrix.PixM) {
                    res[0] = res[0].applyFilter(filtermatrix.PixM);


                }
            });

        }


        return res[0];
    }


    public M matGrad(matrix.PixM image, M3 gradientX, M3 gradientY) {
        M matGrad = null;
        // image :  smoothes
        // image : gradientX M3(w, h, 2, 1)
        // image : gradientY M3(w, h, 1, 2)

        // ?mode radar?

        // image of matrix matGrad. outer product of vectors gradient.


        // image of angles gradient orientation atan(y/x)
        return matGrad;
    }

    /*
     * fHM(x, y) = det Hl(x, y)
     * tr Hl(x, y)
     * =
     * λ1λ2
     * λ1 + λ2
     */
    public void cornerStrength(M matGrad) {

    }
}
