package one.empty3.feature;

import one.empty3.feature.FilterPixM;
import one.empty3.feature.M;
import one.empty3.feature.V;
import one.empty3.library.ITexture;
import one.empty3.library.LineSegment;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

import java.awt.*;

import java.util.Arrays;

public class PixM extends M {
    public static final int COMP_RED = 0;
    public static final int COMP_GREEN = 1;
    public static final int COMP_BLUE = 2;
    public static final int COMP_ALPHA = 3;
    public static final int COMP_INTENSITY = 4;
    private int MAX_DISTANCE_ITERATIONS = 100;

    public PixM(int l, int c) {
        super(l, c);
    }

    public PixM(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        float[] colorComponents = new float[getCompCount()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                colorComponents =  one.empty3.feature.app.replace.java.awt.Color.Color(rgb).getColorComponents(colorComponents);
                for (int com = 0; com < getCompCount(); com++) {
                    setCompNo(com);
                    set(i, j, colorComponents[com]);
                }
            }
        }
    }

    public PixM(double[][] distances) {
        super(distances.length, distances[0].length);
        for (int i = 0; i < getColumns(); i++)
            for (int j = 0; j < getLines(); j++)
                set(i, j, distances[i][j]);
    }

    public Point3D getRgb(int i, int j) {
        setCompNo(0);
        double dr = get(i, j);
        setCompNo(1);
        double dg = get(i, j);
        setCompNo(2);
        double db = get(i, j);
        return new Point3D(dr, dg, db);
    }

    public static PixM getPixM(BufferedImage image, double maxRes) {
        double f = 1.0;
        if (maxRes < image.getWidth() && maxRes < image.getHeight())
            f = 1.0 / Math.max(image.getWidth(), image.getHeight()) * maxRes;

        double columns2 = 1.0 * image.getWidth() * f;
        double lines2 = 1.0 * image.getHeight() * f;
        System.out.println("pixm resampling init  --> (" + maxRes + ", " + maxRes + ")  (" + columns2 + ", " + lines2 + ")");
        PixM pixM = new PixM((int) (columns2), ((int) lines2));


        for (int i = 0; i < (int) columns2; i++) {
            for (int j = 0; j < (int) lines2; j++) {


                int rgb = image.getRGB(
                        (int) (1.0 * i / columns2 * image.getWidth())


                        , (int) (1.0 * j / lines2 * image.getHeight()));
                float[] colorComponents = new float[pixM.getCompCount()];
                colorComponents =  one.empty3.feature.app.replace.java.awt.Color.Color(rgb).getColorComponents(colorComponents);
                for (int com = 0; com < pixM.getCompCount(); com++) {
                    pixM.setCompNo(com);
                    pixM.set(i, j, colorComponents[com]);

                    //double m = mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                    //        (int) (cli2 * div));
                    //pixM.set(i, j, );
                }
            }

        }
        return pixM;


    }

    public PixM applyFilter(FilterPixM filter) {
        PixM c = new PixM(columns, lines);
        double sum;
        for (int comp = 0; comp < getCompCount(); comp++) {

            setCompNo(comp);
            c.setCompNo(comp);
            filter.setCompNo(comp);


            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    c.set(i, j, 0.0); //???
                    sum = 0.0;
                    for (int u = -filter.columns / 2; u <= filter.lines / 2; u++) {
                        for (int v = -filter.lines / 2; v <= filter.lines / 2; v++) {
     
                        
                        /*V derivative = derivative(i, j, 2, null);
                        double v1 = derivative.get(0, 0);
                        double v2 = derivative.get(1, 0);
                        c.set(i, j,(v1+v2)
                                * filter.filterUVvalue(u, v, u*v));*/
                            double filterUVvalue = filter.get(u + filter.columns / 2,
                                    v + filter.lines / 2);
                            double vAtUv = get(i + u, j + v);
                            if (!(vAtUv == noValue)) {

                                c.set(i, j, c.get(i, j) + filterUVvalue * vAtUv);
                                sum += filterUVvalue;

                            }


                        }
                    }
                    c.set(i, j, c.get(i, j) / sum);
                }
            }
        }
        return c;
    }

    public one.empty3.feature.V derivative(int x, int y, int order, one.empty3.feature.V originValue) {
        if (originValue == null) {
            originValue = new V(2, 1);
            originValue.set(0, 0, get(x, y));
            originValue.set(1, 0, get(x, y));
        }
        originValue.set(0, 0, -get(x + 1, y) + 2 * get(x, y) - get(x - 1, y));
        originValue.set(1, 0, -get(x, y + 1) + 2 * get(x, y) - get(x, y - 1));
        if (order > 0) {
            derivative(x, y, order - 1, originValue);
        }

        return originValue;
    }

    public BufferedImage getImage() {

        float[] f = new float[getCompCount()];

        BufferedImage image = BufferedImage.BufferedImage(columns,
                lines,
Bitmap.Config.RGB565);


        float[] rgba = new float[getCompCount()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                for (int comp = 0; comp < 3; comp++) {
                    setCompNo(comp);
                    float value = (float) (get(i, j));
                    value = Math.max(value, 0f);
                    value = Math.min(value, 1f);

                    rgba[comp] = value;
                }
                image.setPixel(i, j,  one.empty3.feature.app.replace.java.awt.Color.Color(rgba[0], rgba[1], rgba[2]).getRGB());
            }
        }
        return image;

    }


    public void plotCurve(ParametricCurve curve, ITexture texture) {
        double INCR_T = 0.001;

        float[] rgba = new float[getCompCount()];
        for (double t = 0; t < 1.0; t += INCR_T) {
            rgba =  one.empty3.feature.app.replace.java.awt.Color.Color(curve.texture().getColorAt(t, 0.)).getColorComponents(rgba);
            Point3D p = curve.calculerPoint3D(t);
            Point3D p1 = curve.calculerPoint3D(t + INCR_T);
            for (double t1 = t; t1 < t + INCR_T; t1 += 1 / Point3D.distance(p, p1)) {
                Point3D pp = p.plus(p1.moins(p).mult(t1));
                for (int c = 0; c < 3; c++) {
                    setCompNo(c);
                    set((int) (double) pp.getX(), (int) (double) pp.getY(), rgba[c]);
                }
            }
        }
    }

/*

    public void plotCurve(ParametricCurve curve, Color color, int x, int y) {

        float[] rgba = new float[getCompCount()];
        color.getColorComponents(rgba);
        for (double t = 0; t < 1.0; t += 0.001) {
            for (int c = 0; c < 3; c++) {
                Point3D p = curve.calculerPoint3D(t);
                setCompNo(c);
                set((int) (double) p.getX(), (int) (double) p.getY(), rgba[c]);
            }
        }
    }
*/

    public void fillIn(ParametricCurve curve, ITexture texture, ITexture borderColor) {
        int[] columnIn = new int[getLines()];
        int[] columnOut = new int[getLines()];
        for (int i = 0; i < getLines(); i++) {
            columnIn[i] = -1;
            columnOut[i] = -1;
        }
        float[] rgba = new float[getCompCount()];
        float[] rgbaBorder = new float[getCompCount()];


        Point3D old = Point3D.O0;
        double incrRef = (1.0 / getColumns());
        Point3D p0 = curve.calculerPoint3D(curve.getStartU());
        double deltaT = incrRef;
        for (double t = curve.getStartU() - (double) (curve.getIncrU().getElem()); t < curve.getEndU() + (double) curve.getIncrU().getElem();
             t += incrRef) {

            Point3D p = curve.calculerPoint3D(t);
/*
            double incrRef0 = incrRef;
            do {
                p0 = p;

                deltaT = p.moins(p0).norme();


                if (deltaT > 1.5) {
                    incrRef /= 1.5;
                }
                if (deltaT < 0.1) {
                    incrRef *= 2;
                }

                p = curve.calculerPoint3D(t);
            } while (p.moins(p0).norme() > 1.5 || p.moins(p0).norme() < 0.1);

            t = t - incrRef0 + incrRef;
*/
            int x = (int) (double) p.get(0);
            int y = (int) (double) p.get(1);
            int xOld = (int) (double) old.get(0);
            int yOld = (int) (double) old.get(1);


            if (y >= columnIn.length || y < 0)
                continue;

            int abs = Math.abs(columnIn[y] - x);

            if (x >= 0 && x < getColumns() && y >= 0 && y < getLines()
                    && abs > 2 && (columnIn[y] == -1 || columnOut[y] == -1)) {


                if (columnIn[y] == -1) {
                    columnIn[y] = x;
                } else if (columnOut[y] == -1 || columnOut[y] != x) {// ADDED columnOut[y] != x
                    if (columnIn[y] > x) {
                        columnOut[y] = columnIn[y];
                        columnIn[y] = x;
                    } else {
                        columnOut[y] = x;
                    }
                }
            }
            old = p;
            p0 = curve.calculerPoint3D(curve.getStartU());
        }

/*
        for (int i = 0; i < columnIn.length; i++) {
            System.out.printf("LinesIn [ i %d ] = %d\n", i, columnIn[i]);
            System.out.printf("LinesOut[ i %d ] = %d\n", i, columnOut[i]);
        }
*/


        for (int y = 0; y < columnIn.length; y++) {
            if (columnIn[y] != -1 && columnOut[y] != -1) {
                plotCurve(new LineSegment(Point3D.n(columnIn[y], y, 0), Point3D.n(columnOut[y], y, 0)),
                        texture);
            }
            if (columnIn[y] != -1)
                setValues(columnIn[y], y, Lumiere.getDoubles(
                        borderColor.getColorAt(1.0 * columnIn[y] / getColumns(), 1.0 * y / getLines())));
            if (columnOut[y] != -1)
                setValues(columnOut[y], y, Lumiere.getDoubles(
                        borderColor.getColorAt(1.0 * columnIn[y] / getColumns(), 1.0 * y / getLines())));


        }


    }

    public PixM normalize(final double min, final double max) {

        double[] maxRgbai = new double[compCount];
        double[] meanRgbai = new double[compCount];
        double[] minRgbai = new double[compCount];
        double minA = 0.0;
        double maxA = 1.0;
        if (min != -1 || max != -1) {
            minA = min;
            maxA = max;
        }
        for (int i = 0; i < getCompCount(); i++) {
            maxRgbai[i] = maxA;
            minRgbai[i] = minA;
            meanRgbai[i] = 0.0;
        }
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);
                    double valueAt = get(i, j);
                    if (!Double.isNaN(valueAt) || !Double.isInfinite(valueAt)) {
                        if (valueAt > maxRgbai[comp]) {
                            maxRgbai[comp] = valueAt;
                        }
                        if (valueAt < minRgbai[comp]) {
                            minRgbai[comp] = valueAt;
                        }
                    } else {
                        valueAt = 0.0;
                        set(i, j, valueAt);
                    }
                    meanRgbai[comp] += valueAt / (lines * columns);
                }
            }
        }
        PixM image = new PixM(columns, lines);
        for (int i = 0; i < image.columns; i++) {
            for (int j = 0; j < image.lines; j++) {
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);
                    image.setCompNo(compNo);
                    float value;
                    value = (float) ((get(i, j) - minRgbai[comp]) / (maxRgbai[comp] - minRgbai[comp]));
                    //value = Math.max(value, 0f);
                    //value = Math.min(value, 1f);
                    //if (comp == 3) value = 1f;

                    image.set(i, j, value);
                }
            }
        }
        return image;
    }

    public PixM normalize(final double inMin, final double inMax, final double min, final double max) {

        double[] maxRgbai = new double[compCount];
        double[] meanRgbai = new double[compCount];
        double[] minRgbai = new double[compCount];
        double minA = 0.0;
        double maxA = 1.0;
        if (min != -1 || max != -1) {
            minA = min;
            maxA = max;
        }
        for (int i = 0; i < getCompCount(); i++) {
            maxRgbai[i] = inMax;
            minRgbai[i] = inMin;
            meanRgbai[i] = (inMax + inMin) / 2;
        }
        PixM image = new PixM(columns, lines);
        for (int i = 0; i < image.columns; i++) {
            for (int j = 0; j < image.lines; j++) {
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);
                    image.setCompNo(compNo);
                    float value;
                    value = (float) ((get(i, j) - minRgbai[comp]) / (maxRgbai[comp] - minRgbai[comp]));
                    image.set(i, j, value);
                }
            }
        }
        return image;
    }

    public PixM subSampling(double div) {
        double columns2 = 1.0 * columns / div;
        double lines2 = 1.0 * lines / div;
        double cli2 = 1.0 * 1 / div;
        PixM pixM = new PixM((int) (columns2), ((int) lines2));
        for (int c = 0; c < getCompCount(); c++) {
            setCompNo(c);
            pixM.setCompNo(c);
            for (int i = 0; i < (int) columns2; i++)
                for (int j = 0; j < (int) lines2; j++) {
                    double m = mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                            (int) (cli2 * div));
                    pixM.set(i, j, m);
                }
        }
        return pixM;
    }

    public double mean(int i, int j, int w, int h) {
        double m = 0.0;
        int p = 0;
        for (int a = i; a < i + w; a++)
            for (int b = j; b < j + h; b++) {
                m += get(a, b);
                p++;
            }
        return m / p;
    }


    public PixM copy() {


        PixM pixM = new PixM(columns, lines);
        for (int c = 0; c < getCompCount(); c++) {
            setCompNo(c);
            pixM.setCompNo(c);
            for (int i = 0; i < (int) columns; i++)
                for (int j = 0; j < (int) lines; j++) {
                    //double m = mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                    //        (int) (cli2 * div));
                    pixM.set(i, j, get(i, j));
                }
        }
        return pixM;
    }

    public double distance(ParametricCurve curve, Point3D p) {
        double dI, dist = 10000;
        double j = -1;
        for (int i = 0; i < MAX_DISTANCE_ITERATIONS; i++)
            if ((dI = Point3D.distance(curve.calculerPoint3D(1.0 / i), p)) < dist) {
                dist = dI;
                j = 1. / i;
            }
        return j;
    }

    public double distance(PixM p2) {
        double d = 0.0;


        double div = 1.0;
        double columns2 = 1.0 * columns / div;
        double lines2 = 1.0 * lines / div;
        double cli2 = 1.0 * 1 / div;
        PixM pixM = new PixM((int) (columns2), ((int) lines2));
        for (int c = 0; c < getCompCount(); c++) {
            setCompNo(c);
            pixM.setCompNo(c);
            for (int i = 0; i < (int) columns2; i++)
                for (int j = 0; j < (int) lines2; j++) {
                    double m = mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                            (int) (cli2 * div));
                    double m2 = p2.mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                            (int) (cli2 * div));
                    d += Math.abs(m - m2);
                }
        }
        return d / columns / lines;
    }


    public void colorsRegion(int x, int y, int w, int h, double[] comps) {
        for (int i = x; i < x + w; i++)
            for (int j = y; j < y + h; j++)
                for (int c = 0; c < comps.length; c++) {
                    setCompNo(c);
                    set(i, j, comps[c]);
                }
    }

    public PixM getColorsRegion(int x, int y, int w, int h, int sizeX, int sizeY) {
        PixM subimage = new PixM(sizeX, sizeY);
        for (int i = x; i < x + w; i++)
            for (int j = y; j < y + h; j++)
                for (int c = 0; c < getCompCount(); c++) {
                    setCompNo(c);
                    subimage.setCompNo(c);
                    double v = get(i, j);
                    subimage.set((int) (1.0 * (x + w - i) / w * subimage.columns), (int) (1.0 * (y + h - j) / h * subimage.lines), v);
                    set(i, j, v);
                }
        return subimage;
    }

    public void colorsRegion(int x, int y, int w, int h, PixM subimage, int subImageCopyMode) {
        for (int i = x; i < x + w; i++)
            for (int j = y; j < y + h; j++)
                for (int c = 0; c < getCompCount(); c++) {
                    setCompNo(c);
                    subimage.setCompNo(c);
                    double v = subimage.get((int) (1.0 * (x + w - i) / w * subimage.columns), (int) (1.0 * (y + h - j) / h * subimage.lines));
                    set(i, j, v);
                }
    }

    public PixM subImage(int x, int y, int w, int h) {
        PixM p2 = new PixM(w, h);
        for (int i = x; i < x + w; i++)
            for (int j = y; j < y + h; j++)
                for (int c = 0; c < getCompCount(); c++) {
                    setCompNo(c);
                    p2.setCompNo(c);
                    double v = get(i, j);
                    set(i - x, j - y, v);
                }
        return p2;
    }

    /*
     * Default paste: mask comp = alpha value for chanel
     * @param x ordX in original space
     * @param y ordY in original space
     * @param w width in original space
     * @param h height in original space
     * @param subimage image to paste
     * @param addMask transparency mask for components: 0->original pixel 1->paste pixel
     */
    public void colorsRegionWithMask(int x, int y, int w, int h, PixM subimage, PixM addMask) {
        for (int i = x; i < x + w; i++)
            for (int j = y; j < y + h; j++)
                for (int c = 0; c < getCompCount(); c++) {
                    setCompNo(c);
                    subimage.setCompNo(c);
                    addMask.setCompNo(c);
                    double compOrigi = get(i, j);
                    double compPaste = subimage.get((int) (1.0 * (i - x) / w * subimage.columns), (int) (1.0 * (j - y) / h * subimage.lines));
                    double compMask = addMask.get((int) (1.0 * (i - x) / w * subimage.columns), (int) (1.0 * (j - y) / h * subimage.lines));
                    double v = compOrigi * (1 - compMask) + compPaste * compMask;
                    set(i, j, v);
                }
    }

    public boolean equals(Object compare) {
        if (compare instanceof PixM)
            if (Arrays.equals(((PixM) compare).x, x))
                return true;
        return false;

    }

    public double luminance(int x, int y) {
        double l = 0.0;
        setCompNo(0);
        l += 0.2126 * get(x, y);
        setCompNo(1);

        l += 0.7152 * get(x, y);
        setCompNo(2);
        //l += 0.0722 * get(x, y);
        l += 0.722 * get(x, y);

        return l;
    }

    public double norme(int x, int y) {
        double l = 0.0;
        setCompNo(0);
        l += get(x, y);
        setCompNo(1);

        l += get(x, y);
        setCompNo(2);
        l += get(x, y);

        return l;
    }

    public int getColumns() {
        return columns;
    }

    public int getLines() {
        return lines;
    }

    public void paintAll(double[] doubles) {
        for (int i = 0; i < getColumns(); i++)
            for (int j = 0; j < getLines(); j++)
                for (int c = 0; c < 3; c++) {
                    setCompNo(c);
                    set(i, j, doubles[c]);
                }

    }
}
