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

import android.os.Build;

import androidx.annotation.RequiresApi;

import android.graphics.Color;

import one.empty3.library.core.raytracer.RtIntersectInfo;
import one.empty3.library.core.raytracer.RtRay;

public class TRI extends Representable {

    private StructureMatrix<Point3D> sommet = new StructureMatrix<>(1, Point3D.class);

    public TRI() {
        super();
        sommet.setElem(Point3D.X, 0);
        sommet.setElem(Point3D.Y, 1);
        sommet.setElem(Point3D.O0, 2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TRI(Point3D coordPoint3D, Point3D coordPoint3D0, Point3D coordPoint3D1) {
        this(coordPoint3D, coordPoint3D0, coordPoint3D1, Color.valueOf(0, 0, 1));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TRI(Point3D point3d, Point3D point3d2, Point3D point3d3,
               android.graphics.Color red) {
        sommet.setElem(point3d, 0);
        sommet.setElem(point3d2, 1);
        sommet.setElem(point3d3, 2);
        this.texture(new TextureCol(red));
    }

    public TRI(Point3D point3d, Point3D point3d2, Point3D point3d3,
               ITexture red) {
        sommet.setElem(point3d, 0);
        sommet.setElem(point3d2, 1);
        sommet.setElem(point3d3, 2);
        this.texture = red;
    }

    public TRI(Point3D[] s, Color c) {
        this(s[0], s[1], s[2], c);
    }

    public TRI(Point3D[] s, ITexture c) {
        this(s[0], s[1], s[2], c);
    }


    public StructureMatrix<Point3D> getSommet() {
        return sommet;
    }

    public void setSommet(Point3D[] sommet) {
        this.sommet.setAll(sommet);
    }

    public Point3D normale() {
        return sommet.getElem(2).moins(sommet.getElem(0)).prodVect(sommet.getElem(1).moins(sommet.getElem(0)))
                .norme1();
    }


    public void setCouleur(Color couleur) {
        this.texture(new TextureCol(couleur));

    }

    @Override
    public String toString() {
        String t = "tri (";
        for (int i = 0; i < 3; i++) {
            t += "\n\t\t(" + sommet.getElem(0).getX() + ", " + sommet.getElem(1).getY() + ", " + sommet.getElem(2).getZ() + "), ";
        }
        return t + "\n\t\t(" + texture.toString() + ")\n\t)\n";
    }

    @Override
    public Intersects.Intersection intersects(RtRay ray, RtIntersectInfo cii) {
        // TODO Implements
        return null;
    }

    public Point3D getCentre() {
        return getSommet().getElem(0).plus(getSommet().getElem(1)).plus(getSommet().getElem(2).mult(1 / 3d));
    }

    public int intersects(TRI tri2) {


        return TRI_Collide.tr_tri_intersect3D(getCentre(), getSommet().getElem(0), getSommet().getElem(1),
                tri2.getCentre(), tri2.getSommet().getElem(0), tri2.getSommet().getElem(1));
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("sommet/points sommets du triangle", sommet);

    }
}
