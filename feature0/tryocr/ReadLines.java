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

package one.empty3.feature.tryocr;

import one.empty3.ImageIO;
import matrix.PixM;
import one.empty3.io.ProcessFile;

import java.io.File;
import java.io.IOException;

public class ReadLines extends ProcessFile {


    @Override
    public boolean process(File in, File out) {
        PixM pixM = new PixM(one.empty3.ImageIO.read(in));

        PixM pixM2 = new PixM(pixM.getColumns(), pixM.getLines());

        // Block of texts (aligned horizontally)
        int maxSize = 30;
        double countEmpty[][][] = new double[maxSize][pixM.getColumns()][pixM.getLines()];
        double countNotEmpty[][][] = new double[maxSize][pixM.getColumns()][pixM.getLines()];
        double[] valueBlack = new double[]{1, 1, 1};
        double[] valueWhite = new double[]{0, 0, 0};
        for (int i = 0; i < pixM.getColumns(); i++)
            for (int j = 0; j < pixM.getLines(); j++) {
                int size = 0;
                do {
                    size++;
                    for (int x = i - maxSize; x >= 0 && x < pixM.getColumns() && x < i + maxSize; x++)
                        for (int y = i - maxSize; y >= 0 && y < pixM.getLines() && y < j + maxSize; y++) {
                            double[] values = pixM.getValues(x, y);

                            if (valueWhite.equals(values)) {
                                countNotEmpty[size][i][j]++;
                            } else if (valueBlack.equals(values)) {
                                countEmpty[size][i][j]++;
                            }
                        }
                } while (countNotEmpty[size][i][j] <= size * 4);
            }
        // STEP1 Separate lines, then characters
        /*
         * pour chaque point i, j
         * parcourir en 2 dim ( dans quelles directions exactement ?(1) )
         * jusqu'à max(taille image /  10?, espace vide)
         * si espace vide est la condition de fin=> accepter i, j, w, h comme candidat caractère.
         * ligne : déterminer les candidats lignes par  hough adapté sur échantillon de candidats. i, j, w, h.?? ne pas confondre
         * orientation du texte : des ensembles candidats conditions de fin sont sur des  lignes parallèles. les mêmes et les autres les autres sont aux frontières des caractères.
         * (1)  orientation du texte détecter des lignes noires entourées de points blancs ??
         * vote pour :
         *     (a, b) tels que y=ax+b ligne noire
         *     (a=a0, b) tels que y=ax+b2 ligne avec des blanches
         *     (a, b) tels que séparation de caractères - alternance avec ou sans présence de blanches. + perpendiculaire-b/a
         * masse blancs des 2 côtés au-dessus et au-dessous. caractères séparables par des points noirs, perpendiculaires.
         */

        PixM pixOut1 = new PixM(pixM.getColumns(), pixM.getLines());
        // STEP2 Lines = lines of 0 and 1 and 0 for height
        File out2 = new File(out.getAbsolutePath() + "-2.jpg");
        for (int i = 0; i < pixM.getColumns(); i++)
            for (int j = 0; j < pixM.getLines(); j++) {
                double sum = 0;
                for (int s = 0; s < maxSize; s++)
                    if (countNotEmpty[s][i][j] / maxSize > sum)
                        sum = (countNotEmpty[s][i][j] / maxSize);
                pixOut1.setValues(i, j, sum, sum, sum);
            }
        try {
            one.empty3.ImageIO.write(pixOut1.getImage().getBitmap(), "jpg", out2, shouldOverwrite);
            one.empty3.ImageIO.write(pixM2.getImage().getBitmap(), "jpg", out, shouldOverwrite);
            return true;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
