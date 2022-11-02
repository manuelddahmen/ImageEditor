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

/*__
 * *
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library;


import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public final class LumierePonctuellePeriodique extends Lumiere {

    private Color couleurLumiere = Color.valueOf(Color.RED);
    private Point3D position;
    private double k = 1;
    private double r0 = 11;

    public LumierePonctuellePeriodique(Point3D pos, Color couleurLumiere) {
        this.position = pos;
        this.couleurLumiere = couleurLumiere;
    }

    @Override
    public int getCouleur(int base, Point3D p, Point3D n) {
        double x = (n.norme1().prodScalaire(position.moins(p).norme1()) + 1) / 2;
        double r = x;
        Color couleurObjet = Color.valueOf(base);
        return Color.valueOf((float) ((couleurObjet.red() / 256.0) * r + (couleurLumiere.red() / 256.0) * (1 - r)), (float) ((couleurObjet.green() / 256.0) * r + (couleurLumiere.green() / 256.0) * (1 - r)), (float) ((couleurObjet.blue()
                / 256.0) * r + (couleurLumiere.blue() / 256.0) * (1 - r))).toArgb();
    }

    public void r0(int r0) {
        this.r0 = r0;
    }

}
