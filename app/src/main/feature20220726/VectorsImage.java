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

import one.empty3.libs.Image;
import one.empty3.libs.Image;

public class VectorsImage extends M3 {
    public VectorsImage(int columns, int lines, int columnsIn, int linesIn) {
        super(columns, lines, 2, 1);
    }

    public VectorsImage(Image image1) {
        this(image1.getWidth(), image1.getHeight(), 2, 1);
        PixM pixM = new PixM(image1);
        //M3 gradientDoubleValuesMap =pixM.applyFilter(new GradientFilter(image1));
        // construire les vecteurs
    }


}
