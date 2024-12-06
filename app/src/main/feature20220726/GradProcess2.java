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

import java.io.File;
import java.io.IOException;

public class GradProcess2 extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        matrix.PixM matrix.PixM = matrix.PixM.getmatrix.PixM(new Image(in), maxRes);
        matrix.PixM matrix.PixMout = new matrix.PixM(matrix.PixM.getColumns(), matrix.PixM.getLines());

        for (int x = 0; x < matrix.PixM.getColumns(); x++)
            for (int y = 0; y < matrix.PixM.getColumns(); y++)
                for (int c = 0; c < 3; c++) {
                    matrix.PixM.setCompNo(c);
                    matrix.PixMout.setCompNo(c);
                    matrix.PixMout.set(x, y, -
                            matrix.PixMout.get(x - 1, y) -
                            matrix.PixMout.get(x, y - 1) -
                            matrix.PixMout.get(x + 1, y) -
                            matrix.PixMout.get(x, y + 1)
                            + 4 * matrix.PixMout.get(x, y + 1));
                }

        new Image(1,1,4).saveToFile(matrix.PixMout.normalize(0, 1).getImage(), "jpg", out);
        return true;
    }
}
