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

import one.empty3.library.Camera;
import one.empty3.library.Point3D;

import java.util.HashMap;
import java.util.ResourceBundle;

/*__
 * Created by manuel on 10-07-17.
 */
public class PositionMobile {
    protected final ResourceBundle bundle;
    protected final PositionUpdate positionUpdate;
    protected final double positionIncrement;
    protected final double positionIncrement2;
    protected Terrain terrain;
    protected Point3D positionSol;
    protected Point3D angleVisee;

    protected Point3D positionMobile;
    protected Point3D angleVueMobile;
    protected double score;
    protected HashMap<Long, Point3D> trace;


    public PositionMobile(PositionUpdate positionUpdate) {
        super();
        bundle = ResourceBundle.getBundle("one.empty3.apps.opad.Bundle"); // NOI18N
        positionIncrement = Double.parseDouble(bundle.getString("positionIncrement"));
        positionIncrement2 = Double.parseDouble(bundle.getString("positionIncrement2"));
        this.positionUpdate = positionUpdate;
        this.terrain = positionUpdate.getTerrain();
        positionSol = new Point3D(0.5, 0.5, 0.0);
        getPositionSol().setZ(Double.parseDouble(bundle.getString("hauteur")));
        angleVisee = new Point3D(Point3D.O0);
        trace = new HashMap<>();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Point3D getPositionSol() {
        return positionSol;
    }

    public void setPositionSol(Point3D positionSol) {
        trace.put(System.nanoTime(), this.positionSol);
        System.out.println("+");
        this.positionSol = positionSol;
    }

    public Point3D getAngleVisee() {
        return angleVisee;
    }

    public void setAngleVisee(Point3D angleVisee) {
        this.angleVisee = angleVisee;
    }

    public Point3D getAngleVueMobile() {
        return angleVueMobile;
    }

    public void setAngleVueMobile(Point3D angleVueMobile) {
        this.angleVueMobile = angleVueMobile;
    }

    public Point3D calcPosition() {
        return getTerrain().p3(getPositionSol());
    }

    public Point3D calcDirection()
    {
        return getTerrain().p3(positionSol.plus(positionUpdate.getVecDir2D()));
    }

    public Point3D calcPosition2D() {
        return getPositionSol();
    }

    public Point3D calcDirection2D() {
        return positionSol.plus(positionUpdate.getVecDir2D());
    }
    public Camera calcCameraMobile()
    {
        final Point3D camera = calcPosition();
        final Point3D lookAt = camera.plus(angleVisee);
        Point3D mult = camera.moins(lookAt).norme1().mult(-positionIncrement);
        return new Camera(camera.moins(mult), lookAt);
    }

    public Camera calcCamera() {
        final Point3D camera = calcPosition();
        final Point3D lookAt = calcDirection();
        Point3D mult = camera.moins(lookAt).norme1().mult(-positionIncrement);
        return new Camera(camera.moins(mult), lookAt);
    }

    public void calculerVitesseEcoulement()
    {
    }


    public Point3D getPositionMobile() {
        return positionMobile;
    }

    public HashMap<Long, Point3D> getTrace() {
        return trace;
    }
}
