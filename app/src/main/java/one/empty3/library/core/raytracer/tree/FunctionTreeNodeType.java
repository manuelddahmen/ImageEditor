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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*__
 * Created by manuel on 16-12-16.
 */
public abstract class FunctionTreeNodeType extends TreeNodeType {
    protected AlgebricTree algebricTree;
    private String fName;
    protected double[] objects;

    public AlgebricTree getAlgebricTree() {
        return algebricTree;
    }

    public void setAlgebricTree(AlgebricTree algebricTree) {
        this.algebricTree = algebricTree;
    }

    @Override
    public Double eval() {
        return null;
    }

    @Override
    protected void instantiate(Object[] objects) {
        this.fName = (String) objects[0];
    }

    public String getFName() {
        return fName;
    }

    public Double compute() {
        return 0.0;
    }

    public void setObjects(double[] objects) {
        this.objects = objects;
    }

    public double[] getValue() {
        return objects;
    }

    public Double compute(String fName, TreeNode treeNode) {
        try {
            Method method;
            method = Math.class.getMethod(fName, double.class);
            try {
                return (Double) method.invoke(Math.class, treeNode.eval());
            } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
