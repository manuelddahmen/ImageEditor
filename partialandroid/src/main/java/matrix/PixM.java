/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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

package matrix;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import one.empty3.feature.M;
import one.empty3.feature.V;
import one.empty3.library.ITexture;
import one.empty3.library.LineSegment;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.Serialisable;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.libs.Image;

public class PixM extends M implements Parcelable, Serializable, Serialisable {
    public static final int COMP_RED = 0;
    public static final int COMP_GREEN = 1;
    public static final int COMP_BLUE = 2;
    public static final int COMP_ALPHA = 3;
    public static final int COMP_INTENSITY = 4;
    private int MAX_DISTANCE_ITERATIONS = 100;

    public PixM(int l, int c) {

        super(l, c);
    }

    public PixM(@NotNull Bitmap image) {
        this(image.getWidth(), image.getHeight());
        double[] colorComponents = new double[4];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getPixel(i, j);
                int pix = image.getPixel(i, j);
                x[index(i, j)] = pix;
            }
        }
    }

    public PixM(@NotNull Bitmap image, boolean isBitmap) {
        this(image.getWidth(), image.getHeight());
        double[] colorComponents = new double[3];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getPixel(i, j);
                int pix = image.getPixel(i, j);
                x[index(i, j)] = pix;
            }
        }
    }

    public PixM(double[][] distances) {
        super(distances.length, distances[0].length);
        for (int i = 0; i < getColumns(); i++)
            for (int j = 0; j < getLines(); j++)
                set(i, j, distances[i][j]);
    }

    public PixM(@NotNull Image read) {
        this(read.getImage());
        if (read.getBitmap() == null)
            throw new NullPointerException("public PixM(@NotNull Image read) || bitmap is null");
    }

    protected PixM(Parcel in) {
        super(in.readInt(), in.readInt());
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getLines(); j++) {
                for (int i1 = 0; i1 < compCount; i1++) {
                    setCompNo(i1);
                    set(i, j, in.readDouble());
                }
            }
        }

        MAX_DISTANCE_ITERATIONS = in.readInt();
    }

    public static final Creator<PixM> CREATOR = new Creator<PixM>() {
        @Override
        public PixM createFromParcel(Parcel in) {
            return new PixM(in);
        }

        @Override
        public PixM[] newArray(int size) {
            return new PixM[size];
        }
    };

    public static <T> PixM getPixM(@NotNull Bitmap bitmap) {
        return new PixM(bitmap);
    }

    public Point3D getRgb(int i, int j) {
        if (i < 0 || i >= getColumns() || j < 0 || j >= getLines()) {
            return Point3D.O0;
        }
        setCompNo(0);
        double dr = get(i, j);
        setCompNo(1);
        double dg = get(i, j);
        setCompNo(2);
        double db = get(i, j);
        return new Point3D(dr, dg, db);
    }

    public static PixM getPixM(@NotNull Bitmap image, double maxRes) {
        return getPixM(image, (int) maxRes);
    }

    public static PixM getPixM(@NotNull Image image, double maxRes) {
        if (image.getBitmap() == null) {
            return null;
        } else {
            return getPixM(image.getBitmap(), (int) maxRes);
        }
    }

    public static PixM getPixM(@NotNull Bitmap image, int maxRes) {
        double f = 1.0;
        if (maxRes <= 0) {
            f = 1.0;
        } else if (maxRes < image.getWidth() && maxRes < image.getHeight()) {
            f = 1.0 / Math.max(image.getWidth(), image.getHeight()) * maxRes;
        } else if (maxRes >= image.getWidth() || maxRes >= image.getHeight()) {
            f = 1.0;
        }
        double columns2 = 1.0 * image.getWidth() * f;
        double lines2 = 1.0 * image.getHeight() * f;

        PixM pixM = new PixM((int) (columns2), ((int) lines2));


        for (int i = 0; i < (int) columns2; i++) {
            for (int j = 0; j < (int) lines2; j++) {


                int rgb = image.getPixel(
                        (int) (1.0 * i / columns2 * image.getWidth()), (int) (1.0 * j / lines2 * image.getHeight()));
                one.empty3.libs.Color c = new one.empty3.libs.Color(rgb);
                pixM.setValues(i, j, c.getRed() / 255d, c.getGreen() / 255d, c.getBlue() / 255d);
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
                    for (int u = -filter.getColumns() / 2; u <= filter.getLines() / 2; u++) {
                        for (int v = -filter.getLines() / 2; v <= filter.getLines() / 2; v++) {


                        /*V derivative = derivative(i, j, 2, null);
                        double v1 = derivative.get(0, 0);
                        double v2 = derivative.get(1, 0);
                        c.set(i, j,(v1+v2)
                                * filter.filterUVvalue(u, v, u*v));*/
                            double filterUVvalue = filter.get(u + filter.getColumns() / 2,
                                    v + filter.getLines() / 2);
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

    public V derivative(int x, int y, int order, V originValue) {
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

    public Image getBitmap() {
        Bitmap image = Bitmap.createBitmap(columns,
                lines, Bitmap.Config.ARGB_8888);


        double[] rgba = new double[3];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                for (int c = 0; c < 3; c++) {
                    setCompNo(c);
                    rgba[c] = get(i, j);
                }
                image.setPixel(i, j, Lumiere.getInt(rgba));
            }
        }
        return new Image(image);

    }


    public Image getImage() {
        return getBitmap();
    }
/*

    public void plotCurve(ParametricCurve curve, ITexture texture) {

        float[] rgba = new float[getCompCount()];
        for (double t = 0; t < 1.0; t += 0.001) {
            Color.valueOf(curve.texture().getColorAt(t, 0.)).getColorComponents(rgba);
            Point3D p = curve.calculerPoint3D(t);
            for (int c = 0; c < 3; c++) {
                setCompNo(c);
                set((int) (double) p.getX(), (int) (double) p.getY(), rgba[c]);
            }
        }
    }
*/
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
    /*
    public void fillIn(ParametricCurve curve, ITexture texture, ITexture borderColor) {
        int[] linesIn = new int[getLines()];
        int[] linesOut = new int[getLines()];
        for (int i = 0; i < getLines(); i++) {
            linesIn[i] = -1;
            linesOut[i] = -1;
        }
        float[] rgba = new float[getCompCount()];
        float[] rgbaBorder = new float[getCompCount()];


        Point3D old = Point3D.O0;
        for (double t = 0; t < 1.0; t += (1.0 / getColumns()) / 100) {
            Point3D p = curve.calculerPoint3D(t);

            int x = (int) (double) p.get(0);
            int y = (int) (double) p.get(1);
            int xOld = (int) (double) old.get(0);
            int yOld = (int) (double) old.get(1);

            int abs = Math.abs(linesIn[y] - x);

            if (x >= 0 && x < getColumns() && y >= 0 && y < getLines()
                    && abs > 2 && (linesIn[y] == -1 || linesOut[y] == -1)) {


                if (linesIn[y] == -1) {
                    linesIn[y] = x;
                } else if (linesOut[y] == -1) {
                    if (linesIn[y] > x) {
                        linesOut[y] = linesIn[y];
                    }
                    linesOut[y] = x;
                }
            }
            old = p;

        }
        for (int i = 0; i < linesIn.length; i++) {
            System.out.printf("LinesIn [ i %d ] = %d\n", i, linesIn[i]);
            System.out.printf("LinesOut[ i %d ] = %d\n", i, linesOut[i]);
        }


        for (int y = 0; y < linesIn.length; y++) {
            if (linesIn[y] != -1 && linesOut[y] != -1) {
                plotCurve(new LineSegment(Point3D.n(linesIn[y], y, 0), Point3D.n(linesOut[y], y, 0)),
                        texture);
            }
            if (linesIn[y] != -1)
                setValues(linesIn[y], y, Lumiere.getDoubles(
                        borderColor.getColorAt(1.0 * linesIn[y] / getColumns(), 1.0 * y / getLines())));
            if (linesOut[y] != -1)
                setValues(linesOut[y], y, Lumiere.getDoubles(
                        borderColor.getColorAt(1.0 * linesIn[y] / getColumns(), 1.0 * y / getLines())));


        }


    }
*/

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
        for (int i = 0; i < image.getColumns(); i++) {
            for (int j = 0; j < image.getLines(); j++) {
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

    /*
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
    */
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
                    subimage.set((int) (1.0 * (x + w - i) / w * subimage.getColumns()), (int) (1.0 * (y + h - j) / h * subimage.getLines()), v);
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
                    double v = subimage.get((int) (1.0 * (x + w - i) / w * subimage.getColumns()), (int) (1.0 * (y + h - j) / h * subimage.getLines()));
                    set(i, j, v);
                }
    }

    public boolean equals(Object compare) {
        if (compare instanceof PixM)
            //if (Arrays.equals((((PixM) compare).toGMatrix()).x, toGMatrix()))
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
        l += 0.0722 * get(x, y);

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

    public void plotCurve(ParametricCurve curve, ITexture texture) {

        double[] rgba = new double[getCompCount()];
        double t;
        for (t = curve.getStartU(); t < curve.getEndU(); t += curve.getIncrU().getElem()) {
            rgba = Lumiere.getDoubles(Color.valueOf(curve.texture().getColorAt(t, 0.)).toArgb());
            double t1;
            for (t1 = t; t1 < t + curve.getIncrU().getElem(); t1 += 0.001) {
                Point3D p = curve.calculerPoint3D(t1);
                for (int c = 0; c < 3; c++) {
                    setCompNo(c);
                    set((int) (double) p.getX(), (int) (double) p.getY(), rgba[c]);
                }
            }
            rgba = Lumiere.getDoubles(Color.valueOf(curve.texture().getColorAt(t, 0.)).toArgb());
            Point3D p = curve.calculerPoint3D(t + curve.getIncrU().getElem());
            for (int c = 0; c < 3; c++) {
                setCompNo(c);
                set((int) (double) p.getX(), (int) (double) p.getY(), rgba[c]);
            }
        }
        rgba = Lumiere.getDoubles(Color.valueOf(curve.texture().getColorAt(t, 0.)).toArgb());
        Point3D p = curve.calculerPoint3D(curve.getEndU());
        for (int c = 0; c < 3; c++) {
            setCompNo(c);
            set((int) (double) p.getX(), (int) (double) p.getY(), rgba[c]);
        }

    }

    public void plotCurve(ParametricCurve curve, Color color, int x, int y) {

        double[] rgba = new double[getCompCount()];
        for (double t = 0; t < 1.0; t += 0.001) {
            for (int c = 0; c < 3; c++) {
                rgba = Lumiere.getDoubles(Color.valueOf(curve.texture().getColorAt(t, 0.)).toArgb());
                Point3D p = curve.calculerPoint3D(t);
                setCompNo(c);
                set((int) (double) p.getX(), (int) (double) p.getY(), rgba[c]);
            }
        }
    }

    public void fillIn(ParametricCurve curve, ITexture texture, ITexture borderColor) {
        int[] linesIn = new int[getLines()];
        int[] linesOut = new int[getLines()];
        for (int i = 0; i < getLines(); i++) {
            linesIn[i] = -1;
            linesOut[i] = -1;
        }
        float[] rgba = new float[getCompCount()];
        float[] rgbaBorder = new float[getCompCount()];


        Point3D old = Point3D.O0;
        for (double t = 0; t < 1.0; t += (1.0 / getColumns()) / 100) {
            Point3D p = curve.calculerPoint3D(t);

            int x = (int) (double) p.get(0);
            int y = (int) (double) p.get(1);
            int xOld = (int) (double) old.get(0);
            int yOld = (int) (double) old.get(1);

            int abs = Math.abs(linesIn[y] - x);

            if (x >= 0 && x < getColumns() && y >= 0 && y < getLines()
                    && abs > 2 && (linesIn[y] == -1 || linesOut[y] == -1)) {


                if (linesIn[y] == -1) {
                    linesIn[y] = x;
                } else if (linesOut[y] == -1) {
                    if (linesIn[y] > x) {
                        linesOut[y] = linesIn[y];
                    }
                    linesOut[y] = x;
                }
            }
            old = p;

        }
        for (int i = 0; i < linesIn.length; i++) {
            System.out.printf("LinesIn [ i %d ] = %d\n", i, linesIn[i]);
            System.out.printf("LinesOut[ i %d ] = %d\n", i, linesOut[i]);
        }


        for (int y = 0; y < linesIn.length; y++) {
            if (linesIn[y] != -1 && linesOut[y] != -1) {
                plotCurve(new LineSegment(Point3D.n(linesIn[y], y, 0), Point3D.n(linesOut[y], y, 0)),
                        texture);
            }
            if (linesIn[y] != -1)
                setValues(linesIn[y], y, Lumiere.getDoubles(
                        borderColor.getColorAt(1.0 * linesIn[y] / getColumns(), 1.0 * y / getLines())));
            if (linesOut[y] != -1)
                setValues(linesOut[y], y, Lumiere.getDoubles(
                        borderColor.getColorAt(1.0 * linesIn[y] / getColumns(), 1.0 * y / getLines())));


        }


    }

    /*
        public void pasteSubImage(final PixM copy, final int x, final int y, final int w, final int h) {
            if (w > 0 && h > 0)
                for (int i = 0; i < copy.getColumns(); i++) {
                    for (int j = 0; j < copy.getLines(); j++) {
                        int xx = (int) (x + ((1.0 * i) / copy.getColumns()) * w);
                        int yy = (int) (y + ((1.0 * j) / copy.getLines()) * h);

                        double dx = (1.0 / copy.getColumns()) * w;
                        double dy = (1.0 / copy.getLines()) * h;

                        if(dx>=0 && dy>=0) {
                            for (double i2 = xx; i2 <= xx + dx; i2++) {
                                for (double j2 = yy; j2 <= yy + dy; j2++) {
                                    for (int c = 0; c < 3; c++) {
                                        copy.setCompNo(c);
                                        setCompNo(c);
                                        set((int)i2, (int)j2, copy.get(i, j));
                                    }
                                }
                            }
                        }
                    }
                }
        }
        */
    public void pasteSubImage(final PixM copy, final int x, final int y, final int w, final int h) {
        if (w > 0 && h > 0)
            for (int i = x; i < x + w; i++) {
                for (int j = y; j < y + h; j++) {
                    int xx = (int) ((1.0 * (i - x) / w) * copy.getColumns());
                    int yy = (int) ((1.0 * (j - y) / h) * copy.getLines());

                    for (int c = 0; c < 3; c++) {
                        copy.setCompNo(c);
                        setCompNo(c);
                        set(i, j, copy.get(xx, yy));
                    }
                }
            }
    }
/*
    public PixM pasteSubImage(int x, int y, int w, int h) {
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
    }*/

    public PixM copySubImage(int x, int y, int w, int h) {
        if (w <= 0 || h <= 0)
            return null;
        PixM p2 = new PixM(w, h);
        for (int i = x; i < x + w; i++)
            for (int j = y; j < y + h; j++)
                for (int c = 0; c < 3; c++) {
                    setCompNo(c);
                    p2.setCompNo(c);
                    p2.set(i - x, j - y, get(i, j));
                }
        return p2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int k) {
        parcel.writeInt(getColumns());
        parcel.writeInt(getLines());
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getLines(); j++) {
                for (int i1 = 0; i1 < compCount; i1++) {
                    setCompNo(i1);
                    parcel.writeDouble(get(i, j));
                }
            }
        }
    }

    public void replaceColor(int oldColorFill, int newColorFill, double d) {
        double[] doublesOld = Lumiere.getDoubles(oldColorFill | 0xff000000);
        double[] doublesNew = Lumiere.getDoubles(newColorFill | 0xff000000);

        Point3D p0 = new Point3D(doublesOld);
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getLines(); j++) {
                if (getP(i, j).moins(p0).norme() < d) {
                    setInt(index(i, j), newColorFill);
                }
            }
        }

    }

    public static boolean equalsArrays(double[] arr1, double[] arr2, double inter) {
        if (arr1 != null && arr2 != null && arr1.length == 3 && arr2.length == 3) {
            for (int i1 = 0; i1 < 3; i1++) {
                if (arr1[i1] > arr2[i1] + inter || arr1[i1] < arr2[i1] - inter)
                    return false;
            }

            return true;
        }
        return false;
    }

    private double[] tmpColor2 = new double[3];

    public void paintIfNot(int x, int y, int width, int height, @Nullable Bitmap bitmaToDraw, int colorTransparent) {
        double[] tmpColor3 = new double[3];
        tmpColor3 = Lumiere.getDoubles(colorTransparent);
        for (int i = x; i < width; i++) {
            for (int j = y; j < height; j++) {
                assert bitmaToDraw != null;
                double xOrig = Math.min(1.0 * (i - x) / width * bitmaToDraw.getWidth(), bitmaToDraw.getWidth() - 1);
                double yOrig = Math.min(1.0 * (j - y) / height * bitmaToDraw.getHeight(), bitmaToDraw.getHeight() - 1);

                int pixel = bitmaToDraw.getPixel((int) xOrig, (int) yOrig);
                tmpColor2 = Lumiere.getDoubles(pixel);
                if (!equalsArrays(tmpColor2, tmpColor3, 0.01)) {
                    setValues(i, j, tmpColor2);
                }
            }
        }
    }

    //private double[] tmpColor2 = new double[3];
    public void paintIfNot(int x, int y, int width, int height, @NotNull Bitmap bitmapToDraw, int colorPaint, PixM filledImage) {
        double[] tmpColor3 = new double[3];
        double[] pixelsFilterValue = new double[3];
        tmpColor3 = Lumiere.getDoubles(colorPaint);
        for (int i = x; i < width; i++) {
            for (int j = y; j < height; j++) {
                double percentXtarget = 1.0 * (i - x) / width;
                double percentYtarget = 1.0 * (j - y) / height;
                double percentXsource = 1.0 * (i - x) / width;
                double percentYsource = 1.0 * (j - y) / height;
                assert bitmapToDraw != null;
                double xOrig = Math.min(percentXtarget * bitmapToDraw.getWidth(), bitmapToDraw.getWidth() - 1);
                double yOrig = Math.min(percentYtarget * bitmapToDraw.getHeight(), bitmapToDraw.getHeight() - 1);
                double xFilter = Math.min(percentXtarget * filledImage.getColumns(), filledImage.getColumns() - 1);
                double yFilter = Math.min(percentYtarget * filledImage.getLines(), filledImage.getLines() - 1);
                int pixel = bitmapToDraw.getPixel((int) xOrig, (int) yOrig);
                double[] pixelIfNot = filledImage.getValues((int) xFilter, (int) yFilter);
                tmpColor2 = Lumiere.getDoubles(pixel);
                if (!equalsArrays(pixelsFilterValue, pixelIfNot, 0.01)) {
                    setValues(i, j, tmpColor2);
                }
            }
        }
    }

    public PixM resize(int columnsResized, int linesResized) {
        PixM resized = new PixM(columnsResized, linesResized);
        Point3D incr = new Point3D(1.0, 1.0, 0.0);

        incr.set(0, 1.0 * columnsResized / columns);
        incr.set(1, 1.0 * linesResized / lines);

        double incrX = 1.0 * incr.get(0);
        double incrY = 1.0 * incr.get(1);

        for (double i = 0; i < (int) columnsResized; i++) {
            for (double j = 0; j < (int) linesResized; j++) {
                double iOrig = i / incrX;
                while (iOrig < (i + 1) / incrX) {
                    double jOrig = i / incrY;
                    while (jOrig < (j + 1) / incrY) {
                        for (int c = 0; c < compCount; c++) {
                            setCompNo(c);
                            resized.setCompNo(c);

                            resized.setValues((int) i, (int) j, getValues((int) iOrig, (int) jOrig));
                        }
                        jOrig += 1 / incrY;
                    }
                    iOrig += 1 / incrX;
                }
            }
        }
        return resized;
    }

    @Override
    public Serialisable decode(DataInputStream in) {
        try {
            int w = in.readInt();
            int h = in.readInt();
            int c = in.readInt();
            PixM pixM = new PixM(w, h);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    for (int k = 0; k < c; k++) {
                        pixM.setCompNo(k);
                        pixM.set(i, j, in.readDouble());
                    }
                }
            }

            return pixM;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int encode(DataOutputStream out) {
        try {
            out.writeInt(columns);
            out.writeInt(lines);
            out.writeInt(compCount);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    for (int k = 0; k < compCount; k++) {
                        setCompNo(k);
                        out.writeDouble(get(i, j));
                    }
                }
            }

            return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int type() {
        return 4;
    }

    public void setInt(int index, int rgb) {
        x[index] = rgb;
    }
}
