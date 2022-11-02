package one.empty3.library.core.nurbs;

import one.empty3.library.core.raytracer.tree.*;

public class F {
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


    /*__
     * Created by md 2020 4 8 */
    private int dima, dimb;
    private AlgebricTree treeF;

    /*__
     * input x(n:int) : formula
     * separator ; y(n) = formula(x(n:int))
     */
    public void result(Double[] input, Double[] output) {

    }

    private String fs = "1:1:1.0";
    private String formula = "1.0";

    /*__
     *
     * @param fs formula:dima(int):dimb(int)
     * // TODO algebre tree n dim
     *
     */
    public F(String fs) {

        String[] splits = fs.split(":");

        if (splits.length == 3) {
            this.fs = fs;
            this.formula = splits[0];
            dima = Integer.parseInt(splits[1]);
            dimb = Integer.parseInt(splits[2]);
        }
        try {
            treeF = new AlgebricTree(formula);
        } catch (AlgebraicFormulaSyntaxException ex) {
            ex.printStackTrace();
        }

    }


}
