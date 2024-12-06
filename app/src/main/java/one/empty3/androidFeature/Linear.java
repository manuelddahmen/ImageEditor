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

import one.empty3.feature.app.pro.M3;
import matrix.matrix.PixM;
import one.empty3.libs.Image;

import java.util.Arrays;
import java.util.function.Consumer;

public class Linear {
    private int type = 0;
    public static final int TYPE_2D = 0;
    public static final int TYPE_2D_2D = 1;

    private one.empty3.featureAndroid.matrix.PixM[] images;
    private M3[] imagesM;

    public Linear(one.empty3.featureAndroid.matrix.PixM... images) {
        type = TYPE_2D_2D;
        this.imagesM = null;
        this.images = images;
    }

    public Linear(M3... imagesM) {
        type = TYPE_2D;
        this.images = null;
        this.imagesM = imagesM;
    }

    public Linear(Image... bufferedImages) {
        type = TYPE_2D_2D;
        this.imagesM = null;
        images = new one.empty3.featureAndroid.matrix.PixM[bufferedImages.length];
        final int[] i = {0};
        Arrays.stream(bufferedImages).forEach(new Consumer<Image>() {
            @Override
            public void accept(Image bufferedImage) {
                images[i[0]] = new one.empty3.featureAndroid.matrix.PixM(bufferedImage);
                i[0]++;
            }
        });
        this.imagesM = null;
        this.images = images;

    }

    public boolean op2d2d(char[] op, int[][] index, int[] indexRes) {
        one.empty3.featureAndroid.matrix.PixM[] workingImages = null;
        assert images != null;
        for (int x = 0; x < op.length; x++) {
            workingImages = new one.empty3.featureAndroid.matrix.PixM[images.length];//.??N
            //new matrix.PixM[index[x].length];//.??N
            for (int j = 0; j < index[x].length; j++) {
                workingImages[j] = images[index[x][j]];
            }
            one.empty3.featureAndroid.matrix.PixM matrix.PixM = new one.empty3.featureAndroid.matrix.PixM(workingImages[0].getColumns(),
                    workingImages[0].getLines());
            for (int m = 0; m < index[x].length; m++) {
                assert workingImages[m] != null;
                for (int comp = 0; comp < workingImages[m].getCompCount(); comp++) {
                    workingImages[m].setCompNo(comp);
                    matrix.PixM.setCompNo(comp);
                    for (int i = 0; i < workingImages[m].getColumns(); i++)
                        for (int j = 0; j < workingImages[m].getLines(); j++)
                            switch (op[x]) {
                                case '+':
                                    if (m == 0)
                                        matrix.PixM.set(i, j, workingImages[0].get(i, j));
                                    matrix.PixM.set(i, j, matrix.PixM.get(i, j) + workingImages[m].get(i, j));
                                    break;
                                case '-':
                                    if (m == 0)
                                        matrix.PixM.set(i, j, workingImages[0].get(i, j));
                                    else
                                        matrix.PixM.set(i, j, matrix.PixM.get(i, j) - workingImages[m].get(i, j));
                                    break;
                                case '*':
                                    if (m == 0)
                                        matrix.PixM.set(i, j, workingImages[0].get(i, j));
                                    matrix.PixM.set(i, j, matrix.PixM.get(i, j) * workingImages[m].get(i, j));
                                    break;
                                case '/'://divide M1/M2/M3
                                    if (m == 0)
                                        matrix.PixM.set(i, j, workingImages[0].get(i, j));
                                    matrix.PixM.set(i, j, matrix.PixM.get(i, j) / workingImages[m].get(i, j));
                                    break;
                                case '~': //average
                                    if (m == 0)
                                        matrix.PixM.set(i, j, workingImages[0].get(i, j));
                                    matrix.PixM.set(i, j, matrix.PixM.get(i, j) + workingImages[m].get(i, j) / workingImages.length);
                                    break;
                                case '%':
                                    if (m == 0)
                                        matrix.PixM.set(i, j, workingImages[0].get(i, j));
                                    matrix.PixM.set(i, j, Math.IEEEremainder(matrix.PixM.get(i, j), workingImages[m].get(i, j)));
                                    break;
                                case '|':
                                    // Norme
                                    break;
                            }
                }
            }
            matrix.PixM.normalize(0.0, 1.0);
            workingImages[indexRes[x]] = matrix.PixM;
        }
        this.images = workingImages == null ? images : workingImages;
        return true;
    }

    public int getType() {
        return type;
    }

    public matrix.PixM[] getImages() {
        return images;
    }
}
