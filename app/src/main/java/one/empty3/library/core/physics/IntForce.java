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

package one.empty3.library.core.physics;

import one.empty3.library.*;

import java.util.List;

/*__
 * Created by manuel on 07-06-18.
 */
public interface IntForce {
    void configurer(Bille[] courant);

    Point3D centreMasse();

    Point3D attractionRepulsion(Bille other, Bille p);

    Point3D frottement(Bille p);

    Point3D force(int ind);

    Point3D acc(int ind);

    Point3D vitesse(int ind);

    Point3D position(int ind);

    void calculer();

    double getDistMax();

    void setDistMax(double distMax);

    double getDistMin();

    void setDistMin(double distMin);

    List<Bille> getCourant();

    void setCourant(Bille[] courant);

    List<Bille> getNext();

    void setNext(List<Bille> next);

    double getDt();

    void setDt(double dt);

    double getG();

    void setG(double g);

    double getDistMinFusion();

    void setDistMinFusion(double distMinFusion);

    boolean isFusion();

    void setFusion(boolean fusion);
}
