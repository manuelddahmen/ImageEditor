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

package one.empty3.library.core.raytracer.tree;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

import java.util.HashMap;

/*__
 * Created by manuel on 05-02-17.
 */
public class MathExprParametricCurve extends ParametricCurve {
    /*__
     * t: parameter
     */
    private final String[] exprStringXyz;
    AlgebricTree[] algebricTree;

    public MathExprParametricCurve(String[] exprStringXyz) {
        this.exprStringXyz = exprStringXyz;
        algebricTree = new AlgebricTree[exprStringXyz.length];
        for (int i = 0; i < exprStringXyz.length; i++) {
            try {
                algebricTree[i] = new AlgebricTree(exprStringXyz[i]);
                algebricTree[i].construct();
            } catch (AlgebraicFormulaSyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    public Point3D calculerPoint3D(double t) {
        Point3D p = new Point3D();
        HashMap<String, Double> stringDoubleHashMap = new HashMap<>();
        stringDoubleHashMap.put("t", t);
        for (int i = 0; i < algebricTree.length; i++) {
            algebricTree[i].getParametersValues().putAll(stringDoubleHashMap);
            try {
                p.set(i, (Double) algebricTree[i].eval());
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
            } catch (AlgebraicFormulaSyntaxException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        throw new UnsupportedOperationException("pas encore implanté");

    }

}
