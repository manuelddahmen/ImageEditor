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

import one.empty3.feature.app.pro.M3;
import one.empty3.featureAndroid.PixM;
import one.empty3.io.ProcessFile;

import java.io.File;

public class ExtremaProcess extends ProcessFile {
    private final int pointsCount;
    private final int neighbourSize;


    public ExtremaProcess() {
        this.neighbourSize = 3;//neighbourSize;
        this.pointsCount = 1; //pointsCount;
        //sub = new double[4*lines*columns];
    }

    public boolean process(File in, File out) {
        one.empty3.featureAndroid.PixM pix = null;
        if (!isImage(in))
            return false;

        try {
            pix = new one.empty3.featureAndroid.PixM(one.empty3.ImageIO.read(in));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }


        LocalExtrema le = new LocalExtrema(
                pix.getColumns(), pix.getLines(),
                neighbourSize, pointsCount);


        PixM m = le.filter(new M3(pix, 1, 1)).getImagesMatrix()[0][0];

        try {
            one.empty3.ImageIO.write(m.normalize(0, 1).getImage().getBitmap(), "jpg", out, shouldOverwrite);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


    }


}
