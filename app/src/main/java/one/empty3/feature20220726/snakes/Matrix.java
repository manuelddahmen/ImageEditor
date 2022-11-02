/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*

 Vous êtes libre de :

 */
package one.empty3.feature20220726.snakes;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.StructureMatrix;

import java.util.List;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         17 nov. 2011
 */
public class Matrix extends Representable {

    public static final Matrix XYZ;
    public static final Matrix YZX;
    public static final Matrix ZXY;
    public static final Matrix I;
    public static final Matrix O;

    static {
        O = new Matrix(new Double[]{0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d});
        XYZ = new Matrix(new Double[]{1d, 0d, 0d, 0d, 1d, 0d, 0d, 0d, 1d});
        YZX = new Matrix(new Double[]{0d, 1d, 0d, 0d, 0d, 1d, 1d, 0d, 0d});
        ZXY = new Matrix(new Double[]{0d, 0d, 1d, 1d, 0d, 0d, 0d, 1d, 0d});
        I = new Matrix(new Double[]{1d, 0d, 0d, 0d, 1d, 0d, 0d, 0d, 1d});

    }

    private StructureMatrix<Double> d = new StructureMatrix<>(1, Double.class);
    private int dim1;
    private int dim2;

    public Matrix(Matrix copy) {
        this();
        d.setAll(copy.getDoubleArray1e());
    }

    public Matrix() {
        d = new StructureMatrix<>(1, Double.class);
        d.setAll(new Double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0});
        dim1 = dim2 = (int) Math.sqrt(d.getData1d().size());

    }

    public Matrix(Double[] d) {
        dim1 = dim2 = (int) Math.sqrt(d.length);
        if (d.length != 9) {
            System.out.println("Erreur dans Matrix33 . 9 éléments requis");
            throw new IndexOutOfBoundsException("Matrix33 9 " + d.length);
        }
        this.d.setAll(d);
    }

    public Matrix(double[] d) {
        if (d.length != 9) {
            System.out.println("Erreur dans Matrix33 . 9 éléments requis");
            throw new IndexOutOfBoundsException("Matrix33 9 " + d.length);
        }
        dim1 = dim2 = (int) Math.sqrt(d.length);
        Double[] D = new Double[9];
        for (int i = 0; i < 9; i++) {
            D[i] = d[i];
        }
        this.d.setAll(D);
    }

    public Matrix(Point3D[] p) {
        this();
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                d.setElem(p[i].get(j), i * 3 + j);
            }
        }
        dim1 = dim2 = (int) Math.sqrt(p.length);
    }

    public Matrix(int columns, int lines) {
        for (int i = 0; i < columns * lines; i++) {
            d.setElem(0.0, i);
        }
    }

    public static Matrix rot(double a, double b) {
        return new Matrix(
                new Double[]{
                        Math.cos(a), Math.sin(b), 0d,
                        -Math.sin(a), Math.cos(b), 0d,
                        0d, 0d, 1d

                }
        );
    }

    public static Matrix rotationX(double a) {
        return new Matrix(
                new Double[]{1d, 0d, 0d,
                        Math.cos(a), -Math.sin(a), 0d,
                        Math.sin(a), Math.cos(a), 0d}).tild();
    }

    public static Matrix rotationY(double a) {
        return new Matrix(
                new Double[]{Math.cos(a), 0d, Math.sin(a),
                        0d, 1d, 0
                        - Math.sin(a), 0d, Math.cos(a)}).tild();
    }

    public static Matrix rotationZ(double a) {
        return new Matrix(
                new Double[]{Math.cos(a), -Math.sin(a), 0d,
                        Math.sin(a), Math.cos(a), 0d,
                        0d, 0d, 1d}).tild();
    }

    public double get(int i, int j) {
        return d.getElem(i * 3 + j);
    }

    public Double[] getDoubleArray1e() {
        Double[] d2 = new Double[9];
        for (int i = 0; i < 9; i++)
            d2[i] = d.getElem(i);
        return d2;
    }

    public Double[][] getDoubleArray() {
        Double[][] d2 = new Double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                d2[i][j] = get(i, j);
            }
        }
        return d2;
    }

    public Matrix inverse() {
        return this;
    }

    public Matrix mult(Matrix mult) {
        Matrix r = new Matrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                double d0 = 0;
                for (int k = 0; k < 3; k++) {
                    d0 += get(i, k) * get(k, j);
                }
                r.set(i, j, d0);
            }
        }
        return r;
    }

    public Point3D mult(Point3D p) {
        return rotation(p);
    }

    public Matrix mult(double f) {
        Matrix mres = new Matrix(this);

        for (int i = 0; i < d.getData1d().size(); i++) {
            Double value = mres.d.getElem(i);
            mres.d.setElem(value * f, i);
        }
        return mres;
    }

    public Matrix plus(Matrix m) {
        Matrix mres = new Matrix(this);

        for (int i = 0; i < d.getData1d().size(); i++) {
            double value = mres.d.getElem(i);
            mres.d.setElem(d.getElem(i) + value);
        }
        return mres;
    }

    public Matrix moins(Matrix m) {
        Matrix mres = new Matrix(this);

        for (int i = 0; i < d.getData1d().size(); i++) {
            double value = mres.d.getElem(i);
            mres.d.setElem(d.getElem(i) - value);
        }
        return mres;
    }

    public Point3D rotation(Point3D p) {
        Point3D pa = new Point3D();
        if (p != null) {
            for (int i = 0; i < 3; i++) {
                double d0 = 0;
                for (int j = 0; j < 3; j++) {
                    d0 += this.get(i, j) * p.get(j);
                }
                pa.set(i, d0);
            }
            return pa;
        } else return Point3D.O0;
    }

    public void set(int i, int j, double d0) {
        d.setElem(d0, i * 3 + j);
    }

    public void set(int i, Point3D p) {
        for (int j = 0; j < 3; j++) {
            set(i, j, p.get(j));
        }
    }

    public Matrix tild() {
        Matrix m = new Matrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m.set(i, j, get(j, i));
            }
        }
        return m;
    }

    @Override
    public String toString() {
        String str = "m (\n";

        for (int i = 0; i < d.getData1d().size(); i++) {
            str += (i % 3 == 0 ? "\n\t" : " ") + d.getElem(i);
        }

        str += "\n)\n";
        return str;
    }

    public Matrix uniteH() {
        return this;
    }

    public Matrix uniteV() {
        return this;
    }

    public Matrix power(int n) {
        Matrix a = null; // RESULT
        if (n == 0) {
            a = Matrix.I;
        } else if (n == 1)
            a = this;
        else if (n > 1) {
            a = this;
            for (int i = 2; i <= n; i++)
                a = a.mult(this);
        } else if (n == -1) {
            a = inverse();
        } else if (n < -1) {
            a = inverse();
            for (int i = -1; i >= n; i--)
                a = a.mult(this);
        }
        return new Matrix(a);
    }

    public Matrix pourcents(Matrix m, double pc) {
        return mult(1 - pc).plus(m.mult(pc));
    }

    public List<Double> getDoubles() {
        return d.getData1d();
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("d/3x3 matrix", d);

    }

    public StructureMatrix<Double> getD() {
        return d;
    }

    public void setD(Double[] d1) {
        d = new StructureMatrix<>(1, Double.class);
        d.setAll(d1);
    }


    public Point3D[] getColVectors() {
        Point3D[] colVectors = new Point3D[3];
        for (int c = 0; c < 3; c++) {
            Point3D p = new Point3D(d.getElem(c), d.getElem(3 + c), d.getElem(6 + c));
            colVectors[c] = p;
        }
        return colVectors;
    }

    public Point3D[] getRowVectors() {
        Point3D[] rowVectors = new Point3D[3];
        for (int l = 0; l < 3; l++) {
            Point3D p = new Point3D(d.getElem(l * 3), d.getElem(l * 3 + 1), d.getElem(l * 3 + 2));
            rowVectors[l] = p;
        }
        return rowVectors;
    }

    public double determinant() {
        double det = 0.0;
        for (int i = 0; i < getDim1(); i++)
            for (int j = 0; j < getDim2(); j++) {
                det += cofactor(i, j).determinant() * (d.getElem(j * getDim1() + j));
            }
        return determinant();
    }

    private Matrix subMatrice(int i, int j) {
        return null;
    }

    private Matrix cofactor(int i, int j) {
        if (getDim1() == 2 && getDim2() == 2)
            return new Matrix(new double[]{
                    get((i - 1) % 3, ((j - 1) % 3)),
                    get((i - 1) % 3, ((j + 1) % 3)),
                    get((i + 1) % 3, ((j + 1) % 3)),
                    get((i - 1) % 3, ((j + 1) % 3))});
        else if (dim1 == 1 && dim2 == 1) {
            return new Matrix(new double[]{get(0, 0)});
        }
        Matrix matrix33 = new Matrix(dim1 - 1, dim2 - 1);
        int row = 0, column = 0;
        for (int r = 0; r < dim1; r++) {
            for (int c = 0; c < dim2; c++) {
                if (r != i && c != j) {
                    matrix33.set(column, row, d.getElem(dim1 - 1) * row + column);
                } else {

                }
                column++;
            }
            column = 0;
            row++;
        }
        return matrix33;
    }

    private int getDim1() {
        return dim1;
    }

    private int getDim2() {
        return dim2;
    }

}
