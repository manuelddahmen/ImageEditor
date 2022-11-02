package one.empty3.feature20220726;

import android.graphics.Color;

import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature20220726.kmeans.MakeDataset;
import one.empty3.feature20220726.kmeans.ReadDataset;
import one.empty3.io.ProcessFile;
import one.empty3.library.core.lighting.Colors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DBScan extends ProcessFile {

    public List<DataPoint> points;
    private List<Cluster> clusters;

    public double max_distance;
    public int min_points;

    public boolean[] visited;
    private int minPoints;

    public DBScan() {
        init(3, 3);
    }

    public void init(double max_distance, int min_points) {
        this.points = new ArrayList<DataPoint>();
        this.clusters = new ArrayList<Cluster>();
        this.max_distance = max_distance;
        this.min_points = min_points;
    }

    public void cluster() {
        Iterator<DataPoint> it = points.iterator();
        int n = 0;

        while (it.hasNext()) {

            if (!visited[n]) {
                DataPoint d = it.next();
                visited[n] = true;
                List<Integer> neighbors = getNeighbors(d);

                if (neighbors.size() >= min_points) {
                    Cluster c = new Cluster(clusters.size());
                    buildCluster(d, c, neighbors);
                    clusters.add(c);
                }
            }
        }
    }

    private void buildCluster(DataPoint d, Cluster c, List<Integer> neighbors) {
        c.addPoint(d);

        for (int i = 0; i < neighbors.size(); i++) {
            Integer point = neighbors.get(i);
            DataPoint p = points.get(point);
            if (!visited[point]) {
                visited[point] = true;
                List<Integer> newNeighbors = getNeighbors(p);
                if (newNeighbors.size() >= min_points) {
                    neighbors.addAll(newNeighbors);
                }
            }
            if (p.getCluster() == -1) {
                c.addPoint(p);
            }
        }
    }

    private synchronized List<Integer> getNeighbors(DataPoint d) {
        List<Integer> neighbors = new ArrayList<Integer>();
        int i = 0;
        for (DataPoint point : points) {
            double distance = d.distance(point);

            if (distance >= max_distance) {
                neighbors.add(i);
            }
            i++;
        }

        return neighbors;
    }

    public void setPoints(List<DataPoint> points) {
        this.points = points;
        this.visited = new boolean[points.size()];
    }

    @Override
    public boolean process(File in, File out) {
        try {
            String s = in.getAbsoluteFile() + ".csv";
            MakeDataset makeDataset = new MakeDataset(in, new File(s), maxRes);

            PixM p = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)), maxRes);
            init(maxRes, minPoints);
            ReadDataset readDataset = new ReadDataset();
            readDataset.read(new File(s));
            points = list(readDataset.getFeatures());
            setPoints(points);

            cluster();

            for (Cluster cluster : clusters) {
                Color colorCluster = (Color) Colors.random();
                for (int i = 0; i < cluster.getPoints().size(); i++) {
                    DataPoint centroid = cluster.getPoints().get(i);
                    p.setValues(centroid.getX(), centroid.getY(), colorCluster.red(),
                            colorCluster.green(), colorCluster.blue());
                }
            }
            ImageIO.write(p.getImage(), "jpg", out);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private List<DataPoint> list(List<double[]> features) {
        for (double[] d : features) {
            points.add(new DbsPoint(d));
        }
        return points;
    }

    public List<DataPoint> getPoints() {
        return points;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public double getMax_distance() {
        return max_distance;
    }

    public void setMax_distance(double max_distance) {
        this.max_distance = max_distance;
    }

    public int getMin_points() {
        return min_points;
    }

    public void setMin_points(int min_points) {
        this.min_points = min_points;
    }

    public boolean[] getVisited() {
        return visited;
    }

    public void setVisited(boolean[] visited) {
        this.visited = visited;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }
}