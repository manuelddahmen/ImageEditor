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
import one.empty3.library.core.tribase.Plan3D;

import android.graphics.Bitmap;

/***
 * Created by manue on 17-03-19.
 * Update 2021.
 */
public class HeightMapSurface extends ParametricSurface {
    protected StructureMatrix<ImageContainer> image = new StructureMatrix<>(0, ImageContainer.class);
    protected StructureMatrix<ParametricSurface> surface = new StructureMatrix<>(0, ParametricSurface.class);

    public HeightMapSurface() {
        ImageContainer imageContainer = new ImageContainer();
        imageContainer.getImage().setElem(Bitmap.createBitmap(1000, 1000, Bitmap.Config.RGB_565));
        image.setElem(imageContainer);

        surface.setElem(new Plan3D());
    }

    public HeightMapSurface(ParametricSurface ps, Bitmap image) {
        this.image.setElem(new ImageContainer(image));
        this.surface.setElem(ps);
    }

    public Point3D height(double u, double v) {

        Bitmap elem = image.getElem().getImage().getElem();

        int i = (int) (u * (image.getElem().getImage().getElem().getWidth()));
        int j = (int) (v * (image.getElem().getImage().getElem().getHeight()));
        if (i < 0) i = 0;
        if (j < 0) j = 0;
        if (i >= image.getElem().getImage().getElem().getWidth())
            i = image.getElem().getImage().getElem().getWidth() - 1;
        if (j >= image.getElem().getImage().getElem().getHeight())
            j = image.getElem().getImage().getElem().getHeight() - 1;


        return surface.getElem().calculerPoint3D(u, v).plus(
                surface.getElem().calculerTangenteU(u, v).prodVect(surface.getElem().calculerTangenteV(u, v)
                ).norme1().mult(elem.getPixel(i, j)));
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
