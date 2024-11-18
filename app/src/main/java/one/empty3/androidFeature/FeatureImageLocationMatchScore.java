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

import java.io.File;

import one.empty3.featureAndroid.PixM;

public class FeatureImageLocationMatchScore {
    // matrix size, matrix values, thresoldused, x, y, imageLocation, deltaE??
    private int sizeSquare = 3;
    private int componentSize;
    private PixM values;
    private double thresoldused = 0.5;
    private int x;
    private int y;
    private File image;
    private double deltaEnergy;

}
