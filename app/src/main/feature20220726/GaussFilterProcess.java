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


public class GaussFilterProcess extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        if (!in.getName().endsWith(".jpg"))

            return false;

        matrix.PixM pix = null;
        Image img = null;

        try {
            img = new Image(in);
            pix = matrix.PixM.getmatrix.PixM(img, maxRes);

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;

            // assertTrue(false);


        }

        GaussFiltermatrix.PixM th = new GaussFiltermatrix.PixM(pix, 1);

        matrix.PixM pixRes = new matrix.PixM(pix.getColumns(), pix.getLines());
        for (int c = 0; c < 3; c++) {
            th.setCompNo(c);
            pix.setCompNo(c);
            pixRes.setCompNo(c);
            for (int i = 0; i < pix.getColumns(); i++)
                for (int j = 0; j < pix.getLines(); j++)
                    pixRes.set(i, j, th.filter(i, j));
        }


        matrix.PixM normalize = pix.normalize(0.0, 1.0);


        //


        try {

            new Image(1,1,4).saveToFile(normalize.getImage(), "JPEG", out);
            return true;
        } catch (Exception ex) {

        }
        return true;
    }

}

