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

import one.empty3.featureAndroid.PixM;
import one.empty3.io.ProcessFile;

import one.empty3.libs.Image;

import java.io.File;


public class GaussFilterProcess extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        if (!in.getName().endsWith(".jpg"))

            return false;

        one.empty3.featureAndroid.PixM pix = null;
        Image img = null;

        try {
            img = one.empty3.ImageIO.read(in);
            pix = one.empty3.featureAndroid.PixM.getPixM(img, maxRes);

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;

            // assertTrue(false);


        }

        GaussFilterPixM th = new GaussFilterPixM(pix, 1);

        one.empty3.featureAndroid.PixM pixRes = new one.empty3.featureAndroid.PixM(pix.getColumns(), pix.getLines());
        for (int c = 0; c < 3; c++) {
            th.setCompNo(c);
            pix.setCompNo(c);
            pixRes.setCompNo(c);
            for (int i = 0; i < pix.getColumns(); i++)
                for (int j = 0; j < pix.getLines(); j++)
                    pixRes.set(i, j, th.filter(i, j));
        }


        PixM normalize = pix.normalize(0.0, 1.0);


        //


        try {

            one.empty3.ImageIO.write(normalize.getImage().getImage(), "JPEG", out, shouldOverwrite);
            return true;
        } catch (Exception ex) {

        }
        return true;
    }

}

