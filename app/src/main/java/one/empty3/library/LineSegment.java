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

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.core.lighting.Infini;
import one.empty3.library.core.nurbs.CurveElem;
import one.empty3.library.core.nurbs.ParametricCurve;


/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         15 oct. 2011
 */
public class LineSegment extends ParametricCurve implements CurveElem {

    public double SMALL_NUM = Double.MIN_VALUE; // anything that avoids division
    private StructureMatrix<Point3D> origine = new StructureMatrix<>(0, Point3D.class);
    private StructureMatrix<Point3D> extremite = new StructureMatrix<>(0, Point3D.class);
    // overflow

    public LineSegment() {
        super();
        this.setOrigine(new Point3D());
        this.setExtremite(new Point3D());
    }

    // prodScalaire product (3D) which allows vector operations in arguments
    public LineSegment(Point3D p1, Point3D p2) {
        this();
        setOrigine(p1);
        setExtremite(p2);
    }

    public LineSegment(Point3D origin, Point3D extrem, ITexture texture) {
        this(origin, extrem);
        this.texture(texture);
        origin.texture(texture);
        extrem.texture(texture);
    }

    public Point3D calculerPoint3D(double d) {
        return origine.getElem().plus(extremite.getElem().moins(origine.getElem()).mult(d));
    }

    /*__
     * @return the extremite
     */
    public Point3D getExtremite() {
        return extremite.getElem();
    }

    /*__
     * @param extremite the extremite to set
     */
    public void setExtremite(Point3D extremite) {
        this.extremite.setElem(extremite);
    }

    /*__
     * @return the origine
     */
    public Point3D getOrigine() {
        return origine.getElem();
    }

    /*__
     * @param origine the origine to set
     */
    public void setOrigine(Point3D origine) {
        this.origine.setElem(origine);
    }

    // intersect3D_RayTriangle(): find the 3D intersection of a ray with a
    // triangle
    // Input: a ray R, and a triangle T
    // Output: *I = intersection point (when it exists)
    // Return: -1 = triangle is degenerate (a segment or point)
    // 0 = disjoint (no intersect)
    // 1 = intersect in unique point I1
    // 2 = are in the same plane
    private Representable intersect3D_RayTriangle(LineSegment ray, TRI T) {
        Point3D u, v, n = null; // triangle vectors
        Point3D dir, w0, w; // ray vectors
        double r, a, b; // params to calc ray-plane intersect

        Point3D I;
        // get triangle edge vectors and plane normal
        u = T.getSommet().getElem(1).moins(T.getSommet().getElem(0));
        v = T.getSommet().getElem(2).moins(T.getSommet().getElem(0));
        n = u.prodVect(v);
        if (n.equals(Point3D.O0)) // triangle is degenerate
        {
            return Infini.Default; // do not deal with this case
        }
        dir = ray.getOrigine().moins(ray.getExtremite()); // ray direction vector
        w0 = ray.getOrigine().moins(T.getSommet().getElem(0));
        a = -n.prodScalaire(w0);
        b = n.prodScalaire(dir);
        if (Math.abs(b) < SMALL_NUM) { // ray is parallel to triangle plane
            if (a == 0) // ray lies in triangle plane
            {
                return T;
            } else {
                return Infini.Default; // ray disjoint from plane
            }
        }

        // get intersect point of ray with triangle plane
        r = a / b;
        if (r < 0.0) // ray goes away from triangle
        {
            return Infini.Default; // => no intersect
        }        // for a segment, also test if (r > 1.0) => no intersect

        I = ray.getOrigine().plus(dir.mult(r)); // intersect point of ray and
        // plane

        // is I inside T?
        double uu, uv, vv, wu, wv, D;
        uu = u.prodScalaire(u);
        uv = u.prodScalaire(v);
        vv = v.prodScalaire(v);
        w = I.moins(T.getSommet().getElem(0));
        wu = w.prodScalaire(u);
        wv = w.prodScalaire(v);
        D = uv * uv - uu * vv;

        // get and test parametric coords
        double s, t;
        s = (uv * wv - vv * wu) / D;
        if (s < 0.0 || s > 1.0) // I is outside T
        {
            return Infini.Default;
        }
        t = (uv * wu - uu * wv) / D;
        if (t < 0.0 || (s + t) > 1.0) // I is outside T
        {
            return Infini.Default;
        }

        return I; // I is in T
    }

    public Representable intersection(TRI tri) {
        return intersect3D_RayTriangle(this, tri);
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Barycentre position() {
        throw new UnsupportedOperationException("Not supported yet."); // To
        // change
        // body
        // of
        // generated
        // methods,
        // choose
        // Tools
        // |
        // Templates.
    }


    @Override
    public boolean supporteTexture() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        Color c = Color.valueOf(texture.getColorAt(0.5, 0.5));
        return "Droite (\n\t" + origine.toString() + "\n\t"
                + extremite.toString() + "\n\t( " + c.red() + " , "
                + c.green() + " , " + c.blue() + " )\n)\n";
    }

    public int mesure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        return getExtremite().moins(getOrigine()).norme1();
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("origine/point origine", origine);
        getDeclaredDataStructure().put("extremite/point extremite", extremite);

    }

    public Double getLength() {
        return getOrigine().moins(getExtremite()).norme();
    }
}
