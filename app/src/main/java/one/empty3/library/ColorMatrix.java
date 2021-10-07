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

public class ColorMatrix<E> extends Representable {

    /*__
     *
     */
    private static final long serialVersionUID = 8229059930778672554L;
    private String id;
    private Point3D[] nodes;
    private My2DarrayColor colors;
    private E[] elements;
    private Point3D[] interpolate;
    private int dimx;
    private int dimy;

    public ColorMatrix(int dimx, int dimy) {
        super();
        this.dimx = dimx;
        this.dimy = dimy;
    }

    public My2DarrayColor getColors() {
        return colors;
    }

    public void setColors(My2DarrayColor colors) {
        this.colors = colors;
    }

    public int getDimx() {
        return dimx;
    }

    public void setDimx(int dimx) {
        this.dimx = dimx;
    }

    public int getDimy() {
        return dimy;
    }

    public void setDimy(int dimy) {
        this.dimy = dimy;
    }

    public E[] getElements() {
        return elements;
    }

    public void setElements(E[] elements) {
        this.elements = elements;
    }

    public Point3D[] getInterpolate() {
        return interpolate;
    }

    public void setInterpolate(Point3D[] interpolate) {
        this.interpolate = interpolate;
    }

    public Point3D[] getNodes() {
        return nodes;
    }

    public void setNodes(Point3D[] nodes) {
        this.nodes = nodes;
    }

}
