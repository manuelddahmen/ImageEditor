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

import one.empty3.ImageIO;

import java.io.File;

public class GradProcess2 extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        one.empty3.featureAndroid.PixM pixM = one.empty3.featureAndroid.PixM.getPixM(ImageIO.read(in), maxRes);
        one.empty3.featureAndroid.PixM pixMout = new PixM(pixM.getColumns(), pixM.getLines());

        for (int x = 0; x < pixM.getColumns(); x++)
            for (int y = 0; y < pixM.getColumns(); y++)
                for (int c = 0; c < 3; c++) {
                    pixM.setCompNo(c);
                    pixMout.setCompNo(c);
                    pixMout.set(x, y, -
                            pixMout.get(x - 1, y) -
                            pixMout.get(x, y - 1) -
                            pixMout.get(x + 1, y) -
                            pixMout.get(x, y + 1)
                            + 4 * pixMout.get(x, y + 1));
                }

        ImageIO.write(pixMout.normalize(0, 1).getImage().getImage(), "jpg", out, shouldOverwrite);
        return true;
    }
}
