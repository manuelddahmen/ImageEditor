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
package one.empty3.library;

import java.util.List;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         17 nov. 2011
 */
public class Matrix33 extends Representable {

    public static final Matrix33 XYZ;
    public static final Matrix33 YZX;
    public static final Matrix33 ZXY;
    public static final Matrix33 I;
    public static final Matrix33 O;

    static {
        O = new Matrix33(new Double[]{0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d});
        XYZ = new Matrix33(new Double[]{1d, 0d, 0d, 0d, 1d, 0d, 0d, 0d, 1d});
        YZX = new Matrix33(new Double[]{0d, 1d, 0d, 0d, 0d, 1d, 1d, 0d, 0d});
        ZXY = new Matrix33(new Double[]{0d, 0d, 1d, 1d, 0d, 0d, 0d, 1d, 0d});
        I = new Matrix33(new Double[]{1d, 0d, 0d, 0d, 1d, 0d, 0d, 0d, 1d});

    }

    private StructureMatrix<Double> d = new StructureMatrix<>(1, Double.class);

    public Matrix33(Matrix33 copy) {
        this();
        d.setAll(copy.getDoubleArray1e());
    }

    public Matrix33() {
        d = new StructureMatrix<>(1, Double.class);
        d.setAll(new Double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0});

    }

    public Matrix33(Double[] d) {
        if (d.length != 9) {
            System.out.println("Erreur dans Matrix33 . 9 éléments requis");
            throw new IndexOutOfBoundsException("Matrix33 9 " + d.length);
        }
        this.d.setAll(d);
    }

    public Matrix33(double[] d) {
        if (d.length != 9) {
            System.out.println("Erreur dans Matrix33 . 9 éléments requis");
            throw new IndexOutOfBoundsException("Matrix33 9 " + d.length);
        }
        Double[] D = new Double[9];
        for (int i = 0; i < 9; i++) {
            D[i] = d[i];
        }
        this.d.setAll(D);
    }

    public Matrix33(Point3D[] p) {
        this();
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                d.setElem(p[i].get(j), i * 3 + j);
            }
        }
    }

    public static Matrix33 rot(double a, double b) {
        return new Matrix33(
                new Double[]{
                        Math.cos(a), Math.sin(b), 0d,
                        -Math.sin(a), Math.cos(b), 0d,
                        0d, 0d, 1d

                }
        );
    }

    public static Matrix33 rotationX(double a) {
        return new Matrix33(
                new Double[]{1d, 0d, 0d,
                        Math.cos(a), -Math.sin(a), 0d,
                        Math.sin(a), Math.cos(a), 0d}).tild();
    }

    public static Matrix33 rotationY(double a) {
        return new Matrix33(
                new Double[]{Math.cos(a), 0d, Math.sin(a),
                        0d, 1d, 0
                        - Math.sin(a), 0d, Math.cos(a)}).tild();
    }

    public static Matrix33 rotationZ(double a) {
        return new Matrix33(
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

    public Matrix33 inverse() {
        return this;
    }

    public Matrix33 mult(Matrix33 mult) {
        Matrix33 r = new Matrix33();
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

    public Matrix33 mult(double f) {
        Matrix33 mres = new Matrix33(this);

        for (int i = 0; i < d.getData1d().size(); i++) {
            Double value = mres.d.getElem(i);
            mres.d.setElem(value * f, i);
        }
        return mres;
    }

    public Matrix33 plus(Matrix33 m) {
        Matrix33 mres = new Matrix33(this);

        for (int i = 0; i < d.getData1d().size(); i++) {
            double value = mres.d.getElem(i);
            mres.d.setElem(d.getElem(i) + value);
        }
        return mres;
    }

    public Matrix33 moins(Matrix33 m) {
        Matrix33 mres = new Matrix33(this);

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

    public Matrix33 tild() {
        Matrix33 m = new Matrix33();
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

    public Matrix33 uniteH() {
        return this;
    }

    public Matrix33 uniteV() {
        return this;
    }

    public Matrix33 power(int n) {
        Matrix33 a = null; // RESULT
        if (n == 0) {
            a = Matrix33.I;
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
        return new Matrix33(a);
    }

    public Matrix33 pourcents(Matrix33 m, double pc) {
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
}
