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

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import one.empty3.library.core.lighting.Colors;

public class HarrisProcess extends ProcessFile {
    PixM m;

    public HarrisProcess() {

    }

    public boolean process(File in, File out) {
        try {
            Properties resourceBundle = new Properties();
            resourceBundle.load(new FileInputStream(new File("resources/settings.properties")));
            Bitmap img = Objects.requireNonNull(ImageIO.read(in)).bitmap;
            PixM m;
            m = PixM.getPixM(img, Double.parseDouble(resourceBundle.getProperty("HarrisProcess.maxRes")));

            PixM m2;

            HarrisToPointInterest h = new HarrisToPointInterest(2, 2);

            m2 = m.applyFilter(h);

            LocalExtrema le;

            le = new LocalExtrema(m2.getColumns(), m2.getLines(), 7, 5);

            m2 = le.filter(new
                    M3(m2, 1, 1)).getImagesMatrix()[0][0];

            ImageIO.write(m2.getImage(), "JPEG", out);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
