/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;

import java.util.PrimitiveIterator;
import java.util.Random;

import one.empty3.libs.Image;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;

public class MImage /*implements InterfaceMatrix*/ {
    public static PrimitiveIterator.OfDouble r = new Random().doubles().iterator();
    public static final Double noValue = r.next();
    protected one.empty3.libs.Image Image = null;
    protected int columns;
    protected int lines;
    double[] x;
    protected int compNo;
    public int compCount = 3;

    public MImage(int c, int l) {
        this.Image = new Image(c, l, javaAnd.awt.image.Image.TYPE_INT_ARGB);
        this.lines = l;
        this.columns = c;
    }

    public MImage(one.empty3.libs.Image Image) {
        this.Image = Image;
        int l = Image.getHeight();
        int c = Image.getWidth();
        this.lines = l;
        this.columns = c;

    }

    public MImage(one.empty3.libs.Image Image, int resMax) {
        this.Image = Image;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public MImage plus(M m2) {
        for (int i = 0; i < lines; i++)
            for (int j = 0; j < columns; j++) {
                set(i, j, get(i, j));
            }
        return this;
    }

    public M operator(char[] operators, M... m) {
        return null;
    }

    /*public static GMatrix diag(GMatrix square) {
        GMatrix matrix = new GMatrix(square.getColumns());
        for (int i = 0; i < matrix.getColumns(); i++)
            matrix.set(i, i, square.get(i, i));

        return matrix;
    }*/

    public double[] getValues(int i, int j) {

        double[] v = new double[getCompCount()];

        for (int d = 0; d < getCompCount(); d++) {
            setCompNo(d);
            if (i >= 0 && i < getColumns() && j >= 0 && j < getLines()) {
                v[d] = get(i, j);
            }
        }
        return v;
    }

    public one.empty3.libs.Image getImage() {
        return Image;
    }

    public static double[] getVector(int add, double[]... vectors) {
        int d = 0;
        for (int i = 0; i < vectors.length; i++)
            d += vectors[i].length;
        d += add;
        double[] f = new double[d];
        int di = 0;
        for (int i = 0; i < vectors.length; i++) {
            for (double dou : vectors[i])
                f[di++] = dou;
        }
        return f;
    }

    public void setP(int i, int j, Point3D p) {
        if (Image != null && i < Image.getWidth() && i >= 0 && j < Image.getHeight() && j >= 0) {
            Image.setRGB(i, j, Lumiere.getInt(new double[]
                    {p.get(0), p.get(1), p.get(2)}));
            return;
        }

        for (int d = 0; d < 3; d++) {
            setCompNo(d);
            set(i, j, p.get(d));
        }
    }

    public Point3D getP(int i, int j) {
        if (Image != null && i < Image.getWidth() && i >= 0 && j < Image.getHeight() && j >= 0) {
            if (i >= 0 && i < getColumns() && j >= 0 && j < getLines()) {
                int pixel = Image.getRGB(i, j);
                double[] p = Lumiere.getDoubles(pixel);
                return new Point3D(p[0], p[1], p[2]);
            }
        }
        Point3D p = new Point3D();

        for (int d = 0; d < 3; d++) {
            setCompNo(d);
            if (i >= 0 && i < getColumns() && j >= 0 && j < getLines()) {
                p.set(d, get(i, j));
            }
        }
        return p;
    }

    public void setValues(int i, int j, double... v) {
        if (Image != null) {
            if (i >= 0 && i < Image.getWidth() && j >= 0 && j < Image.getHeight()) {
                Image.setRGB(i, j, Lumiere.getInt(new double[]
                        {v[0], v[1], v[2]}));
            }
        }

        for (int d = 0; d < v.length; d++) {
            setCompNo(d);
            if (i >= 0 && i < getColumns() && j >= 0 && j < getLines()) {
                set(i, j, v[d]);
            }
        }
    }

    public MImage(matrix.PixM pix) {
        this(pix.getLines(), pix.getColumns());
        this.lines = pix.getLines();
        this.columns = pix.getColumns();
        x = new double[lines * columns * 3];

        for (int i = 0; i < pix.getColumns(); i++) {
            for (int j = 0; j < pix.getLines(); j++) {
                for (int c = 0; c < 3; c++) {
                    setCompNo(c);
                    set(i, j, pix.get(i, j));
                }
            }
        }
        //Logger.getAnonymousLogger().log(Level.INFO, "Columns=" + columns + "\n Lines = " + lines+ " \n Total size ="+x.length);
    }


    public void init(int l, int c) {
        this.lines = l;
        this.columns = c;
        x = new double[l * c * compCount];
    }

    public MImage(int cl) {
        this(cl, cl);
    }

    public double get(int column, int line) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && compNo >= 0 && compNo < compCount) {
            if (Image != null) {
                return Lumiere.getDoubles(Image.getRGB(column, line))[compNo];
            }

            return x[index(column, line)];
        } else
            return noValue; // OutOfBound?
    }

    public double getIntensity(int column, int line) {
        double i = 0;
        for (int c = 0; c < 3; c++) {
            setCompNo(c);
            i += get(column, line) * get(column, line);

        }
        return Math.sqrt(i);
    }

    public void getColor(int column, int line,
                         float[] comps) {
        for (int c = 0; c < 3; c++) {
            setCompNo(c);
            comps[c] = (float) (get(column, line));

        }
    }

    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    public int index(int column, int line) {
        return getCompNo() + getCompCount() * ((line * columns + column));
    }

    public void set(int column, int line, double d) {
        if (column >= 0 && column < columns && line >= 0 && line < lines) {
            if (Image != null) {
                int pixel = Image.getRGB(column, line);
                double[] ps = new double[]{0, 0, 0};
                ps[compNo] = d;
                int p = pixel | Lumiere.getInt(ps);
                Image.setRGB(column, line, p);
                return;
            }
            x[index(column, line)] = d;
        }

    }

    public void set(int column, int line, double... values) {
        setValues(column, line, values);
    }

    public MImage tild() {
        MImage m = new MImage(lines, columns);
        for (int i = 0; i < lines; i++)
            for (int j = 0; j < columns; j++)
                for (int comp = 0; comp < getCompNo(); comp++)
                    m.set(i, j, get(j, i));
        return m;
    }

    public double trace() {
        return tild().dot(this).trace();
    }


    public double diagonalSum() {
        double[] sums = new double[getCompCount()];
        if (!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        double sum = 0.0;
        for (int comp = 0; comp < getCompNo(); comp++)
            for (int i = 0; i < lines; i++)
                sums[comp] += get(i, i);
        return sum;
    }

    public int getCompCount() {
        return compCount;
    }

    private MImage dot(MImage m) {
        if (!isSquare() || columns == m.getLines())
            throw new MatrixFormatException("determinant: not square matrix");
        MImage res = new MImage(m.getColumns(), lines);
        for (int comp = 0; comp < getCompNo(); comp++) {
            res.setCompNo(comp);
            this.setCompNo(comp);
            for (int i = 0; i < m.getColumns(); i++) {
                for (int j = 0; j < lines; j++) {
                    for (int k = 0; k < lines; k++)
                        res.set(i, j, res.get(i, j) + get(i, k) * res.get(k, j));
                }
            }
        }
        return res;
    }

    /*
        Recursive definition of determinate using expansion by minors.
                */
    public double determinant() {
        if (!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        int i, j, j1, j2;
        double det = 0;
        MImage m = null;

        if (lines < 1) { /* Error */
            throw new MatrixFormatException("<1 determinant");
        } else if (lines == 1) { /* Shouldn't get used */
            det = get(0, 0);
        } else if (lines == 2) {
            det = get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
        } else {
            det = 0;
            for (j1 = 0; j1 < lines; j1++) {
                m = new MImage(lines - 1);
                for (i = 1; i < lines; i++) {
                    j2 = 0;
                    for (j = 0; j < lines; j++) {
                        if (j == j1)
                            continue;
                        m.set(i - 1, j2, get(i, j));
                        j2++;
                    }
                }
                det += Math.pow(-1.0, j1 + 2.0) * get(0, j1) * m.determinant();
            }
        }
        return (det);
    }

    private boolean isSquare() {
        return lines == columns;
    }

    /*
       Find the cofactor matrix of a square matrix
    */
    public MImage CoFactor() {
        if (!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        int n = lines;
        MImage a = this;
        MImage b = new MImage(lines - 1);


        int i, j, ii, jj, i1, j1;
        double det;
        M c;

        c = new M(n - 1);

        for (j = 0; j < n; j++) {
            for (i = 0; i < n; i++) {

                /* Form the adjoint a_ij */
                i1 = 0;
                for (ii = 0; ii < n; ii++) {
                    if (ii == i)
                        continue;
                    j1 = 0;
                    for (jj = 0; jj < n; jj++) {
                        if (jj == j)
                            continue;
                        c.set(i1, j1, get(ii, jj));
                        j1++;
                    }
                    i1++;
                }

                /* Calculate the determinate */
                det = c.determinant();

                /* Fill in the elements of the cofactor */
                b.set(i, j, Math.pow(-1.0, i + j + 2.0) * det);
            }
        }
        return b;
    }

    /*
     * pa: mesure de l'erreur dans la fenêtre
     * en W(0, 1, 2, 3)
     * par rapport à W(4, 5, 2, 3)
     * @param w12 x0, y0, w.w, w.h, x1, y1
     * @return E  errors sum of differences. compNo
     */
    public double error(double... w12) {
        double E = 0.0;
        for (double i = 0; i < w12[2]; i++)
            for (double j = 0; j < w12[3]; j++) {
                E +=
                        (get((int) (w12[0] + w12[2]), (int) (w12[1] + w12[3]))
                                - get((int) (w12[4] + w12[2]), (int) (w12[5] + w12[3])));
            }
        return E;
    }

    public double getOpValue(String op, double m1ij, double m2ij) {
        return m1ij - m2ij;
    }

    public M op(M mat2, String op) {

        M res = new M(columns, lines);
        for (int comp = 0; comp < getCompNo(); comp++) {
            res.setCompNo(comp);
            this.setCompNo(comp);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {

                    res.set(i, j, getOpValue("", get(i, j), mat2.get(i, j)));
                }
            }
        }
        return res;

    }

    public void setRegionCopy(M3 original, int ii, int ij, int iStart, int jStart, int iEnd, int jEnd,
                              PixM pixM, int iPaste, int jPaste) {
        for (int c = 0; c < getCompCount(); c++) {
            original.setCompNo(c);
            pixM.setCompNo(c);
            int x = 0;
            for (int i = iStart; i < iEnd; i++) {
                int y = 0;
                for (int j = jStart; j < jEnd; j++) {
                    double v = original.get(i, j, ii, ij);
                    pixM.set(iPaste + x, jPaste + y, v);
                    y++;
                }
                x++;
            }


        }
    }

    public void setRegionCopy(matrix.PixM original, int iStart, int jStart, int iEnd, int jEnd,
                              PixM pixM, int iPaste, int jPaste) {
    }

    public void setRegionCopy(matrix.PixM original, int iStart, int jStart, int iEnd, int jEnd,
                              M3 m3, int iPaste, int jPaste, int iiPaste, int ijPaste) {
    }

    public static M repmat(double[][] dd, int nLine, int mColumn) {
        M matrix = new M(nLine * dd.length, mColumn * dd[0].length);
        for (int i = 0; i < nLine; i++) {
            for (int j = 0; j < mColumn; j++) {
                for (int ii = 0; ii < dd.length; i++)
                    for (int ij = 0; ij < dd[0].length; ij++) {
                        matrix.set(i * nLine + ii, j * mColumn + ij, dd[ii][ij]);
                    }
            }
        }
        return matrix;
    }

    public static M diag(double... d) {
        M matrix = new M(d.length, d.length);
        for (int i = 0; i < d.length; i++)
            matrix.set(i, i, d[i]);
        return matrix;
    }

    public static M diag(M d) {
        M matrix = new M(d.getLines(), d.getColumns());
        for (int i = 0; i < d.getLines(); i++)
            matrix.set(i, i, d.get(i, i));
        return matrix;
    }
}
