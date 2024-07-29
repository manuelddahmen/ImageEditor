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

    package one.empty3.library;

    import javaAnd.awt.Color;
    import one.empty3.library.core.nurbs.ParametricCurve;
    import one.empty3.library.core.nurbs.ParametricSurface;

    import org.jetbrains.annotations.NotNull;

    import java.util.ArrayList;
    import java.util.List;


    /*__
     * *

     */

    /**
     * The Point3D class represents a point in a three-dimensional space.
     * It provides methods to perform various operations on points.
     * Classe pour les éléments à trois coordonnées de type Double
     * Points, Vecteur 3D et calcul
     *
     * @author Manuel Dahmen
     */
    public class Point3D extends Representable {
        private static Double DISTANCE_MIN = 0.0001;
        private StructureMatrix<Double> point3DStructureMatrix;
        private double x, y, z;
        private Point3D normale;
        private final StructureMatrixListener setElem = new StructureMatrixListener() {
            @Override
            public void actionOnChange(Object oldValue, Object newValue, int dim, int posI, int posJ) {
                switch (dim) {
                    case 0:
                        break;
                    case 1:
                        if (posI >= 0 && posI < 3) {
                            set(posI, (double) newValue);
                        }
                        break;
                    case 2:
                        break;
                }
            }
        };
        private double tempx;
        private double tempy;
        private double tempz;

        public static void start() {
        }

        public static void end() {
        }

        public Point3D() {
            x = 0;
            y = 0;
            z = 0;
        }


        /**
         * Represents a constant point in 3D space.
         */
        public static final Point3D X = new Point3D(1d, 0d, 0d);

        /**
         * Represents a constant point in 3D space.
         */
        public static final Point3D Y = new Point3D(0d, 1d, 0d);

        /**
         * Represents a constant point in 3D space.
         */
        public static final Point3D Z = new Point3D(0d, 0d, 1d);

        /**
         * Represents a constant point in 3D space.
         */
        public static final Point3D O0 = new Point3D(0d, 0d, 0d);

        /**
         * Represents a constant point in 3D space.
         */
        public static final Point3D INFINI = new Point3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        /**
         * Represents a constant point in 3D space.
         */

        /**
         * Represents a three-dimensional point with coordinates (x, y, z) and an associated texture.
         *
         * @param x0 The x-coordinate of the point
         * @param y0 The y-coordinate of the point
         * @param z0 The z-coordinate of the point
         * @param t  The texture associated with the point
         */
        public Point3D(Double x0, Double y0, Double z0, ITexture t) {
            this(x0, y0, z0);
            texture(t);
        }


        /**
         * Represents a three-dimensional point with coordinates (x, y, z).
         *
         * @param x0 The x-coordinate of the point
         */
        public Point3D(double[] x0) {
            int i = 0;
            if (x0.length < 3)
                throw new UnsupportedOperationException("x0.length<3");
            x = x0[0];
            y = x0[1];
            z = x0[2];
        }


        /**
         * Represents a three-dimensional point with coordinates (x, y, z).
         *
         * @param x0 The x-coordinate of the point
         */
        public Point3D(Double... x0) {
            int i = 0;
            if (x0.length < 3)
                throw new UnsupportedOperationException("x0.length<3");
            x = x0[0];
            y = x0[1];
            z = x0[2];

        }

        /**
         * Represents a three-dimensional point with coordinates (x, y, z) and an associated texture.
         *
         * @param x0 The x-coordinate of the point
         * @param t  The texture associated with the point
         */
        public Point3D(Double[] x0, ITexture t) {
            int i = 0;
            if (x0.length < 3)
                throw new UnsupportedOperationException("x0.length<3");
            x = x0[0];
            y = x0[1];
            z = x0[2];
            texture(t);
        }


        /**
         * Creates a Point3D object with the specified length.
         *
         * @param n The length of the Point3D object
         */
        public Point3D(int n) {
            super();
            x = 0;
            y = 0;
            z = 0;

        }


        /**
         * Creates a new Point3D object by copying the coordinates of another Point3D object.
         *
         * @param p0 The Point3D object to be copied
         */
        public Point3D(@NotNull one.empty3.library.Point3D p0) {
            super();
            for (int i = 0; i < 3; i++)
                set(i, p0.get(i));
            texture(p0.texture);
        }

        /**
         * Represents a three-dimensional point with coordinates (x, y, z) initialized from a object.
         *
         * @param coordArr An object containing the coordinates of the point in the order [x, y, z].
         */
        public Point3D(StructureMatrix<Double> coordArr) {
            this(coordArr.getElem(0), coordArr.getElem(1), coordArr.getElem(2));
        }

        /**
         * Creates a new Point3D object with the given coordinates.
         *
         * @param a The x-coordinate of the point
         * @param b The y-coordinate of the point
         * @param c The z-coordinate of the point
         * @return A new Point3D object with the specified coordinates
         */
        public static Point3D n(Double a, Double b, Double c) {
            return new Point3D(a, b, c);
        }


        /**
         * Creates a new Point3D object with the same coordinates as the input point.
         *
         * @param p The Point3D object from which the coordinates will be copied
         * @return A new Point3D object with the same coordinates as p
         */
        public static Point3D n(Point3D p) {
            return new Point3D(p);
        }


        /**
         * Calculates the Euclidean distance between two 3D points.
         *
         * @param p1 The first 3D point.
         * @param p2 The second 3D point.
         * @return The Euclidean distance between p1 and p2.
         */
        public static Double distance(one.empty3.library.Point3D p1, one.empty3.library.Point3D p2) {
            double d = 0.0;
            for (int i = 0; i < p1.getCoordArr().getData1d().size(); i++)
                d += (p1.get(i) - p2.get(i)) * (p1.get(i) - p2.get(i));
            return Math.sqrt(d);
        }

        /**
         * Generates a random Point3D object with coordinates in the range [-0.5, 0.5].
         *
         * @param d The scaling factor to apply on the generated coordinates
         * @return A new Point3D object with random coordinates multiplied by the scaling factor
         */
        public static one.empty3.library.Point3D random(Double d) {
            return new one.empty3.library.Point3D(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).mult(d * 2);
        }

        /***
         * Generates a random Point3D object with coordinates in the range [-0.5, 0.5].
         *
         * @param d The scaling factor to apply on the generated coordinates
         * @param n The number of times the generated coordinates will be multiplied by the scaling factor
         * @return A new Point3D object with random coordinates multiplied by the scaling factor
         */
        public static one.empty3.library.Point3D random(Double d, int n) {

            return new one.empty3.library.Point3D(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).mult(d * 2);
        }

        /**
         * Generates a random Point3D object with coordinates in the range [-0.5, 0.5].
         *
         * @param d The scaling factor to apply on the generated coordinates
         * @return A new Point3D object with random coordinates multiplied by the scaling factor
         */
        public static one.empty3.library.Point3D r(Double d) {
            return random(d);
        }

        /**
         * Generates a random 3D point with coordinates in the range [-0.5, 0.5] multiplied by the scaling factor.
         *
         * @param d The scaling factor to apply on the generated coordinates
         * @return A new Point
         */
        public static Point3D random2(Double d) {

            return new Point3D(((Math.random() - 0.5) * 2 * d), ((Math.random() - 0.5) * 2 * d), ((Math.random() - 0.5) * 2 * d));
        }

        /**
         * Convert a Point3D object to a one-dimensional double array.
         *
         * @param norme1 The Point3D object containing the coordinates to be converted.
         * @param arr    The array to store the coordinates. If the length of the array is less than 3, a new array of length 3 will be created.
         * @return The one-dimensional double array containing the coordinates of the Point3D object.
         */
        public static double[] toArray1d(one.empty3.library.Point3D norme1, double[] arr) {
            double[] d = arr;

            if (arr.length < 3)
                d = new double[3];

            arr[0] = norme1.getX();
            arr[1] = norme1.getY();
            arr[2] = norme1.getZ();

            return arr;
        }

        /**
         * Generates a point on a circle given a parameter value.
         *
         * @param v The parameter value.
         * @return A Point3D object representing a point on the circle with coordinates (cos(v), sin(v), 0.0).
         */
        public static Point3D circle(double v) {
            return new Point3D(Math.cos(v), Math.sin(v), 0.0);
        }

        /**
         * Generates a point on a sphere given two parameter values.
         *
         * @param v  The first parameter value.
         * @param v1 The second parameter value.
         * @return A Point3D object representing a point on the sphere with coordinates (cos(v) * sin(v1), sin(v) * sin(v1), cos(v1)).
         */
        public static one.empty3.library.Point3D sphere(double v, double v1) {
            return new one.empty3.library.Point3D(Math.cos(v) * Math.sin(v1), Math.sin(v) * Math.sin(v1), Math.cos(v1));
        }

        @Override
        public Object clone() {
            return new Point3D(x, y, z);
        }

        /**
         * Retrieves the element at the specified index from the coordArr array.
         *
         * @param i the index of the element to retrieve
         * @return the element at the specified index
         */
        public Double get(int i) {
            // if(i>=0 && i<3 && coordArr.data1d.size()==3)
            switch (i) {
                case 0:
                    return x;
                case 1:
                    return y;
                case 2:
                    return z;
                default:
                    return null;
            }
       /* else
            try {
                throw new Throwable("point3D coordArr out of bounds or array dim error\nValues="+coordArr.toString()+"\nSize="+coordArr.data1d.size());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        return Double.NaN;*/
        }


        /**
         * Retrieves an array of doubles.
         *
         * @return a List of doubles representing the array of doubles.
         */
        public List<Double> getDoubleArray() {
            // LANG J16 return Arrays.stream(coordArr).toList();
            ArrayList<Double> list = new ArrayList<>();
            list.add(x);
            list.add(y);
            list.add(z);
            return list;
        }


        /**
         * Retrieves the y-coordinate value of a point.
         *
         * @return The y-coordinate value of the point as a Double.
         */
        public Double getY() {
            return y;
        }

        /**
         * Sets the Y coordinate of the point.
         *
         * @param x0 the new Y coordinate value
         */
        public void setY(Double x0) {
            y = x0;

        }

        /**
         * Retrieves the Z-coordinate from the coordinate array.
         *
         * @return the Z-coordinate value
         */
        public Double getZ() {
            return z;
        }

        /**
         * Sets the Z-coordinate value of the coordinate array.
         * The Z-coordinate value is stored at index 2 of the coordinate array.
         *
         * @param x0 the new Z-coordinate value to set
         */
        public void setZ(Double x0) {
            z = x0;

        }

        /**
         * Retrieves the X-coordinate value.
         *
         * @return the X-coordinate value
         */
        public Double getX() {
            return x;
        }

        /**
         * Sets the x-coordinate value.
         *
         * @param x0 the new x-coordinate value to be set
         */
        public void setX(Double x0) {
            x = x0;

        }

        /**
         * Calculates the vector sum of this point and the given point.
         *
         * @param p the point to be added to this point
         * @return a new Point3D representing the vector sum of this point and the given point
         */
        public Point3D plus(one.empty3.library.Point3D p) {
            Point3D p1 = new Point3D();
            for (int i = 0; i < 3; i++) {
                p1.set(i, get(i) + p.get(i));
            }

            return p1;
        }

        /**
         * Returns a new Point3D object that represents the difference between the current point and the provided point.
         * The difference is calculated by subtracting the corresponding coordinates of the provided point from the current point.
         *
         * @param p The point to subtract from the current point.
         * @return A new Point3D object representing the difference between the current point and the provided point.
         */
        public Point3D moins(Point3D p) {
            Point3D p1 = new Point3D();
            for (int i = 0; i < 3; i++)
                p1.set(i, get(i) - p.get(i));

            return p1;
        }


        /**
         * Multiplies each coordinate of the current Point3D object with the corresponding coordinate of the specified Point3D object.
         * Creates a new Point3D object with the multiplied coordinates and returns it.
         * The current Point3D object remains unchanged.
         *
         * @param p The Point3D object to multiply with the current Point3D object.
         * @return A new Point3D object with the multiplied coordinates.
         */
        public Point3D mult(Point3D p) {
            Point3D p1 = new Point3D();
            for (int i = 0; i < 3; i++)
                p1.set(i, get(i) * p.get(i));

            return p1;
        }

        /**
         * Multiplies the coordinates of two points element-wise.
         *
         * @param p1 The Point3D object to multiply with.
         * @return A new Point3D object with the multiplied coordinates.
         */
        public Point3D multDot(Point3D p1) {
            return mult(p1);
        }

        /**
         * Multiplies the coordinates of the current Point3D object by the given scaling factor.
         *
         * @param d The scaling factor to multiply the coordinates by.
         * @return A new Point3D object with the scaled coordinates.
         */
        public Point3D mult(Double d) {
            return mult((double) d);
        }

        /**
         * Multiplies each coordinate of the Point3D by the given value.
         *
         * @param d the value to multiply the coordinates by
         * @return a new Point3D object with the multiplied coordinates
         */
        public Point3D mult(double d) {

            Point3D p1 = new Point3D();
            for (int i = 0; i < 3; i++)
                p1.set(i, get(i) * d);

            return p1;
        }

        /**
         * Calculates the norm of the vector.
         *
         * @return the norm of the vector as a Double value
         */
        public Double norme() {
            double n = 0.0;
            for (int i = 0; i < 3; i++)
                n += get(i) * get(i);
            return Math.sqrt(n);
        }


        /**
         * Calculates the normalized vector of this Point3D object.
         *
         * @return The normalized Point3D object.
         */
        public Point3D norme1() {
            if (norme() == 0.0)
                return O0;
            return mult(1d / norme());
        }

        /**
         * Adds the given value to each coordinate of this point and returns a new Point3D object.
         *
         * @param d the value to be added to each coordinate
         * @return a new Point3D object with each coordinate incremented by the given value
         */
        public one.empty3.library.Point3D plus(Double d) {
            one.empty3.library.Point3D p = new Point3D();
            for (int i = 0; i < 3; i++)
                p.set(i, get(i) + d);
            return p;
        }

        /**
         * Calculates the dot product between this point and the given point.
         *
         * @param p2 the second point in the dot product calculation
         * @return the dot product value as a Double
         * @throws NullPointerException if p2 is null
         */
        public Double prodScalaire(one.empty3.library.Point3D p2) {
            double s = 0.0;
            if (p2 != null) {
                for (int i = 0; i < 3; i++)
                    s += get(i) * p2.get(i);
            } else
                throw new NullPointerException("Exception prodScalre p2==null");
            return s;
        }

        /**
         * Calculates the dot product between this point and the given point.
         *
         * @param p2 The other point used to calculate the dot product.
         * @return The dot product between this point and the given point.
         */
        public Double dot(one.empty3.library.Point3D p2) {
            return this.prodScalaire(p2);
        }


        /**
         * Calculates the cross product between this point and the given point.
         *
         * @param p1 The point to calculate the cross product with.
         * @return The result of the cross product as a new Point3D object.
         */
        public one.empty3.library.Point3D prodVect(one.empty3.library.Point3D p1) {
            return new one.empty3.library.Point3D(p1.getY() * getZ() + -p1.getZ() * getY(), p1.getZ()
                    * getX() - p1.getX() * getZ(), p1.getX() * getY() - p1.getY()
                    * getX());
        }

        /**
         * Sets the value of the element at the specified index in the coordArr array.
         *
         * @param i the index of the element to be set
         * @param d the new value for the element at the specified index
         */
        public void set(int i, Double d) {
            switch (i) {
                case 0:
                    x = d;

                    break;
                case 1:
                    y = d;
                    break;
                case 2:
                    z = d;
                    break;
                default:

            }
        }

        /**
         * Converts the coordinates and texture of the object to a formatted long string representation.
         *
         * @return The long string representation of the object.
         */
        public String toLongString() {
            //Color c = texture.toString();
            return "p ( \n\t(" + get(0) + " , " + get(1) + " , " + get(2) + " )\n\t("
                    + texture.toString()
                    + ")\n)\n";
        }

        /**
         * Returns a string representation of the object.
         * <p>
         * The returned string contains the coordinates of the point in
         * the format Point3D(x, y, z), where (x, y, z) are the values of the
         * coordinates.
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            String s = "\n\tPoint3D( " + (Double) (x) + " , " + (Double) (y) + " , " + (Double) (z) + " ) ";
            return s;
        }

        /**
         * Checks if the structure is drawn fast.
         *
         * @param z The z-buffer used for drawing the structure.
         * @return true if the structure is drawn fast, false otherwise.
         */
        @Override
        public boolean ISdrawStructureDrawFastIMPLEMENTED(ZBuffer z) {
            return super.ISdrawStructureDrawFastIMPLEMENTED(z); //To change body of generated methods, choose Tools | Templates.
        }

        /**
         * Draws the structure quickly using the given ZBuffer.
         *
         * @param z the ZBuffer object used for rendering the structure
         */
        @Override
        public void drawStructureDrawFast(ZBuffer z) {

            z.testDeep(this.toPoint3D(), Color.color(CFAST.getColorAt(0.5, 0.5)));

        }

        private Point3D toPoint3D() {
            return new Point3D(x, y, z);
        }

        /**
         * Returns a Point2D object representing the two-dimensional coordinates.
         *
         * @return a Point2D object representing the two-dimensional coordinates.
         */
        public Point2D get2D() {
            return new Point2D(get(0), get(1));
        }

        /**
         * Normalizes the vector by dividing each element by its norm.
         */
        public void normalize() {
            Double n = norme();
            for (int i = 0; i < 3; i++)
                set(i, get(i) / n);
        }

        /**
         * Returns a Point2D object representing the two-dimensional point without the z-coordinate.
         *
         * @return a Point2D object representing the two-dimensional point without the z-coordinate
         */
        public Point2D to2DwoZ() {
            return get2D();
        }

        /**
         * Calculates the square of the norm of a vector represented by an array of coordinates.
         *
         * @return The square of the norm of the vector.
         */
        public Double NormeCarree() {
            return x * x + y * y + z * z;
        }

        /***
         * Checks if this Representable intersects with the given Representable.
         *
         * @param r2 the Representable to check for intersection
         * @return the intersecting Representable if it exists, otherwise null
         */
        @Override
        public Representable intersects(Representable r2) {
            if (r2 instanceof one.empty3.library.Point3D) {
                one.empty3.library.Point3D p2 = (one.empty3.library.Point3D) (r2);
                return ((x == p2.get(0)) && (y == p2.get(1)) && (z == p2.get(2))) ? this : null;
            } else if (r2 instanceof LineSegment) {
                LineSegment sd = (LineSegment) r2;

            } else if (r2 instanceof TRI) {
                TRI tri = (TRI) r2;

            } else if (r2 instanceof ParametricSurface) {
            } else if (r2 instanceof ParametricCurve) {
            }
            throw new UnsupportedOperationException("Pas implémenté encore");
        }


        /**
         * Changes the coordinates and texture of this Point3D to the coordinates and texture of the given Point3D.
         *
         * @param src the Point3D object whose coordinates and texture will be copied
         * @return this Point3D object with updated coordinates and texture
         */
        public one.empty3.library.Point3D changeTo(one.empty3.library.Point3D src) {
            for (int i = 0; i < 3; i++)
                set(i, src.get(i));

            texture(src.texture());
            return this;
        }

        /**
         * Creates a new Point3D object with the given x, y, and z coordinates.
         *
         * @param x the x-coordinate of the point
         * @param y the y-coordinate of the point
         * @param z the z-coordinate of the point
         * @return a new Point3D object with the specified coordinates
         */
        public static one.empty3.library.Point3D n(double x, double y, double z) {
            return new one.empty3.library.Point3D(x, y, z);
        }

        /**
         * Returns the length of the vector.
         *
         * @return the length of the vector (norm)
         */
        public double getLength() {
            return norme();
        }


        /**
         * The P class is a subclass of the Point3D class. It represents a point in a three-dimensional coordinate system.
         *
         * <p>
         * The P class does not have any additional properties or methods and inherits all the properties and methods from the Point3D class.
         *
         * <p>
         * Example usage:
         * <pre>
         *     P point = new P();
         *     point.setX(2);
         *     point.setY(3);
         *     point.setZ(4);
         * </pre>
         */
        public class P extends one.empty3.library.Point3D {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof one.empty3.library.Point3D || o instanceof Point3D)) return false;

            one.empty3.library.Point3D point3D = (one.empty3.library.Point3D) o;
            ;
            for (int i = 0; i < 3; i++) {
                if (!(get(i) - (point3D.get(i)) < 1E-10))
                    return false;
                if (get(i).equals(Double.NaN) || point3D.get(i).equals(Double.NaN)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Checks if any element in the coordArr array is NaN.
         *
         * @return true if any element in coordArr is NaN, false otherwise
         */
        public boolean isAnyNaN() {
            for (int i = 0; i < 3; i++) {
                if (get(i).equals(Double.NaN)) {
                    return true;
                }
            }
            return false;
        }

        public void declareProperties() {
            super.declareProperties();
            getDeclaredDataStructure().put("coordArr/coordonnées", getCoordArr());
        }


        /**
         * Retrieves the coordinate array as a
         *
         * @return a containing the coordinate array
         */
        public StructureMatrix<Double> getCoordArr() {
            point3DStructureMatrix = new StructureMatrix<>(1, Double.class);
            point3DStructureMatrix.add(x);
            point3DStructureMatrix.add(y);
            point3DStructureMatrix.add(z);
            point3DStructureMatrix.addListener(setElem);

            return point3DStructureMatrix;
        }

        /**
         * Sets the coordinate array.
         *
         * @param coordArr the structure matrix representing the coordinates
         */
        public void setCoordArr(StructureMatrix<Double> coordArr) {
            for (int i = 0; i < 3; i++)
                set(i, coordArr.getElem(i));
        }

        public Point3D calculerPoint0dT(double t) {
            return this;
        }

    /*
    public Point3D statOp(Point3D p, char po, int length){
        switch(po) {
                case '+':
                for(int i=0; i<3; i++)
             coordArr[](i,coordArr.getElem(i)+p.get(i));
                break;
                case '-':
                    for(int i=0; i<3; i++)
              coordArr[]( coordArr.getElem(i)-p.get(i));
                break;
                    case '*':

        for(int i=0; i<3; i++)
              coordArr[]( i,  coordArr.getElem(i)*p.get(i));
                break;
                    case '/':

        for(int i=0; i<3; i++)
              coordArr[](i,
            coordArr.getElem(i)/p.get(i));
                break;
                case '.':
                double sum = 0.0;
                for(int i=0; i<3; i++)

                  sum += coordArr.getElem(i)*p.get(i);
                coordArr[](0, sum);
                break;
                }


        return this;
    }*/

        /**
         * Converts the current object to a Color object.
         *
         * @return The Color object representing the RGB values of the current object.
         */
        public Color toColor() {
            return (Color) Color.color((double) get(0), (double) get(1), (double) get(2));
        }

        /**
         * Converts a Color object to a Point3D object.
         *
         * @param color the Color object to convert
         * @return a Point3D object representing the color components
         */
        public static one.empty3.library.Point3D fromColor(Color color) {
            float[] colorComponents = color.getColorComponents(color);
            one.empty3.library.Point3D point3D = new one.empty3.library.Point3D(3);
            for (int i = 0; i < colorComponents.length; i++)
                point3D.set(i, (double) colorComponents[i]);
            return point3D;
        }

        /**
         * Rotates a Point3D around a reference point using a specified axis.
         * The rotation is performed in-place, meaning the original Point3D
         * object is modified.
         *
         * @param point3D the Point3D object to be rotated
         * @param ref     the reference point around which the rotation occurs
         * @param axe     the axis used for rotation
         */
        @Deprecated
        public void rotate(one.empty3.library.Point3D point3D, one.empty3.library.Point3D ref, one.empty3.library.Point3D axe) {
            if (one.empty3.library.Point3D.distance(point3D, ref) < one.empty3.library.Point3D.DISTANCE_MIN)
                return;
            one.empty3.library.Point3D moins = ref.moins(point3D);
            one.empty3.library.Point3D y = moins.prodVect(axe);
            one.empty3.library.Point3D x = y.prodVect(moins);
            getRotation().setElem(new Rotation(new Matrix33(new one.empty3.library.Point3D[]{x, y, moins}), ref));
            getRotation().getElem().rotation(ref);
        }

        /**
         * Sets the texture index for the object based on the given coordinates.
         *
         * @param tempx The X-coordinate for the texture index.
         * @param tempy The Y-coordinate for the texture index.
         * @param tempz The Z-coordinate for the texture index.
         */
        public void textureIndex(Double tempx, Double tempy, Double tempz) {
            this.tempx = tempx;
            this.tempy = tempy;
            this.tempz = tempz;
        }

        public Point3D getNormale() {
            return normale;
        }

        public void setNormale(Point3D p) {
            normale = p;
        }
    }

