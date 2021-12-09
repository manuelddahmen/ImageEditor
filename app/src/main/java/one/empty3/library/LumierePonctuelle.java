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

import java.awt.*;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public final class LumierePonctuelle extends Lumiere {

    double minThreshold = 0.0, maxThreshold = 1.0;

    private StructureMatrix<ITexture> couleurLumiere = new StructureMatrix<>(0, ITexture.class);
    private StructureMatrix<Point3D> position = new StructureMatrix<>(0, Point3D.class);
    private double r0 = 100.0;
    private StructureMatrix<Boolean> directional = new StructureMatrix<>(0, Boolean.class);

    public LumierePonctuelle() {
        position.setElem(Point3D.O0);
        this.couleurLumiere.setElem(new ColorTexture(Color.YELLOW));
        directional.setElem(Boolean.FALSE);
    }

    public LumierePonctuelle(Point3D pos, Color couleurLumiere) {
        this.position.setElem(pos);
        this.couleurLumiere.setElem(new ColorTexture(couleurLumiere));
        this.Ls = couleurLumiere;
        directional.setElem(Boolean.FALSE);

    }

    @Override
    public int getCouleur(int base, Point3D p, Point3D n) {
        if (n == null)
            return base;
        //double x = Math.asin(p.moins(position.getElem()).norme1().dot(n.norme1())) / 2 / Math.PI;
        Point3D l = p.moins(position.getElem()).norme1();

        Double dot = l.dot(n.norme1());
        double angle = Math.cos(dot);


        double[] couleurObjet = getDoubles(base);
        double[] res = new double[3];
        double[] Lsa = getRgb(Ls);
        double[] Laa = getRgb(La);
        for (int i = 0; i < 3; i++) {
            double x = (1-angle)*couleurObjet[i]
                    + (angle) * Lsa[i];
            res[i] = Math.exp(-x*x*2);

            if (res[i] < minThreshold) {
                res[i] = minThreshold;
                res[i] = couleurObjet[i];
            }
            if (res[i] > maxThreshold) {
                res[i] = Lsa[i];
            }

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
