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

<<<<<<<< HEAD:feature0/IdentNullProcess.java
package one.empty3.feature;
========
package one.empty3.androidFeature;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/IdentNullProcess.java

import matrix.PixM;
import one.empty3.io.ProcessFile;

<<<<<<<< HEAD:feature0/IdentNullProcess.java
import one.empty3.ImageIO;

========
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/IdentNullProcess.java
import java.io.File;
import java.util.Objects;

public class IdentNullProcess extends ProcessFile {

    @Override
    public boolean process(File in, File out) {
<<<<<<<< HEAD:feature0/IdentNullProcess.java
        PixM pixM = null;
        if(maxRes>0) {
            pixM = PixM.getPixM(Objects.requireNonNull(one.empty3.ImageIO.read(in)), maxRes);
========
        matrix.PixM pixM = null;
        if (maxRes > 0) {
            pixM = matrix.PixM.getPixM(Objects.requireNonNull(one.empty3.ImageIO.read(in)), maxRes);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/IdentNullProcess.java
        } else {
            pixM = new PixM(Objects.requireNonNull(one.empty3.ImageIO.read(in)));
        }
        assert pixM != null;
<<<<<<<< HEAD:feature0/IdentNullProcess.java
        one.empty3.ImageIO.write(pixM.getBitmap(), "jpg", out);
========
        one.empty3.ImageIO.write(pixM.getBitmap().getBitmap(), "jpg", out);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/IdentNullProcess.java
        addSource(out);
        return true;

    }

}
