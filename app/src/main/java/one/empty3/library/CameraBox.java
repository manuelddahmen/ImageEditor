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

/*__
 * @author Manuel DAHMEN
 * @date
 */
public class CameraBox extends Representable {

    public static final int PERSPECTIVE_ISOMETRIQUE = 1;
    public static final int PERSPECTIVE_POINTDEFUITE = 1;
    private StructureMatrix<Double> angleX = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Double> angleY = new StructureMatrix<>(0, Double.class);
    private int type = PERSPECTIVE_POINTDEFUITE;

    public CameraBox() {

        angleX.setElem(Math.PI * 2 / 360 * 60);
        angleY.setElem(Math.PI * 2 / 360 * 60);
    }

    public Double getAngleX() {
        return angleX.getElem();
    }

    public void setAngleX(Double angleX) {
        this.angleX.setElem(angleX);
    }

    public void angleXr(double angleX, double ratioXY) {
        this.angleX.setElem(angleX);
        this.angleY.setElem(angleX / ratioXY);
    }

    public void angleXY(double angleX, double angleY) {
        this.angleX.setElem(angleX);
        this.angleY.setElem(angleY);
    }

    public Double getAngleY() {
        return angleY.getElem();
    }

    public void setAngleY(Double angleY) {
        this.angleY.setElem(angleY);
    }

    public void setAngleYr(double angleY, double ratioXY) {
        this.angleY.setElem(angleY);
        this.angleX.setElem(angleY * ratioXY);
    }

    public void perspectiveIsometrique() {
        this.type = PERSPECTIVE_ISOMETRIQUE;
    }

    public void perspectivePointDeFuite() {
        this.type = PERSPECTIVE_POINTDEFUITE;
    }

    public int type() {
        return type;
    }

    public void viserObjet(Representable r) {
        throw new UnsupportedOperationException("Non supportée");
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("angleX/angle horizontal caméra", angleX);
        getDeclaredDataStructure().put("angleY/angle vertical caméra", angleY);

    }
}
