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
package one.empty3.library;


import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Lighter implements IBasicLighter {

    Point3D pointLumiere;
    double intensite;
    double rayonEnglobant;

    public Lighter() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Color calculerLumierePoint(Point3D point, Color couleur, Point3D normale) {
        double facteurAngulaire = point.moins(pointLumiere).norme1().prodScalaire(normale.norme1());

        double distance = Point3D.distance(point, pointLumiere);

        double formule = facteurAngulaire * intensite * Math.exp(-distance * distance / rayonEnglobant / rayonEnglobant);

        float[] colorsComp;

        colorsComp = couleur.getComponents();

        for (int i = 0; i < 3; i++) {
            colorsComp[i] = (float) (colorsComp[i] * formule);
            if (colorsComp[i] > 1f) {
                colorsComp[i] = 1f;
            } else if (colorsComp[i] < 0f) {
                colorsComp[i] = 0f;
            }
        }

        return Color.valueOf(colorsComp[0], colorsComp[1], colorsComp[2]);
    }

    public void config(Point3D pointLumiere, double intensite, double rayonEnglobant) {
        this.pointLumiere = pointLumiere;
        this.intensite = intensite;
        this.rayonEnglobant = rayonEnglobant;
    }
}
