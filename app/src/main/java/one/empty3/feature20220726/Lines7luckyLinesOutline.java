package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;
import one.empty3.library.ColorTexture;
import one.empty3.library.LineSegment;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.core.lighting.Colors;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.*;
import javaAnd.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lines7luckyLinesOutline extends ProcessFile {


    ArrayList<Point3D> listTmpCurve = new ArrayList<>();
    ArrayList<Double> listTmpX = new ArrayList<>();
    ArrayList<Double> listTmpY = new ArrayList<>();
    ArrayList<Double> listTmpZ = new ArrayList<>();
    private PixM pixM;
    private double pz;
    private double py;
    private double px;
    private double distMax;
    private Random random = new Random();

    public Lines7luckyLinesOutline() {
    }

    public List<Point3D> relierPoints(List<List<Point3D>> points, Point3D p0) {
        List<Point3D> list = new ArrayList<>();

        List<Point3D> p = points.get(0);

        for (int i = 0; i < p.size(); i++) {
            Point3D proche = proche(p0, p);
            if (proche == null)
                return list;
            else {
                p.remove(proche);
                list.add(proche);
            }
        }

        return list;
    }

    private Point3D proche(Point3D point3D, List<Point3D> p) {
        double dist = distMax;
        Point3D pRes = null;
        for (Point3D p2 : p) {
            if (Point3D.distance(point3D, p2) < dist && p2 != point3D && !p2.equals(point3D)) {
                dist = Point3D.distance(point3D, p2);
                pRes = p2;
            }
        }
        return pRes;
    }

    public double r() {
        return (random.doubles().iterator().nextDouble() + 1.) / 2;
    }

    @Override
    public boolean process(File in, File out) {
        listTmpCurve = new ArrayList<>();
        listTmpX = new ArrayList<>();
        listTmpY = new ArrayList<>();
        listTmpZ = new ArrayList<>();
        try {
            pixM = null;
            pixM = new PixM(ImageIO.read(in));
            ArrayList<List<Point3D>> lists = new ArrayList<>();
            lists.add(new ArrayList<>());
            PixM o = new PixM(pixM.getColumns(), pixM.getLines());

            double valueDiff = 0.95;


            int[][] p = new int[pixM.getColumns()][pixM.getLines()];//!!
            for (double levels : Arrays.asList(1.0/*, 0.8, 0.6, 0.4, 0.3, 0.2/*, 0.1/*,0.0*/)) {

                pz = 0.0;
                py = 0.0;
                px = 0.0;
                distMax = (pixM.getColumns() + pixM.getLines()) / 2.;//???
                random = new Random();
                listTmpCurve = new ArrayList<Point3D>();
                listTmpX = new ArrayList<Double>();
                listTmpY = new ArrayList<Double>();
                listTmpZ = new ArrayList<Double>();


                for (int x = 0; x < pixM.getColumns(); x++)
                    for (int y = 0; y < pixM.getLines(); y++)
                        p[x][y] = 0;

                for (int i = 0; i < pixM.getColumns(); i++) {
                    for (int j = 0; j < pixM.getLines(); j++) {


                        int x = i;
                        int y = j;
                        if (!isInBound(new Point3D((double) x, (double) y, 0.0)))
                            continue;
                        double valueAvg = pixM.luminance(x, y);

                        if (p[x][y] == 0) {
                            listTmpCurve.add(new Point3D((double) x, (double) y, valueAvg));
                        } else {
                            continue;
                        }

                        int cont = 1;

                        while (valueAvg >= 1 / 256./*levels - valueDiff*/ && valueAvg <= levels + valueDiff && cont == 1 && p[x][y] == 0) {//2nd condition

                            p[x][y] = 1;


                            neighborhood((int) (double) x, (int) (double) y, valueAvg, valueDiff, levels);

                            while (listTmpX.size() > 0) {
                                getTmp(0);
                                x = (int) px;
                                y = (int) py;
                                removeTmp(0);
                                if (!isInBound(new Point3D(px, py, 0.0)))
                                    break;

                                if (p[x][y] == 0) {
                                    listTmpCurve.add(new Point3D((double) x, (double) y, levels));

                                    cont = 1;

                                    valueAvg = pixM.luminance(x, y);

                                } else cont = 0;
                            }

                        }

                        if (listTmpCurve.size() == 1)
                            lists.get(0).add(listTmpCurve.get(0));
                        else if (listTmpCurve.size() > 1 && !lists.contains(listTmpCurve)) {
                            lists.add(listTmpCurve);
                        }
                    }
                }
            }


            List<Point3D> lists2 = new ArrayList<>();

            for (int i = 0; i < lists.size(); i++) {
                for (int j = 0; j < lists.get(i).size(); j++) {
                    Point3D point3D = lists.get(i).get(j);
                    if (point3D != null)
                        lists2.add(point3D);
                }
            }

            List<LineSegment> lines = new ArrayList<>();
            List<List<Point3D>> list3 = new ArrayList<>();


            final double distMin = 1.3;

            boolean modified = true;
            System.out.println("List2(i).size" + lists2.size());
            for (int i = 0; i < lists2.size(); ) {
                boolean added = false;
                Point3D p1 = lists2.get(i);
                lists2.remove(i);
                //compare to list3 points
                for (int j = 0; j < list3.size() && !added; j++) {
                    for (int j1 = 0; j1 < list3.get(j).size() && !added; j1++) {
                        Point3D p2 = list3.get(j).get(j1);
                        if (Point3D.distance(p1, p2) <= distMin) {
                            list3.get(j).add(p1);
                            added = true;
                        }

                    }
                }
                if (!added) {
                    List<Point3D> currentList = new ArrayList<>();
                    currentList.add(p1);
                    list3.add(currentList);
                }
            }

            System.out.println("List3.size : " + list3.size());
            for (int j1 = 0; j1 < list3.size(); j1++) {
                //System.out.println("List3(i).size : " + list3.get(j1).size());
            }

            while (modified) {
                modified = false;
                for (int j1 = 0; j1 < list3.size(); j1++) {
                    for (int k1 = 0; j1 < list3.size() && k1 < list3.get(j1).size(); k1++) {
                        for (int j2 = 0; j2 < list3.size(); j2++) {
                            for (int k2 = 0; j2 < list3.size() && k2 < list3.get(j2).size(); k2++) {
                                if ((j1 < list3.size() && k1 < list3.get(j1).size() &&
                                        j2 < list3.size() && k2 < list3.get(j2).size())) {
                                    Point3D p1 = list3.get(j1).get(k1);
                                    Point3D p2 = list3.get(j2).get(k2);
                                    if (p1 != p2 && j1 != j2) {
                                        if (Point3D.distance(p1, p2) < distMin) {
                                            if (j1 < j2) {
                                                list3.get(j1).add(p2);
                                                list3.get(j2).remove(p2);
                                                if (list3.get(j2).size() == 0)
                                                    list3.remove(j2);
                                            } else {
                                                list3.get(j2).add(p1);
                                                list3.get(j1).remove(p1);
                                                if (list3.get(j1).size() == 0)
                                                    list3.remove(j1);
                                            }
                                            modified = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("List3.size : " + list3.size());
            for (int j1 = 0; j1 < list3.size(); j1++) {
                //System.out.println("List3(i).size : " + list3.get(j1).size());
            }

            PixM img3 = new PixM(pixM.getColumns(), pixM.getLines());

            list3.forEach(p3s -> {
                android.graphics.Color r = Color.color((float) 1.0, (float) r(), (float) 1.0);
                if (p3s.size() >= 2) {
                    for (int j = 0; j < p3s.size() - 1; j++) {
                        Point3D p1 = p3s.get(j);

                        img3.setValues((int) (double) p1.getX(), (int) (double) p1.getY(),
                                Lumiere.getDoubles(r.toArgb()));
                    }
                }
            });

            System.out.println("Lines : " + lines.size());


            double d = 0.0;
            for (int i = 0; i < list3.size(); i++) {
                List<Point3D> list1 = list3.get(i);
                Point3D l1, l2 = null;
                if (list1.size() >= 2) {
                    Point3D p1 = list1.get(0);
                    l1 = p1;
                    for (int l = 1; l < list1.size(); l++) {
                        Point3D p2 = list1.get(l);
                        if (Point3D.distance(l1, p2) > d) {
                            l2 = p2;
                        }
                    }
                    for (int l = 0; l < list1.size(); l++) {
                        Point3D p2 = list1.get(l);
                        if (Point3D.distance(l1, p2) > d) {
                            l2 = p2;
                            d = Point3D.distance(l1, p2);
                        }
                    }

                    if (l1 != l2 && l1 != null && l2 != null)
                        lines.add(new LineSegment(l1, l2, new ColorTexture(Color.BLUE)));
                }
            }


            BufferedImage bLines = new javaAnd.awt.image.BufferedImage(o.getColumns(), o.getLines(), javaAnd.awt.image.BufferedImage.TYPE_INT_RGB);
            for (LineSegment line : lines) {
                android.graphics.Color ab = Color.random();
                if (line.getLength() >= 2) {
                    Point3D pDraw1 = line.getOrigine().plus(
                            line.getOrigine().plus(line.getExtremite().moins(line.getOrigine().mult(0.0))));
                    Point3D pDraw2 = line.getOrigine().plus(
                            line.getOrigine().plus(line.getExtremite().moins(line.getOrigine().mult(1.0))));
                    double[] doubles = Lumiere.getDoubles(line.texture().getColorAt(0.5, 0.5));
                    int x1 = (int) (double) pDraw1.getX();
                    int y1 = (int) (double) pDraw1.getY();
                    int x2 = (int) (double) pDraw2.getX();
                    int y2 = (int) (double) pDraw2.getY();
                    if (isInBound(pDraw1) && isInBound(pDraw2)) {
                        bLines.drawLine(x1, y1, x2, y2, ab.toArgb());
                        for (double a = 0; a < 1.0; a += 1. / (distMax)) {
                            Point3D p2 = pDraw1.plus(pDraw2.moins(pDraw1).mult(a));
                            int x = (int) (double) p2.get(0);
                            int y = (int) (double) p2.get(1);
                            img3.setValues(x, y, doubles);
                        }
                    }
                }
            }

            ImageIO.write(img3.normalize(0, 1).getImage(), "jpg", out);

            return true;
        } catch (
                IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean isInBound(Point3D p1) {
        return p1.get(0) >= 0 && p1.get(0) < pixM.getColumns() && p1.get(1) >= 0 && p1.get(1) < pixM.getLines();
    }

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

    private void neighborhood(int i, int j, double valueAvg, double valueDiff, double valueMin) {
        listTmpX.clear();
        listTmpY.clear();
        listTmpZ.clear();
        listTmpCurve.clear();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int x2 = i + (x - 1);
                int y2 = j + (y - 1);
                if (x2 != i && y2 != j) {
                    Point point = new Point(x2, y2);
                    px = point.getX();
                    py = point.getY();
                    pz = pixM.luminance((int) point.getX(), (int) point.getY());
                    if (pz >= valueAvg - valueDiff && pz <= valueAvg + valueDiff && pz > valueMin
                            && px >= 0.0 && px < pixM.getColumns() && py >= 0 && py < pixM.getLines()) {
                        addTmp(px, py, pz);
                        break;
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
