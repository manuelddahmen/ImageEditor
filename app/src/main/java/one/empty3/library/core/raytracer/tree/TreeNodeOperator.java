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

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class TreeNodeOperator extends TreeNodeValue {

    public TreeNodeOperator(TreeNode parent, Object[] values, VariableTreeNodeType type) {
        super(parent, values);
    }

    @Override
    public Double eval() throws TreeNodeEvalException {
        final Object v1 = getChildren().get(0);
        final Object v2 = getChildren().get(1);

        if (v1 instanceof Double && v2 instanceof Double)
            return Math.exp(Math.log((Double) v1) * (Double) v2);
        else
            throw new TreeNodeEvalException("Not valid");
    }
}
