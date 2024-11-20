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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import one.empty3.library.core.lighting.Colors;
import one.empty3.featureAndroid.PixM;
import one.empty3.libs.Color;
import one.empty3.libs.Image;
import one.empty3.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;

public class Classification extends ProcessFile {
    Random random = new Random();
    private one.empty3.libs.Image imageOut;
    private int SIZE = 5;
    private double ratio = 0.3;
    private double threshold = 0.3;


    @Override
    public boolean process(File in, final File out) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("effectClassification-", ".jpg");
            Lines7luckyLinesOutline lines7luckyLinesOutline = new Lines7luckyLinesOutline();
            lines7luckyLinesOutline.process(in, tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //!!! Border effect
        in = tempFile;
        if (in == null) {
            try {
                throw new FileNotFoundException("Temporary file in $tempFile");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!in.getName().endsWith(".jpg"))
            return false;
        PixM selectPointColorMassAglo = null;
        Image read = null;
        read = new Image(Objects.requireNonNull(ImageIO.read(in)).getImage());
        selectPointColorMassAglo = PixM.getPixM(read, maxRes);
        imageOut = one.empty3.ImageIO.read(in);
        SelectPointColorMassAglo selectPointColorMassAglo1 = new SelectPointColorMassAglo(read);
        int color = Color.WHITE;
        for (int i = 0; i < imageOut.getWidth(); i += 1)
            for (int j = 0; j < imageOut.getHeight(); j += 1) {
                selectPointColorMassAglo1.setTmpColor(Colors.random());
                double v = selectPointColorMassAglo1.averageCountMeanOf(i, j, SIZE, SIZE, threshold);
                if (v > ratio) {
                    imageOut.setRgb(i, j, color);/*selectPointColorMassAglo.getChosenColor().getRGB()*/
                } else {
                    double[] doubles = Lumiere.getDoubles(read.getRgb(i, j));
                    /*for(int c=0; c<3; c++)
                        doubles[c] = doubles[c]/3;
*/
                    imageOut.setRgb(i, j, Lumiere.getInt(doubles));
                }
            }

        ImageIO.write(imageOut, "jpg", out, false);
        return true;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
