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

package one.empty3.library.core.raytracer;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;


public class RtColor {
    public double red, green, blue, alpha;    // Les trois composantes de la couleur
    RtColor c;

    // constructeurs et destructeur
    public RtColor() {
        red = 0;
        green = 0;
        blue = 0;
        alpha = 0;
    }

    public RtColor(double r, double g, double b) {
        red = r;
        green = g;
        blue = b;
        alpha = 0;

    }

    public RtColor(double r, double g, double b, double a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RtColor(Color color) {
        red = color.red();
        green = color.green();
        blue = color.blue();
        alpha = color.alpha();

    }

    // operateurs
    public static RtColor mult(RtColor c1, RtColor c2) {
        return new RtColor(c1.red() * c2.red(), c1.green() * c2.green(), c1.blue() * c2.blue());
    }

    public static RtColor mult(RtColor c1, double multiple) {
        return new RtColor(c1.red() * multiple, c1.green() * multiple, c1.blue() * multiple);
    }

    public static RtColor add(RtColor c1, RtColor c2) {
        return new RtColor(c1.red() + c2.red(),
                c1.green() + c2.green(),
                c1.blue() + c2.blue()
        );
    }

    public static RtColor plus(RtColor c1, RtColor c2) {
        return new RtColor(c1.red() + c2.red(), c1.green() + c2.green(), c1.blue() + c2.blue());
    }

    public static RtColor div(RtColor c1, float multiple) {
        return new RtColor(c1.red() / multiple, c1.green() / multiple, c1.blue() / multiple);
    }


    public static RtColor normalizeColor(RtColor finalColor) {
        double max = Math.max(finalColor.red(), Math.max(finalColor.green(), Math.max(finalColor.blue(), finalColor.getAlpha())));
        if (max > 1.0f || max < 0.0f) {
            finalColor = RtColor.mult(finalColor, 1 / max);
        }/*
        if (finalColor.red() > 1.0f)
            finalColor.red = 1.0f;
        if (finalColor.green() > 1.0f)
            finalColor.green = 1.0f;
        if (finalColor.blue() > 1.0f)
            finalColor.blue = 1.0f;
        if (finalColor.red() < .0f)
            finalColor.red = .0f;
        if (finalColor.green() < .0f)
            finalColor.green = .0f;
        if (finalColor.blue() < .0f)
            finalColor.blue = .0f;
        */
        return finalColor;
    }

    public double red() {
        return red;
    }

    public double green() {
        return green;
    }

    public double blue() {
        return blue;
    }

    public double getAlpha() {
        return alpha;
    }

    public Color toColor() {
        RtColor c = normalizeColor(this);
        return Color.valueOf((float) c.red(), (float) c.green(), (float) c.blue(), (float) c.getAlpha());
    }


}
