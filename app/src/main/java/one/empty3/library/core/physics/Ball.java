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

package one.empty3.library.core.physics;


import one.empty3.library.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Ball {


    public String nom;
    public Color color;
    public Point3D vitesse;
    public Point3D forceAttaction;
    public Point3D forceRepulsion;
    public double masse;
    public double energie;
    public Point3D position;
    public BufferedImage texture;
    public Matrix33 rotation;

    public Ball(String nom, Color color, Point3D vitesse, double masse,
                double energie, Point3D position, BufferedImage texture,
                Matrix33 rotation) {
        super();
        this.nom = nom;
        this.color = color;
        this.vitesse = vitesse;
        this.masse = masse;
        this.energie = energie;
        this.position = position;
        this.texture = texture;
        this.rotation = rotation;
    }
}
