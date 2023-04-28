package one.empty3.feature_arr_style;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Vector;

import one.empty3.library.Point3D;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GMatrix {
    public static PrimitiveIterator.OfDouble r = new Random().doubles().iterator();
    public static final Double noValue = r.next();

    protected int columns;
    protected int lines;
    double[] x;
    protected int compNo;
    protected final int compCount = 3;

    public GMatrix(int c, int l) {
        this.lines = l;
        this.columns = c;
        x = new double[l * c * compCount];

        //System.out.println("Columns=" + columns + "\n Lines = " + lines+ " \n Total size ="+x.length);
    }

    public double[] getValues(int i, int j) {

        double[] v = new double[getCompCount()];

        for (int d = 0; d < getCompCount(); d++) {
            setCompNo(d);
            v[d] = get(i, j);
        }
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

    public void setP(int i, int j, Vector<Double> p) {


        for (int d = 0; d < 3; d++) {
            setCompNo(d);
            set(i, j, p.get(d));
        }
    }

    public Point3D getP(int i, int j) {

        Point3D p = new Point3D();

        for (int d = 0; d < 3; d++) {
            setCompNo(d);
            p.set(d, get(i, j));
        }
        return p;
    }

    public void setValues(int i, int j, double... v) {


        for (int d = 0; d < v.length; d++) {
            setCompNo(d);
            set(i, j, v[d]);
        }
        return;
    }

    public GMatrix(PixM pix) {
        this.lines = pix.getLines();
        this.columns = pix.getColumns();
        x = new double[lines * columns * 3];
        for (int c = 0; c < 3; c++) {
            setCompNo(c);

            for (int i = 0; i < pix.getColumns(); i++) {
                for (int j = 0; j < pix.getLines(); j++) {
                    set(i, j, pix.get(i, j));
                }
            }
        }
        //System.out.println("Columns=" + columns + "\n Lines = " + lines+ " \n Total size ="+x.length);
    }


    protected void init(int l, int c) {
        this.lines = l;
        this.columns = c;
        x = new double[l * c * compCount];
    }

    public GMatrix(int cl) {
        this(cl, cl);
    }

    public double get(int column, int line) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && compNo >= 0 && compNo < compCount) {
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
            x[index(column, line)] = d;
        }

    }

    public GMatrix tild() {
        GMatrix GMatrix = new GMatrix(lines, columns);
        for (int i = 0; i < lines; i++)
            for (int j = 0; j < columns; j++)
                for (int comp = 0; comp < getCompNo(); comp++)
                    GMatrix.set(i, j, get(j, i));
        return GMatrix;
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

    private GMatrix dot(GMatrix gMatrix) {
        if (!isSquare() || columns == gMatrix.lines)
            throw new MatrixFormatException("determinant: not square matrix");
        GMatrix res = new GMatrix(gMatrix.columns, lines);
        for (int comp = 0; comp < getCompNo(); comp++) {
            res.setCompNo(comp);
            this.setCompNo(comp);
            for (int i = 0; i < gMatrix.columns; i++) {
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
    double determinant() {
        if (!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        int i, j, j1, j2;
        double det = 0;
        GMatrix GMatrix = null;

        if (lines < 1) { /* Error */
            throw new MatrixFormatException("<1 determinant");
        } else if (lines == 1) { /* Shouldn't get used */
            det = get(0, 0);
        } else if (lines == 2) {
            det = get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
        } else {
            det = 0;
            for (j1 = 0; j1 < lines; j1++) {
                GMatrix = new GMatrix(lines - 1);
                for (i = 1; i < lines; i++) {
                    j2 = 0;
                    for (j = 0; j < lines; j++) {
                        if (j == j1)
                            continue;
                        GMatrix.set(i - 1, j2, get(i, j));
                        j2++;
                    }
                }
                det += Math.pow(-1.0, j1 + 2.0) * get(0, j1) * GMatrix.determinant();
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
    public GMatrix CoFactor() {
        if (!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        int n = lines;
        GMatrix a = this;
        GMatrix b = new GMatrix(lines - 1);


        int i, j, ii, jj, i1, j1;
        double det;
        GMatrix c;

        c = new GMatrix(n - 1);

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

    /***
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

    public GMatrix op(GMatrix mat2, String op) {

        GMatrix res = new GMatrix(columns, lines);
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
}
