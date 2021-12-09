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
Global license : 

 CC Attribution

 author Manuel Dahmen _manuel.dahmen@gmx.com_

 ***/


package one.empty3.apps.opad;

import one.empty3.library.*;
import one.empty3.library.Polygon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/*__
 *
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class SimpleMap extends Map
{
    private Point3D gamePosition;
    private final Camera camera;
    private final ArrayList<Polygon> polygons;
    private ZBuffer z;


    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public SimpleMap() {
        polygons = new ArrayList<Polygon>();
        camera = new Camera(new Point3D(0.5d, 1d, 0.5), new Point3D(0.5, 0d, 0.5));
    }

    public Camera camera() {
        return camera;
    }

    public void prepareImage() {
        z = ZBufferFactory.instance(width, height);
        z.scene(new Scene());
        Iterator<Polygon> itP;
        itP = polygons.iterator();
        while(itP.hasNext())
            z.scene().add(itP.next());
        z.scene().add(gamePosition);
    }

    @Override
    public Point3D getGamePosition() {
        return gamePosition;
    }

    @Override
    public void setGamePosition(Point3D coordonnees) {
        this.gamePosition = coordonnees;
    }

    @Override
    public BufferedImage genererImage() {
        prepareImage();
        return z.image();
    }

    @Override
    public void initCard(int width, int height, Color[][] objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
