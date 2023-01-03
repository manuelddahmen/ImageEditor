/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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

package one.empty3.feature20220726.kmeans;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadDataset {

    public List<double[]> features = new ArrayList<>();
    public final int numberOfFeatures = 5;

    public List<double[]> getFeatures() {
        return features;
    }

    public void read(File s) throws NumberFormatException, IOException {

        File file = s;

        try {
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String line;
            while ((line = readFile.readLine()) != null) {

                String[] split = line.split(" ");
                double[] feature = new double[5];
                int i = 0;

                for (i = 0; i < split.length; i++)
                    feature[i] = Double.parseDouble(split[i]);

                features.add(feature);

            }
            readFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    void display() {
        Iterator<double[]> itr = features.iterator();
        while (itr.hasNext()) {
            double db[] = itr.next();
            for (int i = 0; i < db.length; i++) {
                System.out.print(db[i] + " ");
            }

        }

    }
}
