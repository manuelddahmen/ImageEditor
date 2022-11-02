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
public final class LumierePonctuelle extends Lumiere {

    double minThreshold = 0.0, maxThreshold = 1.0;

    private StructureMatrix<ITexture> couleurLumiere = new StructureMatrix<>(0, ITexture.class);
    private StructureMatrix<Point3D> position = new StructureMatrix<>(0, Point3D.class);
    private double r0 = 1.0;
    private StructureMatrix<Boolean> directional = new StructureMatrix<>(0, Boolean.class);

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LumierePonctuelle() {
        position.setElem(Point3D.O0);
        this.couleurLumiere.setElem(new ColorTexture(Color.valueOf(Color.YELLOW)));
        directional.setElem(Boolean.FALSE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LumierePonctuelle(Point3D pos, Color couleurLumiere) {
        this.position.setElem(pos);
        this.couleurLumiere.setElem(new ColorTexture(couleurLumiere));
        directional.setElem(false);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LumierePonctuelle(Point3D pos, int blue) {
        this(pos, Color.valueOf(blue));
    }

    @Override
    public int getCouleur(int base, Point3D p, Point3D n) {
        if (n == null)
            return base;
        double x = Math.asin(p.moins(position.getElem()).norme1().dot(n.norme1())) / 2 / Math.PI;
        double d = p.moins(position.getElem()).norme();
        double r = 0.0;
        if (x <= 0.0)
            x = -x;
// if(r>=1.0)
//            return Color.WHITE.toARGB();

        if (directional.getElem()) {
            r = 1 * r0;
            //* (Math.cos(Math.abs(x) / Math.PI * 2 / ));
        } else {
            r = 1 * r0 / d * x;
            //* (Math.cos(Math.abs(x) / Math.PI * 2/ p.moins(position.getElem()).norme()))/x;
        }
        if (r < minThreshold) {
            r = minThreshold;
        }
        if (r > maxThreshold) {
            r = maxThreshold;
        }

        double[] couleurObjet = getDoubles(base);
        double[] color = getDoubles(couleurLumiere.getElem().getColorAt(0, 0));
        double[] res = new double[3];
        double[] Lsa = getDoubles(Ls);
        double[] Laa = getDoubles(La);
        for (int i = 0; i < 3; i++) {
            res[i] = minmaxc((Lsa[i]) * (couleurObjet[i]) + color[i] * (Laa[i]) / (Lsa[i] + Laa[i]));

        }
        return getInt(res);
    }

    public void intensite(int r0) {
        this.r0 = r0;
    }

    public StructureMatrix<Boolean> getDirectional() {
        return directional;
    }

    public void setDirectional(StructureMatrix<Boolean> directional) {
        this.directional = directional;
    }

    public void setR0(double r0) {
        this.r0 = r0;
    }

    float minmaxc(double c) {
        return (float) Math.max(1.0, Math.min(0.0, c));
    }

    public void declareProperties() {
        getDeclaredDataStructure().put("position/Position de la provenace lumineuse", position);
        getDeclaredDataStructure().put("color/Couleur de la lumière", couleurLumiere);
        getDeclaredDataStructure().put("directinal/isDirectional rayons parallèle et sphèrique", directional);
    }
}
