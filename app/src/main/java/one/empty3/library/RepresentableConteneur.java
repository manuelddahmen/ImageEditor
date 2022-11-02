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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

import java.util.*;

/*__
 * @author Atelier
 */
public class RepresentableConteneur extends Representable implements IMovable, IScalable {

    private StructureMatrix<Representable> re = new StructureMatrix(1, Representable.class);

    public RepresentableConteneur() {
    }


    public synchronized void add(Representable r) {
        re.add(1, r);
    }

    public synchronized void clear() {
        re.getData1d().clear();
    }


    public Iterator<Representable> iterator() {
        return re.getData1d().iterator();
    }

    public synchronized void remove(Representable r2) {
        re.getData1d().remove(r2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("conteneur (\n\n");

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            sb.append(next.toString());
        }

        sb.append("\n\n)\n\n");

        return sb.toString();
    }

    @Override
    public void moveAdd(Point3D add) {
        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            if (next instanceof IMovable) {
                ((IMovable) next).moveAdd(add);
            }
        }

    }

    @Override
    public void moveTo(Point3D to) {

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();
            if (next instanceof IMovable) {
                ((IMovable) next).moveTo(to);
            }

        }
    }

    @Override
    public void scale(Point3D center, double scale) {

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            if (next instanceof IScalable) {
                ((IScalable) next).scale(center, scale);
            }
        }
    }

    @Override
    public void scale(double scale) {

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            if (next instanceof IScalable) {
                ((IScalable) next).scale(scale);
            }
        }
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("listRepresentable", re);
    }

    public List<Representable> getListRepresentable() {
        return re.getData1d();
    }

}
