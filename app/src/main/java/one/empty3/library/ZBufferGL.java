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

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.ParametricVolume;
import one.empty3.library.core.nurbs.RPv;

import java.awt.*;
import java.io.File;
/*__
 * Created by manue on 15-06-19.
 */
public class ZBufferGL implements ZBuffer {
    private int idImg;

    @Override
    public Camera camera() {
        return null;
    }

    @Override
    public void couleurDeFond(ITexture couleurFond) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void draw(Representable r) {

    }

    @Override
    public int getColorAt(Point p) {
        return 0;
    }

    @Override
    public ZBuffer getInstance(int x, int y) {
        return null;
    }

    @Override
    public ECBufferedImage image() {
        return null;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public void isobox(boolean isBox) {

    }


    @Override
    public void line(Point3D p1, Point3D p2, ITexture t) {

    }

    @Override
    public boolean lock() {
        return false;
    }

    @Override
    public void plotPoint(Point3D p, Color c) {

    }

    @Override
    public int resX() {
        return 0;
    }

    @Override
    public int resY() {
        return 0;
    }
    
    @Override
    public boolean checkScreen(Point p1) {
        return false;
    }
    @Override
    public Scene scene() {
        return null;
    }

    @Override
    public void scene(Scene s) {

    }

    @Override
    public void next() {

    }

    @Override
    public void testDeep(Point3D point3D) {

    }

    @Override
    public void testDeep(Point3D p, Color c) {

    }

    @Override
    public void testDeep(Point3D p, int c) {

    }

    @Override
    public void tracerLumineux() {

    }

    @Override
    public boolean unlock() {
        return false;
    }

    @Override
    public void zoom(float z) {

    }

    @Override
    public ITexture backgroundTexture() {
        return null;
    }

    @Override
    public void backgroundTexture(ITexture iTexture) {

    }

    @Override
    public int largeur() {
        return 0;
    }

    @Override
    public int hauteur() {
        return 0;
    }

    @Override
    public void setDimension(int width, int height) {

    }
    public void copyResourceFiles(File destDirectory){}
 
    @Override
    public Point3D clickAt(double x, double y) {
        return null;
    }


    @Override
    public int idz() {
        return idImg;
    }

    @Override
    public void drawElementVolume(Representable representable, ParametricVolume volume) {

        throw new UnsupportedOperationException("Not implemented");//return null;
    }

    @Override
    public int getDisplayType() {
        return 0;
    }

    @Override
    public double maxDistance(Point p1, Point p2, Point p3, Point p4) {
        return 0;
    }

    @Override
    public void testDeep(Point3D pFinal, ITexture texture, double u, double v, ParametricSurface n) {

    }

    @Override
    public int la() {
        return resX();
    }

    @Override
    public int ha() {
        return resY();
    }


    public void idzpp() {
        idImg++;
    }
}
