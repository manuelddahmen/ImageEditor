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

package one.empty3.feature20220726.opsNto1;

import one.empty3.feature20220726.matrix.PixM;
import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

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
            matrix.PixM inpix = matrix.PixM.getmatrix.PixM(new Image(in), maxRes);
            matrix.PixM outpix = matrix.PixM.getmatrix.PixM(new Image(in), maxRes);
            //success = processMem(inpix, outpix);
            new Image(1,1,4).saveToFile(outpix.getImage(), "jpg", out);
            return success;
        } catch (Exception ex) {}

        return false;
    }

}
