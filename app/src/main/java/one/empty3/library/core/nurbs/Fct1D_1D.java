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

package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;

/*__
 * Created by manue on 28-05-19.
 */
public abstract class Fct1D_1D extends ParametricCurve {
    public class I extends Fct1D_1D {

        public I() {

        }

        @Override
        public double result(double input) {
            return input;
        }
    }

    public class Fx extends Fct1D_1D {

        private final double x;

        public Fx(double x) {
            this.x = x;
        }

        @Override
        public double result(double input) {
            return x;
        }
    }

    /*
        StructureMatrix<Fct1D_1D> predifinedFunctions = new StructureMatrix<>(1);
        {
            predifinedFunctions.add(1, new I());
            predifinedFunctions.add(1, new Fx(0.0));
        }
    */
    public abstract double result(double input);

    @Override
    public Point3D calculerPoint3D(double t) {
        return new Point3D(t, result(t), 0d);
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
    }
}
