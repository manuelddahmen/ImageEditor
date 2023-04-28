/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.apps.morph;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.List;

public class ShapeMorph1 extends ParametricSurface {
    double t;
    private ITexture text1;
    private ITexture text2;
    private StructureMatrix<Point3D> grid1 = new StructureMatrix<>(2, Point3D.class);
    private StructureMatrix<Point3D> grid2 = new StructureMatrix<>(2, Point3D.class);
    private int gridSizeX = 0;
    private int gridSizeY = 0;
    private ITexture textTexture;

    ShapeMorph1(ITexture text1, ITexture text2, StructureMatrix<Point3D> grid1,
               StructureMatrix<Point3D> grid2) {
        this.text1 = text1;
        this.text2 = text2;
        this.grid1 = grid1;
        this.grid2 = grid2;
        this.t = 0;
    }

    public ITexture getText1() {
        return text1;
    }

    public void setText1(ITexture text1) {
        this.text1 = text1;
    }

    public ITexture getText2() {
        return text2;
    }

    public void setText2(ITexture text2) {
        this.text2 = text2;
    }

    public StructureMatrix<Point3D> getGrid1() {
        return grid1;
    }

    public void setGrid1(StructureMatrix<Point3D> grid1) {
        this.grid1 = grid1;
    }

    public StructureMatrix<Point3D> getGrid2() {
        return grid2;
    }

    public void setGrid2(StructureMatrix<Point3D> grid2) {
        this.grid2 = grid2;
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public void setGridSizeX(int gridSizeX) {
        this.gridSizeX = gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setGridSizeY(int gridSizeY) {
        this.gridSizeY = gridSizeY;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    private Point3D calculerColorUVGridXY(Point3D [] gridT, double du, double dv) {
        Point3D pU0 = gridT[0].plus(gridT[1].moins(gridT[0]).mult(du));
        Point3D pU1 = gridT[3].plus(gridT[2].moins(gridT[3]).mult(du));
        Point3D pV0 = gridT[2].plus(gridT[1].moins(gridT[2]).mult(dv));
        Point3D pV1 = gridT[3].plus(gridT[0].moins(gridT[3]).mult(dv));

        Point3D pU = pU0.plus(pU1.moins(pU0).mult(dv));
        Point3D pV = pV0.plus(pV1.moins(pV0).mult(du));


        Point3D mult = pU;//.plus(pV).mult(0.5);

        return mult;
    }

    public int colorMean(int color1, int color2) {
        double[] doubles1 = Lumiere.getDoubles(color1);
        double[] doubles2 = Lumiere.getDoubles(color2);
        double[] d3 = new double[3];
        for (int i = 0; i < d3.length; i++) {
            d3[i] = doubles1[i] * (1-t) + doubles2[i] * t;
            if(d3[i]>=1)
                d3[i] = 1;
            else if(d3[i]<0)
                d3[i] = 0;
        }
        return Lumiere.getInt(d3);
    }
    /**
     * @param u ordonnée du point intersection texturé
     * @param v coordonnée du point intersection texturé
     * @return u, v : coordonnées de texurée dans la case
     */
    @Override
    public Point3D calculerPoint3D(double u, double v) {
        try {

            int sizeGridX = grid1.getData2d().size();
            int sizeGridY = grid1.getData2d().get(0).size();
            int xGrid1 = (int) ((sizeGridX) * u);
            int yGrid1 = (int) ((sizeGridY) * v);
            int xGrid2 = xGrid1+1;
            int yGrid2 = yGrid1+1;

            double du = (u*(sizeGridX)-xGrid1);
            double dv = (v*(sizeGridY)-yGrid1);


            if (xGrid1 >= grid1.getData2d().size() || xGrid2 >= grid2.getData2d().size() ||
                    yGrid1 >= grid1.getData2d().get(xGrid1).size()
                    || yGrid2 >= grid2.getData2d().get(xGrid2).size()) {
                return null;
            } else {

                Point3D[] gridP1 = new Point3D[]{
                        grid1.getElem(xGrid1, yGrid1),
                        grid1.getElem(xGrid2, yGrid1),
                        grid1.getElem(xGrid2, yGrid2),
                        grid1.getElem(xGrid1, yGrid2)
                };
                Point3D[] gridP2 = new Point3D[]{
                        grid2.getElem(xGrid1, yGrid1),
                        grid2.getElem(xGrid2, yGrid1),
                        grid2.getElem(xGrid2, yGrid2),
                        grid2.getElem(xGrid1, yGrid2)
                };
                Point3D[] gridT = new Point3D[4];

                for (int i = 0; i < 4; i++) {
                    gridT[i] = gridP1[i].plus(gridP2[i].moins(gridP1[i]).mult(t));
                }

                Point3D pT = calculerColorUVGridXY(gridT, du, dv);
                int c;
                //if(texture instanceof TextureMorphing) {
                //    c = texture.getColorAt(u, v);
                //} else
                    c = colorMean(text1.getColorAt(u, v),
                            text2.getColorAt(u, v));

                pT.texture(new ColorTexture(c));

                return pT;
            }
        } catch (NullPointerException | IndexOutOfBoundsException exception1) {
            exception1.printStackTrace();
            return null;
        }

    }

}
