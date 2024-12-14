


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

package one.empty3.androidFeature.kmeans;
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
import matrix.PixM;
import one.empty3.library.core.lighting.Colors;
import one.empty3.libs.Color;

public class K_Clusterer /*extends ReadDataset*/ {
    public List<double[]> features;
    public final int numberOfFeatures = 5;
    private static int K = 3;
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
            features = new ArrayList<>();
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
            //System.out.println(db[0] + " " + db[1] + " " + db[2] + " " + db[3] + " " + db[4]);
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

            ReadDataset r1 = new ReadDataset();
            r1.read(inCsv);
            features = r1.features;
            //read(inCsv);
            int ex = 1;
            int k = K;
            int max_iterations = 200;
            int distance = 2;

            //Hashmap to store centroids with index
            Map<Integer, double[]> centroids = new HashMap<>();
            // calculating initial centroids
            double[] x1 = new double[numberOfFeatures];
            int r = 0;
            for (int i = 0; i < k; i++) {

                x1 = features.get(r++);
                centroids.put(i, x1);

            }
            //Hashmap for finding cluster indexes
            Map<double[], Integer> clusters = new HashMap<>();
            clusters = kmeans(distance, centroids, k);
            // initial cluster print
	/*	for (double[] key : clusters.keySet()) {
			for (int i = 0; i < key.length; i++) {
				System.out.print(key[i] + ", ");
			}
			System.out.print(clusters.get(key) + "\n");
		}
		*/
            double db[] = new double[numberOfFeatures];
            //reassigning to new clusters
            for (int i = 0; i < max_iterations; i++) {
                for (int j = 0; j < k; j++) {
                    List<double[]> list = new ArrayList<>();
                    for (double[] key : clusters.keySet()) {
                        if (clusters.get(key) == j) {
                            list.add(key);
//					for(int x=0;x<key.length;x++){
//						System.out.print(key[x]+", ");
//						}
//					System.out.println();
                        }
                    }
                    db = centroidCalculator(j, list);
                    centroids.put(j, db);

                }
                clusters.clear();
                clusters = kmeans(distance, centroids, k);

            }

            //final cluster print
            System.out.println("\nFinal Clustering of Data");
            System.out.println("Feature1\tFeature2\tFeature3\tFeature4\tCluster");
            for (double[] key : clusters.keySet()) {
                for (int i = 0; i < key.length; i++) {
                    System.out.print(key[i] + "\t \t");
                }
                //System.out.print(clusters.get(key) + "\n");
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
            System.out.println("\n*********Programmed by Shephalika Shekhar************\n*********Results************\nDistance Metric: " + dis);
            System.out.println("Iterations: " + max_iterations);
            System.out.println("Number of Clusters: " + k);
            System.out.println("WCSS: " + wcss);
            System.out.println("Press 1 if you want to continue else press 0 to exit..");
            ex = 0;
            Color[] colors = new Color[k];
            for (int i = 0; i < K; i++)
                colors[i] = Colors.random();
            clustersPrint = clusters;


            double[][] realValues = new double[k][6];


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

            pix2.normalize(0.0, 1.0).getImage().saveFile(out);
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
