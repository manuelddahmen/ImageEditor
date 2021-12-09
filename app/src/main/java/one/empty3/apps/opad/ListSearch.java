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
/*__

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

 * Created by manuel on 19-05-17.
 */
/*
public class ListSearch<E> extends ArrayList<E> {
    public E search(String fieldOrGetter, Object value) throws IllegalAccessException {
        Iterator<E> it = this.iterator();

        boolean found = false;

        while(it.hasNext() && found==false) {
            E element = it.next();

            Field f = null;
            Object invoke = null;
            try {
                f = element.getClass().getField(fieldOrGetter);
            } catch (NoSuchFieldException e) {
                try {
                    Method m = element.getClass().getMethod(fieldOrGetter);
                    try {
                        invoke = m.invoke(element, (Object)null);
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    }
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                }
            }

            if(value.equals(f.get(element)) || value.equals(invoke)) {
                return element;
            }


        }

        return null;
    }
}
*/