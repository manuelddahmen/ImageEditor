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

package one.empty3.library.core;

import java.util.Arrays;

/*__
 * Created by manue on 24-07-19.
 */
public class EcArrays<T> {
    public T[] deleteRowAtDim1(T[] array, int pos) {
        T[] ts = Arrays.copyOf(array, array.length - 1);
        int i0 = 0;
        for (int i = 0; i < ts.length; i++) {
            if (pos != i) {
                ts[i] = array[i0];
                i0++;
            }
        }
        return ts;
    }

    public T[][] deleteRowAtDim2(T[][] array, int pos) {
        T[][] ts = Arrays.copyOf(array, array.length - 1);
        int i0 = 0;
        for (int i = 0; i < ts.length; i++) {
            if (pos != i) {
                ts[i] = array[i0];
                i0++;
            }
        }
        return ts;
    }

    public T[][] deleteColAtDim2(T[][] array, int pos) {
        T[][] ts = Arrays.copyOf(array, array.length);
        for (int i = 0; i < array.length; i++) {
            ts[i] = deleteRowAtDim1(array[i], pos);
        }
        return ts;
    }

    public T[] insertRowAtDim1(T[] array, int pos, T value) {
        T[] ts = Arrays.copyOf(array, array.length + 1);
        int i0 = 0;
        for (int i = 0; i < ts.length; i++) {
            if (pos != i) {
                ts[i] = array[i0];
                i0++;
            } else {
                ts[i] = value;
            }
        }
        return ts;
    }

    public T[][] insertRowAtDim2(T[][] array, int pos, int rowSize) {
        T[][] ts = Arrays.copyOf(array, array.length + 1);
        int i0 = 0;
        for (int i = 0; i < ts.length; i++) {
            if (i != pos) {
                ts[i] = array[i0];
                i0++;
            } else {
                ts[i] = Arrays.copyOf(array[0], array[0].length);
            }
        }
        return ts;
    }

    public T[][] insertColAtDim2(T[][] array, int pos) {
        T[][] ts = Arrays.copyOf(array, array.length);

        for (int i = 0; i < array.length; i++) {
            ts[i] = insertRowAtDim1(array[i], pos, null);
        }
        return ts;
    }


}
