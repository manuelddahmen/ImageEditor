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

import one.empty3.library.Point2D;

import java.awt.*;

public class Plasma {
    public static double scale;
    public static double t_factor = 0.01;

    public static double f(double x, double y, double t) {
        return Math.sin(
                new Point2D(x, y).
                        distance(
                                new Point2D(
                                        (Math.sin(-t*t_factor)),
                                        (Math.cos(-t*t_factor)))
                        ) / scale
        );

    }

    public static Color color(double x, double y, double t) {
        return new Color(
                (float)((Math.cos(Math.PI * f(x,y,t) + 0.5 + t*t_factor)+1)/2),
                (float) ((Math.cos(Math.PI * f(x, y, t) + 1.0 + t * t_factor + Math.PI / 2) + 1) / 2),
                (float) ((Math.cos(Math.PI * f(x, y, t) + 1.5 + t * t_factor + Math.PI) + 1) / 2)
        );
    }
}
