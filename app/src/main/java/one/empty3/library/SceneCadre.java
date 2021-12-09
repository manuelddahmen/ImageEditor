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

import java.io.Serializable;

/*__
 * cadre à la scène, avec possibilité d'élargir le cadre ou de ne pas en tenir
 * compte
 *
 * @author DAHMEN Manuel
 *         <p>
 *         dev
 * @date 24-mars-2012
 */
public class SceneCadre implements Serializable {

    /*__
     *
     */
    private static final long serialVersionUID = -2001787693050570304L;
    private Point3D[] points = new Point3D[4];
    private boolean cadre = false;
    private boolean exterieur = false;

    public SceneCadre() {
        cadre = false;
    }

    public SceneCadre(Point3D[] p) {
        this.points = p;
        cadre = true;
    }

    public Point3D get(int i) {
        return points[i];
    }

    public boolean isCadre() {
        return cadre;
    }

    public boolean isExterieur() {
        return exterieur;
    }

    public void setExterieur(boolean b) {
        this.exterieur = b;
    }

    public void set(int i, Point3D p) {
        this.points[i] = p;
    }

}
