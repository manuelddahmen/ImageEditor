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

package one.empty3.androidFeature;


import matrix.matrix.PixM;

/*
 * Prendre pour points d'intérêt les zones denses en intensité
 * Prendre les mesures entre elles et avec les zones moins denses
 *
 */
public class InforceDensity {
    private double rarityRatio = 0.0;

    public InforceDensity(matrix.PixM m, double rarityRatio) {
        this.rarityRatio = rarityRatio;
        Histogram h = new Histogram(m, 10, 0.3, m.getColumns() / 10, 0.1);
        // circle r grands I faible
        // circle r petits ou grands. I haute. 

    }
}
