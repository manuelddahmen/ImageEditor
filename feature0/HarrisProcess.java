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

package one.empty3.feature;

import one.empty3.io.ProcessFile;

import one.empty3.ImageIO;
import one.empty3.libs.Image;

import java.io.File;

public class HarrisProcess extends ProcessFile {
    public boolean process(File in, File out) {
        try {
            Image img = one.empty3.ImageIO.read(in);
            PixM m2 = PixM.getPixM(img, maxRes);
            HarrisToPointInterest h = new HarrisToPointInterest(2, 2);

            m2.applyFilter(h);

            one.empty3.ImageIO.write(m2.normalize(0.0, 1.0).getImage().getBitmap(), "JPEG", out, shouldOverwrite);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
