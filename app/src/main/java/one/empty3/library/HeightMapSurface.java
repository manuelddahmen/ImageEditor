/*
 * Copyright (c) 2024. Manuel Daniel Dahmen
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

package one.empty3.library;

import android.graphics.Bitmap;

import javaAnd.awt.image.BufferedImage;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.tribase.Plan3D;

/***
 * Created by manue on 17-03-19.
 * Update 2021.
 */
public abstract class HeightMapSurface extends ParametricSurface {
    protected StructureMatrix<ImageContainer> image = new StructureMatrix<>(0, ImageContainer.class);
    protected StructureMatrix<ParametricSurface> surface = new StructureMatrix<>(0, ParametricSurface.class);

    public HeightMapSurface() {
        ImageContainer imageContainer = new ImageContainer();
        imageContainer.getImage().setElem(Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888));
        image.setElem(imageContainer);

        surface.setElem(new Plan3D());
    }

    public HeightMapSurface(ParametricSurface ps, BufferedImage image) {
        this.image.setElem(new ImageContainer(image.getBitmap()));
        this.surface.setElem(ps);
    }

    public Point3D height(double u, double v) {

        Bitmap elem = image.getElem().getImage().getElem();

        int i = (int) (u * (image.getElem().getImage().getElem().getWidth() ));
        int j = (int) (v * (image.getElem().getImage().getElem().getHeight()));
        if(i<0) i = 0;
        if(j<0) j = 0;
        if(i>=image.getElem().getImage().getElem().getWidth()) i = image.getElem().getImage().getElem().getWidth()-1;
        if(j>=image.getElem().getImage().getElem().getHeight()) j = image.getElem().getImage().getElem().getHeight()-1;


        return surface.getElem().calculerPoint3D(u, v).plus(
                surface.getElem().calculerTangenteU(u,v).prodVect(surface.getElem().calculerTangenteV(u,v)
                ).norme1().mult(elem.getPixel(i,j)));
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("image/Level map", image);
        getDeclaredDataStructure().put("surface/Base surface", image);
    }

    public StructureMatrix<ImageContainer> getImage() {
        return image;
    }

    public void setImage(StructureMatrix<ImageContainer> image) {
        this.image = image;
    }

    public StructureMatrix<ParametricSurface> getSurface() {
        return surface;
    }

    public void setSurface(StructureMatrix<ParametricSurface> surface) {
        this.surface = surface;
    }
}
