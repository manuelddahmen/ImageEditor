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

/*

 Vous êtes libre de :

 */
package one.empty3.library;

import android.graphics.Color;

import one.empty3.library.core.animation.Animation;
import one.empty3.library.core.extra.SimpleSphere;


import java.util.Random;

public class RandomSpheres extends Animation {

    private Point3D[] ran;
    private Point3D[] next;
    private int n = 10;
    private double t = 0;
    private Random r = new Random();

    public RandomSpheres(Scene s) {
        //super(s, new ECDim(1000, 1000));
        n = 10;
        ran = new Point3D[n];
        next = new Point3D[n];
        for (int i = 0; i < n; i++) {
            ran[i] = new Point3D(r.nextDouble() * 100, r.nextDouble() * 100, r.nextDouble() * 100);
            next[i] = new Point3D(r.nextDouble() * 100, r.nextDouble() * 100, r.nextDouble() * 100);

        }

    }

    public void modifier() {
        Scene s = new Scene();
        t += 0.01f;
        if (t > 1) {
            t = 0;
            for (int i = 0; i < n; i++) {
                ran[i] = next[i];
                next[i] = new Point3D(r.nextDouble() * 100, r.nextDouble() * 100, r.nextDouble() * 100);

            }
        }
        for (int i = 0; i < n; i++) {
            SimpleSphere ss = new SimpleSphere(ran[i].mult((double) 1 - t).plus(next[i].mult((double) t)), i, Color.WHITE);
            s.add(ss);
        }
        scene = s;
    }
}
