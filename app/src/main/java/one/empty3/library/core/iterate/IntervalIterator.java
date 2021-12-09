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

package one.empty3.library.core.iterate;

/*__
 * Created by Win on 10-01-19.
 */
public class IntervalIterator {
    public int dim;
    public double[] xmin;
    public double[] xmax;
    public int[] incr;
    public int currentDim;
    public double[] currentCordinates;

    public IntervalIterator() {
    }

    /*__
     * Réfléchir surface et caméra en plus
     * pas 1 -1 et lsystem
     * @param step
     */
    public void iterate11(int[] step) {

    }

    public boolean next() throws InvalidObjectConfiguration {
        currentCordinates[currentDim] += incr[currentDim];
        if (currentCordinates[currentDim] > xmax[currentDim]) {
            currentCordinates[currentDim] = xmin[currentDim];
            currentDim++;
        }
        if (currentDim >= currentCordinates.length) {
            currentDim = 0;
            return false;
        } else {
            return true;

        }
    }
}
