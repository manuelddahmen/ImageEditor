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

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.libs.Image;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class IdentNullProcess extends ProcessFile {

    @Override
    public boolean process(File in, File out) {
        matrix.PixM matrix.PixM = null;
        matrix.PixM = matrix.PixM.getmatrix.PixM(Objects.requireNonNull(new Image(in)), maxRes);
        new Image(1,1,4).saveToFile(matrix.PixM.getImage(), "jpg", out);
        addSource(out);
        return true;

    }

}
