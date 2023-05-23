/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.library;

import javaAnd.awt.Color;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.ArrayList;
import java.util.List;

/*__
 * *
 * <p>
 * Classe pour les éléments à trois coordonnées de type Double
 * Points, Vecteur 3D et calcul
 *
 * @author Manuel Dahmen
 */
/*__
 * *
 * <p>
 * Classe pour les éléments à trois coordonnées de type Double
 * Points, Vecteur 3D et calcul
 *
 * @author Manuel Dahmen
 */public class Point3D extends Representable {
    private Double tempz;
    private Double tempy;
    private Double tempx;
    private StructureMatrix<Double> point3DStructureMatrix;
    /*private final StructureMatrixListener setElem = new StructureMatrixListener() {
        @Override
        public void actionOnChange(Object oldValue, Object newValue, int dim, int posI, int posJ) {
            switch (dim) {
                case 0:
                    break;
                case 1:
                    if(posI>=0 && posI<3) {
                        set(posI, (double)newValue);
                    }
                    break;
                case 2:
                    break;
            }
        }
    };*/

    public static void start() {
    }

    public static void end() {
    }

    public Point3D() {
        super();
    }

    /*__
     * *
     * axe X vector
     */
    public static final Point3D X = new Point3D(1d, 0d, 0d);
    /*__
     * *
     * axe Y vector
     */
    public static final Point3D Y = new Point3D(0d, 1d, 0d);
    /*__
     * *
     * axe Z vector
     */
    public static final Point3D Z = new Point3D(0d, 0d, 1d);
    /*__
     * *
     * O0 origin
     */
    public static final Point3D O0 = new Point3D(0d, 0d, 0d);
    /*__
     * *
     * Point "Infinite" limite pour Z-Buffer
     */
    public static final Point3D INFINI = new Point3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    /*__
     * *
     * Coordonnées (coordArr,y,z) du point
     */
    //final StructureMatrix<Double> coordArr = new StructureMatrix<>(1, Double.class);
    final Double[] coordArr = new Double[3];

    {
        coordArr[0]=0d;
        coordArr[1]=0d;
        coordArr[2]=0d;

    }
    /*__
     * *
     * Pour le tracé de surface normale au point
     */
    protected Point3D normale;

    /*__
     * *
     * id
     */
    /*__
     * *
     *
     * @param x0 coordArr-coordonnée
     * @param y0 y-coordonnée
     * @param z0 z-coordonnée
     */
    public Point3D(Double x0, Double y0, Double z0, ITexture t) {
        this(x0, y0, z0);
        texture(t);
    }

    /*__
     * *
     * Initialise à partir d'un vecteur
     *
     * @param x0 coordonnées (>3)
     */
    public Point3D(double[] x0) {
        int i = 0;
        if(x0.length<3)
            throw new UnsupportedOperationException("x0.length<3");
        for (double d : x0) {
            coordArr[i]=d;
            i++;
        }
    }

    /*__
     * *
     * Initialise à partir d'un vecteur
     *
     * @param x0 coordonnées (>3)
     */
    public Point3D(Double... x0) {
        int i = 0;
        if(x0.length<3)
            throw new UnsupportedOperationException("x0.length<3");
        for (Double d : x0) {
            coordArr[i]=d;
            i++;
        }
    }

    public Point3D(Double[] x0, ITexture t) {
        int i = 0;
        if(x0.length<3)
            throw new UnsupportedOperationException("x0.length<3");
        for (Double d : x0) {
            coordArr[i] = d;
            i++;
        }
        texture(t);
    }

    /*__
     *
     *
     * @param p0 point à copier
     */
    public Point3D(int n) {
        super();
        for (int i = 0; i < n; i++)
            coordArr[i] = 0.0;
    }

    /*__
     *
     *
     * @param p0 point à copier
     */
    public Point3D(Point3D p0) {
        super();
        for (int i = 0; i < 3; i++)
            coordArr[i] = p0.get(i);
        texture(p0.texture);
    }

    public Point3D(StructureMatrix<Double> coordArr) {
        this(coordArr.getElem(0), coordArr.getElem(1), coordArr.getElem(2));
    }

    public static Point3D n(Double a, Double b, Double c) {
        return new Point3D(a, b, c);
    }


    public static Point3D n(Point3D p) {
        return new Point3D(p);
    }

    /*__
     * *
     * Distance cartésienne entre 2 points
     *
     * @param p1 Point1
     * @param p2 Point2
     * @return
     */
    public static Double distance(Point3D p1, Point3D p2) {
        double d = 0.0;
        for (int i = 0; i < p1.getCoordArr().getData1d().size(); i++)
            d += (p1.get(i) - p2.get(i)) * (p1.get(i) - p2.get(i));
        return Math.sqrt(d);
    }
    /*
    public static Double distance(Point3D p1, Point3D p2, Fct1D_1D f) {
        double d = 0.0;
        for (int i = 0; i < p1.getCoordArr().getData1d().size(); i++)
            d += (f.result(p1.get(i) - p2.get(i))) * f.result((p1.get(i) - p2.get(i)));
        return Math.sqrt(d);
    }
*/
    public static Point3D random(Double d) {
        return new Point3D(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).mult(d * 2);
    }

    public static Point3D random(Double d, int n) {

        return new Point3D(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).mult(d * 2);
    }

    public static Point3D r(Double d) {
        return random(d);
    }

    public static Point3D random2(Double d) {

        return new Point3D(((Math.random() - 0.5) * 2 * d), ((Math.random() - 0.5) * 2 * d), ((Math.random() - 0.5) * 2 * d));
    }

    public static double[] toArray1d(Point3D norme1, double [] arr) {
        double[] d = arr;

        if(arr.length<3)
            d = new double[3];

        arr[0] = norme1.coordArr[0];
        arr[1] = norme1.coordArr[1];
        arr[2] = norme1.coordArr[2];

        return arr;
    }

    @Override
    public Object clone() {
        return new Point3D(coordArr);
    }

    public Double get(int i) {
        // if(i>=0 && i<3 && coordArr.data1d.size()==3)
        return coordArr[i];
       /* else
            try {
                throw new Throwable("point3D coordArr out of bounds or array dim error\nValues="+coordArr.toString()+"\nSize="+coordArr.data1d.size());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        return Double.NaN;*/
    }


    public List<Double> getDoubleArray() {
        // LANG J16 return Arrays.stream(coordArr).toList();
        ArrayList<Double> list = new ArrayList<>();
        list.add(coordArr[0]);
        list.add(coordArr[1]);
        list.add(coordArr[2]);
        return list;
    }


    public Point3D getNormale() {
        return normale;
    }

    public void setNormale(Point3D normale) {
        this.normale = normale;
    }

    public Double getY() {
        return coordArr[1];
    }

    public void setY(Double x0) {
        coordArr[1] = x0;

    }

    public Double getZ() {
        return coordArr[2];
    }

    public void setZ(Double x0) {
        coordArr[2] = x0;

    }

    public Double getX() {
        return coordArr[0];
    }

    public void setX(Double x0) {
        coordArr[0] = x0;

    }


    public Point3D plus(Point3D p) {
        Point3D p1 = new Point3D(this);
        for (int i = 0; i < coordArr.length; i++) {
            p1.set(i, get(i) + p.get(i));
        }

        return p1;
    }

    public Point3D moins(Point3D p) {
        Point3D p1 = new Point3D(this);
        for (int i = 0; i < coordArr.length; i++)
            p1.set(i, get(i) - p.get(i));

        return p1;
    }

    /*__
     * *
     * Multiplication
     *
     * @param xFactor facteur
     * @return
     */

    public Point3D mult(Point3D p) {
        Point3D p1 = new Point3D(this);
        for (int i = 0; i < coordArr.length; i++)
            p1.set(i, get(i) * p.get(i));

        return p1;
    }
    public Point3D multDot(Point3D p1) {
        return mult(p1);
    }
    public Point3D mult(Double d) {

        Point3D p1 = new Point3D(this);
        for (int i = 0; i < coordArr.length; i++)
            p1.set(i, get(i) * d);

        return p1;
    }

    public Point3D mult(double d) {

        Point3D p1 = new Point3D(this);
        for (int i = 0; i < coordArr.length; i++)
            p1.set(i, get(i) * d);

        return p1;
    }

    /*
     public Vec  mult(Vec point3D) {
        return Matrix33.YZX.mult(Matrix33.ZXY.mult(Matrix33.XYZ.mult(point3D)));
       }*/
    /*__
     * *
     * norme d'un vecteur (distance du point à l'origine)
     *
     * @return
     */
    public Double norme() {
        double n = 0.0;
        for (int i = 0; i < coordArr.length; i++)
            n += get(i) * get(i);
        return Math.sqrt(n);
    }

    /*__
     * *
     * "direction" (norme1)
     *
     * @return Vecteur normalisé à 1
     */
    public Point3D norme1() {
        if(norme()==0.0)
            return Point3D.O0;
        return mult(1 / norme());
    }

    /*__
     * *
     * Ajoute @param i à chaque coordonnée
     *
     * @param i
     * @return
     */
    public Point3D plus(Double d) {
        Point3D p = new Point3D(this);
        for (int i = 0; i < coordArr.length; i++)
            p.set(i, get(i) + d);
        return p;
    }


    /*__
     * *
     * Produit scalaire
     *
     * @param p2
     * @return
     */
    public Double prodScalaire(Point3D p2) {
        double s = 0.0;
        if (p2 != null) {
            for (int i = 0; i < coordArr.length; i++)
                s += coordArr[i] * p2.get(i);
        } else
            throw new NullPointerException("Exception prodScalre p2==null");
        return s;
    }

    /*__/*__
     * *
     * Produit scalaire
     *
     * @param p2
     * @return
     */
    public Double dot(Point3D p2) {
        return this.prodScalaire(p2);
    }

    /* *
     * produit vectoriel
     *
     * @param p1
     * @return
     */
    public Point3D prodVect(Point3D p1) {
        return new Point3D(p1.getY() * getZ() + -p1.getZ() * getY(), p1.getZ()
                * getX() - p1.getX() * getZ(), p1.getX() * getY() - p1.getY()
                * getX());
    }

    public void set(int i, Double d) {
        coordArr[i] = d;

    }

    public String toLongString() {
        //Color c = texture.toString();
        return "p ( \n\t(" + coordArr[0] + " , " + coordArr[1] + " , " + coordArr[2]+ " )\n\t("
                + texture.toString()
                + ")\n)\n";
    }

    @Override
    public String toString() {
        String s = "\n\tp3( " + (Double) (coordArr[0]) + " , " + (Double) (coordArr[1]) + " , " + (Double) (coordArr[2]) + " ) ";
        return s;
    }

    @Override
    public boolean ISdrawStructureDrawFastIMPLEMENTED(ZBuffer z) {
        return super.ISdrawStructureDrawFastIMPLEMENTED(z); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawStructureDrawFast(ZBuffer z) {

//        z.testDeep(this, new Color(CFAST.getColorAt(0.5, 0.5)));

    }

    public Point2D get2D() {
        return new Point2D(coordArr[0], coordArr[1]);
    }

    public void normalize() {
        Double n = norme();
        for (int i = 0; i < coordArr.length; i++)
            set(i, get(i) / n);
    }

    public Point2D to2DwoZ() {
        return get2D();
    }

    public Double NormeCarree() {
        return coordArr[0] * coordArr[0] + coordArr[1] * coordArr[1]
                + coordArr[2] * coordArr[2];
    }

    @Override
    public Representable intersects(Representable r2) {
        if (r2 instanceof Point3D) {
            Point3D p2 = (Point3D) (r2);
            return ((coordArr[0] == p2.get(0)) && (coordArr[1] == p2.get(1)) && (coordArr[2] == p2.get(2))) ? this : null;
        } else if (r2 instanceof LineSegment) {
            LineSegment sd = (LineSegment) r2;

        } else if (r2 instanceof TRI) {
            TRI tri = (TRI) r2;

        } else if (r2 instanceof ParametricSurface) {
        } else if (r2 instanceof ParametricCurve) {
        }
        throw new UnsupportedOperationException("Pas implémenté encore");
    }


    public Point3D changeTo(Point3D dst) {
        for (int i = 0; i < 3; i++)
            this.coordArr[i] = dst.coordArr[i];

        texture(dst.texture());
        return this;
    }

    public static Point3D n(double x, double y, double z) {
        return new Point3D(x, y, z);
    }

    public double getLength() {
        return norme();
    }

    public void textureIndex(Double tempx, Double tempy, Double tempz) {
        this.tempx = tempx;
        this.tempy = tempy;
        this.tempz = tempz;
    }


    public class P extends Point3D {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;

        Point3D point3D = (Point3D) o;

        if (point3D.coordArr.length !=
                this.coordArr.length)
            return false;
        for (int i = 0; i < 3; i++) {
            if (!(coordArr[i] - (point3D.get(i)) < 1E-10))
                return false;
            if(coordArr[i].equals(Double.NaN)|| point3D.coordArr[i].equals(Double.NaN)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnyNaN() {
        for (int i = 0; i < 3; i++) {
            if (coordArr[i].equals(Double.NaN)) {
                return true;
            }
        }
        return false;
    }
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("coordArr/coordonnées", getCoordArr());
    }

    /*
        public Double get(int i) {
            return i<coordArr.data1d.size()?coordArr.getElem(i):Double.NaN;
        }*/
    public StructureMatrix<Double> getCoordArr() {
        point3DStructureMatrix = new StructureMatrix<>(1, Double.class);
        point3DStructureMatrix.add(coordArr[0]);
        point3DStructureMatrix.add(coordArr[1]);
        point3DStructureMatrix.add(coordArr[2]);
  //      point3DStructureMatrix.addListener(setElem);

        return point3DStructureMatrix;
    }

    public void setCoordArr(StructureMatrix<Double> coordArr) {
        for(int i=0; i<3; i++)
            this.coordArr[i] = coordArr.getElem(i);
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

    public android.graphics.Color toColor() {
        return android.graphics.Color.valueOf
                ((float)(double)(get(0)), (float)(double)(get(1)), (float)(double)(get(2)));
    }
    public static Point3D fromColor(Color color) {
        float[] colorComponents = new float[4];
        colorComponents = color.getComponents(colorComponents);
        Point3D point3D = new Point3D(3);
        for(int i=0; i< colorComponents.length; i++)
            point3D.set(i, (double)colorComponents[i]);
        return point3D;
    }

    public Double getTempz() {
        return tempz;
    }

    public void setTempz(Double tempz) {
        this.tempz = tempz;
    }

    public Double getTempy() {
        return tempy;
    }

    public void setTempy(Double tempy) {
        this.tempy = tempy;
    }

    public Double getTempx() {
        return tempx;
    }

    public void setTempx(Double tempx) {
        this.tempx = tempx;
    }
}
