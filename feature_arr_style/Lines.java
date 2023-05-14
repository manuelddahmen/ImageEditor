package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import javaAnd.awt.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Lines extends ProcessFile {
    private PixM pixM;
    private double pz;
    private double py;
    private double px;
    private double distMax = 40.;
    private final Random random = new Random();
    Point3D[][] mapPoints;
    private final List<List<Point3D>> lists = new ArrayList<>();
    private int[][] p;

    public List<List<Point3D>> relierPointsMap() {
        List<List<Point3D>> list = new ArrayList<>();
        List<Point3D> l = new ArrayList<>();
        List<Point3D> currentList = new ArrayList<>();

        for (List<Point3D> p : lists) {
            for (int i = 0; i < p.size(); i++) {
                Point3D p0 = p.get(i);
                for (List<Point3D> p2 : lists) {
                    Point3D proche = near(p0, mapPoints);
                    if (proche == null) {
                        p.remove(i);
                        p2.remove(proche);
                        l = new ArrayList<>();
                        list.add(l);
                        currentList = l;
                    } else {
                        p.remove(i);
                        p2.remove(proche);
                        currentList.add(proche);
                    }
                }
            }
        }
        return list;
    }

    public List<List<Point3D>> relierPointsList() {
        List<List<Point3D>> list2 = new ArrayList<>();

        List<Point3D> currentList = new ArrayList<>();
        list2.add(currentList);

        for (List<Point3D> p : lists) {
            for (int i = 0; i < p.size(); i++) {
                Point3D p0 = p.get(i);
                for (List<Point3D> p2 : lists) {
                    Point3D proche = near(p0, p2);
                    if (proche == null) {
                        p.remove(i);
                        List<Point3D> l = new ArrayList<>();
                        list2.add(l);
                        currentList = l;
                        list2.add(currentList);
                    } else {
                        p.remove(i);
                        currentList.add(proche);
                    }
                }
            }
        }
        return list2;
    }

    private Point3D near(Point3D p0, List<Point3D> p) {
        double distMax1 = 2;
        double dist = distMax;
        Point3D pRes = null;
        for (Point3D p2 : p) {
            if (Point3D.distance(p0, p2) < distMax1 && p2 != p0 && !p2.equals(p0)) {
                dist = Point3D.distance(p0, p2);
                pRes = p2;
                if (dist < 2.0)
                    return pRes;
            }
        }
        return pRes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Point3D near(Point3D p0, Point3D[][] map) {
        int distMax1 = 2;
        double dist = distMax;
        Point3D pRes = null;
        int x0 = (int) (double) p0.getX();
        int y0 = (int) (double) p0.getY();
        for (int i = Math.max(x0 - distMax1 / 2, 0); i < Math.min(x0 + distMax1 / 2, pixM.getColumns()); i++) {
            for (int j = Math.max(y0 - distMax1 / 2, 0); j < Math.min(y0 + distMax1 / 2, pixM.getLines()); j++) {
                Point3D p2 = mapPoints[i][j];
                if (p2 != null && Point3D.distance(p0, p2) < distMax1 && p2 != p0 && !p2.equals(p0)) {
                    dist = Point3D.distance(p0, p2);
                    pRes = p2;
                    map[i][j] = null;

                }
            }
        }
        if (dist <= distMax1) {
            mapPoints[(int) (double) pRes.getX()][(int) (double) pRes.getY()] = null;
        }
        return pRes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public double r() {
        return (random.doubles().iterator().nextDouble() + 1.) / 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean process(File in, File out) {
        lists.add(new ArrayList<>());
        listTmpCurve = new ArrayList<Point3D>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pixM = new PixM(Objects.requireNonNull(ImageIO.read(in)).bitmap);
        }
        PixM o = new PixM(pixM.getColumns(), pixM.getLines());

        p = new int[pixM.getColumns()][pixM.getLines()];
        mapPoints = new Point3D[pixM.getColumns()][pixM.getLines()];

        for (int x = 0; x < pixM.getColumns(); x++)
            for (int y = 0; y < pixM.getLines(); y++)
                p[x][y] = 0;

        for (int i = 0; i < pixM.getColumns(); i++) {
            for (int j = 0; j < pixM.getLines(); j++) {
                double valueMin = 0.4;

                double valueDiff = 0.1;


                int x = i;
                int y = j;

                double valueAvg = pixM.luminance(x, y);

                while (valueAvg >= valueMin) {

                    neighborhood((int) (double) x, (int) (double) y, valueAvg, valueDiff, valueMin);
                    x = (int) px;
                    y = (int) py;

                    Point3D p0 = null;
                    if (listTmpCurve.size() > 0) {
                        getTmp(0);
                        if (!(x >= 0 && x < pixM.getColumns() && y >= 0 && y < pixM.getLines()) && p[x][y] == 0) {

                            p0 = new Point3D(px, px, pz);
                            listTmpCurve.add(p0);
                            lists.get(0).add(p0);
                        }
                    } else
                        break;

                    p[x][y] = 1;

                    valueAvg = pixM.luminance(x, y);

                }

                if (listTmpCurve.size() == 1) {
                    lists.get(0).add(listTmpCurve.get(0));
                } else if (listTmpCurve.size() > 1) {
                    lists.add(listTmpCurve);
                }


/*
                for (List<Point3D> ps : lists)
                    for (Point3D p0 : ps)
                        for (int c = 0; c < listTmpCurve.size(); c++)
                            if (listTmpCurve.get(c).equals(p0)) {
                                listTmpCurve.remove(c);
                            }
*/

            }
        }


        List<List<Point3D>> lists2 = new ArrayList<>();

        lists2 = relierPointsList();

        lists2.forEach(p3s -> {
            Color r = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                r = Color.valueOf((float) r(), (float) r(), (float) r());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Color finalR = r;
                p3s.forEach(point3D -> o.setValues((int) (double) (point3D.getX()), (int) (double) (point3D.getY()),
                        finalR.red() / 255., finalR.green() / 255., finalR.blue() / 255.));
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ImageIO.write(o.normalize(0.0, 1.0).getImage(), "jpg", out);
        }
        return true;
    }

    ArrayList<Point3D> listTmpCurve = new ArrayList<Point3D>();
    ArrayList<Double> listTmpX = new ArrayList<Double>();
    ArrayList<Double> listTmpY = new ArrayList<Double>();
    ArrayList<Double> listTmpZ = new ArrayList<Double>();

    public void addTmp(double x, double y, double z) {
        listTmpX.add(x);
        listTmpY.add(y);
        listTmpZ.add(z);
    }

    public void removeTmp(int i) {
        listTmpX.remove(i);
        listTmpY.remove(i);
        listTmpZ.remove(i);
    }

    public void getTmp(int i) {
        px = listTmpX.get(i);
        py = listTmpY.get(i);
        pz = listTmpZ.get(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void neighborhood(int i, int j, double valueAvg, double valueDiff, double valueMin) {
        listTmpX.clear();
        listTmpY.clear();
        listTmpZ.clear();
        listTmpCurve.clear();
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                int x2 = i + (x - 1);
                int y2 = j + (y - 1);
                if (x2 != i && y2 != j) {
                    Point point = new Point(x2, y2);
                    double px = point.getX();
                    double py = point.getY();
                    double pz = pixM.luminance((int) point.getX(), (int) point.getY());
                    if (pz >= valueAvg - valueDiff && pz <= valueAvg + valueDiff && pz > valueMin && p[x2][y2] == 0) {
                        addTmp(px, py, pz);
                        return;
                    }
                }
            }
        }
    }

    public double getDistMax() {
        return distMax;
    }

    public void setDistMax(double distMax) {
        this.distMax = distMax;
    }

}
