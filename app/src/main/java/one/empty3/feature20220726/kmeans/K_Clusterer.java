package one.empty3.feature20220726.kmeans;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javaAnd.awt.Color;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature20220726.PixM;
import one.empty3.feature20220726.kmeans.ReadDataset;
import one.empty3.library.core.lighting.Colors;

public class K_Clusterer /*extends ReadDataset*/ {

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

    private static final int K = 20;
    static int k = 20;
    protected Map<double[], Integer> clustersPrint;
    protected Map<double[], Integer> clusters;
    public Map<Integer, double[]> centroids;

    public K_Clusterer() {
        super();
    }


    //main method
    public void process(File in, File inCsv, File out, int res) throws IOException {


        final PixM pix;
        try {
            if (res > 0)
                pix = PixM.getPixM(ImageIO.read(in), res);
            else
                pix = new PixM(ImageIO.read(in));
            PixM pix2 = new PixM(
                    pix.getColumns(),
                    pix.getLines()
            );

            String fileCsv = inCsv.getAbsolutePath();
            features.clear();
            read(inCsv); //load data


            ReadDataset r1 = new ReadDataset();
            r1.features.clear();
            System.out.println("Enter the filename with path");
            r1.read(inCsv); //load data
            int ex = 1;
            clusters = new HashMap<>();
            centroids = new HashMap<>();
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

                    x1 = r1.features.get(r++);
                    centroids.put(i, x1);

                }
                //Hashmap for finding cluster indexes
                clusters = kmeans(distance, centroids, k);
                // initial cluster print
	/*	for (double[] key : clusters.keySet()) {
			for (int i = 0; i < key.length; i++) {
				System.out.print(key[i] + ", ");
			}
			System.out.print(clusters.get(key) + "\n");
		}
		*/
                double[] db = new double[numberOfFeatures];
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
                ////System.out.println("\nFinal Clustering of Data");
                ////System.out.println("Feature1\tFeature2\tFeature3\tFeature4\tCluster");
                for (double[] key : clusters.keySet()) {
                    for (int i = 0; i < key.length; i++) {
                        //System.out.print(key[i] + "\t \t");
                    }
                    ////System.out.print(clusters.get(key) + "\n");
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
                ex = 0;//sc.nextInt();
            } while (ex == 1);

            android.graphics.Color[] colors = new Color[k];
            for (int i = 0; i < k; i++)
                colors[i] = Color.random();
            clustersPrint = clusters;

            centroids.forEach((integer1, db1) -> clustersPrint.forEach((doubles, integer2) -> {
                //System.out.println("Mean k=" + integer2 + " count=" + clustersPrint.get(integer2));
//                pix2.setValues((int) (float) (db1[0]), (int) (float) (db1[1]),
//                        colors[integer2].red(), colors[integer2].green(),
//                        colors[integer2].blue());
                pix2.setValues((int) (float) (doubles[0]), (int) (float) (doubles[1]),
                        colors[integer2].red(), colors[integer2].green(),
                        colors[integer2].blue());

            }));

            ImageIO.write(pix2.normalize(0.0, 1.0).getImage(), "jpg", out);

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

}
