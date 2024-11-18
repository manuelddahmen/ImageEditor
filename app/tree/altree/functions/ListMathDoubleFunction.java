/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

package one.empty3.library1.tree.functions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import one.empty3.library.StructureMatrix;

import java.util.List;

public class ListMathDoubleFunction {
    public static String functionName = "";
    public boolean isExited;

    public static String[] getList() {
        List<String> sequences = new ArrayList<String>();
        Method[] methods = Math.class.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String s = "" + method.getName() + "(";
            String s1 = s;
            for (int j = 0; j < method.getParameters().length; j++) {
                Parameter parameter = method.getParameters()[j];
                if (parameter.getType().equals(Double.class)) {
                    s += parameter.getName() + " : " + parameter.getType().getName();
                }
                if (j < method.getParameterCount() - 1)
                    s += ",";
            }
            s += ") : " + method.getReturnType().getName();

            if (method.getParameterCount() <= 1 && method.getReturnType().equals(double.class))
                sequences.add(s1);
        }
        Field[] numbers = Math.class.getDeclaredFields();
        for (int i = 0; i < numbers.length; i++) {
            Field field = numbers[i];
            String s = "" + field.getName() + " : " + field.getType();
            //sequences.add(s);
        }

        String[] cs = new String[sequences.size()];

        return sequences.toArray(cs);

    }
}
