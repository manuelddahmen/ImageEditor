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
/*
package one.empty3.androidFeature.kmeans;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Map;
import java.util.Objects;


import matrix.PixM;
import one.empty3.library.core.lighting.Colors;
import one.empty3.libs.Color;
import one.empty3.libs.Image;

public class K_Clusterer  {

    public List<double[]> features = new ArrayList<>();
    public final int numberOfFeatures = 5;
    private boolean random;

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

    private static final int K = 3;
    static int k = 3;
    protected Map<double[], Integer> clustersPrint;
    protected Map<double[], Integer> clusters;
    public Map<Integer, double[]> centroids;

    public K_Clusterer() {
    }


    //main method
    public void process(File in, File inCsv, File out, int res) throws IOException {
        features = new ArrayList<>();

        matrix.PixM pix;
        try {
            if (res > 0)
                pix = PixM.getPixM(Objects.requireNonNull(Image.loadFile(in)), res);
            else
                pix = new PixM(Objects.requireNonNull(Image.loadFile(in)));
            matrix.PixM pix2 = new PixM(
                    pix.getColumns(),
                    pix.getLines()
            );

            String fileCsv = inCsv.getAbsolutePath();
            features.clear();
            read(inCsv); //load data


            //ReadDataset r1 = new ReadDataset();
            //Logger.getAnonymousLogger().log(Level.INFO, "Enter the filename with path");
            //r1.read(inCsv); //load data
            int ex = 1;
            clusters = new HashMap<>();
            centroids = new HashMap<>();

            //features = r1.features;

            do {
                int k = K;
                //Scanner sc = new Scanner(System.in);
                int max_iterations = 100;//sc.nextInt();
                int distance = 1;//sc.nextInt();
                //Hashmap to store centroids with index
                // calculating initial centroids
                double[] x1 = new double[numberOfFeatures];
                int r = 0;
                for (int i = 0; i < k; i++) {

                    x1 = features.get(r++);
                    centroids.put(i, x1);

                }
                //Hashmap for finding cluster indexes
                clusters = kmeans(distance, centroids, k);
                // initial cluster print
                double[] db = new double[numberOfFeatures];
                //reassigning to new clusters
                for (int i = 0; i < max_iterations; i++) {
                    for (int j = 0; j < k; j++) {
                        List<double[]> list = new ArrayList<>();
                        for (double[] key : clusters.keySet()) {
                            if (clusters.get(key) == j) {
                                list.add(key);
                            }
                        }
                        db = centroidCalculator(j, list);
                        centroids.put(j, db);

                    }
                    clusters.clear();
                    clusters = kmeans(distance, centroids, k);

                }
                for (double[] key : clusters.keySet()) {
                    for (int i = 0; i < key.length; i++) {
                        //System.out.print(key[i] + "\t \t");
                    }
                }

                //Calculate WCSS
                double wcss = 0;

                for (int i = 0; i < k; i++) {
                    double sse = 0;
                    for (double[] key : clusters.keySet()) {
                        if (clusters.get(key) == i) {
                            sse += Math.pow(Distance.eucledianDistance(key, centroids.get(i)), 2);
                        }
                    }
                    wcss += sse;
                }
                String dis = "";
                if (distance == 1)
                    dis = "Euclidean";
                else
                    dis = "Manhattan";
                ex = 0;
            } while (ex == 1);

            clustersPrint = clusters;

            Color[] colors = new Color[k];
            if (random) {
                for (int i = 0; i < k; i++)
                    colors[i] = Colors.random();
            } else {
                int[] count = new int[k];
                double[][] sums = new double[k][numberOfFeatures];
                centroids.forEach((integer1, db1) -> {
                    if(clusters!=null && !clusters.isEmpty()) {
                        clustersPrint.forEach((doubles, integer2) -> {
                            if (Objects.equals(integer1, integer2)) {
                                for (int i1 = 2; i1 < numberOfFeatures; i1++) {
                                    sums[integer2][i1] += doubles[i1];
                                    count[integer2]++;
                                }
                            }
                        });
                        for (int i1 = 2; i1 < numberOfFeatures; i1++) {
                            sums[integer1][i1] = sums[integer1][i1] / count[i1];
                            colors[integer1] = Color.newCol(sums[integer1][i1], sums[integer1][i1], sums[integer1][i1]);
                        }
                    }
                });
            }
            clustersPrint = clusters;

            centroids.forEach((integer1, db1) ->
                    clustersPrint.forEach((doubles, integer2) -> {
                        pix2.setValues((int) (float) (doubles[0]), (int) (float) (doubles[1]),
                                colors[integer2].getRed() / 255f, colors[integer2].getGreen() / 255f,
                                colors[integer2].getBlue() / 255f);

                    }));

            pix2.normalize(0.0, 1.0).getImage().saveToFile(out.getAbsolutePath());

        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
    }

    //method to calculate centroids
    public double[] centroidCalculator(int id, List<double[]> a) {

        int count = 0;
        //double x[] = new double[ReadDataset.numberOfFeatures];
        double sum = 0.0;
        double[] centroids = new double[numberOfFeatures];
        for (int i = 0; i < numberOfFeatures; i++) {
            sum = 0.0;
            count = 0;
            for (double[] x : a) {
                count++;
                sum = sum + x[i];
            }
            centroids[i] = sum / count;
        }
        return centroids;
    }

    //method for putting features to clusters and reassignment of clusters.
    public Map<double[], Integer> kmeans(int distance, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        int k1 = 0;
        double dist = 0.0;
        for (double[] x : features) {
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {
                if (distance == 1) {
                    dist = Distance.eucledianDistance(centroids.get(j), x);
                } else if (distance == 2) {
                    dist = Distance.manhattanDistance(centroids.get(j), x);
                }
                if (dist < minimum) {
                    minimum = dist;
                    k1 = j;
                }

            }
            clusters.put(x, k1);
        }

        return clusters;
    }

    public void setRandom(boolean b) {
        this.random = b;
    }
}
*/