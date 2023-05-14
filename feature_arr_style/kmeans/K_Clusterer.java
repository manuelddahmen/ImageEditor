package one.empty3.feature_arr_style.kmeans;
/*
 * Programmed by Shephalika Shekhar
 * Class for Kmeans Clustering implemetation
 */

import android.os.Build;

import androidx.annotation.RequiresApi;

import android.graphics.Color;

import java.io.IOException;
import java.util.*;

import one.empty3.feature_arr_style.PixM;
import one.empty3.library.Point3D;
import one.empty3.library.core.lighting.Colors;

import java.util.List;
import java.io.File;

import javaAnd.awt.image.imageio.ImageIO;

public class K_Clusterer extends ReadDataset {
    static int k = 4;

    public K_Clusterer() {
        super();
    }

    //main method
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void process(File in, File inCsv, File out, int res) throws IOException {

        PixM pix = null;
        try {
            if (res > 0)
                pix = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)).bitmap, res);
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pix = new PixM(Objects.requireNonNull(ImageIO.read(in)).bitmap);
            }
            PixM pix2 = new PixM(
                    pix.getColumns(),
                    pix.getLines()
            );

            String fileCsv = inCsv.getAbsolutePath();
            features.clear();
            read(inCsv); //load data


            Map<double[], Integer> clusters = new HashMap<>();
            Map<Integer, double[]> centroids = new HashMap<>();

            int ex = 0;

            k = 4;

            int r = 0;
            for (int i = 0; i < k; i++) {

                double[] x1 = features.get(r++);
                centroids.put(i, x1);

            }
            clusters = kmeans(1, centroids, k);
            int max_iterations = 100000;
            double db[] = new double[numberOfFeatures];

            for (int i = 0; i < max_iterations; i++) {
                //reassigning to new clusters
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
                clusters = kmeans(1, centroids, k);

            }
            double wcss = 0.0;
            for (int i = 0; i < k; i++) {
                double sse = 0;
                for (double[] key : clusters.keySet()) {
                    if (clusters.get(key) == i) {
                        sse += Math.pow(Distance.eucledianDistance(key, centroids.get(i)), 2);
                    }
                }
                wcss += sse;
            }
            int[] colors = new int[k];
            for (int i = 0; i < k; i++)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    colors[i] = Colors.random().toArgb();
                }
            clusters.forEach((doubles, integer) -> centroids.forEach((i, db1) -> {

                android.graphics.Color ic = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ic = Color.valueOf(colors[i]);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    pix2.setValues((int) (float) (db1[0]),
                            (int) (float) (db1[1]),
                            ic.red(), ic.green(), ic.blue());
                }


            }));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ImageIO.write(pix2.normalize(0.0, 1.0).getImage(), "jpg", out);
            }

            return;
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return;
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

}
