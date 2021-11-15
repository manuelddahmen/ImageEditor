package test3;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.core.testing.*;
import one.empty3.library.*;
import one.empty3.library.core.lighting.*;

import one.empty3.library.core.nurbs.*;

import one.empty3.feature.app.replace.java.awt.image.*;
import java.util.*;

/***
 *
 * proximity. bezier _curve_ ie.
 *
 */
public class TestVoronoi extends TestObjetSub {
    public final int pointsSize = 20;
    private final CourbeParametriquePolynomialeBezier[] curves = new CourbeParametriquePolynomialeBezier[pointsSize];
    // private Double [][][] distancesSum;
    //private ArrayList<Point3D> pointsList;
    private Double maxDist;
    private final int nPointsDist = 1;
    ///private double[][][] pointDist;
    // private int[][][] pointNo;
    private int[][] colorsArr;
    // private ColorDist [][][] cds;


    Random random = new Random();
    private double distRes;

    protected void addRand(CourbeParametriquePolynomialeBezier c) {
        c.getCoefficients().add(new Point3D(random.nextDouble() * getResx(),
                random.nextDouble() * getResy(),
                0.0));

    }

    private final Color[] colors = new Color[pointsSize];
    ColorDist[] cds = new ColorDist[pointsSize];

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ginit() {
        distRes = Math.max(getResx(), getResy());
        // mettre les distances dans l ordre
        // regarder a 1 ou 2 pixels près
        // non


        //pointDist = new double[getResx()][getResy()][pointsSize];

        //  distancesSum = new Double [getResx()][getResy()][pointsSize];
        //  pointNo = new int[getResx()][getResy()][pointsSize];
        //  cds = new ColorDist[getResx()][getResy()][pointsSize];
        for (int i = 0; i < pointsSize; i++) {
            //pointsList.add(new Point3D(Math.random()*getResx(),
            //                        Math.random()*getResy(),
            //                        0.0));
            curves[i] = new CourbeParametriquePolynomialeBezier();
            curves[i].getCoefficients().data1d.clear();
            for (int p = 0; p < 4; p++) {
                addRand(curves[i]);
            }
            colors[i] = Colors.random();
            curves[i].declareProperties();
            cds[i] = new ColorDist();
            cds[i].color = colors[i];

        }
        colorsArr = new int[getResx()][getResy()];
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void finit() {
        for (int i = 0; i < getResx(); i++)
            for (int j = 0; j < getResy(); j++) {
                Point3D p = new Point3D((double) i, (double) j, 0.0);
                for (int k = 0; k < pointsSize; k++) {
                    double d = Point3D.distance(p, curves[k].calculerPoint3D(frame() / 20.5 / 4.0));
                    cds[k].dist = Math.min(d, distRes);
                    cds[k].color = colors[k];
                }
                ColorDist.sort(cds);
                android.graphics.Color z = Colors.mean(cds, 1.0, nPointsDist);
                colorsArr[i][j] = z.toArgb();
            }
    }


    public void afterRenderFrame() {
        Bitmap image = img();
        for (int i = 0; i < getResx(); i++)
            for (int j = 0; j < getResy(); j++) {
                //int c = ((int)(double) distancesSum[i][j][])*256+ 255<<24;
                image.setPixel(i, j, colorsArr[i][j]);
            }
    }
}
