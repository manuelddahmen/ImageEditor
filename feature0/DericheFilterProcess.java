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

<<<<<<<< HEAD:feature0/DericheFilterProcess.java
package one.empty3.feature;
========
package one.empty3.androidFeature;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/DericheFilterProcess.java

import matrix.PixM;
import one.empty3.io.ProcessFile;

import one.empty3.ImageIO;

import java.io.File;

public class DericheFilterProcess extends ProcessFile {


    @Override
    public boolean process(File in, File out) {
<<<<<<<< HEAD:feature0/DericheFilterProcess.java
        try {
            PixM pixM = PixM.getPixM(one.empty3.ImageIO.read(in), maxRes);


            one.empty3.ImageIO.write(pixM.getImage().getBitmap(), "jpg", out, shouldOverwrite);
========
        matrix.PixM pixM = PixM.getPixM(ImageIO.read(in), maxRes);


        ImageIO.write(pixM.getImage().getBitmap(), "jpg", out, shouldOverwrite);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/DericheFilterProcess.java


        return false;
    }
}
