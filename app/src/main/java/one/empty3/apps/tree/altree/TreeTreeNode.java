/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.apps.tree.altree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import one.empty3.apps.tree.altree.AlgebraicFormulaSyntaxException;
import one.empty3.apps.tree.altree.AlgebricTree;
import one.empty3.apps.tree.altree.TreeNode;
import one.empty3.apps.tree.altree.TreeNodeEvalException;
import one.empty3.apps.tree.altree.TreeNodeType;

public class TreeTreeNode extends one.empty3.apps.tree.altree.TreeNode {
    private final one.empty3.apps.tree.altree.AlgebricTree tree;
    private Method method = null;

    public TreeTreeNode(TreeNode t, Object[] objects, TreeNodeType type) {
        super(t, objects, type);
        tree = new AlgebricTree((String) objects[0], (Map<String, Double>) objects[1]);
        try {
            tree.construct();
            if (objects[2] instanceof String && ((String) objects[2]).length() > 0) {
                String call = (String) objects[2];
                if (call.length() > 1)
                    method = Math.class.getMethod(call, double.class);
            }
        } catch (AlgebraicFormulaSyntaxException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        double r = 0.0;
        r = tree.eval();
        if (method != null) {
            try {
                r = (Double) method.invoke(Math.class, r);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return r;

    }
}
