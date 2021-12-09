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

/*__
 * *
 * rotation, translation, scaling
 * <p>
 * Translation et rotation d'objets. (OBJET) @ ((TRANSLATION: V3D) (ROTATION :
 * P3D, M3D)) Homothétie (OBJET) * (CENTRE: P3D, FACTEUR: DOUBLE) L'ordre a de
 * l'importance.
 *
 * @author Manuel
 * @ (T) @ (R) * (H)
 */
public class MODObjet {

    private MODRotation rotation;
    private MODTranslation translation;
    private MODHomothetie homothetie;

    public MODHomothetie homothetie() {
        return homothetie;
    }
    /*
     public Representable place(Representable r)
     {
     return r.place(this);
     }
     * 
     */

    public void modificateurs(MODRotation r, MODTranslation t, MODHomothetie h) {
        this.rotation = r;
        this.translation = t;
        this.homothetie = h;
    }

    public MODRotation rotation() {
        return rotation;
    }

    public MODTranslation translation() {
        return translation;
    }
}
