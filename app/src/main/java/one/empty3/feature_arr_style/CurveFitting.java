//package one.empty3.feature_arr_style;
//
//import one.empty3.io.ProcessFile;
//import one.empty3.library.*;
//import one.empty3.library.core.nurbs.CourbeParametriquePolynomiale;
//import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
//import one.empty3.library.core.nurbs.ParametricCurve;
//
//import javaAnd.awt.image.imageio.ImageIO;//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//public class CurveFitting extends ProcessFile {
//    CourbeParametriquePolynomialeBezier curvePoints;
//    /***
//     *
//     * Morphing
//     * Mask
//     *     Paste
//     *
//     *     On donne une courbe initiale on essaie de la faire correspondre &agrave; une portion de l'image
//     *     Commencer par une courbe initiale comme un cercle
//     *     D&eacute;former en courbe de B&eacute;zier de longueur N = N points de contr&ocirc;le correspondant aux pixels
//     *     de la carte initiale
//     *     Carte des distances &agrave; la courbe
//     *     Vu dans Dr Lingrand, et R. Szeliski.
//     */
//
//    PixM pix = null;
//    private double lambda1 = 0.01, lambda2 = 0.01, lambda3 = 1.0;
//    double[] outAvg = new double[]{0, 0, 0}, inAvg = new double[]{0, 0, 0};
//    private double arcLength;
//    private double[][] distancesArr;
//    private CourbeParametriquePolynomialeBezier curveResult;
//    private CourbeParametriquePolynomialeBezier curveInitial;
//    private double[][] deltaE;
//    private HashMap<Integer, List<Integer>> border;
//    private PixM inPix;
//    private PixM outPix;
//
//    public void init() {
//        border = new HashMap<>();
//        deltaE = new double[pix.getColumns()][pix.getLines()];
//        lambda1 = 0.01;
//        lambda2 = 0.02;
//        lambda3 = 1.0;
//        distancesArr = new double[pix.getColumns()][pix.getLines()];
//        outAvg = new double[]{0., 0., 0.};
//        inAvg = new double[]{0., 0., 0.};
//        curveResult = new CourbeParametriquePolynomialeBezier();
//    }
//
//    public double E() {
//        double t1 = 0.0;
//        double t2 = 0.0;
//        double arcLengthPart = 0;
//        double e = 0.0;
//
//        inAvg = mean(true);
//        outAvg = mean(false);
//
//        for (int j = 0; j < pix.getColumns(); j++) {
//            for (int k = 0; k < pix.getLines(); k++) {
//                t1 += inPix.get(j, k) * lambda1 * (pix.luminance(j, k) - Math.sqrt(inAvg[0] * inAvg[0]
//                        + inAvg[1] * inAvg[1] + inAvg[2] * inAvg[2])) * (pix.luminance(j, k)
//                        - Math.sqrt(inAvg[0] * inAvg[0]
//                        + inAvg[1] * inAvg[1] + inAvg[2] * inAvg[2]));
//                t2 += outPix.get(j, k) * lambda2 * (pix.luminance(j, k)
//                        + Math.sqrt(outAvg[0] * outAvg[0] + outAvg[1] * outAvg[1] + outAvg[2] * outAvg[2]))
//                        * (pix.luminance(j, k) -
//                        Math.sqrt(outAvg[0] * outAvg[0] + outAvg[1] * outAvg[1] + outAvg[2] * outAvg[2]));
//
//
//            }
//        }
//        arcLength = 0.0;
//        for(int i=1; i<curvePoints.getCoefficients().getData1d().size()-1; i++)
//            arcLength += lambda3 * curvePoints.getCoefficients().getData1d().get(i+1)
//                    .moins(curvePoints.getCoefficients().getElem(i)).norme();
//
//        e = t1 - t2 + lambda3 * arcLength;
//        System.out.printf("\nE  terme intérieur   t1 %f\n", t1);
//        System.out.printf(  "t2 terme extérieur  t2 %f\n", t2);
//        System.out.printf(  "L  Longueur de la courbe  %f\n", arcLength);
//
//
//        return e;
//    }
//
//    public double[] mean(boolean in) {
//        double[] res = new double[]{0., 0., 0.};
//        for (int i = 0; i < pix.getCompNo(); i++) {
//            for (int j = 0; j < pix.getColumns(); j++) {
//                for (int k = 0; k < pix.getLines(); k++) {
//                    int isIn = in ? 1 : 0;
//
//                    pix.setCompNo(i);
//                    res[i] += pix.get(j, k) / Math.sqrt(3)
//                            * (in ? inPix.get(j, k) : (outPix.get(j, k)));
//
//                }
//            }
//        }
//        return res;
//    }
//
//    public double[][] distances() {
//
//        border = new HashMap<>();
//        int IN = 0, OUT = 0;
//        arcLength = 0.0;
//        double dist = 0.0;
//        double inEnergy, outEnergy;
//        arcLength = 0.0;
//        distancesArr = new double[pix.getColumns()][pix.getLines()];
//        for (int i = 0; i < distancesArr.length; i++) {
//            Arrays.fill(distancesArr[i], 100000);
//        }
//        //distancesArr[i][j] = 0.0;
//        for (int j = 0; j < pix.getLines(); j++) {
//            border.put(j, new ArrayList<>());
//            for (int i = 0; i < pix.getColumns(); i++) {
//                for (int k = 0; k < curvePoints.getCoefficients().getData1d().size(); k++) {
//                    // Si vous habitez dans une petite rue, ça n'ira pas tout seul. Je le redis une fois. NON. Autographe d'un mongol.
//                    // Dédié à Lucky Luke.
//
//                    dist = Point3D.distance(pix.getRgb(i, j), curvePoints.getCoefficients().getElem(k));
//                    if (dist < distancesArr[i][j])
//                        distancesArr[i][j] = dist;
//
//
//                    if (dist < 1.0) {
//
//                        // Point de la courbe
//
//                        border.get(j).add(i);
//                    }
//                }
//                if (inPix.luminance(i, j) > 0) {
//                    IN++;
//                } else if (outPix.luminance(i, j) > 0) {
//                    OUT++;
//                }
//
//            }
//        }
//        for (int i = 1; i < curvePoints.getCoefficients().getData1d().size(); i++) {
//            arcLength += curvePoints.getCoefficients().getData1d().get(i)
//                    .moins(curvePoints.getCoefficients().getData1d().get(i - 1)).norme();
//
//        }
//
//        System.out.printf("In   points count %d\n", IN);
//        System.out.printf("Out  points count %d\n", OUT);
//
//        return distancesArr;
//    }
//
//    /*
//
//            Générer une approximation avec points de contrôle.
//    */
//    public CourbeParametriquePolynomiale approx() {
//        List<Point3D> p = new ArrayList<>();
//        double incr = 0.1; // Garder l'incrément en chaque point? pour l'itération suivante
//        for (double t = 0.0; t < 1.0; t += incr) {
//            Point3D pt1 = curveInitial.calculerPoint3D(t);
//            Point3D pt2 = curveInitial.calculerPoint3D(t + incr);
//            double dist = Point3D.distance(pt1, pt2);
//            //if (dist >= 1.0 && dist < 2.0) {
//            if (dist >= 0.9 && dist < 1.1) {
//                p.add(Point3D.P.n((int) (double) pt2.get(0),
//                        (int) (double) pt2.get(1), (int) (double) pt2.get(2)));
//                arcLength = dist;
//            } else if (dist < 1.0) {
//                t = t - incr;
//                incr *= 1.5;
//            } else {
//                incr /= 1.5;
//                t = t - incr;
//            } // à détailler
//        }
//        Point3D[] point3DS = new Point3D[p.size()];
//        p.toArray(point3DS);
//        curvePoints = new CourbeParametriquePolynomialeBezier(point3DS);
//        return null;
//    }
//
//    @Override
//    public boolean process(File in, File out) {
//        try {
//            pix = new PixM(ImageIO.read(in));
//
//
//            init();
//
//
//            inPix = new PixM(pix.getImage());
//            outPix = new PixM(pix.getImage());
//
//            curveInitial = new CourbeParametriquePolynomialeBezier();
//
//
//            int imgX1;
//            int imgY1;
//            int imgX2;
//            int imgY2;
//            if (pix.getColumns() > pix.getLines()) {
//                imgX1 = pix.getColumns() / 2 - pix.getLines() / 2;
//                imgY1 = 0;
//                imgX2 = pix.getColumns() / 2 + pix.getLines() / 2;
//                imgY2 = pix.getLines() - 1;
//            } else {
//                imgX1 = 0;
//                imgY1 = (pix.getLines() / 2 - pix.getColumns() / 2);
//                imgX2 = pix.getColumns() - 1;
//                imgY2 = (pix.getLines() / 2 + pix.getColumns() / 2);
//            }
//
//            int N = 12;
//            for (int i = 0; i < N+3; i++) {
//                curveInitial.getCoefficients().getData1d().add(
//                        new Point3D(pix.getColumns() / 2
//                                + (imgX2 - imgX1) / 2. * Math.cos(i * 2 * Math.PI / N) / 3 * 2,
//                                pix.getLines() / 2
//                                        + (imgY2 - imgY1) / 2. * Math.sin(i * 2 * Math.PI / N) / 3 * 2,
//                                0.0));
//            }
//            //System.out.printf("%s", curveInitial.toString());
//
//            //curveInitial = new Circle(new Axe(Point3D.Z.plus(Point3D.n(pix.getColumns()/2,
//            //        pix.getLines()/2, 0)), Point3D.Z.mult(-1)), (imgX2-imgX1)/3.*2);
//
//            inPix.paintAll(new double[]{0, 0, 0});
//            //inPix.fillIn(curveInitial, Color.WHITE, Color.RED);
//            outPix.paintAll(new double[]{1, 1, 1});
//            //outPix.fillIn(curveInitial, Color.BLACK, Color.RED);
//
//            CourbeParametriquePolynomiale approx = approx();
//            double[][] distances = distances();
//            //inPix.paintAll(new double[]{0, 0, 0});
//            //outPix.paintAll(new double[]{1, 1, 1});
//            inPix.fillIn(curvePoints, new ColorTexture(Color.WHITE), new ColorTexture(Color.WHITE));
//            outPix.fillIn(curvePoints, new ColorTexture(Color.BLACK), new ColorTexture(Color.BLACK));
//
//            double e = E();
//            curveResult = modify();
//
//            PixM p = inPix;
//
//            p.plotCurve(curvePoints, new ColorTexture(Color.GREEN));
//
//
//            PixM normalize = p.normalize(0.0, 1.0);
//
//            // E = Sout + Sin + Scourbe :
//            // Convergence vers ?
//            // Changer l1,l2,l3
//            // Convergence de courbe . Modification de la courbe.
//            //normalize.plotCurve(courbeParametriquePolynomialeBezier, Color.GREEN);
//            //normalize.plotCurve(curvePoints, Color.BLUE);
//
//            //normalize.fillIn(courbeParametriquePolynomialeBezier, Color.BLACK, Color.WHITE);
//
//            System.out.printf("curveResult  %d\n", curveResult.getCoefficients().getData1d().size());
//            System.out.printf("curveInitial %d\n", ((CourbeParametriquePolynomialeBezier) curveInitial)
//                    .getCoefficients().getData1d().size());
//            System.out.printf("curvePoints  %d\n", curvePoints.getCoefficients().getData1d().size());
//            System.out.printf("Energy       %f\n", e);
//
//
//            String absolutePath = out.getAbsolutePath();
//            absolutePath = absolutePath.substring(0, absolutePath.length() - 4);
//            ImageIO.write(normalize.getImage(), "jpg", new File(absolutePath + ".jpg"));
//            ImageIO.write(outPix.normalize(0., 1.).getImage(), "jpg", new File(
//                    absolutePath + "-outPixels.jpg"));
//            ImageIO.write(inPix.normalize(0., 1.).getImage(), "jpg", new File(
//                    absolutePath + "-iinPixels.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    private CourbeParametriquePolynomialeBezier modify() {
//        return new CourbeParametriquePolynomialeBezier();
//    }
//}
