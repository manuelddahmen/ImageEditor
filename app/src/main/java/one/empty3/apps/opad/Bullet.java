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

import one.empty3.library.MoveeObject;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.Scene;
import one.empty3.library.stl_loader.IncorrectFormatException;
import one.empty3.library.stl_loader.ParsingErrorException;
import one.empty3.library.stl_loader.StlFile;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/*__
 * Created by manuel on 29-06-17.
 */
public class Bullet extends Representable implements MoveeObject {
    private Point3D origin;
    private Point3D direction;
    private double speed;
    private Representable shape;
    private long nanoTime;

    public Bullet(Point3D origin, Point3D direction, double speed, Representable shape, long nanoTime) {
        this();
        this.origin = origin;
        this.direction = direction;
        this.speed = speed;
        this.shape = shape;
        this.nanoTime = nanoTime;

    }

    public Bullet() {
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
        String bundleString = bundle.getString("resource3D.stl.Bullet1");
        StlFile stlFile = new StlFile();
        try {
            Scene load = stlFile.load(bundleString);
            this.setShape(load.getObjets().getElem(0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IncorrectFormatException e) {
            e.printStackTrace();
        } catch (ParsingErrorException e) {
            e.printStackTrace();
        }


    }

    public long getNanoTime() {
        return nanoTime;
    }

    public void setNanoTime() {
        this.nanoTime = System.nanoTime();
    }

    public Point3D getOrigin() {
        return origin;
    }

    public void setOrigin(Point3D origin) {
        this.origin = origin;
    }

    public Point3D getDirection() {
        return direction;
    }

    public void setDirection(Point3D direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Representable getShape() {
        return shape;
    }

    public void setShape(Representable shape) {
        this.shape = shape;
    }

    public Point3D getPosition() {
        return origin.plus(direction.norme1().mult(speed));
    }

    @Override
    public void setPositionAtTime(Point3D point3D, long l) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Point3D getCurrentPosition() {
        return getPosition();
    }
}
