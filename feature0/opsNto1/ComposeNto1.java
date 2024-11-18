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

package one.empty3.feature.opsNto1;

import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;

import one.empty3.ImageIO;

import java.io.File;
import java.io.IOException;

public class ComposeNto1 extends ProcessFile {
    public boolean addEntry(Composer composer, ProcessFile... processFiles) {
        return false;
    }

    @Override
    public boolean process(File in, File out) {
        try {
            boolean success = false;
            PixM inpix = PixM.getPixM(one.empty3.ImageIO.read(in), maxRes);
            PixM outpix = PixM.getPixM(one.empty3.ImageIO.read(in), maxRes);
            //success = processMem(inpix, outpix);
            one.empty3.ImageIO.write(outpix.getImage().getBitmap(), "jpg", out, shouldOverwrite);
            return success;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
