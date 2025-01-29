/*
 *
 *  *
 *  *  * Copyright (c) 2025. Manuel Daniel Dahmen
 *  *  *
 *  *  *
 *  *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *  *
 *  *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *  *    you may not use this file except in compliance with the License.
 *  *  *    You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  *    Unless required by applicable law or agreed to in writing, software
 *  *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  *    See the License for the specific language governing permissions and
 *  *  *    limitations under the License.
 *  *
 *  *
 *
 *
 *
 *  * Created by $user $date
 *
 *
 */
package matrix;

import org.jetbrains.annotations.NotNull;

import one.empty3.feature.MatrixFormatException;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.libs.Image;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.Random;

public class M implements InterfaceMatrix {
    public static PrimitiveIterator.OfDouble r = new Random().doubles().iterator();
    public static final Double noValue = r.next();
    protected int columns;
    protected int lines;
    protected int[] x;
    protected int compNo = 0;
    public final int compCount = 3;

    public M(int c, int l) {
        this.lines = l;
        this.columns = c;
        x = new int[l * c];
        Arrays.fill(x, 0);
        //Logger.getAnonymousLogger().log(Level.INFO, "Columns=" + columns + "\n Lines = " + lines+ " \n Total size ="+x.length);
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

    public M plus(M m2) {
        for (int i = 0; i < lines; i++)
            for (int j = 0; j < columns; j++) {
                set(i, j, get(i, j));
            }
        return this;
    }

    public M operator(char[] operators, M... m) {
        return null;
    }

    /*public static M diag(M square) {
        M matrix = new M(square.columns);
        for (int i = 0; i < matrix.columns; i++)
            matrix.set(i, i, square.get(i, i));

        return matrix;
    }*/

    public double[] getValues(int i, int j) {

        double[] v;
        v = readComps(i, j);
        return v;
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

    public void setP(int i, int j, @NotNull Point3D p) {
        int v = 0;
        if (i >= 0 && i < columns && j >= 0 && j < lines) {
            for (int d = 0; d < 3; d++) {
                v += ((int) (p.get(d) * 255d)) << ((2 - d) * 8);
            }
        }
        x[index(i, j)] = v;
    }

    public Point3D getP(int i, int j) {
        Point3D p = new Point3D(0.0,0.0,0.0);
        if (i >= 0 && i < columns && j >= 0 && j < lines) {
            int[] ints = readCompsInts(i, j);
            for (int k = 0; k < 3; k++) {
                p.set(k, ints[k] / 255.0);
            }
        }
        return p;

    }

    public void setValues(int i, int j, double... v) {


        for (int d = 0; d < v.length; d++) {
            if (d < compCount) {
                writeComp(i, j, v[d], d);
            }
        }
    }

    public M(PixM pix) {
        this.lines = pix.getLines();
        this.columns = pix.getColumns();
        x = new int[lines * columns];
        for (int c = 0; c < 3; c++) {
            setCompNo(c);

            for (int i = 0; i < pix.getColumns(); i++) {
                for (int j = 0; j < pix.getLines(); j++) {
                    set(i, j, pix.get(i, j));
                }
            }
        }
    }


    public void init(int l, int c) {
        this.lines = l;
        this.columns = c;
        x = new int[l * c];
    }

    @Override
    public Image getBitmap() {
        Image image = new one.empty3.libs.Image(columns, lines);
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getLines(); j++) {
                for (int k = 0; k < 3; k++) {
                    image.setRgb(i, j, getInt(i, j));
                }
            }

        }
        return image;
    }

    public M(int cl) {
        this(cl, cl);
    }

    public double get(int column, int line) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && compNo >= 0 && compNo < compCount) {
            return readComps(column, line)[compNo];
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
        return ((line * columns + column));
    }

    /***
     * Must be correct drawing
     * @param i column
     * @param j line
     * @param d value 0..1
     * @param compNoP r,g,b,a
     */
    public void writeComp(int i, int j, double d, int compNoP) {
        int index = index(i, j);
        if(d<0.0) d=0.0;
        if(d>1.0) d=1.0;
        int tmpCompNo  = getCompNo();
        setCompNo(compNoP);
        if (compNoP < 3) {
            int pixelValue = x[index];
            int mask1 = 0xffffffff - (0xff << ((2 - compNoP) * 8));

            pixelValue = (pixelValue & mask1) +  (((int)(d * 0xff)) << ((2 - compNoP) * 8));
            x[index] = pixelValue|0xff000000;
        }
        setCompNo(tmpCompNo);
    }

    public void writeComps(int i, int j, int color) {
        int index = index(i, j);
        x[index] = color|0xff000000;

    }

    public double[] readCompsA(int i, int j) {
        if(i>=0&&i<columns&&j>=0&&j<lines&&index(i,j)<x.length&&index(i,j)>=0) {
            int d = x[index(i, j)];
            int a = ((d & 0xFF000000) >> 24) & 0xFF;
            int r = ((d & 0x00FF0000) >> 16) & 0xFF;
            int g = ((d & 0x0000FF00) >> 8) & 0xFF;
            int b = ((d & 0x000000FF)) & 0xFF;
            return new double[]{r / 255., g / 255., b / 255., a / 255.};
        }
        return new double[]{(double) 0 / 255f, (double) 0 / 255f, (double) 0/ 255f};
    }
    public double[] readComps(int i, int j) {
        int[] c = new int[] {0,0,0,0};
        if(i>=0&&i<columns&&j>=0&&j<lines&&index(i,j)<x.length&&index(i,j)>=0) {
            int value = this.x[index(i, j)];
            for (int k = 0; k < 3; k++) {
                c[k] = (value >> ((2 - k) * 8)) & 0xFF;
            }
        }
        return new double[]{(double) c[0] / 255f, (double) c[1] / 255f, (double) c[2] / 255f};
    }


    public int getInt(int i, int j) {
        return x[index(i, j)];
    }

    public int[] readCompsInts(int x, int y) {
        int[] c = new int[3];
        int value = this.x[index(x, y)];
        for (int i = 0; i < 3; i++) {
            c[i] = (( value) >> ((2 - i) * 8)) & 0xFF;
        }
        return new int[]{c[0], c[1], c[2]};
    }

    public void set(int column, int line, double d) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && compNo<compCount) {
            writeComp(column, line, d, compNo);
        }

    }

    public void set(int column, int line, double... values) {
        setValues(column, line, values);
    }

    public void set(int index, int value) {
        x[index] = value;
    }



    public M tild() {
        M m = new M(lines, columns);
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

    private M dot(M m) {
        if (!isSquare() || columns == m.lines)
            throw new MatrixFormatException("determinant: not square matrix");
        M res = new M(m.columns, lines);
        for (int comp = 0; comp < getCompNo(); comp++) {
            res.setCompNo(comp);
            this.setCompNo(comp);
            for (int i = 0; i < m.columns; i++) {
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
        M m = null;

        if (lines < 1) { /* Error */
            throw new MatrixFormatException("<1 determinant");
        } else if (lines == 1) { /* Shouldn't get used */
            det = get(0, 0);
        } else if (lines == 2) {
            det = get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
        } else {
            det = 0;
            for (j1 = 0; j1 < lines; j1++) {
                m = new M(lines - 1);
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
    public M CoFactor() {
        if (!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        int n = lines;
        M a = this;
        M b = new M(lines - 1);


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

    public void setRegionCopy(PixM original, int iStart, int jStart, int iEnd, int jEnd,
                              PixM pixM, int iPaste, int jPaste) {
    }

    public void setRegionCopy(PixM original, int iStart, int jStart, int iEnd, int jEnd,
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
        M matrix = new M(d.lines, d.columns);
        for (int i = 0; i < d.lines; i++)
            matrix.set(i, i, d.get(i, i));
        return matrix;
    }

    public int[]  getX() {
        return x;
    }


}
