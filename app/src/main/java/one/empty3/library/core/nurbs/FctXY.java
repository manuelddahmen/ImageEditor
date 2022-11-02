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

import one.empty3.library.StructureMatrix;
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.raytracer.tree.AlgebricTree;
import one.empty3.library.core.raytracer.tree.TreeNodeEvalException;

/*__
 * Created by manue on 28-05-19.
 */
public class FctXY extends Fct1D_1D {
    private StructureMatrix<String> formulaX = new StructureMatrix<>(0, String.class);
    private AlgebricTree treeX;

    public FctXY() {

        formulaX.setElem("10.0");
        setFormulaX(formulaX.getElem());

    }

    public void setFormulaX(String formulaX) {
        this.formulaX.setElem(formulaX);

        try {
            treeX = new AlgebricTree(formulaX);
            treeX.getParametersValues().put("x", 0.0);
            treeX.construct();
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getFormulaX() {
        return formulaX.getElem();
    }

    public double result(double input) {
        treeX.getParametersValues().put("x", input);
        try {
            return treeX.eval();
        } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
        return Double.NaN;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("formulaX/fonction Y=f(x)", formulaX);
    }

    @Override
    public String toString() {
        return "fctXY( \"" + formulaX + "\" )\n";
    }
}
