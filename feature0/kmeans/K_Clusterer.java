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

<<<<<<<< HEAD:feature0/kmeans/K_Clusterer.java
package one.empty3.feature.kmeans;
========
package one.empty3.androidFeature.kmeans;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/kmeans/K_Clusterer.java
/*
 * Programmed by Shephalika Shekhar
 * Class for Kmeans Clustering implemetation
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import one.empty3.ImageIO;
<<<<<<<< HEAD:feature0/kmeans/K_Clusterer.java
import one.empty3.feature.PixM;
========
import one.empty3.featureAndroid.PixM;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/kmeans/K_Clusterer.java
import one.empty3.library.core.lighting.Colors;

public class K_Clusterer /*extends ReadDataset*/ {
    public List<double[]> features;
    public final int numberOfFeatures = 5;
    private static final int K = 20;
    protected Map<double[], Integer> clustersPrint;
    protected Map<double[], Integer> clusters;
    public Map<Integer, double[]> centroids;
    private boolean random = true;

    public K_Clusterer() {
    }

    public List<double[]> getFeatures() {
        return features;
    }

    public void read(File s) throws NumberFormatException, IOException {

        try {
            BufferedReader readFile = new BufferedReader(new FileReader(s));
            int j = 0;
            String line;
            while ((line = readFile.readLine()) != null) {

                String[] split = line.split(" ");
                double[] feature = new double[5];
                int i;

                assert split.length == 5;

                for (i = 0; i < split.length; i++)
                    feature[i] = Double.parseDouble(split[i]);

                features.add(feature);

                j++;
            }
            readFile.close();


            System.out.println("MakeDataset csv out size: " + j + " " + features.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    void display() {
        for (double[] db : features) {
            System.out.println(db[0] + " " + db[1] + " " + db[2] + " " + db[3] + " " + db[4]);
        }
    }


    //main method
    public void process(File in, File inCsv, File out, int res) throws IOException {
        features = new ArrayList<>();

        PixM pix;
        try {
            pix = PixM.getPixM(Objects.requireNonNull(one.empty3.ImageIO.read(in)), res);
            PixM pix2 = new PixM(pix.getColumns(), pix.getLines());

            System.out.println("size out : " + pix2.getColumns() + ", " + pix2.getLines());

            String fileCsv = inCsv.getAbsolutePath();
            features.clear();
            read(inCsv); //load data


            //display();


            //ReadDataset r1 = new ReadDataset();
            //System.out.println("Enter the filename with path");
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
                    int r1 = (int) (Math.random() * features.size());
                    r1 = (r1 >= features.size() ? features.size() - 1 : r1);

                    x1 = features.get(r1);
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
                            if (Objects.equals(clusters.get(key), j)) {
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
                        if (Objects.equals(clusters.get(key), i)) {
                            sse += Math.pow(Distance.eucledianDistance(key, centroids.get(i)), 2);
                        }
                    }
                    wcss += sse;
                }
                String dis = "";
                dis = "Euclidean";
                ex++;
            } while (ex < 1);

            android.graphics.Color[] colors = new android.graphics.Color[K];
            for (int i = 0; i < K; i++)
                colors[i] = Colors.random();
            clustersPrint = clusters;


            double[][] realValues = new double[K][6];


            centroids.forEach((i, doubles) -> {
                clustersPrint.forEach((d1, i2) -> {

                    for (int j = 2; j < 5; j++) {
                        pix.setCompNo(j - 2);
                        realValues[i2][j] += pix.get((int) (float) (d1[0]), (int) (float) (d1[1]));
                    }
                    realValues[i2][5]++;
                });
            });


            for (int i2 = 0; i2 < K; i2++) {
                for (int j = 2; j < 5; j++) {
                    realValues[i2][j] /= realValues[i2][5];
                }
            }
            centroids.forEach((i, doubles) -> {
                clustersPrint.forEach((d1, i2) -> {
                    if (random) {
                        pix2.setValues((int) (float) (d1[0]), (int) (float) (d1[1]),
                                colors[i2].red(), colors[i2].green(), colors[i2].blue());
                    } else {
                        pix2.setValues((int) (float) (d1[0]), (int) (float) (d1[1]),
                                realValues[i2][2], realValues[i2][3], realValues[i2][4]);
                    }
                });
            });

<<<<<<<< HEAD:feature0/kmeans/K_Clusterer.java
            one.empty3.ImageIO.write(pix2.normalize(0.0, 1.0).getImage().getBitmap(), "jpg", out);//.getImage().getBitmap()
========
            ImageIO.write(pix2.normalize(0.0, 1.0).getImage().getBitmap(), "jpg", out);//.getImage().getBitmap()
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/kmeans/K_Clusterer.java

        } catch (Exception ex1) {
            System.err.println(ex1.getMessage());
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
