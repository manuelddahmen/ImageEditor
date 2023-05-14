//package one.empty3.feature_arr_style;
//
//import one.empty3.feature_arr_style.selection.Paste;
//import one.empty3.io.ProcessFile;
//import one.empty3.library.ColorTexture;
//import one.empty3.library.LineSegment;
//import one.empty3.library.Point3D;
//import one.empty3.library.core.nurbs.F;
//import one.empty3.library.core.nurbs.Point3DS;
//
//import javaAnd.awt.image.imageio.ImageIO;//
//import android.graphics.Bitmap;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
///***
// * Segmentation
// */
//public class Lines6 extends ProcessFile {
//
//
//    ArrayList<Point3D> listTmpCurve = new ArrayList<>();
//    ArrayList<Double> listTmpX = new ArrayList<>();
//    ArrayList<Double> listTmpY = new ArrayList<>();
//    ArrayList<Double> listTmpZ = new ArrayList<>();
//    private PixM pixM;
//    private double pz;
//    private double py;
//    private double px;
//    //    private double distMax;
//    private Random random = new Random();
//
//    /*
//        public List<Point3D> relierPoints(List<List<Point3D>> points, Point3D p0) {
//            List<Point3D> list = new ArrayList<>();
//
//            List<Point3D> p = points.get(0);
//
//            for (int i = 0; i < p.size(); i++) {
//                Point3D proche = proche(p0, p);
//                if (proche == null)
//                    return list;
//                else {
//                    p.remove(proche);
//                    list.add(proche);
//                }
//            }
//
//            return list;
//        }
//    *//*
//    private Point3D proche(Point3D point3D, List<Point3D> p) {
//        double dist = distMax;
//        Point3D pRes = null;
//        for (Point3D p2 : p) {
//            if (Point3D.distance(point3D, p2) < dist && p2 != point3D && !p2.equals(point3D)) {
//                dist = Point3D.distance(point3D, p2);
//                pRes = p2;
//            }
//        }
//        return pRes;
//    }
//*/
//    public double r() {
//        return (random.doubles().iterator().nextDouble() + 1.) / 2;
//    }
//
//    @Override
//    public boolean process(File in, File out) {
//        try {
//            pixM = null;
//            pixM = new PixM(ImageIO.read(in));
//            ArrayList<List<Point3D>> lists = new ArrayList<>();
//            PixM o = new PixM(pixM.getColumns(), pixM.getLines());
//
//            double valueDiff = 0.2;
//
//            int[][] p = new int[pixM.getColumns()][pixM.getLines()];//!!
//            listTmpCurve = new ArrayList<Point3D>();
////[] x, y-> pCount, subListRef.
//            double distMax;
//            for (double levels : Arrays.asList(1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4/*, 0.3 ,x0.2,0.1,0.0*/)) {
//
//                pz = 0.0;
//                py = 0.0;
//                px = 0.0;
//                distMax = (pixM.getColumns() + pixM.getLines()) >> 1;//???
//                random = new Random();
//
//                for (int x = 0; x < pixM.getColumns(); x++)
//                    for (int y = 0; y < pixM.getLines(); y++)
//                        p[x][y] = 0;
//
//                for (int i = 0; i < pixM.getColumns(); i++) {
//                    for (int j = 0; j < pixM.getLines(); j++) {
//// remove long complicated uncertain loop//
//
//                        int x = i;
//                        int y = j;
//                        if (!isInBound(new Point3D((double) x, (double) y, 0.0)))
//                            continue;
//                        double valueAvg = pixM.norme(x, y);
//
//                        if (valueAvg >= levels - valueDiff && valueAvg <= levels + valueDiff && p[x][y] == 0) {//2nd condition
//
//                            p[x][y] = 1;
//                            listTmpCurve.add(new Point3D((double) x, (double) y, valueAvg));
//
//                        }
//
//
//                    }
//
//                }
//            }
//            lists.add(listTmpCurve);
//            ArrayList<Point3D> list2 = new ArrayList<Point3D>();
//
//
//            for (List<Point3D> point3DS : lists) {
//                list2.addAll(point3DS);
//            }
//
//            List<LineSegment> lines = new ArrayList<>();
//            List<List<Point3D>> list3 = new ArrayList<>();
//
//            for (int i = 0; i < list2.size(); i++) {
//                Point3D point3D = list2.get(i);
//                final double distNormal = 1.1;//0.9??
//                list3.add(new ArrayList<>());
//                list3.get(list3.size() - 1).add(point3D);
//                distMax = (pixM.getColumns() + pixM.getLines()) >> 1;
//
//                if (isInBound(point3D)) {
//                    for (int j = 0; j < list2.size(); j++) {
//                        Point3D current = list2.get(j);
//                        Point3D prev = list3.get(list3.size() - 1).get(
//                                list3.get(list3.size() - 1).size() - 1);
//
//                        if (prev != current && current != point3D &&
//                                Point3D.distance(prev, current) <= distNormal &&
//                                !(Point3D.distance(point3D, current) > distMax)) {
//                            list3.get(list3.size() - 1).add(current);
//                            //distMax = Point3D.distance(point3D, current);
//                            p[(int) (double) current.getX()][(int) (double) current.getY()]++;
//                        }
//
//                    }
//
//
//                    if (list3.get(list3.size() - 1).size() < 2) {
//                        list3.remove(list3.size() - 1);
//
//                    } else {
//                        for (Point3D d : list3.get(list3.size() - 1)) {
//                            list2.remove(d);
//
//                        }
//                        i = 0;
//                    }
//                    // supprimer points en doubles
//                }
//
//            }
//            // d'après pcount x, y et curve xy supprimer les courbes en trop.
//            Bitmap bLines = Bitmap.createBitmap(o.getColumns(), o.getLines(), Bitmap.TYPE_INT_RGB);
//            Graphics g = bLines.getGraphics();
//
//            g.setColor(Color.RED);
//            list3.forEach(point3DS -> {
//                Point3D p1 = point3DS.get(0);
//                Point3D p2 = point3DS.get(point3DS.size() - 1);
//                if (p1 != p2) {
//                    g.drawLine((int) (double) p1.getX(),
//                            (int) (double) p1.getY(),
//                            (int) (double) p2.getX(),
//                            (int) (double) p2.getY());
//                }
//            });
//
//            double longueur = 0.0;
//            // Prendre un pourcentage de lignes les plus longues et les dessiner en blanc
//            for (List<Point3D> point3DS : list3) {
//                longueur += size(point3DS);
//
//            }
//            longueur /= list3.size();
//
//
//            double finalLongueur = longueur;
//            int i = 0;
//            List<Point3D> temp1 = new ArrayList<Point3D>();
//            List<Point3D> temp2 = new ArrayList<Point3D>();
//            g.setColor(Color.GREEN);
//            boolean temp1b = false;
//            boolean temp2b = false;
//            List<Point3D> listTemp1 = null;
//            List<Point3D> listTemp2 = null;
//
//            for (List<Point3D> points : list3) {
//                if (size(points) > finalLongueur * 2.0 + 2) {
//                    g.setColor(Color.valueOf(0, (int) (((double) (i)) * 255 / list3.size()), 0));
//                    Point3D p1 = points.get(0);
//                    Point3D p2 = points.get(points.size() - 1);
//                    if (p1 != p2) {
//                        g.setColor(Color.RED);
//                        g.drawLine((int) (double) p1.getX(),
//                                (int) (double) p1.getY(),
//                                (int) (double) p2.getX(),
//                                (int) (double) p2.getY());
//                    }
//                    /*
//                    for (int j = 0; j < points.size() - 1; j++) {
//                        p1 = points.get(j);
//                        p2 = points.get(j + 1);
//                        if (p1 != p2)
//                            g.drawLine((int) (double) p1.getX(),
//                                    (int) (double) p1.getY(),
//                                    (int) (double) p2.getX(),
//                                    (int) (double) p2.getY());
//                    }*/
//                    // Il faut au moins deux contours (les contours du visage) et des yeux.
//                    if (temp1.size() == 0) {
//                        temp1b = true;
//                        temp1.add(p1);
//                        listTemp1 = points;
//                    } else if (temp1b) {
//                        Point3D d1 = distanceCurvePoint(temp1, p1);
//                        double distance = Point3D.distance(d1, p1);
//                        if (listTemp1 == points || distance < pixM.getColumns() / 5.) {
//                            temp1.add(p1);
//                        } else {
//                            temp2b = true;
//                            temp1b = false;
//                            listTemp2 = points;
//                        }
//                    }
//                    if (temp2b) {
//                        Point3D d2 = distanceCurvePoint(temp2, p1);
//                        double distance = Point3D.distance(d2, p1);
//                        if (listTemp2 == points) {
//                            temp2.add(p1);
//                        } else if (distance > pixM.getColumns() / 5.) {
//                            temp2b = false;
//
//                        }
//                    }
//                    i++;
//                }
//            }
//
//            g.setColor(Color.BLUE);
//            temp1.forEach(point3D -> {
//                        System.out.printf("POINT LIST TEMP1 %s", point3D);
//                        g.drawLine((int) (double) point3D.getX() - 2, (int) (double) point3D.getY() - 2,
//                                (int) (double) point3D.getX() + 2, (int) (double) point3D.getY() + 2);
//                    }
//            );
//            temp2.forEach(point3D -> System.out.printf("POINT LIST TEMP2 %s", point3D));
//
//
//            g.setColor(Color.RED);
//
//
//            Paste paste = new Paste();
//
//            PixM stack = new PixM(
//                    pixM.getColumns(), pixM.getLines()
//                    //ImageIO.read(getStackItem(1))
//            );
//
//            list3.forEach(point3DS -> {
//                paste.pasteList(point3DS,
//                        stack, new ColorTexture(Color.WHITE));
//            });
//
//            ImageIO.write(stack.getImage(), "jpg", out);
//
//            images.add(out);
//
//            System.out.printf("IMAGES 0 : %s\n", images.get(0));
//
//            return true;
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    private double size(List<Point3D> point3DS) {
//        return Point3D.distance(point3DS.get(0), point3DS.get(point3DS.size() - 1));
//    }
//
//    private boolean isInBound(Point3D p1) {
//        return p1.get(0) >= 0 && p1.get(0) < pixM.getColumns() && p1.get(1) >= 0 && p1.get(1) < pixM.getLines();
//    }
//
//    public void addTmp(double x, double y, double z) {
//        listTmpX.add(x);
//        listTmpY.add(y);
//        listTmpZ.add(z);
//    }
//
//    public void removeTmp(int i) {
//        listTmpX.remove(i);
//        listTmpY.remove(i);
//        listTmpZ.remove(i);
//    }
//
//    public void getTmp(int i) {
//        px = listTmpX.get(i);
//        py = listTmpY.get(i);
//        pz = listTmpZ.get(i);
//    }
//
//    private void neighborhood(int i, int j, double valueAvg, double valueDiff, double valueMin) {
//        listTmpX.clear();
//        listTmpY.clear();
//        listTmpZ.clear();
//        listTmpCurve.clear();
//        for (int x = 0; x < 3; x++) {
//            for (int y = 0; y < 3; y++) {
//                int x2 = i + (x - 1);
//                int y2 = j + (y - 1);
//                if (x2 != i && y2 != j) {
//                    Point point = new Point(x2, y2);
//                    px = point.getX();
//                    py = point.getY();
//                    pz = pixM.norme((int) point.getX(), (int) point.getY());
//                    if (pz >= valueAvg - valueDiff && pz <= valueAvg + valueDiff && pz > valueMin
//                            && px >= 0.0 && px < pixM.getColumns() && py >= 0 && py < pixM.getLines()) {
//                        addTmp(px, py, pz);
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    public Point3D distanceCurvePoint(List<Point3D> curve, Point3D point) {
//        if (point != null && curve.size() > 0) {
//            double dist = Point3D.distance(point, curve.get(0));
//            int p = 0;
//            for (int i = 0; i < curve.size(); i++) {
//                Double distance = Point3D.distance(curve.get(i), point);
//                if (distance < dist) {
//                    dist = distance;
//                    p = i;
//                }
//            }
//            return curve.get(p);
//        } else
//            return Point3D.INFINI;
//    }
//}
