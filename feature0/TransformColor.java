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

<<<<<<<< HEAD:feature0/TransformColor.java
package one.empty3.feature;
========
package one.empty3.androidFeature;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/TransformColor.java

import matrix.PixM;
import one.empty3.io.ProcessFile;

import one.empty3.ImageIO;

import java.io.File;

public class TransformColor extends ProcessFile {
    @Override
    public boolean process(File in, File out) {

<<<<<<<< HEAD:feature0/TransformColor.java
            PixM pix = PixM.getPixM(one.empty3.ImageIO.read(in), maxRes);
========
        matrix.PixM pix = PixM.getPixM(ImageIO.read(in), maxRes);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/TransformColor.java

        for (int i = 0; i < pix.getColumns(); i++) {
            for (int j = 0; j < pix.getLines(); j++) {
                for (int c = 0; c < pix.getCompNo(); c++) {
                    pix.setCompNo(c);
                    pix.set(i, j, 1 - pix.get(i, j));
                }
            }
<<<<<<<< HEAD:feature0/TransformColor.java

            one.empty3.ImageIO.write(pix.normalize(0.0, 1.0).getImage().getBitmap(), "jpg", out, shouldOverwrite);

            return true;
        } catch (IOException e) {

            e.printStackTrace();

            return false;
========
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/TransformColor.java
        }

        ImageIO.write(pix.normalize(0.0, 1.0).getImage().getBitmap(), "jpg", out, shouldOverwrite);

        return true;
    }
}
