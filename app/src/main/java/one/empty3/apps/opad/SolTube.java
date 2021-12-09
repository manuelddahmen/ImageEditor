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

package one.empty3.apps.opad;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.RepresentableConteneur;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.tribase.TubulaireN2;

import java.util.Arrays;
import java.util.Iterator;

/*__
 * Created by manuel on 25-05-17.
 */
public class SolTube extends Terrain  {
    public SolTube(/*Game game*/) {
        setDessineMurs(false);
        TubulaireN2 n2 = new TubulaireN2();
        CourbeParametriquePolynomialeBezier courbeParametriquePolynomialeBezier;
        courbeParametriquePolynomialeBezier = new CourbeParametriquePolynomialeBezier();
        courbeParametriquePolynomialeBezier.getCoefficients().add(Point3D.O0);
        courbeParametriquePolynomialeBezier.getCoefficients().add(Point3D.X);
        courbeParametriquePolynomialeBezier.getCoefficients().add(Point3D.Y);
        courbeParametriquePolynomialeBezier.getCoefficients().add(Point3D.Z);
        n2.getSoulCurve().setElem(courbeParametriquePolynomialeBezier);
        n2.nbrAnneaux(40);
        n2.nbrRotations(20);
        //n2.diam(1);

        //n2.generate();

        ps = n2;

        SolPP sol = new SolPP(ps);
        RepresentableConteneur generateWire = sol.generateWire();

        Iterator<Representable> it = generateWire.iterator();

        while (it.hasNext()) {
            add(it.next());
        }
    }
}
