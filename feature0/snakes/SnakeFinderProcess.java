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

<<<<<<<< HEAD:feature0/snakes/SnakeFinderProcess.java
package one.empty3.feature.snakes;

import one.empty3.feature.PixM;
========
package one.empty3.androidFeature.snakes;

>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/snakes/SnakeFinderProcess.java
import one.empty3.io.ProcessFile;
import one.empty3.featureAndroid.PixM;
import one.empty3.library.Point3D;

import one.empty3.ImageIO;

import java.io.File;

public class SnakeFinderProcess extends ProcessFile {
    public SnakeFinderProcess() {

    }

    @Override
    public boolean process(File in, File out) {
        DipSnake snake = new DipSnake();
<<<<<<<< HEAD:feature0/snakes/SnakeFinderProcess.java
        try {
            PixM image = PixM.getPixM(one.empty3.ImageIO.read(in), maxRes);
            for (int i = 0; i < 6; i++) {
                snake.add(i, new Point3D(image.getColumns() / 2 + 0.6 * image.getColumns() / 2 * Math.cos(2 * 3.1416),
                        image.getLines() / 2 - 0.6 * image.getLines() / 2 * Math.sin(2 * 3.1416),
                        0.0));
            }

            snake.energyMinimization(image);

            //image.fillIn(snake, new ColorTexture(Color.WHITE), new ColorTexture(Color.WHITE));

            one.empty3.ImageIO.write(image.normalize(0, 1).getImage().getBitmap(), "jpg", out, shouldOverwrite);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
========
        PixM image = PixM.getPixM(ImageIO.read(in), maxRes);
        for (int i = 0; i < 6; i++) {
            snake.add(i, new Point3D(image.getColumns() / 2 + 0.6 * image.getColumns() / 2 * Math.cos(2 * 3.1416),
                    image.getLines() / 2 - 0.6 * image.getLines() / 2 * Math.sin(2 * 3.1416),
                    0.0));
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/snakes/SnakeFinderProcess.java
        }

        snake.energyMinimization(image);

        //image.fillIn(snake, new ColorTexture(Color.WHITE), new ColorTexture(Color.WHITE));

        ImageIO.write(image.normalize(0, 1).getImage().getBitmap(), "jpg", out, shouldOverwrite);
        return true;
    }

}
